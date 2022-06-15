package com.mk.adx.entity.json.request.tzyoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoUser {
    private String gender;//性别：M：male：femaleO：otherU：unknow
    private String birthday;//用户出生年份
    private List<String> tags;//用户所具有的标签
}
