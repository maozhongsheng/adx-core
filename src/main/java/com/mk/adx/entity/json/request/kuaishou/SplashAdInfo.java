package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 开屏广告状态信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class SplashAdInfo {
    private int dailyShowCount;//客户端上传的天级广告曝光次数
    private SplashStyleControl splashStyleControl;//媒体控制开屏各种actionBar 样式是否展示，目前有 摇一摇、 扭一扭、滑动

}
