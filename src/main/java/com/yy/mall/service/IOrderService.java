package com.yy.mall.service;

import com.github.pagehelper.PageInfo;
import com.yy.mall.common.Msg;
import com.yy.mall.vo.OrderProductVo;
import com.yy.mall.vo.OrderVo;

/**
 * Created by chenrongfa on 2017/10/14.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public interface IOrderService {
	public Msg<OrderVo> create(Integer userId, Integer shippingId);

    Msg<OrderProductVo> getOrderInfo(Integer id);

    Msg<PageInfo> getOrderlistInfo(Integer userId, Integer pageNum, Integer id);

    Msg<OrderVo> getOrderDetail(Integer id, long orderNum);

    Msg<OrderVo> cancelOrderByOrderNum(Integer id, Long orderNum);

    Msg updateSend(Integer id, Long orderNum);
}
