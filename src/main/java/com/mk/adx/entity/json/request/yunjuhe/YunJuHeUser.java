package com.mk.adx.entity.json.request.yunjuhe;

import lombok.Data;

import java.util.List;

/**
 * User 对象
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/14 13:53
 */
@Data
public class YunJuHeUser {
    private String gender;//用户性别0:未知 1:男 2:女
    private List<String> age;//用户年龄范围[XX , YY]：从 XX 到 YY 岁的
    private boolean vip;//是否是付费用户
    private List<String> keywords;//用户兴趣关键词

}
