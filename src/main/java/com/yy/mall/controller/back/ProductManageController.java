package com.yy.mall.controller.back;

import com.github.pagehelper.PageInfo;
import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.Product;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IProductService;
import com.yy.mall.service.IUserService;
import com.yy.mall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Controller
@RequestMapping(value = "/manage/product/")
public class ProductManageController {
	@Autowired
	IProductService iProductService;
	@Autowired
	IUserService userService;
	@ResponseBody
	@RequestMapping(value = "product_list.do", method = RequestMethod.GET)
	public Msg<PageInfo> findProductList(HttpSession session, @RequestParam(value = "pageNum",
			defaultValue = "1")
			Integer pageNum, @RequestParam(value = "pageSize",
			defaultValue = "10")
			Integer pageSize) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addSuccessMessage("用户未登录,请登录");
		} else {
			if (!userService.isAdmin(user)) {
				return Msg.addErrorMessage("无权限操作");
			}
		}
		Msg<PageInfo> msg = iProductService.getProductList(pageNum, pageSize);
		return msg;
	}
	@ResponseBody
	@RequestMapping(value = "search.do", method = RequestMethod.POST)
	public Msg<PageInfo> searchProduct(HttpSession session, @RequestParam(value = "pageNum",
			defaultValue = "1")
			Integer pageNum, @RequestParam(value = "pageSize",
			defaultValue = "10" )
			Integer pageSize,@RequestParam(value = "keyword",required = false)String keyword,
	                                   @RequestParam(value = "productId",required = false) Integer	productId) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addSuccessMessage("用户未登录,请登录");
		} else {
			if (!userService.isAdmin(user)) {
				return Msg.addErrorMessage("无权限操作");
			}
		}
		Msg<PageInfo> msg = iProductService.searchProductList(keyword,productId,pageNum, pageSize);
		return msg;
	}
	@ResponseBody
	@RequestMapping(value = "product_detail.do", method = RequestMethod.GET)
	public Msg<ProductDetailVo> getProductDetail(HttpSession session,Integer	productId) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addSuccessMessage("用户未登录,请登录");
		} else {
			if (!userService.isAdmin(user)) {
				return Msg.addErrorMessage("无权限操作");
			}
		}
		Msg<ProductDetailVo> msg = iProductService.getProductDetail(productId);
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "set_status.do", method = RequestMethod.POST)
	public Msg setStatus(HttpSession session,
	                     Integer status, Integer	productId) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addSuccessMessage("用户未登录,请登录");
		} else {
			if (!userService.isAdmin(user)) {
				return Msg.addErrorMessage("无权限操作");
			}
		}
		Msg msg = iProductService.setStatusById(productId,status);
		return msg;
	}
	@ResponseBody
	@RequestMapping(value = "save_or_update.do", method = RequestMethod.POST)
	public Msg saveOrUpdate(HttpSession session,
	                        Product product) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addSuccessMessage("用户未登录,请登录");
		} else {
			if (!userService.isAdmin(user)) {
				return Msg.addErrorMessage("无权限操作");
			}
		}
		Msg msg = iProductService.saveOrUpdate(product);
		return msg;
	}

}
