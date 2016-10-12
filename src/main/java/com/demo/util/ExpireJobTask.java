package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ExpireJobTask {
	private static final Logger logger = LoggerFactory.getLogger(ExpireJobTask.class);

	@Scheduled(cron = "0 0 0/1 * * ?")
	public void doBiz() {
		logger.info("~~~~~~~~~~~~~");
	}
}
