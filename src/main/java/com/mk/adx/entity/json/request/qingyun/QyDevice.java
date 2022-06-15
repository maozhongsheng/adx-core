package com.mk.adx.entity.json.request.qingyun;


import lombok.Data;


/**
 * 青云-设备信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class QyDevice {
    private String imei;//Android 设备唯一标识码
    private String oaid;//Android 设备唯一标识码 Android 10 系统及以上
    private String android_id;//Android 设备系统 ID
    private String idfa;//IOS 设备唯一标识码
    private String caid;//广协 CAID
    private String mac;//设备 MAC 地址
    private int device_type;//设备类型：0-未知，1-手机，2-平板
    private String user_agent;//客户端 WebView 的 UserAgent 信息
    private String vendor;//设备厂商名称，例如：HUAWEI
    private String model;//设备型号，例如：nova 8
    private int os_type;//操作系统：1-Android，2-IOS，3-HarmonyOS不填写将不返回广告，填写错误会影响变现效果
    private String os_version;//操作系统三段式或两段式版本号
    private int screen_width;//屏幕宽度
    private int screen_height;//屏幕高度
}
