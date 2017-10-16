package com.yy.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.yy.mall.common.Msg;
import com.yy.mall.dao.CartMapper;
import com.yy.mall.dao.ProductMapper;
import com.yy.mall.pojo.Cart;
import com.yy.mall.pojo.Product;
import com.yy.mall.service.ICartService;
import com.yy.mall.utils.BigDecimalUtil;
import com.yy.mall.vo.CartProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/12.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	CartMapper cartMapper;

	@Autowired
	ProductMapper productMapper;

	/**
	 * 通过usrId 查询购物车的信息
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public Msg<CartProductVo> getCartList(Integer pageNum, Integer pageSize,
	                                      Integer userId) {
		if (userId == null) {
			return Msg.addErrorMessage("用户错误");
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Cart> carts = cartMapper.getCartList(userId);
		if (carts.size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		CartProductVo cartProductVo = createCartProduct(carts);
		if (cartProductVo.getCartProductVoList().size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		return Msg.createSuccess(cartProductVo);
	}

	public Msg<CartProductVo> addCart(Integer userId, Integer productId, Integer count) {
		if (userId == null) {
			return Msg.addErrorMessage("用户错误");
		}
		if (productId == null || count == null) {
			return Msg.addErrorMessage("参数错误");
		}
		/**
		 *  有就+1 没有就创建
		 */
		Cart produce = cartMapper.getCartByUserIdAndProductId(userId, productId);
		if(produce==null) {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setProductId(productId);
			cart.setChecked(1);
			int insert = cartMapper.insert(cart);
			if (insert == 0) {
				return Msg.addErrorMessage("添加失败");
			}

			System.out.println(cart.toString());
			return updateCount(userId, productId, count);
		}else{

			return updateCount(userId,productId,1);
		}

	}

	public Msg<CartProductVo> updateCount(Integer userId, Integer productId, Integer count) {
		if (userId == null) {
			return Msg.addErrorMessage("用户错误");
		}
		if (productId == null || count == null || count < 0) {
			return Msg.addErrorMessage("参数错误");
		}
		Cart cart = cartMapper.getCartByUserIdAndProductId(userId, productId);

		if (cart == null) {
			return Msg.addErrorMessage("购物车没有这样商品");
		}
		Product product = productMapper.selectByPrimaryKey(productId);
		if (product == null) {
			return Msg.addErrorMessage("获取商品错误或者没有这个商品");
		}
		Integer stock = product.getStock();
		Integer quantity = cart.getQuantity();
		if(quantity==null){
			quantity=0;
		}
		int finalcount = quantity + count;
		if (stock < finalcount) {
			return Msg.addSuccessMessage("已经超过了库存");
		}
		cart.setQuantity(finalcount);
		int update = cartMapper.updateByPrimaryKey(cart);
		if (update == 0) {
			return Msg.addErrorMessage("增加失败");
		}

		List<Cart> carts = cartMapper.getCartList(userId);
		if (carts.size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		CartProductVo cartProductVo = createCartProduct(carts);
		if (cartProductVo.getCartProductVoList().size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		return Msg.createSuccess(cartProductVo);
	}

	public Msg<CartProductVo> deleteCart(Integer userId, String productIds) {
		if (userId == null) {
			return Msg.addErrorMessage("用户错误");
		}
		String[] splitIds = null;
		try {
			splitIds = productIds.split(",");
		} catch (Exception e) {

			return Msg.addErrorMessage("格式错误");
		}
		int delete = cartMapper.deleteCartByProduct(userId, Arrays.asList(splitIds));
		if (delete == 0) {
			return Msg.addErrorMessage("删除失败或者商品不存在");
		}

		List<Cart> carts = cartMapper.getCartList(userId);
		if (carts.size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		CartProductVo cartProductVo = createCartProduct(carts);
		if (cartProductVo.getCartProductVoList().size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		return Msg.createSuccess(cartProductVo);

	}

	public Msg<CartProductVo> selectCart(Integer userId, Integer productId, Integer ckecked) {
		if (userId == null) {
			return Msg.addErrorMessage("用户错误");
		}
		if (productId == null) {
			return Msg.addErrorMessage("参数错误");
		}
		int update = cartMapper.updateCartCheckedByUserIdAndProduce(userId, productId, ckecked);
		if (update == 0) {
			return Msg.addErrorMessage("修改失败");
		}

		List<Cart> carts = cartMapper.getCartList(userId);
		if (carts.size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		CartProductVo cartProductVo = createCartProduct(carts);
		if (cartProductVo.getCartProductVoList().size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		return Msg.createSuccess(cartProductVo);

	}

	public Msg<CartProductVo> selectAllCart(Integer userId, Integer ckecked) {
		if (userId == null) {
			return Msg.addErrorMessage("用户错误");
		}


		List<Integer> productIds = cartMapper.getCartProductIds(userId);

		if (productIds.size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}

		int count = cartMapper.updateCartCheckedByProductIds(productIds, ckecked);


		CartProductVo cartProductVo = createCartProduct(cartMapper.getCartList(userId));
		if (cartProductVo.getCartProductVoList().size() == 0) {
			return Msg.addSuccessMessage("购物车为空");
		}
		return Msg.createSuccess(cartProductVo);


	}

	private CartProductVo createCartProduct(List<Cart> carts) {
		CartProductVo cartProductVo = new CartProductVo();
		List<CartProductVo.CartProductVoListBean> cartProductVoListBeans = Lists.newArrayList();
		BigDecimal cartTotalPrice = new BigDecimal("0");
		for (Cart item : carts) {
			CartProductVo.CartProductVoListBean cartBean = new CartProductVo
					.CartProductVoListBean();
			Integer quantity = item.getQuantity();
			if(quantity==null){
				quantity=0;
			}
			cartBean.setId(item.getId());
			cartBean.setUserId(item.getUserId());
			Integer checked = item.getChecked();
			if(checked==null){
				checked=0;
			}
			cartBean.setProductChecked(checked);
			cartBean.setProductId(item.getProductId());


			Product product = productMapper.selectByPrimaryKey(item.getProductId());
			int maxQuaulity = 0;
			if (product != null) {
				cartBean.setProductName(product.getName());
				cartBean.setProductMainImage(product.getMainImage());
				cartBean.setProductStatus(product.getStatus());
				cartBean.setProductStock(product.getStock());
				if (product.getStock() >= quantity) {
					maxQuaulity = quantity;
					cartBean.setLimitQuantity("LIMIT_NUM_SUCCESS");
				} else {
					maxQuaulity = product.getStock();
					cartBean.setLimitQuantity("LIMIT_NUM_FAIL");
				}

				cartBean.setProductSubtitle(product.getSubtitle());
				cartBean.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue()
						, maxQuaulity));
				if (cartBean.getProductChecked() == 1) {
					cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),
							cartBean.getProductTotalPrice().doubleValue());
				}
				cartProductVoListBeans.add(cartBean);
			}
			cartBean.setQuantity(maxQuaulity);

		}
		cartProductVo.setCartProductVoList(cartProductVoListBeans);
		cartProductVo.setCartTotalPrice(cartTotalPrice);
		int select = cartMapper.isAllSelect(carts.get(0).getUserId());
		cartProductVo.setAllChecked(select > 0 ? false : true);
		return cartProductVo;

	}
}
