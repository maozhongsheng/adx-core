package com.mk.adx.entity.json.response.mk;

import lombok.Data;

import java.util.List;

/**
 * 视频监测地址
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/16 17:29
 */
@Data
public class MkCheckVideoUrls {
    private List<String> url;//视频监测地址
    private float time;//视频时间,0~1 之间,默认值为 0
}
