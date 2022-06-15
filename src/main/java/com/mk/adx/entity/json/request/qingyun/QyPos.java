package com.mk.adx.entity.json.request.qingyun;

import lombok.Data;


/**
 * 青云-广告信息
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class QyPos {
    private String id;//广告位 ID
    private int width;//广告位宽度，默认 0
    private int height;//广告位高度，默认 0
}
