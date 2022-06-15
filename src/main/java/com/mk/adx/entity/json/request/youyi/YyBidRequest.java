package com.mk.adx.entity.json.request.youyi;

import lombok.Data;

@Data
public class YyBidRequest {
    private String version; //版本号：3.0
    private String app_id; //流量源ID (由SSP平台生成)
    private String pid; //广告位ID (由SSP平台生成)
    private String is_mobile; //标识是否移动流量:( 1:移动流量，0:pc)默认=1
    private String secure; //0:只支持 http 协议1:只支持 https 协议2:http、https 协议都支持（不传默认此值）
    private String gender; //性别:(-1:未知、01:男、10:女)
    private String yob; //4位数字出生年
    private String keywords; //用户感兴趣关键词多个用英文逗号隔(Get请求需做urlencode)
    private String country; //国家:使用ISO 3166-1\Alpha-2标准
    private String language; //设备的语言设置:使用ISO 639-1\Alpha-2标准
    private String deny_cats; //禁止投放行业的 ID(多个用英文逗号”,” 分隔，例 如”deny_cats=123,456” ID 由 SSP 平台􏰥供)
    private String deny_cids; //禁止投放的创意ID(多个用英文逗号”,” 分隔，例如”deny_cids=123,456” 注：此ID为SSP平台ID)
    private String deny_ader_ids; //禁止投放的广告主ID(多个用英文逗号”,” 分隔，例如” deny_ader_ids =123,456” 注：此ID为SSP平台ID)
    private String h5_app_type; //是否支持打开相关应用，多个用英文逗号分隔1:快应用2:微信小程序3:支付宝小程序（预留字段）
    private String ext; //扩展字段，json字符串（必须且只做一次urlencode）Json结构如：{"features": [{"value": ["1"], "key": "hap"},{"value": ["xxx"], "key": "xxx"},…]}
    private String app_package; //应用包名
    private String app_name; //APP应用名称
    private String app_ver; //App应用本身版本eg:5.0.1
    private String device_geo_lat; //GPS纬度(-90 ~ 90)
    private String device_geo_lon; //GPS经度(-180 ~ 180)
    private String device_imei; //安卓设备IMEI
    private String device_oaid; //OAID:Android Q系统无法再获取到IMEI作为安卓设备唯一标识，移动联盟推出OAID作为设备唯一标识。OAID(Open Anonymous ID )是由中国信通院联合华为、小米、OPPO、VIVO共同推出的设备识别字段。即开放匿名ID，只能用于广告场景（禁止使用在其他场景）安卓设备imei,oaid不能同时为空
    private String device_oaidmd5; //oaid原始值md5加密后的值
    private String device_adid; //安卓系统为android id, ios则为idfa（原始值或md5加密后的值））
    private String device_ali_aaid; //阿里巴巴匿名设备标识，需集成阿里SDK获取
    private String device_caid; //iOS CAID(iOS设备idfa,caid不可能同时为空), 广协CAID,若存在多个版本，返的最新的两个版本，格式如下（caid_老,caid_新）219b18c1e17490f9114ab8235fe84ed2,5b68c571eddd947c21cec5c2c655fe30
    private String device_openudid; //苹果设备唯一标识号; 安卓系统不必填写
    private String device_idfv; //ios idfv for iOS(>=iOS6) 安卓系统不必填写
    private String device_ppi; //设备屏幕像素密度:286ppi
    private String device_density ; //屏幕分辨率值如:3.0.Android平台参考:DisplayMetrics.density,iOS平台参考:UIScreen.scale
    private String device_mac; //MAC地址(00:23:5A:15:99:42)
    private String device_type_os; //操作系统版本(8.0.1)
    private String device_apiLevel; //Android API level (iOS不填)
    private String device_battery_level; //电量百分比（0~100）
    private String device_type; //设备类型(-1:未知, 0:phone, 1:pad, 2:pc, 3:tv， 4:wap，5:户外广告屏)
    private String device_brand; //设备品牌、生产厂商("HUAWEI"、"Apple"、"Xiaomi")
    private String device_model; //手机型号（"SM-G9280"、"iPhone8"、"MIX 2S"等）
    private String device_width; //屏幕宽度，以像素为单位
    private String device_height; //屏幕高度，以像素为单位
    private String device_imsi; //网络运营商代码取值：-1：未知46000：中国移动，46001：中国联通，46003：中国电信
    private String device_network; //网络类型:(-1:未知，1:WIFI，2:4G，3:3G，4:2G, 6：5G）
    private String device_os; //Android/iOS/WP/Others 字符串，注意大小写
    private String device_ip; //客户端ip(必须是外网可访问IP，客户端直接发起请求此字段必须不传，服务器发起请求必须传客户端外网IP)
    private String device_ua; //User-Agent(GET请求须且只做一次urlencode)必须是标准Webview UA而非自定义UA
    private String device_orientation; //横竖屏（-1:未知，1:横屏，0:竖屏）
    private String device_rom_version; //手机rom的版本号
    private String device_hmscore; //华为HMS Core版本号PackageInfo pkginfo = getPackageInfo(this, "com.huawei.hwid");if (pkginfo != null) {String hms_version = pkginfo.versionCode;}
    private String device_appstore_ver; //厂商应用商店版本号（vovi、小米、华为、oppo等厂商应用商店）
    private String device_boot_mark; //系统启动标识，原始传输
    private String device_update_mark; //系统更新标识，原始传输
    private String device_syscmp_time; //手机系统编译时间（毫秒时间戳）
    private String device_ssid; //无级网SSID名称
    private String device_wifi_mac; //WIFI路由器MAC地址
    private String device_isroot; //Android 设备是否 ROOT。1--是, 0--否/ 未知(默认)
}
