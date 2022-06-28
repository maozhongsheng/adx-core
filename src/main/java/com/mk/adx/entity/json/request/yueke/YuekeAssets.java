package com.mk.adx.entity.json.request.yueke;

import lombok.Data;

@Data
public class YuekeAssets {
    private int id;//编号信息，从 1 开始递增，例如：1，2，3，4 ... ...
    private YuekeTitle title;//标题信息，对标题信息有要求时该字段必填
    private YuekeData data;//描述信息，品牌信息，当广告样式为 "原生图文" 或者 "激励视频" 时，必填项
    private YuekeImg img;//图片内容信息要求，当广告样式为图文广告或者图片广告时必填
    private YuekeVideo video;//视频广告采买标准描述信息，当广告样式为“视频信息流”时，该字段为必填选项
    private int required;//强制状态，1:必须准守 0:选择遵守 默认：0


}
