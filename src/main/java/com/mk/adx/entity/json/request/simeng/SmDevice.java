package com.mk.adx.entity.json.request.simeng;

import lombok.Data;

@Data
public class SmDevice {
    private String idfa;  // IOS设备唯一标识码
    private String imei;  // 安卓设备唯一标识码
    private String mac;  // 设备Wifi网卡MAC地址
    private String androidId;  // 安卓设备ID
    private String model;  // 设备型号
    private String vendor;  // 设备厂商
    private int screenWidth;  // 设备屏幕宽度
    private int screenHeight;  // 设备屏幕高度
    private int osType;  // 操作系统类型   1—ANDRIOD    2—IOS
    private String osVersion;  // 操作系统版本
    private int deviceType;  // 设备类型  1—手机  2 —平板
    private String ua;  // User-agent
    private int ppi;  // 屏幕大小(单位ppi,每英寸所有的像素)
    private int screenOrientation;  // 横竖屏，0：未知，1：竖屏，2：横屏
    private String brand;  // 手机品牌
    private String imsi;  // imsi如不填写可能会影响变现能力
    private String oaid;  // Android10及以上传递
    private String bootMark;  // 系统启动标识iOS例⼦:1623815045.970028Android例⼦:ec7f4f33-411a-47bc-8067-744a4e7e0723
    private String updateMark;  // 系统更新标识iOS例⼦:1581141691.570419583Android例⼦:1004697.709999999
}
