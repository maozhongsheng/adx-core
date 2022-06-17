package com.mk.adx.controller;

import com.mk.adx.config.TopicConfig;
import com.mk.adx.entity.protobuf.tz.TzBidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author mzs
 * @Description
 * @Date 2020/10/28 9:48
 */
@RestController
@Slf4j
@RequestMapping("/api/proto")
public class TzProtoController {



//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    /**
//     * @Author mzs
//     * @Description 请求protobuf
//     * @Date 2021/02/23 9:51
//     */
//    @ResponseBody
//    @RequestMapping(value = "/tzRequest",produces = "application/x-protobuf")
//    public TzBidResponse.BidResponse tzRequest(@RequestBody byte[] content) throws IOException {
//        TzBidResponse.BidResponse bidResponse = jmProtoService.getJmDataByProto(content);
//        kafkaTemplate.send(TopicConfig.SSP_DSP_REQUEST,bidResponse.toString());
//        return bidResponse;
//    }

}
