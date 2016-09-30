package mongo;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.demo.mongo.dao.MDao;
import com.demo.mongo.domain.MEntity;

import base.BaseTest;
import static org.junit.Assert.*;
public class MTest extends BaseTest{

	@Autowired
	MDao mDao;
	
	@Test
	public void inser() {
		MEntity mongoTest = new MEntity();
		mongoTest.setId("123456");
		mongoTest.setMessage("test");
		mDao.insert(mongoTest);
		List<MEntity> entities = mDao.find(new Query().addCriteria(Criteria.where("Id").is("123456")));
		assertEquals("test", entities.get(0).getMessage());
	}
}
