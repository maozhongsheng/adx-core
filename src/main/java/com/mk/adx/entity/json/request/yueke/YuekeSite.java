package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

@Data
public class YuekeSite {
    private String id;//网站编号，站点在智友平台上的唯一识别码，由智友广告交易平台提供
    private String name;//流量站点名称
    private String domain;//流量站点在线访问的主域名信息
    private String page;//当前页面在线访问的网络地址
    private String ref;//当前页面在线访问的前置页面在线网络访问地址
    private int mobile;//移动设备，1:是 0：否
    private String keywords;//关键字列，流量站点分类描述信息，逗号分隔。例如: "军事,汽车,萌宠" 等

}
