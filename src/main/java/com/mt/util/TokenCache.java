package com.mt.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.log4j.Log4j;


/**
 *@Title 关于问题的缓存类
 *@Author TomcatBbzzzs
 *@Desctiption 主要用于存放问题答案的有效时长,当超过这个时间后,必须重新加载
 *@Date 2019/2/6 12:20:12
 *由Guava提供,使用Tomcat集群就不需要使用这个了
 */
@Log4j
public class TokenCache {
	//身份标识
	public static final String TOKEN_PREFIX="token_";
	//超时事件
	public static final Integer TOKEN_TIMEOUT=5;
	
	//设置缓存的初始化容量为1000,最大缓存为100000,当超过这个容量后,guava将使用LRU算法移除这个缓存项,缓存有效期为12小时
	private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().
			initialCapacity(1000).maximumSize(10000).expireAfterAccess(TOKEN_TIMEOUT, TimeUnit.MINUTES)
			.build(new CacheLoader<String, String>() {
				/*默认的数据加载实现,当调用get取值的时候,如果key没有对
 				应的值,则调用这个方法进行加载*/
				public String load(String s) throws Exception {
					return "null";
				}
			});
	
	/**存放缓存到guava中*/
	public static void setKey(String key, String value) {
		localCache.put(key, value);
	}
	
	/**读取缓存*/
	public static String getKey(String key) {
		String value = null;
		try {
			value = localCache.get(key);
			if ("null".equals(value)) {
				return null;
			}
			return value;
		} catch (Exception e) {
			log.error("guava缓存读取失败",e);
		}
		return null;
	}
	
	/**清除缓存*/
	public static void clearKey(String key) {
		localCache.invalidate(key);
	}
	
	
}

