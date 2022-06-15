package com.mk.adx.entity.json.response.yoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoBid {
    private String id;//DSP生成的单次出价id
    private String adid;//DSP生成的素材校验id
    private String impid;//对应请求中的imp的id
    private Double price;//竞价价格，单位：分/千次曝光（人民币），即CPM。非竞价广告无此字段
    private String nurl;//竞价成功回调，并通过宏替换%%WIN_PRICE%%提供二价。非竞价广告无此字段。处理成功，需要给返回状态码200，并返回SUCCESS字符串
    private List<YolohoAdms> adms;//素材对象
}
