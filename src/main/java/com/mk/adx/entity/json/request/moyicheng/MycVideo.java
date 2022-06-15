package com.mk.adx.entity.json.request.moyicheng;

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
public class MycVideo {
    private Integer format;//返回广告数据协议，1-VAST(默认)，2-JSON
    private List<String> mimes;//==内容支持MIME的类型,video/mp4
    private Integer minduration;//最小的视频广告持续时间以秒为单位
    private Integer maxduration;//最大的视频广告持续时间以秒为单位
    private List<Integer> protocols;//支持的vast video协议，见附录5.3
    private Integer w;//播放器宽（广告位尺寸）
    private Integer h;//播放器高（广告位尺寸）
    private Integer skip;//是否允许视频可跳过(1=Yes,0=No)
    private Integer pos;//广告在屏幕上曝光位置，见附录5.4


}
