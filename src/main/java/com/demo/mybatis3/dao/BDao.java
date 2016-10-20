package com.demo.mybatis3.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.demo.mybatis3.domain.BEntity;
import com.demo.mybatis3.tkMapper.InsertListOnDuplicateUpateMapper;
import com.demo.web.vo.BEntityVo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BDao extends Mapper<BEntity>,MySqlMapper<BEntity>,InsertListOnDuplicateUpateMapper<BEntity> {

	@Select("select * from BEntity where id =#{id}")
	@ResultMap("com.demo.mybatis3.dao.BDao.ChildResultMap")
	BEntityVo queryAllChild(@Param("id") Integer id);
}