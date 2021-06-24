package com.jrsoft.engine.base.domain.sys;/**
 * Created by chesijian on 2021/6/12.
 */

import com.jrsoft.engine.model.org.OrgRole;
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
public class PfMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String parentId;
    private String title;
    private String code;
    private String pattern;
    private Integer resourceType;
    private boolean enable;
    @Transient
    private List<OrgRole> roleList;
    @Transient
    private List<PfMenu> children;
}
