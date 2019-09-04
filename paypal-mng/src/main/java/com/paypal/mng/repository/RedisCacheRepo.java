package com.paypal.mng.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisCacheRepo {

    private RedisTemplate<String, String> redisTemplate;

    public RedisCacheRepo(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValue(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
