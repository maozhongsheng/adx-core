package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/21 11:27
 */
@Data
public class JdValue {
    private String id;//用户标签枚举值id，id 和 name 一一对应
    private String name;//用户标签枚举值名称，如：汽车品牌 A
    private double weight;//用户标签枚举值置信度打分值，范围在 0~1 之间，0 代表置信度最低，1 代表置信度最高
}
