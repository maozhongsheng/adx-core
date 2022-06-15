package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

/**
 * 用户对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class MycUser {
    private String id;//用户id
    private int yob;//出生年份4位整数
    private String gender;//==性别,“M” = male, “F” = female, “O” = known to be
    private String keywords;//逗号分隔的列表关键字
}
