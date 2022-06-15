package com.mk.adx.entity.json.request.yuanyin;

import lombok.Data;

/**
 * 创典
 */
@Data
public class YyDevice {
    private String os; //⼿机操作系统，Android、iOS、
    private String ov; //⼿机操作系统版本，未知填unknown
    private int dType; //设备类型，⼿机：0，平板：1，2：未知
    private String brand; //⼿机品牌，如xiaomi, 未知填unknown
    private String model; //⼿机型号，如red mi, 未知填unknown
    private String vendor; //设备⼚商, 未知填unknown
    private String imei; //imei号
    private String androidId; //androidId
    private String idfa; //idfa
    private String md5Imei; //imei的32位Md5值
    private String md5AndroidId; //androidId的32位Md5值
    private String md5Idfa; //idfa的32位Md5值
    private String mac; //mac地址，格式例：02:02:02:02:02:02
    private String ua; //⼿机浏览器的user-agent
    private int screenWidth; //⼿机屏幕宽度，以像素为单位
    private int screenHeight; //⼿机屏幕⾼度，以像素为单位
    private int screenOrientation; //横竖屏，0:未知，1: 竖屏 ，2:横屏
    private String oaid; //安卓10以上必填
    private String imsi; //国际移动⽤户识别码
    private String bootMark; //设备开机时间
    private String updateMark; //系统更新时间
    private int dpi; //设备屏幕像素密度
    private double density; //设备屏幕密度
    private int apiLevel; //安卓API等级


}
