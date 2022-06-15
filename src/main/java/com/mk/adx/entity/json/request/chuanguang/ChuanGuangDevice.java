package com.mk.adx.entity.json.request.chuanguang;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/10/25 15:21
 */
@Data
public class ChuanGuangDevice {
    private String make;//手机品牌
    private String model;//手机型号
    private int os;//操作系统(1：Android 2：Ios)
    private String osVer;//操作系统版本(传操作系统具体版本，例如：Android10 系统版本为 10)
    private int deviceType;//终端设备类型(0 -> 手机 ； 1 -> 平板)
    private int operatorType;//运营商类型(1：移动 2：联通 3：电信 0：未知)
    private String operatorNop;//运行商代号
    private int screenWidth;//手机屏幕宽度
    private int screenHeight;//手机屏幕高度
    private Double deny;//手机密度
    private int dpi;//手机dpi值
    private String imsi;//设备imsi值
    private String imei;//手机真实物理序列号
    private int networkType;//用户网络类型
    private String mac;//mac 地址(android)
    private String androidId;//Android设备的Android ID
    private String appName;//app名称
    private String apkVersion;//apk版本
    private String packageName;//媒体的包名，即本app的包名
    private String idfa;//设备唯一标识码
    private String ip;//终端设备外网的IP地址
    private String userAgent;//终端设备实际 webview User-Agent 值
    private String lon;//手机所在位置的经度
    private String lat;//手机所在位置的纬度
    private String bootMark;//系统启动标识
    private String updateMark;//系统更新标识
}
