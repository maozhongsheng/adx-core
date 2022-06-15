package com.mk.adx.entity.json.request.tz;

import lombok.Data;

/**
 * 部分对象的数值拓展
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:12
 */
@Data
public class TzSegment {
    private String id;//段的id
    private String name;//段的名字数据名称
    private String value;//部分对象的数值拓展
}
