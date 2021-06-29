package com.jrsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan({"com.jrsoft.business.modules","com.jrsoft.engine"}) //mapper接口扫描
//@MapperScan(basePackages = "com.jrsoft.business.modules")
//@MapperScan("com.jrsoft.business.modules")
@Configuration
public class GdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdbApplication.class, args);
	}

}
