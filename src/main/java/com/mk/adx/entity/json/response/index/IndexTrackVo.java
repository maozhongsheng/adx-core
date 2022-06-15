package com.mk.adx.entity.json.response.index;


import lombok.Data;

import java.util.List;

/**
 * Index总返回数据实体
 *
 * @author gj
 * @version 1.0
 * @date 2021/9/2 16:18
 */
@Data
public class IndexTrackVo {

    private int type;//监测类型

    private List<String> Required;//上报 url 列表
}
