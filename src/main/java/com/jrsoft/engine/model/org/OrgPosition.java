package com.jrsoft.engine.model.org;/**
 * Created by chesijian on 2021/6/17.
 */

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;

/**
 * @ClassName OrgPosition
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/17 15:08
 * @Version 1.0
 */
@Data
public class OrgPosition extends BaseEntity {

    private String positionName;
    private String parentName;
    private String departName;
    private String subCompanyName;

    private String positionUid;
}
