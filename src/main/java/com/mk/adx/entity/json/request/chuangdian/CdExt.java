package com.mk.adx.entity.json.request.chuangdian;

import lombok.Data;

import java.util.List;

/**
 * 创典
 */
@Data
public class CdExt {
    private String applist; //已安装的app包名列表, 以逗号分隔。
    private int is_cache_idfa; //是否为缓存的idfa， 0 - 实时获取的idfa，1 - 缓存 的idfa
    private List<String> wifilist; //设备wifilist信息
    private String verCodeOfHms; //HMS Core版本号,华为设备必填,详⻅9.1节如何获取 字段
    private String verCodeOfAG; //应⽤市场版本号,华为设备必填,详⻅9.1节如何获取
    private String agCountryCode; //应⽤市场App中设置⾥⾯的国家和地区, 华为设备推 荐填写,若获取不到就不填
}
