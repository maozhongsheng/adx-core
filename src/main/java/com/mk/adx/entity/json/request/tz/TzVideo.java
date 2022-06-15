package com.mk.adx.entity.json.request.tz;

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
public class TzVideo {
    private List<String> mimes;//==内容支持MIME的类型,video/mp4
    private Integer minduration;//最小的视频广告持续时间以秒为单位
    private Integer maxduration;//最大的视频广告持续时间以秒为单位
    private List<Integer> protocols;//数组支持的视频投标响应协议,参考 3.4
    private Integer w;//广告位宽
    private Integer h;//广告位高
    private Integer startdelay;//该字段仅在 linearity=1 时有效；参考 3.5
    private List<Integer> battr;//创意属性
    private Integer minbitrate;//最低bit速度
    private Integer maxbitrate;//最高bit速度
    private Integer pos;//广告位位置，0:未知，1:首屏，2：次屏
    private Integer linearity;//广告展现样式,参考 3.6

}
