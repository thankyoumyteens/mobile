package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.GoodsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsComment record);

    int insertSelective(GoodsComment record);

    GoodsComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsComment record);

    int updateByPrimaryKey(GoodsComment record);

    List<GoodsComment> selectByGoodsId(Long goodsId);

    List<GoodsComment> selectByUserId(Long userId);

    List<GoodsComment> selectByGoodsIdAndStar(
            @Param("goodsId") Long goodsId,
            @Param("starLeft") int starLeft,
            @Param("starRight") int starRight);
}