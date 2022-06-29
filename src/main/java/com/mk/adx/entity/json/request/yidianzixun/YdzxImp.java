package com.mk.adx.entity.json.request.yidianzixun;

import lombok.Data;

/**
 * 描述广告位对象-曝光对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:48
 */
@Data
public class YdzxImp {
    private String slot_id;//广告位的唯一标识符，由一点提供
    private Integer bidfloor;//广告位底价，单位是：分/CPM
    private String dealId;//直接交易标识 ID；由交易平台和DSP 提前约定
}
