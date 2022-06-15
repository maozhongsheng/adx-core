package com.mk.adx.entity.json.request.zxsj;

import lombok.Data;

/**
 * 自旋视界总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2022/5/31 14:21
 */
@Data
public class ZxsjBidRequest {
    private String id;//本次请求唯一标识
    private String slot_id;  //广告位ID，请联系商务提供
    private int slot_width;  //广告位宽度，单位：像素
    private int slot_height;  //广告位高度，单位：像素
    private double bid_floor;  //CPM 底价，单位：分
    private String bid_floor_cur;  //价格单位；USD：美元，CNY：人民币默认：CNY
    private String app_name;  //App的名称
    private String app_package;  //App的包名，需要和在提交注册的AppStoreURL中App的包名⼀致
    private String app_ver;  //App的版本号
    private String app_store_url;  //App的应用商店下载地址
    private String ip;  //公网 ip 地址
    private String user_agent;  //webview 原始user-agent,不支持自定义格式
    private int os_type;  //操作系统类型1：Android，2：iOS
    private String os_version;  //操作系统版本，如：10.2.1
    private String imei;  //Android 必填，IMEI
    private String oaid;  //Android 10以上必填，OAID
    private String android_id;  //Android 必填，Android ID
    private String imsi;  //国际移动用户识别码
    private String idfa;  //iOS 必填，IDFA
    private String idfv;  //iOS 建议，IDFV
    private String openudid;  //iOS 建议，OpenUDID
    private String caid;  //iOS 建议，CAID
    private String mac;  //设备 MAC 地址如：B8:37:65:F1:86:2D
    private String imei_md5;  //IMEI 地址的MD5 值，全部小写
    private String android_id_md5;  //Android ID 的MD5 值，全部小写
    private String mac_md5;  //MAC 地址的 MD5 值，全部小写
    private String vendor;  //设备厂商
    private String brand;  //设备品牌
    private String model;  //设备型号
    private String serialno;  //设备序列号
    private int screen_orientation;  //设备屏幕朝向0：未知，1：竖屏，2：横屏
    private int screen_width;  //设备屏幕宽度，单位：像素
    private int screen_height;  //设备屏幕高度，单位：像素
    private int screen_dpi;  //设备屏幕像素密度，如：160
    private double screen_density;  //设备屏幕密度，如：2.0
    private int connection_type;  //网络连接类型0:未知,1:wifi,2:2g,3:3g,4:4g,5:5g
    private int device_type;  //设备类型1:手机,2:平板,3:智能TV,4:PC
    private int carrier;  //网络运营商类型1:中国移动，2:中国联通，3:中国电信
    private String mccmnc;  //网络运营商ID, MCC+MNC，如:46000
    private String ssid;  //⽆线⽹SSID 名称
    private String wifi_mac;  //WIFI 路由器MAC 地址
    private String rom_version;  //手机 ROM 版本
    private String sys_compiling_time;  //系统编译时间，时间戳
    private String app_store_version;  //应用商店版本号vivo和oppo⼴告必填
    private double longitude;  //经度
    private double latitude;  //纬度
    private String hms;  //鸿蒙内核版本（华为设备必须）
    private String hag;  //应用市场版本（华为设备必须）
    private String device_name;  //设备名称（iOS 设备必须）
    private long startup_time;  //最近启动时间，单位：秒（iOS 设备必须）
    private long upgrade_time;  //最近升级时间，单位：秒（iOS 设备必须）
    private String timezone;  //系统当前时区（iOS 设备必须）
    private long memory;  //物理内存大小，单位：byte（iOS 设备必须）
    private long hard_disk;  //物理硬盘大小，单位：byte（iOS 设备必须）
    private int cpu_num;  //处理器核数（iOS 设备必须）
    private double cpu_freq;  //处理器主频（iOS 设备必须）
    private int idfa_policy;  //IDFA 授权策略（iOS 设备必须）0：未确定1：受限制2：被拒绝3：已授权
    private int battery_status;  //电池充电状态（iOS 设备必须）1：未知状态2：不在充电3：正在充电4：满电状态
    private int battery_power;  //电池电量比例（iOS 设备必须）例如: 60 代表60%，80 代表80%
    private String boot_mark;  //系统启动标识，原值传输
    private String update_mark;  //系统更新标识，原值传输
    private String miui_version;  //MIUI版本（小米设备必须）


}
