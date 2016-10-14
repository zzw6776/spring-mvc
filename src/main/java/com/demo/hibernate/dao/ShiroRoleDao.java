package com.demo.hibernate.dao;

import org.springframework.stereotype.Repository;

import com.demo.hibernate.entity.ShiroRole;

@Repository
public class ShiroRoleDao extends HGenericDao<ShiroRole, String> {

	public String queryResoureceByAccount(String account) {
		String sql = "select r.resourceIds from shiro_role r,user u where u.account =:account and u.roleId = r.id";
		return (String) getCurrentSession().createSQLQuery(sql).setParameter("account", account).uniqueResult();
	}
	
	public String queryRoleByAccount(String account) {
		String sql = "select r.role from shiro_role r,user u where u.account =:account and u.roleId = r.id";
		return (String) getCurrentSession().createSQLQuery(sql).setParameter("account", account).uniqueResult();
	}
}
