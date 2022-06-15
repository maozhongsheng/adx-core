package com.mk.adx.entity.json.request.renze;

import lombok.Data;

/**
 * 网络信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class RzNetwork {
    private String ip;//客户端 ip 地址，若是服务器请求，必填
    private String mac;//mac 地址
    private int net;//网络类型0: 未知, 1:WIFI, 2:2G, 3:3G, 4:4G,5:5G,6:LTE,7:ETH
    private int carrier;//运营商0: 未知运营商 1: 中国移动; 2: 中国电信; 3: 中国联通
    private String ua;//客户端 UA
    private String reffer;//终端用户 HTTP 请求头中的 referer
    private String country;//国家，使用 ISO-3166-1 Alpha-3
    private String mcc;//移动国家码,如:460
    private String mnc;//移动网络码,如:00
    private int coordinateType;//GPS 坐标类型1:全球卫星定位系统坐标系2:国家测绘局坐标系3:百度坐标系
}
