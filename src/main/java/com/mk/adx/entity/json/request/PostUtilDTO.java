package com.mk.adx.entity.json.request;

import lombok.Data;

/**
 * 工具类实体
 *
 * @author yjn
 * @version 1.0
 * @date 2022/1/5 13:53
 */
@Data
public class PostUtilDTO {
    private String url;//请求地址
    private String content;//参数内容
    private String ua;//ua
    private String headerTongcheng;//同城header value值
    private String headerYunJuHe;//云聚合header value值
    private String headerWoKe;//沃氪header value值
    private String headerSzyd;//数字悦动header value值
    private String szyd_ip;//数字悦动ip
    private String Yueke;//阅客


}
