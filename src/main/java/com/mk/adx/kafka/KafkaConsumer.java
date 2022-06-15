package com.mk.adx.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**

 * kafka消费者

 */

@Component
@Slf4j
public class KafkaConsumer {

     //消费监听
//    @KafkaListener(topics = {TopicConfig.SSP_MEDIA_REQUEST},groupId = "group")
//    public void onMessage1(ConsumerRecord<?, ?> record){
//        // 消费的哪个topic、partition的消息,打印出消息内容
//        System.out.println("kafka接受请求："+record.topic()+"-"+record.partition()+"-"+record.value());
//    }

//    /**
//     * @Title 指定topic、partition、offset消费
//     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
//     * @Author long.yuan
//     * @Date 2020/3/22 13:38
//     * @Param [record]
//     * @return void
//     **/
//    @KafkaListener(id = "consumer1",groupId = "group1",topicPartitions = {
//            @TopicPartition(topic = "test123", partitions = { "0" }),
//            @TopicPartition(topic = "test", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
//    })
//    public void onMessage2(ConsumerRecord<?, ?> record) {
//        System.out.println("topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
//    }
//
//    @KafkaListener(id = "consumer2",groupId = "group1", topics = "test123")
//    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
//        System.out.println(">>>批量消费一次，records.size()="+records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            System.out.println(record.value());
//        }
//    }

}

