package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * 竞价集合对象,若是竞价至少有一个
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:48
 */
@Data
public class JmSeat {
    private List<JmBid> bid;//出价对象,广告集合
    private String seat;//投标人的对于这个竞价集合的标识
}
