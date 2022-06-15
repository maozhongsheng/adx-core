package com.mk.adx.entity.json.request.inveno;

import lombok.Data;

@Data
public class InvenoDeviceId {
    private int device_id_type;//设备ID类型。取值：1=IMEI；2=IDFA；3=Android ID；4=MAC；5= IDFV；6=AAID(Android Ad ID)；7=SERIALID；8=IMSI；9=OAID；目前Android支持类型IMEI、Android ID、MAC、SERIALID、IMSI，对于Android IMEI、MAC、IMSI必传。安卓IMEI取不到的时候OAID必须传目前iOS支持类型IDFA、IDFV，推荐传IFDA。
    private String device_id;//设备ID值，原始值或小写的MD5值，MD5前请先转成小写字符串。
    private int hash_type;//设备ID加密类型。取值：0=原始值；1=MD5(推荐使用原始值)
}
