package com.demo.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "shiro_role")
public class ShiroRole {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length=50)
    private String id;

    /**
     * 角色名字
     */
    private String role;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 资源ID,以英文逗号分隔，如1,2,3
     */
    private String resourceIds;

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
     * 获取角色名字
     *
     * @return role - 角色名字
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色名字
     *
     * @param role 角色名字
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取角色描述
     *
     * @return description - 角色描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色描述
     *
     * @param description 角色描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取资源ID,以英文逗号分隔，如1,2,3
     *
     * @return resourceIds - 资源ID,以英文逗号分隔，如1,2,3
     */
    public String getResourceIds() {
        return resourceIds;
    }

    /**
     * 设置资源ID,以英文逗号分隔，如1,2,3
     *
     * @param resourceIds 资源ID,以英文逗号分隔，如1,2,3
     */
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
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