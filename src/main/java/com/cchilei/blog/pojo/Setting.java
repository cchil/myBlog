package com.cchilei.blog.pojo;

import java.io.Serializable;

public class Setting implements Serializable {
    private Integer id;

    private String config;

    private Boolean status;

    public Setting(Integer id, String config, Boolean status) {
        this.id = id;
        this.config = config;
        this.status = status;
    }

    public Setting() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}