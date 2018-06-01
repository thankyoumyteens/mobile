package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Favorite;
import org.apache.ibatis.annotations.Param;

public interface FavoriteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Favorite record);

    int insertSelective(Favorite record);

    Favorite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Favorite record);

    int updateByPrimaryKey(Favorite record);

    int selectCountByGoodsId(
            @Param("userId") Long userId, @Param("goodsId") Long goodsId);

    int selectCountBySellerId(
            @Param("userId") Long userId, @Param("sellerId") Long sellerId);
}