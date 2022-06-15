package com.mk.adx.entity.json.request.moyicheng;

import lombok.Data;

import java.util.List;

@Data
public class MycImg {
    private Integer type;
    private Integer w;
    private Integer h;
    private Integer wmin;
    private Integer hmin ;
    private List<String> mimes;

}
