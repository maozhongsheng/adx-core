package com.mk.adx.entity.json.request.algorix;


import lombok.Data;

/**
 * algorix
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class AlgorixDevice {
    private String ua;//客户端 User Agent
    private String ip;//客户端公网 ip
    private String carrier;//运营商名，"ChinaMobile", "ChinaUnicom", "ChinaTelecom" 等，未知填写 "unknown"
    private String make;//Device make (e.g., "Apple")
    private String model;//Device model (e.g., "iPhone")
    private String os;//Device operating system (e.g., "iOS", "Android")
    private String osv;//Device operating system version (e.g., "3.1.2")
    private String hwv;//Hardware version of the device (e.g., "5S" for iPhone 5S)
    private int w;//Physical width of the screen in pixels
    private int h;//Physical height of the screen in pixels
    private int ppi;//Screen size as pixels per linear inch
    private float pxratio;//The ratio of physical pixels to device independent pixels
    private int connectiontype;//网络类型，具体值请参考枚举 ConnectionType
    private int devicetype;//设备类型，具体值请参考枚举 DeviceType
    private String ifa;//iOS 的 IDFA，Android 的 GAID(中国不支持GSF的设备，可以不传此项)
    private String didsha1;//Hardware device ID (e.g., IMEI); hashed via SHA1.
    private String didmd5;//Hardware device ID (e.g., IMEI); hashed via MD5.
    private String dpidsha1;//Platform device ID (e.g., Android ID); hashed via SHA1.
    private String dpidmd5;//Platform device ID (e.g., Android ID); hashed via MD5.
    private String macsha1;//MAC address of the device; hashed via SHA1.
    private String macmd5;//MAC address of the device; hashed via MD5.
    private String mccmnc;//格式为 MCC-MNC (e.g., "460-00", "460-01")
    private AlgorixGeo geo;
    private AlgorixDeviceExt ext;

}
