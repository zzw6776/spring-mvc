package mybaits;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.service.DataBaseTestService;

import base.baseTest;

public class DataSourceTest extends baseTest{

	@Autowired
	DataBaseTestService dataService;
	@Test
	public void create() {
		dataService.mybatisTransactionTest();
	}
}
