package com.bb.manager.web.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger
public class SwaggerMvcConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .swaggerGroup("public-api")
                .includePatterns(".*api.*")
                .apiInfo(apiInfo());
    }

    @Bean
    public SwaggerSpringMvcPlugin manageApi() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .swaggerGroup("manage-api")
                .includePatterns(".*management.*")
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "My API",
                "My API",
                "",
                "",
                "",
                ""
        );
    }
}