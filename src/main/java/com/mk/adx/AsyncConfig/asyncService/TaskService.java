package com.mk.adx.AsyncConfig.asyncService;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;


public interface TaskService {

    TzBidResponse ckJsonRequest(TzBidRequest request);
}
