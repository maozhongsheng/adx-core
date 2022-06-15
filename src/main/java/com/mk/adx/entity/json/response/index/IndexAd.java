package com.mk.adx.entity.json.response.index;


import lombok.Data;

import java.util.List;

/**
 * Index总返回数据实体
 *
 * @author gj
 * @version 1.0
 * @date 2021/9/2 16:18
 */
@Data
public class IndexAd {
    private String adId;//广告 ID

    private List<String> imageSrcs;//广告图片地址，单个广告可能存在多个图片地址

    private String clickAdUrl;//广告点击链接,若 InteractionType=3 时，该字段值可能为下载地址，与 downloadUrl 相同

    private String deeplink;//app 端 deeplink 链接，该参数有值优先处理该值，若无值则调用 clickAdUrl

    private String reportAddHeaderUa;//注：上报时,http header 头中需要增加 User-Agent 属性

    private List<IndexTrackVo> tracks;//Track 对象列表，用于上报广告执行情况，同一类型的 track 可能有多个，必须全部依次上报。


}
