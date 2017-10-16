package com.yy.mall.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/14.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class OrderVo {


	/**
	 * orderNo : 1485158223095
	 * payment : 2999.11
	 * paymentType : 1
	 * postage : 0
	 * status : 10
	 * paymentTime : null
	 * sendTime : null
	 * endTime : null
	 * closeTime : null
	 * createTime : 1485158223095
	 * shippingId : 5
	 * shippingVo : null
	 */

	private Long orderNo;
	private Double payment;
	private Integer paymentType;
	private Integer postage;
	private Integer status;
	private Date paymentTime;
	private Date sendTime;
	private Date endTime;
	private Date closeTime;
	private Date createTime;
	private Integer shippingId;
	private ShippingVo shippingVo;

	public List<OrderItemVo> getOrderItemVoList() {
		return orderItemVoList;
	}

	public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
		this.orderItemVoList = orderItemVoList;
	}

	private List<OrderItemVo> orderItemVoList;
	public long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPostage() {
		return postage;
	}

	public void setPostage(Integer postage) {
		this.postage = postage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Object getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Object getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Object getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getShippingId() {
		return shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public Object getShippingVo() {
		return shippingVo;
	}

	public void setShippingVo(ShippingVo shippingVo) {
		this.shippingVo = shippingVo;
	}
}
