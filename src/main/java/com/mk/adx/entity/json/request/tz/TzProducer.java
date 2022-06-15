package com.mk.adx.entity.json.request.tz;

import lombok.Data;

import java.util.List;

/**
 * 提供者对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class TzProducer {
    private String id;//提供者 ID
    private String name;//提供者的名字
    private List<String> cat;//内容类别描述内容生产商
    private String domain;//最高级别的 domain

}
