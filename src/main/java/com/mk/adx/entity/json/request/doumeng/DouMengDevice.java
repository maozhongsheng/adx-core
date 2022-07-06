package com.mk.adx.entity.json.request.doumeng;

import lombok.Data;

@Data
public class DouMengDevice {
    private String idfa;//IOS 设备唯一标识码
    private String imei;//安卓设备唯一标识码
    private String imeiMd5;//String	Md5加密的imei
    private String imsi;//国际移动用户识别码
    private String oaid;//Android10 及以上版本必填
    private String oaidMd5;//md5加密oaid
    private String mac;//设备 MAC 地址
    private String androidId;//安卓设备 ID
    private String model;//设备型号
    private String vendor;//设备厂商
    private String screenHeight;//设备屏幕高度
    private String screenWidth;//设备屏幕宽度
    private String osType;//操作系统类型：android,ios
    private String osVersion;//操作系统版本
    private String deviceType;//设备类型：flat(平板),mobile(手机)
    private String screenOrientation;//横竖屏：horizontal(横屏),vertical(竖屏)
    private String brand;//手机品牌
    private String dpi;//手机屏幕dpi
    private String density;//手机屏幕密度
    private String ppi;//像素密度
    private String serialNo;//移动设备序号，Build.Serial Android 系统用 androidId 代替，IOS 系统可以忽略
    private Integer androidApi;//安卓API Level,安卓系统必传
    private String appStoreVersion;//
    private String bootMark;//
    private String updateMark;//
    private long osCompilingTime;//系统编译时间戳,单位：秒
    private long osUpdateTime;//系统更新时间戳,单位：秒
    private long osStartupTime;//系统启动时间戳,单位：秒
    private String phoneName;//设备名称 例如：张三的 iPhone
    private long sysMemory;//系统内存，单位字节
    private long sysDiskSize;//硬盘容量，单位字节
    private Integer cpuNum;//设备的 cpu 核心数量
    private String hardwareMachine;//设备 machine 值，系统型号
    private String hmsVer;//华为安卓设备的 HMS Core 的版本号，保留原始值。华为手机必填
    private String hwagVer;//华为安卓设备的 AG（应用市场）的版本号，保留原始值。华为手机必填
    private String miuiVersion;//miui 版本(小米设备必须)



}
