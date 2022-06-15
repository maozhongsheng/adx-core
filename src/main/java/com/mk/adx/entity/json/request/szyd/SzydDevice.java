package com.mk.adx.entity.json.request.szyd;

import lombok.Data;

/**
 * algorix
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class SzydDevice  {
    private String os;//Device operating system (e.g., "iOS", "Android")
    private String osv;//Device operating system version (e.g., "3.1.2")
    private String did;//Android 设备的 IMEI
    private String didmd5;//Android 设备的 IMEI 使⽤ MD5 后的值
    private String didsha1;//Android 设备的 IMEI 使⽤ SHA1 后的值
    private String oid;//Android 设备的 OAID 明⽂
    private String dpid;//Android 设备 Android ID
    private String dpidmd5;//Android 设备 Android ID 使⽤ MD5 后的值
    private String dpidsha1;//Android 设备 Android ID 使⽤ SHA1 后的值
    private String ifa;//iOS 设备的 IDFA，需⼤写
    private String ip;//设备 IP 地址
    private String ipv6;//设备 IPv6 地址
    private String ua;//User Agent，需使⽤系统 webview 的 ua
    private int connectiontype;//设备链接类型，可选值参考附录
    private int devicetype;//设备类型，可选值参考附录
    private String make;//设备制造商（例如：HUAWEI）
    private String model;//设备硬件型号（例如：honor）
    private String hwv;//硬件型号版本（例如：FRD-AL00）
    private String carrier;//运营商名称，可选值参考附录
    private String macmd5;//设备 MAC 地址的 MD5 后的值
    private String macsha1;//设备 MAC 地址的 SHA1 后的值
    private int h;//屏幕的物理⾼度，单位：px
    private int w;//屏幕的物理宽度，单位：px
    private int ppi;//每英⼨像素个数，单位：ppi
    private SzydGeo geo;
    private String ssid;//⽆线⽹ ssid 名称
    private String wifi_mac;//wifi 路由器的 Mac 地址
    private String rom_version;//⼿机 ROM 版本
    private String sys_compiling_time;//系统编译时间 getLong(“ro.build.date.utc”) * 1000

}
