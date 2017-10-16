package com.yy.mall.service;


import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;

import javax.servlet.http.HttpSession;


public interface IUserService {
	Msg login(String username,String password);
	Msg register(User user);

	/**
	 *
	 * @param validString
	 * @param type 1为验证用户名 2. 验证 email 3.验证手机号
	 * @return
	 */
	Msg check_valid(String validString ,int type);

	Msg getUserInfo(HttpSession session);

	/**
	 *  通过用户名 得到问题
	 * @param username
	 * @return
	 */
	Msg getQuestion(String username);

	/**
	 *  通过问题 验证答案
	 * @param username
	 * @param question
	 * @return
	 */
	Msg checkAnswer(String username, String question,String answer);

	/**
	 *  回答问题正确然后修改密码
	 * @param username
	 * @param password
	 * @param token
	 * @return
	 */
	Msg resetPassword(String username, String password, String token);

	Msg resetPasswordWithLogin(String passwordOld, String passwordOld1,User session);

	Msg updateUserInfo(String email, String phone, String question, String answer, User attribute);
	boolean isAdmin(User user);
}
