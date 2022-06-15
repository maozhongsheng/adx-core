package com.mk.adx.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ad-ssp")
public interface SspClient {
    /*
     *  根据广告位id查询媒体id
     **/
    @RequestMapping(value = "/ad-ssp/api/mediaByTagid",method = RequestMethod.POST)
    String getmediaId(String SlotId);
    /*
     *  根据媒体id查询代理商id
     **/
    @RequestMapping(value = "/ad-ssp/api/agentByMediaId",method = RequestMethod.POST)
    String getagentId(String MediaId);
}
