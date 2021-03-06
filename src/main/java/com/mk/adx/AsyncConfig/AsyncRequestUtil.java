package com.mk.adx.AsyncConfig;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.client.AdminClient;
import com.mk.adx.entity.json.request.mk.MkAdv;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.service.*;
import com.mk.adx.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 异步请求时，公共的逻辑
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/11 18:20
 */
@Slf4j
@Configuration
public class AsyncRequestUtil {

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private InsertKafka insertKafka;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private MkTestService mktestService;

    @Autowired
    private WokeJsonService wokeJsonService;

    @Autowired
    private ZhimengJsonService zhimengJsonService;

    @Autowired
    private YdzxJsonService ydzxJsonService;

    @Autowired
    private OneNJsonService oneNJsonService;

    @Autowired
    private XiaoMiJsonService xiaoMiJsonService;

    @Autowired
    private HailiangJsonService hailiangJsonService;

    @Autowired
    private YunJuHeJsonService yunJuHeJsonService;

    @Autowired
    private DouMengJsonService douMengJsonService;

    @Autowired
    private UcJsonService ucJsonService;

    @Autowired
    private YiLiangJsonService yiLiangJsonService;

    @Autowired
    private LanWaJsonService lanWaJsonService;

    @Autowired
    private ZhongMengJsonService zhongMengJsonService;

    @Autowired
    private HuanRuiJsonService huanRuiJsonService;



    public Map<Integer, MkBidResponse> totalRequest(Map<String, Integer> map, Map distribute, MkBidRequest request, int status){
        String timeoutStr = distribute.get("timeout").toString();//后台配置超时时间
        MkAdv mkAdv = new MkAdv();
        Map upper = new HashMap();
        Map<Integer, MkBidResponse> mapObj = new HashMap<>();

        //循环获取相应数据
        for (String str : map.keySet()) {
            String adv_id = str;//联盟广告位id(adv_id)
            Integer rate = map.get(str);//比例

            //先从redis查询，如果为空则从数据库先查再放入redis
            //根据联盟广告位id查询上游数据,无效流量直接返回
            upper = JSONObject.parseObject((redisUtil.get(adv_id).toString().replace("=", ":")), HashMap.class);//根据联盟id（key）去查询相应信息
            if (null == upper) {
                //根据联盟广告位id查询上游数据
                upper = adminClient.selectUpperBySlotId(adv_id);

                //将上游数据存入redis
                HashMap hashMap = new HashMap();
                hashMap.put("dsp_id", upper.get("dsp_id").toString());
                hashMap.put("app_id", upper.get("app_id").toString());
                hashMap.put("app_name", upper.get("app_name").toString());
                hashMap.put("bundle", upper.get("package").toString());
                hashMap.put("version", upper.get("version").toString());
                hashMap.put("tag_id", upper.get("tag_id").toString());
                hashMap.put("size", upper.get("dsp_size").toString());
                hashMap.put("slot_type", upper.get("slot_type").toString());
                hashMap.put("os", upper.get("os").toString());
                redisUtil.set(adv_id, JSONObject.toJSONString(hashMap));
            }

            //将上游数据存入adv中
            mkAdv.setApp_name(upper.get("app_name").toString());
            mkAdv.setDsp_id(upper.get("dsp_id").toString());
            mkAdv.setApp_id(upper.get("app_id").toString());
            mkAdv.setTag_id(upper.get("tag_id").toString());
            mkAdv.setSize(upper.get("size").toString());
            mkAdv.setBundle(upper.get("bundle").toString());
            mkAdv.setSlot_type(upper.get("slot_type").toString());
            mkAdv.setVersion(upper.get("version").toString());
            mkAdv.setOs(upper.get("os").toString());
            mkAdv.setPrice(Integer.valueOf(distribute.get("price").toString().split("\\.")[0]));
            mkAdv.setTest(Integer.valueOf(distribute.get("test").toString()));
            request.setAdv(mkAdv);

            //2、处理kafka请求数据
            insertKafka.insertKafka(mkAdv,request);

            //3、根据adv_id处理请求service
            if(null != mkAdv){
                MkBidResponse response = asyncRequest(request);//获得返回数据
                if (null!=response.getId()){
                    Long selectTimeout = response.getProcess_time_ms();//请求上游花费时间
                    //上游返回数据时间小于配置时间才给下游返回(如果配置时间为空则不卡时间)
                    if (StringUtils.isNotEmpty(timeoutStr)){
                        Long timeout = Long.parseLong(timeoutStr);
                        if (selectTimeout <= timeout){
                            mapObj.put(rate,response);//比例和返回集合
                        }
                    }else {
                        mapObj.put(rate,response);//比例和返回集合
                    }
                }
            }
        }

        return mapObj;
    }


    /**
     * 3、根据adv_id处理请求service
     * @param bidRequest
     * @return
     */
    private MkBidResponse asyncRequest(MkBidRequest bidRequest){
        MkBidResponse bidResponse = null;//返回数据
        if (1 == bidRequest.getAdv().getTest()) {
            if("2021000056".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = wokeJsonService.getWokeDataByJson(bidRequest);//沃克
            }else if("2021000057".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = zhimengJsonService.getZhimengDataByJson(bidRequest);//知乎
            }else if("2021000058".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = xiaoMiJsonService.getXiaoMiDataByJson(bidRequest);//小米
            }else if("2021000059".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = yunJuHeJsonService.getYunJuHeDataByJson(bidRequest);//云聚合
            }else if("2021000060".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = hailiangJsonService.getHailiangDataByJson(bidRequest);//嗨量
            }else if("2021000062".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = ydzxJsonService.getYdzxDataByJson(bidRequest);//一点咨询
            }else if("2021000063".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = oneNJsonService.getOneNDataByJson(bidRequest);//1n
            }else if("2021000064".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = ucJsonService.getUcDataByJson(bidRequest);//uc
            }else if("2021000065".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = douMengJsonService.getDouMengDataByJson(bidRequest);//豆盟
            }else if("2021000066".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = yiLiangJsonService.getYiLiangDataByJson(bidRequest);//奕量
            }else if("2021000068".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = lanWaJsonService.getLanWaDataByJson(bidRequest);//蓝蛙
            }else if("2021000069".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = zhongMengJsonService.getZhongMengDataByJson(bidRequest);//众盟
            }else if("2021000070".equals(bidRequest.getAdv().getDsp_id())){
                bidResponse = huanRuiJsonService.getHuanRuiDataByJson(bidRequest);//环睿
            }
        }else {
            bidResponse = mktestService.getTestDataByJson(bidRequest);
        }



        return bidResponse;

    }


}
