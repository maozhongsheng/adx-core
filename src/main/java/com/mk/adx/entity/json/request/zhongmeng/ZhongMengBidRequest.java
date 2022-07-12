package com.mk.adx.entity.json.request.zhongmeng;

import lombok.Data;

/**
 * 众盟总请求
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/8 14:21
 */
@Data
public class ZhongMengBidRequest {

    private ZhongMengReqInfo reqInfo;//广告位请求配置数据
    private ZhongMengAdSlotInfo adSlotInfo;//广告位基本信息
    private ZhongMengMobileInfo mobileInfo;//手机的相关配置
    private ZhongMengNetworkInfo networkInfo;//当前网络连接信息
    private ZhongMengCoordinateInfo coordinateInfo;//定位数据

}
