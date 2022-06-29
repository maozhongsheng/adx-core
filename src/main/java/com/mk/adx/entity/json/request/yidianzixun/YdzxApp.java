package com.mk.adx.entity.json.request.yidianzixun;

import lombok.Data;

/**
 * 移动app对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:52
 */
@Data
public class YdzxApp {
    private String name;//媒体app名称
    private String bundle;//应用程序包或包名称
    private String ver;//app应用版本
}
