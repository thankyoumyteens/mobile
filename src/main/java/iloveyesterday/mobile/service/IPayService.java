package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;

public interface IPayService {
    ResponseData createForm(Long userId, Long orderNo);

    // 保存支付信息
    ResponseData save(Long userId, Long orderNo, String alipayOrderNo);

    ResponseData alipayNotify(String orderNo, String alipayOrderNo, String alipayStatus);

    ResponseData query(Long userId, Long orderNo);
}
