package com.yy.mall.service.impl;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.common.TokenCache;
import com.yy.mall.dao.UserMapper;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IUserService;
import com.yy.mall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * Created by chenrongfa on 2017/10/9.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl implements IUserService {

	@Autowired
	UserMapper userMapper;

	/**
	 *  登录
	 * @param username
	 * @param password
	 * @return
	 */
	public Msg login(String username, String password) {
		int exist = userMapper.isExistUsername(username);
		if(exist==0){
			return Msg.addErrorMessage("用户名不存在");
		}
		String md5 = MD5Util.MD5EncodeUtf8(password);
		System.out.println(md5+"md5");
		User pass = userMapper.checkPassword(username, md5);
		if(pass==null){
			return Msg.addErrorMessage("密码错误");
		}
		Msg msg = Msg.addMessage(200, "登录成功");
		pass.setPassword("");
		msg.putData(Constance.CURRENT_USER,pass);

		return msg;
	}

	/**
	 *  注册
	 * @param user
	 * @return
	 */
	public Msg register(User user) {
		String username = user.getUsername();
		if (isExistUsername(username))
			return Msg.addErrorMessage("用户名存在");
		String password = user.getPassword();

		String email = user.getEmail();
		if (isExistEmail(email))
			return Msg.addErrorMessage("email已经被使用");
		//检查email
		String md5 = MD5Util.MD5EncodeUtf8(password);
		user.setPassword(md5);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setRole(0);
		int selective = userMapper.insert(user);

		System.out.println(selective+"selective");
		System.out.println(user.toString());
		if(selective==0){
			return Msg.addErrorMessage("插入数据失败");
		}


		return Msg.addSuccessMessage("注册成功");
	}

	private boolean isExistEmail(String email) {

		int existEmail = userMapper.isExistEmail(email);
		if(existEmail>0){
			return true;
		}
		return false;
	}
	private boolean isExistPhone(String phone) {
		int existEmail = userMapper.isExistPhone(phone);
		if(existEmail>0){
			return true;
		}
		return false;
	}

	private boolean isExistUsername(String username) {
		int exist = userMapper.isExistUsername(username);
		if(exist>0){
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param validString
	 * @param type 1为验证用户名 2. 验证 email 3.验证手机号
	 * @return
	 */
	public Msg check_valid(String validString, int type) {
		if(StringUtils.isBlank(validString)){
			return Msg.addErrorMessage("参数不能为空");
		}
		if(type==1){
			if (isExistUsername(validString))
				return Msg.addErrorMessage("用户名存在");
		}
		 else if(type==2){
			// todo 验证email合不合格式
			if (isExistUsername(validString))
				return Msg.addErrorMessage("email已经被使用");
		}

		else if(type==3){
			// todo 验证phone合不合格式
			if (isExistUsername(validString))
				return Msg.addErrorMessage("手机号已经被使用");
		}
		return Msg.addSuccessMessage("验证成功");
	}

	/**
	 *  获取当前用户信息
	 * @param session
	 * @return
	 */

	public Msg getUserInfo(HttpSession session) {
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("用户当前未登录");
		}

		Msg msg = Msg.addSuccessMessage();
		msg.putData(Constance.CURRENT_USER,user);
		return msg;
	}

	/**
	 *
	 * @param username 通过username 获取问题
	 * @return
	 */
	public Msg getQuestion(String username) {
		if(org.apache.commons.lang3.StringUtils.isBlank(username)){
			return Msg.addErrorMessage("用户名不能为空");
		}
		String question=null;
		if(isExistUsername(username)){
			//存在
		 question=userMapper.getQuestion(username);
		if(StringUtils.isBlank(question)){
			return Msg.addErrorMessage("问题为空");
		}
		}
		Msg msg = Msg.addSuccessMessage();
		msg.putData("question",question);
		return msg;
	}

	public Msg checkAnswer(String username, String question,String answer) {
		if(org.apache.commons.lang3.StringUtils.isBlank(username)){
			return Msg.addErrorMessage("用户名不能为空");
		}
		 if(!isExistUsername(username)){
			return Msg.addErrorMessage("用户名不存在");
		 }
		if(org.apache.commons.lang3.StringUtils.isBlank(answer)){
			return Msg.addErrorMessage("答案不能为空");
		}
	int success=userMapper.checkAnswer(username,question,answer);
		if(success==0){

			return Msg.addErrorMessage("答案错误");
		}
		String uuid = UUID.randomUUID().toString();
		//产生token 用于修改密码
		TokenCache.putToken(TokenCache.CACHE_KEY+username,uuid);
		Msg msg = Msg.addSuccessMessage("回答正确");
		msg.putData("forget_token",uuid);
		return msg;
	}

	/**
	 *  答案正确 修改密码
	 * @param username
	 * @param password
	 * @param token
	 * @return
	 */
	public Msg resetPassword(String username, String password, String token) {
		if(org.apache.commons.lang3.StringUtils.isBlank(username)){
			return Msg.addErrorMessage("用户名不能为空");
		}
		if(!isExistUsername(username)){
			return Msg.addErrorMessage("用户名不存在");
		}
		if(org.apache.commons.lang3.StringUtils.isBlank(password)){
			return Msg.addErrorMessage("密码不能为空");
		}
		if(org.apache.commons.lang3.StringUtils.isBlank(token)){
			return Msg.addErrorMessage("token不能为空");
		}
		String tokenCache = TokenCache.getToken(TokenCache.CACHE_KEY + username);
		if(!token.equals(tokenCache)){
			return Msg.addErrorMessage("token过期");
		}
		String md5 = MD5Util.MD5EncodeUtf8(password);
		int update= userMapper.resetPassword(username, md5);
		if(update==0){
			return Msg.addErrorMessage("修改失败");
		}

		Msg msg = Msg.addSuccessMessage("重置成功");
		return msg;
	}

	public Msg resetPasswordWithLogin(String passwordOld, String passwordOld1,User user) {
		if(org.apache.commons.lang3.StringUtils.isBlank(passwordOld)||org.apache.commons
				.lang3.StringUtils.isBlank(passwordOld1)){
			return Msg.addErrorMessage("密码不能为空");
		}
		if(!isExistUsername(user.getUsername())){
			return Msg.addErrorMessage("用户名不存在");
		}
		if(passwordOld.equals(passwordOld1)){
			return Msg.addErrorMessage("旧密码和新密码不能相等");
		}
		String md5 = MD5Util.MD5EncodeUtf8(passwordOld1);
		int resetPassword = userMapper.resetPassword(user.getUsername(), md5);
		if(resetPassword==0){
			return Msg.addErrorMessage("修改失败");
		}
		return Msg.addSuccessMessage("修改成功");
	}

	public Msg updateUserInfo(String email, String phone, String question, String answer, User
			session) {
		if(StringUtils.isBlank(email)){
			return Msg.addSuccessMessage("email不能为空");
		}
		if(isExistEmail(email)){
			return Msg.addErrorMessage("email已经被使用");
		}
		if(StringUtils.isBlank(phone)){
			return Msg.addSuccessMessage("email不能为空");
		}
		if(isExistPhone(phone)){
			return Msg.addErrorMessage("手机号已经被使用");
		}
		session.setEmail(email);
		session.setPassword(null);
		session.setPhone(phone);
		session.setUpdateTime(new Date());
		if(StringUtils.isNotBlank(question))
		session.setQuestion(question);
		if(StringUtils.isNotBlank(answer))
		session.setAnswer(answer);
		int update = userMapper.updateByPrimaryKeySelective(session);
		if(update==0){
			return Msg.addErrorMessage("更新失败");
		}
		return Msg.addSuccessMessage("更新成功");
	}

	public boolean isAdmin(User user) {

		if(user.getRole()==Constance.ADMIN){
			return true;
		}
		return false;
	}

}
