package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoPageInfo {
    private String url;//资讯文章的URL。（如果该页面使用的是百度内容联盟的资讯，可以传该值）
    private String title;//请求页面的标题，UTF-8 编码。
    private String source_url;//资讯文章的URL。（不局限于百度内容联盟）
    private String content_id;//内容ID，请求页面的内容 ID。（如果该页面使用的是百度内容联盟的资讯，可以传该值）
    private String content_category;//内容分类，请求页面的内容分类，UTF-8 编码。如，[“军事”,”娱乐”]
    private String content_label;//内容标签，请求页面的内容标签，类似于文章关键词标签等，UTF-8 编码。如，[“飞机”,”火车”]
    private String author_id;//作者ID。（如果该页面使用的是百度内容联盟的资讯，可以传该值）
}
