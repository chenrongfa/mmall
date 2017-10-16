package com.yy.mall.controller.back;

import com.yy.mall.common.Constance;
import com.yy.mall.common.Msg;
import com.yy.mall.pojo.User;
import com.yy.mall.service.IUploadFileService;
import com.yy.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Controller
@RequestMapping(value = "/common/")
public class CommonController {
    @Autowired
	IUserService userService;
    @Autowired
	IUploadFileService iUploadFileService;
    @ResponseBody
    @RequestMapping(value = "upload.do",method= RequestMethod.POST)
	public Msg upload(MultipartFile uploadFile, HttpSession session){
		User user = (User) session.getAttribute(Constance.CURRENT_USER);
		if (user == null) {
			return Msg.addSuccessMessage("用户未登录,请登录");
		} else {
			if (!userService.isAdmin(user)) {
				return Msg.addErrorMessage("无权限操作");
			}
		}
		String path = session.getServletContext().getRealPath("img");
		System.out.println(path+"path");
	    Msg msg = iUploadFileService.upload(uploadFile, path);
	    return msg;

	}
}
