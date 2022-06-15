package com.mk.adx.entity.json.request.moyicheng;

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
public class MycBidRequest {
    private String id;//竞价请求id
    private String version;//协议版本号，当前版本“3.2“
    private MycApp app;//移动app对象
    private MycDevice device;//设备对象
    private MycUser user;//用户对象
    private List<MycImp> imp;//广告位曝光的imp对象数组
    private Integer at;//1-GFP  2-GSP 3-分成，以线下约定为准
    private Integer tmax;//超时时间，单位毫秒，默认300ms
    private Integer secure;//超标记返回链接是否必须https（0-否 1-是）默认0
    private List<String> bcat;//广告行业黑名单

}
