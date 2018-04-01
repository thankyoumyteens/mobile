package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUserId(Long userId);

    Order selectByOrderNo(Long orderNo);
}