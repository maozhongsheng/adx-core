package com.mk.adx.entity.json.response.yoloho;

import lombok.Data;

@Data
public class YolohoApp {
    private String appName;//app名称
    private String packageName;//包名
    private String appVersion;//app版本
    private String iconImage;//图标url
    private String appDeveloper;//开发者
    private String appPrivacyAgreement;//隐私协议url
    private String appAuthority;//权限url

}
