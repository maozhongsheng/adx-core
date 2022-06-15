package com.mk.adx.entity.json.request.simeng;

import lombok.Data;

/**
 * 思盟-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class SmBidRequest {
    private String requestId; //请求ID
    private SmDevice device;  //设备相关信息
    private SmNetWork network;  //网络相关信息
    private String channelId;  //代码位id
}
