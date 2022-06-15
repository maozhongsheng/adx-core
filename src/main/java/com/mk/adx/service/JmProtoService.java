package com.mk.adx.service;

import com.mk.adx.entity.protobuf.tz.TzBidResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface JmProtoService {

    TzBidResponse.BidResponse getJmDataByProto(@RequestBody byte[] content);

}
