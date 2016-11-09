package com.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ExpireJobTask {
	
	@Resource(name = "keyValue")
	Map<String, String> keyValueMap;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ExpireJobTask.class);

	@Scheduled(cron = "0 0 01 * * ?")
	public  void exportSql() throws Exception {
		String common = "mysqldump -uroot --databases zzw>/root/spring-mvc/sql/"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".sql";
		logger.info(common);
		String[] cmds = {"/bin/sh","-c",common};  
		Process process = Runtime.getRuntime().exec(cmds);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
			String line = null;
			String log = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				log += line;
			}
			logger.info(log);
		} finally {
			br.close();
		}
	}
	
	
	@Scheduled(cron = "0 0 02 * * ?")
	public  void backUpSql() throws Exception {
		Dropbox.upload(keyValueMap.get("DropboxAccessToken"), "/root/spring-mvc/sql/"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".sql", "/sqlBack/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".sql");
	}
}
