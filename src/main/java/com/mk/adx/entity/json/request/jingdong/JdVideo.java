package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * video类型的广告位对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:51
 */
@Data
public class JdVideo {
    private int w;//广告位宽
    private int h;//广告位高度,保持原始渲染位置的高宽比
    private int minduration;//最小的视频广告持续时间以秒为单位
    private int maxduration;//最大的视频广告持续时间以秒为单位
    private int startdelay;//视频广告开始播放的延时时间>0: Mid-Roll（值代表延时） 0：Pre-Roll-1: GenericMid-Roll-2: GenericPost-Roll
    private List<String> mines;//支持的视频类型，如video/mp4
    private int linearity;//指示视频广告在视频流中的播放位置1：Linear/in-Stream，在视频流中展现2：Non-Linear/Overlay，悬浮在视频内容上展现
    private int minbitrate;//最小比特率，单位：Kbps
    private int maxbitrate;//最大比特率，单位：Kbps

}
