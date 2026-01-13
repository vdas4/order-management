package com.order.app.orderingestionservice.config;

import org.apache.camel.component.redis.processor.idempotent.SpringRedisIdempotentRepository;
import org.apache.camel.spi.IdempotentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }

    @Bean
    public IdempotentRepository idempotentRepository(RedisTemplate<String, String> redisTemplate) {
        return new SpringRedisIdempotentRepository(redisTemplate, "order-files");
    }
}
