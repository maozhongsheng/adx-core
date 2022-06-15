package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdGps {
    private double longitude;//GPS 坐标经度
    private double latitude;//GPS 坐标纬度
    private Integer coordinate_type;//1：全球卫星定位系统坐标系;2：国家测绘局坐标系,;3：百度坐标系;
    private long timestamp;//GPS 时间戳信息，单位：毫秒
    private Integer accu;//定位精度


}
