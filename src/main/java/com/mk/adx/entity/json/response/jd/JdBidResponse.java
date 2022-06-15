package com.mk.adx.entity.json.response.jd;

import lombok.Data;

import java.util.List;

/**
 * jd总返回数据实体
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/2 16:30
 */
@Data
public class JdBidResponse {
    private String id;//竞价请求的 ID
    private List<JdSeatbid> seatbid;//响应席位(竞价集合对象,若是竞价至少有一个)
    private String bidid;//京东生成的 bid id 供调试使用
}
