package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.*;
import iloveyesterday.mobile.pojo.*;
import iloveyesterday.mobile.service.IGoodsService;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.GoodsDetailVo;
import iloveyesterday.mobile.vo.GoodsListVo;
import iloveyesterday.mobile.vo.GoodsPropertiesVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPropertiesMapper goodsPropertiesMapper;

    @Resource
    private GoodsCommentMapper goodsCommentMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private GoodsDetailMapper detailMapper;

    @Override
    public ResponseData<PageInfo> getListByCategoryId(int role, Long categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList;
        switch (role) {
            case Const.Role.ADMIN:
                goodsList = goodsMapper.selectByCategoryId(categoryId);
                break;
            case Const.Role.USER:
                goodsList = goodsMapper.selectByCategoryIdAndStatus(categoryId, Const.ProductStatus.ON_SALE);
                break;
            default:
                return ResponseData.error("参数错误");
        }
        List<GoodsListVo> resultList = convertToVoList(goodsList);
        if (resultList == null) {
            return ResponseData.error("失败");
        }
        PageInfo pageResult = new PageInfo(goodsList);
        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> getListByKeyword(int role, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList;
        keyword = "%" + keyword + "%";
        switch (role) {
            case Const.Role.ADMIN:
                goodsList = goodsMapper.selectByKeyword(keyword);
                break;
            case Const.Role.USER:
                goodsList = goodsMapper.selectByKeywordAndStatus(keyword, Const.ProductStatus.ON_SALE);
                break;
            default:
                return ResponseData.error("参数错误");
        }
        List<GoodsListVo> resultList = convertToVoList(goodsList);
        if (resultList == null) {
            return ResponseData.error("失败");
        }
        PageInfo pageResult = new PageInfo(goodsList);
        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<GoodsDetailVo> detail(Long goodsId, int role) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (Const.Role.USER == role) {
            if (goods.getStatus() != Const.ProductStatus.ON_SALE) {
                return ResponseData.error("商品已下架");
            }
        }
        GoodsDetailVo detailVo = assembleGoodsDetailVo(goods);

        return ResponseData.success(detailVo);
    }

    @Override
    public ResponseData add(Goods goods) {
        int resultCount;
        // 默认下架, 上架编辑完成后手动上架
        goods.setStatus(Const.ProductStatus.NOT_ON_SALE);
        resultCount = goodsMapper.insert(goods);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<PageInfo> getListBySellerId(Long sellerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList = goodsMapper.selectBySellerId(sellerId);
        List<GoodsListVo> resultList = convertToVoList(goodsList);
        if (resultList == null) {
            return ResponseData.error("失败");
        }
        PageInfo pageResult = new PageInfo(goodsList);
        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> getListByCategoryIdAndSellerId(Long sellerId, Long categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList = goodsMapper.selectBySellerIdAndCategoryId(sellerId, categoryId);
        List<GoodsListVo> resultList = convertToVoList(goodsList);
        if (resultList == null) {
            return ResponseData.error("失败");
        }
        PageInfo pageResult = new PageInfo(goodsList);
        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData<PageInfo> getListByKeywordAndSellerId(Long sellerId, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        keyword = "%" + keyword + "%";
        List<Goods> goodsList = goodsMapper.selectByKeywordAndSellerId(sellerId, keyword);
        List<GoodsListVo> resultList = convertToVoList(goodsList);
        if (resultList == null) {
            return ResponseData.error("失败");
        }
        PageInfo pageResult = new PageInfo(goodsList);
        pageResult.setList(resultList);
        return ResponseData.success(pageResult);
    }

    @Override
    public ResponseData changeStatus(Long sellerId, Long goodsId, int status) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null) {
            return ResponseData.error("商品不存在");
        }
        if (!sellerId.equals(goods.getSellerId())) {
            return ResponseData.error("无权执行此操作");
        }
        if (goods.getStatus() == status) {
            return ResponseData.success();
        }
        List<GoodsProperties> propertiesList = goodsPropertiesMapper.selectByGoodsId(goodsId);
        if (CollectionUtils.isEmpty(propertiesList)) {
            // 规格为空的商品不能上架
            return ResponseData.error("请先完善商品细节");
        }
        Goods goodsForUpdate = new Goods();
        goodsForUpdate.setId(goodsId);
        goodsForUpdate.setStatus(status);
        goodsForUpdate.setUpdateTime(new Date());
        int resultCount = goodsMapper.updateByPrimaryKeySelective(goodsForUpdate);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData deleteProperty(Long propertiesId) {
        GoodsProperties properties = goodsPropertiesMapper.selectByPrimaryKey(propertiesId);
        if (properties == null) {
            return ResponseData.success();
        }
        Long goodsId = properties.getGoodsId();
        int resultCount = goodsPropertiesMapper.deleteByPrimaryKey(propertiesId);
        if (resultCount > 0) {
            List<GoodsProperties> propertiesList = goodsPropertiesMapper.selectByGoodsId(goodsId);
            if (CollectionUtils.isEmpty(propertiesList)) {
                // 下架规格为空的商品
                Goods goods = new Goods();
                goods.setId(goodsId);
                goods.setStatus(Const.ProductStatus.NOT_ON_SALE);
                resultCount = goodsMapper.updateByPrimaryKeySelective(goods);
            }
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData addOrUpdateProperties(GoodsProperties properties) {
        int resultCount;
        if (properties.getId() != null && properties.getId() > 0) {
            // update
            properties.setUpdateTime(new Date());
            resultCount = goodsPropertiesMapper.updateByPrimaryKeySelective(properties);
        } else {
            // insert
            properties.setId(null);
            properties.setName("手机");
            properties.setStatus(Const.ProductStatus.ON_SALE);
            resultCount = goodsPropertiesMapper.insert(properties);
        }
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<GoodsDetail> getDetail(Long goodsId) {
        GoodsDetail detail = detailMapper.selectByGoodsId(goodsId);
        return ResponseData.success(detail);
    }

    @Override
    public ResponseData<GoodsDetail> updateDetail(GoodsDetail detail) {
        int resultCount;
        ResponseData responseData = getDetail(detail.getGoodsId());
        GoodsDetail goodsDetail = (GoodsDetail) responseData.getData();
        if (goodsDetail != null) {
            detail.setId(goodsDetail.getId());
            detail.setUpdateTime(new Date());
            resultCount = detailMapper.updateByPrimaryKeySelective(detail);
        } else {
            resultCount = detailMapper.insert(detail);
        }
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    private GoodsDetailVo assembleGoodsDetailVo(Goods goods) {
        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setCategoryId(goods.getCategoryId());
        detailVo.setGoodsId(goods.getId());
        detailVo.setMainImage(PropertiesUtil.getImageHost() + goods.getMainImage());
        detailVo.setName(goods.getName());
        detailVo.setStatus(goods.getStatus());
        detailVo.setStatusText(convertStatus(goods.getStatus()));
        detailVo.setSubImages(getSubImagesWithUrl(goods.getSubImages()));
        detailVo.setSubtitle(goods.getSubtitle());
        List<GoodsProperties> propertiesList = goodsPropertiesMapper.selectByGoodsId(goods.getId());
        if (CollectionUtils.isEmpty(propertiesList)) {
            if (goods.getStatus() != Const.ProductStatus.ON_SALE) {
                return detailVo;
            }
            return null;
        }
        List<GoodsPropertiesVo> propertiesVoList = convertToPropertiesVoList(propertiesList);
        detailVo.setPropertiesList(propertiesVoList);
        return detailVo;
    }

    private List<GoodsPropertiesVo> convertToPropertiesVoList(List<GoodsProperties> propertiesList) {
        List<GoodsPropertiesVo> resultList = Lists.newArrayList();
        for (GoodsProperties properties : propertiesList) {
            GoodsPropertiesVo propertiesVo = assembleGoodsPropertiesVo(properties);
            if (propertiesVo == null) {
                continue;
            }
            resultList.add(propertiesVo);
        }
        return resultList;
    }

    private GoodsPropertiesVo assembleGoodsPropertiesVo(GoodsProperties properties) {
        GoodsPropertiesVo propertiesVo = new GoodsPropertiesVo();
        propertiesVo.setMainImage(PropertiesUtil.getImageHost() + properties.getMainImage());
        propertiesVo.setName(properties.getName());
        propertiesVo.setPrice(properties.getPrice());
        propertiesVo.setPropertiesId(properties.getId());
        // 不现实已删除的商品
        if (Const.ProductStatus.DELETE == properties.getStatus()) {
            return null;
        }
        propertiesVo.setStatus(properties.getStatus());
        propertiesVo.setStatusText(convertStatus(properties.getStatus()));
        propertiesVo.setStock(properties.getStock());
        propertiesVo.setText(properties.getText());
        return propertiesVo;
    }

    private String getSubImagesWithUrl(String subImages) {
        StringBuilder sb = new StringBuilder();
        String[] imgUrlList = subImages.split(",");
        for (String img : imgUrlList) {
            sb.append(PropertiesUtil.getImageHost()).append(img).append(",");
        }
        sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        return sb.toString();
    }

    private String convertStatus(Integer status) {
        switch (status) {
            case Const.ProductStatus.ON_SALE:
                return "在售";
            case Const.ProductStatus.NOT_ON_SALE:
                return "已下架";
            case Const.ProductStatus.DELETE:
                return "商品不存在";
            default:
                return "商品不存在";
        }
    }

    private List<GoodsListVo> convertToVoList(List<Goods> goodsList) {
        List<GoodsListVo> resultList = Lists.newArrayList();
        for (Goods goods : goodsList) {
            GoodsListVo goodsListVo = assembleGoodsListVo(goods);
            if (goodsListVo == null) {
                return null;
            }
            resultList.add(goodsListVo);
        }
        return resultList;
    }

    private GoodsListVo assembleGoodsListVo(Goods goods) {
        GoodsListVo goodsListVo = new GoodsListVo();
        goodsListVo.setGoodsId(goods.getId());
        goodsListVo.setCategoryId(goods.getCategoryId());
        goodsListVo.setCategoryName(getCategoryName(goods.getCategoryId()));
        goodsListVo.setName(goods.getName());
        goodsListVo.setStatus(goods.getStatus());
        goodsListVo.setStatusMsg(getStatusMsg(goods.getStatus()));
        goodsListVo.setMainImage(PropertiesUtil.getImageHost() + goods.getMainImage());
        goodsListVo.setSubtitle(goods.getSubtitle());
        BigDecimal price = goodsPropertiesMapper.selectMinimumPrice(goods.getId());
        if (price == null) {
            if (goods.getStatus() != Const.ProductStatus.ON_SALE) {
                return goodsListVo;
            }
            return null;
        }
        goodsListVo.setPrice(price.toString());
        List<GoodsComment> commentList = goodsCommentMapper.selectByGoodsId(goods.getId());
        goodsListVo.setCommentCount((long) commentList.size());

        // 筛选好评
        int count = 0;
        for (GoodsComment comment : commentList) {
            if (comment.getStar() > 5 || comment.getStar() < 4) {
                continue;
            }
            count++;
        }
        double rate = (count * 1.0 / commentList.size()) * 100;
        // 保留一位小数, 如果要其它位,如4位,这里两个10改成10000
        goodsListVo.setCommentStatus(((double) (Math.round(rate * 10)) / 10) + "%");
        return goodsListVo;
    }

    private String getStatusMsg(Integer status) {
        switch (status) {
            case Const.ProductStatus.ON_SALE:
                return "在售";
            case Const.ProductStatus.NOT_ON_SALE:
                return "下架";
            case Const.ProductStatus.DELETE:
                return "删除";
            default:
                return "";
        }
    }

    private String getCategoryName(Long categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            return null;
        }
        return category.getName();
    }
}
