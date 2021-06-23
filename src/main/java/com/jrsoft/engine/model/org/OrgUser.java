package com.jrsoft.engine.model.org;/**
 * Created by chesijian on 2021/5/25.
 */

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName OrgUser
 * @Description TODO
 * @Author chesijian
 * @Date 2021/5/25 11:58
 * @Version 1.0
 */
@Data
public class OrgUser extends BaseEntity{

    private String password;
    private String userName;
    private String userId;
    private String positionName;
    private String departName;



}
