package com.mk.adx.entity.json.request.yiqi;


import lombok.Data;

/**
 * 益起
 *
 * @author gj
 * @version 1.0
 * @date 2021/11/03 09:51
 */
@Data
public class YQBidRequest {
    private String ver;//当前文档版本名称
    private YQApp app;//App对象
    private YQDevice device;//Device对象
    private YQNetwork network;//Network对象
    private YQAdSpace adspace;//Ad Space对象列表
    private String timestamp;//请求时间戳(毫秒)
    private Integer maxTime;//响应时间(毫秒)
}
