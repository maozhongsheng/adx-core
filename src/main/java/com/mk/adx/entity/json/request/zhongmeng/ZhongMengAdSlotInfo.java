package com.mk.adx.entity.json.request.zhongmeng;

import lombok.Data;

/**
 * 广告位请求配置数据
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/8 14:21
 */
@Data
public class ZhongMengAdSlotInfo {
    private String mimes;//广 告 位 支 持 的 物 料 类 型 e.g. jpg/gif/png/mp4/webp/flv/swf/txt/icon/c 其中:txt 指文字链,icon指图文,c指富文本 纯图片素材请求可以等价设置mimes为 img
    private int slotWidth;//广告位⻓度
    private int slotHeight;//广告位宽度
    private int minDuration;//视频广告位允许最短时⻓(s)
    private int maxDuration;//视频广告位允许最大时⻓(s)
//    private int pos;//广告展示位置,顶部:1;底部:2;信息流内:3; 中部:4;全屏:5
    private String videoTitle;//当前视频的标题(视频类广告位)
    private int videoLength;//当前视频内容⻓度(单位秒,视频类广告位)

}
