/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author armdev
 */
@Configuration
class SwaggerConfig {

    ///http://localhost:4040/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("City Jokes")
                .pathsToMatch("/api/**")
                .build();
    }
}
