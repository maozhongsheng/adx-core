package com.mk.adx.entity.json.request.yiliang;

import lombok.Data;

import java.util.List;

@Data
public class YiLiangUser {
    private List<String> appList;//用户安装列表，例：["com.netease.dhxy.vivo", "com.tencent.mobileqq"]
}
