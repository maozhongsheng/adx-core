package com.mk.adx.entity.json.request.tengxun;

import lombok.Data;

/**
 * 腾讯-优量汇-用户设备网络相关信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/26 14:21
 */
@Data
public class TengXunNetwork {
    private int connect_type;//联网方式。不填写将无广告返回；填未知，会严重影响流量变现效果
    private int carrier;//运营商。不填写将无广告返回；填未知，会影响流量变现效果
    private String ip;//1.请从服务端获取IP，不要从客户端获取IP2.优先上报IPV4地址，如果没有则上报IPV6地址3.请上报公网IP，不要上报内网IP（例如192.168.0.100等）4.如果X-Forward-For中包含多个公网IP，优先上报第一个公网IP
    private String ua;//客户端WebView的UserAgent信息
}
