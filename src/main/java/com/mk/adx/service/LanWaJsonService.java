package com.mk.adx.service;

import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
public interface LanWaJsonService {

    MkBidResponse getLanWaDataByJson(MkBidRequest request);

}
