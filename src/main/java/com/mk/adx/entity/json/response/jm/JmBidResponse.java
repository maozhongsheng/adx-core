package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * jm总返回数据实体
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:30
 */
@Data
public class JmBidResponse {
    private String id;//竞价请求的 ID
    private List<JmSeat> seatbid;//竞价集合对象,若是竞价至少有一个
    private String debug_info;//debug信息
    private Integer process_time_ms;//耗时，单位：ms
    private String media_version;//媒体版本
    private String bidid;//bidid 供调试使用
}
