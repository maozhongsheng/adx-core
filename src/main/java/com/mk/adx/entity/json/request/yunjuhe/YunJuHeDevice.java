package com.mk.adx.entity.json.request.yunjuhe;

import lombok.Data;

/**
 * 设备对象
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/14 13:53
 */
@Data
public class YunJuHeDevice {
    private int osType;//操作系统类型0: Unknown; 1:Android; 2: IOS3:Android pad4:IPad 5:windows;6:MacBook
    private int type;//设备类型 1⼿机 2 平板 3 OTT 终端 4 PC 9 其他。注：OTT 终端包括互联⽹电视和电视机顶盒
    private String adid;//androidId android 必填，不填影响填充
    private String udid;//iOS 设备的 OpenUDID,小于 ios 6 必传
    private String osVersion;//操作系统版本号
    private String language;//系统语言
    private String model;//设备型号，系统原始值，不要做修改
    private String brand;//设备品牌
    private String vendor;//设备厂商。1)android 设备： 可调用系统接口android.os.Build.MANUFACTURER 直接获得。如果获取不到，填写 unknown( 小写）。2)ios 设备：无需填写。
    private String imei;//android Q 之前设备标识必填，Android 必传
    private String oaid;//Android Q 之后广告标识符，Android 必传
    private int width;//设备屏幕宽度
    private int height;//设备屏幕高度
    private float density;//每英寸像素,获取方法：安卓：context.getResources().getDisplayMetrics().density; iOS：UIScreen.scale
    private String idfa;//IDFA，Ios 6 之后必填
    private int orientation;//屏幕方向 0：unknown 1：竖屏 2：横屏
    private int screenDpi;//设备屏幕像素密度，如：160
    private int osl;//Android 操作系统 API Level，如 23、22，上游广告为vivo 时必传
    private String vaid;//android 开发者匿名设备标识符,获取方式详见 OAID和 VAID 获取方式
    private Integer elapseTime;//开机时长,单位秒
    private String imsi;//国际移动客户识别码
    private String idfv;//iOS 设备当前 IDFV 值
    private String screenSize;//屏幕尺寸 例:4.7 , 5.5 单位:英寸
    private String romVersion;//手机 rom 的版本
    private String sysComplingTime;//系统编译时间
    private String ssid;//无线网 SSID 名称,多个用,分开
    private String bssid;//所连接的 WIFI 设备的 MAC 地址 ,路由器 WIFI 的MAC 地址
    private String serialno;//移动设备序列号
    private String bootMark;//Tanx 反作弊参数-设备启动时 间见文档对接附件：《Tanx API 新增参数说明》Tanx 预算必传
    private String updateMark;//Tanx 反作弊参数-设备系统更新时间; 见文档对接附件：《Tanx API 新增参数说明》Tanx 预算必传
    private Boolean isSupportDp;//是否支持 DP
    private String startupTime;//开机时间,如 "1596270702.486691"，仅 ios 需要回传，安卓可不填写该字段
    private String mbTime;//系统版本更新时间，如 "1596632447.155983"，仅 ios需 要回传，安卓可不填写该字段
    private int cpuNum;//CPU 数⽬，如“4” ，仅 ios 需要回传，安卓可不填写该字段
    private long diskTotalmemTotal;//磁盘总空间，单位：字节， 如“250685575168” ，仅ios 需要回传，安卓可不填写该字段
    private long memTotal;//系统总内存空间，单位： 字节，如“17179869184” ，仅 ios 需要回传，安卓可 不填写该字段
    private int authStatus;//广告标识授权情况，如 “3 ”(代表 authorized) ， 仅ios 需 要回传，安卓可不填写该字段
    private String imeiMd5;//不方便回传 imei，传递 imei 的加密字段
    private String androidIdMd5;//安卓设备 ID 的 md5 加密

}
