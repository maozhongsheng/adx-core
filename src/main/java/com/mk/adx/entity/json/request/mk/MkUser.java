package com.mk.adx.entity.json.request.mk;

import lombok.Data;

/**
 * 用户对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class MkUser {
    private int yob;//出生年份4位整数
    private String gender;//==性别,“M” = male, “F” = female, “O” = known to be
    private int age;//年龄：0:15岁以下,1:16~25岁,2:26~35,3:36~45,4:46~55,5:56岁以上,7:未知
    private String ip;//用户的 ip 地址
}
