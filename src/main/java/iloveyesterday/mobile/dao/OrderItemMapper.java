package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKeyWithBLOBs(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);
}