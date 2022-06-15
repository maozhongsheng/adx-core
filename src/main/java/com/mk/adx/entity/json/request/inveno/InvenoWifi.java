package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoWifi {
    private String mac;//热点MAC地址。
    private boolean is_connected;//热点MAC地址。
    private int rssi;//热点信号强度，通常是负数
    private String name;//热点名称，用于判断用户当前所处场所。
}
