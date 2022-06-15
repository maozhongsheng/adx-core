package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunImp {
    private String id;//标识该广告位本次竞价请求的唯一 id
    private String tagid;//广告位唯一标识
    private int bidfloor;//千次展现单价，单位人民币分
    private BaiXunBanner banner;//Native 广告位信息 banner 和 native_ad、video 只会存在其一
    private BaiXunNative native_ad ;//Native 广告位信息 banner 和 native_ad、video 只会存在其一
    private BaiXunVideo video;//视频广告位信息
    private int w;// 广告位实际尺寸高，单位像素
    private int h;// 广告位实际尺寸高，单位像素
    private String ext;//扩展信息
}
