package com.mk.adx.entity.json.response.yoloho;

import lombok.Data;

import java.util.List;

@Data
public class YolohoBidRespones {
    private String reqid;
    private String bidid;
    private List<YolohoBid> bids;
}
