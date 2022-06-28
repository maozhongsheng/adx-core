package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

@Data
public class YuekeVideo {
    private int w;//广告位视频所要求的宽度: 单位：像素 px
    private int h;//广告位视频所要求的高度: 单位：像素 px
    private int type;//频类型，选项:( 1：原生视频，2：激励视频 ) 默认：1
    private int minduration;//视频播放的最小时长: 单位：秒
    private int maxduration;//视频播放的最大时长: 单位：秒
    private int startdelay;//视频在流量售卖位中的位置，选项：（0：前贴，1：中贴，2：后贴）
    private int linearity;//视频在广告位中的展示方式，选项：(1：视频流中展示，2：视频内容上悬浮展示)
    private int pos;//流量售卖位在设备屏幕上显示的位置编号，默认: 0
    private int mimes;//默认: video/mp4
}
