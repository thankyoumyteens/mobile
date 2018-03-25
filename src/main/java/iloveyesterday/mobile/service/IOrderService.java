package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.vo.OrderVo;

public interface IOrderService {
    ResponseData<OrderVo> create(Long userId, Long shippingId);
}
