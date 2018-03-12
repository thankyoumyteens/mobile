package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Shipping;

public interface ShippingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}