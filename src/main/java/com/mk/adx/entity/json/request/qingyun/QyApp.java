package com.mk.adx.entity.json.request.qingyun;


import lombok.Data;


/**
 * 青云-APP
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class QyApp {
    private String app_id;//应用 ID，青云平台生成
    private String app_bundle;//应用包名
    private String app_version;//应用版本，建议填写
}
