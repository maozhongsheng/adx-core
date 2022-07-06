package com.mk.adx.entity.json.request.yiliang;

import lombok.Data;

@Data
public class YiLiangBidRequest {
    private String apiVersion; //协议版本，当前版本1.0，请严格填写 1.0
    private String sysVersion; //vivo系统ROM版本号，无法获取可填写unknow
    private int appstoreVersion; //vivo应用商店版本号，详见- vivo应用商店版本号获取方式
    private int timeout; //超时要求，设置后vivo侧会尽可能保证在该时间内返回，设置前请跟vivo侧运营沟通，不要自行设置。
    private YiLiangUser user; //用户信息，详见User Object描述
    private YiLiangMedia media; //媒体信息，详见Media Object描述
    private YiLiangPostion postion; //广告位信息，详见Position Object描述
    private YiLiangDevice device; //设备信息，详见Device Object描述
    private YiLiangNetWork network; //网络信息，详见Network Object描述
    private YiLiangGeo geo; //地理位置信息，详见Geo Object描述，若无法获取请勿传该字段
}
