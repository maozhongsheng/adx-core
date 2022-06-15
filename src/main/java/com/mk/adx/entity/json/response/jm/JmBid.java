package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * 出价对象,广告集合,目前有且只有一个
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:51
 */
@Data
public class JmBid {
    private String id;//出价 ID，协助日志/跟踪
    private String impid;//曝光id	和请求中的imp保持一致
    private float price;//出价，cpm
    private String adid;//广告 ID
    private String nurl;//win notice url，竞价成功提醒 url,支持的“宏”标准
    private String adm;//广告素材URL
    private List<String> adomain;//广告商的 domain
    private String iurl;//进行广告质量/安全检查的 url，自己平台的曝光监测 client to server
    private String cid;//活动 ID ，以协助广告质量检查
    private String crid;//SP 系统中的创意 ID，以协助广告质量检查
    private List<String> cat;//创意的类型,参考媒体分类列表
    private List<String> attr;//描述创意广告的属性
    private String dealid;//参考的 deal id
    private Integer h;//高度创造性的像素
    private Integer w;//宽度创造性的像素
    private String title;//标题 (创意名称)
    private String sub_title;//副标题
    private String desc;//预留描述
    private String style_id;//创意的展现形式 id
    private String android_url;//android 下载 url
    private String ios_url;//ios 下载 url
    private String download_md5;//下载 md5
    private String click_url;//点击 url
    private String deeplink_url;//deeplinkUrl
    private Integer ad_type;//素材类型,参考 3.11
    private String adms;//广告素材集合	默认json格式，当ad_type为html时，则为html代码
    private String source;//广告来源
    private Integer valid_time;//广告过期时间，单位ms,过期后将不会显示
    private List<String> check_views;//曝光监测URL，支持宏替换 第三方曝光监测
    private List<String> check_clicks;//点击监测URL 第三方曝光监测
    private List<String> check_video_start;//视频开始播放监测
    private List<String> check_video_end;//视频播放结束监测
    private List<String> check_start_downloads;//开始下载监测URL 第三方曝光监测
    private List<String> check_end_downloads;//结束安装监测URL 第三方曝光监测
    private List<String> check_start_installs;//开始安装监测URL 第三方曝光监测
    private List<String> check_end_installs;//结束安装监测URL 第三方曝光监测
    private List<String> check_activations;//激活监测URL 第三方曝光监测
    private List<String> check_deeplinks;//尝试唤起监测数组
    private List<String> check_success_deeplinks;//唤醒成功监测URL 第三方曝光监测
    private List<String> check_fail_deeplinks;//唤醒失败监测URL 第三方曝光监测
    private String clicktype;//点击类型:0,点击 1,跳转 2,拉活(deeplink)
    private JmBidApps app;//应用信息，参考 2.4
    private List<JmImages> iamges;//图片对象数组
    private JmBidVideo video;//视频对象
    private List<JmCheckVideoUrls> check_video_urls;//视频监测地址
    private List<JmMacros> macros;//支持DSP定义宏替换 且指定范围

}
