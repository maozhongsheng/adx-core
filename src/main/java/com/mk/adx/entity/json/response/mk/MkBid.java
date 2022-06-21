package com.mk.adx.entity.json.response.mk;

import lombok.Data;
import java.util.List;

/**
 * 出价对象,广告集合
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/16 16:51
 */
@Data
public class MkBid {
    private float price;//出价，cpm  Float
    private String adid;//广告 ID
    private String nurl;//win notice url，竞价成功提醒 url,支持的“宏”标准
    private String adm;//广告素材URL
    private String title;//标题 (创意名称)
    private String sub_title;//副标题
    private String desc;//描述
    private String download_url;//下载地址
    private String click_url;//点击 url
    private String deeplink_url;//deeplinkUrl
    private String clicktype;//点击类型:0,点击 1,跳转 2,拉活(deeplink)
    private Integer ad_type;//返回的广告素材类型,0=>图片横幅广告1=>纯文字广告2=>图文广告3=>图片插屏广告4=>html广告5=>开屏（图片、 视频6=>交互视频广告7=>贴片视频广告8=>原生广告9=>wap原生广告10>wap html广告100=>富媒体插屏
    private String source;//广告来源
    private List<String> check_views;//曝光监测URL，支持宏替换 第三方曝光监测
    private List<String> check_clicks;//点击监测URL 第三方曝光监测
    private List<String> check_start_downloads;//开始下载监测URL 第三方曝光监测
    private List<String> check_end_downloads;//结束下载监测URL 第三方曝光监测
    private List<String> check_start_installs;//开始安装监测URL 第三方曝光监测
    private List<String> check_end_installs;//结束安装监测URL 第三方曝光监测
    private List<String> check_activations;//激活监测URL 第三方曝光监测
    private List<String> check_success_deeplinks;//唤醒成功监测URL 第三方曝光监测
    private List<String> check_fail_deeplinks;//唤醒失败监测URL 第三方曝光监测
    private String aic;//广告图标（Icon的url地址), ad_type =0|1|2|3|5 时填充
    private String adLogo;//广告来源Logo, ad_type =0|1|2|3|5 时填充
    private List<MkImage> images;//图片对象数组
    private MkVideo video;//视频对象
    private List<MkCheckVideoUrls> check_video_urls;//视频监测地址
    private MkApp app;//应用信息，参考 2.4

}
