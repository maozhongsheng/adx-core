package com.mk.adx.entity.json.request.onen;

import lombok.Data;

/**
 * 描述广告位对象-曝光对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/2 13:48
 */
@Data
public class OneNAdSlot {
    private int adType;
    private int position;
    private int acceptedCreativeTypes;
    private int acceptedInteractionType;
    private int width;
    private int height;
}
