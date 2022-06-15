package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 媒体App信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class KuaiShouAppInfo {
    private String appId;//媒体 app id 唯一标识，由快手平台分 配
    private String name;//媒体名称
    private String packageName;//媒体包名
    private String version;//媒体自身版本号
    private KuaiShouAppContentInfo content;//媒体内容信息

}
