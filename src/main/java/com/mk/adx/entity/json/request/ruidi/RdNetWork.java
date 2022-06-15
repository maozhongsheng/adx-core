package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdNetWork {
    private String ipv4;//公网 IPv4 地址
    private String ipv6;//公网 ipv6 地址
    private Integer connection_type;//网络类型0 无法探测网络状态;蜂窝数据接入;2：2G 网络;3： 3G 网络;4： 4G 网;5： 5G 网络;100：Wi-Fi 网络 ：以太网接入;999：未知新类型
    private Integer operator_type;//运营商类型0：未知运营商;1: 中国移动;2: 中国电信;3: 中国联通;99: 其他运营商;
    private String cellular_id;//基站 Id
    private String mcc;//移动国家码
    private String mnc;//移动网络码



}
