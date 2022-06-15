package com.mk.adx.entity.json.request.uc;

import lombok.Data;

/**
 * UC扩展信息
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/25 15:21
 */
@Data
public class UcExt {
    private String key; //扩展参数的key
    private String value;//扩展参数的value
}
