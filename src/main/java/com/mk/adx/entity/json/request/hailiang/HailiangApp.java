package com.mk.adx.entity.json.request.hailiang;

import lombok.Data;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/26 13:52
 */
@Data
public class HailiangApp {
    private String id;//必填，发起当前请求的应⽤ID （嗨量提供）
    private String name;//必填，当前应⽤的名称
    private String ver;//必填，当前应⽤的版本信息
    private String bundle;//必填，当前应⽤的包名

}
