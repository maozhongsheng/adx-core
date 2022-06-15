package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

@Data
public class YolohoDvice {
    private int w;//w
    private int h;//h
    private String ua;//浏览器属性，User Agent
    private String ip;//设备的公网ip地址
    private YolohoGeo geo;//w
    private String did;//设备IMEI（仅Android）
    private String didmd5;//IMEI的md5值，与didmd5二选一，可同时存在
    private String dpid;//Android ID（仅Android
    private String dpidmd5;//Android ID的md5值，与dpid二选一，可同时存在
    private String oaid;//android 匿名设备标识符，安卓10以上必填
    private String vaid;//android 开发者匿名设备标识符
    private String mac;//MAC地址（仅Android）
    private String macmd5;//MAC的md5值，与mac二选一，可同时存在
    private String ifa;//IDFA（仅iOS）
    private String ifamd5;//IDFA的md5值，与ifa二选一，可同时存在
    private String make;//设备生产商，如“Samsung”
    private String model;//设备型号，如“SM-A7100”
    private String brand;//设备品牌，如“Samsung”
    private String imsi;//国际移动用户识别码，存储在SIM卡中
    private String os;//操作系统，大小写不敏感AndroidiOS
    private String osv;//操作系统版本号，如“4.4.0”
    private String carrier;//运营商编号，可留空，例如：46000-中国移动46001-中国联通46003-中国电信46020-中国铁通
    private String language;//浏览器语言，使用ISO-639-1-alpha-2
    private int js;//是否启用JavaScript0-否1-是（默认）
    private String bootMark;//Tanx 反作弊参数-设备启动时间；
    private String updateMark;//Tanx 反作弊参数-设备系统更新时间；
    private int connectiontype;//网络连接类型：0-Unknow1-Ethernet2-WiFi3-2G4-3G5-4G
    private int devicetype;//设备类型：0-手机1-平板
    private int ppi;//像素密度，每英寸表示的屏幕尺寸

}
