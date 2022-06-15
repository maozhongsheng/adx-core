package com.mk.adx.entity.json.request.mangguo;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/27 16:33
 */
@Data
public class MangGuoUser {
    private int gender;//性别可选，用户的性别 0:未知，1:男性；2:女性
    private int age;//年龄可选，用户的年龄

}
