package com.yy.mall.service.impl;

import com.yy.mall.common.Msg;
import com.yy.mall.dao.CategoryMapper;
import com.yy.mall.pojo.Category;
import com.yy.mall.pojo.User;
import com.yy.mall.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service(value = "iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	CategoryMapper categoryMapper;
	public Msg<List<Category>> findCategory(int parentId) {
	List<Category> categorys=categoryMapper.findCategoryByParentId(parentId);
	if(categorys==null||categorys.size()==0){
		Msg msg = Msg.addSuccessMessage("数据为空");
		return msg;
	}

	Msg<List<Category>> msg = Msg.createSuccess(categorys);

		return msg;
	}
	public Msg createCategory(int parentId, String categoryName) {
		Category category=new Category();
		category.setParentId(parentId);
		category.setName(categoryName);
		category.setCreateTime(new Date());
		category.setUpdateTime(new Date());
		int success = categoryMapper.insertSelective(category);
		if(success==0){
			return Msg.addErrorMessage("添加种类失败");
		}
		return Msg.addSuccessMessage("添加成功");
	}

	public Msg updateCategory(int categoryId, String categoryName) {
		Category category = categoryMapper.selectByPrimaryKey(categoryId);
		if(category==null){
			return Msg.addErrorMessage("categoryId 没有找到");
		}
		category.setName(categoryName);
		int update = categoryMapper.updateByPrimaryKey(category);
		if(update==0){
			return Msg.addErrorMessage("修改失败");
		}
		return Msg.addSuccessMessage("修改成功");
	}

	public Msg<List<Category>> findDeepCategory(int categoryId) {
		List<Category> categorys=new LinkedList<Category>();
		List<Category> categories = deepCategory(categorys, categoryId);
		if(categories.size()==0){
			return Msg.addSuccessMessage("没有数据");
		}
		Msg<List<Category>> msg = Msg.createSuccess(categories);

		return msg;
	}

	private List<Category>  deepCategory(List<Category> categorys, int categoryId) {
		Category category = categoryMapper.selectByPrimaryKey(categoryId);
		if(category!=null){
			categorys.add(category);
		}
		List<Category> categoryList = categoryMapper.findCategoryByParentId(categoryId
				);
		if(categoryList!=null){

		for(Category cate:categoryList){
			deepCategory(categorys,cate.getId());
		}
		}
		return categorys;
	}
}
