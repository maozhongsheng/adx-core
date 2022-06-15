package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunImage {
    private int required;//是否必须
    private int sn;//序号，*如果广告需要多张图片，那么响应中的图片的序号要与请求中的一致
    private int w;//
    private int h;//
}
