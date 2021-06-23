package com.jrsoft.engine.config;/**
 * Created by chesijian on 2021/5/25.
 */

import com.jrsoft.component.redis.RedisUtil;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName EngineConfig
 * @Description TODO
 * @Author chesijian
 * @Date 2021/5/25 13:58
 * @Version 1.0
 */
@Configuration
//@Import(RedisAutoConfiguration.class)
public class EngineConfig {

    @Bean
    public RedisUtil redisUtil(RedisTemplate<Object, Object> redisTemplate){
        RedisUtil res = new RedisUtil();
        res.setRedisTemplate(redisTemplate);
        return res;
    }

}
