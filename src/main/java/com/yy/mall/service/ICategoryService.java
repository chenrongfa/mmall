package com.yy.mall.service;/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */

import com.yy.mall.common.Msg;
import com.yy.mall.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */

public interface ICategoryService {
	/**
	 *
	 * @param parentId 通过parentId 查找种类
	 * @return
	 */
	Msg<List<Category>> findCategory(int parentId);

	/**
	 *  创建 分类节点
	 * @param parentId
	 * @param categoryName
	 * @return
	 */

	Msg createCategory(int parentId, String categoryName);

	Msg updateCategory(int categoryId, String categoryName);

	Msg<List<Category>> findDeepCategory(int categoryId);
}
