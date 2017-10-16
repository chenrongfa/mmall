package com.yy.mall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IAlipayService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.krb5.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by chenrongfa on 2017/10/13.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function: 支付宝
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController {
	private static org.slf4j.Logger log= LoggerFactory.getLogger(AlipayController.class);
	@Autowired
	IAlipayService ilIAlipayService;
	@ResponseBody
	@RequestMapping(value = "/pay.do")
	public Msg pay(HttpSession session, Long orderNum, HttpServletRequest request){

		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}
		String path = request.getRealPath("img");
		return ilIAlipayService.pay(user.getId(),orderNum,path);
	}

	@ResponseBody
	@RequestMapping(value = "/pay_callback.do",produces = MediaType.TEXT_PLAIN_VALUE)
	public String pay_callbak(HttpSession session,  HttpServletRequest request){
		Map<String, String[]> parameterMap = request.getParameterMap();
		if(parameterMap.size()==0){
			System.out.println(1222);
			return "参数为空";
		}



		Map<String,String> param= Maps.newHashMap();
		for(String key:parameterMap.keySet()){
			System.out.println(key+" key");
			String values[]=parameterMap.get(key);
			StringBuffer trim=new StringBuffer("");

			for(int i=0;i<values.length;i++){
				 trim =( i == (values.length - 1)) ? trim.append(values[i]) :
						trim.append(values[i] + ",");
			}
			System.out.println(trim.toString());
			param.put(key,trim.toString());

		}
		Configs.init("zfbinfo.properties");
		  param.remove("sign_type");
		try {
			System.out.println("zfbinfo.properties");
			boolean checkV2 = AlipaySignature.rsaCheckV2(param, Configs.getAlipayPublicKey(),
					"utf-8"
					, Configs.getSignType());
			if(!checkV2){
				log.error( "sign 验证失败");
				return  "sign 验证失败";
			}
			// 处理业务

			Msg msg=ilIAlipayService.checkData(param);

			if(msg.isSuccess()){

				return "success";
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			System.out.println("fail");
			log.error( "验证异常");
			return  " 验证异常";
		}
		return "failed";
	}
	@ResponseBody
	@RequestMapping("/pay_query.do")
	public Msg payQuery(HttpSession session,  Long orderNum){

		User user= (User) session.getAttribute(Constance.CURRENT_USER);
		if(user==null){
			return Msg.addErrorMessage("当前用户未登录,请登录");
		}

		Msg msg=ilIAlipayService.query(user.getId(),orderNum);
		return msg;
	}



}
