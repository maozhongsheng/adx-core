package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

import java.util.List;

/**
 * 设备信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/12/8 14:28
 */
@Data
public class YuekeDevice {
    private String ua;//移动设备的 WebView User- Agent，务必是合理有效的
    private YuekeGeo geo;//该字段务必填写，目前我们仅接受 中国在内的地区
    private String ip;//设备的公网 IPV4 地址，该地址务必是移动客户端的网络地址
    private String ipv6;//设备的公网 IPV6 地址，该地址务必是移动客户端的网络地址
    private int devicetype;//pc:2, tv:3, 手机:4, 平板:5, 机顶盒:7, 未知:0
    private String make;//例如：HUAWEI
    private String model;//设备 machine 信息，例如：iPhone10,3
    private String os;//苹果设备:“ ios ” 安卓设备: " android "
    private String osv;//例如: 1.0.0. 1.0.1
    private String hwv;//苹果设备:“ ios ” 安卓设备: " android "
    private int h;
    private int w;
    private int sw;
    private int sh;
    private int ppi;
    private int dpi;
    private String caid;
    private String caidVer;
    private String ifa;
    private String ifamd5;
    private String ifv;
    private String ifvmd5;
    private String udid;
    private String udidmd5;
    private String did;
    private String didmd5;
    private String dpid;
    private String dpidmd5;
    private String oaId;
    private String mac;
    private String macmd5;
    private String carrier;
    private int connectiontype;
    private String imsi;
    private int orientation;
    private List<String> apps;
    private String appstore;
    private String hms;
    private String startupTime;
    private String updateTime;
    private String country;
    private String language;
    private String timeZone;
    private int memorySize;
    private int diskSize;
    private String phoneNameMd5;
    private int idfaPolicy;
    private String boot_mark;
    private String update_mark;

}
