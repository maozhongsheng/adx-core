package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

/**
 * 设备对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class SdDevice {
    private String ua;//user agent(Browser user agent string)请保持原值不要转义或者自定否则会影响广告填充
    private SdGeo geo;//地理位置对象
    private String ip;//客户端ip（IPv4）
    private Integer devicetype;//设备类型：0-未知，1-手机，2-平板
    private String make;//制造厂商,如“apple”“samsung”“huawei“，默认为空String
    private String model;//设备型号，例如 "iphone"
    private String os;//操作系统,取值如下：Windows: "windows",Android: "android",iPhone: "ios",苹果电脑: "mac"
    private String osv;//操作系统版本号,如"4.1", "xp"等
    private int osl;//安卓系统版本系统级别
    private int h;//屏幕宽度, 像素
    private int w;//屏幕高度, 像素
    private int ppi;//屏幕大小
    private Double dip;//移动设备屏幕物理像素密度，常见的取值 1.5，1.0
    private int carrier;//网络运营商（46000-移动  46001-联通   46003-电信）
    private int connectiontype;//网络连接类型 0- 未知 1- Ethernet以太网 2- WIFI 3- 移动网络-2G 4- 移动网络-2G 5- 移动网络-3G 6- 移动网络-4G 7- 移动网络-5G
    private String did;//imei，安卓设备
    private String didmd5;//imei md5 值，安卓设备
    private String oaid;//oaid，安卓设备
    private String oaidmd5;//oaid md5 值，安卓设备
    private String dpid;//android id，安卓设备
    private String dpidmd5;//android id md5，安卓设备
    private String mac;//mac，安卓设备
    private String macmd5;//mac md5，安卓设备
    private String idfa;//idfa，IOS设备
    private String idfamd5;//idfa md5，IOS设备
    private String caid;//中国广告协会互联网广告标识（CAID），idfa 获取不到必填
    private String openudid;//openudid，iOS 设备必填
    private String idfv;//IOS 设备当前的 IDFV 值NSString * IDFV = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
    private String imsi;//imsi，安卓设备
    private int orientation;//横竖屏 0-未知，1-竖屏，2-横屏
    private String ssid; //当前连接 WiFi 的 SSID
    private String bssid;//当前连接 WiFi 的 MAC
    private String referer; //客户端 Referer
    private String serialno; //移动设备序列号
    private String syst; //系统编译时间，时间戳，精确到毫秒，如：1545362006000,获取不到可以为空
    private String romv;
    private String startup_time;
    private String mb_time;
    private int cpu_num; //CPU数⽬，如“4” ，仅ios需要回传，安卓可不 填写该字段
    private long disk_total; //磁盘总空间，单位：字节， 如“250685575168” ， 仅ios需要回传，安卓可 不填写该字段
    private long mem_total; //系统总内存空间，单位：字节， 如“17179869184”，仅ios需要回传，安卓可 不填写该字段
    private int auth_status; //广告标识授权情况，如“3 ”(代表authorized) ，仅ios需要回传，安卓可不填写该字段
    private String appstore_ver; //厂商应用商店版本号(vovi、小米、华为、oppo 等厂商应 用商店) 必填

}
