package com.yy.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yy.mall.common.Msg;
import com.yy.mall.dao.ShippingMapper;
import com.yy.mall.pojo.Shipping;
import com.yy.mall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service
public class ShippingServiceImpl implements IShippingService {

@Autowired
	ShippingMapper shippingMapper;
	public Msg addShipping(Integer id, Shipping shipping) {
		if(shipping==null){
			return Msg.addErrorMessage("参数有误");
		}
		if(id==null){
			return Msg.addErrorMessage("用户名发生错误");
		}
		shipping.setUserId(id);
		int insertGetId = shippingMapper.insertGetId(shipping);
		if(insertGetId==0){
			Msg msg = Msg.addErrorMessage();
			return msg;
		}
		Msg msg = Msg.addSuccessMessage("添加成功");
		msg.putData("shippingId",shipping.getId());
		return msg;
	}

	public Msg deleteShipping(Integer userId, String shippingId) {
		int del=shippingMapper.deleteByPrimaryKeyAndUserId(userId,shippingId);
		if(del==0){
			return Msg.addErrorMessage("删除失败");
		}
		return Msg.addSuccessMessage("删除成功");
	}

	public Msg updateShipping(Integer userId, Shipping shipping) {
		shipping.setUserId(userId);
		Shipping shippingCache = shippingMapper.selectByPrimaryKey(shipping.getId());
		if(shipping==shippingCache){
			return Msg.addSuccessMessage("数据已经最新");
		}
		int update=shippingMapper.updateByPrimaryKeyAndUseridSelective(shipping);
		if(update==0){
			return Msg.addErrorMessage("修改失败");
		}
		return Msg.addSuccessMessage("修改成功");
	}

	public Msg selectAllShipping(Integer userId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Shipping> shippings= shippingMapper.selectAllByUserId(userId);
		if(shippings.size()==0){
			return Msg.createSuccess("数据为空");
		}
		PageInfo pageInfo=new PageInfo(shippings);
		return Msg.createSuccess(pageInfo);
	}

	public Msg selectShipping(Integer userId, String shippingId) {
		Shipping shipping=shippingMapper.getShippingByUserIdShippingId(userId,shippingId);
		  if(shipping==null){
		  	return Msg.addErrorMessage("查找不到");
		  }
		return Msg.createSuccess(shipping);
	}
}
