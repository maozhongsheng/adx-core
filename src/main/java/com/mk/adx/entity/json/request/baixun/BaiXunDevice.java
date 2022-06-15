package com.mk.adx.entity.json.request.baixun;


import lombok.Data;

/**
 * 百寻
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class BaiXunDevice {
    private String ua;//客户端 User Agent
    private String ipv4;//客户端公网 ip
    private int type;//设备类型 0-未知 1-phone 2-tablet 3-pc
    private String make;//Device make (e.g., "Apple")
    private String model;//Device model (e.g., "iPhone")
    private String model_type;//设备型号类型,例如设备型号 iPhone7,1 对应的设备类型 iPhone 6 Plu
    private String os;//Device operating system (e.g., "iOS", "Android")
    private String osv;//Device operating system version (e.g., "3.1.2")
    private int w;//Physical width of the screen in pixels
    private int h;//Physical height of the screen in pixels
    private String carrier;//运营商，46000 – 中国移动 GSM 46001 – 中国联通 46002-中国移动 TD 46003 – 中国电信46020 – 中国铁通
    private String language;//语言，如 zh-CN
    private int connection;//网络类型，0-未知 1-ethernet 2-2G 3-3G 4-4G5-5G 6-wif
    private String imei;//Imei
    private String imeimd5;//Imei-Md5
    private String imeisha1;//Imei-Sha1
    private String idfa;//Idfa
    private String idfamd5;//Idfa-Md5
    private String idfasha1;//Idfa-Sha1
    private String idfv;//Idfa
    private String idfvmd5;//IDFV-Md5
    private String idfvsha1;//IDFV-sha1
    private String mac;//Mac 地址
    private String macmd5;//Mac 地址 Md5
    private String macsha1;//Mac 地址 Sha1
    private String androidid;//AndroidId
    private String androididmd5;//AndroidId-Md5
    private String androididsha1;//AndroidId-Sha1
    private BaiXunGeo geo;//地理位置信息
    private String xiaomitoken;//小米的真机识别标识，小米系统专用，参见附件
    private int dpi;//每英寸像素个数
    private float density;//dpi/160
    private String oaid;//移动安全联盟(MSA)-匿名设备标识符
    private String caid;//中国广告协会互联网广告标示（CAID）
    private String caidversion;//CAID 生成算法的版
    private String boot_mark;//系统启动标示
    private String update_mark;//系统更新标示
    private String rom_version;//手机 ROM 版本
    private int sys_compile_ts;//系统编译时间，时间戳，单位毫秒


}
