package com.yy.mall.dao;

import com.yy.mall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);


	User checkPassword(@Param("username") String username, @Param("password") String password);

	int isExistUsername(String username);

	int isExistEmail(String email);

	int isExistPhone(String phone);

	String getQuestion(String username);

	int checkAnswer(@Param("username") String username, @Param("question") String question, @Param
			("answer") String
			answer);

	int resetPassword(@Param("username")String username,@Param("password") String password);
}