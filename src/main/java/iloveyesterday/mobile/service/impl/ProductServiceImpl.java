package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.*;
import iloveyesterday.mobile.pojo.Category;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.pojo.Product;
import iloveyesterday.mobile.service.IProductService;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.GoodsDetailVo;
import iloveyesterday.mobile.vo.GoodsListVo;
import iloveyesterday.mobile.vo.ProductListVo;
import iloveyesterday.mobile.vo.ProductVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPropertiesMapper goodsPropertiesMapper;

    @Resource
    private GoodsCommentMapper goodsCommentMapper;

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

//    public ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Product> productList = null;
//
//        switch (role) {
//            case Const.Role.ADMIN:
//                productList = productMapper.selectByCategoryId(categoryId);
//                break;
//            case Const.Role.USER:
//                productList = productMapper.selectByCategoryIdAndStatus(categoryId, Const.ProductStatus.ON_SALE);
//                break;
//        }
//
//        List<ProductListVo> productListVoList = Lists.newArrayList();
//        for (Product productItem : productList) {
//            ProductListVo productListVo = assembleProductListVo(productItem);
//            productListVoList.add(productListVo);
//        }
//        PageInfo pageResult = new PageInfo(productList);
//        pageResult.setList(productListVoList);
//        return ResponseData.success(pageResult);
//    }

    public ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList = null;
        switch (role) {
            case Const.Role.ADMIN:
                goodsList = goodsMapper.selectByCategoryId(categoryId);
                break;
            case Const.Role.USER:
                goodsList = goodsMapper.selectByCategoryIdAndStatus(categoryId, Const.ProductStatus.ON_SALE);
                break;
        }
        List<GoodsListVo> resultList = Lists.newArrayList();
        for (Goods goods : goodsList) {
            GoodsListVo goodsListVo = assembleGoodsListVo(goods);
            if (goodsListVo == null) {
                return ResponseData.error("数据错误");
            }
            resultList.add(goodsListVo);
        }
        PageInfo pageResult = new PageInfo(goodsList);
        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

    private GoodsListVo assembleGoodsListVo(Goods goods) {
        GoodsListVo goodsListVo = new GoodsListVo();
        goodsListVo.setGoodsId(goods.getId());
        goodsListVo.setCategoryId(goods.getCategoryId());
        goodsListVo.setName(goods.getName());
        goodsListVo.setMainImage(goods.getMainImage());
        goodsListVo.setSubtitle(goods.getSubtitle());
        BigDecimal price = goodsPropertiesMapper.selectMinimumPrice(goods.getId());
        if (price == null) {
            return null;
        }
        goodsListVo.setPrice(price.toString());
        return goodsListVo;
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

//    public ResponseData<ProductVo> detail(Long productId, int role) {
//        Product product = productMapper.selectByPrimaryKey(productId);
//        if (Const.Role.USER == role) {
//            if (product.getStatus() != Const.ProductStatus.ON_SALE) {
//                return ResponseData.error("商品已下架");
//            }
//        }
//        ProductVo productVo = new ProductVo();
//        productVo.setId(product.getId());
//        productVo.setDetail(product.getDetail());
//        productVo.setName(product.getName());
//        productVo.setPrice(product.getPrice());
//        productVo.setStock(product.getStock());
//        productVo.setMainImage(PropertiesUtil.getImageHost() + product.getMainImage());
//        StringBuilder subImages = new StringBuilder();
//        String[] imgUrlList = product.getSubImages().split(",");
//        for (String img : imgUrlList) {
//            subImages.append(PropertiesUtil.getImageHost()).append(img).append(",");
//        }
//        subImages = new StringBuilder(subImages.substring(0, subImages.lastIndexOf(",")));
//        productVo.setSubImages(subImages.toString());
//        productVo.setSubtitle(product.getSubtitle());
//
//        return ResponseData.success(productVo);
//    }

    public ResponseData<ProductVo> detail(Long productId, int role) {
        Goods goods = goodsMapper.selectByPrimaryKey(productId);
        if (Const.Role.USER == role) {
            if (goods.getStatus() != Const.ProductStatus.ON_SALE) {
                return ResponseData.error("商品已下架");
            }
        }
        // todo goodsservice
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        for (String img : imgUrlList) {
            subImages.append(PropertiesUtil.getImageHost()).append(img).append(",");
        }
        subImages = new StringBuilder(subImages.substring(0, subImages.lastIndexOf(",")));
        productVo.setSubImages(subImages.toString());
        productVo.setSubtitle(product.getSubtitle());

        return ResponseData.success(goodsDetailVo);
    }

    @Override
    public ResponseData<PageInfo> getListByKeyword(int role, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = null;
        keyword = "%" + keyword + "%";

        switch (role) {
            case Const.Role.ADMIN:
                productList = productMapper.selectByKeyword(keyword);
                break;
            case Const.Role.USER:
                productList = productMapper.selectByKeywordAndStatus(keyword, Const.ProductStatus.ON_SALE);
                break;
        }
        // todo 每种参数对应不同的库存
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ResponseData.success(pageResult);
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
        productListVo.setMainImage(PropertiesUtil.getImageHost() + product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setStock(product.getStock());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setUpdateTime(product.getUpdateTime());

        return productListVo;
    }
}
