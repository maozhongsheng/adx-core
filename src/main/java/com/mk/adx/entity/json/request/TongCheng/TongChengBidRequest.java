package com.mk.adx.entity.json.request.TongCheng;

import lombok.Data;

/**
 * 同城总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/27 17:37
 */
@Data
public class TongChengBidRequest {
    private static final long serialVersionUID = 1L;

    private TongChengApp app;//app包信息
    private TongChengDevice device;//设备信息
    private TongChengImp imp;//广告合约信息
    private String positionId;//广告位ID
    private long timestamp;//当前时间戳（13位毫秒时间）
    private String uuid;//请求唯一ID（32或36位UUID）
    private TongChengUser user;//用户信息
    private TongChengJaid jaid;//JAID

}
