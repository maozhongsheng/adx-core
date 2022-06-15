package com.mk.adx.service;


import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;

public interface InvenoService {

    TzBidResponse getInvenoDataByJson(TzBidRequest request);

}
