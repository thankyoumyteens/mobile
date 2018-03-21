package iloveyesterday.mobile.service;


import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ResponseData addCategory(String categoryName, Long parentId);

    ResponseData updateCategoryName(Long categoryId, String categoryName);

    ResponseData<List<Category>> getChildrenParallelCategory(Long categoryId);

    ResponseData<List<Category>> selectCategoryAndChildrenById(Long categoryId);

}
