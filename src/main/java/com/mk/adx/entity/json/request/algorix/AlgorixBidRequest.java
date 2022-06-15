package com.mk.adx.entity.json.request.algorix;


import lombok.Data;

import java.util.List;

/**
 * algorix
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class AlgorixBidRequest {
    private String id	;//唯一请求 ID
    private List<AlgorixImp> imp;
    private AlgorixApp app;
    private AlgorixDevice device;
    private AlgorixUser user;
    private Integer tmax;//超时时间，单位毫秒


}
