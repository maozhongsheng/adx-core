package com.mk.adx.entity.json.response.index;


import lombok.Data;

/**
 * Index总返回数据实体
 *
 * @author gj
 * @version 1.0
 * @date 2021/9/2 16:18
 */
@Data
public class IndexBidResponse {

    private String requestId;//请求 id

    private IndexAd ads;//ad 对象

    private int statusCode;//状态码
}
