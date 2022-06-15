package com.mk.adx.entity.json.response.yoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoPlay {
    private int percent;//播放进度百分比，取值范围0-100
    private List<String> urls;//统计地址
}
