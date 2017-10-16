package com.yy.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.dao.*;
import com.yy.mall.pojo.*;
import com.yy.mall.service.IOrderService;
import com.yy.mall.utils.BigDecimalUtil;
import com.yy.mall.utils.IdUtils;
import com.yy.mall.utils.PropertiesUtil;
import com.yy.mall.vo.OrderItemVo;
import com.yy.mall.vo.OrderProductVo;
import com.yy.mall.vo.OrderVo;
import com.yy.mall.vo.ShippingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by chenrongfa on 2017/10/14.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ShippingMapper shippingMapper;

    public Msg<OrderVo> create(Integer userId, Integer shippingId) {

        List<Cart> carts = cartMapper.selectCheckByUserId(userId);
        if (carts.size() == 0) {
            return Msg.addSuccessMessage("选购为空");
        }

        Msg msg = createOrderItem(userId, carts);
        if (!msg.isSuccess()) {
            return Msg.addErrorMessage("创建订单详细失败");
        }
        List<OrderItem> items = (List<OrderItem>) msg.getData();
        BigDecimal payNum = getTotalPrice(items);
        Order order = createOrder(userId, shippingId, payNum);
        if (order == null) {
            return Msg.addErrorMessage("创建订单失败");
        }
        for (OrderItem item : items) {
            item.setOrderNo(order.getOrderNo());

        }
        orderItemMapper.batchInsert(items);

        //清除购物车
        clearCart(carts);
        //拼装 orderVo
        OrderVo orderVo = createOrderVo(order, items);
        return Msg.createSuccess(orderVo);
    }

    public Msg<OrderProductVo> getOrderInfo(Integer userId) {

        List<Order> orders = orderMapper.selectOrderByUserId(userId);
        OrderProductVo orderProductVo = new OrderProductVo();
        if (orders.size() == 0) {
            return Msg.addErrorMessage("订单为空");
        }
        List<OrderItemVo> orderItemVos = createOrderItemVo(userId, orders);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("host"));

        BigDecimal totalPrice = getTotalPriceByOrderItemVo(orderItemVos);
        orderProductVo.setProductTotalPrice(totalPrice);
        orderProductVo.setOrderItemVoLists(orderItemVos);


        return Msg.createSuccess(orderProductVo);
    }

    public Msg cancelOrderByOrderNum(Integer id, Long orderNum) {
        Order order = orderMapper.selectOrderByOrderNum(orderNum);
        if (order == null) {
            return Msg.addErrorMessage("没有该订单  ");

        }
        order.setStatus(Constance.OrderStatus.CANNAL.getStatus());
        // todo 优化 sql
        orderMapper.updateByPrimaryKeySelective(order);
        return Msg.addSuccessMessage("修改成功");
    }

    public Msg updateSend(Integer id, Long orderNum) {
        Order order = orderMapper.selectOrderByOrderNum(orderNum);
        if (order == null) {
            return Msg.addErrorMessage("没有该订单  ");

        }
        // todo 优化 sql
        if (order.getStatus() < Constance.OrderStatus.OFFERED.getStatus()) {
            return Msg.addErrorMessage("还未支付不能发货");

        }
        order.setSendTime(new Date());
        int i = orderMapper.updateByPrimaryKeySelective(order);
        if (i == 0) {
            Msg.addErrorMessage("发货失败");
        }
        return Msg.addSuccessMessage("发货成功");

    }

    public Msg<OrderVo> getOrderDetail(Integer userId, long orderNum) {
        Order order = orderMapper.selectOrderByOrderNum(orderNum);
        if (order == null) {
            return Msg.addErrorMessage("没有该订单  ");

        }

        List<OrderItem> orderItems = orderItemMapper.selectList(userId, orderNum);

        OrderVo orderVo = createOrderVo(order, orderItems);
        return Msg.createSuccess(orderVo);
    }

    public Msg<PageInfo> getOrderlistInfo(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectOrderByUserId(userId);
        List<OrderVo> orderVos = Lists.newArrayList();
        if (orders.size() == 0) {
            return Msg.addErrorMessage("订单 为空");
        }
        for (Order order : orders) {

            List<OrderItem> orderItems = orderItemMapper.selectList(userId, order.getOrderNo());
            if (orderItems.size() == 0) {
                continue;
            }
            OrderVo vo = createOrderVo(order, orderItems);
            orderVos.add(vo);
        }
        PageInfo pageInfo = new PageInfo(orders);
        pageInfo.setList(orderVos);
        return Msg.createSuccess(pageInfo);
    }

    private BigDecimal getTotalPriceByOrderItemVo(List<OrderItemVo> orderItemVos) {
        BigDecimal total = new BigDecimal("0");
        for (OrderItemVo item : orderItemVos) {
            total = BigDecimalUtil.add(total.doubleValue(), item.getTotalPrice().doubleValue());
        }
        return total;

    }

    private List<OrderItemVo> createOrderItemVo(Integer userId, List<Order> orders) {
        List<OrderItemVo> ordrOrderItemVos = Lists.newArrayList();
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemMapper.selectList(userId, order.getOrderNo());

            for (OrderItem orderItem : orderItems) {
                OrderItemVo orderItemVo = new OrderItemVo();
                putOrderItemInfo(orderItem, orderItemVo);
                ordrOrderItemVos.add(orderItemVo);


            }
        }

        return ordrOrderItemVos;
    }

    private OrderVo createOrderVo(Order order, List<OrderItem> items) {
        OrderVo orderVo = new OrderVo();
        orderVo.setShippingId(order.getShippingId());
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setCloseTime(order.getCloseTime());
        orderVo.setCreateTime(order.getCreateTime());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setPayment(order.getPayment().doubleValue());
        orderVo.setPaymentTime(order.getPaymentTime());
        orderVo.setPostage(order.getPostage());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setSendTime(order.getSendTime());
        orderVo.setStatus(order.getStatus());
        List<OrderItemVo> orderITemVos = Lists.newArrayList();
        for (OrderItem orderItem : items) {
            OrderItemVo orderItemVo = new OrderItemVo();
            putOrderItemInfo(orderItem, orderItemVo);
            orderITemVos.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderITemVos);
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        ShippingVo shippingVo = null;
        if (shipping != null) {
            shippingVo = new ShippingVo();
            shippingVo.setReceiverAddress(shipping.getReceiverAddress());
            shippingVo.setReceiverCity(shipping.getReceiverCity());
            shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
            shippingVo.setReceiverMobile(shipping.getReceiverMobile());
            shippingVo.setReceiverName(shipping.getReceiverName());
            shippingVo.setReceiverPhone(shipping.getReceiverPhone());
            shippingVo.setReceiverProvince(shipping.getReceiverProvince());
            shippingVo.setReceiverZip(shipping.getReceiverZip());
        }

        orderVo.setShippingVo(shippingVo);
        return orderVo;
    }

    private void putOrderItemInfo(OrderItem orderItem, OrderItemVo orderItemVo) {
        orderItemVo.setCreateTime(orderItem.getCreateTime());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice().doubleValue());
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
    }

    private void clearCart(List<Cart> carts) {
        for (Cart cart : carts) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }

    }

    private Order createOrder(Integer userId, Integer shippingId, BigDecimal payNum) {
        Order order = new Order();
        order.setPayment(payNum);
        order.setUserId(userId);
        order.setStatus(Constance.OrderStatus.WAITPAY.getStatus());
        order.setPostage(0);
        order.setPaymentType(1);
        order.setOrderNo(IdUtils.getinstance().makeItemId());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setShippingId(shippingId);
        int insertSelective = orderMapper.insertSelective(order);
        if (insertSelective > 0) {
            return order;
        }

        return null;
    }

    private BigDecimal getTotalPrice(List<OrderItem> items) {
        BigDecimal total = new BigDecimal("0");
        for (OrderItem item : items) {
            total = BigDecimalUtil.add(total.doubleValue(), item.getTotalPrice().doubleValue());
        }
        return total;
    }

    private Msg<List<OrderItem>> createOrderItem(Integer userId, List<Cart> carts) {
        List<OrderItem> orderItems = Lists.newArrayList();
        for (Cart cart : carts) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(userId);
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());
            if (product.getStatus() != 1) {
                return Msg.addErrorMessage("该商品已经下架");
            }
            if (cart.getQuantity() > product.getStock()) {
                return Msg.addErrorMessage("已经超过了库存");
            }
            orderItem.setProductImage(product.getMainImage());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setProductName(product.getName());
            orderItem.setProductId(product.getId());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cart
                    .getQuantity()));

            orderItems.add(orderItem);
            product.setStock(product.getStock() - cart.getQuantity());
            //todo 优化
            int update = productMapper.updateByPrimaryKeySelective(product);
            if (update == 0) {
                return Msg.addErrorMessage("库存发生错误");
            }
        }
        return Msg.createSuccess(orderItems);
    }
}
