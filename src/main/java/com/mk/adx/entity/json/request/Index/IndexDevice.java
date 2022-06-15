package com.mk.adx.entity.json.request.Index;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/8/30 15:21
 */
@Data
public class IndexDevice {
    private String deviceId;//设备 id, android 为androidId;ios 系统为 idfa
    private String imei;//设备 IMEI，若 IOS 设备拿不到则填空
    private String oaid;//oaid 的明文，支持获取 oaid 的Android 设备必传
    //private String ssid;//无线网 SSID 名称
    private String wifiMac;//WIFI 路由器 MAC 地址
    private String mac;//设备 mac 地址
    private String imsi;//IMSI(SIM 卡串号)
    private int deviceType;//设备类型型 Unknown=0;Phone/手机=1;Tablet/平板=2;TV/智能电视=3
    private String os;//Android;IOS
    private String osVersion;//操作系统版本
    private String vendor;//设备厂商，如 Apple
    private String model;//设备型号，如 iPhone5s,
    private String language;//设备设置的语言:中文、英文、其他
    private int connType;//设备的网络类型:Unknown=0;Wifi=1;2G=2;3G=3;4G=4,5G=5
    private int operatorType;//运营商:UNKNOWN_OPERATOR=0;CHINA_MOBILE=1;CHINA_TELECOM=2;CHINA_UNICOM=3;OTHER_OPERATOR = 99 其他运营商
    private int screenWidth;//设备屏宽
    private int screenHeight;//设备屏高
//    private String romVersion;//手机 ROM 版本
//    private String sysComplingTime;//系统编译时间:时间戳（系统更新时间）
//    private int bootTimeSec;//设备最近一次开机时间，IOS 操作系统必传;秒级时间戳
//    private int osUpdateTimeSec;//系统最近一次更新时间，IOS 操作系统必传;秒级时间戳
//    private int diskSize;//手机容量大小，单位:GB 示例:16 .IOS 操作系统必传
//    private int batteryStatus;//电池充电状态，IOS 操作系统必传0 未知 1 不充电 2 充电 3 满电
//    private int batteryPower;//电池电量百分比，IOS 操作系统必传例如:60
//    private int memorySize;//手机运行内存大小，单位 GB; IOS操作系统必传例如:8
//    private int cpuNumber;//手机 CPU 个数，IOS 操作系统必传例如:4
//    private float cpuFrequency;//手机 CPU 频率，单位:GHz IOS 操作系统必传例如:2.2
//    private String modelCode;//设备型号码，IOS 操作系统必传例如:D22AP
//    private String timeZone;//当前设备时区，IOS 操作系统必传例如:28800
//    private int lmt;//是否允许获取 IDFA，IOS 操作系统必传 0:未确定，开发者尚未请求用户许可; 1:受限制，用户可以退出 IDFA 在设备级别的所有应用程序; 2: 被拒绝，用户选择退出IDFA; 3.授权，可以继续获取IDFA
//    private int laccu;//定位精准度，IOS 操作系统必传0: 定位精准，可以获取到小数点4 位及以上;1: 定位不准确
//    private int caid;//ios14 替代 idfa
}
