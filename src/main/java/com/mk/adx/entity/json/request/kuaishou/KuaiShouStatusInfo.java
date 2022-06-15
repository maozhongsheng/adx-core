package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 客户端状态信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouStatusInfo {
    private int personalRecommend;//个性化推荐开关：关闭后，看到的广告数量不变，相关度将降低。 是否允许开启广告的个性化推荐（0-关闭，1-开启），由开发者通过SDK 的接口来设置。不设置的话则 默认为 1
    private int programmaticRecommend;//程序化推荐开关：关闭后，看到的广告数量不变，但将不会为你推荐程序 化广告。是否允许开启广告的程序化推荐（0-关闭，1-开启），由开发者通过SDK 的接口来设置。不设置的话则 默认为 1。
    private SplashAdInfo splashAdInfo;//客户端上传的开屏广告相关的状态信息
}
