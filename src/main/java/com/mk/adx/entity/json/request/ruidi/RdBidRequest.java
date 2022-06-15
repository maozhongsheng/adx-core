package com.mk.adx.entity.json.request.ruidi;


import lombok.Data;

/**
 * 瑞迪
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/25 15:21
 */
@Data
public class RdBidRequest {
    private String request_id	;//请求 id，媒体侧生成，需确保全局唯一，最大长度为 36 位
    private RdVersion api_version	;//广告位信息
    private RdAd adslot;//API 版本信息
    private RdApp app;//应用参数信息
    private RdDevice device;//设备参数信息
    private RdNetWork network;//移动网络参数信息
    private RdGps gps;//GPS 参数信息
    private RdWifi wifi;//无线网络参数信息
    private Integer request_protocol_type;//是否支持返回 https 物料。(1：http，2：https，默认 1)
    private boolean support_deeplink;//是否支持 deeplink 唤醒，(true：支持，false：不支持，默认false)
    private RdWeb web;//Web 页面特征


}
