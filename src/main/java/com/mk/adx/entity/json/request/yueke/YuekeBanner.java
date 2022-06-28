package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

import java.util.List;

@Data
public class YuekeBanner {
    private int w; //广告位图片所要求的宽度: 单位：像素 px
    private int h; //广告位图片所要求的高度: 单位：像素 px
    private int pos; //流量售卖位在设备屏幕上显示的位置编号，默认: 0
    private int type;//图片类型编号，主图默认："3"
    private List<String> mimes;//默认填写: image/gif，image/jpeg，image/jpg，image/png

}
