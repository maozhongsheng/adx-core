package com.mk.adx.entity.json.request.zhimeng;

import lombok.Data;

import java.util.List;

/**
 * 知盟-竞价请求对象-总请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/12/08 13:49
 */
@Data
public class ZhimengBidRequest {
    private static final long serialVersionUID = 1L;

    private String protocol_version;//协议版本号
    private String channel;//渠道ID（可在平台媒体管理中查看渠道ID）
    private String request_id;//请求ID
    private ZhimengUserInfo user_info;//用户信息
    private List<ZhimengImpInfo> imp_info;//位置信息
    private ZhimengDeviceInfo device_info;//设备信息
    private ZhimengContextInfo context_info;//上下文信息
    private String ciphertext;//密文：由channel,domain,timestamp 组成


}
