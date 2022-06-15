package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import java.util.List;

/**
 * 用户对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class TzUser {
    private String id;//用户id
    private String buyeruid;//投标人的uid
    private int yob;//出生年份4位整数
    private String gender;//==性别,“M” = male, “F” = female, “O” = known to be
    private String keywords;//逗号分隔的列表关键字
    private TzGeo geo;//地理位置对象
    private int age;//年龄：0:15岁以下,1:16~25岁,2:26~35,3:36~45,4:46~55,5:56岁以上,7:未知
    private String ip;//用户的 ip 地址
    private List<TzData> data;//额外的用户数据对象
   // private String tags;//用户标签，逗号分割
}
