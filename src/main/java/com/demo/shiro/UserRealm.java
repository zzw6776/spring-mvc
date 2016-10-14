package com.demo.shiro;

import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.demo.hibernate.dao.ShiroResourceDao;
import com.demo.hibernate.dao.ShiroRoleDao;
import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.entity.User;
public class UserRealm extends AuthorizingRealm {

	@Autowired
	ShiroResourceDao resourceDao;
	@Autowired
	ShiroRoleDao roleDao;
	@Autowired
	UserDao userDao;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String account = (String) principals.getPrimaryPrincipal();
		String resourceIds = roleDao.queryResoureceByAccount(account);// 多个资源以逗号区分
		String role = roleDao.queryRoleByAccount(account);// 多个资源以逗号区分
		List<String> permissions = resourceDao.queryPermissionByIds(resourceIds);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(CollectionUtils.asSet(role));
		authorizationInfo.setStringPermissions(new HashSet<String>(permissions));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String account = (String) token.getPrincipal();
		User user = userDao.find(account);
		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(), // 用户名
				user.getPassword(), // 密码
				getName() // realm name
		);
		return authenticationInfo;
	}

}
