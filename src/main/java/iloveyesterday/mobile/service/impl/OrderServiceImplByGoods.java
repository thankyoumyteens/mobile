package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.*;
import iloveyesterday.mobile.pojo.*;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.util.BigDecimalUtil;
import iloveyesterday.mobile.util.DateTimeUtil;
import iloveyesterday.mobile.util.JsonUtil;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.OrderItemListVo;
import iloveyesterday.mobile.vo.OrderListVo;
import iloveyesterday.mobile.vo.OrderVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("orderService")
public class OrderServiceImplByGoods implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPropertiesMapper propertiesMapper;

    @Resource
    private ShippingMapper shippingMapper;

    @Resource
    private OrderSellerMapper orderSellerMapper;

    @Transactional
    @Override
    public ResponseData<OrderVo> create(Long userId, Long shippingId) {
        // 取出被选中的购物车商品
        List<Cart> cartCheckedList = getCartCheckedList(userId);
        if (CollectionUtils.isEmpty(cartCheckedList) || cartCheckedList.size() <= 0) {
            return ResponseData.error("请选择要购买的商品");
        }

        // 生成订单项
        List<OrderItem> orderItemList = assembleOrderItem(userId, cartCheckedList);
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResponseData.error("生成订单失败");
        }
        // 生成订单
        Order order = assembleOrder(userId, shippingId);
        if (order == null) {
            return ResponseData.error("生成订单失败");
        }

        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem item : orderItemList) {
            item.setOrderNo(order.getOrderNo());
            totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), item.getTotalPrice().doubleValue());
        }
        order.setPayment(totalPrice);

        // 确认库存
        if (!checkStock(orderItemList)) {
            return ResponseData.error("库存不足");
        }

        List<Long> goodsIdList = Lists.newArrayList();
        List<OrderSeller> orderSellerList = Lists.newArrayList();
        for (OrderItem item : orderItemList) {
            Long goodsId = item.getProductId();
            if (!goodsIdList.contains(goodsId)) {
                OrderSeller orderSeller = new OrderSeller();
                orderSeller.setOrderNo(order.getOrderNo());
                orderSeller.setUserId(userId);
                Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
                if (goods == null) {
                    return ResponseData.error();
                }
                orderSeller.setSellerId(goods.getSellerId());
                orderSellerList.add(orderSeller);
                goodsIdList.add(goodsId);
            }
        }

        // 添加数据
        orderMapper.insert(order);
        orderItemMapper.batchInsert(orderItemList);
        orderSellerMapper.batchInsert(orderSellerList);

        // 减少库存
        reduceStock(orderItemList);
        // 删除购物车中已下单的商品
        cleanCart(cartCheckedList);

        OrderVo orderVo = assembleOrderVo(order, orderItemList, shippingId);
        return ResponseData.success(orderVo);
    }

    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList, Long shippingId) {
        OrderVo orderVo = new OrderVo();

        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPostage(order.getPostage());
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setStatus(order.getStatus());
        List<OrderItemListVo> itemList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemListVo vo = assembleOrderItemListVo(orderItem);
            itemList.add(vo);
        }
        orderVo.setItemList(itemList);
        orderVo.setPaymentTypeMsg(getPaymentTypeMsg(order.getPaymentType()));
        orderVo.setShipping(shipping);
        orderVo.setStatusMsg(convertStatus(order.getStatus()));

        return orderVo;
    }

    private String getPaymentTypeMsg(Integer paymentType) {
        switch (paymentType) {
            case Const.PaymentType.ONLINE:
                return "在线支付";
        }
        return "";
    }

    private boolean checkStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            GoodsProperties properties = propertiesMapper.selectByPrimaryKey(orderItem.getPropertiesId());
            Long stock = properties.getStock() - orderItem.getQuantity();
            if (stock < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回订单状态对应的文本描述
     *
     * @param status
     * @return
     */
    private String convertStatus(Integer status) {
        switch (status) {
            case Const.OrderStatus.CANCELED:
                return "已取消";
            case Const.OrderStatus.NOT_PAY:
                return "未付款";
            case Const.OrderStatus.PAYED:
                return "已付款";
            case Const.OrderStatus.SENT:
                return "已发货";
            case Const.OrderStatus.SUCCESS:
                return "已完成";
            case Const.OrderStatus.CLOSED:
                return "已完成";
            default:
                return "未知";
        }
    }

    /**
     * 减少库存
     *
     * @param orderItemList
     */
    private void reduceStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            GoodsProperties properties = propertiesMapper.selectByPrimaryKey(orderItem.getPropertiesId());
            GoodsProperties propertiesForUpdate = new GoodsProperties();
            propertiesForUpdate.setId(properties.getId());
            propertiesForUpdate.setStock(properties.getStock() - orderItem.getQuantity());
            propertiesForUpdate.setUpdateTime(new Date());
            // todo 修改 -> where id in (...)
            propertiesMapper.updateByPrimaryKeySelective(propertiesForUpdate);
        }
    }

    /**
     * 删除购物车中已下单的商品
     *
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private OrderItemListVo assembleOrderItemListVo(OrderItem orderItem) {
        OrderItemListVo orderItemListVo = new OrderItemListVo();
        orderItemListVo.setOrderItemId(orderItem.getId());
        orderItemListVo.setMainImage(PropertiesUtil.getImageHost() + orderItem.getProductImage());
        orderItemListVo.setGoodsId(orderItem.getProductId());
        orderItemListVo.setProductName(orderItem.getProductName());
        orderItemListVo.setQuantity(orderItem.getQuantity());
        orderItemListVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemListVo.setDetail(getProperties(orderItem.getPropertiesId()));
        return orderItemListVo;
    }

    /**
     * 格式化规格 -> 蓝色 裸机 4GB+64GB
     *
     * @param propertiesId
     * @return
     */
    private String getProperties(Long propertiesId) {
        GoodsProperties properties = propertiesMapper.selectByPrimaryKey(propertiesId);
        return JsonUtil.getPropertiesString(properties.getText());
    }

    private Order assembleOrder(Long userId, Long shippingId) {
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setPaymentType(Const.PaymentType.ONLINE);
        order.setPostage(0); // 运费
        order.setShippingId(shippingId);
        order.setStatus(Const.OrderStatus.NOT_PAY); // 待付款
        order.setUserId(userId);
        return order;
    }

    private List<OrderItem> assembleOrderItem(Long userId, List<Cart> cartCheckedList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        Long propertiesId;
        Goods goods;
        GoodsProperties properties;
        for (Cart cart : cartCheckedList) {
            // cart.detail存储properties_id
            propertiesId = Long.valueOf(cart.getDetail());
            goods = goodsMapper.selectByPrimaryKey(cart.getProductId());
            properties = propertiesMapper.selectByPrimaryKey(propertiesId);
            if (goods == null || properties == null) {
                return null;
            }
            OrderItem item = new OrderItem();
            item.setProductImage(goods.getMainImage());
            item.setCurrentUnitPrice(properties.getPrice());
            item.setProductId(goods.getId());
            item.setProductName(goods.getName());
            item.setPropertiesId(propertiesId);
            item.setQuantity(Integer.parseInt(String.valueOf(cart.getQuantity())));
            item.setUserId(userId);
            item.setTotalPrice(BigDecimalUtil.mul(item.getCurrentUnitPrice().doubleValue(), item.getQuantity()));
            orderItemList.add(item);
        }
        return orderItemList;
    }

    private OrderListVo assembleOrderListVo(Order order) {
        OrderListVo orderListVo = new OrderListVo();

        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        if (CollectionUtils.isEmpty(orderItemList) || orderItemList.size() <= 0) {
            return null;
        }

        orderListVo.setOrderId(order.getId());
        orderListVo.setOrderNo(order.getOrderNo());
        orderListVo.setTotalPrice(order.getPayment());
        orderListVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));

        List<OrderItemListVo> orderItemListVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            OrderItemListVo orderItemListVo = assembleOrderItemListVo(orderItem);
            if (orderItemListVo == null) {
                return null;
            }
            orderItemListVoList.add(orderItemListVo);
        }
        orderListVo.setOrderItemList(orderItemListVoList);
        orderListVo.setCount(orderItemListVoList.size());
        orderListVo.setStatus(order.getStatus());
        orderListVo.setStatusMsg(convertStatus(order.getStatus()));

        return orderListVo;
    }

    private OrderVo assembleOrderVo(Order order) {
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        if (CollectionUtils.isEmpty(orderItemList) || orderItemList.size() <= 0) {
            return null;
        }
        return assembleOrderVo(order, orderItemList, order.getShippingId());
    }

    /**
     * 获取购物车中已经选中的商品
     *
     * @param userId
     * @return
     */
    private List<Cart> getCartCheckedList(Long userId) {
        return cartMapper.selectByUserIdAndChecked(userId, Const.CartStatus.CHECKED);
    }

    @Override
    public ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);

        List<OrderListVo> orderListVoList = Lists.newArrayList();
        for (Order order : orderList) {
            OrderListVo orderListVo = assembleOrderListVo(order);
            if (orderListVo == null) {
                return ResponseData.error("获取订单失败");
            }
            orderListVoList.add(orderListVo);
        }
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderListVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<OrderVo> detail(Long userId, Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return ResponseData.error();
        }
        OrderVo orderVo = assembleOrderVo(order);
        return ResponseData.success(orderVo);
    }

    @Override
    public ResponseData<OrderVo> detailByOrderNo(Long userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ResponseData.error();
        }
        OrderVo orderVo = assembleOrderVo(order);
        return ResponseData.success(orderVo);
    }

    @Override
    public ResponseData<PageInfo> search(Long userId, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderItem> orderItemList;
        keyword = "%" + keyword + "%";
        orderItemList = orderItemMapper.selectByKeyword(keyword);

        List<Long> orderNoList = Lists.newArrayList();
        for (OrderItem item : orderItemList) {
            if (orderNoList.indexOf(item.getOrderNo()) < 0) {
                orderNoList.add(item.getOrderNo());
            }
        }
        List<Order> orderList = orderMapper.selectByOrderNoList(orderNoList);
        List<OrderListVo> orderListVoList = Lists.newArrayList();
        for (Order order : orderList) {
            OrderListVo orderListVo = assembleOrderListVo(order);
            if (orderListVo == null) {
                return ResponseData.error("失败");
            }
            orderListVoList.add(orderListVo);
        }
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderListVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<OrderVo> create(Long userId, Long goodsId, Long propertiesId, int count, Long shippingId) {
        List<Cart> cartCheckedList = Lists.newArrayList();

        Cart cart = new Cart();
        cart.setChecked(Const.CartStatus.CHECKED);
        cart.setDetail(propertiesId.toString());
        cart.setProductId(goodsId);
        cart.setQuantity((long) count);
        cart.setUserId(userId);
        cartCheckedList.add(cart);
        // 生成订单项
        List<OrderItem> orderItemList = assembleOrderItem(userId, cartCheckedList);
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResponseData.error("生成订单失败");
        }
        // 生成订单
        Order order = assembleOrder(userId, shippingId);
        if (order == null) {
            return ResponseData.error("生成订单失败");
        }

        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem item : orderItemList) {
            item.setOrderNo(order.getOrderNo());
            totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), item.getTotalPrice().doubleValue());
        }
        order.setPayment(totalPrice);

        // 确认库存
        if (!checkStock(orderItemList)) {
            return ResponseData.error("库存不足");
        }

        // 添加数据
        orderMapper.insert(order);
        orderItemMapper.batchInsert(orderItemList);

        // 减少库存
        reduceStock(orderItemList);

        OrderVo orderVo = assembleOrderVo(order, orderItemList, shippingId);
        return ResponseData.success(orderVo);
    }

    @Override
    public ResponseData<OrderVo> confirm(Long userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ResponseData.error("订单不存在");
        }
        if (order.getStatus() < Const.OrderStatus.PAYED) {
            return ResponseData.error("请先付款");
        }
        if (order.getStatus() >= Const.OrderStatus.SUCCESS) {
            // 订单已完成
            return ResponseData.error();
        }
        Order orderForUpdate = new Order();
        orderForUpdate.setId(order.getId());
        orderForUpdate.setUpdateTime(new Date());
        orderForUpdate.setStatus(Const.OrderStatus.SUCCESS);
        orderForUpdate.setEndTime(new Date());
        int resultCount = orderMapper.updateByPrimaryKeySelective(orderForUpdate);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<OrderVo> cancel(Long userId, Long orderNo) {
        Order order = orderMapper.selectByOrderNoAndUserId(orderNo, userId);
        if (order == null) {
            return ResponseData.error("订单不存在");
        }
        if (order.getStatus() != Const.OrderStatus.NOT_PAY) {
            return ResponseData.error("无法取消");
        }
        Order orderForUpdate = new Order();
        orderForUpdate.setId(order.getId());
        orderForUpdate.setUpdateTime(new Date());
        orderForUpdate.setStatus(Const.OrderStatus.CANCELED);
        orderForUpdate.setCloseTime(new Date());
        // todo 回复库存
        int resultCount = orderMapper.updateByPrimaryKeySelective(orderForUpdate);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<PageInfo> listByStatus(Long userId, int status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserIdAndStatus(userId, status);

        List<OrderListVo> orderListVoList = Lists.newArrayList();
        for (Order order : orderList) {
            OrderListVo orderListVo = assembleOrderListVo(order);
            if (orderListVo == null) {
                return ResponseData.error();
            }
            orderListVoList.add(orderListVo);
        }
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderListVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData payed(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ResponseData.error("订单不存在");
        }
        Order orderForUpdate = new Order();
        orderForUpdate.setId(order.getId());
        orderForUpdate.setUpdateTime(new Date());
        orderForUpdate.setStatus(Const.OrderStatus.PAYED);
        orderForUpdate.setPaymentTime(new Date());
        int resultCount = orderMapper.updateByPrimaryKeySelective(orderForUpdate);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<PageInfo> listBySeller(Long sellerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderSeller> orderSellerList = orderSellerMapper.selectBySellerId(sellerId);
        List<Long> orderNoList = Lists.newArrayList();
        for (OrderSeller orderSeller : orderSellerList) {
            orderNoList.add(orderSeller.getOrderNo());
        }
        List<Order> orderList = orderMapper.selectByOrderNoList(orderNoList);
        List<OrderListVo> orderListVoList = Lists.newArrayList();
        for (Order order : orderList) {
            OrderListVo orderListVo = assembleOrderListVo(order);
            if (orderListVo == null) {
                return ResponseData.error("获取失败");
            }
            orderListVoList.add(orderListVo);
        }
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderListVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<OrderVo> send(Long sellerId, Long orderNo) {
        OrderSeller orderSeller = orderSellerMapper.selectBySellerIdAndOrderNo(sellerId, orderNo);
        if (orderSeller == null) {
            return ResponseData.error("无权操作");
        }
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return ResponseData.error("订单不存在");
        }
        if (order.getStatus() != Const.OrderStatus.PAYED) {
            return ResponseData.error("无法发货");
        }
        Order orderForUpdate = new Order();
        orderForUpdate.setId(order.getId());
        orderForUpdate.setUpdateTime(new Date());
        orderForUpdate.setStatus(Const.OrderStatus.SENT);
        orderForUpdate.setCloseTime(new Date());
        int resultCount = orderMapper.updateByPrimaryKeySelective(orderForUpdate);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    /**
     * 生成订单号
     *
     * @return
     */
    private Long generateOrderNo() {
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }
}
