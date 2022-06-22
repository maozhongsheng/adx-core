package com.mk.adx.AsyncConfig.asyncService.impl;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.AsyncConfig.InsertKafka;
import com.mk.adx.AsyncConfig.InsertMysql;
import com.mk.adx.AsyncConfig.RandomUtil;
import com.mk.adx.AsyncConfig.asyncService.TaskService;
import com.mk.adx.client.AdminClient;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.util.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service("clickHouseJsonService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private AdminClient adminClient;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RandomUtil randomUtil;

    @Autowired
    private InsertMysql insertMysql;

    @Autowired
    private InsertKafka insertKafka;


    /**
     * 一、处理流量分配和并发请求，并且将请求参数写入kafka
     * @param request
     * @return
     */
    @SneakyThrows
    @Override
    public MkBidResponse ckJsonRequest(MkBidRequest request) {
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        MkBidResponse bidResponse = new MkBidResponse();//最后返回数据

        Object adx = redisUtil.get(request.getImp().get(0).getTagid());//根据广告位id从redis中查询数据
        Map distribute = new HashMap();
        //1、根据广告位id查询联盟信息，判断redis中是否存在
        if(null != adx) {//redis存在，直接从redis查询
            distribute = JSONObject.parseObject(adx.toString().replace("=", ":"), HashMap.class);

        }else {//数据库查询联盟信息，并存入redis
            //1、根据广告位id查询配置的上游信息
            distribute = adminClient.selectRequetBySlotId(request.getImp().get(0).getTagid());
            if (null != distribute) {
                //将流量分发数据存入redis
                Map map = new HashMap();
                map.put("directional_status", distribute.get("directional_status"));
                map.put("dsp_slot_id", distribute.get("dsp_slot_id"));
                map.put("requet_rate", distribute.get("requet_rate"));
                map.put("test", distribute.get("test"));
                map.put("price", distribute.get("price"));
                redisUtil.set(request.getImp().get(0).getTagid(), JSONObject.toJSONString(map));
            }else{
                log.info("此请求为无效请求-请求id为："+ request.getId());
            }
        }


        //获取最后返回
        bidResponse = randomUtil.randomRequest(distribute,request);


        //4、处理返回数据，放入mysql
        if (null != bidResponse.getId()) {
//            bidResponse.setProcess_time_ms(tempTime);
            insertKafka.ckDataByJson(bidResponse,request,startTime);
        }

        return bidResponse;
    }

}
