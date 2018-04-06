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
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.OrderItemListVo;
import iloveyesterday.mobile.vo.OrderListVo;
import iloveyesterday.mobile.vo.OrderVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ShippingMapper shippingMapper;


    @Override
    public ResponseData<OrderVo> create(Long userId, Long shippingId) {
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        List<Cart> cartCheckedList = Lists.newArrayList();
        for (Cart cart : cartList) {
            if (cart.getChecked().equals(Const.CartStatus.CHECKED)) {
                cartCheckedList.add(cart);
            }
        }
        if (CollectionUtils.isEmpty(cartCheckedList)) {
            ResponseData.error("请选择商品");
        }
        Order order = assembleOrder(userId, shippingId, cartCheckedList);
        if (order == null) {
            return ResponseData.error();
        }
        List<OrderItem> orderItemList = assembleOrderItem(userId, order, cartCheckedList);
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResponseData.error();
        }

        orderMapper.insert(order);
        orderItemMapper.batchInsert(orderItemList);

        // 减少库存
        reduceStock(orderItemList);
        // 删除购物车中已下单的商品
        cleanCart(cartCheckedList);

        OrderVo orderVo = assembleOrderVo(order, orderItemList, shippingId);
        return ResponseData.success(orderVo);
    }

    @Override
    public ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);

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

    private OrderVo assembleOrderVo(Order order) {
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        if (CollectionUtils.isEmpty(orderItemList)) {
            return null;
        }
        return assembleOrderVo(order, orderItemList, order.getShippingId());
    }

    private OrderListVo assembleOrderListVo(Order order) {
        OrderListVo orderListVo = new OrderListVo();

        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        if (CollectionUtils.isEmpty(orderItemList)) {
            return null;
        }

        orderListVo.setOrderId(order.getId());
        orderListVo.setOrderNo(order.getOrderNo());
        orderListVo.setTotalPrice(order.getPayment());
        orderListVo.setCreateTime(order.getCreateTime());

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
                return "已关闭";
            default:
                return "未知";
        }
    }

    private OrderItemListVo assembleOrderItemListVo(OrderItem orderItem) {
        OrderItemListVo orderItemListVo = new OrderItemListVo();
        orderItemListVo.setOrderItemId(orderItem.getId());
        orderItemListVo.setDetail(orderItem.getTypeNames());
        orderItemListVo.setMainImage(PropertiesUtil.getImageHost() + orderItem.getProductImage());
        orderItemListVo.setProductName(orderItem.getProductName());
        orderItemListVo.setQuantity(orderItem.getQuantity());
        orderItemListVo.setTotalPrice(orderItem.getTotalPrice());
        return orderItemListVo;
    }

    /**
     * 删除购物车中已购买的商品
     *
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private void reduceStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList, Long shippingId) {
        OrderVo orderVo = new OrderVo();

        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);

        orderVo.setCloseTime(order.getCloseTime());
        orderVo.setCreateTime(order.getCreateTime());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentTime(order.getPaymentTime());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPostage(order.getPostage());
        orderVo.setSendTime(order.getSendTime());
        orderVo.setStatus(order.getStatus());
        for (OrderItem orderItem : orderItemList) {
            orderItem.setProductImage(PropertiesUtil.getImageHost() + orderItem.getProductImage());
        }
        orderVo.setOrderItemList(orderItemList);
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

    private List<OrderItem> assembleOrderItem(Long userId, Order order, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (Cart cart : cartList) {
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if (product == null) {
                return null;
            }
            BigDecimal unitPrice = product.getPrice();
            // todo 加上选择的参数(detail)价格
            OrderItem orderItem = new OrderItem();
            orderItem.setCurrentUnitPrice(unitPrice);
            orderItem.setOrderNo(order.getOrderNo());
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(cart.getQuantity().intValue());
            orderItem.setTotalPrice(BigDecimalUtil.mul(cart.getQuantity(), unitPrice.doubleValue()));
            orderItem.setTypeNames(cart.getDetail());
            orderItem.setUserId(userId);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private Order assembleOrder(Long userId, Long shippingId, List<Cart> cartList) {
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setPaymentType(Const.PaymentType.ONLINE);
        order.setStatus(Const.OrderStatus.NOT_PAY);
        order.setCreateTime(new Date());
        // 计算总价
        BigDecimal totalPrice = new BigDecimal("0");
        for (Cart cart : cartList) {
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if (product == null) {
                return null;
            }
            BigDecimal unitPrice = product.getPrice();
            // todo 加上选择的参数(detail)价格
            totalPrice = BigDecimalUtil.add(
                    totalPrice.doubleValue(),
                    BigDecimalUtil.mul(cart.getQuantity(), unitPrice.doubleValue()).doubleValue()
            );
        }
        order.setPostage(0); // todo 运费为0
        order.setPayment(BigDecimalUtil.add(totalPrice.doubleValue(), order.getPostage()));
        return order;
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
