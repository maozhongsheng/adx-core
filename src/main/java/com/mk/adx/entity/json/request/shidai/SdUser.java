package com.mk.adx.entity.json.request.shidai;

import lombok.Data;

/**
 * 用户对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:53
 */
@Data
public class SdUser {
    private String id;//用户id
    private int yob;//出生年份4位整数
    private String gender;//性别， M表示男性， F表示女性， O标识其他类型，不填充表示未知
    private String keywords;//逗号分隔的列表关键字
}
