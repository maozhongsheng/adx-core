package com.mk.adx.entity.json.response.mk;

import lombok.Data;

/**
 * 图片对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/3/16 17:25
 */
@Data
public class  MkImage {
    private String id;//序号，标示顺序，与媒体方约定展示顺序时使用
    private String url;//图片地址
    private Integer h;//图片高
    private Integer w;//图片宽
    private Integer type;//类型
}
