package com.mk.adx.entity.json.request.xiaomi;

import lombok.Data;

@Data
public class XiaoMiDevice {
    private String make;
    private String model;
    private String os;
    private String androidVersion;
    private String miuiVersion;
    private int screenWidth;
    private int screenHeight;

}
