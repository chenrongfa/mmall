package com.yy.mall.service;

import com.github.pagehelper.PageInfo;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.Product;
import com.yy.mall.vo.ProductDetailVo;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public interface IProductService {
	/**
	 *  查询所有的商品
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Msg<PageInfo> getProductList(Integer pageNum, Integer pageSize);

	/**
	 *  通过关键字或者 id查询
	 * @param keyword
	 * @param productId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Msg<PageInfo> searchProductList(String keyword, Integer productId, Integer pageNum, Integer pageSize);
	

	Msg<ProductDetailVo> getProductDetail(Integer productId);

	Msg setStatusById(Integer productId, Integer status);

	/**
	 *  根据是否通入id 进行插入还是更新操作
	 * @param product
	 * @return
	 */
	Msg saveOrUpdate(Product product);

	/**
	 *   通过关键字或者id 查询
	 * @param keyword
	 * @param categoryId
	 * @param orderBy
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Msg<PageInfo> getProductListWithOrderby(String keyword, Integer categoryId, String orderBy, Integer
			pageNum, Integer pageSize);

	Msg<ProductDetailVo> getProductDetailPortal(Integer productId);
}
