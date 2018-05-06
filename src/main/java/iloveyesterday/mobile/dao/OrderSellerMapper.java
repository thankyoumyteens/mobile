package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.OrderSeller;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSellerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderSeller record);

    int insertSelective(OrderSeller record);

    OrderSeller selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderSeller record);

    int updateByPrimaryKey(OrderSeller record);

    void batchInsert(@Param("orderSellerList") List<OrderSeller> orderSellerList);

    List<OrderSeller> selectBySellerId(Long sellerId);

    List<OrderSeller> selectBySellerIdAndOrderNo(
            @Param("sellerId") Long sellerId,
            @Param("orderNo") Long orderNo);
}