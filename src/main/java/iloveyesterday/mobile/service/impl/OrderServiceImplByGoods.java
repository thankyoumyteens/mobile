package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.vo.OrderVo;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImplByGoods implements IOrderService {
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
