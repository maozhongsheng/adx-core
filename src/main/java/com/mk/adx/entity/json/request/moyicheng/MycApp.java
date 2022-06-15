package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

import java.util.List;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class MycApp {
    private String id;//交互App ID
    private String bundle;//应用程序包或包名称
    private String ver;//app应用版本
    private String name;//媒体app名称
    private String storeurl;//应用商店安装应用程序URL
    private String domain;//交互app的domain
    private List<String> cat;//内容类别

}
