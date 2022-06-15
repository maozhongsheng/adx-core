package com.mk.adx.entity.json.request.jialiang;

import lombok.Data;

/**
 * 应⽤信息描述
 *
 * @author jny
 * @version 1.0
 * @date 2022/4/7 15:21
 */
@Data
public class JiaLiangApp {
    private String  name;//应⽤名称，流量应⽤名称
    private String  bundle;//应⽤包名 - 流量应⽤包名，例如：com.jialiangad.app
    private String  pkg_version;//⽤版本名称 - 流量应⽤版本，例如：1.0.0
    private Integer pkg_version_code;//应⽤版本号，例如：25
    private Integer paid;//是否付费应⽤，1：是；0：否（默认）

}
