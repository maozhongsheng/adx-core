package com.mk.adx.service;


import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;

public interface TestJsonService {

    TzBidResponse getTestDataByJson(TzBidRequest request);
}
