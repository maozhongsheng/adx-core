package com.mk.adx.entity.json.request.baixun;

import lombok.Data;

@Data
public class BaiXunUser {
    private String userid;//用户 id
    private String buyerid;//映射的消耗方用户 i
    private String tags;//用户标签
    private String gender;//male/female
    private int age;//年龄
}
