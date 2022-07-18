package com.mk.adx.entity.json.request.huanrui;

import lombok.Data;

import java.util.List;

/**
 * 设备对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/26 13:53
 */
@Data
public class HuanRuiDevice {
    private int os;
    private String osv;
    private String brand;
    private String model;
    private float dpi;
    private Integer sw;
    private Integer sh;
    private String ua;
    private List<String> installs;
    private String oaid;
    private String imsi;
    private String imei;
    private String imei_md5;
    private String serial_no;
    private String androidid;
    private String androidid_md5;
    private String idfa;
    private String idfa_md5;
    private String idfv;
    private String openudid;
    private String ssid;
    private String wifi_mac;
    private String rom_version;
    private String sys_compling_time;
    private String store_url;
    private Integer can_deepLink;
    private String ipv6;
    private String phone_name;
    private String language;
    private String country;
    private String boot_time;
    private String op_up_time;
    private Integer disk_size;
    private String battery_status;
    private String battery_power;
    private Integer memory_size;
    private Integer cpu_number;
    private String cpu_frequency;
    private String model_code;
    private String time_zone;
    private Integer lmt;
    private Integer laccu;
    private String caid;
    private String app_store_version;
    private String hms_ver;
    private String hwag_ver;
    private String boot_mark;
    private String update_mark;

}
