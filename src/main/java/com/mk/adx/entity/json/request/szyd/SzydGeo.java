package com.mk.adx.entity.json.request.szyd;

import lombok.Data;

@Data
public class SzydGeo {
    private float lat;//纬度，取值范围 -90.0 到 90.0，负值表示南⽅
    private float lon;//经度，取值范围 -180.0 到 180.0，负值表示⻄⽅

}
