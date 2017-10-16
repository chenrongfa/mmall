package com.yy.mall.common;

/**
 * Created by chenrongfa on 2017/10/9.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class Constance {

	public static final Integer ADMIN =1 ;
	public static String CURRENT_USER="current_user";

	public enum OrderStatus{
	   SEND(40,"已经发货"),
	   CANNAL(0,"已经取消"),
	   WAITPAY(10,"未付款"),
	   OFFERED(20,"已经付款"),
	   CLOED(50,"已经关闭"),
	   FINISHED(60,"已经完成");
	 	OrderStatus(int status,String msg){
		this.status=status;
		this.msg=msg;
	}
		private String msg;
		private int status;

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}



	}

	public interface OrderPayStatus{

		String WAIT_BUYER_PAY="WAIT_BUYER_PAY";
		String TRADE_CLOSED ="TRADE_CLOSED";
		String TRADE_SUCCESS="TRADE_SUCCESS";
		String TRADE_FINISHED="TRADE_FINISHED";

	}
}
