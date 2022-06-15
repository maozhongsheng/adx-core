package com.mk.adx.entity.json.request.jiaming;

import lombok.Data;
import java.util.List;

/**
 * 嘉明-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class JmBidRequest {
    private String id;//竞价请求id
    private List<JmImp> imp;//描述广告位对象-曝光对象
    private JmSite site;//网站对象
    private JmApps app;//移动app对象
    private JmDevice device;//设备对象
    private JmUser user;//用户对象
    private String adx_id;//adx分配id
    private String adx_name;//adx分配名称
    private String meida_version;//媒体或ADX协议版本号,爱奇艺


}
