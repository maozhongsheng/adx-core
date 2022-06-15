package com.mk.adx.entity.json.request.zhimeng;

import lombok.Data;

import java.util.List;

/**
 * 设备信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/8 14:28
 */
@Data
public class ZhimengDeviceInfo {

    private int type;//设备类型 0-未知 1-PC 2-Phone 3-Pad
    private int network;//网络类型 0-未知 1-WIFI 2-2G 3-3G 4-4G 5-5G
    private int os;//系统类型 0-未知 1-iOS 2-Android 3-MACOSX 4-Windows
    private String os_version;//系统版本
    private String brand;//设备品牌
    private String model;//设备型号
    private String ip;//IP地址
    private String ua;//User-Agent
    private int operator;//运营商 0-未知 1-中国移动2-中国电信 3-中国联通
    private String mac;//设备MAC地址
    private String idfa;//iOS设备idfa，与idfa_md5至少填一个
    private String idfa_md5;//iOS设备idfa MD5加密，与idfa至少填一个
    private String imei;//imei，与imei_md5，oaid至少填一个
    private String imei_md5;//imei MD5加密，与imei，oaid至少填一个
    private String android_id;//Android设备标识
    private String oaid;//Android设备标识，与imei_md5，imei至少填一个
    private List<String> app_package_name;//已安装app包名
    private float latitude;//设备所处纬度
    private float longitude;//设备所处经度

}
