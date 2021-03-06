package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAll();

    List<Goods> selectByCategoryId(Long categoryId);

    List<Goods> selectByCategoryIdAndStatus(
            @Param("categoryId") Long categoryId,
            @Param("status") int status
    );

    List<Goods> selectByKeyword(String keyword);

    List<Goods> selectByKeywordAndStatus(
            @Param("keyword") String keyword,
            @Param("status") int status
    );

    Goods selectBySellerIdAndCategoryIdAndName(
            @Param("sellerId") Long sellerId,
            @Param("categoryId") Long categoryId,
            @Param("name") String name);

    List<Goods> selectBySellerId(Long sellerId);

    List<Goods> selectBySellerIdAndCategoryId(
            @Param("sellerId") Long sellerId,
            @Param("categoryId") Long categoryId);

    List<Goods> selectByKeywordAndSellerId(
            @Param("sellerId") Long sellerId,
            @Param("keyword") String keyword);
}