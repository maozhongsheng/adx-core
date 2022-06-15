package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdWifi {
    private String ap_mac;//无线热点 MAC 地址，如获取不到可不传
    private String ap_name;//无线网 ssid 名称，如获取不到可不传
    private boolean is_connected;//是否是当前连接热点
    private String rssi;//热点信号强度
}
