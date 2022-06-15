package com.mk.adx.entity.json.request.yiqi;


import lombok.Data;

/**
 * 移动app对象
 *
 * @author gj
 * @version 1.0
 * @date 2021/11/03 09:51
 */
@Data
public class YQApp {
    private String appId;//应用 ID
    private String appName;//APP 名称。如，有道词典
    private String pkg;//APP 包名
    private String appVer;//APP 版 本 号
    private int appVerCode;//应用程序版本
}
