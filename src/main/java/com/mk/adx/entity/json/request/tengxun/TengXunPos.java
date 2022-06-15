package com.mk.adx.entity.json.request.tengxun;

import lombok.Data;

/**
 * 腾讯-优量汇-广告位相关信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/26 14:21
 */
@Data
public class TengXunPos {
    private int id;//广告位ID
    private int width;//广告位宽。该参数在联盟后台用来选取合适尺寸的广告，不要求与用户设备上真实的广告位宽严格一致
    private int height;//广告位高。该参数在联盟后台用来选取合适尺寸的广告，不要求与用户设备上真实的广告位高严格一致
    private int ad_count;//请求广告数量
    private String last_ad_ids;//最近曝光过的广告。联盟后台会过滤掉这些广告，不填写或填写错误可能导致短时间内多次请求返回的广告重复。
    private int deep_link_version;//是否请求应用直达广告
    private int max_duration;//视频广告最大播放时长,单位为秒

}
