package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunBanner {
    private int type;//Banner 类型 0-横幅 1-全屏 2-插
    private int w;//w
    private int h;//h
    private String mimes;//允许的素材格式，多个以逗号分隔：img-图片，html-html 自渲染， 如：img,html
}
