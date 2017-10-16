package com.yy.mall.service.impl;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.dao.OrderItemMapper;
import com.yy.mall.dao.OrderMapper;
import com.yy.mall.dao.PayInfoMapper;
import com.yy.mall.pojo.OrderItem;
import com.yy.mall.pojo.PayInfo;
import com.yy.mall.service.IAlipayService;
import com.yy.mall.utils.BigDecimalUtil;
import com.yy.mall.utils.FtpUtil;
import com.yy.mall.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static com.yy.mall.utils.FtpUtil.uploadFile;

/**
 * Created by chenrongfa on 2017/10/13.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service
public class AlipayServiceImpl implements IAlipayService {

	// 支付宝当面付2.0服务
	private static AlipayTradeService tradeService;



	static {
		/** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
		 *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
		 */
		Configs.init("zfbinfo.properties");

		/** 使用Configs提供的默认参数
		 *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
		 */
		tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
	}
		private static Logger log= LoggerFactory.getLogger(AlipayServiceImpl.class);
	@Autowired
	OrderMapper orderMapper;

	@Autowired
	OrderItemMapper orderItemMapper;

	@Autowired
	PayInfoMapper payInfoMapper;

	public Msg pay(Integer userId,Long orderNum,String path){
		if(orderNum==null){
			return Msg.addErrorMessage("参数传递错误");
		}
		if(userId==null){
			return Msg.addErrorMessage("用户名未分配请稍后重试");
		}
	com.yy.mall.pojo.Order order=orderMapper.selectOrderByUserIdOrderNum(userId,orderNum);
	if(order==null){
		return Msg.addErrorMessage("没用该订单");
	}

    return trade_pay(tradeService,order,path);
	}

	public Msg query(Integer id, Long orderNum) {
		com.yy.mall.pojo.Order order = orderMapper.selectOrderByUserIdOrderNum(id, orderNum);
		if(order==null){
			return Msg.addErrorMessage("本商城没有这个订单号");
		}

		return Msg.createError(order.getStatus());
	}

	public Msg checkData(Map<String, String> param) {
		String outTradeNo = param.get("out_trade_no");
		String status=param.get("trade_status");
		String tradeNo=param.get("trade_no");
		com.yy.mall.pojo.Order order = orderMapper.selectOrderByOrderNum(Long
				.parseLong(outTradeNo));
		if(order==null){
			return Msg.addErrorMessage("本商城没有这个订单号");
		}
		if(order.getStatus()> Constance.OrderStatus.OFFERED.getStatus()){
			return Msg.addErrorMessage("不需要处理重复操作");
		}
		if(status.equals(Constance.OrderPayStatus.TRADE_SUCCESS)){
			//已经付款回调过来 更新order 表和pay_info表
			order.setStatus(Constance.OrderStatus.OFFERED.getStatus());
			order.setPaymentTime(new Date());
			order.setUpdateTime(new Date());
			int orderMapperUpdate = orderMapper.updateByPrimaryKeySelective(order);
			PayInfo payInfo=payInfoMapper.selectPayInfoByUserIdOrderNum(order.getUserId(),Long
					.parseLong
				(outTradeNo));

		   /*if(payInfo!=null){
		   	  payInfo.setPlatformStatus(status);
		   	  payInfo.setUpdateTime(new Date());
		   	  payInfo.setUpdateTime(new Date());
		   	  payInfo.setPayPlatform(1);
		   }
			int payInfoMapperUpdate = payInfoMapper.updateByPrimaryKeySelective(payInfo);
			if(orderMapperUpdate>0&&payInfoMapperUpdate>0){
				return Msg.addSuccessMessage("支付成功");
			}
		   return Msg.addErrorMessage("操作失败");*/
		}

		PayInfo payInfo=new PayInfo();
		payInfo.setUpdateTime(new Date());
		payInfo.setCreateTime(new Date());
		payInfo.setPlatformStatus(status);
		payInfo.setOrderNo(Long.parseLong(outTradeNo));
		payInfo.setPlatformNumber(tradeNo);
		payInfo.setUserId(order.getUserId());
		int payinfoUpdate = payInfoMapper.insertSelective(payInfo);
		if(payinfoUpdate>0){
			return Msg.addSuccessMessage("预下单成功");
		}
		return Msg.addErrorMessage();
	}

	public Msg trade_pay(AlipayTradeService service, com.yy.mall.pojo.Order order,String path) {
		// (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
		// 需保证商户系统端不能重复，建议通过数据库sequence生成，
		String outTradeNo = order.getOrderNo().toString();

		// (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
		String subject = "你在本店消费 :"+ order.getPayment().toString();

		// (必填) 订单总金额，单位为元，不能超过1亿元
		// 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
		String totalAmount = order.getPayment().toString();

		// (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
		String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
		// (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
		// 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
		//        String discountableAmount = "1.00"; //

		// (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
		// 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
		String undiscountableAmount = "0.0";

		// 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
		// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
		String sellerId = "";
		// 商户操作员编号，添加此参数可以为商户操作员做销售统计
		String operatorId = "test_operator_id";

		// (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
		String storeId = "test_store_id";
		// 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"

		String body = "购买商品%d件共%s元";

		// 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
		String providerId = "2088100200300400500";
		ExtendParams extendParams = new ExtendParams();
		extendParams.setSysServiceProviderId(providerId);

		// 支付超时，线下扫码交易定义为5分钟
		String timeoutExpress = "5m";

		// 商品明细列表，需填写购买商品详细信息，
		List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
		List<OrderItem> orderItemlist=orderItemMapper.selectList(order.getUserId(),order
				.getOrderNo
						());
		int count =0;
		for(OrderItem item:orderItemlist){
   // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
			GoodsDetail goods1 = GoodsDetail.newInstance(item.getProductId().toString(), item.getProductName(),
					BigDecimalUtil.mul(item.getCurrentUnitPrice().doubleValue(),100.0).longValue(),
					item.getQuantity());
			// 创建好一个商品后添加至商品明细列表
			goodsDetailList.add(goods1);
			count++;
		}
		body=String.format(body,count,totalAmount+"");



		String appAuthToken = "应用授权令牌";//根据真实值填写

		// 创建条码支付请求builder，设置请求参数
		// 创建扫码支付请求builder，设置请求参数
		AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
				.setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
				.setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
				.setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
				.setTimeoutExpress(timeoutExpress)
				       .setNotifyUrl("http://f4hx87.natappfree.cc/alipay/pay_callback.do")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
				.setGoodsDetailList(goodsDetailList);

		AlipayF2FPrecreateResult result = service.tradePrecreate(builder);
		switch (result.getTradeStatus()) {
			case SUCCESS:
				log.info("支付宝预下单成功: )");

				AlipayTradePrecreateResponse response = result.getResponse();
				/*dumpResponse(response);*/

				// 需要修改为运行机器上的路径
				String fileName = String.format("qr-%s.png",
						response.getOutTradeNo());
				log.info("filePath:" + fileName);
				File file=new File(path);
				if(!file.exists()){
					file.mkdirs();
					file.setWritable(true);
				}
				String filePath=file.getAbsolutePath()+"/"+fileName;
				File codeImge = ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
				FileInputStream in= null;
				try {

					in = new FileInputStream(codeImge);
					boolean flag = uploadFile(PropertiesUtil.getProperty("ftp_host"),
							Integer.parseInt(PropertiesUtil.getProperty("ftp_port")),
							PropertiesUtil.getProperty
									("ftp_username"), PropertiesUtil.getProperty("ftp_password"),
							"", PropertiesUtil.getProperty("ftp_qrImage"),fileName, in);
					if(!flag){
						return Msg.addErrorMessage("二维码没有上传成功");
					}

					Map resultMap=new HashMap();
					resultMap.put("orderNo",order.getOrderNo());
					resultMap.put("qrPath", PropertiesUtil.getProperty("ftp_host_qrImageUrl")
							+"/"+fileName);
					return Msg.createSuccess(resultMap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return Msg.addErrorMessage("二维码没有上传成功");
				}



			case FAILED:
				log.error("支付宝预下单失败!!!");
				return Msg.addErrorMessage("支付宝预下单失败");


			case UNKNOWN:
				log.error("系统异常，预下单状态未知!!!");
				return Msg.addErrorMessage("系统异常，预下单状态未知!!!");

			default:
				log.error("不支持的交易状态，交易返回异常!!!");
				return Msg.addErrorMessage("不支持的交易状态，交易返回异常!!!");
		}

	}

	public static void main(String[] args) {
		String body = "购买商品%d件共%s元";
		String format = String.format(body, null, "你好");
		System.out.println(format);
	}
}
