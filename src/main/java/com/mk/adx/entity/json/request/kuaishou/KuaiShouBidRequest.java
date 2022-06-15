package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

import java.util.List;

/**
 * 快手总请求
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouBidRequest {

    private String protocolVersion;//协议版本号
    private KuaiShouAppInfo appInfo;//媒体 App 信息
    private List<KuaiShouImpInfo> impInfo;//媒体广告位信息
    private KuaiShouDeviceInfo deviceInfo;//设备信息
    private KuaiShouNetworkInfo networkInfo;//网络状态信息
    private KuaiShouGeoInfo geoInfo;//地理位置信息
    private KuaiShouUserInfo userInfo;//用户信息
    private List<Integer> excludeCreativeId;//屏蔽的创意 id
    private KuaiShouStatusInfo statusInfo;//客户端状态信息

}
