package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

import java.util.List;

/**
 * 摩邑诚-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class SdBidRequest {
    private String id;//竞价请求id
    private String version;//协议版本号，当前版本“3.2“
    private List<SdImp> imp;//广告位曝光的imp对象数组
    private SdApp app;//移动app对象
    private SdDevice device;//设备对象
    private SdUser user;//用户对象
    private int tmax;//超时时间，单位毫秒，默认300ms
    private int test; //0表示实况（非测试）模式，1表示测试模式

}
