package com.yy.mall.dao;

import com.yy.mall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	Order selectOrderByUserIdOrderNum(@Param(value = "userId") Integer userId, @Param(value =
            "orderNum")long orderNum);

	Order selectOrderByOrderNum(long orderNum);

    List<Order> selectOrderByUserId(Integer userId);
}