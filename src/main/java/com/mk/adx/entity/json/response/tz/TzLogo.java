package com.mk.adx.entity.json.response.tz;

import lombok.Data;

/**
 * 原生对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/7/1 17:26
 */
@Data
public class TzLogo {
    private String url;//logo图片地址
    private Integer h;//高
    private Integer w;//宽
    private Integer type;//类型
}
