package iloveyesterday.mobile.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.CategoryMapper;
import iloveyesterday.mobile.pojo.Category;
import iloveyesterday.mobile.service.ICategoryService;
import iloveyesterday.mobile.util.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Resource
    private CategoryMapper categoryMapper;

    public ResponseData addCategory(String categoryName, Long parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ResponseData.error("参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ResponseData.successMessage("添加成功");
        }
        return ResponseData.error("添加失败");
    }

    public ResponseData updateCategoryName(Long categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ResponseData.error("参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ResponseData.successMessage("更新成功");
        }
        return ResponseData.error("更新失败");
    }

    public ResponseData<List<Category>> getChildrenParallelCategory(Long categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        for (Category category : categoryList) {
            category.setImg(PropertiesUtil.getImageHost() + category.getImg());
        }
        return ResponseData.success(categoryList);
    }


    /**
     * 递归查询本节点的id及孩子节点的id
     */
    public ResponseData<List<Category>> selectCategoryAndChildrenById(Long categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Category> categoryList = Lists.newArrayList();
        if (categoryId != null) {
            categoryList.addAll(categorySet);
        }
        return ResponseData.success(categoryList);
    }


    //递归算法,算出子节点
    private void findChildCategory(Set<Category> categorySet, Long categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点, 无子节点时退出
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
    }


}
