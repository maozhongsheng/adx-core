package com.mk.adx.entity.json.request.yunxi;


import lombok.Data;

/**
 * 云袭
 *
 * @author gj
 * @version 1.0
 * @date 2021/11/04 09:51
 */
@Data
public class YxApp {
    private int ver;//API版本
    private String slotcode;//广告位标识，由云袭分配，12位标识符
    private String reqid;//请求标识,GUID每次请求都是新的GUID
    private int w;//广告位宽度(像素)
    private int h;//广告位高度(像素)
    private String app_name;//app应用名称
    private String bundle;//包名、bundle id(app 必需)
    private String ua;//user agent，URL编码
    private String app_version;//app版本
    private int device_type;//设备类型
    private String make;//设备厂家，URL编码
    private String model;//设备型号，URL编码
    private String os;//操作系统名称，URL编码
    private String osv;//操作系统版本，URL编码
    private int sh;//屏幕高
    private int sw;//屏幕宽
    private float pxratio;//屏幕密度
    private String language;
    private int mnc;//运营商标识
    private int connectiontype;//联网方式
    private String idfa;//(ios)必需
    private String anid;//(安卓)必需
    private String oaid;//(安卓10以上)必需
    private String imei;//(安卓)必需
    private String mac;//MAC
    private String lat;//纬度
    private String lon;//经度
    private String region;//省，URL编码
    private String city;//市，URL编码
    private String cip;//客户端ip （服务端必填）
    private String app_store_version;//应用商店版本号，上游广告为 VIVO 时必传，不传没有广告
    private String boot_mark;//系统启动标识，建议获取，影响填充
    private String update_mark;//系统更新标识，建议获取，影响填充
    private String imsi;//国际移动用户识别码
    private String brand;//移动设备品牌
    private String pkgs;//用户已安装包名列表 多个逗号隔开
    private String hmscore;//华为HMS Core版本号
    private int orientation;//屏幕方向
    private String ssid;//无线网 ssid 名称
    private String wifimac;//WIFI 路由器 MAC 地址
    private String romversion;//手机 ROM 版本
    private String syscomplingtime;//系统编译时间:时间戳

}
