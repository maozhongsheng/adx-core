package com.mk.adx.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * kafka异步生产
 *
 * @author yjn
 * @version 1.0
 * @date 2021/1/28 19:43
 */
@RestController
@Slf4j
@Component
public class KafkaProducer {

      @Autowired
      private KafkaTemplate<String, String> kafkaTemplate;

      public RecordMetadata sendChannelMess(String topic, String message){
          ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(topic,message);
          RecordMetadata recordMetadata = null;
          try {
              recordMetadata = future.get().getRecordMetadata();
          }catch (InterruptedException|ExecutionException e){
              e.printStackTrace();
              System.out.println("发送失败");
          }
          System.out.println("发送成功");
          System.out.println("partition:" + recordMetadata.partition());
          System.out.println("offset:" + recordMetadata.offset());
          System.out.println("topic:" + recordMetadata.topic());

          return  recordMetadata;
      }



//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    // 发送消息
//    @GetMapping("/api/kafka/{message}")
//    public void sendMessage1(@PathVariable("message") String message) {
//        kafkaTemplate.send("test123", message);
//    }
//
//    @GetMapping("/api/kafka/callbackOne/{message}")
//    public void sendMessage2(@PathVariable("message") String callbackMessage) {
//        kafkaTemplate.send("test123", callbackMessage).addCallback(success -> {
//            // 消息发送到的topic
//            String topic = success.getRecordMetadata().topic();
//            // 消息发送到的分区
//            int partition = success.getRecordMetadata().partition();
//            // 消息在分区内的offset
//            long offset = success.getRecordMetadata().offset();
//            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
//        }, failure -> {
//            System.out.println("发送消息失败:" + failure.getMessage());
//        });
//    }
//
//    @GetMapping("/api/kafka/callbackTwo/{message}")
//    public void sendMessage3(@PathVariable("message") String callbackMessage) {
//        kafkaTemplate.send("test123", callbackMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("发送消息失败："+ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
//                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
//            }
//        });
//    }

}

