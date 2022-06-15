package com.mk.adx.entity.json.request.zhongmeng;

import lombok.Data;

/**
 * 当前网络连接信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/8 14:21
 */
@Data
public class ZhongMengNetworkInfo {
    private String ua;//系统webview的user-agent
    private String ip;//IP地址
    private int ipType;//ip地址类型 0:ipv4 1:ipv6 默认:0
    private int httpType;//是否是Https环境 0:不是 1:是 默认:0

}
