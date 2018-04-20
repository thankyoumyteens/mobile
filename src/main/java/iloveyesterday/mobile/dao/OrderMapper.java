package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Order;
import org.apache.ibatis.annotations.Param;

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

    List<Order> selectByOrderNoList(@Param("orderNoList") List<Long> orderNoList);

    Order selectByPrimaryKeyAndUserId(
            @Param("orderId") Long orderId,
            @Param("userId") Long userId);

    Order selectByOrderNoAndUserId(
            @Param("orderNo") Long orderNo,
            @Param("userId") Long userId);

    List<Order> selectByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") int status);
}