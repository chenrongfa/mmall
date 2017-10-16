package com.yy.mall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class CartProductVo {




	private boolean allChecked;
	private BigDecimal cartTotalPrice;
	private List<CartProductVoListBean> cartProductVoList;

	public boolean isAllChecked() {
		return allChecked;
	}

	public void setAllChecked(boolean allChecked) {
		this.allChecked = allChecked;
	}

	public BigDecimal getCartTotalPrice() {
		return cartTotalPrice;
	}

	public void setCartTotalPrice(BigDecimal cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}

	public List<CartProductVoListBean> getCartProductVoList() {
		return cartProductVoList;
	}

	public void setCartProductVoList(List<CartProductVoListBean> cartProductVoList) {
		this.cartProductVoList = cartProductVoList;
	}

	public static class CartProductVoListBean {
		/**
		 * id : 1
		 * userId : 13
		 * productId : 1
		 * quantity : 1
		 * productName : iphone7
		 * productSubtitle : 双十一促销
		 * productMainImage : mainimage.jpg
		 * productPrice : 7199.22
		 * productStatus : 1
		 * productTotalPrice : 7199.22
		 * productStock : 86
		 * productChecked : 1
		 * limitQuantity : LIMIT_NUM_SUCCESS
		 */

		private int id;
		private int userId;
		private int productId;
		private int quantity;
		private String productName;
		private String productSubtitle;
		private String productMainImage;
		private double productPrice;
		private int productStatus;
		private BigDecimal productTotalPrice;
		private int productStock;
		private int productChecked;
		private String limitQuantity;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductSubtitle() {
			return productSubtitle;
		}

		public void setProductSubtitle(String productSubtitle) {
			this.productSubtitle = productSubtitle;
		}

		public String getProductMainImage() {
			return productMainImage;
		}

		public void setProductMainImage(String productMainImage) {
			this.productMainImage = productMainImage;
		}

		public double getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(double productPrice) {
			this.productPrice = productPrice;
		}

		public int getProductStatus() {
			return productStatus;
		}

		public void setProductStatus(int productStatus) {
			this.productStatus = productStatus;
		}

		public BigDecimal getProductTotalPrice() {
			return productTotalPrice;
		}

		public void setProductTotalPrice(BigDecimal productTotalPrice) {
			this.productTotalPrice = productTotalPrice;
		}

		public int getProductStock() {
			return productStock;
		}

		public void setProductStock(int productStock) {
			this.productStock = productStock;
		}

		public int getProductChecked() {
			return productChecked;
		}

		public void setProductChecked(int productChecked) {
			this.productChecked = productChecked;
		}

		public String getLimitQuantity() {
			return limitQuantity;
		}

		public void setLimitQuantity(String limitQuantity) {
			this.limitQuantity = limitQuantity;
		}
	}
}
