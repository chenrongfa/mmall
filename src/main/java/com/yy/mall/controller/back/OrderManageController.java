package com.yy.mall.controller.back;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IOrderService;
import com.yy.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by crf on 2017/10/16
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {
    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;
    @ResponseBody
    @RequestMapping("send.do")
    public Msg sendOrderProduct(HttpSession session,Long orderNum){
        User user = (User) session.getAttribute(Constance.CURRENT_USER);
        if (user == null) {
            return Msg.addSuccessMessage("用户未登录,请登录");
        } else {
            if (!userService.isAdmin(user)) {
                return Msg.addErrorMessage("无权限操作");
            }
        }
        Msg msg=  orderService.updateSend(user.getId(),orderNum);
        return msg;
    }
}
