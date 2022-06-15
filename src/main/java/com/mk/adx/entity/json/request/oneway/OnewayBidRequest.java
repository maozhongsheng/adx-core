package com.mk.adx.entity.json.request.oneway;

import lombok.Data;

/**
 * one way总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/20 11:21
 */
@Data
public class OnewayBidRequest {

    private static final long serialVersionUID = 1L;

    private String apiVersion;//对接 API 版本, 建议使用最新版本
    private String placementId;//广告位 ID 由平台分配获取
    private String bundleId;//应用包名 eg: com.app.xyx
    private String bundleVersion;//应用版本 eg: 2.1.0
    private String appName;//应用名称
    private String subAffId;//应用子渠道标识
    private String pagecat;//(信息流)app当前page类型 eg: 体育-NBA
    private String deviceId;//设备广告ID (iOS填IDFA, Android填Google Play services Advertising ID, Android获取不到可不填)
    private String deviceIdMd5;//MD5(deviceId) (小写) 值
    private String imei;//Android必填
    private String imeiMd5;//MD5(imei) (小写) 值
    private String androidId;//Android必填
    private String oaid;//(andorid版本>=10必填) 移动安全联盟 匿名设备标识符
    private int apiLevel;//Android API level (iOS不填)
    private int os;//操作系统 1: Android 2: iOS
    private String osVersion;//操作系统版本 如: 8.0.0
    private String connectionType;//网络连接类型
    private int networkType;//网络类型 查看取值 值为0会影响填充
    private String networkOperator;//移动网络运营商编码 eg: 46000
    private String simOperator;//SIM卡运营商编码 格式: MCC+MNC eg: 46000
    private String imsi;//IMSI 国际移动用户识别码, 格式: MCC+MNC+MSIN 组成 eg: 460001357924680
    private String deviceMake;//设备产商 eg: HUAWEI
    private String deviceModel;//设备型号 eg: FLA-AL10
    private int deviceType;//设备类型: Phone/手机=1, Tablet/平板=2, TV/智能电视=3, PC=4
    private String orientation;//设备屏幕方向 H：横屏, V: 竖屏
    private String serialno;//设备序列号, 全部大写
    private String mac;//设备MAC地址 eg: 6c:5c:14:2b:f4:b9
    private String wifiBSSID;//WIFI MAC地址 eg: 74:c3:30:0f:64:3c
    private String wifiSSID;//WIFI用户名(SSID名称) eg: FAST_643C
    private int screenWidth;//设备屏幕(分辨率)宽 eg: 1080
    private int screenHeight;//设备屏幕(分辨率)高 eg: 1920
//    private int screenDensity;//设备屏幕像素密度 eg: 432
//    private float screenInch;//设备屏幕尺寸 eg: 5.8
    private String userAgent;//客户端webview User-Agent 格式可查看请求参数示例  请确保上报参数userAgent, 上报请求Header User-Agent 和广告请求参数userAgent信息一致以免判定为无效上报
    private String ip;//客户端 ip4
    private String ip6;//客户端 ip6
    private String language;//语言 eg: zh_CN
    private String timeZone;//时区 eg: GMT+08:00
    private float longitude;//经度 eg: 116.43558
    private float latitude;//纬度 eg: 39.93548
    private String installedApp;//已安装应用列表, 多个用逗号隔开 eg: com.abc.app,com.def.app
    private String appStoreVersion;//手机应用商店版本(推荐)
    private String hmsVersion;//华为 HMS 版本 (推荐)
    private String bootMark;//采集方式参考 原值示例：iOS：1623815045.970028 Android: ec7f4f33-411a-47bc-8067-744a4e7e0723
    private String updateMark;//采集方式参考 原值示例：iOS：1581141691.570419583 Android：1004697.709999999

}
