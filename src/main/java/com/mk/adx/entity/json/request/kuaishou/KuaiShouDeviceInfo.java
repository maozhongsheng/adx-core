package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

import java.util.List;

/**
 * 设备信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouDeviceInfo {
    private String idfa;//ios 设备标识，必填
    private String imei;//android 设备标识，主流手机的安卓10 以下必填，安卓 10 以上非必填
    private String imeiMd5;//md5 加密后的 imei 号，建议尽量填写，如果不填写会影响流量变现效 率，imei 和 imeiMd5 至少填写一个，建议优先填写 imei
    private String oaid;//android 设备标识，主流手机的安卓10 以上必填
    private int osType;//操作系统类型， 0: Unknown;1: Android; 2: iOS
    private String osVersion;//操作系统版本号
    private String language;//系统语言
    private List<AppPackageName> appPackageName;//已安装 app 列表
    private int screenWidth;//手机屏幕宽度
    private int screenHeight;//手机屏幕高度
    private String androidId;//android id，必填
    private String androidIdMd5;//md5 加密后 android id，建议尽量填写，如果不填写会影响流量变现效 率，androidId 和 androidIdMd5 至少填写一个，建议优先填写 androidId
    private String deviceModel;//设备型号，系统原始值，不要做修改。例：ios：iPhone9,1android：vivo y83a、mi 8
    private String deviceBrand;//设备品牌
    private String deviceVendor;//设备厂商。1) android 设备： 可调用系统接口android.os.Build.MANUFACTURER 直接获得。如果获取不到，填写 unknown( 小写）。2) ios 设备：无需填写
    private int platform;//平台类型,1:iphone,2:ipad,3:android_phone,4:a ndroid_pad
    private String deviceNameMd5;//设备名称的 MD5
    private int physicalMemoryKBytes;//物理内存 单位 KB
    private int hardDiskSizeKBytes;//硬盘大小 单位 KB
    private String country;//国家，中国：CHN
    private String timeZone;//时区 北京时间 "GMT+0800"
    private String systemUpdateTimeNanoSec;//设备系统更新时间, 精度纳秒，格式为：1621329481::433642666, :前部 分为秒，:后部分为纳秒
    private String systemBootTimeMilliSec;//设备系统启动时间,精度毫秒

}
