package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Favorite;

public interface IFavoriteService {
    ResponseData add(Favorite favorite);

    ResponseData<PageInfo> list(Long userId, int type, int pageNum, int pageSize);
}
