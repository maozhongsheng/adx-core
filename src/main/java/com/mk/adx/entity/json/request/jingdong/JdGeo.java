package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * 地理位置对象  按照 WGS84 坐标系输出经纬度
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class JdGeo {
    private double lat;//纬度信息，取值范围-90.0 到 90.0，负值表示南方
    private String latenc;//编码或加密后的纬度信息， 采用base64编码或对称方式加密，对于lat 和latenc 至少一个必填
    private double lon;//经度信息，取值范围-180.0 到 180.0,负值表示西方
    private String lonenc;//编码或加密后的经度信息， 采用base64编码或对称方式加密，对于lon 和lonenc 至少一个必填
}
