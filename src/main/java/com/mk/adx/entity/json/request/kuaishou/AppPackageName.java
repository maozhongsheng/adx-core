package com.mk.adx.entity.json.request.kuaishou;

import lombok.Data;

/**
 * 已安装 app 列表
 *
 * @author jny
 * @version 1.0
 * @date 2022/5/5 14:21
 */
@Data
public class AppPackageName {
    private String appName;//app 名称
    private String pkgName;//app 包名
    private String appVersion;//app 版本号
    private int system_app;//是否系统预装 app
    private long firstInstallTime;//首次安装的时间戳， themilliseconds since January 1,1970, 00:00:00 GMT.(java.util.Date)
    private long lastUpdateTime;//最后升级更新的时间戳the milliseconds since January1, 1970, 00:00:00 GMT.(java.util.Date)

}
