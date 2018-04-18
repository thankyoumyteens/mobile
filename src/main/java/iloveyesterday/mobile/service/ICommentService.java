package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.GoodsComment;

public interface ICommentService {
    ResponseData create(GoodsComment comment);

    ResponseData<PageInfo> list(Long goodsId, int pageNum, int pageSize);
}
