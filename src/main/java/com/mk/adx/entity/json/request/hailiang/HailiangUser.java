package com.mk.adx.entity.json.request.hailiang;

import lombok.Data;

import java.util.List;

/**
 * 用户对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/26 13:53
 */
@Data
public class HailiangUser {

    private String id;//条件必填，当前请求发起⽅持有的⽤户ID
    private int yob;//推荐，当前⽤户的出⽣年份信息，四位数字表示
    private String gender;//推荐，当前⽤户的性别信息 M - 男 F - ⼥ 缺省 - 未知
    private String keywords;//推荐，当前⽤户的关键词、兴趣点信息，通过逗号分割表述多个关键词信息
    private List<String> applist;//推荐，当前设备的应⽤安装包名列表

}
