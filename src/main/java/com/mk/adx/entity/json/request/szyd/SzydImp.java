package com.mk.adx.entity.json.request.szyd;

import lombok.Data;

@Data
public class SzydImp {
    private String id;//曝光标识 ID，只在多次曝光有意义，媒体⽣成，请确保全局唯⼀
    private String tagid;//⼴告代码位
    private Integer secure;//是否需要返回 https，0 不需要，1 需要，默认为 0
    private SzydImpExt ext;//扩展信息，查看 Imp ext 对象
    private int bidfloor;//底价，单位 分/CPM采⽤ RTB 模式需要传⼊正整数的底价，不传或传 0 代表⾮ RTB 模式
}
