package com.project.cityjokes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
//@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class})

@EnableMongoRepositories("com.project.cityjokes.repository")
@ComponentScan("com.project.cityjokes")
@EntityScan("com.project.cityjokes.model.*")
@EnableAsync
@Slf4j

public class CityjokesApplication {

    public static void main(String[] args) {

        final SpringApplication application = new SpringApplication(CityjokesApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);

    }

}
