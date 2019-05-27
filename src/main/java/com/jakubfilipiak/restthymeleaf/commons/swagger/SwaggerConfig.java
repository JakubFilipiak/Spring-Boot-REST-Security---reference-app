package com.jakubfilipiak.restthymeleaf.commons.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Jakub Filipiak on 09.05.2019.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket config () {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jakubfilipiak" +
                        ".restthymeleaf.controllers"))
                .paths(regex("/api/dto/.*"))
                .build();
    }
}
