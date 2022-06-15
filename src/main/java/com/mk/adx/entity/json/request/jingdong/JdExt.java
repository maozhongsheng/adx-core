package com.mk.adx.entity.json.request.jingdong;

import lombok.Data;

/**
 * TODO
 *
 * @author yjn
 * @version 1.0
 * @date 2021/5/21 11:00
 */
@Data
public class JdExt {
    private String wifi;//应反作弊需求添加， 表示扫描到的WIFI 列表（3 个或者至少 1 个）, 以’,’ 逗号分割
    private String radius;//应反作弊需求添加，表示客户端所在位置的定位半径，单位米
}
