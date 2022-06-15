package com.mk.adx.entity.json.request.changqing;

import lombok.Data;

/**
 * 长青总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/10/15 15:21
 */
@Data
public class CqBidRequest {
    private String requestId; //请求ID
    private CqDevice device;  //设备相关信息
    private CqNetWork network;  //网络相关信息
    private String channelId;  //代码位id
}
