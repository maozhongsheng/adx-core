package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/21 11:33
 */
@Data
public class JdContent {
    private String title;//广告位所在内容页或者信息流上下文的标题
    private List<String> cat;//广告位所在内容页或者信息流上下文的标签、类别
    private String keywords;//广告位所在内容页或者信息流上下文的关键词
    private int context;//内容类型，取值 1：Video， 2：Game， 3：Music， 4：Application，5：Text，6：Other， 7：Unknown
}
