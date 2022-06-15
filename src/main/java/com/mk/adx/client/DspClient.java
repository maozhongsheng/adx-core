package com.mk.adx.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("ad-dsp")
public interface DspClient {
    /*
     *  DSP根据媒体广告位Id查询所有数据
     **/
    @RequestMapping(value = "/ad-dsp/api/selectDspRta",method = RequestMethod.POST)
    Map getselectDspRta(String slot_id);
}
