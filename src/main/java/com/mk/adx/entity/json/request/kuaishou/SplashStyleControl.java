package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 开屏广告媒体控制 actionBar 样式信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class SplashStyleControl {
    private boolean disableShake;//是否屏蔽摇一 摇，true：屏蔽，false 或不传: 不 屏蔽
    private boolean disableRotate;//是否屏蔽扭一 扭，true：屏蔽，false 或不传: 不 屏蔽
    private boolean disableSlide;//是否屏蔽滑动手势，true：屏 蔽，false 或不传: 不 屏蔽

}
