package com.pnmins.min.common.redis;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/***
 * @ClassName RedisConfig
 * @Description redis 配置
 * @Date 2019/3/8 10:47
 * @Author qr
 * @Param
 * @return
 **/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(1));
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
        return redisCacheManager;
    }

    /***
     * @ClassName RedisConfig
     * @Description 自定义序列化方式
     * @Date 2019/3/8 10:47
     * @Author qr
     * @Param [factory]
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.Object>
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
         /* RedisSerializer redisSerializer = new StringRedisSerializer();
        template.setKeySerializer(redisSerializer);
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        template.setValueSerializer(jsonRedisSerializer);*/
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return template;
    }
}
