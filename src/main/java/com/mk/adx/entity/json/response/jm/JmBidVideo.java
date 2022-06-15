package com.mk.adx.entity.json.response.jm;

import lombok.Data;

/**
 * 视频对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:26
 */
@Data
public class JmBidVideo {
    private String url;//视频地址
    private Integer h;//视频高
    private Integer w;//视频宽
    private Integer duration;//视频时长
    private JmImages conver_image;//视频封面图
    private JmVideoExt ext;//视频扩展内容

}
