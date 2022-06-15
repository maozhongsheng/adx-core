package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * 应用信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:22
 */
@Data
public class JmBidApps {
    private String app_name;//下载类型：应用名称
    private String bundle;//广告应用的包名
    private String app_icon;//下载类型：应用图标
    private Integer app_size;//下载类型：应用包大小
    private String tune_class_name;//拉活对应的service名称
    private String tune_action;//拉活对应的action
    private String tmast_download_url;//应用宝下载地址
    private List<String> tmast_download_urls;//应用宝下载打点地址
    private String app_version;//应用版本

}
