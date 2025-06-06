package com.paytogether.config;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class RedisContainer {

  private static final String REDIS_DOCKER_IMAGE = "redis";

  static {
    GenericContainer<?> redis =
        new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE)).withExposedPorts(6379);
    redis.start();
    System.setProperty("spring.data.redis.host", redis.getHost());
    System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
  }
}
