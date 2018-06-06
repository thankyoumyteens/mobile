package iloveyesterday.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.FavoriteMapper;
import iloveyesterday.mobile.dao.GoodsMapper;
import iloveyesterday.mobile.pojo.Favorite;
import iloveyesterday.mobile.pojo.Goods;
import iloveyesterday.mobile.service.IFavoriteService;
import iloveyesterday.mobile.util.DateTimeUtil;
import iloveyesterday.mobile.vo.FavoriteVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("favoriteService")
public class FavoriteServiceImpl implements IFavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public ResponseData add(Favorite favorite) {
        int resultCount;
        Integer type = favorite.getType();
        if (type == null) {
            return ResponseData.error("参数错误");
        }
        switch (type) {
            case Const.FavoriteType.GOODS:
                resultCount = favoriteMapper.selectCountByGoodsId(
                        favorite.getUserId(), favorite.getGoodsId());
                if (resultCount > 0) {
                    return ResponseData.error("商品已收藏");
                }
                break;
            case Const.FavoriteType.SHOP:
                resultCount = favoriteMapper.selectCountBySellerId(
                        favorite.getUserId(), favorite.getSellerId());
                if (resultCount > 0) {
                    return ResponseData.error("店铺已收藏");
                }
                break;
        }
        resultCount = favoriteMapper.insert(favorite);
        if (resultCount > 0) {
            return ResponseData.success();
        }
        return ResponseData.error();
    }

    @Override
    public ResponseData<PageInfo> list(Long userId, int type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Favorite> favoriteList = favoriteMapper.selectByUserIdAndType(userId, type);
        if (favoriteList == null) {
            return ResponseData.error();
        }
        List<FavoriteVo> list = Lists.newArrayList();
        for (Favorite item : favoriteList) {
            FavoriteVo vo = createFavoriteVo(item);
            if (vo == null) {
                return ResponseData.error();
            }
            list.add(vo);
        }

        PageInfo pageInfo = new PageInfo(favoriteList);
        pageInfo.setList(list);
        return ResponseData.success(pageInfo);
    }

    @Override
    public ResponseData count(Long userId, int type) {
        int count = favoriteMapper.selectCountByUserIdAndType(userId, type);
        return ResponseData.success(count);
    }

    private FavoriteVo createFavoriteVo(Favorite item) {
        FavoriteVo vo = new FavoriteVo();

        vo.setId(item.getId());
        vo.setUserId(item.getUserId());
        vo.setType(item.getType());
        Long goodsId = item.getGoodsId();
        if (goodsId != null) {
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            if (goods == null) {
                return null;
            }
            vo.setGoods(goods);
        }
        // todo shop
        vo.setCreateTime(DateTimeUtil.dateToStr(item.getCreateTime()));
        return vo;
    }
}
