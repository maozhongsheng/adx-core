package com.mk.adx.entity.json.request.uc;

import lombok.Data;

/**
 * UCapp信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcApp {
    private String fr; //平台分类枚举值android/iphone/other现在基本都是⽤ android和iphone，⽤户平台定向投放
    private String dn; //安装序列号（外部）
    private String sn; //安装序列号（内部）
    private String utdid; //阿⾥统⼀⽤户ID，淘内强烈建议填充，⽤于⽤户识别和定向投放(不要⼆次加⼯，⻓度为24)
    private String is_ssl; //为1则⽀持https，为0则不⽀持https
    private String pkg_name; //包名，ios 取bundleid（⼩写归⼀化）（对于ios直接取appstore上的包名），⽤于渠道识别和⼴告效果分析
    private String pkg_ver; //版本号，⽤于渠道识别和⼴告效果分析
    private String app_name; //应⽤名称（⼩写归⼀化）⽤于渠道识别和⼴告效果分析
    private String ua; //User Agent，⽆法填充可以填空串，⽤于渠道识别和⼴告效果分析
    private String app_country; //App发⾏国家
    private String lang; //App发⾏语⾔
    private String timezone; //App发⾏时区



}
