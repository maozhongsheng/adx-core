package com.mk.adx.entity.json.request.zhimeng;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/12/8 14:23
 */
@Data
public class ZhimengUserInfo {
    private String id;//用户ID
    private int gender;//性别 1-男 0-女
    private int age;//年龄
    private List<String> keywords;//关键词
    private List<String> interest;//用户兴趣ID 详见兴·趣列表

}
