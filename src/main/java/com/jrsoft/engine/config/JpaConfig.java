package com.jrsoft.engine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.jrsoft.engine.base.domain.sys")
@Configuration
public class JpaConfig {
}
