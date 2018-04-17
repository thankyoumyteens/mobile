package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.GoodsCommentMapper;
import iloveyesterday.mobile.dao.GoodsMapper;
import iloveyesterday.mobile.dao.GoodsPropertiesMapper;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.pojo.GoodsProperties;
import iloveyesterday.mobile.service.IGoodsService;
import iloveyesterday.mobile.util.PropertiesUtil;
import iloveyesterday.mobile.vo.GoodsDetailVo;
import iloveyesterday.mobile.vo.GoodsListVo;
import iloveyesterday.mobile.vo.GoodsPropertiesVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsPropertiesMapper goodsPropertiesMapper;

    @Resource
    private GoodsCommentMapper goodsCommentMapper;

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

    private GoodsDetailVo assembleGoodsDetailVo(Goods goods) {
        GoodsDetailVo detailVo = new GoodsDetailVo();
        List<GoodsProperties> propertiesList = goodsPropertiesMapper.selectByGoodsId(goods.getId());
        if (CollectionUtils.isEmpty(propertiesList)) {
            return null;
        }
        detailVo.setCategoryId(goods.getCategoryId());
        detailVo.setGoodsId(goods.getId());
        detailVo.setMainImage(PropertiesUtil.getImageHost() + goods.getMainImage());
        detailVo.setName(goods.getName());
        detailVo.setStatus(goods.getStatus());
        detailVo.setStatusText(convertStatus(goods.getStatus()));
        detailVo.setSubImages(getSubImagesWithUrl(goods.getSubImages()));
        detailVo.setSubtitle(goods.getSubtitle());
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
        goodsListVo.setName(goods.getName());
        goodsListVo.setMainImage(PropertiesUtil.getImageHost() + goods.getMainImage());
        goodsListVo.setSubtitle(goods.getSubtitle());
        BigDecimal price = goodsPropertiesMapper.selectMinimumPrice(goods.getId());
        if (price == null) {
            return null;
        }
        goodsListVo.setPrice(price.toString());
        return goodsListVo;
    }
}
