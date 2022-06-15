package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoContext {
    private String title;// 广告位所在内容页或信息流上下文的标题
    private List<String> cat;//广告位所在内容页或信息流上下文的标签、分类
    private List<String> keywords;// 广告位所在内容页或信息流上下文的关键词
    private int context;// 内容类型0：Unknown1：Application2：Game
}
