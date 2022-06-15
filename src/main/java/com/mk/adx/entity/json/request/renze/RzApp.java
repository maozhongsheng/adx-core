package com.mk.adx.entity.json.request.renze;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 13:52
 */
@Data
public class RzApp {
    private String appId;//应用 id
    private String name;//App 名
    @JSONField(name = "package")
    private String PACKAGE;//包名
    private String version;//版本
}
