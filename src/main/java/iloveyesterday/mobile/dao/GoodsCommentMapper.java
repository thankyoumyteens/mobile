package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.GoodsComment;

public interface GoodsCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsComment record);

    int insertSelective(GoodsComment record);

    GoodsComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsComment record);

    int updateByPrimaryKey(GoodsComment record);
}