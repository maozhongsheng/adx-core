package com.mk.adx.entity.json.response.mk;

import lombok.Data;

/**
 * 应用信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/16 17:22
 */
@Data
public class MkApp {
    private String app_name;//下载类型：应用名称
    private String bundle;//广告应用的包名
    private String app_icon;//下载类型：应用图标
    private Integer app_size;//下载类型：应用包大小

}
