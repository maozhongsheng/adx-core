package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdAd {
    private String adslot_id;//请求 id，媒体侧生成，需确保全局唯一，最大长度为 36 位
    private RdAdSize adslot_size;//广告位尺寸信息

}
