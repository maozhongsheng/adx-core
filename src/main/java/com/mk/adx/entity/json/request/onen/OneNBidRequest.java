package com.mk.adx.entity.json.request.onen;

import lombok.Data;

import java.util.List;

/**
 * 一点资讯-竞价请求对象-总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:49
 */
@Data
public class OneNBidRequest {
    private String requestId;//检索ID，唯一标示请求
    private String apiVersion;//此 API 的版本
    private String sourceType;
    private String userAgent;
    private String ip;
    private OneNUser userInfoParam;
    private OneNApp appInfoParam;
    private OneNDevice deviceInfoParam;
    private OneNAdSlot adSlotInfoParam;
    private Boolean isSupportDp;

    private static final long serialVersionUID = 1L;
}
