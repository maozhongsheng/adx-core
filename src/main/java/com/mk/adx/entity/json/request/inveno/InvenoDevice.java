package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

import java.util.List;

/**
 * 英威诺
 */
@Data
public class InvenoDevice {
    private List<InvenoDeviceId> device_id; //DeviceId对象列表，建议尽可能多填写，有助于提高广告变现能力。见DeviceId对象
    private int os_type; //用户设备的操作系统类型。取值：0=未知；1=IOS；2=Android；3=WP；
    private String os_version; //用户设备的操作系统版本号，必须真实来源于客户端。如，9.0
    private String brand; //用户设备的品牌，必须真实来源于客户端。Android来源于Build.BRAND，可用Build.MANUFACTURE代替，但效果会变差。iOS使用默认值apple
    private String model; //用户设备型号，必须真实来源于客户端。Android取Build.MODEL
    private int device_type; //用户设备类型。取值：0=未知；1=平板；2=手机；
    private String language; //用户设备设置的语言。如，zh_cn
    private int screen_width; //用户设备屏幕的宽度，以像素为单位，与密度无关像素
    private int screen_height; //用户设备屏幕的高度，以像素为单位，与密度无关像素
    private double screen_density; //用户设备屏幕密度
    private int screen_orientation; //用户设备屏幕朝向。取值：0=未知；1=竖屏；2=横屏
    private String boot_mark; //系统启动标识iOS例⼦: 1623815045.970028Android例⼦:ec7f4f33-411a-47bc-8067-744a4e7e0723
    private String update_mark; //系统更新标识iOS例⼦:1581141691.570419583Android例⼦:1004697.709999999
    private String hms_version; //华为安卓设备的 HMSCore的版本号
    private String appstore_version; //应用商店版本号

}
