package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

import java.util.List;

@Data
public class InvenoAdspaces {
    private String adspace_id;//广告位id，唯一标识一个广告位，由INVENO提供
    private int adspace_type;//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
    private boolean allowed_html;//由INVENO渲染还是由媒体渲染。暂时只支持媒体渲染(即false)取值：true= NVENO渲染（HTML素材）；false=媒体渲染（图片素材或者原生素材）
    private int width;//广告位宽度，以像素为单位，与屏幕密度无关（返回图片尺寸可能会与请求有一定差异）
    private int height;//广告位高度，以像素为单位，与屏幕密度无关（返回图片尺寸可能会与请求有一定差异）
    private int impression_num;//当前广告位一次请求返回的创意个数，应与双方约定值一致。现在只支持１。
    private int open_type;//广告位允许的落地页打开类型。取值：0= ALL(内开、外开都支持)；1= INNER (内开,由应用webview打开)；2= OUTER(外开，由系统浏览器打开)；
    private List<Integer> interaction_type;//广告位允许的交互类型。取值：1= NO_INTERACTION(不交互)；2=BROWSE(跳链接)；3= DOWNLOAD (下载)；4= DIALING (电话)；5= MESSAGE (短信)；6= MAIL (邮件)；
    private int support_deeplink;//是否支持deeplink。取值：0=不支持；1=支持
    private List<Integer> material_type;//支持的素材类型。取值：0=图片素材；1=视频素材；2=音频素材；3= GIF图片素材；4=多图素材；5=HTML素材(JS代码素材)；2.1.1新增，之前版本统一默认为[0]
    private List<Integer> assets;//对当前广告位所需物料有明确要求时，可以通过该字段指定物料被必备字段。取值：1=推广标题；2=推广摘要；3=广告icon图标；4=广告图片；(该字段暂未启用)
    private int impression_time;//计时曝光类广告位曝光时长单位：秒。
    private String key_words;//广告品类关键字
    private int adspace_position;//广告位位置。取值：0=未知；1=首屏；2=非首屏
}
