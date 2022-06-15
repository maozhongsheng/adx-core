package com.mk.adx.entity.json.request.jialiang;

import lombok.Data;

/**
 * ⼴告位描述信息
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class JiaLiangImp {
    private Integer aw;//本次流量售卖位置的真实宽度数值信息，单位：像素
    private Integer ah;//本次流量售卖位置的真实⾼度数值信息，单位：像素
    private Integer ad_type;//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
    private Integer size;//如果是⼴告视频，最⼤视频⼤⼩，单位kb
    private Integer max_duration;//视频最⼤时⻓，单位秒
    private Integer min_duration;//视频最⼩时⻓，单位秒

}
