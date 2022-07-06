package com.mk.adx.entity.json.request.yiliang;

import lombok.Data;

@Data
public class YiLiangMedia {
    private String mediaId;//媒体ID，在vivo联盟创建媒体时分配的MediaID
    private String appPackage;//应用包名，要与注册媒体时填写保持一致
    private int appVersion;//整型应用版本号

}
