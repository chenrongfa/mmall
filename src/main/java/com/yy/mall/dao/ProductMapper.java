package com.yy.mall.dao;

import com.yy.mall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

	List<Product> selectAll();

    List<Product> getProductByKeywordOrId(@Param(value = "keyword") String keyword,@Param(value = "productId") Integer productId);


	int updateStatusById(@Param(value = "id")Integer productId, @Param(value = "status")Integer
			status);

	Product selectByPrimaryKeyAndStatus(Integer productId);



}