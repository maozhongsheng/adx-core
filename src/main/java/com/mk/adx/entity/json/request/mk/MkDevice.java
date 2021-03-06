package com.mk.adx.entity.json.request.mk;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 设备对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class MkDevice {
    @NotBlank(message = "user agent(Browser user agent string)不能为空")
    private String ua;//user agent(Browser user agent string)请保持原值不要转义或者自定否则会影响广告填充
    private MkGeo geo;//地理位置对象
    @NotBlank(message = "ip不能为空")
    private String ip;//
    private String ipv6;//
    @NotBlank(message = "设备类型不能为空")
    private String devicetype;//设备类型,手机:phone,平板:ipad,PC:pc,互联网电视:tv
    @NotBlank(message = "制造厂商不能为空")
    private String make;//制造厂商,如“apple”“samsung”“huawei“，默认为空String
    @NotBlank(message = "型号不能为空")
    private String model;//型号,如”iphonea1530”，默认为空String
    @NotBlank(message = "操作系统不能为空")
    private String os;//操作系统,取值如下：Windows: "windows",Android: "android",iPhone: "ios",苹果电脑: "mac"
    @NotBlank(message = "操作系统版本号不能为空")
    private String osv;//操作系统版本号,如"4.1", "xp"等
    private String mccmnc;//Mobile carrier as the concatenated MCC-MNC code (e.g., “310-005” identifies Verizon Wireless CDMA in the USA). Refer to https://en.wikipedia.org/wiki/Mobile_country_code for further examples. Note that the dash between the MCC and MNC parts is required to remove parsing ambiguity.
    private Integer geofetch;//Indicates if the geolocation API will be available to JavaScript code running in the banner, where 0 = no, 1 = yes.
    private float pxratio;//物理设备独立像素大小的比值
    //@NotBlank(message = "硬件的设备版本不能为空")
    private String hwv;//硬件的设备版本
    @NotNull(message = "物理屏幕高度的像素不能为空")
    private Integer h;//物理屏幕高度的像素
    @NotNull(message = "物理屏幕宽度的像素不能为空")
    private Integer w;//物理屏幕宽度的像素
    //@NotNull(message = "屏幕大小不能为空")
    private Integer ppi;//屏幕大小
    // @NotNull(message = "设备屏幕密度不能为空")
    private double deny;//设备屏幕密度（安卓:context.getResources().getDisplayMetrics().density； iOS:UIScreen.scale取值例如:2.0）
    //  @NotBlank(message = "网络运营商不能为空")
    private String carrier;//网络运营商（中国移动：70120 中国联通：70123 中国电信：70121）
    private Integer connectiontype;//网络链接类型，取值如下：eth，wifi，3g，4g，5g，未知留空
    private String gaid;//安卓advertising id，示例：AdvertisingIdClient.Info info = AdvertisingIdClie nt.getAdvertisingIdInfo(context);String gpId = info.getId()
    private String imei;//IMEI，明文传输，默认为空字符串
    private String imei_md5;//
    private String imei_sha1;//
    private String android_id;//安卓id
    private String android_id_md5;//
    private String android_id_sha1;//
    private String oaid;//移动安全联盟推出的匿名设备标识符
    private String oaid_md5;//移动安全联盟推出的匿名设备标识符 md5值
    private String idfa;//IDFA，明文传输，默认为空字符串
    private String idfa_md5;//
    private String idfa_sha1;//
    private String idfv;//iOS 系统的 idfv值
    private String caid;//中国广告协会互联网广告标识（CAID）
    private String caid_version;//caid算法版本，提供caid时必填
//    @NotBlank(message = "MAC地址不能为空")
    private String mac;//MAC地址，明文传输，默认为空字符串
    private String mac_md5;//
    private String mac_sha1;//
    @NotBlank(message = "国家不能为空")
    private String country;//国家，使用ISO 3166 1 Alpha 3
    @NotBlank(message = "设备的语言设置不能为空")
    private String language;//设备的语言设置使用 alpha 2/ISO 639-1
    private Integer orientation;//横竖屏:0°,90°,180°,270°,-1未知
    private String open_udid;//iOS < 6 -> idfa
    private String flashver;//版本的Flash支持的浏览器
    private int dnt;//==不跟踪,0：跟踪，1：不跟踪
    private String phoneName;//设备名称，例如:张三的 iPhone
    private String bootTimeSec;//设备最近一次开机时间，IOS操作系统必传;秒级时间戳， 小数点后保留 6 位，示例: 1595214620.383940
    private String osUpdateTimeSec;//系统最近一次更新时间，IOS操作系统必传;秒级时间戳， 小数点后保留 6 位，示例: 1595214620.383940
    private int diskSize;//手机容量大小，单位:GB，示例:16 . IOS操作系统必传
    private int memorySize;//手机内存大小，单位 GB; IOS操作系统必传，例如:8
    private int cpuNumber;//手机 CPU 个数，IOS操作系统必传:例如:4
    private String modelCode;//设备型号码，IOS操作系统必传,例如:D22AP
    private String timeZone;//当前设备时区，IOS操作系统必传,例如:28800
    private String local_name;//时区local时区，如"Asia/Shanghai"，仅ios需要回传，安卓可不填写该字段
    private String hardware_machine;//如“iPhone10,3”, 仅ios需要回传，安卓可不填写该字段
    private String appstore_ver;//厂商应用商店版本号(vovi、小米、华为、oppo 等厂商 应 用商店) 必填
    private String vercodeofhms;//HMS Core 版本号，实现被推广应用的静默安装依赖 HMS Core 能力。华为设备必填。


}
