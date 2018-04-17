package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.vo.GoodsDetailVo;

public interface IGoodsService {
    ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize);

    ResponseData<PageInfo> getListByKeyword(int role, String keyword, int pageNum, int pageSize);

    ResponseData<GoodsDetailVo> detail(Long goodsId, int role);
}
