package com.demo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.hibernate.dao.KeyValueDao;
import com.demo.hibernate.entity.KeyValue;

@Configuration
public class SpringConfig {

	@Autowired
	KeyValueDao keyValueDao;

	@Bean(name = "keyValue")
	public Map<String, String> getKeyValueMap() {
		Map<String, String> map = new HashMap<>();
		List<KeyValue> keyValues = keyValueDao.findAll();
		for (KeyValue keyValue : keyValues) {
			map.put(keyValue.getKey(), keyValue.getValue());
		}
		return map;
	}
}
