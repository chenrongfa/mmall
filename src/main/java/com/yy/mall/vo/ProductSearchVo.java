package com.yy.mall.vo;

import java.math.BigDecimal;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class ProductSearchVo {

	/**
	 * id : 1
	 * categoryId : 3
	 * name : iphone7
	 * subtitle : 双十一促销
	 * mainImage : mainimage.jpg
	 * price : 7199.22
	 */

	private int id;
	private int categoryId;
	private String name;
	private String subtitle;
	private String mainImage;
	private BigDecimal price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
