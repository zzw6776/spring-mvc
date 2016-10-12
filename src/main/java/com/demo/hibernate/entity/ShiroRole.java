package com.demo.hibernate.entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "shiro_role")
public class ShiroRole {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String role;

    private String description;

    private String resource_ids;

    private Boolean available;

 
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return resource_ids
     */
    public String getResource_ids() {
        return resource_ids;
    }

    /**
     * @param resource_ids
     */
    public void setResource_ids(String resource_ids) {
        this.resource_ids = resource_ids;
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