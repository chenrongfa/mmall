package com.yy.mall.dao;

import com.yy.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

	int  insertGetId(Shipping shipping);

    int deleteByPrimaryKeyAndUserId(@Param(value = "userId") Integer userId,@Param(value =
            "shippingId") String shippingId);

    Shipping getShippingByUserIdShippingId(@Param(value = "userId") Integer userId,@Param(value =
            "shippingId") String shippingId);

    List<Shipping> selectAllByUserId(Integer userId);

    int updateByPrimaryKeyAndUseridSelective(Shipping shipping);
}