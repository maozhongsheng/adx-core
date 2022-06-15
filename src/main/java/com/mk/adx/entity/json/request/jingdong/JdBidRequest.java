package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * 京东-竞价请求对象-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:49
 */
@Data
public class JdBidRequest {
    private String id;//请求id，唯一标识一次广告请求；由媒体侧生成，请确保全局唯一
    private String version;//协议版本，此处填写：3.7
    private List<JdImp> imp;//展现广告资源位描述
    private JdSite site;//Wap、PC 时必填；其他可选 【注意：当携带 site 结构时，必须填充 user 结构中的id 字段】
    private JdApp app;//移动app对象,app/site,必须2选1
    private JdDevice device;//设备信息，移动端（填充app字段时）必填;其他可选
    private JdUser user;//用户对象
    private List<JdContent> content;//内容页或者信息流上下文信息


}
