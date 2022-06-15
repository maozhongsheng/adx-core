package com.mk.adx.entity.json.request.tz;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 描述广告位对象-曝光对象-native
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:48
 */
@Data
public class TzImp {
    @NotBlank(message = "曝光id不能为空")
    private String id;//曝光id,不是广告位id
    @NotBlank(message = "广告位ID不能为空")
    private String tagid;//广告位ID
    //@NotEmpty(message = "曝光位接受创意展现形式的ID数组对象不能为空")
    private List<String> allowstyle;//rtb 曝光位接受创意展现形式的ID数组对象,wangyi-nex
    @NotBlank(message = "广告位类型不能为空")
    private String ad_slot_type;//广告位类型:1,信息流 2,banner 3,开屏 4,视频 5,横幅 6,插屏 7,暂停 8,贴片
    private TzBanner banner;///banner类型的广告位对象,横幅类型
    private TzVideo video;//video类型的广告位对象,视频类型
    @JSONField(name = "native")
    private TzNative NATIVE;//native类型的广告对象,	原生类型
    private Float bidfloor;//广告位底价，单位是：分/CPM
    private String bidfloorcur;//==广告位底价的货币币种,使用 ISO-4217 国际标准货币代码
    private TzPmp pmp;//管理协议规则对象
    private String refresh_time;//页面刷新标识，用于确认多个请求的广告位是否是同一用户的一次浏览。进行广告投放去重判断
    private String ad_type;//广告位能接受的广告类型:0,无限制 1,点击跳转 2,点击下载 3,LBA 4,仅展示
    private Integer req_num;//广告位请求的广告条数，默认请求一条广告

}
