package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * user对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/21 11:13
 */
@Data
public class JdUser {
    private String id;//应反作弊需求添加，表示客户端所在位置的定位半径，单位米
    private String gender;//性别，取值M：男， F：女
    private String keywords;//用户关键字/词标签，多个直接用“,”分割
    private JdData data;//其他用户数据
}
