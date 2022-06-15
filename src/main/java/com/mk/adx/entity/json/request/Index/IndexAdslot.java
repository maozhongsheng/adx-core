package com.mk.adx.entity.json.request.Index;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/8/30 15:21
 */
@Data
public class IndexAdslot {
    private int adType;//广告类型，横幅=1;信息流=2;开屏广告=3;插屏=4;视频=5;激励视频=6
//    private int position;//广告展现位置:顶部=1;底部=2;信息流内=3;中部=4;全屏=5
//    private int width;//宽度，单位像素
//    private int height;//高度，单位像素
}
