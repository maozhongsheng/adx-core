package com.mk.adx.entity.json.request.chuangdian;

import lombok.Data;

/**
 * 创典总请求
 */
@Data
public class CdBidRequest {
    private String version; //接⼝版本号,⽬前为3.0。
    private String time; //请求的unix时间戳,精确到毫秒(13位), 服务器端会使⽤ time作tonken校验**(与媒体⽣成token的时间数值相 同)
    private String token; // 流量校验码,⻅2.2
    private String reqid; //此次请求的id,使⽤uuid格式,⻅2.3。
    private String appid; //APP的id(媒体管理系统中，创建好媒体后，会⾃动⽣成 APP id)。
    private String appver; //APP的版本号，如2.3.1。
    private String adspotid; //⼴告位的id(媒体管理系统中，创建好⼴告位后，会⾃动 ⽣成⼴告位id)。
    private String ip; //设备IP地址，⼀般为ipv4地址，ip和ipv6两个字段必填 ⼀个- APP客户端对接(C2S)，请填设备IP地址； - 服务器端对接(S2S)，依然填设备IP地址，⽽⾮服务 器IP；
    private String ipv6; //设备的ipv6地址，ip和ipv6两个字段必填⼀个
    private String ua; //user agent信息(请求ua)⻅2.4。正确示 例：”Mozilla/6.0…”，异常ua示例：”Dalvik/1.6.0 …”
    private String make; //设备制造商
    private String model; //设备机型。
    private int os; //操作系统类型,0:未识别, 1:ios, 2:android, 3:windows。
    private String osv; //操作系统版本号,取前两位(安卓)或者三位(ios)数字表 示。如:8.1/12.1.1
    private String carrier; //运营商信息, 使⽤标准MCC/MNC码。注:若获取不到请 传空字符串，若是⾮国内流量，传国外运营商信息。国 内运营商示例编码请参考2.5。
    private int network; //⽹络连接类型, 0:未识别, 1:WIFI, 2:2G, 3:3G, 4:4G, 5:5G, 6:以太⽹, 7:蜂窝-未识别.
    private int sw; //设备屏幕宽度,物理像素
    private int sh; //设备屏幕⾼度,物理像素。
    private int ppi; //设备像素密度,物理像素。
    private String oaid; //匿名设备标识符(只适⽤于android，ios设备不需要填 写)，安卓10.0以上版本会有该字段，必须填写
    private String imei; //imei(只适⽤于android，必须填写，ios设备不需要填 写)，由15-17位数字组成。注：安卓10.0版本以上没有 该字段，不需要传。
    private String imei_md5; //imei的MD5值，只适⽤于android，如果获取不到请不 填，安卓10.0版本以上没有该字段，不需要传。
    private String mac; //mac地址(只适⽤于android)。注：安卓10.0版本以上没 有该字段，不需要传
    private String androidid; //androidid(只适⽤于android，ios设备不需要填写)
    private String androidid_md5; //androidid的MD5值，只适⽤于android，如果获取不到 请不填
    private String imsi; //国际移动客户识别码(只适⽤于android)。注：安卓10.0 版本以上没有该字段，不需要传。
    private String imsi_md5; //imsi的MD5值，只适⽤于android，如果获取不到请不 填，安卓10.0版本以上没有该字段，不需要传
    private String idfa; //idfa(只适⽤于ios，android设备不需要填写)。
    private String idfa_md5; //idfa的MD5值，只适⽤于ios，如果获取不到请不填
    private String idfv; //idfv(只适⽤于ios，android设备不需要填写)。
    private String idfv_md5; //idfv的MD5值，只适⽤于ios，如果获取不到请不填
    private int impsize; //请求⼴告的数量，默认是1。
    private float lat; //纬度。
    private float lon; //经度。
    private int devicetype; //设备类型, 0:未识别,1:⼿机,2:平板,3:机顶盒。
    private int donottrack; //个性化⼴告开关 0: (默认)允许1: 禁⽌。
    private int max_duration; //视频最⼤时⻓。
    private CdDevice device; //设备参数(iOS 14及以上 设备必填)。
    private CdExt ext; //附加字段, 媒体可以根据需求进⾏扩展。


}
