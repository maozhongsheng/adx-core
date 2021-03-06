package com.mk.adx.AsyncConfig.asyncService;

import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;

import java.util.Map;
import java.util.concurrent.Future;


public interface RandomRateService {

    Future<Map<Integer, MkBidResponse>> randomRequest(Map<String, Integer> map, Map distribute, MkBidRequest request);

    Future<Map<Integer, MkBidResponse>> concurrentRequest(Map<String, Integer> map, Map distribute, MkBidRequest request);

}
