package com.mk.adx.entity.json.request.renze;


import lombok.Data;

/**
 * 地理位置
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class RzGeo {
    private float lat;//纬度，gcj 坐标系，不传可能影响广告填充
    private float lng;//经度，gcj 坐标系，不传可能影响广告填充
}
