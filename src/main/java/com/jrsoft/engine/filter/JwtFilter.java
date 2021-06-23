package com.jrsoft.engine.filter;/**
 * Created by chesijian on 2021/6/12.
 */

import com.alibaba.fastjson.JSON;
import com.jrsoft.component.cache.CacheUtil;
import com.jrsoft.engine.base.domain.sys.PfTokenRedis;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName JwtFilter
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 14:26
 * @Version 1.0
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        StringBuffer requestURL = req.getRequestURL();

        String jwtToken = req.getHeader("Authorization");
        System.out.println("requestURL=======>"+requestURL.toString()+"====jwtToken===>"+jwtToken);
        Jws<Claims> jws = Jwts.parser().setSigningKey("chesijian@126")
                .parseClaimsJws(jwtToken.replace("Bearer", ""));
        Claims claims = jws.getBody();
        String userUid = claims.getSubject();
        //PfTokenRedis tokenRedis = JSON.parseObject(userUid,PfTokenRedis.class);
        PfTokenRedis tokenRedis = new PfTokenRedis();
        tokenRedis.setUserUid(userUid);
        OrgUser user = new OrgUser();
        user.setId(userUid);
        OrgDepart company = new OrgDepart();
        company.setId(String.valueOf(claims.get("COMPANY_")));
        tokenRedis.setUser(user);
        tokenRedis.setCompany(company);
        tokenRedis = CacheUtil.getToken(tokenRedis);
        List<GrantedAuthority> authorities =AuthorityUtils.commaSeparatedStringToAuthorityList((String)claims.get("authorities"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(tokenRedis,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
