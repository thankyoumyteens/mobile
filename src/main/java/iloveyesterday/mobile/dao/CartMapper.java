package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectByUserId(Long userId);

    Cart selectByUserIdANdProductId(
            @Param("userId") Long userId,
            @Param("productId") Long productId);

    Cart selectByUserIdANdProductIdAndDetail(@Param("userId") Long userId,
                                             @Param("productId") Long productId,
                                             @Param("detail") String detail);

    Cart selectByUserIdAndGoodsInfo(@Param("userId") Long userId,
                                    @Param("goodsId") Long goodsId,
                                    @Param("propertiedId") String propertiedId);
}