package com.mk.adx.controller;


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


    @RequestMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
