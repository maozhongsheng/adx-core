package com.mk.adx.entity.json.response.ydzx;

import lombok.Data;

import java.util.List;

/**
 * 一点资讯-总返回数据实体
 *
 * @author yjn
 * @version 1.0
 * @date 2021/8/3 16:30
 */
@Data
public class YdzxBidResponse {
    private String id;//对应BidRequest中的 ID
    private List<YdzxSeatBid> seatBid;//DSP竞价信息，如果出价会固定返回一个
    private String bidid;//DSP产生的响应ID，用于日志与追踪
    private String processtime;//DSP处理时间，单位（ms）
    private int code;//

}
