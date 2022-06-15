package com.mk.adx.entity.json.request.yunjuhe;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 移动app对象
 *
 * @author yjn
 * @version 1.0
 * @date 2022/3/14 13:52
 */
@Data
public class YunJuHeApp {
    private String appId;//应用 id

    private String name;//App 名字

    @JSONField(name = "package")
    private String Package;//包名

    private String version;//版本

}
