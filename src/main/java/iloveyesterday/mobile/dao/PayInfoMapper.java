package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.PayInfo;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}