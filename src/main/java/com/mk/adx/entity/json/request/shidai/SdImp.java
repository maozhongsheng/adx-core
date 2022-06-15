package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

/**
 * 描述广告位对象-曝光对象-native
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:48
 */
@Data
public class SdImp {
    private String id;//曝光id,不是广告位i
    private int type;//广告位类型： 1 – banner 2 – 开屏 3 – 插屏 4 – 原生 5 – 激励视频
    private String tagid;//广告位ID
    private SdBanner banner;///banner类型的广告位对象,横幅类型
    private SdVideo video;//video类型的广告位对象,视频类型
    private SdPmp pmp;//管理协议规则对象
    private int bidfloor;//广告位底价，单位是：分/CPM
    private int dplink;//是否支持deeplink，0-不支持 1-支持（默认）




}
