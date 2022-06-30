package com.mk.adx.entity.json.request.xiaomi;

import lombok.Data;

@Data
public class XiaoMiUser {
    private String imei;
    private String oaid;
    private String ua;
    private String connectionType;
    private String ip;
    private String mac;
    private String androidId;
}
