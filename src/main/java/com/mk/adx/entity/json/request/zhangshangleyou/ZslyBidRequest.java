package com.mk.adx.entity.json.request.zhangshangleyou;

import lombok.Data;

/**
 * 掌上乐游总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/23 14:21
 */
@Data
public class ZslyBidRequest {
    private String api_type;//接入方式：API:服务器对接(默认),SDK:客户端对接
    private String requestId; //请求ID
    private String slot_id;  //广告位ID，请联系商务提供
    private int device_type;//设备类型 1:手机(含iTouch) 2:平板
    private String os_type;//系统类型:ANDROID、IOS
    private String os_version;//系统版本号,格式:a.b.c,如:4.3.3
    private String device_id;//设备标识:用于设备唯一标识(必须明文)对应IOS 设备,该值为idfa,对于android设备,该值为imei,安卓10 无imei,用oaid
    private String imei;//国际移动设备识别码原始值,32 位小写的MD5 值,原始值与md5 值二选一,必传
    private String imei_md5;//
    private String oaid;//匿名设备标识符,安卓10 无imei,用oaid32 位小写的MD5 值,原始值与md5 值二选一,必传
    private String oaid_md5;//
    private String android_id;//Android ID 手机唯一标识
    private int api_level;//安卓API 等级(IOS 不填)
    private String serialno;//移动设备序列号
    private int dpi;//设备屏幕像素密度如:160同ios 系统的ppi
    private String hms_core;//HMS Core 版本号，实现被推广应用的静默安装依赖HMS Core 能力。华为设备必填。verCodeOfHms >= 50200100
    private String ag_version;//AG 版本号，应用市场版本号。与下载类广告的转化路径有关。华为设备必填。verCodeOfAG >= 110002000
    private String rom_version;//设备ROM 版本，如获取不到可不传
    private long sys_compiling_time;//系统编译时间戳(ro.build.date.utc)精确至毫秒
    private int is_deeplink;//是否支持deep_link。默认支持0:不支持1:支持
    private String mac;//本机无线网卡的MAC 地址
    private String ssid;//SSID ⽆线⽹名称
    private String bssid;//所连接的WIFI 设备的MAC 地址,路由器WIFI 的MAC 地址
    private String vendor;//设备厂商
    private String brand;//品牌
    private String model;//设备型号
    private int orientation;//屏幕方向0:未知1:竖屏2:横屏
    private int w;//屏幕宽
    private int h;//屏幕高
    private double density;//设备屏幕密度,如：2.0
    private double screen_size;//屏幕尺寸,例:4.7 , 5.5,单位:英寸
    private String ip;//客户端IPv4 地址 注意：ip 为外网ip
    private String ip_v6;//IP6 地址
    private String ua;//浏览器User-Agent，空格不要转义，请求广告、上报监测的ua，使用Webview 或浏览器ua，不要使用系统原始ua，或自定义ua，请求广告、上报监测中的ua 要保持一致，否则数据会被过滤
    private String connection_type;//网络连接类型UNKNOWN(未知)、CELL_UNKNOWN(蜂窝数据)、2G、3G、4G、5G、WIFI、ETHERNET(以太网)
    private String operator_type;//运营商类型CMCC(中国移动)CUCC(中国联通)CTCC(中国电信)OTHER(OTHER)
    private String imsi;//国际移动用户识别码
    private String mcc;//移动国家码,如:460
    private String mnc;//移动网络吗,如:00
    private int coordinate_type;//GPS 坐标类型1:全球卫星定位系统坐标系 2:国家测绘局坐标系 3:百度坐标系
    private double lng;//经度
    private double lat;//纬度
    private double location_accur;//经纬度精度半径，单位为米。该参数会用于基于地理位置的广告的定向，正确填写有助于提高流量变现效果
    private long coord_time;//GPS 时间戳信息单位:毫秒
    private String country;//国家编码(ISO-3166-1/alpha-2),例CN
    private String language;//设备语言(ISO-639-1/alpha-2),例zh
    private String timezone;//设备操作系统时区; 例：Asia/Shanghai
    private String idfa;//ios 设备唯一标识码 IOS 系统填写
    private String idfv;//ios 设备的应用开发商标识符 IOS 系统填写
    private String openudid;//ios 设备的openUdid 值 IOS 系统填写
    private int ppi;//设备屏幕像素密度，如：160同android 的dpi IOS 系统填写
    private String sys_update_time;//系统更新时间(秒级时间戳)，如：1595214620
    private String sys_boot_time;//设备开机时间(秒级时间戳)
    private String sys_name;//设备的名称(*的iPhone);
    private int sys_disk_size;//手机磁盘总空间(单位:字节);
    private int sys_memory_size;//设备运行内存，单位:字节;
    private int sys_cpu_num;//设备cpu 核数;
    private double sys_cpu_freq;//手机CPU 频率，单位:GHz
    private int sys_idfa_policy;//广告标识授权情况，是否允许获取IDFA0:未确定1:受限制2:被拒绝3:授权
    private int battery_state;//设备当前充电状态;1:未知状态，2:正在充电，3 放电4:未充满，5:满状态
    private int battery;//设备电量; 60 代表60%，80 代表80%

}
