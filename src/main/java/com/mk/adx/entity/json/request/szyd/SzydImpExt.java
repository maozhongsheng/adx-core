package com.mk.adx.entity.json.request.szyd;

import lombok.Data;

@Data
public class SzydImpExt {
    private int deeplink;//是否⽀持唤醒⼴告主 App，1 代表⽀持，0 代表不⽀持
    private int ul;//iOS 流量使⽤，在 is_deeplink 为 1 时有效如需返回 Universal Link 时传 1，否则不传


}
