package com.mybank.configuration;

import com.mybank.entity.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private String redisPort;

    private JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(redisHost);
        jedisConFactory.setPort(Integer.parseInt(redisPort));
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Comment> redisTemplate() {
        RedisTemplate<String, Comment> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
