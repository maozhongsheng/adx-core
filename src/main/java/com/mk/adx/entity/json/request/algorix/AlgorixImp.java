package com.mk.adx.entity.json.request.algorix;

import lombok.Data;

@Data
public class AlgorixImp {
    private String id;//通常，从 1 递增。因为目前只支持一个 imp，所以传 1 即可
    private String tagid;//系统分配的 slot_id
    private Integer secure;//广告物料是否要求 https， 0否，1是
}
