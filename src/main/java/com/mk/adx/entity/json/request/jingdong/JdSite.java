package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 网站对象   一个request 中，app 与site 互斥，仅可填充其一。
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:52
 */
@Data
public class JdSite {
    private String domain;//站点域名
    private String page;//广告展现的具体页面 url
    private String ref;//进入该页面时前一页面的 ur（lreferrer）
    private String keywords;//逗号分隔的站点或页面关键字标签列表
    private List<String> pagecat;//当前页面的分类信息标签列表，参加open-RTBv2.3 list 5.1
    private String search;//通过搜索引擎进入页面时的搜索词

}
