package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * banner类型的广告位对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/20 13:50
 */
@Data
public class JdBanner {
    private int w;//广告位宽度
    private int h;//广告位高度
    private int pos;//广告位在屏幕位置，参见（6.5Ad Position）http://www.iab.net/media/file/OpenRTBAPiSpecificationVersion2_2.pdf

}
