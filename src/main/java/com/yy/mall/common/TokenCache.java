package com.yy.mall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenrongfa on 2017/10/10.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
public class TokenCache {
	public static String CACHE_KEY="token_forget";
	private static LoadingCache<String,String> cacheToken= CacheBuilder.newBuilder()
			.expireAfterAccess(120, TimeUnit.SECONDS)
			.maximumSize(1000)
			.build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {
					System.out.println("keyyyy");
					return null;
				}
			});

	public static String getToken(String key){
		try {
			return cacheToken.get(key);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void putToken(String key,String value){

		     cacheToken.put(key,value);

	}

}
