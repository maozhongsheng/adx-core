package com.mk.adx.entity.json.request.yunjuhe;

import lombok.Data;

/**
 * Geo 对象
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/14 13:53
 */
@Data
public class YunJuHeGeo {
    private float lat;//纬度，gcj 坐标系，不传可能影响广告填充
    private float lng;//经度，gcj 坐标系，不传可能影响广告填充

}
