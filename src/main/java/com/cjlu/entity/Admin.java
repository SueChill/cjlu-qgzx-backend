package com.cjlu.entity;

public class Admin {
    private Integer admin_id;
    private Integer permission_level;
    private Integer user_id;

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public Integer getPermission_level() {
        return permission_level;
    }

    public void setPermission_level(Integer permission_level) {
        this.permission_level = permission_level;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}