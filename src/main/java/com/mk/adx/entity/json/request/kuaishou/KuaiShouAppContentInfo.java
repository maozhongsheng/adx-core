package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 媒体内容信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouAppContentInfo {
    private String prevTitle;//内容标签（广告前文章标题），多个用分号分割
    private String postTitle;//内容标签（广告后文章标题），多个用分号分割
    private String historyTitle;//内容标签（用户最近浏览文章标 题），多个用分号分割
    private String channel;//频道标签，直接传文字

}
