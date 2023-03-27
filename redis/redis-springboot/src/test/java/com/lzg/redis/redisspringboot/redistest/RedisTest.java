package com.lzg.redis.redisspringboot.redistest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 测试连接
     */
    @Test
    public void testConnection() {
        redisTemplate.opsForValue().set("k1","v1");

        System.out.println(redisTemplate.opsForValue().get("k1"));

    }
}
