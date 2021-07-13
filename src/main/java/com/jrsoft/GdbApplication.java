package com.jrsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@MapperScan(basePackages = {"com.jrsoft.business.modules","com.jrsoft.engine.dao","com.jrsoft.engine.db.mapping","com.jrsoft.engine.**.dao","com.jrsoft.engine.sys.dict.repository"})
//@ComponentScan(basePackages = {"com.jrsoft.engine","com.jrsoft.component"})
@EnableJpaRepositories(basePackages = {"com.jrsoft.engine.sys.dict.repository"})
@EntityScan("com.jrsoft.engine.sys.dict.domin")
@Configuration
public class GdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdbApplication.class, args);
	}

}
