package com.mk.adx.entity.json.request.algorix;

import lombok.Data;

@Data
public class AlgorixExt {
    private Integer supportdeeplink; //是否支持 deeplink，调用第三方app。1支持 0不支持
    private String storeurl; //app应用商店链接
    private String storename; //应用商店名称
    private String storever; //应用商店版本号
}
