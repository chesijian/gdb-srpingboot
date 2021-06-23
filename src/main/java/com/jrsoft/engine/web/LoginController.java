package com.jrsoft.engine.web;/**
 * Created by chesijian on 2020/2/23.
 */

import com.jrsoft.engine.base.model.ReturnJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author chesijian
 * @Date 2020/2/23 16:13
 * @Version 1.0
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    public ReturnJson login(){
        return ReturnJson.error(50006,"尚未登录，请登录");
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello secury!";
    }
}
