package com.mk.adx.entity.json.request.tongzhou;

import lombok.Data;

import java.util.List;

/**
 * 同舟
 *
 * @author  mzs
 * @version 1.0
 * @date 2022/5/30 18:21
 */
@Data
public class TongZhouBidRequest {
    private String req_id; //请求 id，由媒体生成
    private List<TongZhouImp> imp; //广告位，数组格式，目前仅支持一个
    private TongZhouApp app; //请求 id，由媒体生成
    private TongZhouDevice device; //请求 id，由媒体生成

}
