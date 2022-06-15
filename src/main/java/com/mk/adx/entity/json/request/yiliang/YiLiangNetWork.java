package com.mk.adx.entity.json.request.yiliang;

import lombok.Data;

@Data
public class YiLiangNetWork {
    private int connectType;//联网方式。见附录-网络连接类型。 不填将无广告返回；填未知，会严重影响流量变现效果。
    private int carrier;//运营商。见附录-运营商类型。
    private String mccmnc;//运营商识别码
}
