package com.mk.adx.entity.json.response.jd;

import lombok.Data;

import java.util.List;

/**
 * Item 对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/2 16:48
 */
@Data
public class JdItem {
    private String title;//标题
    private String desc;//描述
    private String ad_resource;//广告来源，用于媒体侧展示广告来源，如：JD 或者拉下载的APP 名称
    private String id;//序号
    private String media_style;//媒体端广告样式类型：1：跳转类广告，使用 click_url浏览器打开落地页或者 dpl_url在app打开落地页2：下载类广告，需使用原生方法处理download_url ，并单独触发 click_url 点击监测
    private String download_url;//当media_style为 下 载 时，download_url 为应用下载链接
    private String click_url;//点击跳转h5 页面地址（已封装点击监测，不需要再单独触发点击监测）。ADX模式必 须 替 换 URL 里 面 的%%WIN_PRICE%%二价宏，展示分成模式不需要。
    private String dpl_url;//Deeplink协议，用于呼起京东APP或广告主APP。
    private String img;//图片素材 url
    private List<JdImage> imgs;//返回多张图片 url 时的对象，此时 img字段不返回。
    private String video;//视频素材 url
    private List<String> exposal_urls;//曝光监测 url列表, 会有多个，每个 url都需要发送。必须替换 URL里面的%%WIN_PRICE%%二价宏。
    private List<String> click_monitor_urls;//点击监测url 列表，可能会有多个，每个都需要触发上报
}
