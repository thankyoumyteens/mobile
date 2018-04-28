package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.vo.GoodsAddVo;
import iloveyesterday.mobile.vo.GoodsDetailVo;

public interface IGoodsService {
    ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize);

    ResponseData<PageInfo> getListByKeyword(int role, String keyword, int pageNum, int pageSize);

    ResponseData<GoodsDetailVo> detail(Long goodsId, int role);

    ResponseData add(GoodsAddVo goodsVo);

    ResponseData<PageInfo> getListBySellerId(Long sellerId, int pageNum, int pageSize);

    ResponseData<PageInfo> getListByCategoryIdAndSellerId(Long sellerId, Long categoryId, int pageNum, int pageSize);

    ResponseData<PageInfo> getListByKeywordAndSellerId(Long sellerId, String keyword, int pageNum, int pageSize);

    ResponseData changeStatus(Long sellerId, Long goodsId, int status);

    ResponseData deleteProperty(Long propertiesId);
}
