package com.mk.adx.service;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;

import java.util.Map;

public interface DspJsonService {

    TzBidResponse getDspDataByJson(TzBidRequest request, Map parames);

}
