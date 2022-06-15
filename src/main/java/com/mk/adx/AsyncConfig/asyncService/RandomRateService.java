package com.mk.adx.AsyncConfig.asyncService;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;

import java.util.Map;
import java.util.concurrent.Future;


public interface RandomRateService {

    Future<Map<Integer, TzBidResponse>> randomRequest(Map<String, Integer> map, Map distribute, TzBidRequest request);

    Future<Map<Integer, TzBidResponse>> concurrentRequest(Map<String, Integer> map, Map distribute, TzBidRequest request);

}
