package com.mk.adx.controller;


import com.mk.adx.entity.json.request.mk.MkBidRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 接受请求
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/26 15:30
 */
@RestController
@Slf4j
@RequestMapping("/api/json")
public class TestController {


    /**
     * @Author mzs
     * @Description
     * @Date 2021/8/26 15:30
     */
//    @ResponseBody
//    @RequestMapping(value = "/tzRequestTest",method = {RequestMethod.POST})
//    public ResponseResult indexJsonRequest(@Valid @RequestBody TzBidRequest request) throws IOException, ExecutionException, InterruptedException {
//        log.info("========="+ JSONObject.toJSONString(request.toString()));
//        MkBidResponse bidResponse;
//       // bidResponse = douMengJsonService.getDouMengDataByJson(request);
//      //  bidResponse = jiaLiangJsonService.getJiaLiangDataByJson(request);
//        if (null!=bidResponse.getId()){
//            return new ResponseResult(bidResponse);
//        }else {
//            return new ResponseResult();
//        }
//    }
}
