package com.mk.adx.entity.json.request.Index;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/8/30 15:21
 */
@Data
public class IndexApp {
    private String appId;//广告位 id(联系平台商务人员获取)
    private String appName;//app 名称
    private String packageName;//app 包名，(iOS 填写 bundleID,安卓填写包名）
    private String version;//app 版本
    private String storeUrl;//应用商店下载地址
}
