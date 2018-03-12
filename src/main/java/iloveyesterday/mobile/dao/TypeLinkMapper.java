package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.TypeLink;

public interface TypeLinkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TypeLink record);

    int insertSelective(TypeLink record);

    TypeLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TypeLink record);

    int updateByPrimaryKey(TypeLink record);
}