package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunDesc {
    private int required;//是否必须
    private int len;//文字个数上限，0 表示未
}
