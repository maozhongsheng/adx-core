package com.mk.adx.entity.json.request.renze;


import lombok.Data;

/**
 * 设备对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class RzDevice {
    private int osType;//操作系统类型0: Unknown; 1:Android; 2: IOS; 3:Android pad;4:IPad;5:windows;6:MacBook
    private int type;//设备类型 1⼿机 2 平板 3 OTT 终端 4 PC 9 其他。注：OTT 终端包括互联⽹电视和电视机顶盒
    private String adid;//androidId android 必填，不填影响填充
    private String udid;//iOS 设备的 OpenUDID,小于 ios 6 必
    private String osVersion;//操作系统版本号
    private String language;//系统语言
    private String model;//设备型号，系统原始值，不要做修改。
    private String brand;//设备品牌
    private String vendor;//设备厂商。1)android 设备： 可调用系统接口android.os.Build.MANUFACTURER 直接获得。如果获取不到，填写 unknown( 小写）。2)ios 设备：无需填写
    private String imei;//android Q 之前设备标识必填，Android 必传
    private String oaid;//Android Q 之后广告标识符，Android 必传
    private int width;//设备屏幕宽度
    private int height;//设备屏幕高度
    private float density;//每英寸像素,获取方法：安卓：context.getResources().getDisplayMetrics().densityiOS：UIScreen.scale
    private String idfa;//IDFA，Ios 6 之后必填
    private int orientation;//屏幕方向 0：unknown 1：竖屏 2：横屏
    private int screenDpi;//设备屏幕像素密度，如：160
    private int osl;//Android 操作系统 API Level，如 23、22，上游广告为vivo 时必
    private String imsi;//国际移动客户识别码
    private String screenSize;//屏幕尺寸 例:4.7 , 5.5 单位:英寸
    private String romVersion;//手机 rom 的版本
    private String sysComplingTime;//系统编译时间
    private String bssid;//所连接的 WIFI 设备的 MAC 地址 ,路由器 WIFI 的MAC 地
    private String serialno;//移动设备序列号
    private String bootMark;//Tanx 反作弊参数-设备启动时 间见文档对接附件：《Tanx API 新增参数说明》Tanx 预算必传
    private String updateMark;//Tanx 反作弊参数-设备系统更新时间; 见文档对接附件：《Tanx API 新增参数说明》Tanx 预算必传
}
