package com.mk.adx.service;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;

import java.util.Map;
import java.util.concurrent.Future;

public interface MgRtaJsonService {

    Future<TzBidResponse> getMgRtaDataByJson(TzBidRequest request, Map parames);

}
