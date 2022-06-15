package com.mk.adx.entity.json.request.renze;


import lombok.Data;

/**
 * 仁泽-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class RzBidRequest {
    private String version;//Api 版本号 0.0.1
    private RzApp app;//App信息
    private RzDevice device;//设备信息
    private RzNetwork network;//网络信息
    private RzGeo geo;//地理位置；推荐传，不传影响填充
    private RzImp imp;//广告位信息，即对本次广告位的描述
    private Integer appStoreVersion;//应用商店版本号，上游广告为 VIVO 时必传，不传没有广告,获得方式见详情
}
