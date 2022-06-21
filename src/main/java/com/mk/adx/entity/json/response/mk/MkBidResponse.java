package com.mk.adx.entity.json.response.mk;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

/**
 * 天卓总返回数据实体
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/16 16:30
 */
@Data
public class MkBidResponse {
    private String id;//竞价请求的 ID
    private String bidid;//广告主生成的id
    private List<MkBid> seatbid;//竞价集合对象,若是竞价至少有一个
    private Long process_time_ms;//实验标记，内部使用,上游消耗时间

    private static final long serialVersionUID = 1L;
}