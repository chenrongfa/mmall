package com.yy.mall.service;

import com.yy.mall.common.Msg;
import com.yy.mall.pojo.Shipping;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public interface IShippingService {
	Msg addShipping(Integer id, Shipping shipping);

	Msg deleteShipping(Integer id, String shippingId);

	Msg selectShipping(Integer userId, String shippingId);

	Msg selectAllShipping(Integer id, Integer pageNum, Integer pageSize);

	Msg updateShipping(Integer id, Shipping shipping);
}
