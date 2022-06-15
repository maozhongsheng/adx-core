package com.mk.adx.entity.json.request.mangguo;

import lombok.Data;

/**
 * 芒果-总请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:49
 */
@Data
public class MangGuoBidRequest {
    private String sign;//请求签名认证，签名是由请求id+时间戳ts+token再md5生成，md5(req_id_ts_token) (req_id和ts和token用”_”连接后整体取md5值)
    private long ts;//时间戳(13位毫秒级)
    private String mid_id;//媒体id
    private String req_id;//请求唯一id
    private String version;//api协议版本
    private MangGuoAdspace adspace;//广告位类
    private MangGuoDevice device;//device类型
    private MangGuoUser user;//user类型可选，额外的用户信息

    private static final long serialVersionUID = 1L;

}
