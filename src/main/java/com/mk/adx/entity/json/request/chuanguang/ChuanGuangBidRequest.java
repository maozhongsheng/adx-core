package com.mk.adx.entity.json.request.chuanguang;


import lombok.Data;

/**
 * 传广总请求
 *
 * @author gj
 * @version 1.0
 * @date 2021/10/25 15:21
 */
@Data
public class ChuanGuangBidRequest {
    private int testMode;//是否为测试模式；测试模式不计入收益(0 -> 正式 ；1 -> 测试)
    private int adCount;//请求的广告数量,返回小于等于n条的广告(1)
    private String apiVersion;//协议版本号(1.2.2)
    private String sspId;//媒体Id
    private String adId;//广告位Id 由传广分配(目前支持banner、开屏、插屏、信息流、视频广告)
    private int adType;//广告位类型(1：开屏 2：插屏 3：Banner（暂不支持） 4：信息流 5：视频广告（暂不支持）)
    private int adWith;//广告宽度，单位为像素(320)
    private int adHeight;//广告高度，单位为像素(480)
    private ChuanGuangDevice device;//手机终端参数


}
