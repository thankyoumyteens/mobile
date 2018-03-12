package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKeyWithBLOBs(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}