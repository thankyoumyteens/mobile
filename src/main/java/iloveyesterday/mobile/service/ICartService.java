package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;

public interface ICartService {
    ResponseData create(Long userId, Long productId, Long quantity, String detail);

    ResponseData<PageInfo> list(Long userId, int pageNum, int pageSize);

    ResponseData check(Long userId, Long cartId);

    ResponseData checkAll(Long userId);

    ResponseData add(Long userId, Long cartId);

    ResponseData sub(Long userId, Long cartId);

    ResponseData createByGoods(Long userId, Long goodsId, Long propertiesId, Long quantity);
}
