package com.mk.adx.service;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;

import java.util.concurrent.Future;

public interface WbRtaJsonService {
    Future<TzBidResponse> getWbRtaDataByJson(TzBidRequest request);
}
