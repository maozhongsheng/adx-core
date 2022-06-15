package com.mk.adx.entity.json.request.algorix;

import lombok.Data;

@Data
public class AlgorixGeo {
    private float lat;//Latitude, 纬度
    private float lon;//Longitude, 经度
    private String country;//Country using ISO-3166-1 Alpha-3. 中国默认填写 "CHN" 即可
    private String region;//省份，"Guangdong" 等
    private String city;//Latitude, 纬度
    private Integer type;//定位信息来源，具体值请参考枚举 LocationType
}
