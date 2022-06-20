package com.mk.adx.service.Imp;

import com.mk.adx.config.MkResponseTestConfig;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.service.MkTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


/**
 * @Author mzs
 * @Description
 * @date 2022/6/20 15:21
 */
//测试数据
@Slf4j
@Service
public class MkTestServiceImpl implements MkTestService {
    @Override
    public MkBidResponse getTestDataByJson(MkBidRequest request)  {
        MkBidResponse bidResponse = new MkBidResponse();
        if("1".equals(request.getImp().get(0).getSlot_type()) || "2".equals(request.getImp().get(0).getSlot_type())){ // 信息流测试数据
            bidResponse = MkResponseTestConfig.XxlbidResponseTest(request);
        }else if("3".equals(request.getImp().get(0).getSlot_type())){
            bidResponse = MkResponseTestConfig.KpbidResponseTest(request);
        }

        return bidResponse;
    }
}
