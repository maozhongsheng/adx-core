package com.mk.adx.entity.json.response.tz;

import lombok.Data;
import java.util.List;

/**
 * 天卓总返回数据实体
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:30
 */
@Data
public class TzBidResponse {
    private String id;//竞价请求的 ID
    private String bidid;//广告主生成的id
    private List<TzSeat> seatbid;//竞价集合对象,若是竞价至少有一个
    private String cur;//币种,ISO-4217 alpha codes 标准
    private Integer nbr;//不竞价的原因
    private String debug_info;//debug 信息
    private Long process_time_ms;//实验标记，内部使用,上游消耗时间
    private TzExt ext;//扩展站位对象

    private static final long serialVersionUID = 1L;
}