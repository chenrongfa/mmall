package com.yy.mall.service;

import com.yy.mall.common.Msg;
import com.yy.mall.vo.CartProductVo;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public interface ICartService {

	Msg<CartProductVo> getCartList(Integer pageNum, Integer pageSize,
	                               Integer userId);

	Msg<CartProductVo> addCart(Integer userId,Integer id, Integer count);

	Msg<CartProductVo> updateCount(Integer id, Integer productId, Integer count);

	Msg<CartProductVo> deleteCart(Integer userId,String productIds);

	/**
	 *  单选/反选
	 * @param id
	 * @param productId
	 * @param ckecked
	 * @return
	 */
	Msg<CartProductVo> selectCart(Integer id, Integer productId, Integer ckecked);

	/**
	 *  全选/全反选
	 * @param id
	 * @param ckecked
	 * @return
	 */
	Msg<CartProductVo> selectAllCart(Integer id, Integer ckecked);
}
