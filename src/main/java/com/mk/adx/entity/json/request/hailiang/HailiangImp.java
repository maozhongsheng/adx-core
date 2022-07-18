package com.mk.adx.entity.json.request.hailiang;

import lombok.Data;

/**
 * 描述广告位对象-曝光对象-native
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/26 13:48
 */
@Data
public class HailiangImp {
    private String id;//必填 标识⼀个Imp对象的唯⼀ID
    private String slot_id;//必填，当前Imp所对应的⼴告位ID（嗨量提供）
    private int slot_type;//必填，描述当前⼴告位的类型信息
    private int bid_floor;//必填，描述当前⼴告位的类型信息

}
