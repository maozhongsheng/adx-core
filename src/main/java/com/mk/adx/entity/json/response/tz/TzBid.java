package com.mk.adx.entity.json.response.tz;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.util.List;

/**
 * 出价对象,广告集合
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:51
 */
@Data
public class TzBid {
    private String id;//出价 ID，协助日志/跟踪
    private String impid;//request 中的 impid
    private float price;//出价，cpm  Float
    private String adid;//广告 ID
    private String nurl;//win notice url，竞价成功提醒 url,支持的“宏”标准
    private String adm;//广告素材URL
   // private TzAdm adms;//素材对象
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
    private String download_url;//下载地址
    private String download_md5;//下载 md5
    private String click_url;//点击 url
    private String deeplink_url;//deeplinkUrl
    private String deeplink_murl;//deeplink 链接调起成功监测地址
    private String fallback;//跳转替换地址。如果al 链接不支持，使用本地址,ad_type=8 时可能返回该字段
    private String aptAppId;//小程序在微信开放平台注册并获取的id
    private String aptOrgId;//小程序获取的appid
    private String aptPath;//小程序的入口路径，由小程序开发者指定，如果没有是进入到小程序的首页
    private String aptType;//小程序类型
    private String aptUL;//universalLink 微信 iOS SDK注册 的时候需要用到 的参数
    private Integer ad_type;//返回的广告素材类型,0=>图片横幅广告1=>纯文字广告2=>图文广告3=>图片插屏广告4=>html广告5=>开屏（图片、 视频6=>交互视频广告7=>贴片视频广告8=>原生广告9=>wap原生广告10>wap html广告100=>富媒体插屏
    private String as;//广告尺寸。at =8 时不用返回。
    private String xs;//广告物料，xs 为 HTML 代码段,ad_type=4|10 时填充
    private String index;//序号
    private String source;//广告来源
    private Integer valid_time;//广告过期时间，单位ms,过期后将不会显示
    private List<String> check_views;//曝光监测URL，支持宏替换 第三方曝光监测
    private List<String> check_clicks;//点击监测URL 第三方曝光监测
    private List<String> check_start_downloads;//开始下载监测URL 第三方曝光监测
    private List<String> check_end_downloads;//结束下载监测URL 第三方曝光监测
    private List<String> check_start_installs;//开始安装监测URL 第三方曝光监测
    private List<String> check_end_installs;//结束安装监测URL 第三方曝光监测
    private List<String> check_activations;//激活监测URL 第三方曝光监测
    private List<String> check_success_deeplinks;//唤醒成功监测URL 第三方曝光监测
    private List<String> check_fail_deeplinks;//唤醒失败监测URL 第三方曝光监测
    private String clicktype;//点击类型:0,点击 1,跳转 2,拉活(deeplink)
    private String aic;//广告图标（Icon的url地址), ad_type =0|1|2|3|5 时填充
    private String ate;//插屏广告描述, ad_type =0|1|2|3|5 时填充
    private String abi;//广告行为转化图标URL, ad_type =0|1|2|3|5 时填充
    private String adLogo;//广告来源Logo, ad_type =0|1|2|3|5 时填充
    private List<TzImage> images;//图片对象数组
    private TzVideo video;//视频对象
    @JSONField(name = "native")
    private TzNative NATIVE;//Native对象
    private List<TzCheckVideoUrls> check_video_urls;//视频监测地址
    private TzAdvertiser advertiser;//广告主对象
    private TzBidApps app;//应用信息，参考 2.4
    private TzAttachDetail attach_detail;//附加信息，参考 2.10
    private TzExt ext;//扩展站位对象
    private List<TzMacros> macros;//支持DSP定义宏替换 且指定范围
    private Integer altype;//0- 普通落地页1- 广点通专用落地页，需要替换广点通的点击坐标宏以及转化上报
    private String gdt_conversion_link;//广点通专用转化上报链接，需要替换转化上报宏字段
    private String image;//单个图片

}
