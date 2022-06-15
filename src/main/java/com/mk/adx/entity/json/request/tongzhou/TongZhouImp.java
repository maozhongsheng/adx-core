package com.mk.adx.entity.json.request.tongzhou;

import lombok.Data;

@Data
public class TongZhouImp {
    private String imp_id;//Imp 对象 id，由媒体生成
    private int pos_type;//广告位类型：1 – 横幅2 – 开屏3 – 插屏4 – 信息流5 – 激励视频
    private String pos_id;//广告位 id，联系商务人员获
    private int pos_width;//广告位宽，单位：像素
    private int pos_height;//广告位高，单位：像素
    private int deeplink;//是否支持 deeplink：0 - 不支持1 - 支持     默认不支持
}
