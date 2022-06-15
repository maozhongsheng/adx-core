package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunVideo {
    private String mimes;//视频格式,多个以逗号分割，如： video/mp4,video/3g
    private int minduration;//最小播放时长，单位秒
    private int maxduration;//最大播放时长，单位秒
    private int w;//w
    private int h;//h
    private int skip;//是否允许跳过
    private int skipafter;//几秒后允许跳
    private int videotype;//视频类型, 0
}
