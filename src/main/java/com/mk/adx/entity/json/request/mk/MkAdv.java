package com.mk.adx.entity.json.request.mk;

import lombok.Data;

@Data
public class MkAdv {
    private int price; //价格
    private String dsp_id; //联盟id
    private String app_id; //联盟媒体id
    private String tag_id; //联盟广告位id
    private String size; //尺寸
    private String bundle; //包名
    private String slot_type; //广告位类型
    private String app_name; //媒体名称
    private String version; //app版本
    private String os; //系统
    private Integer test; //是否测试
    private Long startTime;//开始时间
    private Long endTime;//结束时间
    private String lowest_status;//并发开关
    private String timeout;//超时时间
}
