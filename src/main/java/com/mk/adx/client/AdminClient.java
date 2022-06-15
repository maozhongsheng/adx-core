package com.mk.adx.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("ad-admin")
public interface AdminClient {
    /*
     *  根据广告位id查找分配比例以及广告联盟广告位
     **/
    @RequestMapping(value = "/ad-admin/api/selectRequetBySlotId",method = RequestMethod.POST)
    Map selectRequetBySlotId(String slot_id);
    /*
     *  根据广告联盟广告位查询所有数据
     **/
    @RequestMapping(value = "/ad-admin/api/selectUpperBySlotId",method = RequestMethod.POST)
    Map selectUpperBySlotId(String slot_id);
}
