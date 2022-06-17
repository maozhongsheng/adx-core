package com.mk.adx.entity.json.request.mk;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 描述广告位对象-曝光对象-native
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/11 13:48
 */
@Data
public class MkImp {
    @NotBlank(message = "广告位ID不能为空")
    private String tagid;//广告位ID
    @NotBlank(message = "广告位类型不能为空")
    private String slot_type;//广告位类型:1,信息流 2,banner 3,开屏 4,视频 5,横幅 6,插屏 7,暂停 8,贴片
    @NotBlank(message = "广告位宽不能为空")
    private int w;
    @NotBlank(message = "广告位高不能为空")
    private int h;
}
