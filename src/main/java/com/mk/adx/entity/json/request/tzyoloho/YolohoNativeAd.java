package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

@Data
public class YolohoNativeAd {
    private String spaceid; //广告位ID，在yoloho平台注册生成的广告位id
    private int w; //广告位宽度，单位：px
    private int h; //广告位高度，单位：px

}
