package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import java.util.List;

/**
 * 发布者对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class TzPublisher {
    private String id;//发布者 ID
    private String name;//发布者名称
    private List<String> cat;//内容类别
    private String domain;//交互发布者的domain

}
