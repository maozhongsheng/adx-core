package com.mk.adx.entity.json.request.yueke;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 位置信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/12/8 14:26
 */
@Data
public class YuekeImp {

    private String id;//售卖广告位置描述编号，从 1 递增，默认：1
    private int aw;//广告位置的真实宽度，单位：像素 px
    private int ah;//广告位置的真实高度，单位：像素 px
    private String tagid;//广告位置 ID，该信息由智友广告交易系统提供
    private float bidfloor;//竞价底价信息，每千次曝光底价，单位:分，默认币种 CNY，默认：0
    private YuekeBanner banner;//纯图片类广告，当广告位为 "开屏"，"插屏" 和“横幅”样式时必填
    private YuekeVideo video;//视频广告，当广告位为“激励视频”样式时必填
    @JSONField(name = "native")
    private YuekeNative NATIVE;//原生广告，当广告位为“图文信息流”或“视频信息流”样式时必填
    private YuekePmp pmp;//原生广告，当广告位为“图文信息流”或“视频信息流”样式时必填
    private List<String> mts;//原生广告，当广告位为“图文信息流”或“视频信息流”样式时必填


}
