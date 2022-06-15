package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoGps {
    private int type;//坐标类型。取值：1= WGS84(全球卫星定位系统坐标系)；2= GCJ02（国家测绘局坐标系）；3= BD09（百度坐标系）
    private double longitude;//地理位置-经度（小数点格式），如，115.234521取值范围：-180 ~ 180
    private double latitude;//地理位置-纬度（小数点格式），如, 60.323456取值范围：-90 ~ 90
    private int timestamp;//时间戳信息，单位毫秒
}
