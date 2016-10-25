package com.appleframework.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.appleframework.async.annotation.AppleAsync;

@Service
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(TeacherService.class);
	
	@AppleAsync
	public User addUser(User user){
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		logger.info("正在添加用户{}",user.getName());
		System.out.println("正在添加用户{}" + user.getName());
		
		return user;
	}
}
