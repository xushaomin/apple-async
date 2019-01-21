package com.appleframework.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/config/spring.xml" })
public class AysncTest {
	
	private final static Logger logger = LoggerFactory.getLogger(AysncTest.class);
	
	@Autowired
	private UserService userService;
		
	@Test
	public void testAsyncAnnotation(){
		long t = System.currentTimeMillis();
		User user1 = userService.addUser(new User(34,"李一"));
		User user2 = userService.addUser(new User(35,"李二"));
		logger.info("异步任务已执行");
		logger.info("执行结果  任务1：{}  任务2：{}",user1.getName(),user2.getName());
		System.out.println("执行结果  任务1：" + user1.getName() + " 任务2：" + user2.getName());
		System.out.println(System.currentTimeMillis() - t);
	}
	
}
