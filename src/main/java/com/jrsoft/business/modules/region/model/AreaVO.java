package com.jrsoft.business.modules.region.model;

import java.io.Serializable;

/**
 * Created by zcf on 2018/10/25.
 */
public class AreaVO implements Serializable{
    private static final long serialVersionUID = -3334998566157175167L;

    private int id;
    // 级别
    private int level;
    // 区域编码
    private String code;
    // 区域名称
    private String name;
    // 排序
    private String sort;
    // 经度
    private String longitude;
    // 纬度
    private String latitude;
    // 父节点ID
    private int parentId;
    // 简称
    private String ak;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }
}
