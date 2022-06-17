package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 地理位置对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class TzGeo {
    private float lat;//纬度从-90.0 + 90.0,-是南
    private float lon;//经度-180.0 + 180.0,-是西方。
    private String llt;//获取位置信息的时间与发起广告请求的时间差，单位为分钟
    private Integer lalo_type;//0 高德, 1 百度, 2 腾讯, 3 谷歌
}
