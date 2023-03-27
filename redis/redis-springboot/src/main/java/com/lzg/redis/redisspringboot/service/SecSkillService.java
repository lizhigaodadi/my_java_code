package com.lzg.redis.redisspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecSkillService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param userId 用户id
     * @param objId  商品id  默认0101
     * @return 真表示秒杀成功，假则反之
     */
    public boolean secSkill(String userId,String objId) {
        //获取两个key
        String userKey = "sk:user";
        String objKey = "sk:" + objId;

        //判断库存是否为null ，如果为null则表示秒杀还未开始
        Integer objCount = (Integer) redisTemplate.opsForValue().get(objKey);
        if (objCount == null) {
            //秒杀还未开始
            System.out.println("秒杀还未开始，请稍后再来");
            return false;
        }

        //判断是否为重复秒杀用户
        if (redisTemplate.opsForSet().isMember(userKey,userId)) {
            System.out.println("您已秒杀一次，请勿重复秒杀");
            return false;
        }

        //开始判断是否有库存，有库存就秒杀
        if (objCount < 0) {
            //没库存，再见
            System.out.println("秒杀已经结束，请下次再来");
            return false;
        }
        //监视库存
        redisTemplate.watch(objKey);

        //开始秒杀,使用事务
        redisTemplate.multi();

        redisTemplate.opsForValue().decrement(objKey);
        //将userId加入到集合中
        redisTemplate.opsForSet().add(userKey,userId);
        List exec = redisTemplate.exec();
        if (exec == null || exec.size() == 0) {
            //执行失败，说明有错误
            System.out.println("秒杀失败");
            return false;
        }

        System.out.println("您已秒杀成功");

        return true;
    }

}
