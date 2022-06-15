package com.mk.adx.entity.json.request.Index;


import lombok.Data;

/**
 *  音代克斯-index
 *
 * @author gj
 * @version 1.0
 * @date 2021/8/30 15:21
 */
@Data
public class IndexBidRequest {
    private String requestId;//自定义的请求 id，开发者自行生成需，保证其唯一性
    private String apiVersion;//此 API 的版本例：1.0
    private String userAgent;//User-Agent
    private String ip;//设备的 ip，用于定位
    private IndexApp appInfoParam;//app 对象
    private IndexDevice deviceInfoParam;//移动设备的信息。需 供字段详见:device 对象
    private IndexAdslot adSlotInfoParam;//广告位的信息
    private boolean isSupportDp;//是否支持 deeplink，如果支持，需要填写 true
}
