package com.mk.adx.entity.json.request.uc;

import lombok.Data;

/**
 * Uc设备信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcDevice {
    private String android_id;//传递androdid的值
    private String devid;//设备ID，Android取imei，IOS按客户端现有逻辑获取；如果获取不到imei5取；如果获取不到imei，则传递androdid的值；
    private String imei;//Android设备id，⽤于android设备的⽤户识别和兴趣投放
    private String imei_md5;//Android设备id，⽤于android设备的⽤户识别和兴趣投放
    private String oaid;//Android 设备⽤于替代imei，⽤户识别和兴趣投放
    private String oaid_md5;//Android 设备⽤于替代imei，⽤户识别和兴趣投放
    private String aaid;//阿⾥集团内推出的匿名⼴告标识符，格式示例:CD7D878A870C-97D4-89AA-3EB3-D48AF066
    private String caid;//中国⼴告协会推出的⼴告标识符，有版本号概念，在后端算法升级时，会获得两个 caid，分别为升级前和升级后的 id，数据为json，期望进⾏
    private String udid;//ios备选设备ID，Ios版本<=6.0有效，⽤于ios设备的⽤户识别和兴趣投放
    private String open_udid;//ios备选设备ID，Ios版本<=6.0有效，⽤于⽤于ios设备的⽤户识别和兴趣投放
    private String idfa;//ios设备ID，仅IOS 6+有效，⽤于ios设备的⽤户识别和兴趣投放6充 别和兴趣投放
    private String device;//设备型号（需要归⼀化所有的设备型号），直接填写⽤户的设备类型即可，⽬前没有归⼀化
    private String os;//操作系统，枚举选项，android/ios/wp，⽤于操作系统定向投放
    private String osv;//操作系统版本（需要归⼀化所有的设备型号）
    private String cpu;//cpu型号
    private String mac;//mac 地址（⼩写归⼀化）
    private String sw;//屏幕宽度（物理分辨率）
    private String sh;//屏幕⾼度（物理分辨率）
    private String is_jb;//系统是否越狱（Android取是否 root，IOS 取是否越狱）枚举值0 不确定1 越狱2 未越狱⽤于定向投放
    private String access;//⽹络类型 枚举值 WiFi/2G/3G/4G/Unknown，⽤于⽹络定向投放
    private String carrier;//运营商 枚举值UnknownChinaMobileChinaUnicomChinaTelecomChinaTietong
    private String cp;//cp信息，格式如：isp:电信;prov:⼴东;city:⼴州;na:中国;cc:CN;ac:，7⽤于获取⽤户地域信息，如果实在⽆法获取，可以不填
    private String aid;//阿⾥统⼀设备id，强烈建议填充，⽤于⽤户识别和兴趣投放
    private String client_ip;//服务端转发必须填充该字段，客户端直连不需要填充，⽤于⽤户地域识别，⼴告地域投放和反作弊

}
