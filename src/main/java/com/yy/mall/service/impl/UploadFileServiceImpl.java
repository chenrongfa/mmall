package com.yy.mall.service.impl;

import com.google.common.collect.Maps;
import com.yy.mall.common.Msg;
import com.yy.mall.service.IUploadFileService;
import com.yy.mall.utils.FtpUtil;
import com.yy.mall.utils.PropertiesUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenrongfa on 2017/10/11.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService{

	public Msg upload(MultipartFile file,String path){
		String filename = file.getOriginalFilename();
		int lastIndexOf = filename.lastIndexOf(".");
		String extendName=null;
		if(lastIndexOf>0){
			extendName=filename.substring(lastIndexOf);
		}
		String targeFilename = UUID.randomUUID().toString()+extendName;
		//验证路径
		File targeFile=new File(path);

		if(!targeFile.exists()){
			targeFile.mkdirs();
			targeFile.setWritable(true);

		}
		try {
			file.transferTo(targeFile);
			//todo上传到 ftp服务器
			FileInputStream in=new FileInputStream(targeFile);
			boolean flag = FtpUtil.uploadFile(PropertiesUtil.getProperty("ftp_host"),
					Integer.parseInt(PropertiesUtil.getProperty("ftp_port")),
					PropertiesUtil.getProperty
							("ftp_username"), PropertiesUtil.getProperty("ftp_password"),
					"", PropertiesUtil.getProperty("ftp_baseDir"),targeFilename, in);
			//todo 删除tomcat 服务器的文件
			if(flag){
				targeFile.delete();
				Map<String ,String> data= Maps.newHashMap();
				data.put("uri",targeFilename);
				data.put("url","http://"+PropertiesUtil.getProperty("ftp_host")
						+":8080"+PropertiesUtil.getProperty("ftp_baseDir")+"/"+targeFilename);
				Msg<Map<String, String>> success = Msg.createSuccess(data);

				return success;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Msg.addErrorMessage("上传失败");
	}

}
