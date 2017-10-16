package com.yy.mall.dao;

import com.yy.mall.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

	PayInfo selectPayInfoByUserIdOrderNum(@Param(value = "userId") Integer userId, @Param(value =
            "orderNum")long orderNum);
}