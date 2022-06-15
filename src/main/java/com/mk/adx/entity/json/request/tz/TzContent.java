package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import java.util.List;

/**
 * 视频的内容相关信息。只有视频贴片类型的广告位才会有这个字段
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class TzContent {
    private String id;//唯一标识内容的 ID
    private Integer episode;//集数量(通常适用于视频内容)
    private String title;//标题名称
    private String series;//内容系列
    private String season;//内容,通常视频内容
    private TzProducer producer;//提供者对象
    private String url;//内容的url
    private List<String> cat;//内容类别描述内容生产商
    private Integer videoquality;//视频质量分类
    private Integer context;//内容类型
    private String contentrating;//内容评级
    private String userrating;//内容评级的内容
    private Integer qagmediarating;//媒体每QAG评级准则
    private String keywords;//标签关键字
    private Integer livestream;//ot live, 1 = content is live
    private Integer len;//内容以秒为单位的长度，适合视频或音频
    private String language;//使用语言标准 ISO-639-1-alpha-2
    private String embedable;//内容是否可嵌入的，0：否，1：是

}
