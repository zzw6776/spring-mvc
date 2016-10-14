package shiro;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.service.impl.ShiroServiceImpl;

import base.BaseTest;

public class ShiroTest extends BaseTest {
	@Autowired
	ShiroServiceImpl shiroService;

	@Test
	public void name1() {
		shiroService.login("1", "1");
	}
}
