package com.mk.adx.entity.json.request.jiaming;

import lombok.Data;

import java.util.List;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class JmApps {
    private String id;//交互App ID
    private String name;//媒体app名称
    private String bundle;//应用程序包或包名称
    private String ver;//app应用版本
    private String domain;//交互app的domain
    private String storeurl;//应用商店安装应用程序URL
    private List<String> cat;//内容类别
    private List<String> sectioncat;//描述当前网站片段的内容类别
    private List<String> pagecat;//描述当前网站页的内容类别
    private int privacypolicy;//==app是否有隐私策略,0：否，1：是
    private int paid;//0：app是免费的，1：app是付费的
    private String keywords;//逗号分隔关键字列表

}
