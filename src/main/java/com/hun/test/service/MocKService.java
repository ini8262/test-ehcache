package com.hun.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hun.test.domain.User;

@Service
public class MocKService {
	
	public List<User> getUsers() {
		
		List<User> list = new ArrayList<User>();
		
		for (int i = 0; i < 5; i++) {
			User user = new User();
			
			user.setId("id" + i);
			user.setAddr("addr" + i);
			user.setPhone("phone" + i);
			user.setAge("age" + i);
			
			list.add(user);
			System.out.println("sleep...");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
}
