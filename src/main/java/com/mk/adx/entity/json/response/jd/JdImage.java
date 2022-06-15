package com.mk.adx.entity.json.response.jd;

import lombok.Data;

import java.util.List;

/**
 * Image 对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/2 16:48
 */
@Data
public class JdImage {
    private String id;//序号，标示顺序，与媒体方约定展示顺序时使用
    private String url;//图片地址
}
