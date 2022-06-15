package com.mk.adx.entity.json.request.yuanyin;

import lombok.Data;

/**
 * 缘音总请求
 */
@Data
public class YyBidRequest {
    private String reqId; //⼴告唯⼀请求ID，请尽量保证每次请求唯⼀
    private String apiVer;//对接版本号，默认1.0
    private String mediaId;//媒体ID
    private String posId;//⼴告位ID
    private YyApp app;//应⽤信息
    private YyDevice device;//设备信息
    private YyNetWork network;//⽹络信息
    private YyProfile profile;//⽤户信息
}
