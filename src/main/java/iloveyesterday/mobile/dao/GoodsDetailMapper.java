package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.GoodsDetail;

public interface GoodsDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsDetail record);

    GoodsDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsDetail record);
}