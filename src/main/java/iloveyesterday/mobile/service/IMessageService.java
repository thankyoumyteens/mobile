package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;

public interface IMessageService {
    ResponseData sendDeliveryMessage(Long userId, Long orderNo);

    ResponseData<PageInfo> getMessageList(Long userId, int pageNum, int pageSize);
}
