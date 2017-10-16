package com.yy.mall.utils;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
/**
 *  id生成器
 * @author crf
 *
 */
public class IdUtils {
 private static IdUtils upload=new IdUtils();
	
	private IdUtils(){};
	
	public static IdUtils getinstance(){
		return upload;
	}
	/**
	 *  图片id
	 * @return
	 */
	
	public String makeImageId(){
		long timeMillis = System.currentTimeMillis();
		String timestamp = UUID.randomUUID().toString();
		String randomId="";
		
		Random random=new Random();
		for(int i=0;i<5;i++){
			int nextInt = random.nextInt(timestamp.length()-1);
			randomId+=timestamp.substring(nextInt, nextInt+1);
		}
		String id=timeMillis+randomId;
		
		
		return id;
	}
	/**
	 *  商品 id
	 * @return
	 */
	public long makeItemId(){
		long timeMillis = System.currentTimeMillis();
		/*long timestamp = UUID.randomUUID().timestamp();*/
		String randomId="";
		
		Random random=new Random();
		for(int i=0;i<5;i++){			int nextInt = random.nextInt(1000);
			randomId+=randomId;
		}
		String id=timeMillis+randomId;
		
		System.out.println("id"+id);
		return Long.parseLong(id);
	}
}
