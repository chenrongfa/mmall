package com.yy.mall.controller.portal;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by chenrongfa on 2017/10/13.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function: 测试是否连接外网
 */
@Controller

public class TestController {
	@ResponseBody
	@RequestMapping(value = "/test.do"  )
	public Object test()  {

		return "success";
	}
}
