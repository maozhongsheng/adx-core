package com.mk.adx.entity.json.request.onen;

import lombok.Data;

import java.util.List;

/**
 * 设备对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:53
 */
@Data
public class OneNDevice {
    private String deviceId;
    private String imei;
    private String imeiMd5;
    private String oaid;
    private String openUdid;
    private String ssid;
    private String wifiMac;
    private String phoneName;
    private String powerOnTime;
    private String mac;
    private String imsi;
    private int deviceType;
    private String os;
    private String osVersion;
    private String vendor;
    private String model;
    private String language;
    private int connType;
    private int operatorType;
    private int screenWidth;
    private int screenHeight;
    private int orientation;
    private float dpi;
    private String romVersion;
    private String sysComplingTime;
    private int bootTimeSec;
    private int osUpdateTimeSec;
    private int diskSize;
    private int batteryStatus;
    private int batteryPower;
    private int memorySize;
    private int cpuNumber;
    private float cpuFrequency;
    private String modelCode;
    private String timeZone;
    private int lmt;
    private int laccu;
    private String caid;
    private String bootMark;
    private String updateMark;
    private String appStoreVersion;
    private String hmsVersion;

}
