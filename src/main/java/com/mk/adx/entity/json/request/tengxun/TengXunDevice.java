package com.mk.adx.entity.json.request.tengxun;

import lombok.Data;

/**
 * 腾讯-优量汇-用户设备相关信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/26 14:21
 */
@Data
public class TengXunDevice {
    private String os;//操作系统。不填将不返回广告，填写错误会影响流量变现效果
    private String os_version;//os版本。不填将不返回广告，填写错误或填写unknown会影响流量变现效果
    private String model;//设备型号。不填将不返回广告，填写错误或填写unknown会影响流量变现效果
    private String manufacturer;//设备厂商。安卓设备不填将不返回广告，填写错误或填写unknown会影响流量变现效果。
    private int device_type;//设备类型。不填将不返回广告，填写错误或填写unknown会影响流量变现效果
    private int screen_width;//设备竖屏状态时的屏幕宽。取设备物理像素
    private int screen_height;//设备竖屏状态时的屏幕高。取设备物理像素
    private int orientation;//APP横竖屏。激励视频广告位该参数为必填
    private String imei;//android设备的imei，保留原始值。在能取到的情况下必须填写，不填写或填写错误会严重影响流量变现效果。
    private String imei_md5;//android设备的imei（如果出现字母，转为小写），取md5sum摘要，摘要小写，32位。例如：imei为100000239060471，则该参数应填写md5(100000239060471)=df895d8ed25548b603fddf94fecdd7cc。该参数用来替补imei，两者传其一即可，强烈建议优先填写imei。
    private String android_id;//android设备的Android ID，保留原始值。在能取到的情况下必须填写，不填写或填写错误会影响流量变现效果
    private String android_id_md5;//android设备的Android ID，取md5sum摘要，摘要小写。该参数用来替补android_id，两者传其一即可，强烈建议优先填写android_id
    private String android_ad_id;//android设备的Android Advertising ID，保留原始值。大陆大部分设备无法获取，在保证取值正确有效的前提下填写，后续腾讯广告联盟会将其用于定向优化
    private String oaid;//android设备的OAID，保留原始值，部分厂商部分安卓系统版本提供，MSA官方链接为：http://msa-alliance.cn/
    private String idfa;//ios设备的idfa，保留原始值。不填将不返回广告，填写错误会严重影响流量变现效果
    private String idfa_md5;//ios设备的idfa（如果出现字母，转为大写），取md5sum摘要，摘要小写，32位。该参数用来替补idfa，两者传其一即可，强烈建议优先填写idfa
    private String device_start_sec;//设备启动时间（秒）
    private String country;//国家
    private String language;//语言
    private String device_name_md5;//设备名称的MD5值
    private String hardware_machine;//设备machine值
    private String hardware_model;//设备model值
    private String physical_memory_byte;//物理内存
    private String harddisk_size_byte;//硬盘大小
    private String system_update_sec;//系统更新时间
    private String time_zone;//时区
}
