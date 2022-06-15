package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoApp {
    private String app_id; //应用ID，INVENO分配，需要在SSP新建应用，才可获得。此ID的获取是后续联调活动开展的最基本条件。
    private String channel_id; //渠道名，由INVENO分配，请联系获取。产品只有一个推广渠道，该字段可以填集成厂家名。如，meizu
    private String app_name; //APP名称，与在SSP注册的应用名称保持一致，为英文名称或者拼音字母。如，xiaozhi
    private String package_name; //APP包名，来源于manifest的package，与在SSP注册的包名保持一致。如，com.inveno.newpiflow
    private String app_version; //APP版本号，来源于manifest的versionName，而不是versionCode。如，3.5.6
    private int report_pv_method; //用于描述客户端是否具备端上报广告展示事件的能力，上报能力是指需要上报到多个地址（比如原生广告PV上报地址和英威诺PV上报地址）。对于不具上报广告展示能力的客户端，投放的广告范围会有所限制。取值：0=客户端具有多地址上报能力，1=不具备该能力(即需要服务端辅助上报)
}
