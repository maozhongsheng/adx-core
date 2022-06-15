package com.mk.adx.entity.json.request.jialiang;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 定位信息描述
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class JiaLiangGeo {
    private Float lat;//纬度，默认值0

    @JSONField(name = "long")
    private Float Long;//经度，默认值0

    private Integer coordinate;//坐标系；0：未知；1：WGS84全球卫星定位系统坐标系；2：GCJ02国家测绘局坐标系；3：BD09百度坐标系


}
