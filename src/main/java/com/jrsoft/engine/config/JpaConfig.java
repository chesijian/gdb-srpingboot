package com.jrsoft.engine.config;


/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2020-07-10 9:47 AM
 **/
// 标明该类使用Spring基于Java的配置

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
// 读取配置文件的，通过Environment读取
@PropertySource("classpath:application.properties")
// scan the package of the annotated configuration class for Spring Data repositories
@EnableJpaRepositories(basePackages = "com.jrsoft.engine")
// Enables Spring's annotation-driven transaction management
@EnableTransactionManagement
public class JpaConfig {
    @Autowired
    private Environment env;

    /**
     * 2.配置EntityManagerFactory
     *
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        System.out.println("--------------------JpaConfig------------------");
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        // 配置数据源
        factory.setDataSource(dataSource);
        // VendorAdapter
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(null);
        String packages = env.getRequiredProperty("spring.hibernate.packages");
        // entity包扫描路径
//		factory.setPackagesToScan("com.jrsoft","com.jrsoft.modules.form");
        factory.setPackagesToScan("com.jrsoft.engine");

        // jpa属性
        factory.setJpaProperties(hibernateProperties());
        factory.afterPropertiesSet();

//		factory.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");

        return factory;
    }

    /**
     * hibernate配置
     * @return
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        // 显示sql语句
        properties.put("hibernate.show_sql", env.getRequiredProperty("spring.hibernate.show_sql"));
        // 格式化sql语句
        properties.put("hibernate.format_sql", env.getRequiredProperty("spring.hibernate.format_sql"));
        // 方言
        //properties.put("hibernate.dialect", env.getRequiredProperty("spring.hibernate.dialect"));
//		properties.put("hibernate.dialect", env.getRequiredProperty("spring.hibernate.dialect"));
        // 自动生成表
        //properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.hibernate.hbm2ddl.auto"));
        // 名字策略
        //properties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("spring.hibernate.ejb.naming_strategy"));
        return properties;
    }
}
