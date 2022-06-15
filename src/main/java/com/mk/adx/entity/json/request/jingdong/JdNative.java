package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * native类型的广告对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:50
 */
@Data
public class JdNative {
    private int w;//广告位宽度（填写时请与平台要求的广告位宽保持一致）
    private int h;//广告位高度,（填写时请与平台要求的广告位宽保持一致）
    private int count;//所需素材个数，一般为 1；若支持多素材，并且对每个素材图片点击支持独立跳转可大于 1
    private int imgnum;//标识所需图片个数；1：开屏/横幅/单图信息流；2：信息流视频；3：三图信息流；

}
