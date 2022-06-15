package com.mk.adx.entity.json.response.ydzx;

import lombok.Data;

import java.util.List;

/**
 * 竞价集合对象,若是竞价至少有一个
 *
 * @author yjn
 * @version 1.0
 * @date 2021/8/3 16:48
 */
@Data
public class YdzxSeatBid {
    private List<YdzxBid> bid;//出价对象数组，如果出价则对应于BidRequest中的Imp
}
