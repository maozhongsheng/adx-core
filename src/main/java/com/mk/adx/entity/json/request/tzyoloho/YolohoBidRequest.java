package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoBidRequest {
    private String reqid;//本次请求的唯一标识，由媒体侧生成，请确保全局唯一
    private String ver;//协议版本，如 1.0.0
    private YolohoApp App;//应用信息
    private List<YolohoImps> imps;//广告位描述数组，目前只支持一个
    private YolohoDvice device;//设备信息
    private YolohoUser user;//用户信息
    private int test;//当前请求是否是test0-正式请求（默认）     1-测试请求
}
