package com.jrsoft.engine.base.domain.sys;/**
 * Created by chesijian on 2021/6/12.
 */

import com.jrsoft.engine.model.org.OrgRole;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @ClassName Menu
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 11:42
 * @Version 1.0
 */
@Data
public class PfMenu {

    private String id;
    private String name;
    private String parentId;
    private String title;
    private String code;
    private String pattern;
    private Integer resourceType;
    private List<OrgRole> roleList;
    private List<PfMenu> children;
}
