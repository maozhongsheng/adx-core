package com.mk.adx.entity.json.request.yiqi;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/11/03 09:51
 */
@Data
public class YQDevice {
    private String uuid;//设备唯一标识，对于 IOS 设备，该值为 idfa, 对于 Android 设备，该值为 imei
    private int os;//用户设备的操作系统类型。取值：0：Android 1：Ios 2：Windows Phone 3：Others
    private String osv;//用户设备的操作系统版本号，必须真实来源于客户端。如，9.0
    private int osc;//Android 系统 API level：
    private String brand;//手机品牌，必须真实来源于客户端。
    private String model;//用户设备型号，必须真实来源于客户端。
    private String oaid;//移动安全联盟推出的匿名设备标识符。Android10 及以上必
    private String imsi;//如果没有完整的 imsi,可以截取 imsi 的前五位
    private int deviceType;//用户设备类型。取值：0=未知；1=平板；2=手机；
    private String androidId;//Android Id，安卓设备必填
    private String mac;//mac 地址，安卓设备必填
    private int sw;//用户设备屏幕的宽度，以像素为单位
    private int sh;//用户设备屏幕的高度，以像素为单位
    private Double dpi;//用户设备屏幕每英寸有多少个像素
    private String ssid;//无线网 ssid 名称，如获取不到可传空
    private String wifiMac;//WIFI 路由器 MAC 地址，如获取不到可传空
    private String romVersion;//手机 ROM 版本，如获取不到可传空
    private String sysComplingTime;//系统编译时间:时间戳，如获取不到可传空
    private String hms;//华为手机需要 HMS Core 版本号如获取不到可传空
    private String asv;//应用市场版本号如获取不到可传空
    private int dst;//设备启动时间（秒） 获取不到可传空
    private int dnm;//设备名称的 MD5 值，MD5（*的 iphone）获取不到可传空
    private int dms;//物理内存（byte）获取不到可传 0
    private int dhd;//硬盘大小（byte）获取不到可传 0
    private int sut;//系统更新时间（秒）获取不到可传 0
    private String tz;//时区 获取不到可传空
}
