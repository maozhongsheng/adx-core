package com.mk.adx.entity.json.request.yidianzixun;

import lombok.Data;

/**
 * 地理位置对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:53
 */
@Data
public class YdzxGeo {
    private float lat;//纬度从-90.0 + 90.0,-是南
    private float lon;//经度-180.0 + 180.0,-是西方。
}
