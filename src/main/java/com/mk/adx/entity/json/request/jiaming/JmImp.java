package com.mk.adx.entity.json.request.jiaming;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 描述广告位对象-曝光对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:48
 */
@Data
public class JmImp {
    private String id;//曝光id,不是广告位i
    private JmBanner banner;///banner类型的广告位对象,横幅类型
    private JmVideo video;//video类型的广告位对象,视频类型
    @JSONField(name = "native")
    private JmNative NATIVE;//native类型的广告对象,原生类型
    private String tagid;//广告位ID
    private float bidfloor;//广告位底价，单位是：分/CPM
    private String bidfloorcur;//==广告位底价的货币币种,使用 ISO-4217 国际标准货币代码
    private int secure;//==安全状态（需要安全的HTTPS URL）,0 不安全, 1 安全
    private JmPmp pmp;//管理协议规则对象
    private List<String> allowstyle;//rtb 曝光位接受创意展现形式的ID数组对象,wangyi-nex
    private String ad_type;//广告位能接受的广告类型:0,无限制 1,点击跳转 2,点击下载 3,LBA 4,仅展示
    private String ad_slot_type;//广告位类型:1,信息流 2,banner 3,开屏 4,视频 5,横幅 6,插屏 7,暂停 8,贴片

}
