package com.contributors.rest.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();

        restTemplate.setRequestFactory(requestFactory());
        return restTemplate;
    }

    private ClientHttpRequestFactory requestFactory() {
        return new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    }
}
