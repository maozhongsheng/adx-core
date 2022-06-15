package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

import java.util.List;

@Data
public class BaiXunNative {
    private BaiXunTitle title;//信息流标题规范
    private BaiXunDesc desc;//信息流描述规范
    private List<BaiXunImage> images;//信息流图片规范

}
