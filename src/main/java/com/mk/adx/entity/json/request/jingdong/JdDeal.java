package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * 协议对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 15:04
 */
@Data
public class JdDeal {
    private String id;//交易id
    private double bidfloor;//底价，单位：千次分（人民币）

}
