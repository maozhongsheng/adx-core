package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

import java.util.List;

@Data
public class YuekeApp {
    private String id;//媒体 ID，该字段由智友广告交易平台提供
    private String name;//流量应用名称
    private String bundle;//流量应用包名，例如：“com.xxx.xxxx”
    private String ver;//流量应用版本，例如: 6
    private String paid;//是否是付费应用，1:是，0:否
    private String keywords;//量应用分类描述信息，逗号分隔。例如: "军事,汽车,萌宠" 等
    private List<String> cat;//媒体类型

}
