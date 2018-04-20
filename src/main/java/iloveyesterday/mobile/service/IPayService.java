package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;

public interface IPayService {
    ResponseData createForm(Long userId, Long orderNo);

    ResponseData save(Long userId, Long orderNo, String alipayOrderNo);
}
