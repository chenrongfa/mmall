package com.yy.mall.controller.back;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:后台
 */
@RequestMapping(value = "/manage/user/")
public class UserManageController {
	@Autowired
	IUserService userService;
	@ResponseBody
	@RequestMapping(value = "login.do",method = RequestMethod.POST)
	public Msg login(String username, String password, HttpSession session){

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return Msg.addMessage(100, "用户名或者密码不能为空");
		}

		Msg login = userService.login(username, password);

		if (login.isSuccess()) {
			User user = (User) login.getDataBean().get(Constance.CURRENT_USER);
			if(user.getRole()==Constance.ADMIN)
			session.setAttribute(Constance.CURRENT_USER, user);
			else{
				return Msg.addErrorMessage("不是管理员,无权限登录");
			}
		}
		return login;
	}
}
