package com.mk.adx.entity.json.request.yidianzixun;

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
public class YdzxBidRequest {
    private String id;//检索ID，唯一标示请求
    private List<YdzxImp> imp;//曝光对象，每个请求最少包含一个Imp
    private YdzxDevice device;//设备信息对象
    private YdzxSite site;//网站对象
    private YdzxApp app;//移动app对象
    private YdzxUser user;//用户对象
    private Integer tmax;//超时时长，单位毫秒，默认300ms
    private Boolean test;//测试字段，默认false：false: 生产模式 true: 测试模式
    private YdzxExt ext;//扩展字段，用户特殊字段要求

    private static final long serialVersionUID = 1L;
}
