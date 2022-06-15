package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

/**
 * 地理位置对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class SdGeo {
    private float lat;//纬度从-90.0 + 90.0,-是南
    private float lon;//经度-180.0 + 180.0,-是西方。
    private int type;//坐标类型：1-GPS/定位服务，2-IP地址
    private String country;//国家，默认中国
    private String region;//地区代码ISO-3166-2
    private String city;//城市使用联合国贸易与运输代码位置
}
