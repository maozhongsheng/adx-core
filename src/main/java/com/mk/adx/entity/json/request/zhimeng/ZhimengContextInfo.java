package com.mk.adx.entity.json.request.zhimeng;

import lombok.Data;

/**
 * 上下文信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/12/8 14:36
 */
@Data
public class ZhimengContextInfo {

    private String type;//页面类型 “ans” “art” “qus” “vip_c” “vip_v” “vip_pay”
    private String token;//页面token
    private String source;//当前页面来源

}
