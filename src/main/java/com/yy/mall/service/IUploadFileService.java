package com.yy.mall.service;

import com.yy.mall.common.Msg;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public interface IUploadFileService {
	public Msg upload(MultipartFile file, String path);
}
