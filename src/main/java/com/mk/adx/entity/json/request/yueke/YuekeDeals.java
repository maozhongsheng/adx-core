package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

@Data
public class YuekeDeals {
    private String id;//交易编号，生成方式:随机字符串的 MD5 加密小写
    private int at;//结算方式，1:第一价格 2：第二价格 3: bidfloor 的值为交易价格
    private float bidfloor;//订单价格，单位：分


}
