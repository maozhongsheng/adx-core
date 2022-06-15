package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

import java.util.List;

/**
 * video类型的广告位对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:51
 */
@Data
public class SdVideo {
    private int w;//播放器宽（广告位尺寸）
    private int h;//播放器高（广告位尺寸）
    private int minduration;//最小的视频广告持续时间以秒为单位
    private int maxduration;//最大的视频广告持续时间以秒为单位
    private List<String> mimes;//==内容支持MIME的类型,video/mp4

}
