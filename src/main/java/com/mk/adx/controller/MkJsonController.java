package com.mk.adx.controller;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.AsyncConfig.asyncService.TaskService;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.ResponseResult;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Socket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * 接受请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:47
 */
@RestController
@Slf4j
public class MkJsonController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private KafkaTemplate kafkaTemplate1;



    /**
     * @Author mzs
     * @Description 总请求json
     * @Date 2021/06/25 9:51
     */
    @ResponseBody
    @RequestMapping(value = "/req",method = {RequestMethod.POST})
    public ResponseResult TzJsonRequest(@Valid @RequestBody MkBidRequest request) throws IOException {
        log.info("=========下游请求参数"+JSONObject.toJSON(request));
        MkBidResponse bidResponse = taskService.ckJsonRequest(request);
        if (null != bidResponse.getId()) {
            return new ResponseResult(bidResponse);
        }
        return new ResponseResult();
    }


//    /**
//     * @Author mzs
//     * @Description 总请求json
//     * @Date 2022/05/9 9:51
//     */
//    @ResponseBody
//    @RequestMapping(value = "/tzSspRequest",method = {RequestMethod.POST})
//    public YolohoBidRespones TzAdxJsonRequest(@Valid @RequestBody YolohoBidRequest request) throws IOException {
//        log.info("=========下游请求参数"+JSONObject.toJSON(request));
//        //将大姨妈请求参数转为天卓请求参数
//        TzBidRequest Tzrequest = yolohoJsonService.getTzRequest(request);
//        //获取返回参数
//        TzBidResponse bidResponse = taskService.ckJsonRequest(Tzrequest);
//        //无填充时直接返回204
//        if (null == bidResponse.getId()) {
//            return new YolohoBidRespones();
//        }
//        //将天卓返回参数转为大姨妈返回参数
//        YolohoBidRespones yolohoResponse = yolohoJsonService.getYolohoResponse(bidResponse);
//        if (null != yolohoResponse.getReqid()) {
//            return yolohoResponse;
//        }
//        return new YolohoBidRespones();
//    }

}