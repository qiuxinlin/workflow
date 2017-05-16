package com.example.sessionRedisDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by pengyingzhi on 2017/3/22.
 */
@SpringBootTest(classes = SessionRedisDemoApplication.class)
@RunWith(SpringRunner.class)
public class SessionRedisDemoApplicationTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Resource(name = "stringRedisTemplate")
    ValueOperations<String,String> valOpsStr;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object,Object> valOps;
    @Test
    public void test(){
        System.out.printf("begin");
        stringRedisTemplate.getClientList();
    }
}
