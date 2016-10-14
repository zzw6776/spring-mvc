package hibernate;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.hibernate.dao.HDao;
import com.demo.hibernate.dao.ShiroRoleDao;
import com.demo.hibernate.entity.HEntity;

import base.BaseTest;

public class HTest extends BaseTest {
	@Autowired
	HDao hDao;

	@Autowired
	ShiroRoleDao roleDao;
	@Test
	public void insert() {
		HEntity entity = new HEntity();
		entity.setMessage("插入测试");
		hDao.saveOrUpdate(entity);
		List<HEntity> hEntities = hDao.findByHql("from HEntity where Message  = '插入测试'");
		assertEquals("插入测试", hEntities.get(0).getMessage());
	}
	
	@Test
	public void test1(){
		
		System.out.println(roleDao.queryResoureceByAccount("1"));
		
	}
}
