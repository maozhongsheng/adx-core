package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

import java.util.List;

@Data
public class YuekeImg {
    private int w;//图片宽度，单位: 像素
    private int h;//图片高度，单位: 像素
    private int pos;//流量售卖位在设备屏幕上显示的位置编号，默认: 0
    private int type;//1: 图标，2:品牌，3:主图 默认：3
    private List<String> mimes;//默认填写: image/gif，image/jpeg，image/jpg，image/png
}
