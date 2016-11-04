package com.demo.hibernate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.hibernate.entity.Diary;
@Repository
public class DiaryDao extends HGenericDao<Diary,String>{

	
public	Diary queryToday(){
		String hql = "from Diary where Date(from_unixtime(createTime/1000))=curdate()";
		List<Diary> diary = findByHql(hql);
		if (diary.isEmpty()) {
			return null;
		}else {
			return diary.get(0);
		}
	}
}
