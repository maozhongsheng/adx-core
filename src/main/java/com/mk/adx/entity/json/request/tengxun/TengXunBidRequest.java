package com.mk.adx.entity.json.request.tengxun;

import lombok.Data;

/**
 * 腾讯-优量汇-总请求
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/26 14:21
 */
@Data
public class TengXunBidRequest {

    private String api_version;//协议版本，目前仅支持3.0及以上版本，建议使用最新版本
    private int support_https;//是否支持HTTPS并且需要HTTPS资源
    private int support_app_store;//是否请求厂商应用商店下载类广告。当该字段填1时，联盟后台可能返回厂商应用商店下载类广告，回包字段见market_url。
    private String pos;//告位相关信息。该参数的值是一个经过url编码的json对象，见pos JSON对象
    private String media;//媒体相关信息。该参数的值是一个经过url编码的json对象，见media JSON对象
    private String device;//用户设备相关信息。该参数的值是一个经过url编码的json对象，见device JSON对象
    private String network;//用户设备网络相关信息。该参数的值是一个经过url编码的json对象，见network JSON对象
    private String geo;//用户设备实时地理位置相关信息。该参数的值是一个经过url编码的json对象，见geo JSON对象

}
