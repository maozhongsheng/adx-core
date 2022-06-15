package com.mk.adx.entity.json.response.jm;

import lombok.Data;

/**
 * 图片对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/16 17:25
 */
@Data
public class JmImages {
    private String url;//图片地址
    private Integer h;//图片高
    private Integer w;//图片宽
    private Integer sort;//素材优先级
    private Integer duration;//展示时长	单位：秒
}
