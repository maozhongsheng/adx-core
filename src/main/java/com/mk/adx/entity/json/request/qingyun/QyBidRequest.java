package com.mk.adx.entity.json.request.qingyun;


import lombok.Data;

/**
 * 青云-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class QyBidRequest {
    private String request_id;//请求 ID由接入方生成，长度 12～36 位，需唯一
    private Integer protocol_type;//协议类型：0-HTTP，1-HTTPS HTTPS 媒体必填，支持返回 HTTPS 物料
    private String api_version;//API 版本号，例如：3.0
    private QyPos pos;//广告位信息
    private QyApp app;//应用信息
    private QyDevice device;//设备信息
    private QyNetwork network;//网络信息
    private Long timestamp;//时间戳：单位毫秒
}
