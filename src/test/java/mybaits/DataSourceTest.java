package mybaits;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.mybatis3.dao.MybayisTestMapper;
import com.demo.mybatis3.domain.MybayisTest;

import base.baseTest;

public class DataSourceTest extends baseTest{

	@Autowired
	MybayisTestMapper mybatisDao;
	@Test
	public void create() {
		MybayisTest test = new MybayisTest();
		test.setMessage("中文支持");
		mybatisDao.insert(test);
	}
}
