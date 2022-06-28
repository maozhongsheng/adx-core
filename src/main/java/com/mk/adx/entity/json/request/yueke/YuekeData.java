package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

@Data
public class YuekeData {
    private int type;//文字数据类型 ( 2:描述 )，默认：2
    private int len;//文本长度要求，默认: 25
}
