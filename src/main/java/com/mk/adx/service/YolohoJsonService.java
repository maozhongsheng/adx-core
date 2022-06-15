package com.mk.adx.service;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.tzyoloho.YolohoBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.entity.json.response.yoloho.YolohoBidRespones;

public interface YolohoJsonService {

    YolohoBidRespones getYolohoResponse(TzBidResponse request);

    TzBidRequest getTzRequest(YolohoBidRequest request);
}
