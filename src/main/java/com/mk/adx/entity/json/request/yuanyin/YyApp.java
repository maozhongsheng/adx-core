package com.mk.adx.entity.json.request.yuanyin;

import lombok.Data;

@Data
public class YyApp {
    private String appName; //应⽤名称
    private String packageName; //应⽤包名
    private String appVersion; //应⽤版本号
    private int dpSupport; //是否⽀持deeplink调起。 1：⽀持；0：不⽀持，默认为0不⽀持
    private int videoSupport; //是否⽀持视频⼴告. 1：⽀持, 0：不⽀持，默认为0不⽀持
    private int contentSupport; //是否⽀持内容. 1：⽀持, 0：不⽀持，默认为0不⽀持
    private String storeUrl; //应⽤市场下载链接，App应⽤市场下载链接，安卓为应⽤宝，豌⾖荚等⼤师⽤市场，iOS为AppStore
}
