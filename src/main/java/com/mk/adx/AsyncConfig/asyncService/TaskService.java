package com.mk.adx.AsyncConfig.asyncService;

import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;


public interface TaskService {

    MkBidResponse ckJsonRequest(MkBidRequest request);
}
