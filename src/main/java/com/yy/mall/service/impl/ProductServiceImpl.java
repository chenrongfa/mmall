package com.yy.mall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yy.mall.common.Msg;
import com.yy.mall.dao.CategoryMapper;
import com.yy.mall.dao.ProductMapper;
import com.yy.mall.pojo.Category;
import com.yy.mall.pojo.Product;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IProductService;
import com.yy.mall.vo.ProductDetailVo;
import com.yy.mall.vo.ProductListVo;
import com.yy.mall.vo.ProductSearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service
public class ProductServiceImpl implements IProductService{
	@Autowired
	ProductMapper productMapper;
	@Autowired
	CategoryMapper categoryMapper;
	public Msg<PageInfo> getProductList(Integer pageNum,Integer pageSize){
		PageHelper.startPage(pageNum,pageSize);
	    List<Product> products= productMapper.selectAll();
	    if(products.size()==0||products==null){
	    	return Msg.addSuccessMessage("数据为空");
	    }
	    List<ProductListVo> productListVos= Lists.newArrayList();
	    for(Product item:products){
	    ProductListVo productListVo=createProductListVo(item);
	    productListVos.add(productListVo);
	    }
	    PageInfo pageInfo=new PageInfo(products);
		pageInfo.setList(productListVos);
		Msg<PageInfo> msg = Msg.createSuccess(pageInfo);
		return msg;
	}

	public Msg<PageInfo> searchProductList(String keyword, Integer productId, Integer pageNum,
	                                       Integer pageSize) {
		if(StringUtils.isNotBlank(keyword)){
			keyword=new StringBuilder("%").append(keyword).append("%").toString();
		}
		PageHelper.startPage(pageNum,pageSize);
		List<Product> products= productMapper.getProductByKeywordOrId(keyword,productId);
		if(products.size()==0||products==null){
			return Msg.addSuccessMessage("数据为空");
		}
		List<ProductSearchVo> productSearchs= Lists.newArrayList();
		for(Product item:products){
			ProductSearchVo productListVo=createProductSearchVo(item);
			productSearchs.add(productListVo);
		}
		PageInfo pageInfo=new PageInfo(products);
		pageInfo.setList(productSearchs);
		Msg<PageInfo> msg = Msg.createSuccess(pageInfo);
		return msg;
	}

	public Msg<ProductDetailVo> getProductDetail(Integer productId) {
		if(productId==null){
			return Msg.addErrorMessage("参数错误");
		}
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product==null){
			return Msg.addErrorMessage("产品下架或者已经删除");
		}
		ProductDetailVo productDetailVo= createProductDetailVo(product);
		return Msg.createError(productDetailVo);
	}

	/**
	 *
	 * @param productId
	 * @param status 1上架 2下架 3删除
	 * @return
	 */
	public Msg setStatusById(Integer productId, Integer status) {
		if(productId==null||status==null){
			return Msg.addErrorMessage("参数错误");
		}
		if (status < 1 || status > 3) {
			return Msg.addErrorMessage("status 参数错误");
		}

		Product product = productMapper.selectByPrimaryKey(productId);
		if(product==null){
			return Msg.addErrorMessage("没有找到对应id");
		}
		if(product.getStatus()==status){
			return Msg.addSuccessMessage("无需修改");
		}
		int update=productMapper.updateStatusById(productId,status);
		if(update==0){
			return Msg.addErrorMessage("修改失败");
		}
		return Msg.addSuccessMessage("修改成功");
	}

	public Msg saveOrUpdate(Product product) {
		Integer id = product.getId();
		if(id==null){
			int insert = productMapper.insertSelective(product);
			if(insert>0){
				return Msg.addSuccessMessage("添加产品成功");
			}else{
				return Msg.addErrorMessage("添加产品失败");
			}

		}

		int update = productMapper.updateByPrimaryKeySelective(product);
		if(update==0){

			return Msg.addErrorMessage("更新产品失败或数据已经最新");

		}

		return Msg.addSuccessMessage("更新产品成功");
	}

	public Msg<PageInfo> getProductListWithOrderby(String keyword, Integer productId, String
			orderBy, Integer pageNum, Integer pageSize) {
		if(StringUtils.isNotBlank(keyword)){
			keyword=new StringBuilder("%").append(keyword).append("%").toString();
		}
		PageHelper.startPage(pageNum, pageSize);


		List<Product> products= productMapper.getProductByKeywordOrId(keyword,productId);
		if(products.size()==0||products==null){
			return Msg.addSuccessMessage("数据为空");
		}
		List<ProductSearchVo> productSearchs= Lists.newArrayList();
		for(Product item:products){
			ProductSearchVo productListVo=createProductSearchVo(item);
			productSearchs.add(productListVo);
		}
		PageInfo pageInfo=new PageInfo(products);

		pageInfo.setList(productSearchs);
		Msg<PageInfo> msg = Msg.createSuccess(pageInfo);
		return msg;


	}

	public Msg<ProductDetailVo> getProductDetailPortal(Integer productId) {
		if(productId==null){
			return Msg.addErrorMessage("参数错误");
		}
		Product product = productMapper.selectByPrimaryKeyAndStatus(productId);
		if(product==null){
			return Msg.addErrorMessage("产品下架或者已经删除");
		}
		ProductDetailVo productDetailVo= createProductDetailVo(product);
		return Msg.createError(productDetailVo);

	}

	private ProductDetailVo createProductDetailVo(Product item) {
		ProductDetailVo productListVo=new ProductDetailVo();
		productListVo.setId(item.getId());
		productListVo.setMainImage(item.getMainImage());
		productListVo.setCategoryId(item.getCategoryId());
		productListVo.setName(item.getName());
		productListVo.setPrice(item.getPrice());
		productListVo.setSubtitle(item.getSubtitle());
		productListVo.setDetail(item.getDetail());
		productListVo.setStatus(item.getStatus());
		productListVo.setStock(item.getStock());
		productListVo.setSubImages(item.getSubImages());
		Category category = categoryMapper.selectByPrimaryKey(item.getCategoryId());
		if(category==null){
			productListVo.setParentCategoryId(0);
		}else{
			productListVo.setParentCategoryId(category.getParentId());
		}

		return productListVo;

	}

	private ProductSearchVo createProductSearchVo(Product item) {
		ProductSearchVo productListVo=new ProductSearchVo();
		productListVo.setId(item.getId());
		productListVo.setMainImage(item.getMainImage());
		productListVo.setCategoryId(item.getCategoryId());
		productListVo.setName(item.getName());
		productListVo.setPrice(item.getPrice());
		productListVo.setSubtitle(item.getSubtitle());
		return productListVo;
	}

	private ProductListVo createProductListVo(Product item) {
			ProductListVo productListVo=new ProductListVo();
			productListVo.setId(item.getId());
			productListVo.setMainImage(item.getMainImage());
			productListVo.setStatus(item.getStatus());
			productListVo.setCategoryId(item.getCategoryId());
			productListVo.setName(item.getName());
			productListVo.setPrice(item.getPrice());
			productListVo.setSubtitle(item.getSubtitle());

	 return productListVo;
	}
}
