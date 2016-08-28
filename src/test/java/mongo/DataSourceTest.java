package mongo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.mongo.dao.MongoTestDao;
import com.demo.mongo.domain.MongoTest;

import base.baseTest;

public class DataSourceTest extends baseTest{

	@Autowired
	MongoTestDao testDao;
	
	@Test
	public void testMongo() {
		MongoTest mongoTest = new MongoTest();
		mongoTest.setId("123456");
		mongoTest.setMessage("test");
		testDao.insert(mongoTest);
	}
}
