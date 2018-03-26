package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Region;

import java.util.List;

public interface RegionMapper {
    int deleteByPrimaryKey(Double regionId);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Double regionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);

    List<Region> selectByParentId(Double parentId);
}