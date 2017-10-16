package com.yy.mall.common;/**
 * Created by chenrongfa on 2017/10/9.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */


public enum  ResponseCode {

	SUCCESS(200,"success"),
	ERROR(100,"error");



	private int status;
	private String msg;

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	ResponseCode(int status , String msg){
		this.status=status;
		this.msg=msg;

	}



}
