package com.jrsoft.engine.filter;/**
 * Created by chesijian on 2021/6/12.
 */

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @ClassName MyAccessDecisionManager
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 12:32
 * @Version 1.0
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        for (ConfigAttribute attribute : collection) {
            if("ROLE_login".equals(attribute.getAttribute())){
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("非法请求！");
                }else{
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(attribute.getAttribute())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("非法请求！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
