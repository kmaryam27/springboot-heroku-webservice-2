package com.galvanize.simplegitarapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SimpleGitarApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleGitarApiApplication.class, args);
    }

}
