package com.mk.adx.entity.json.request.algorix;

import lombok.Data;

@Data
public class AlgorixApp {
    private String id;//系统分配的 app_id
    private String name;//app 名称
    private String ver;//app 版本号，格式为 x.x.x
    private String bundle;//app 包名，iOS 格式为一串数字(e.g., "com.foo.mygame", "1207472156")
    private AlgorixExt ext;//系统分配的 app_id
}
