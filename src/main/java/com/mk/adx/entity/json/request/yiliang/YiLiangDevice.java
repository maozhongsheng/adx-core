package com.mk.adx.entity.json.request.yiliang;

import lombok.Data;

@Data
public class YiLiangDevice {
    private String mac;//mac地址，如00:0A:D5:B7:80:5E，不传或乱填或修改原始值或传固定值或可能会影响收益
    private String imei;//安卓设备IMEI原始值，Android Q以下版本必须提供，无法提供时则需传didMd5
    private String didMd5;//IMEI md5串（小写），原始IMEI取md5。Android Q以下版本若原始imei无法提供时，则需传didMd5
    private String oaid;//匿名设备标识符，详见-OAID和VAID获取方式。Android Q以上必传，否则可能无广告返回
    private String vaid;//开发者匿名设备标识符，详见-OAID和VAID获取方式
    private String androidId;//安卓设备ID，原始值
    private String an;//安卓系统版本，示例：6.0，详见Android版本
    private int av;//安卓系统版本号，安卓API等级。示例：23。 av >= 29时，视为安卓系统为Android Q及以上
    private String ua;//用户设备HTTP请求头中的User-Agent字段。
    private String ip;//用户设备的公网出口IPv4 地址，点分字符串形式，IP示例：14.28.7.140。该字段务必正确填写，否则将严重影响流量变现效果。
    private String make;//设备厂商，示例：vivo，vivo需为全小写
    private String model;//设备型号 ，示例：vivo x20
    private String language;//设备设置的语言，示例：zh-CN
    private int screenWidth;//设备屏幕宽
    private int screenHeight;//设备手机屏幕高
    private int ppi;//屏幕ppi
    private int elapseTime;//开机时长
}
