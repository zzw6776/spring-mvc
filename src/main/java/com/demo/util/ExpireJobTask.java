package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@EnableScheduling
public class ExpireJobTask {
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(ExpireJobTask.class);

	/**
	 * 业务逻辑处理
	 */
	@Scheduled(cron = "* * 0/1 * * ?")
	public void doBiz() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~`");
	}
}
