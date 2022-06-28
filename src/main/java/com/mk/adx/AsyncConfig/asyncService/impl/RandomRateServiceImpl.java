package com.mk.adx.AsyncConfig.asyncService.impl;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.AsyncConfig.InsertKafka;
import com.mk.adx.client.AdminClient;
import com.mk.adx.entity.json.request.mk.MkAdv;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.service.MkTestService;
import com.mk.adx.service.WokeJsonService;
import com.mk.adx.util.RedisUtil;
import com.mk.adx.AsyncConfig.InsertMysql;
import com.mk.adx.AsyncConfig.asyncService.RandomRateService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @Author Jny
 * @Description 异步请求公共接口
 * @Date 2022/1/20 9:48
 */
@Slf4j
@Service("randomRateService")
public class RandomRateServiceImpl implements RandomRateService {

    @Autowired
    private AdminClient adminClient;

//    @Autowired
//    private InsertMysql insertMysql;

    @Autowired
    private InsertKafka insertKafka;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private MkTestService mktestService;

    @Autowired
    private WokeJsonService wokeJsonService;

    /**
     * 1、随机请求
     * @param map
     * @param distribute
     * @param request
     * @return
     */
    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<Map<Integer, MkBidResponse>> randomRequest(Map<String, Integer> map, Map distribute, MkBidRequest request) {
        Map<Integer, MkBidResponse> ranMap = new HashMap<>();
        ranMap = totalRequest(map, distribute, request);//公共方法
//        log.info(request.getImp().get(0).getTagid()+"-"+Thread.currentThread().getName()+"-请求成功");
        return AsyncResult.forValue(ranMap);
    }

    /**
     * 2、并发请求
     * @param map
     * @param distribute
     * @param request
     * @return
     */
    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<Map<Integer, MkBidResponse>> concurrentRequest(Map<String, Integer> map, Map distribute, MkBidRequest request) {
        Map<Integer, MkBidResponse> conMap = new HashMap<>();
        conMap = totalRequest(map, distribute, request);//公共方法
        return AsyncResult.forValue(conMap);
    }


