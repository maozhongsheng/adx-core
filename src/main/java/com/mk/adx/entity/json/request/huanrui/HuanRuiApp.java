package com.mk.adx.entity.json.request.huanrui;

import lombok.Data;

/**
 * 移动app对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/26 13:52
 */
@Data
public class HuanRuiApp {
    private String appid;
    private String slotid;
    private String name;
    private String pkgname;
    private String ver;
    private Integer orientation;

}
