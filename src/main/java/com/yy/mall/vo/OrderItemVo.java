package com.yy.mall.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chenrongfa on 2017/10/14.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class OrderItemVo {


	/**
	 * orderNo : 1485158223095
	 * productId : 2
	 * productName : oppo R8
	 * productImage : mainimage.jpg
	 * currentUnitPrice : 2999.11
	 * quantity : 1
	 * totalPrice : 2999.11
	 * createTime : null
	 */

	private Long orderNo;
	private Integer productId;
	private String productName;
	private String productImage;
	private Double currentUnitPrice;
	private Integer quantity;
	private BigDecimal totalPrice;
	private Date createTime;

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Double getCurrentUnitPrice() {
		return currentUnitPrice;
	}

	public void setCurrentUnitPrice(Double currentUnitPrice) {
		this.currentUnitPrice = currentUnitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
