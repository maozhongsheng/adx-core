package com.mk.adx.kafka;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzUser;
import com.mk.adx.config.TopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/4/6 13:55
 */
@Slf4j
@RestController
public class TestKafka {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/send/{messge}")
    public String sendmsg(@PathVariable String messge) {
        Long startTime = System.currentTimeMillis();
        String res = "";
//        for (int i=0;i<100;i++){
            //建议看一下KafkaTemplate的源码 很多api 我们可以指定分区发送消息
//            KafkaProducer kp = new KafkaProducer();
//            kp.sendChannelMess(TopicConfig.SSP_DSP_RESPONSE, messge+i);
        TzUser user = new TzUser();
        user.setId("1232");
        user.setAge(28);
        user.setIp(messge);
        String content = JSONObject.toJSONString(user);
        kafkaTemplate.send(TopicConfig.SSP_MEDIA_REQUEST, content); //使用kafka模板发送信息
        Long endTime = System.currentTimeMillis();
        Long tempTime = (endTime - startTime);
        res = "消息:【" + messge + "】发送成功 SUCCESS !";
        log.info("花费时间："+
                        (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                        ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                        ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                        ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                        ((tempTime%1000)+"ms"));
        log.info(res);
//        }
        return res;
    }
}
