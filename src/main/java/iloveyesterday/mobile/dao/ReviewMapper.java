package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Review;

import java.util.List;

public interface ReviewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Review record);

    int insertSelective(Review record);

    Review selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);

    List<Review> selectByProductId(Long productId);
}