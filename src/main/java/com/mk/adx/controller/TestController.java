package com.mk.adx.controller;


import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.ResponseResult;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.service.OneNJsonService;
import com.mk.adx.service.UcJsonService;
import com.mk.adx.service.XiaoMiJsonService;
import com.mk.adx.service.YuekeJsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 接受请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/26 15:30
 */
@RestController
@Slf4j
@RequestMapping("/api/json")
public class TestController {

    @Autowired
    private OneNJsonService oneNJsonService;
    @Autowired
    private XiaoMiJsonService xiaoMiJsonService;
    @Autowired
    private YuekeJsonService yuekeJsonService;
    @Autowired
    private UcJsonService ucJsonService;


    /**
     * @Author mzs
     * @Description
     * @Date 2021/8/26 15:30
     */
    @ResponseBody
    @RequestMapping(value = "/tzRequestTest",method = {RequestMethod.POST})
    public ResponseResult indexJsonRequest(@Valid @RequestBody MkBidRequest request) throws IOException, ExecutionException, InterruptedException {
        log.info("========="+ JSONObject.toJSONString(request.toString()));
        MkBidResponse bidResponse;
        bidResponse = ucJsonService.getUcDataByJson(request);
        if (null!=bidResponse.getId()){
            return new ResponseResult(bidResponse);
        }else {
            return new ResponseResult();
        }
    }
}
