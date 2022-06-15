package com.mk.adx.entity.json.request.tz;

import lombok.Data;

/**
 * 额外的用户数据对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 16:10
 */
@Data
public class TzData {
    private String id;//数据id
    private String name;//数据名称
    private TzSegment segment;//部分对象的数值拓展
}
