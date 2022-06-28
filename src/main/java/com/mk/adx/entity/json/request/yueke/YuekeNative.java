package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

import java.util.List;

@Data
public class YuekeNative {
    private String version;//OPEN-RTB 原生广告请求协议版本编号, 默认：1.2
    private List<YuekeAssets> assets;//OPEN-RTB 原生广告请求协议版本编号, 默认：1.2
}
