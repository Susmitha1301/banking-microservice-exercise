package com.example.account_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration //used to tell this clss contains config
public class WebClientConfig {

    @Bean //create this obj nd store it
    public WebClient webClient() {  //this method return webclient obj
        return WebClient.builder().build();
    }
}
