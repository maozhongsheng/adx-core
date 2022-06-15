package com.mk.adx.entity.json.request.doumeng;

import lombok.Data;

@Data
public class DouMengApp {
    private String id;//appid，联系商务人员获取
    private String name;//App 名称
    private String bundle;//App 包名
    private String versionName;//App 版本号，例如：1.1.1
    private int versionCode;//版本号，如26
}
