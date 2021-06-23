package com.jrsoft.engine.config;/**
 * Created by chesijian on 2021/6/16.
 */

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/16 23:33
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket docket(){
        String packages = "com.jrsoft.engine.web,com.jrsoft.common";
        String[] packageArr = packages.split(",");
        Predicate[] predicates = new Predicate[packageArr.length];
        int i = 0;
        for(String packageStr : packageArr){
            Predicate item = RequestHandlerSelectors.basePackage(packageStr);
            predicates[i++] = item;
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.or(
                        predicates
                ))
                .paths(PathSelectors.any())
                .build().apiInfo(buildApiInf());
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("建软API")
                .termsOfServiceUrl("http://www.jrsoft.cc/")
                .description("springmvc swagger2")
                .contact(new Contact("Blueeyedboy", "blueeyedboy521@163.com", "blueeyedboy521@163.com"))
                .build();
    }
}
