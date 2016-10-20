package mybaits;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.aspectj.bridge.Message;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.mybatis3.dao.BDao;
import com.demo.mybatis3.domain.BEntity;
import com.demo.web.vo.BEntityVo;

import base.BaseTest;
import tk.mybatis.mapper.entity.Example;

public class BTest extends BaseTest {

	@Autowired
	BDao bDao;

	@Test
	public void insert() {
		BEntity bEntity = new BEntity();
		bEntity.setMessage("测试数据");
		bDao.insertSelective(bEntity);
		List<BEntity> bEntities = bDao.select(bEntity);
		assertEquals("测试数据", bEntities.get(0).getMessage());
	}

	@Test
	public void selectChild() {
		BEntity bEntity = new BEntity();
		bEntity.setMessage("测试数据");
		bDao.insertSelective(bEntity);
		Integer id = bEntity.getId();
		bEntity.setParentID(id);
		bEntity.setId(null);
		bEntity.setMessage("我是父数据");
		bDao.insertSelective(bEntity);
		BEntityVo vo = bDao.queryAllChild(id);
		assertEquals("我是父数据", vo.getChild().get(0).getMessage());
	}
	@Test
	public void insertList() {
		BEntity bEntity = new BEntity();
		bEntity.setMessage("测试数据");
		bDao.insertListOnDuplicateUpdate(CollectionUtils.asList(bEntity));
		Example example = new Example(BEntity.class);
		example.createCriteria().andCondition("message = '测试数据'");
		List<BEntity> bEntities = bDao.selectByExample(example);
		assertEquals(1,bEntities.size());
	}
}
