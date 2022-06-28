package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

@Data
public class YuekeGeo {
    private float lat;//纬度，保留小数点后 4 位 建议必填 默认：0
    private float lon;//纬度，保留小数点后 4 位 建议必填 默认：0
    private int coordinate;//WGS84 = 1 全球卫星定位系统坐标系, GCJ02 = 2 国家测绘局坐标系, BD09 = 3 百度坐标系
    private Long timestamp;//纬度，保留小数点后 4 位 建议必填 默认：0
    private int accu;//纬度，保留小数点后 4 位 建议必填 默认：0

}
