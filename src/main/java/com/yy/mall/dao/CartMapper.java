package com.yy.mall.dao;

import com.yy.mall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Cart record);

	int insertSelective(Cart record);

	Cart selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Cart record);

	int updateByPrimaryKey(Cart record);

	List<Cart> getCartList(Integer userId);

	int isAllSelect(Integer userId);

	Cart getCartByUserIdAndProductId(@Param(value = "userId") Integer userId, @Param(value =
			"productId") Integer productId);

	int deleteCartByProduct(@Param(value = "userId") Integer userId, @Param(value = "productIds")
			List<String> productIds);


	int updateCartCheckedByUserIdAndProduce(@Param(value = "userId") Integer userId, @Param(value =
			"productId") Integer productId, @Param(value = "checked") Integer ckecked);

	List<Integer> getCartProductIds(Integer userId);

	int updateCartCheckedByProductIds(@Param(value = "productIds")List<Integer> productIds,
	                                  @Param(value = "checked" ) Integer checked);

	List<Cart> selectCheckByUserId(Integer userId);
}