package com.yy.mall.controller.portal;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.Shipping;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Controller
@RequestMapping(value = "/shipping/")
public class ShippingController {
	@Autowired
	IShippingService iShippingService;
	@ResponseBody
	@RequestMapping( value = "add.do" ,method = RequestMethod.POST)
	public Msg addShipping(HttpSession session,
	                       Shipping shipping){
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		System.out.println(shipping.toString());
		Msg	 msg=iShippingService.addShipping(user.getId(),shipping);
		return msg;
	}
	@ResponseBody
	@RequestMapping( value = "delete.do" ,method = RequestMethod.POST)
	public Msg deleteShipping(HttpSession session,
	                      String shippingId){
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg	 msg=iShippingService.deleteShipping(user.getId(),shippingId);
		return msg;
	}
	@ResponseBody
	@RequestMapping( value = "select.do" ,method = RequestMethod.POST)
	public Msg selectShipping(HttpSession session,
	                      String shippingId){
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg	 msg=iShippingService.selectShipping(user.getId(),shippingId);
		return msg;
	}
	@ResponseBody
	@RequestMapping( value = "select_all.do" ,method = RequestMethod.POST)
	public Msg selectAllShipping(HttpSession session
			, @RequestParam(value = "pageNum",
			defaultValue = "1")
			                                 Integer pageNum, @RequestParam(value = "pageSize",
			defaultValue = "10" )
			                                 Integer pageSize  ){
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg	 msg=iShippingService.selectAllShipping(user.getId(),pageNum,pageSize);
		return msg;
	}
	@ResponseBody
	@RequestMapping( value = "update.do" ,method = RequestMethod.POST)
	public Msg updateShipping(HttpSession session
			, Shipping shipping ){
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg	 msg=iShippingService.updateShipping(user.getId(),shipping);
		return msg;
	}
}
