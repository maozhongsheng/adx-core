package com.mk.adx.entity.json.request.yiliang;

import lombok.Data;

@Data
public class YiLiangGeo {
    private float lat;//经度，示例：113.776246
    private float lng;//经度，示例：113.776246
    private int coordTime;//获取经纬度(lat/lng)的时间，其值为从 GMT 1970-01-01 00:00:00 至今的毫秒值。
}
