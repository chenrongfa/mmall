package com.yy.mall.controller.portal;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.ICartService;
import com.yy.mall.vo.CartProductVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import static javax.swing.text.html.CSS.getAttribute;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@RequestMapping(value = "/cart/")
@Controller
public class CartController {

	@Autowired
	ICartService iCartService;
	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<CartProductVo> getCartList(HttpSession session,
	                                      @RequestParam(value = "pageNum", defaultValue = "0")
			                                      Integer pageNum, @RequestParam(value =
			"pageSize", defaultValue = "10")
			                                      Integer pageSize) {
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户为登录,请登录");
		}
		Msg<CartProductVo> cartList = iCartService.getCartList( pageNum, pageSize,user.getId());

		return cartList;
	}
	@RequestMapping(value = "updateCount.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<CartProductVo> updateCount(HttpSession session,Integer productId,
	                                  Integer count
	                                 ) {
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg<CartProductVo> cartList = iCartService.updateCount(user.getId(),productId,count);

		return cartList;
	}
	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<CartProductVo> addCart(HttpSession session,Integer productId,
	                                 @RequestParam(value = "count" ,defaultValue = "0") Integer
			                                 count
	                                 ) {
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg<CartProductVo> cartList = iCartService.addCart(user.getId(),productId,count);

		return cartList;
	}

	@RequestMapping(value = "delete_product.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<CartProductVo> deleteCart(HttpSession session,String productIds

	                                 ) {
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg<CartProductVo> cartList = iCartService.deleteCart(user.getId(),productIds);
		return cartList;
	}

	@RequestMapping(value = "select.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<CartProductVo> selectCart(HttpSession session,Integer productId
	                              ,@RequestParam(value = "ckecked" ,defaultValue = "0")Integer
			                                         ckecked   ) {
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg<CartProductVo> cartList = iCartService.selectCart(user.getId(),productId,ckecked);

		return cartList;
	}
	@RequestMapping(value = "select_all.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<CartProductVo> selectAllCart(HttpSession session
	                              ,@RequestParam(value = "checked" ,defaultValue = "0")Integer
			                                         checked   ) {
		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		Msg<CartProductVo> cartList = iCartService.selectAllCart(user.getId(),checked);

		return cartList;
	}
}
