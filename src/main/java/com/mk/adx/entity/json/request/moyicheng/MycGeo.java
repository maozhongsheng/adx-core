package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

/**
 * 地理位置对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class MycGeo {
    private double lat;//纬度从-90.0 + 90.0,-是南
    private double lon;//经度-180.0 + 180.0,-是西方。
    private int type;//源的位置数据,建议当纬度/经度
    private String country;//城市编码 ISO-3166-1-alpha-3
    private String region;//地区代码ISO-3166-2
    private String metro;//Google metro code（谷歌地铁编码）
    private String city;//城市使用联合国贸易与运输代码位置
}
