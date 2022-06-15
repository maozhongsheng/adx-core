package com.mk.adx.entity.json.request.mangguo;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/27 16:33
 */
@Data
public class MangGuoDevice {
    private String imei;//Android 设备唯一标识码IMEI
    private String imei_md5;//Android 设备唯一标识码IMEI的md5值
    private String oaid;//Android 设备唯一标识OAID
    private String android_id;//Android 设备ID
    private String android_id_md5;//Android 设备ID的md5值
    private String idfa;//iOS 设备唯一标识
    private String openudid;//iOS 设备唯一标志码，idfa关闭时使用
    private String cookie;//Pc端 cookie字段,m站或者Pc必填，用户唯一id
    private String mac;//Mac地址 必填
    private String os;//操作系统必填，设备操作系统，android，ios, win7, win10等
    private String osver;//操作系统版本必填，操作系统版本号，例如4.4.4
    private String brand;//设备品牌移动设备必填，中文需要UTF-8 编码，如“HUAWEI”
    private String model;//设备型号移动设备必填，中文需要UTF-8 编码，如“D22AP”
    private int sw;//屏幕分辨率宽度必填，单位为像素
    private int sh;//屏幕分辨率高度必填，单位为像素
    private int ppi;//像素每英寸(新增) 必填，以像素每英寸表示的屏幕尺寸
    private String ip;//访问者的IP地址必填，客户端请求使用自身ip；服务端请求使用客户端ip
    private String ua;//访问者的代理浏览器类型必填，广告请求中的ua 需使用系统webview 的ua，请勿自定义ua
    private String referer;//访问者请求的referer 必填，用户设备HTTP请求头中的Referer字段
    private int connection_type;//网络连接类型移动端必填，0-无网络, 1-WIFI，2-3G，3-4G，4-2G，5-5G
    private int carrier_type;//移动运营商类型移动端必填，-1-未知，0-中国移动（GSM），1-中国联通（GSM），2-中国移动（TD-S），3-中国电信（CDMA），4-互联网电视，6-中国联通（WCDMA)
    private int device_type;//设备类型必填，设备平台类型，1-PC,21-安卓H5平板，22-安卓H5手机，23-苹果H5平板，24-苹果H5手机，31-安卓APP平板，32-安卓APP手机，33-苹果APP平板，34-苹果APP手机
    private int orientation;//横竖屏状态必填，0-未知，1-竖屏，2-横屏
    private float lg;//GPS坐标经度GCJ02国家测绘局坐标（火星坐标系）
    private float lt;//GPS坐标纬度GCJ02国家测绘局坐标（火星坐标系）
    private String pkgname;//客户端应用包名移动端必填；pc或者wap站填写主站名
    private String app_version;//客户端版本移动端必填，请正确填写
    private String ssid;//无线网ssid名称移动端必填，无线网ssid名称，如获取不到可传空（影响填充）；例如：wifi ssid MGSSID
    private String wifi_mac;//wifi路由器MAC地址移动端必填，WIFI路由器MAC地址，如获取不到可传空(影响填充）；例如：wifi mac地址 20:a6:cd:7e:e3:60
    private String rom_version;//手机ROM的版本移动端必填，手机ROM版本，如获取不到可传空(影响填充）
    private String sys_compling_time;//系统编译时间移动端必填，系统编译时间（getLong("ro.build.date.utc") * 1000），如获取不到可传空(影响填充)；例如：系统编译时间豪秒数 1545362006000，
    //以下参数为广协CAID方案所需参数
    private String hardware_machine;//如“iPhone10,3”, 仅ios需要回传，安卓可不填写该字段，获取方式参考 附录8.1
    private String startup_time;//手机开机时间手机开机时间，如"1596270702.486691"，仅ios需要回传，安卓可不填写该字段
    private String mb_time;//系统版本更新时间系统版本更新时间，如"1596632457.155983"，仅ios需要回传，安卓可不填写该字段
    private String country_code;//local地区local地区，如“CN”，仅ios需要回传，安卓可不填写该字段
    private String carrier_name;//运营商名称运营商名称，如“中国移动”，仅ios需要回传，安卓可不填写该字段
    private int mem_total;//内存空间，字节系统总内存空间，单位：字节，如“17179869184” ，仅ios需要回传，安卓可不填写该字段
    private int disk_total;//磁盘总空间，字节磁盘总空间，单位：字节，如“250685575168” ，仅ios需要回传，安卓可不填写该字段
    private String local_tz_name;//时区local时区，如"Asia/Shanghai"，仅ios需要回传，安卓可不填写该字段
    private String hardware_model;//设备model D22AP
    private String os_version;//系统版本14.0
    private String language;//语言设备设置的语言：中文、英文、其他
    private String phone_name;//设备名称（MD5) MD5(X的iphone )的值，如“ce9ba5a0128991a784d0187d86189b5d”，仅ios需要回传，安卓可不填写该字段
    private int auth_status;//广告授权情况(可选字段) 广告标识授权情况，如“3”（代表authorized），仅ios需要回传，安卓可不填写该字段;
    private int cpu_num;//cpu数目（可选字段） CPU数目，如 4，仅ios需要回传，安卓可不填写该字段
    private String hms;//华为应用市场的版本号(只针对华为手机)必填，具体客户端获取代码见(3)
    private String ag;//华为AG版本号(只针对华为手机)
    private String maker;//手机硬件厂商(只针对华为手机)

}
