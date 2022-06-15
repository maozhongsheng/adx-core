package com.mk.adx.entity.json.request.doumeng;

import lombok.Data;

/**
 * 豆盟
 *
 * @author  mzs
 * @version 1.0
 * @date 2022/5/30 18:21
 */
@Data
public class DouMengBidRequest {
    private String appKey; //媒体唯一标识，ADX平台提供，调试请用下面的appKey
    private String adSpaceKey; //广告位唯一标识，ADX平台提供，调试请用下面的adSpaceKey
    private DouMengDevice device; //设备信息
    private DouMengNetwork network; //网络信息
    private String apiVersion; //api版本号
    private int adSpaceWidth; //广告位宽
    private int adSpaceHeight; //广告位高
    private DouMengApp app; //app应用信息，app流量必填

}
