package com.demo.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "shiro_resource")
public class ShiroResource {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    /**
     * 资源名字
     */
    private String name;

    /**
     * 资源类型
     */
    private String type;

    /**
     * url链接
     */
    private String url;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 全限父ID 如:0/1/2
     */
    private String parentIds;

    /**
     * 权限  格式为*:*
     */
    private String permission;

    /**
     * 是否有效
     */
    private Boolean available;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取资源名字
     *
     * @return name - 资源名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名字
     *
     * @param name 资源名字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取资源类型
     *
     * @return type - 资源类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置资源类型
     *
     * @param type 资源类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取url链接
     *
     * @return url - url链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url链接
     *
     * @param url url链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取父id
     *
     * @return parentId - 父id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父id
     *
     * @param parentId 父id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取全限父ID 如:0/1/2
     *
     * @return parentIds - 全限父ID 如:0/1/2
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置全限父ID 如:0/1/2
     *
     * @param parentIds 全限父ID 如:0/1/2
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * 获取权限  格式为*:*
     *
     * @return permission - 权限  格式为*:*
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限  格式为*:*
     *
     * @param permission 权限  格式为*:*
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 获取是否有效
     *
     * @return available - 是否有效
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * 设置是否有效
     *
     * @param available 是否有效
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}