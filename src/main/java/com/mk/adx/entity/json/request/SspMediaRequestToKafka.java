package com.mk.adx.entity.json.request;

import lombok.Data;

import java.util.Date;

/**
 *  kafka里存入的请求数据，对应clickhouse字段
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/6 9:55
 */
@Data
public class SspMediaRequestToKafka {

    private String request_id;//请求id，数据唯一标识，不重复
    private Date date;//日期
    private String date_hour;//小时
    private String adx_id;//adx的id
    private String publish_id;//代理商id
    private String media_id;//媒体id
    private String pos_id;//媒体广告位id
    private String dsp_id;//dspid
    private String dsp_media_id;//dsp媒体id
    private String dsp_pos_id;//dsp广告位id
    private String ad_id;//广告id
    private String oem_id;//oemid（厂商，oppo、华为等）
    private String token;//token
    private String media_type;//媒体类型
    private String settle_type;//结算类型
    private String platform;//平台类型
    private String system_type;//系统类型
    private String slot_type;//广告位类型
    private String slot_tag;//广告位分类
    private String media_tag;//媒体分类
    private String version;//系统版本号
    private String plant_id;//分发计划id
    private Integer bid_floor;//媒体底价，单位分
    private Integer req; //请求数

    private static final long serialVersionUID = 1L;
}
