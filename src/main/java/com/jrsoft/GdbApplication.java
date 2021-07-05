package com.jrsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@MapperScan({"com.jrsoft.business.modules","com.jrsoft.engine"}) //mapper接口扫描
//@MapperScan(basePackages = "com.jrsoft.business.modules")
//@MapperScan("com.jrsoft.business.modules")
@ComponentScan(basePackages = {"com.jrsoft.engine","com.jrsoft.component"})
@EnableJpaRepositories(basePackages = {"com.jrsoft.business.modules.progress.model","com.jrsoft.engine"})
@EntityScan(basePackages = {"com.jrsoft.engine","com.jrsoft.modules"})
@Configuration
public class GdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdbApplication.class, args);
	}

}
