package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdUdid {
    private String imei;//Android 设备唯一标识码，安卓必填
    private String oaid;//安卓 10 及以上版本必填
    private String imsi;//国际移动客户识别码，建议填写
    private String android_id;//Android 设备 ID，安卓必填
    private String mac;//Mac 地址
    private String idfa;//iOS 设备唯一标识码，IOS 系统必填
    private String idfv;//Ios 设备的应用开发商标识符，IOS 系统必填
    private String openudid;//IOS 备的 openudid 值
    private String serial_number;//设备 sn 码
    private String aaid;//设备广告 id
    private String idfa_md5;//idfa 经过 MD5 方式加密
    private String imei_md5;//imei 经过 MD5 方式加密
    private String imei_sha1;//imei 经过 SHA1 方式加密
    private String androidid_md5;//androidud经过MD5方式加密
    private String androidid_sha1;//androidid经过SHA1方式加密
    private String mac_md5;//mac 转大写后 MD5 方式加密
    private String caid;//中国广告协会互联网广告标识(CAID)，有了尽量传



}
