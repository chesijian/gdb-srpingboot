package com.jrsoft.engine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.jrsoft.business.modules.progress.model")
@Configuration
public class JpaConfig {
}
