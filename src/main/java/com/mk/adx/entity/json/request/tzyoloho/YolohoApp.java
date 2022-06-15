package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

@Data
public class YolohoApp {
    private String appid;//app唯一标识，由yoloho分配给媒体
    private String name;//app名称
    private String bundle;//app包名
    private int ver;//app版本号
    private String ext;//自定义参数
}
