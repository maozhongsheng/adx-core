package com.mk.adx.entity.json.request.mitu;


import lombok.Data;

/**
 * 觅途
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/22 16:51
 */
@Data
public class MituBidRequest {
    private int ad_type;//广告类型：1 - Banner2 - 开屏3 - 插- 4信息流- 5激励视频
    private String app_name;//应用名称
    private String app_version;//应用版本,来源 于 manifest 的 versionName ，而不是versionCode如3.5.6
    private int app_version_code;//应用版本号,来源于 manifest 的 versionCode。如 119
    private String pkg_name;//应用包名
    private int platform;//平台：0 - 未知1 - 安卓2 - IOS3 - 其他
    private String make;//设备制造商，例如 Samsung
    private String model;//设备硬件型号，例如 iPhone
    private String brand;//手机品牌
    private String hwv;//硬件型号版本，例如 iPhone 中的 7S，默认值："unkown"
    private String os;//操作系统，Android 或 iOS 字符串，注意大小写
    private String os_version;//系统版本
    private int osapilevel;//安卓系统版本系统级别
    private String android_id;//Android ID
    private String auidmd5;//Android ID的MD5值
    private String auidsha1;//Android ID的sha1值
    private String imei;//Android 系统必填，Android手机设备的imei
    private String imeimd5;//Android 系统必填，Android手机设备的imei的MD5值
    private String imeisha1;//Android 系统必填，Android手机设备的imei的sha1值
    private String meid;//手机设备的meid号，电信必填
    private String imsi;//国际移动用户识别码
    private String oaid;//广告标识符，安卓10以上必填
    private String idfa;//IOS系统必填，原生大写
    private String idfv;//IOS系统必填
    private String openudid;//IOS系统必填,设备识别码
    private String caid;//中国广告协会互联网广告标识，ios14以上填写
    private String ip;//IP地址
    private String ua;//系统 user-agent
    private String mac;//设备mac地址
    private String macmd5;//32位小写md5加密mac
    private String density;//屏幕密度，比例值（dpi/160） eg: 1.5
    private int ppi;//像素密度，表示每英寸的像素数。eg:401
    private int dpi;//像素点密度，表示每英寸的点数。eg:240
    private int screen_width;//屏幕的宽度
    private int screen_height;//屏幕的高度
    private int screen_orient;//屏幕的方向：1 - 竖屏2 - 横屏
    private String app_store_version;//样例:oppo:5500,vivo:10700 手机自带的应用商店的版本（OV广告不能为空）
    private String rom_version;//手机的ROM版本号（OV广告不能为空）
    private int net_type;//客户端网络类型:0.unkown1.wifi2.2G3.3G4.4G5.5G
    private int carrier;//运营商类型：0. 未知运营商1.中国移动2.中国联通3.中国电信4.其他运营商
    private String bssid;//WIFI 的 ID
    private String wifi_mac;//wifi 路由的 mac 地址
    private String iccid;//Sim卡的序号
    private String mnc;//基站MNC
    private String mcc;//基站MCC
    private String serialno;//系统设备序列号（获取不到需要报备）
    private String ali_aaid;//阿里巴巴匿名设备标识，需集成阿里SDK 获取，阿里预算必填，具体查看附件。
    private String boot_mark;//系统启动标识，原值传输，取值参见附件文档，示例 iOS:：1623815045.970028，Android：ec7f4f33-411a-47bc-8067-744a4e7e0723
    private String update_mark;//系统更新标识，原值传输，取值参见附件文档，示例 iOS：1581141691.570419583， Android：1004697.709999999
    private String os_compiling_time;//系统编译时间戳
    private String os_update_time;//系统更新时间戳
    private String os_startup_time;//系统启动时间戳
    private String phone_name;//设备名称例如：张三的 iPhone
    private String phone_name_md5;//设备名称md5值
    private String sys_memory;//系统内存，单位字节
    private String sys_disksize;//硬盘容量，单位字节例如：63900340224
    private int cpu_num;//int 处理器核数（iOS 设备必须）
    private String hardware_machine;//设备 machine 值，系统型号
    private String country;//国家编码（ISO-3166-1/alpha-2）例如：CN
    private String language;//设备语言（ISO-639-1/alpha-2）例如：zh
    private String timezone;//系统所在时区例如：Asia/Shanghai
    private String hms_ver;//华为安卓设备的 HMS Core的版本号，保留原始值。华为手机必填
    private String hwag_ver;//华为安卓设备的 AG（应用市场）的版本号，保留原始值。，保留原始值。华为手机必填
    private String sys_ui_ver;//系统UI版本，保留原始值。如小米设备的 MIUI的版本号，华为、OV、魅族等设备版本号
    private int gps_type;//坐标类型。0：未知；     1= WGS84(全球卫星定位系统坐标系)；     2= GCJ02（国家测绘局坐标系）；     3= BD09（百度坐标系）；
    private double latitude;//经度，默认值： 0
    private double longitude;//纬度，默认值：0
    private long gps_ts;//获取 gps 位置时的时间戳
    private int ad_width;//w
    private int ad_height;//h
    private int support_deeplink;//是否支持deeplink:0.不支持1.支持
    private int support_universal;//是否支持universal link:0.不支持1.支持

}
