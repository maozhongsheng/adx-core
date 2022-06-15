package com.mk.adx.entity.json.request.algorix;

import lombok.Data;

@Data
public class AlgorixDeviceExt {
    private String idfv;//idfv, iOS 的 IDFV
    private String oaid;//oaid；支持获取oaid的Android设备必填
    private String imei;//imei；支持获取imei的Android设备必填
    private String androidid;//Android ID
    private String mac;//设备MAC地址；支持获取则传
    private String screenorientation;//屏幕方向，具体值请参考枚举 ScreenOrientation
    private String bootmark;//系统启动标识。建议获取，影响填充
    private String upgrademark;//系统更新标识。建议获取，影响填充
}
