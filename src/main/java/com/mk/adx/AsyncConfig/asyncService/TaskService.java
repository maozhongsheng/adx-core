package com.mk.adx.AsyncConfig.asyncService;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;


public interface TaskService {

    MkBidResponse ckJsonRequest(TzBidRequest request);
}
