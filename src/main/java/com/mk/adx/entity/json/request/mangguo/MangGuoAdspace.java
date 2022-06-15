package com.mk.adx.entity.json.request.mangguo;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/27 16:29
 */
@Data
public class MangGuoAdspace {
    private String adspace_id;//广告位id 必填，代码位id 在ssp 平台获取
    private boolean support_dl;//是否支持deeplink 必填，是否支持deeplink唤醒，true：支持，false不支持
    private boolean support_wx;//是否支持微信小程序唤醒可选，是否支持小程序唤醒，true：支持，false不支持，默认不支持，不下发小程序落地页
    private int play_time;//可选，视频广告最大时长（秒数）
    private int adlen;//可选，广告位最大广告支数
    private int width;//可选，该参数在联盟后台用来选取合适尺寸的广告，不要求与用户设备上真实的广告位宽严格一致
    private int height;//可选，该参数在联盟后台用来选取合适尺寸的广告，不要求与用户设备上真实的广告位高严格一致
    private int min_cpm;//最低的cpm出价可选，最低的cpm出价，单位为分

}
