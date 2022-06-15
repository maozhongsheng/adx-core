package com.mk.adx.entity.json.request.ruidi;


import lombok.Data;

/**
 * 瑞迪
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class RdDevice {
    private Integer device_type; //设备类型1：手机(含 iTouch) 2：平板3：智能电视 4：户外屏幕
    private Integer os_type; //操作系统1：ANDROID 2：IOS
    private RdVersion os_version; //操作系统版本信息
    private String vendor; //设备厂商名称
    private String brand; //设备品牌
    private String model; //设备型号
    private RdAdSize screen_size; //设备屏幕尺寸信息
    private String user_agent; //设备浏览器 UA
    private Integer orientation; //设备横竖屏，默认为竖屏1：横屏，2：竖屏
    private RdUdid udid; //设备唯一标识对象
    private Integer ppi; //屏幕像素密度
    private double density; //屏幕分辨率安卓参考:DisplayMetrics.densityiOS 参考:UIScreen.scale
    private String rom_version; //设备 ROM 版本，如获取不到可不传
    private long sys_compiling_time; //系统编译时间(ro.build.date.utc)，精确至毫秒
    private String referer; //用户设备 HTTP 请 求 头 中 的referer 字段
    private String country; //国家编码(ISO-3166-1/alpha-2)
    private String language; //设备语言(ISO-639-1/alpha-2)
    private String timezone; //系统所在时区
    private String startup_time; //设备开机时间(秒级时间戳)，小数点后保留 6 位，IOS 系统必填
    private String phone_name; //手机名称，IOS 系统必填
    private long mem_total; //手机内存总空间(单位：字节)，IOS系统必填
    private long disk_total; //手机磁盘总空间(单位：字节)，IOS系统必填
    private String mb_time; //系统更新时间(秒级时间戳)，小数点后保留 6 位，IOS 系统必填
    private String mode_code; //设备 model，IOS 系统必填
    private Integer auth_status; //广告标识授权情况，是否允许获取 IDFA，仅 IOS 系统填写0：未确定 1：受限制2：被拒绝 3：授权
    private Integer cpu_num; //设备 CPU 个数，IOS 系统必填
    private String battery_status; //电池充电状态，IOS 系统必填Unkow：未知：不充电Charging：充电 Full：满电
    private Integer battery_power; //电池电量百分比，IOS 系统必填
    private double cpu_frequency; //手机 CPU 频率，单位：GHz, IOS系统必填
    private String hwv; //设备硬件版本
    private Integer api_level; //安卓 API 等级(iOS 不填)
    private String storever; //应用商店版本号
    private String hmsver; //华为 hms 版本号
    private String boot_mark;
    private String update_mark;


}
