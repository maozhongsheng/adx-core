package com.mk.adx.entity.json.response.ydzx;

import lombok.Data;

/**
 * 视频对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/8/3 17:26
 */
@Data
public class YdzxVideo {
    private Integer w;//视频宽
    private Integer h;//视频高
    private Long size;//视频文件大小，单位字节
    private String videourl;//视频素材地址
    private String videoduration;//视频时长，单位秒
    private String logourl;//下载图标url，仅用于竖版版视频

}
