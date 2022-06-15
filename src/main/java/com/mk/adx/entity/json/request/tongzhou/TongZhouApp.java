package com.mk.adx.entity.json.request.tongzhou;

import lombok.Data;

@Data
public class TongZhouApp {
    private String app_id;//appid，联系商务人员获取
    private String app_name;//App 名称
    private String app_package;//App 包名
    private String app_ver;//App 版本号，例如：1.1.1
}
