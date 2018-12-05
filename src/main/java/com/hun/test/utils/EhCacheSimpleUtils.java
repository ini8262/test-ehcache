package com.hun.test.utils;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Cache 도구들
 * @author ThinkGem
 * @version 2013-5-29
 */

public class EhCacheSimpleUtils {

	private static CacheManager cacheManager; 
	
	private static final String SYS_CACHE = "simpleBeanCache";

	/**
	 * SYS_CACHE 캐시 가져오기
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	/**
	 * SYS_CACHE 캐시 쓰기
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}
	
	/**
	 * SYS_CACHE 캐시에서 제거
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	/**
	 * 캐시 가져 오기
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element==null?null:element.getObjectValue();
	}

	/**
	 * 캐시 쓰기
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 캐시에서 제거
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * Cache 얻기，없을경우 하나 만들기
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		
		if (cacheManager == null) {
			synchronized (CacheManager.class) {
				if (cacheManager == null)
					cacheManager = CacheManager.create();
			}
		}
		return cacheManager;
	}
	
	
}
