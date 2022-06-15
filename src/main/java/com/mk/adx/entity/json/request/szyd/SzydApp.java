package com.mk.adx.entity.json.request.szyd;

import lombok.Data;

import java.util.List;

@Data
public class SzydApp {
    private String id;//应⽤标识
    private String name;//应⽤名称
    private String ver;//应⽤版本
    private String bundle;//application bundle 或 package name
    private List<String> cat;//app 所属类别
    private List<String> pagecat;//当前⻚⾯的分类信息标签列表
    private List<String> keywords;//app 关键字标签列表

}
