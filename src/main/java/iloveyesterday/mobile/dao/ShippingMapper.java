package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> selectByUserId(Long userId);

    int deleteByPrimaryKeyAndUserId(
            @Param("shippingId") Long shippingId,
            @Param("userId") Long userId);

    int selectByUserIdAndShippingId(
            @Param("userId") Long userId,
            @Param("shippingId") Long shippingId);
}