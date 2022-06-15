package com.mk.adx.entity.json.request.simeng;

import lombok.Data;

@Data
public class SmNetWork {
    private String ip; //IPv4地址
    private int connectionType; //网络类型
    private int operatorType; //运营商类型
    private float lat; //纬度(无法获取填0)
    private float lon; //经度(无法获取填0)
}
