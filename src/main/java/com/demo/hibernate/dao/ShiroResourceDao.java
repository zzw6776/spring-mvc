package com.demo.hibernate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.hibernate.entity.ShiroResource;
@SuppressWarnings("unchecked")
@Repository
@Transactional
public class ShiroResourceDao extends HGenericDao<ShiroResource,String>{

	/**
	 * 
	 * @param ids   如'1','2' 直接in调用
	 * @return		返回permission String的List 
	 */
	
	public List<String> queryPermissionByIds(String ids) {
		String hql = "Select permission from ShiroResource where id in ?";
		return findByQuery(hql, ids);
	}
}
