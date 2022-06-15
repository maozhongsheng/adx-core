package com.mk.adx.entity.json.response.jm;

import lombok.Data;

/**
 * 视频监测地址
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:29
 */
@Data
public class JmCheckVideoUrls {
    private String url;//视频监测地址
    private int time;//视频时间,0~1 之间,默认值为 0
}
