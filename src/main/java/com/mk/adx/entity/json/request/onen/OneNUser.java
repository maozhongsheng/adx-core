package com.mk.adx.entity.json.request.onen;

import lombok.Data;

/**
 * 用户对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:53
 */
@Data
public class OneNUser {
    private int gender;//用户性别 Unknown=0 Male=1Female=2
    private int age;//用户年龄
    private String keywords;//性别：1：男 2：女 3：未知

}
