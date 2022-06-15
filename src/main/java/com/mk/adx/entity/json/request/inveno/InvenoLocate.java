package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoLocate {
    private String country;//(IOS必传)国家代码；使用 ISO-3166-1-alpha-2
    private String province;//用户所在省市，服务端对接的用户，如果无法提供经纬度、IP，可以填充此字段作为补充。如，陕西省
    private String city;//用户所在城市，服务端对接的用户，如果无法提供经纬度、IP，可以填充此字段作为补充。如，商洛市
    private String district;//用户所在地区，服务端对接的用户，如果无法提供经纬度、IP，可以填充此字段作为补充。如，商南县
}
