package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

/**
 * 设备对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class MycDevice {
    private String ua;//user agent(Browser user agent string)请保持原值不要转义或者自定否则会影响广告填充
    private MycGeo geo;//地理位置对象
    private String ip;//
    private String os;//操作系统,取值如下：Windows: "windows",Android: "android",iPhone: "ios",苹果电脑: "mac"
    private String osv;//操作系统版本号,如"4.1", "xp"等
    private String idfa;//IDFA，明文传输，默认为空字符串
    private String idfa_md5;//
    private String caid;//中国广告协会互联网广告标识（CAID）
    private String caid_version;//caid算法版本，提供caid时必填
    private String idfv;//IOS 设备当前的 IDFV 值NSString * IDFV = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
    private String aid;//安卓id
    private String aid_md5;//
    private String imei;//IMEI，明文传输，默认为空字符串
    private String imei_md5;//
    private String oaid;//安卓oaid
    private String mac;//MAC地址，明文传输，默认为空字符串
    private String mac_md5;//
    private int devicetype;//设备类型,手机:phone,平板:ipad,PC:pc,互联网电视:tv
    private Integer connectiontype;//网络链接类型，取值如下：eth，wifi，3g，4g，5g，未知留空
    private String carrier;//网络运营商（中国移动：70120 中国联通：70123 中国电信：70121）
    private String mccmnc;//Mobile carrier as the concatenated MCC-MNC code (e.g., “310-005” identifies Verizon Wireless CDMA in the USA). Refer to https://en.wikipedia.org/wiki/Mobile_country_code for further examples. Note that the dash between the MCC and MNC parts is required to remove parsing ambiguity.
    private String make;//制造厂商,如“apple”“samsung”“huawei“，默认为空String
    private String model;//型号,如”iphonea1530”，默认为空String
    private Integer w;//物理屏幕宽度的像素
    private Integer h;//物理屏幕高度的像素
    private Integer ppi;//屏幕大小
    private Double dpi;//移动设备屏幕物理像素密度，常见的取值 1.5，1.0
    private Integer orientation;//设备横竖屏状态信息 1=横屏，2=竖屏 默认为竖屏
    private int js;//是否支持JavaScript, 0：否，1：是

}
