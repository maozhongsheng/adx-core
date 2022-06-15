package com.mk.adx.entity.json.request.woke;

import lombok.Data;

/**
 * 沃氪
 *
 * @author  mzs
 * @version 1.0
 * @date 2022/4/23 18:21
 */
@Data
public class WoKeBidRequest {
    private String apiVersion; //对接 api 版本
    private String pid;  //在本平台注册的广告位 ID
    private Integer secure;  //媒体使用 HTTP 类型1–只支持 http(默认)2–只支持 https
    private String device_type;  //设备类型-1-未知0-phone1–pad2–pc3–tv4–wap5–server
    private String device_os;  //客户端操作系统取值：Android、IOS、Others
    private String device_type_os;  //操作系统版本号
    private String device_ios_openudid;  //IOS 设备的 OpenUDID（仅 IOS 必填）
    private String device_adid;  //AndroidID（仅 Android 必填）
    private String device_oaid;  //(andorid 版本>=10 必填)移动安全联盟匿名设备标识符
    private String device_oaid_enc;  //oaid 加密方式。0：原值 1：MD52：SHA1
    private String device_imei;  //客户端操作系统设备号 （仅 Android 必填）
    private String device_imei_enc;  //imei 加密方式。0：原值 1：MD52：SHA1
    private String device_ios_idfa;  //IOS 设备 IDFA（仅 IOS 必填）
    private String device_ios_idfa_enc;  //idfa 加密方式。0：原值 1：MD5 2：SHA1
    private String device_ios_idfv;  //iOS 设备的 IDFV（仅 IOS 必填）
    private String device_ios_idfv_enc;  //idfv 加密方式。0：原值 1：MD5 2：SHA1
    private String device_mac;  //客户端的 MAC 地址
    private String device_serial;  //设备序列号
    private String device_imsinum;  //移动用户识别码
    private String device_density;  //设备 ppi，表示以像素每英寸表示的屏幕尺寸
    private String device_imsi;  //网络运营商，取值： “46000”（即中国移动）“46001”（即中国联通）“46003”（即中国电信
    private String device_network;  //联网类型，取值：0—未知1—Ethernet2—WIFI 3—蜂窝网络，未知代4—2G5—蜂窝网络，3G6—蜂窝网络，4G7—蜂窝网络，5G
    private String device_ip;  //客户端外网 IP 地址，当广告请求从服务器端发出时，该字段必填
    private String device_ua;  //User-Agent
    private String device_dpi;  //移动设备屏幕物理像素密度，常见的取值 1.5，1.0
    private String device_width;  //客户端屏幕宽度，以像素为单位
    private String device_height;  //客户端屏幕高度，以像素为单位
    private String device_orientation;  //屏幕方向，取值：0–竖屏1–横屏
    private String device_vendor;  //设备生产商
    private String device_brand;  //设备品牌
    private String device_model;  //设备型号
    private String device_lan;  //目前使用的语言-国家，如 zh-CN
    private String device_isroot;  //设备是否越狱/root，取值：0-否/未知1-是
    private String device_geo_lon;  //地理位置，经度
    private String device_geo_lat;  //地理位置，纬度
    private String device_bootMark;  //系统启动标识：iOS 例子：1623815045.970028Android 例子： ec7f4f33-411a47bc-8067-744a4e7e0723跑 tanx 预算必填，否则不计费
    private String device_updateMark;  //系统更新标识：iOS 例子：1581141691.570419 583Android 例子：004697.709999999跑 tanx 预算必填，否则不计费
    private WoKeLabel label;  //音频广告位请求时上传播放的上下文，即 3-5 条播放内容信息
    private int reception;  //音频广告请求时应用在前台还是后台 1：应用在前台 2：应用在后台



    private static final long serialVersionUID = 1L;
}
