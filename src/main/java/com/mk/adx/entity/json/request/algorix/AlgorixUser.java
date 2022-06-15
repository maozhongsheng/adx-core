package com.mk.adx.entity.json.request.algorix;

import lombok.Data;

@Data
public class AlgorixUser {
    private String id;//用户唯一 ID
    private Integer yob;//出生年份，四位数字，譬如 1995
    private String gender;//性别，"M" male, "F" female, "O" Other
}
