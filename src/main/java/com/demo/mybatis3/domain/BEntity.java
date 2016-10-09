package com.demo.mybatis3.domain;

import javax.persistence.*;

public class BEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    private Integer parentID;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return parentID
     */
    public Integer getParentID() {
        return parentID;
    }

    /**
     * @param parentID
     */
    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }
}