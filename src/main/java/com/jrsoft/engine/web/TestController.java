package com.jrsoft.engine.web;/**
 * Created by chesijian on 2021/6/15.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/15 11:45
 * @Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/setRedis")
    public void setRedis(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("name","chesijian");
    }

    @GetMapping("/getRedis")
    public String getRedis(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.get("name");
    }
}
