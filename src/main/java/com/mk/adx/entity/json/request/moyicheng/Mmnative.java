package com.mk.adx.entity.json.request.moyicheng;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Mmnative {
    @JSONField(name = "native")
    private MycNative NATIVE;//native类型的广告对象,	原生类型
}
