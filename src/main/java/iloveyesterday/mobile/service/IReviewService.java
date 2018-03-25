package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Review;

public interface IReviewService {
    ResponseData create(Review review);

    ResponseData<PageInfo> list(Long productId, int pageNum, int pageSize);
}
