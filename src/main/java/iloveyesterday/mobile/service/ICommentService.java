package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.GoodsComment;

import java.util.List;

public interface ICommentService {
    ResponseData create(GoodsComment comment);

    ResponseData createByList(List<GoodsComment> commentList);

    ResponseData<PageInfo> list(Long goodsId, int pageNum, int pageSize);

    ResponseData<PageInfo> getUserComments(Long userId, int pageNum, int pageSize);

    ResponseData<PageInfo> listByLevel(Long goodsId, int level, int pageNum, int pageSize);

    ResponseData<PageInfo> listWithImages(Long goodsId, int pageNum, int pageSize);

    ResponseData<PageInfo> listWithText(Long goodsId, int pageNum, int pageSize);
}
