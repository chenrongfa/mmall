package com.yy.mall.controller.portal;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IOrderService;
import com.yy.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by chenrongfa on 2017/10/14.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Controller
@RequestMapping(value = "/order/")
public class OrderController {

	@Autowired
	IOrderService iOrderService;

	@ResponseBody
	@RequestMapping("create.do")
	public Msg<OrderVo> createOrder(HttpSession session, Integer shippingId) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg<OrderVo> msg = iOrderService.create(user.getId(), shippingId);
		return msg;
	}
}
