package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

import java.util.List;

/**
 * 网站对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class MycSite {
    private String id;//交互网站ID
    private String name;//媒体网站名称
    private List<String> domain;//交互网站的domain
    private List<String> sectioncat;//描述当前网站片段的内容类别
    private List<String> pagecat;//描述当前网站页的内容类别
    private String page;//当前页面URL
    private String ref;//Referrer URL
    private String search;//当前页面导航搜索字符串
    private Integer mobile;//==移动设备优化表示,0：否，1：是
    private Integer privacypolicy;//==是否网站的隐私政策,0：否，1：是
    private String keywords;//逗号分隔关键字列表

}
