package com.mk.adx.entity.json.request.hailiang;

import lombok.Data;

/**
 * 设备对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/26 13:53
 */
@Data
public class HailiangDevice {
    private String ua;//必填，描述发起当前请求的浏览器User Agent
    private String ip;//必填，当前设备的IP信息
    private String ipv6;//可选，当前设备的IPv6信息可以获取的时候，推荐同时填充ip和ipv6字段
    private String oaid;//条件必填，Android Q以后的⼿机唯⼀标识符。当请求发⾃Android设备时，必填
    private String did;//条件必填，设备号(IMEI)信息。当请求发⾃Android设备时，必填
    private String dpid;//条件必填，Android-id信息。当请求发⾃Android设备时，必填
    private String mac;//条件必填，设备MAC信息
    private String idfa;//条件必填，Apple设备的IDFA信息。当请求发⾃Apple设备时，必填
    private Integer carrier;//必填，提供完整的Carrier对象来描述当前运营商信息
    private String make;//必填，填充完整的make信息来描述当前设备的制造商信息
    private String model;//必填，填充完整的model信息来描述当前设备的型号信息
    private Integer os;//必填，描述当前设备的操作系统信息
    private String osv;//必填，当前设备操作系统的版本号信息
    private Integer w;//必填，当前设备显示屏的宽度
    private Integer h;//必填，当前设备显示屏的⾼度
    private Integer connectiontype;//必填，发起当前请求的设备⽹络连接类型信息
    private Integer devicetype;//必填，发起当前请求的设备类型信息
    private float lat;//选填，设备纬度
    private float lon;//选填，设备经度
    private int ppi;//选填，设备PPI
    private String boot_mark;//（填写获取更多预算）安卓系统启动标识
    private String update_mark;//（填写获取更多预算）安卓系统更新标识
    private String store_ver;//(填写获取更多预算)应用商店版本号

}