    /**
     * 请求公共方法
     * @param map
     * @param distribute
     * @param request
     * @return
     */
    private Map<Integer, MkBidResponse> totalRequest(Map<String, Integer> map, Map distribute, MkBidRequest request) {
        Map<Integer, MkBidResponse> mapObj = new HashMap<>();//最后返回
        MkBidResponse response = new MkBidResponse();//请求返回数据
        MkAdv mkAdv = new MkAdv();
        Map upper = new HashMap();


        String timeoutStr = "";
        if (null!=distribute.get("timeout")){
            timeoutStr = distribute.get("timeout").toString();//后台配置超时时间
        }

        //循环获取相应数据
        for (String str : map.keySet()) {
            String adv_id = str;//联盟广告位id(adv_id)
            Integer rate = map.get(str);//比例

            //先从redis查询，如果为空则从数据库先查再放入redis
            //根据联盟广告位id查询上游数据,无效流量直接返回
            Object o = redisUtil.get(adv_id);
            if(null != o){
                upper = JSONObject.parseObject((redisUtil.get(adv_id).toString().replace("=", ":")), HashMap.class);//根据联盟id（key）去查询相应信息
            }else{
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
            //1、将上游数据存入adv中
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
            request.setAdv(mkAdv);//配置数据放入请求

            //2、处理mysql请求数据
            insertKafka.insertKafka(mkAdv,request);

            //2、根据adv_id处理请求service
            if(null != mkAdv){
                response = asyncRequest(request);//请求响应service，获得返回数据
                if (null!=response.getId()){
                    Long selectTimeout = response.getProcess_time_ms();//请求上游花费时间
                    //上游返回数据时间小于配置时间才给下游返回(如果配置时间为空则不卡时间)
                    if (StringUtils.isNotEmpty(timeoutStr)){
                        Long timeout = Long.parseLong(timeoutStr);
                        if (selectTimeout <= timeout){
                            mapObj.put(rate,response);//比例和返回集合
                        }else {
                            return mapObj;
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
        //test = 1 是正式  test = 0 为测试
            if (1 == bidRequest.getAdv().getTest()) {
                if("2021000056".equals(bidRequest.getAdv().getDsp_id())){
                    bidResponse = wokeJsonService.getWokeDataByJson(bidRequest);//沃克
                }
            }else {
                bidResponse = mktestService.getTestDataByJson(bidRequest);
            }


        //素材替换
//        if (null!=bidResponse.getId()){
//            List<TzSeat> seatList = new ArrayList<>();//重新组合的竞价对象集合
//            List<TzBid> bidList = new ArrayList<>();
//            TzSeat seat = new TzSeat();//竞价对象
//            TzBid tb = new TzBid();//广告集合
//            TzNative tzNative = new TzNative();//信息流内容
//            TzVideo tzVideo = new TzVideo();//视频内容
//            ArrayList<TzImage> images = new ArrayList<>();//图片集合
//            TzImage tzImage = new TzImage();//单个图片
//
//            List<TzSeat> seatbids = bidResponse.getSeatbid();//返回竞价对象集合
//            for (int i=0;i<seatbids.size();i++){
//                List<TzBid> bids = seatbids.get(i).getBid();
//                for (int j=0;j<bids.size();j++){
//                    String dsp_id = upper.get("dsp_id").toString();//联盟id
//                    String tag_id = upper.get("tag_id").toString();//联盟广告位id
//
//                    //获取天卓返回的素材类型
//                    Integer type = bids.get(j).getAd_type();
//                    if (type==null){
//                        type = 8;//给个默认值
//                    }
//                    //设置替换素材类型,默认是1图片
//                    String ad_type = "1";
//                    if (type.equals(8)||type.equals(9)){//信息流
//                        if (null!=bids.get(j).getNATIVE()){
//                            TzVideo native_video = bids.get(j).getNATIVE().getVideo();//信息流视频素材
//                            if (null != native_video){
//                                ad_type = "2";//视频是2，图片是1
//                            }
//                        }
//                    }else {//其他
//                        TzVideo video = bids.get(j).getVideo();//其他视频素材
//                        if (null != video){
//                            ad_type = "2";//视频是2，图片是1
//                        }
//                    }
//
//                    Integer width = bids.get(j).getW();//宽
//                    Integer height = bids.get(j).getH();//高
//                    String title = bids.get(j).getTitle();//标题
//                    String desc = bids.get(j).getDesc();//描述
//
//                    //请求redis,获取上游的广告素材
//                    String key = dsp_id+"_"+tag_id+"_"+ad_type+"_"+width+"_"+height;
//                    Object adObject = redisUtil.get(key);//根据广告位id从redis中查询数据
//                    if (null!=adObject){
//                        Map ad = JSONObject.parseObject(adObject.toString().replace("=", ":"), HashMap.class);
//                        String ad_url = ad.get("material").toString();//redis返回的替换素材url
////                Integer redis_type = Integer.valueOf(ad.get("type").toString());//redis返回的素材类型
//                        Integer status = Integer.valueOf(ad.get("status").toString());//素材替换开关
//                        //素材替换开关是1的，代表开，去替换，否则不替换
//                        if (status == 1){
//                            String keyWord = ad.get("keywords").toString();//关键字
//                            String[] keyWords = keyWord.split(",");
//                            for (int k=0;k<keyWords.length;k++){
//                                //如果标题或者描述包含关键字,则进行关键字替换
//                                if (title.contains(keyWords[k])||desc.contains(keyWords[k])) {
//                                    //分信息流和其他
//                                    if (type==8||type==9){//信息流
//                                        //分图片和视频
//                                        if (null!=bids.get(j).getNATIVE().getVideo()){//视频
//                                            tzVideo.setUrl(ad_url);
//                                            tzNative.setVideo(tzVideo);
//                                            tb.setNATIVE(tzNative);
//                                            bidList.add(tb);//
//                                            seat.setBid(bidList);
//                                            seatList.add(seat);
//                                            bidResponse.setSeatbid(seatList);//广告集合对象
//                                        }else {//图片
//                                            tzImage.setUrl(ad_url);
//                                            images.add(tzImage);
//                                            tzNative.setImages(images);
//                                            tb.setNATIVE(tzNative);
//                                            bidList.add(tb);//
//                                            seat.setBid(bidList);
//                                            seatList.add(seat);
//                                            bidResponse.setSeatbid(seatList);//广告集合对象
//                                        }
//                                    }else {//其他
//                                        //分图片和视频
//                                        if (null!=bids.get(j).getVideo()){//视频
//                                            tzVideo.setUrl(ad_url);
//                                            tb.setVideo(tzVideo);
//                                            bidList.add(tb);//
//                                            seat.setBid(bidList);
//                                            seatList.add(seat);
//                                            bidResponse.setSeatbid(seatList);//广告集合对象
//                                        }else {//图片
//                                            tzImage.setUrl(ad_url);
//                                            images.add(tzImage);
//                                            tb.setImages(images);
//                                            bidList.add(tb);//
//                                            seat.setBid(bidList);
//                                            seatList.add(seat);
//                                            bidResponse.setSeatbid(seatList);//广告集合对象
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return bidResponse;

    }

}
