package com.galvanize.simplegitarapi;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeansDefenition {
    @Bean
    @LoadBalanced//for send and recive data from discovery server not direct client
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @Bean
    public WebClient.Builder getWebClient(){
        return WebClient.builder();
    }
}
