package com.jrsoft.engine.base.domain.sys;/**
 * Created by chesijian on 2021/6/12.
 */

import com.jrsoft.engine.base.domain.BaseEntity;
import com.jrsoft.engine.model.org.OrgRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName Menu
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 11:42
 * @Version 1.0
 */
@Data
@Entity
@Table(name="t_test_menu")
public class PfMenu{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String type;
    private String parentId;
    private String title;
    private String code;
    private String formKey;
    private String icon;
    private String path;
    private String params;
    private String app;
    private String description;
    private String mobilePath;
    private String uri;
    private String pattern;
    private Integer resourceType;
    @ApiModelProperty(value="1表示集团首页，2表示集团首页中类似系统设置，3表示具体项目内容菜单",name="category",dataType="int")
    protected Integer category;
    private boolean enable;
    private Double sort;
    @Transient
    private List<OrgRole> roleList;
    @Transient
    private List<PfMenu> children;
}
