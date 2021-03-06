package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.GoodsProperties;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsPropertiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsProperties record);

    int insertSelective(GoodsProperties record);

    GoodsProperties selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsProperties record);

    int updateByPrimaryKey(GoodsProperties record);

    BigDecimal selectMinimumPrice(Long goodsId);

    List<GoodsProperties> selectByGoodsId(Long goodsId);
}