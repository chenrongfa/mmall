package com.yy.mall.controller.back;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.Category;
import com.yy.mall.pojo.User;
import com.yy.mall.service.ICategoryService;
import com.yy.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function: 后台商品分类
 */
@Controller
@RequestMapping(value = "/manage/category/")
public class CategoryManageController {
	@Autowired
	ICategoryService iCategoryService;
	@Autowired
	IUserService userService;

	@RequestMapping(value = "find_category.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<List<Category>> findCategory(@RequestParam(value ="parentId",defaultValue = "0") int
			                                            parentId, HttpSession session) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addSuccessMessage("用户未登录,请登录");
		}else{
			if(!userService.isAdmin(user)){
				return Msg.addErrorMessage("无权限操作");
			}
		}

		Msg<List<Category>> category = iCategoryService.findCategory(parentId);
		return category;
	}

	@RequestMapping(value = "add_category.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg createCategory(@RequestParam(value ="parentId",defaultValue = "0")
			                                              int
			                                            parentId,String categoryName, HttpSession
			session) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addSuccessMessage("用户未登录,请登录");
		}else{
			if(!userService.isAdmin(user)){
				return Msg.addErrorMessage("无权限操作");
			}
		}

		Msg msg = iCategoryService.createCategory(parentId,categoryName);
		return msg;
	}
	@RequestMapping(value = "update_category.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg updateCategory( Integer categoryId,String categoryName, HttpSession
			session) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addSuccessMessage("用户未登录,请登录");
		}else{
			if(!userService.isAdmin(user)){
				return Msg.addErrorMessage("无权限操作");
			}
		}

		Msg msg = iCategoryService.updateCategory(categoryId,categoryName);
		return msg;
	}
	@RequestMapping(value = "get_deep_category.do", method = RequestMethod.GET)
	@ResponseBody
	public Msg<List<Category>> findDeepCategory( int categoryId, HttpSession
			session) {
		System.out.println("122333");
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addSuccessMessage("用户未登录,请登录");
		}else{
			if(!userService.isAdmin(user)){
				return Msg.addErrorMessage("无权限操作");
			}
		}

		Msg<List<Category>> msg = iCategoryService.findDeepCategory(categoryId);
		return msg;
	}
}
