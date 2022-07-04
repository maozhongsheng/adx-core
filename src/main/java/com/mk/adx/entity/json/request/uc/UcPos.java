package com.mk.adx.entity.json.request.uc;

import lombok.Data;

import java.util.List;

/**
 * UC总请求⼴告位信息数组（⽬前只
 * ⽀持⼀个，多个会有问
 * 题）
 * 特例（对于PP助⼿，⼀期
 * 同⼀请求内⼴告位数量限
 * 定为30）
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcPos {
    private String slot_type; //⼴告位类型 枚举值，包括:0 common id相关，必须有id1 query 可以没有id，必须有query字段对于common请求我们会根据⽤户的兴趣，⾏为和⻚⾯信息，定向投放⼴告
    private String slot_id; //⼴告位id
    private List<String> ad_style; //⽀持的⼴告样式集合数组，默认使⽤⼴告平台当9注：组 组，默认使⽤⼴告平台当前设置的样式集合代替
    private String req_cnt; //数量，数量不做限制
    private String wid; //⾃媒体ID
    private String aw; //⼴告位宽度（单位：像素）
    private String ah; //⼴告位⾼度（单位：像素）
    private String query; //
    private String ad_pos_ext_info; //该⼴告位的⼀些额外信息例如：pp可能会请求指定⼀级分类的app数据,具体的⽤法由双发的开发协商确定
}
