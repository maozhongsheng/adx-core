package com.mk.adx.entity.json.request.yunjuhe;

import lombok.Data;

/**
 * Network 对象
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/14 13:53
 */
@Data
public class YunJuHeNetwork {
    private String ip;//客户端 ip 地址，若是服务器请求，必填
    private String ipv6;//ipv6 版本，与 ip 一起必须存在一个有效值
    private String mac;//mac 地址
    private int net;//网络类型0: 未知, 1:WIFI, 2:2G, 3:3G, 4:4G,5:5G,6:LTE,7:ETH
    private int carrier;//运营商0: 未知运营商1: 中国移动;2: 中国电信;3: 中国联通;
    private String ua;//客户端 UA
    private String code;//手机运营商代号： 46000、46002、46007（中国移动）46001、46006（中国联通）46003、46005（中国电信）没有就填""字符串
    private String reffer;//终端用户 HTTP 请求头中的 referer
    private String country;//国家，使用 ISO-3166-1 Alpha-3
    private String mcc;//移动国家码,如:460
    private String mnc;//移动网络码,如:00
    private int coordinateType;//GPS 坐标类型 1:全球卫星定位系统坐标系 2:国家测绘局坐标系 3:百度坐标系

}
