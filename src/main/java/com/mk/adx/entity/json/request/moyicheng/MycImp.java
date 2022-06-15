package com.mk.adx.entity.json.request.moyicheng;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 描述广告位对象-曝光对象-native
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:48
 */
@Data
public class MycImp {
    private String id;//曝光id,不是广告位i
    private String tagid;//广告位ID
    private float bidfloor;//广告位底价，单位是：分/CPM
    @JSONField(name = "native")
    private MycNative NATIVE;//native类型的广告对象,	原生类型
    private MycBanner banner;///banner类型的广告位对象,横幅类型
    private MycVideo video;//video类型的广告位对象,视频类型
    private int dplink;//是否支持deeplink，0-不支持 1-支持（默认）
    private MycPmp pmp;//管理协议规则对象

}
