package com.mk.adx.controller;


import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.ResponseResult;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.service.*;
import com.mk.adx.service.UcJsonService;
import com.mk.adx.service.YouYiJsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 天卓-接受请求
 *
 * @author jny
 * @version 1.0
 * @date 2021/8/26 15:30
 */
@RestController
@Slf4j
@RequestMapping("/api/json")
public class TestController {

    @Autowired
    private ZhimengJsonService zhimengJsonService;

    @Autowired
    private UcJsonService ucJsonService;
    @Autowired
    private YouYiJsonService youYiJsonService;

    @Autowired
    private YunJuHeJsonService yunJuHeJsonService;

    @Autowired
    private LanWaJsonService lanWaJsonService;

    @Autowired
    private JiaLiangJsonService jiaLiangJsonService;

    @Autowired
    private ZhongMengJsonService zhongMengJsonService;

    @Autowired
    private AlgorixJsonService algorixJsonService;

    @Autowired
    private WokeJsonService wokeJsonService;

    @Autowired
    private SzydJsonService szydJsonService;

    @Autowired
    private MiTuJsonService miTuJsonService;

    @Autowired
    private RuiDiJsonService ruiDiJsonService;
    @Autowired
    private TongZhouJsonService tongZhouJsonService;

    @Autowired
    private DouMengJsonService douMengJsonService;

    /**
     * @Author yjn
     * @Description
     * @Date 2021/8/26 15:30
     */
    @ResponseBody
    @RequestMapping(value = "/tzRequestTest",method = {RequestMethod.POST})
    public ResponseResult indexJsonRequest(@Valid @RequestBody TzBidRequest request) throws IOException, ExecutionException, InterruptedException {
        log.info("========="+ JSONObject.toJSONString(request.toString()));
        TzBidResponse bidResponse;
        bidResponse = douMengJsonService.getDouMengDataByJson(request);
      //  bidResponse = jiaLiangJsonService.getJiaLiangDataByJson(request);
        if (null!=bidResponse.getId()){
            return new ResponseResult(bidResponse);
        }else {
            return new ResponseResult();
        }
    }
}
