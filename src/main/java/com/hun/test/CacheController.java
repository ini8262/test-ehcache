package com.hun.test;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hun.test.domain.User;
import com.hun.test.service.MocKService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@RestController
@Controller
@RequestMapping(value = "/cache/*")
public class CacheController {
	
	@Autowired
	MocKService service;
	
	@RequestMapping(value = "put", method = RequestMethod.GET)
	public User put(@RequestParam String key, User user) {
		
		CacheManager cacheManager = CacheManager.create();
		Cache cache = cacheManager.getCache("simpleBeanCache");
		
		Element newElement = new Element(key, user);
		cache.put(newElement);
		
		return user;
		
	}
	
	@RequestMapping(value = "get", method = RequestMethod.GET)
	public User get(@RequestParam String key) {
		
		CacheManager cacheManager = CacheManager.create();
		Cache cache = cacheManager.getCache("simpleBeanCache");
		
		Element element = cache.get(key);
		
		try {
			User user = (User) element.getObjectValue();
			System.out.println(user);
			return user;
			
		} catch (NullPointerException e) {
			System.out.println("null");
			return null;
		}
		
	}
	
	@RequestMapping(value = "remove/{key}", method = RequestMethod.GET)
	public boolean remove(@PathVariable String key) {
		
		CacheManager cacheManager = CacheManager.create();
		Cache cache = cacheManager.getCache("simpleBeanCache");
		
		boolean deleted = cache.remove(key);
		
		System.out.println(deleted);
		
		return deleted;
	}
	
	@RequestMapping(value = "shutdown", method = RequestMethod.GET)
	public void shutdown() {
		CacheManager cacheManager = CacheManager.create();
		cacheManager.shutdown();
	}
	
	@RequestMapping(value = "io", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public List<User> io() {
		final String IO_KEY = "io";
		
		CacheManager cacheManager = CacheManager.create();
		Cache cache = cacheManager.getCache("simpleBeanCache");
		
		Element element = cache.get(IO_KEY);
		
		if (element == null) {

			List<User> list = service.getUsers();
			
			element = new Element(IO_KEY, list);
			cache.put(element);
			
			return list;
		} else {
			List<User> list = (List<User>) element.getObjectValue();
			return list;
		}
		
	}

}
