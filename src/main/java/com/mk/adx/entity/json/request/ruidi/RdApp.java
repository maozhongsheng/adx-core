package com.mk.adx.entity.json.request.ruidi;

import lombok.Data;

@Data
public class RdApp {
    private String app_id;//应用 ID
    private String app_package;//应用包名，需要跟提交的应用一致
    private RdVersion app_version;//应用版本

}
