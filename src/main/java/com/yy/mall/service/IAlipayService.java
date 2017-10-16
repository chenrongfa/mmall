package com.yy.mall.service;/**
 * Created by chenrongfa on 2017/10/13.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */

import com.yy.mall.common.Msg;

import java.util.Map;

/**
 * Created by chenrongfa on 2017/10/13.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public interface IAlipayService {
	Msg pay(Integer userId, Long orderNum, String path);



	Msg query(Integer id, Long orderNum);

	Msg checkData(Map<String, String> param);
}
