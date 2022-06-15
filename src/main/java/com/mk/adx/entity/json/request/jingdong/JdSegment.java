package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

import java.util.List;

/**
 * segment对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/21 11:23
 */
@Data
public class JdSegment {
    private String id;//
    private String name;//
    private List<JdValue> value;
}
