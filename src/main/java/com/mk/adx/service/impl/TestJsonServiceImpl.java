package com.mk.adx.service.impl;

import com.mk.adx.config.TzResponseTestConfig;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.service.TestJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author yjn
 * @Description 测试数据
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("testJsonService")
public class TestJsonServiceImpl implements TestJsonService {

    /**
     *  测试数据
     * @param request
     * @return
     */
//    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public TzBidResponse getTestDataByJson(TzBidRequest request) {
        TzBidResponse bidResponse =new TzBidResponse();
        if("1".equals(request.getImp().get(0).getAd_slot_type()) || "2".equals(request.getImp().get(0).getAd_slot_type())){ // 信息流测试数据
                bidResponse = TzResponseTestConfig.XxlbidResponseTest(request);
        }else if("3".equals(request.getImp().get(0).getAd_slot_type())){
                bidResponse = TzResponseTestConfig.KpbidResponseTest(request);
        }
        return  bidResponse;
    }

}
