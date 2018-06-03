package iloveyesterday.mobile.service.impl;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.ResponseData;
import iloveyesterday.mobile.dao.FavoriteMapper;
import iloveyesterday.mobile.pojo.Favorite;
import iloveyesterday.mobile.service.IFavoriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("favoriteService")
public class FavoriteServiceImpl implements IFavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

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
}
