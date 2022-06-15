package com.mk.adx.entity.json.response.yunjuhe;

import lombok.Data;

/**
 * 云聚合素材对象
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/21 17:25
 */
@Data
public class Feature {
    private Integer type;//
    private String materialUrl;//素材的 UR
    private Integer width;//素材宽
    private Integer height;//素材高
    private Integer vWidth;//视频宽
    private Integer vHeight;//视频高
}
