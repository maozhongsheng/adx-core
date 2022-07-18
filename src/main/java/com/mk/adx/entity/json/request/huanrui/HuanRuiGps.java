package com.mk.adx.entity.json.request.huanrui;

import lombok.Data;

import java.util.List;

/**
 * 用户对象
 *
 * @author mzs
 * @version 1.0
 * @date 2021/11/26 13:53
 */
@Data
public class HuanRuiGps {

    private Integer coordinateType;
    private String lon;
    private String lat;

}
