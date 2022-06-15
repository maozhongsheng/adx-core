package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunApp {
    private String id;//App ID
    private String name;//app 名称
    private String bundle;//app 包名，iOS 格式为一串数字(e.g., "com.foo.mygame", "1207472156")
    private String ver;//app 版本号，格式为 x.x.x
    private String domain;//App 域名
    private String storeurl;//应用商店地址
    private String cat;//行业代码
    private String keywords;//App 关键字
    private int paid;//系统分配的 app_id
}
