package com.mk.adx.entity.json.request.jialiang;

import lombok.Data;

/**
 * 佳量总请求
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class JiaLiangBidRequest {
    private String request_id; //本次请求ID，便于流程追踪，建议⽣成⽅式：随机字符串的md5加密⼩写
    private String version;  //当前API版本号，请填写本⽂档中的最新接⼝版本号，如：1.0.2
    private String app_id;  //媒体应⽤ID，由佳量⼴告提供
    private String pid;  //媒体⼴告位ID，由佳量⼴告提供
    private JiaLiangImp imp;//⼴告位描述信息
    private JiaLiangApp app;//应⽤信息描述
    private JiaLiangDevice device;//设备信息描述
}
