package com.demo.hibernate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 批量保存如果数据已存在
 * 更新时需要忽略更新的字段
 * 或者运行时属性非真实字段
 * @author spencer.xue
 * @date 2014-1-7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface  BatchSaveIgnore {
	/**
	 * 默认主键名称 
	 * 如果主键名称非"id" 
	 * 请在主键属性上注解{@link javax.persistence.Id}
	 */
	public final String defaultIdName = "id";
	public final int update = 1;
	public final int all = 2;
    /**
     * 忽略字段的级别
     * 默认{@code all} 即 插入、 更新都忽略
     * 不支持单独插入忽略
     */
    public int ingoreLevel() default all;
    
	
}
