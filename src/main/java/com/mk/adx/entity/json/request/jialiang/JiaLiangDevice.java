package com.mk.adx.entity.json.request.jialiang;

import lombok.Data;

/**
 * 设备信息描述
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class JiaLiangDevice {
    private String ua;//移动端访问时的User-Agent信息，务必是合理有效的浏览器代理信息
    private JiaLiangGeo geo;//定位信息，⽬前仅⽀持中国⼤陆的地区，海外地区暂不⽀持
    private String ipv4;//pv4地址，公⽹IP地址，⼀定不能是内⽹IP，且该地址务必⼀定是移动客户端的⽹络地址，与ipv6不可同时为空
    private String ipv6;//ipv6地址，公⽹IP地址，⼀定不能是内⽹IP，且该地址务必⼀定是移动客户端的⽹络地址，与ipv4不可同时为空
    private Integer device_type;//设备类型；0：未知；1：PC；2：苹果⼿机APP；3：苹果平板APP；4：苹果⼿机H5；5：苹果平板H5；6：安卓⼿机APP；7：安卓平板APP；8：安卓⼿机H5；9：安卓平板H5
    private String refer;//referer信息，当device_type = 1/4/5/8/9 时有值
    private String cookie;//cookie信息，当device_type = 1/4/5/8/9 时有值
    private String made_in;//设备⽣产商，⼀个媒体⼀个设备不应该存在采集信息不⼀致问题，包括：⼤⼩写；如：Apple、Xiaomi、Samsung
    private String brand;//设备品牌
    private String model;//设备型号，⼀个媒体⼀个设备不应该存在采集信息不⼀致问题，包括：⼤⼩写；如：iPhone
    private String hww;//设备硬件型号版本；如：iPhone 5S 中的5S
    private String hw_machine;//设备型号；如：iPhone7
    private Integer os;//操作系统；1：IOS；2：Android；3：Windows Phone； 4：PC；5：其他
    private String model_code;//设备型号代码，IOS操作系统必传，；如：D22AP
    private String os_version;//操作系统版本名称，如：1.0.1/1.0.1等，格式务必合理有效
    private Integer os_version_code;//操作系统版本号，例如：25
    private String rom_version;//⼿机ROM版本
    private String os_com_time;//⼿机系统的编译时间（时间戳）
    private String os_update_time;//⼿机系统的更新时间（时间戳）
    private String api_level;//安卓Api-level，Android API Level（苹果流量可空）
    private Integer rh;//屏幕分辨率⾼度；例如：1080
    private Integer rw;//屏幕分辨率宽度；例如：1920
    private Integer sh;//屏幕物理⾼度；例如：480
    private Integer sw;//屏幕物理宽度；例如：320
    private Float density;//分辨率值；例如：3.0；安卓参考：DisplayMetrics.density；苹果参考：UIScreen.scale；
    private Integer ppi;//移动设备像素密度；例如：406
    private String screen_size;//屏幕尺⼨ 例:4.7 , 5.5 单位:英⼨
    private String idfa;//苹果idfa，针对苹果设备时，该字段为必填项，idfa、caid不能同时为空
    private String idfa_md5;//苹果idfa的md5值，针对苹果设备时，该字段为必填项
    private String idfv;//苹果idfv，针对苹果设备时，该字段为必填项
    private String idfv_md5;//苹果idfv的md5值，针对苹果设备时，该字段为必填项
    private String caid;//苹果caid，IOS14以上⽆法获取idfa则获取caid，针对苹果设备时，idfa、caid不能同时为空
    private String caid_md5;//苹果caid的md5值，针对苹果设备时，该字段为必填项
    private String imei;//安卓设备的IMEI信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
    private String imei_md5;//安卓设备的IMEI的md5，针对安卓设备时，该字段为必填项
    private String oaid;//安卓设备的oaid信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
    private String oaid_md5;//安卓设备的oaid的md5，针对安卓设备时，该字段为必填项
    private String android;//android，针对安卓设备时，该字段为必填项
    private String android_md5;//android的md5，针对安卓设备时，该字段为必填项
    private String mac;//MAC地址，安卓务必要获取到，不然不建议发请求；例如：02:00:00:00:00:00
    private String mac_md5;//MAC地址的md5
    private String operator;//运营商；移动：46000/46002/46007；联通：46001/46006；电信：46003/46005；铁通：46020
    private Integer network;//⽹络类型；0：未知；1：wifi；2：2G；3：3G；4：4G； 5：5G
    private String open_udid;//OPENUDID，苹果设备为必填项
    private String serial_num;//设备序列号，Build.SERIAL
    private String imsi;//标准的IMSI卡串信息，必须为460开头的，总⻓度不超过15位
    private Integer source_direction;//屏幕⽅向；1：竖；2：横
    private String app_store_ver;//应⽤商店版本号；oppo、vivo机型必传
    private String ssid;//wifi的SSID名称，获取不到可传空
    private String wifi_mac;//WIFI的MAC地址，获取不到可传空
    private Integer root;//是否越狱ROOT；0：否；1：是
    private Integer cpu_num;//CPU 数⽬，如“4” ，ios 尽可能回传，安卓可不填写 该字段
    private String memory;//系统内存容量
    private String disk;//系统硬盘容量
    private String boot_time;//设备最近⼀次开机时间，IOS必传，秒级时间戳，⼩数点后保留6位，如：1595234650.347268
    private String phone_name;//⼿机名，如：XXX的iPhone
    private String time_zone;//当前设备时区，如：28800，iOS必传
    private String lmt;//当前设备是否允许获取idfa；0：未确定，开发者尚未发起授权请求；1：受限制；2：被拒绝；3：已授权；iOS必传
    private String country_code;//设备国家代码，使⽤ ISO-3166-1-alpha-2 标准
    private String language;//设备语⾔代码，使⽤ ISO-639-1-alpha-2 标准
    private String boot_mark;//(填写获取更多预算)安卓系统启动标识
    private String update_mark;//(填写获取更多预算)安卓系统更新标识

}
