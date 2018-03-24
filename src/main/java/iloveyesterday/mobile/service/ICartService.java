package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;

public interface ICartService {
    ResponseData create(Long userId, Long productId, Long quantity, String detail);

    ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize);
}
