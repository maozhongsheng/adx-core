package com.mk.adx.entity.json.request.yunjuhe;

import lombok.Data;

/**
 * 云聚合
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/14 15:21
 */
@Data
public class YunJuHeBidRequest {
    private String version; //Api 版本号 0.0.1
    private YunJuHeApp app;  //App 信息
    private YunJuHeDevice device;  //设备信息
    private YunJuHeNetwork network;  //网络信息
    private YunJuHeGeo geo;  //地理位置；推荐传，不传影响填充
    private YunJuHeUser user;  //用户信息
    private YunJuHeImp imp;  //广告位信息，即对本次广告位的描述
    private Integer appStoreVersion;  //应用商店版本号，上游广告为 VIVO 时必传，不传没有广告,获得方式见详情
}
