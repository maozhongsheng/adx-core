package com.mk.adx.entity.json.response.yoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoAdms {
    private String title;//标题
    private String desc;//描述
    private String admid;//创意id
    private String owner_name;//广告主名称
    private String click_url;//点击跳转h5页面地址，支持%%WIN_PRICE%%二价宏（不允许传递App直接下载地址）
    private String downloadUrl;//下载类广告，App直接下载地址
    private List<String> imp_trackers;//展示跟踪链接数组，支持%%WIN_PRICE%%二价宏
    private List<String> clk_trackers;//点击跟踪链接数组，支持%%WIN_PRICE%%二价宏
    private String dpl_url;//Deeplink协议，用户呼起广告主App。支持%%WIN_PRICE%%二价宏
    private List<String> dp_trackers;//Deeplink点击跟踪链接数组。当返回Deeplink字段时返回此字段
    private int ad_type;//广告类型，0：落地页广告; 1：下载类广告; 2：deeplink
    private int style_type;//广告样式名称，详见附录11：信息流-小图2：信息流-大图：信息流-组图4：信息流-视频5：开屏-视频6：开屏-图片7：横幅 690*140     10：横幅 750*520     8：信息流-竖版视频 1080*1920     9：信息流-竖版大图 1080*1920     18：信息流-竖版视频 300*400     19：信息流-竖版大图 300*400
    private List<String> images;//图片链接
    private int imageWidth;//w
    private int imageHeight;//h
    private String icon_image;//图标链接
    private String app_name;//当广告为下载类型时，并且app_name不为空时，会返回此字段
    private String package_name;//当广告为下载类型时，并且package_name不为空时，会返回此字段
    private List<String> download_trackers;//app下载时需要上报的url列表
    private List<String> downloaded_trackers;//app下载完成时需要上报的url列表
    private List<String> installed_trackers;//app安装完成时需要上报的url列表
    private int duration;//视频广告时长，单位秒
    private List<YolohoPlay> play_trackers;//当返回素材是视频或者视频信息流试，对视频播放进度进行检测上报
    private String video_url;//视频地址
    private int download_popups_type;//下载类弹框类型：0不弹框；1基础弹框；2六要素弹框
    private YolohoApp appInfo;//视频地址
}
