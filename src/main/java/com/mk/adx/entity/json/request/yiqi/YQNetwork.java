package com.mk.adx.entity.json.request.yiqi;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/11/03 09:51
 */
@Data
public class YQNetwork {
    private String ip;//客户端真实 IP，请勿使用服务器 IP 或内网 IP
    private String network;//网络类型，必须区分 WIFI 和移动网络,如果不能区分 234G，统一填写4G。网络连接名称，2g/3g/4g/5g/wifi/未知
    private int carrier;//运营商编码，使用真实值，如果不能取到，统一填写未知。取值：0=未知；1=中国移动；；2=中国联通 4=中国电信 ；
    private String ua;//User-Agent
}
