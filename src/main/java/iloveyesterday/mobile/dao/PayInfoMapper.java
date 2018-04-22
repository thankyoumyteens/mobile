package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

    PayInfo selectByAlipayOrderNo(String alipayOrderNo);

    PayInfo selectByOrderNoAndUserId(
            @Param("orderNo") Long orderNo,
            @Param("userId") Long userId);
}