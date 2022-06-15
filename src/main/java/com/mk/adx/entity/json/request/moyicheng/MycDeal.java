package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

/**
 * 协议对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 15:04
 */
@Data
public class MycDeal {
    private String id;//直接交易的唯一ID
    private float bidfloor;//deal 价格,单位是分/千次曝光,即CPM
    private Integer at;//==拍卖类型,1-GFP  2-GSP
}
