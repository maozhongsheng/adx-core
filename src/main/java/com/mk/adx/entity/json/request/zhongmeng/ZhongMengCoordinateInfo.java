package com.mk.adx.entity.json.request.zhongmeng;

import lombok.Data;

/**
 * 定位数据
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/8 14:21
 */
@Data
public class ZhongMengCoordinateInfo {
    private int coordinateType;//坐标类型0:全球卫星定位系统坐标 1:国家测绘局坐标系 2:百度坐标系 3. 高德坐标系 4. 腾讯坐标系 5. 谷歌坐标系 100.其他
    private double lng;//经度
    private double lat;//纬度
    private long timestamp;//获取坐标信息的时间戳,其值为从GMT 1970-01-01 00:00:00至今的毫秒值

}
