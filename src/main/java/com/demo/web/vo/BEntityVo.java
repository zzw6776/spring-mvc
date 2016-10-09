package com.demo.web.vo;

import java.util.List;

import com.demo.mybatis3.domain.BEntity;

public class BEntityVo extends BEntity{

	private List<BEntityVo> child;

	public List<BEntityVo> getChild() {
		return child;
	}

	public void setChild(List<BEntityVo> child) {
		this.child = child;
	}

	
}
