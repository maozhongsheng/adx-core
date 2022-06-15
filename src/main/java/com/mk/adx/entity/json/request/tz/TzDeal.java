package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import java.util.List;

/**
 * 协议对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 15:04
 */
@Data
public class TzDeal {
    private String id;//deal id
    private List<String> wseat;//允许竞标的白名单
    private List<String> wadomain;//允许投标这笔交易domains
    private float bidfloor;//deal 价格,单位是分/千次曝光,即CPM
    private String bidfloorcur;//货币指定编码，ISO-4217 alpha codes
    private Integer at;//==拍卖类型,1 = First Price, 2 = Second Price Plus, 3 = the value passed in bidfloor is the agreed upon deal price，其他竞价规则可自己定义
//    private List<String> allowstyle;//deal 曝光位接受创意展现形式的ID数组对象

}
