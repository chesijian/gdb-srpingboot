package com.jrsoft.engine.model.org;/**
 * Created by chesijian on 2021/6/10.
 */

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;

/**
 * @ClassName OrgDepart
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/10 10:54
 * @Version 1.0
 */
@Data
public class OrgDepart extends BaseEntity {

    private String departName;
    private String departId;
    private String departType;
    private String parentId;
    private Double sort;

}
