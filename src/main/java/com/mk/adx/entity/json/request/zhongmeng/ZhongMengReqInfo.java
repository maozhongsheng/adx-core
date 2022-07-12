package com.mk.adx.entity.json.request.zhongmeng;

import lombok.Data;

/**
 * 广告位基本信息
 *
 * @author mzs
 * @version 1.0
 * @date 2022/4/8 14:21
 */
@Data
public class ZhongMengReqInfo {
    private String accessToken;//应用分配的token(需众盟运营同学分配)
    private String adSlotId;//分配的广告位ID(需众盟同学分配)

}
