package com.mk.adx.entity.json.request.doumeng;

import lombok.Data;

@Data
public class DouMengNetwork {
    private String ip;//ipv4地址
    private String ip6;//ipv6地址
    private String connectionType;//链接类型:unknown,2g,3g,wifi,4g,5g
    private String operatorType;//网络信息:unknown（未知）,cm(移动),cu（联通）,ct（电信），cr（铁通）
    private String lat;//纬度
    private String lon;//经度
    private long gpsTs;//获取 gps 位置时的时间戳,单位：秒
}
