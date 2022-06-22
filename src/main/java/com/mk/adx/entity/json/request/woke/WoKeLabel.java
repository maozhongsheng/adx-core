package com.mk.adx.entity.json.request.woke;

import lombok.Data;

@Data
public class WoKeLabel {
    private String title;//播放内容的相关信息 如歌名 歌词等，存在多个时 用英文逗号分隔
    private String url;//播放内容地址
    private String progress;//最终播放进度百分比 如：60用户听了60%

}
