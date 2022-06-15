package com.mk.adx.entity.json.response.jm;

import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:35
 */
@Data
public class JmMacros {
    private String macro;//宏
    private String value;//宏替换值
    private List<String> expression;//宏替换有效范围 使用字段名
}
