package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class TzApp {
    @NotBlank(message = "Appip不能为空")
    private String id;//交互App ID
    private String name;//媒体app名称
    @NotBlank(message = "应用程序包或包名称")
    private String bundle;//应用程序包或包名称
    private String domain;//交互app的domain
    private String storeurl;//应用商店安装应用程序URL
    private List<String> cat;//内容类别
    private List<String> sectioncat;//描述当前网站片段的内容类别
    private List<String> pagecat;//描述当前网站页的内容类别
    private int privacypolicy;//==app是否有隐私策略,0：否，1：是
    private int paid;//0：app是免费的，1：app是付费的
    private TzPublisher publisher;//发布者对象
    private TzContent content;//视频的内容相关信息。只有视频贴片类型的广告位才会有这个字段
    private String keywords;//逗号分隔关键字列表
    private String ver;//app应用版本
    private long applist;//app应用版本

}
