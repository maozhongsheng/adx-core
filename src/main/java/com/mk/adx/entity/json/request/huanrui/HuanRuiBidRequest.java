package com.mk.adx.entity.json.request.huanrui;

import lombok.Data;

import java.util.List;

/**
 * @author mzs
 * @version 1.0
 * @date 2021/11/26 13:49
 */
@Data
public class HuanRuiBidRequest {

    private String ver;//接口版本(文档开头定义)
    private String tag;//请求唯一标识
    private HuanRuiApp app;//必填，当前请求发⾃某个应⽤时，必须提供完整的App对象
    private HuanRuiDevice device;//必填，发起当前请求的设备信息，Device对象中包含了⼤量必填信息，需要注意
    private HuanRuiGps gps;//gps
    private HuanRuiNet net;//网络

    private static final long serialVersionUID = 1L;

}
