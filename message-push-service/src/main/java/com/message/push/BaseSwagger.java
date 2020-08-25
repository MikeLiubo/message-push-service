package com.message.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/24 14:16
 **/
@EnableSwagger2
@Configuration
public class BaseSwagger {

    @Autowired
    private Environment env;

    @Bean
    public Docket createRestApi() {
        String packagePath = env.getProperty("swagger.package");
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(packagePath))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        String title = env.getProperty("swagger.title");
        String description = env.getProperty("swagger.description");
        String version = env.getProperty("swagger.version");
        return new ApiInfoBuilder().title(title).description(description).version(version).build();
    }
}
