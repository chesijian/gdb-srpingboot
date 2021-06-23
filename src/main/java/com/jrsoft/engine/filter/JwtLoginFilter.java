package com.jrsoft.engine.filter;/**
 * Created by chesijian on 2021/6/12.
 */

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrsoft.component.cache.CacheUtil;
import com.jrsoft.component.redis.RedisUtil;
import com.jrsoft.engine.base.domain.sys.PfTokenRedis;
import com.jrsoft.engine.base.interceptor.LogInterceptor;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.common.cache.TokenCache;
import com.jrsoft.engine.db.mapping.org.LoginDao;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgUser;
import com.jrsoft.engine.web.ApplicationContextRegister;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtFilter
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 13:20
 * @Version 1.0
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {


    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        //OrgUser user = new ObjectMapper().readValue(req.getInputStream(),OrgUser.class);
        String userId =req.getParameter("userId");
        String password =req.getParameter("password");
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userId,password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        StringBuffer sb = new StringBuffer();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();//获取登录用户角色
        for (GrantedAuthority authority : authorities) {
            sb.append(authority.getAuthority()).append(",");
        }
        PfTokenRedis tokenRedis = (PfTokenRedis) authResult.getPrincipal();
        String jwt = Jwts.builder()
                .claim("authorities",sb)
                .claim("COMPANY_",tokenRedis.getCompany().getId())
                .setSubject(tokenRedis.getUser().getId())
                .setExpiration(new Date(System.currentTimeMillis()+ TokenCache.TOKEN_0_EXPIRE))
                .signWith(SignatureAlgorithm.HS512,"chesijian@126")
                .compact();
        tokenRedis.getUser().setPassword(null);
        tokenRedis.setToken(jwt);
        //LogInterceptor.TokenRedisThreadLocal.set(tokenRedis);
        //保存到redis中
        CacheUtil.putToken(tokenRedis);
        ReturnJson returnJson = ReturnJson.ok(tokenRedis);
        String s = new ObjectMapper().writeValueAsString(returnJson);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(s);
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        Map<String,String> map = new HashMap<>();
        map.put("msg","登录失败!");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(map));
        out.flush();
        out.close();
    }
}
