package com.mk.adx.entity.json.request.jiaming;

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
public class JmVideo {
    private List<String> mimes;//==内容支持MIME的类型,video/mp4
    private int minduration;//最小的视频广告持续时间以秒为单位
    private int maxduration;//最大的视频广告持续时间以秒为单位
    private int w;//广告位宽
    private int h;//广告位高

}
