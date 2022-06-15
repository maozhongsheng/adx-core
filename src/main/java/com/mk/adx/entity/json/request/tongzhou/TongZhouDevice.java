package com.mk.adx.entity.json.request.tongzhou;

import lombok.Data;

@Data
public class TongZhouDevice {
    private int device_type;//设备类型：1 – 手机2 – 平板99 - 其他
    private String make;//手机制造商
    private String brand;//手机品牌
    private String model;//手机型号
    private int os;//操作系统类型：0 – 未知1 – Android– iOS99 – 其
    private String osv;//操作系统版本号
    private int os_api_level;//操作系统 API Level，Android必填
    private String imei;//imei，Android 必填
    private String imei_md5;//imei md5，Android 必填
    private String android_id;//android id，Android 必填
    private String android_id_md5;//android
    private String oaid;//oaid，Android 填写
    private String vaid;//vaid，Android 填写
    private String caid;//中国广告协会互联网广告标识
    private String mac;//mac，Android 必填
    private String mac_md5;//mac md5，Android 必填
    private String imsi;//imsi，Android 填写
    private String idfa;//idfa，iOS 必填
    private String idfa_md5;//idfa md5，iOS 必填
    private String openudid;//openudid，iOS 必填
    private String idfv;//idfv，iOS 填写
    private int screen_width;//屏幕宽，单位：像素
    private int screen_height;//屏幕高，单位：像素
    private double density;//屏幕分辨率，例如：1.0
    private int dpi;//dpi
    private int ppi;//ppi
    private int orientation;//手机横竖屏： 0 - 未知 1 - 竖屏 2 - 横屏
    private int network;//网络类型： 0 – 未知 1 – 以太网 2 – 2G 3 – 3G 4 – 4G 5 – 5G 98 – wifi 99 – 其他
    private int carrier;//运营商类型： 0 – 未知 1 – 中国移动 2 – 中国联通 3 – 中国电信 99 – 其他
    private String country;//国家
    private String language;//语言
    private String timezone;//时区
    private int geo_type;//坐标类型： 1 - GPS 2 - 国家测绘局坐标系 3 - 百度坐标系 默认 GPS
    private double get_longitude;//经度
    private double geo_latitude;//纬度
    private String ssid;//wifi 的 ssid
    private String wifi_mac;//wifi 的 mac 地址
    private String ip;//客户端 IP
    private String ua;//客户端 User Agent


}
