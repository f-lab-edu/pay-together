package com.paytogether.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@EnableRedisIndexedHttpSession
@Configuration
public class RedisConfig {

  @Bean
  public LettuceConnectionFactory connectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean
  public RedisSerializer<Object> springSessionDefaultRedisSerializer(ObjectMapper objectMapper) {
    return new GenericJackson2JsonRedisSerializer(objectMapper);
  }
}
