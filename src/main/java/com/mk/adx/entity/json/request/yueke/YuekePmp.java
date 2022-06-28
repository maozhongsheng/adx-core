package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

import java.util.List;

@Data
public class YuekePmp {
    private int private_auction;//私有拍卖: 1:是 0:否
    private List<YuekeDeals> deals;//私有交易，流量采买标准描述信息
}
