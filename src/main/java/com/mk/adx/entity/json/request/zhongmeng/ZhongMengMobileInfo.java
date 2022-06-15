package com.mk.adx.entity.json.request.zhongmeng;

import lombok.Data;

/**
 * 手机的相关配置
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/8 14:21
 */
@Data
public class ZhongMengMobileInfo {
    private String osVersion;//操作系统版本 e.g. 6.0.2
    private String appVersion;//软件应用版本 e.g. 3.2.9
    private String mobileModel;//手机设备型号 e.g. xiaomi5
    private String vendor;//手机设备厂商 e.g. XiaoMi
    private String appStoreVersion;//应用商店版本号， 必填 不可 为空 （oppo 、vivo 、华为 等）
    private String sysVersion;//ROM版本号
    private int connectionType;//网络连接类型 1:wifi 2:2g 3:3g 4:4g 5:5g 100:未知
    private int operatorType;//网络运营商类型 0:未知 1:中国移动 2: 电信 3:联通 99:其他
    private String imsi;//安卓设备唯一标识(获取不到传空字符 串)
    private String imei;//安卓移动设备号IMEI， 必填字段， （能 取到的情况下， 需明文， Android设备 请同时传imei和oaid）
    private String imei_md5;//imei替补值(imei加密值)
    private String oaid;//匿名设备标识符， 需要传oaid （原值， 不需要md5） ； 必传， 明文项， 不可为空
    private String androidId;//安卓设备唯一标识(获取不到传空字符 串)
    private String verCodeOfHms;//HMS Core 版本号， 必填， 明文项
    private String androidId_md5;//androidId替补值
    private String idfa;// string 苹果设备唯一标识
    private String idfa_md5;//idfa替补值
    private String idfv;//苹果唯一标识(获取不到传空字符串)
    private String openUdid;//苹果唯一标识(获取不到传空字符串)
    private String mac;//手机唯一标识
    private int deviceType;//设备类型:1:手机 2:平板 3:智能电视 4: 户外屏
    private int osType;//系 统 类 型 : 0:Android 1:IOS
    private int screenWidth;//手机物理屏幕⻓度(建议设置)
    private int screenHeight;//手机物理屏幕宽度(建议设置)
    private String mcc;//移动国家码(建议设置)
    private String mnc;//移动网络码(建议设置)
    private float deny;//屏幕密度(混合预算设置) e.g.2.0
    private String bookMark;//安卓系统启动标识（oppo必传）
    private String updateMark;//安卓系统启动更新标识（oppo必 传）
    private boolean supportHttps;//是否支持https广告,默认为false(不支持)

}
