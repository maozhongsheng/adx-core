package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

@Data
public class YolohoImps {
    private String impid;//展示ID，唯一标识一个展示;由媒体侧生成，请确保全局唯一
    private double bidfloor;//底价，单位：分/千次（人民币），即CPM
    private YolohoBannerAd banner_ad;//Banner广告，返回素材html+JS
    private YolohoNativeAd native_ad;//原生广告，返回素材为图片地址、文字标题等元素
    private int macro;//是否支持宏替换，宏定义参考宏替换0：不支持 （默认）1：支持
    private int isdeeplink;//是否支持呼起广告主app。只有native广告支持0：不返回（默认）     1：返回deeplink url
    private int secure;//要求创意中只包含 https（曝光检测、点击、素材url等均为https）0：http或https的创意1：仅包含https的创意
    private YolohoContext context;//内容页或信息流上下文信息

}
