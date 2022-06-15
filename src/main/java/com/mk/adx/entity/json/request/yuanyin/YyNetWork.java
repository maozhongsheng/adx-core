package com.mk.adx.entity.json.request.yuanyin;

import lombok.Data;

@Data
public class YyNetWork {
    private String clientIp;//客户端IPv4地址，请传客户端真是ip
    private int connectionType;//⽹络类型。1:WI-FI，2:2G，3:3G， 4:4G,5:5G, 0:未知
    private int operatorType;//运营商类型。1:移动，2:联通，3:电信, 0:未知
    private String cellularId;//基站ID
    private float lon;//经度，⽆法获取填0
    private float lat;//纬度，⽆法获取填0
    private String province;//省份
    private String city;//城市
    private String address;//详细地址
}
