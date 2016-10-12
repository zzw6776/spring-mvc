package com.demo.hibernate.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "shiro_resource")
public class ShiroResource {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String name;

    private String type;

    private String url;

    private Long parent_id;

    private String parent_ids;

    private String permission;

    private Boolean available;


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return parent_id
     */
    public Long getParent_id() {
        return parent_id;
    }

    /**
     * @param parent_id
     */
    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * @return parent_ids
     */
    public String getParent_ids() {
        return parent_ids;
    }

    /**
     * @param parent_ids
     */
    public void setParent_ids(String parent_ids) {
        this.parent_ids = parent_ids;
    }

    /**
     * @return permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * @return available
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}