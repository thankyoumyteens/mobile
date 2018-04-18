package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.vo.OrderVo;

public interface IOrderService {
    ResponseData<OrderVo> create(Long userId, Long shippingId);

    ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize);

    ResponseData<OrderVo> detail(Long userId, Long orderId);

    ResponseData<OrderVo> detailByOrderNo(Long userId, Long orderNo);

    ResponseData<PageInfo> search(Long userId, String keyword, int pageNum, int pageSize);
}
