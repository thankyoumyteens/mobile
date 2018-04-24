package iloveyesterday.mobile.service;

import iloveyesterday.mobile.common.ResponseData;

public interface IMessageService {
    ResponseData sendDeliveryMessage(Long userId, Long orderNo);
}
