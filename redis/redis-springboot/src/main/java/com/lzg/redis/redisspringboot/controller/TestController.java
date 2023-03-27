package com.lzg.redis.redisspringboot.controller;

import com.lzg.redis.redisspringboot.service.SecSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/testRedis")
    public String testRedis() {
        Object k1 = redisTemplate.opsForValue().get("k1");

        return (String) k1;
    }

    @Autowired
    private SecSkillService secSkillService;

    @GetMapping("/testSecSkill")
    public boolean secSkill(@RequestParam("userId") String userId,@RequestParam("objId") String objId) {
        return secSkillService.secSkill(userId,objId);
    }


    @GetMapping("/testLock")
    public void testLock() {
        String key = "lock";
        String uuid = String.valueOf(UUID.randomUUID());
        //使用redis的分布式锁
        Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(key, uuid, 10, TimeUnit.SECONDS);

        if (isSuccess) {
            //操作成功了
            //先检查数据库中是否存在nunm
            Object num = redisTemplate.opsForValue().get("num");
            if (StringUtils.isEmpty(num)) {
                return;  //结束
            }

            redisTemplate.opsForValue().increment("num");

            //结束锁
            String uuidValue = (String) redisTemplate.opsForValue().get(key);
            if (uuid.equals(uuidValue)) {
                //发现是自己所对应的那个锁，开始解锁
                redisTemplate.delete(key);
            }

        }
    }
}
