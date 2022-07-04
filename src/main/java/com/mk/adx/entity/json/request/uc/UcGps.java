package com.mk.adx.entity.json.request.uc;

import lombok.Data;

/**
 * UC⽤户GPS信息，强烈建议
 * 填充，⽤于⽤户的地域识
 * 别，实在⽆法获取可以不
 * 填
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcGps {
    private String gps_time;//gps 信息的获取时间,时间戳,精确到秒
    private String lng;//经度
    private String lat;//纬度
    private String amap_code;//⾼德地理位置信息码
}
