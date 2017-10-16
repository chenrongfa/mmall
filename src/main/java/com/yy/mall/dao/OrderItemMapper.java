package com.yy.mall.dao;

import com.yy.mall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

	List<OrderItem> selectList(@Param(value = "userId") Integer userId, @Param(value =
            "orderNum")Long orderNum);

	void batchInsert(@Param("items") List<OrderItem> items);
}