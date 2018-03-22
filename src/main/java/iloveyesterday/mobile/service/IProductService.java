package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Product;

public interface IProductService {
    ResponseData<PageInfo> getAll(int pageNum, int pageSize);

    ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize);

    ResponseData add(Product product);
}
