package iloveyesterday.mobile.service.impl;

import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.CartMapper;
import iloveyesterday.mobile.dao.OrderItemMapper;
import iloveyesterday.mobile.dao.OrderMapper;
import iloveyesterday.mobile.dao.ProductMapper;
import iloveyesterday.mobile.pojo.Cart;
import iloveyesterday.mobile.pojo.Order;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.util.BigDecimalUtil;
import iloveyesterday.mobile.vo.OrderVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
        // todo 创建orderItem
        // todo 减少库存
        // todo 删除购物车中已下单的商品

        return null; // todo
    }

    private Order assembleOrder(Long userId, Long shippingId, List<Cart> cartList) {
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setPaymentType(Const.PaymentType.ONLINE);
        order.setStatus(Const.OrderStatus.NOT_PAY);
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
