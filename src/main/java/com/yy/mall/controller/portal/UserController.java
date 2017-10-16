package com.yy.mall.controller.portal;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/9.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function: 前端用户登录
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {
	@Autowired
	IUserService userService;

	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg login(String username, String password, HttpSession session) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return Msg.addMessage(100, "用户名或者密码不能为空");
		}
		System.out.println("username" + username);
		System.out.println("password" + password);
		Msg login = userService.login(username, password);

		if (login.isSuccess()) {
			User user = (User) login.getDataBean().get(Constance.CURRENT_USER);
			session.setAttribute(Constance.CURRENT_USER, user);
		}
		return login;
	}

	@RequestMapping(value = "register.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg register(@Valid User user, Errors error) {
		Msg msg = null;
		if (error.hasFieldErrors()) {
			List<FieldError> fieldErrors = error.getFieldErrors();
			for (FieldError field : fieldErrors) {
				//移动端
				msg = new Msg(100, field.getDefaultMessage());
				//网页端的校验
				msg.putData(field.getField(), field.getDefaultMessage());

			}
			return msg;
		}
		msg = userService.register(user);
		return msg;
	}

	@RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg check_valid(String valid, int type) {

		Msg msg = userService.check_valid(valid, type);
		return msg;
	}

	@RequestMapping(value = "getUserInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg getUserInfo(HttpSession session) {

		Msg msg = userService.getUserInfo(session);
		return msg;
	}

	@RequestMapping(value = "getQuestion.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg getQuestion(String username) {

		Msg msg = userService.getQuestion(username);
		return msg;
	}
	@RequestMapping(value = "check_answer.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg checkAnswer(String username,String question,String answer) {
		Msg msg = userService.checkAnswer(username,question,answer);
		return msg;
	}

	@RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg resetPassword(String username, String password,String token) {
		Msg msg = userService.resetPassword(username,password,token);
		return msg;
	}
	@RequestMapping(value = "reset_password_withLogin.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg resetPasswordWithLogin(String passwordOld, String passwordNew,HttpSession session) {
		User attribute = (User) session.getAttribute(Constance.CURRENT_USER);
		if(attribute==null){
			return Msg.addSuccessMessage("未登录状态");
		}
		Msg msg = userService.resetPasswordWithLogin(passwordOld,passwordNew,attribute);
		return msg;
	}
   @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg updateUserInfo(String email,String phone,String question,String answer,HttpSession
                              session) {
		User attribute = (User) session.getAttribute(Constance.CURRENT_USER);
		if(attribute==null){
			return Msg.addSuccessMessage("未登录状态");
		}
		Msg msg = userService.updateUserInfo(email,phone,question,answer,attribute);
		return msg;
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	@ResponseBody
	public Msg logout(HttpSession session) {
		User attribute = (User) session.getAttribute(Constance.CURRENT_USER);
		if(attribute==null){
			return Msg.addSuccessMessage("未登录状态");
		}
		session.setAttribute(Constance.CURRENT_USER,null);
		return Msg.addSuccessMessage("成功退出");
	}


}
