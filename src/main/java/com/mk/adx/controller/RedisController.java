package com.mk.adx.controller;

import com.mk.adx.entity.json.request.jiaming.JmApps;
import com.mk.adx.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/4/26 12:14
 */
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {
    private static int ExpireTime = 6000;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/set")
    public boolean redisset(String key, String value){
        JmApps app = new JmApps();
        app.setId("1");
        app.setDomain("123");
        app.setName("zhangsan");

        return redisUtil.set(key,app,ExpireTime);

//        return redisUtil.set(key,value);
    }

    @RequestMapping("/testString")
    public boolean testString(){
        JmApps app = new JmApps();
        app.setId("1");
        app.setDomain("123");
        app.setName("zhangsan");

//        redisUtil.set("ab111", "11111222");
        redisUtil.set("test1",app,ExpireTime);
        System.out.println(redisUtil.get("test1"));
        redisUtil.set("过期时间测试111", 123, 50);
//        redisUtil.del("a");

        return true;
    }


    @RequestMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
