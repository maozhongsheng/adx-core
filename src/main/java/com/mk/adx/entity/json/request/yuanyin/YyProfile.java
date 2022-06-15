package com.mk.adx.entity.json.request.yuanyin;

import lombok.Data;

@Data
public class YyProfile {
    private String age; //年龄数值
    private String gender; //性别，M：男，F：⼥
    private String interest; //兴趣爱好，如：⼩说,影视
    private String marriageType; //婚姻状态，1：已婚，2：未婚，0：未知
    private String consumptionLevel; //消费能⼒，1:⾼，2：中，3：低
    private String education; //学历，0：本科以下，1：本科，2：研究⽣，3：博⼠
}
