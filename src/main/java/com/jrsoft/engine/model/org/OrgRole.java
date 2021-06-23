package com.jrsoft.engine.model.org;/**
 * Created by chesijian on 2021/6/10.
 */

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;

/**
 * @ClassName OrgRole
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/10 9:57
 * @Version 1.0
 */
@Data
public class OrgRole extends BaseEntity {

    private String roleId;
    private String roleName;
}
