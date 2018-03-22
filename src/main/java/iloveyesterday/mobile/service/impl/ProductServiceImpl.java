package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.CategoryMapper;
import iloveyesterday.mobile.dao.ProductMapper;
import iloveyesterday.mobile.pojo.Category;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.service.IProductService;
import iloveyesterday.mobile.vo.ProductListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResponseData<PageInfo> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectAll();

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = null;

        switch (role) {
            case Const.Role.ADMIN:
                productList = productMapper.selectByCategoryId(categoryId);
                break;
            case Const.Role.USER:
                productList = productMapper.selectByCategoryIdAndStatus(categoryId, Const.ProductStatus.ON_SALE);
                break;
        }

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData add(Product product) {
        product.setStatus(Const.ProductStatus.ON_SALE);
        int resultCount = productMapper.insert(product);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            return null;
        }
        productListVo.setId(product.getId());
        productListVo.setCategory(category);
        productListVo.setName(product.getName());
        productListVo.setCreateTime(product.getCreateTime());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setStock(product.getStock());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setUpdateTime(product.getUpdateTime());

        return productListVo;
    }
}
