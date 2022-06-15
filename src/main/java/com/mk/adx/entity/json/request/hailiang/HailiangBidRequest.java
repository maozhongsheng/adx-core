package com.mk.adx.entity.json.request.hailiang;

import lombok.Data;

import java.util.List;

/**
 * 嗨量-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/26 13:49
 */
@Data
public class HailiangBidRequest {

    private String id;//必填，标识⼀次请求的唯⼀ID
    private String api_ver;//必填，标识⼀次请求的唯⼀ID
    private List<HailiangImp> imp;//必填，数组格式，⼀个完整的BidRequest必须包含⾄少⼀个Imp对象，⽬前只⽀持⼀个
    private HailiangApp app;//必填，当前请求发⾃某个应⽤时，必须提供完整的App对象
    private HailiangDevice device;//必填，发起当前请求的设备信息，Device对象中包含了⼤量必填信息，需要注意
    private HailiangUser user;//推荐，推荐填充设备⽤户（⼴告受众）对象
    private HailiangExt ext;//推荐，包含特定⼴告源所需的参数
    private boolean support_dp;//必填，是否⽀持deeplink，默认为false
    private int tmax;//可选，交易的最⼤超时毫秒值(包括⽹络延迟).不填默认2000ms

    private static final long serialVersionUID = 1L;

}
