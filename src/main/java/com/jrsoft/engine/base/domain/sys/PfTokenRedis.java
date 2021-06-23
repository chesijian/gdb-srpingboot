package com.jrsoft.engine.base.domain.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgRole;
import com.jrsoft.engine.model.org.OrgUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2017-07-04 15:42
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PfTokenRedis implements UserDetails,Serializable{
    private static final long serialVersionUID = 1L;

    private String userUid;
    private OrgUser user;
    /*private OrgPosition position;
    private OrgDepart depart;
    private OrgDepart subCompany;*/
    private OrgDepart company;
    private List<OrgRole> roleList;
//	private List<OrgPositionPo> positions;
    private String token;
    private Date createTime;
    private String corpId;
	@ApiModelProperty(value="sub",name="是否子组织",dataType="boolean")
	private boolean sub;
    private int loginType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (OrgRole role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
