package com.mk.adx.entity.json.request.tengxun;

import lombok.Data;

/**
 * 腾讯-优量汇-用户设备实时地理位置相关信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/26 14:21
 */
@Data
public class TengXunGeo {
    private int lat;//用户原始GPS坐标的纬度*1,000,000。该参数会用于基于地理位置的广告的定向，正确填写有助于提高流量变现效果
    private int lng;//用户原始GPS坐标的经度*1,000,000。该参数会用于基于地理位置的广告的定向，正确填写有助于提高流量变现效果
    private double location_accuracy;//经纬度精度半径，单位为米。该参数会用于基于地理位置的广告的定向，正确填写有助于提高流量变现效果

}
