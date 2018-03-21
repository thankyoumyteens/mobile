package iloveyesterday.mobile.dao;

import iloveyesterday.mobile.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);


    List<Product> selectAll();

    List<Product> selectByCategoryId(Long categoryId);

    List<Product> selectByCategoryIdAndStatus(
            @Param("categoryId") Long categoryId,
            @Param("status") int status
    );
}