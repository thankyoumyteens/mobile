package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.*;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.vo.OrderVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public ResponseData<OrderVo> create(Long userId, Long shippingId) {
        return null;
    }

    @Override
    public ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ResponseData<OrderVo> detail(Long userId, Long orderId) {
        return null;
    }

    @Override
    public ResponseData<OrderVo> detailByOrderNo(Long userId, Long orderNo) {
        return null;
    }
}
