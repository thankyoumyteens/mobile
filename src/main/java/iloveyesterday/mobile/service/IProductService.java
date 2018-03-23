package iloveyesterday.mobile.service;

import com.github.pagehelper.PageInfo;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.vo.ProductVo;

public interface IProductService {
    ResponseData<PageInfo> getAll(int pageNum, int pageSize);

    ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize);

    ResponseData add(Product product);

    ResponseData<ProductVo> detail(Long productId, int role);
}
