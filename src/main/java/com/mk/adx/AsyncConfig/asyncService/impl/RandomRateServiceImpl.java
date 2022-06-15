package com.mk.adx.AsyncConfig.asyncService.impl;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.client.AdminClient;
import com.mk.adx.client.DspClient;
import com.mk.adx.entity.json.request.tz.TzAdv;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.*;
import com.mk.adx.util.RedisUtil;
import com.mk.adx.AsyncConfig.InsertKafka;
import com.mk.adx.AsyncConfig.asyncService.RandomRateService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private DspClient dspClient;

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private YdJsonService ydJsonService;

    @Autowired
    private YdzxJsonService ydzxJsonService;

    @Autowired
    private SdJsonService sdJsonService;

    @Autowired
    private TestJsonService testJsonService;

    @Autowired
    private BaiduJsonService baiduJsonService;

    @Autowired
    private ChuanGuangService chuanGuangService;

    @Autowired
    private CdJsonService cdJsonService;

    @Autowired
    private YqJsonService yqJsonService;

    @Autowired
    private YxJsonService yxJsonService;

    @Autowired
    private CqJsonService cqJsonService;

    @Autowired
    private SmJsonService smJsonService;

    @Autowired
    private ZslyJsonService zslyJsonService;

    @Autowired
    private HailiangJsonService hailiangJsonService;

    @Autowired
    private MtRtaJsonService mtRtaJsonService;

    @Autowired
    private RzJsonService rzJsonService;

    @Autowired
    private WbRtaJsonService wbRtaJsonService;

    @Autowired
    private XingYunJsonService xingYunJsonService;

    @Autowired
    private QyJsonService qyJsonService;

    @Autowired
    private ZhimengJsonService zhimengJsonService;

    @Autowired
    private YuanYinJsonService yuanYinJsonService;

    @Autowired
    private MgRtaJsonService mgRtaJsonService;

    @Autowired
    private InvenoService invenoService;

    @Autowired
    private OnewayJsonService onewayJsonService;

    @Autowired
    private UcJsonService ucJsonService;

    @Autowired
    private LanWaJsonService lanWaJsonService;

    @Autowired
    private YunJuHeJsonService yunJuHeJsonService;

    @Autowired
    private DspJsonService dspJsonService;

    @Autowired
    private InsertKafka insertKafka;

    @Autowired
    private TongChengJsonService tongChengJsonService;

    @Autowired
    private MangGuoJsonService mangGuoJsonService;

    @Autowired
    private YiLiangJsonService yiLiangJsonService;

    @Autowired
    private YouYiJsonService youYiJsonService;

    @Autowired
    private ZhongMengJsonService zhongMengJsonService;

    @Autowired
    private JiaLiangJsonService jiaLiangJsonService;

    @Autowired
    private AlgorixJsonService algorixJsonService;

    @Autowired
    private WokeJsonService wokeJsonService;

    @Autowired
    private SzydJsonService szydJsonService;

    @Autowired
    private MiTuJsonService miTuJsonService;

    @Autowired
    private RuiDiJsonService ruiDiJsonService;

    @Autowired
    private BaiXunJsonService baiXunJsonService;

    @Autowired
    private TongZhouJsonService tongZhouJsonService;

    @Autowired
    private DouMengJsonService douMengJsonService;

    @Resource
    private RedisUtil redisUtil;

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
    public Future<Map<Integer, TzBidResponse>> randomRequest(Map<String, Integer> map, Map distribute, TzBidRequest request) {
        Map<Integer, TzBidResponse> ranMap = new HashMap<>();
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
    public Future<Map<Integer, TzBidResponse>> concurrentRequest(Map<String, Integer> map, Map distribute, TzBidRequest request) {
        Map<Integer, TzBidResponse> conMap = new HashMap<>();
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
    private Map<Integer, TzBidResponse> totalRequest(Map<String, Integer> map, Map distribute, TzBidRequest request) {
        Map<Integer, TzBidResponse> mapObj = new HashMap<>();//最后返回
        TzBidResponse response = new TzBidResponse();//请求返回数据
        TzAdv tzAdv = new TzAdv();
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

            //1、将上游数据存入adv中
            tzAdv.setApp_name(upper.get("app_name").toString());
            tzAdv.setDsp_id(upper.get("dsp_id").toString());
            tzAdv.setApp_id(upper.get("app_id").toString());
            tzAdv.setTag_id(upper.get("tag_id").toString());
            tzAdv.setSize(upper.get("size").toString());
            tzAdv.setBundle(upper.get("bundle").toString());
            tzAdv.setSlot_type(upper.get("slot_type").toString());
            tzAdv.setVersion(upper.get("version").toString());
            tzAdv.setOs(upper.get("os").toString());
            tzAdv.setPrice(Integer.valueOf(distribute.get("price").toString().split("\\.")[0]));
            tzAdv.setTest(Integer.valueOf(distribute.get("test").toString()));
            request.setAdv(tzAdv);//配置数据放入请求

            //2、处理kafka请求数据
            request = insertKafka.insertKafka(tzAdv,request);

            //2、根据adv_id处理请求service
            if(null != tzAdv){
                response = asyncRequest(request,upper);//请求响应service，获得返回数据
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
    private TzBidResponse asyncRequest(TzBidRequest bidRequest,Map upper){
        TzBidResponse bidResponse = null;//返回数据
        Object  parames = redisUtil.get("dsp_"+bidRequest.getImp().get(0).getTagid());//根据广告位id（key）去查询相应信息
        if(null != parames){
            //bidResponse = mtRtaJsonService.getMtRtaDataByJson(bidRequest,parames);
         //   bidResponse = dspJsonService.getDspDataByJson(bidRequest,parames);
        }else {
            if (1 == bidRequest.getAdv().getTest()) {
                if ("2021000008".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ydJsonService.getYdDataByJson(bidRequest);//有道
                } else if ("2021000009".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ydzxJsonService.getYdzxDataByJson(bidRequest);//一点咨询
                } else if ("2021000010".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = sdJsonService.getSdDataByJson(bidRequest);//时代广告-滴滴
                } else if ("2021000014".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = baiduJsonService.getBaiduDataByJson(bidRequest);//百度
                } else if ("2021000018".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = chuanGuangService.chuanGuangDataByJson(bidRequest);//传广
                } else if ("2021000019".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = cdJsonService.getCdDataByJson(bidRequest);//创典
                } else if ("2021000021".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yqJsonService.getYqDataByJson(bidRequest);//益起
                } else if ("2021000022".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yxJsonService.getYxDataByJson(bidRequest);//云袭
                } else if ("2021000023".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = cqJsonService.getCqDataByJson(bidRequest);//长青
                } else if ("2021000024".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = smJsonService.getSmDataByJson(bidRequest);//思盟
                } else if ("2021000025".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = zslyJsonService.getZslyDataByJson(bidRequest);//掌上乐游
                } else if ("2021000026".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = hailiangJsonService.getHailiangDataByJson(bidRequest);//嗨量
                } else if ("2021000027".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = rzJsonService.getRzDataByJson(bidRequest);//仁泽
                }else if ("2021000029".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = xingYunJsonService.getXyDataByJson(bidRequest);//星云
                }else if ("2021000031".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = qyJsonService.getQyDataByJson(bidRequest);//青云
                }else if ("2021000030".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = zhimengJsonService.getZhimengDataByJson(bidRequest);//知乎-知盟
                }else if ("2021000032".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yuanYinJsonService.getYuanYinDataByJson(bidRequest);//上海缘音
                }else if ("2021000033".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = invenoService.getInvenoDataByJson(bidRequest);//深圳英威诺
                }else if ("2021000034".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = onewayJsonService.getOnewayDataByJson(bidRequest);//广东万唯
                }else if ("2021000036".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ucJsonService.getUcDataByJson(bidRequest);//UC
                }else if ("2021000038".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = tongChengJsonService.getTongChengDataByJson(bidRequest);//同程ADX
                }else if ("2021000039".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = mangGuoJsonService.getMangGuoDataByJson(bidRequest);//芒果ADX
                } else if ("2021000040".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yiLiangJsonService.getYiLiangDataByJson(bidRequest);//奕量vivo
                }else if ("2021000041".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = youYiJsonService.getYouYiDataByJson(bidRequest);//友谊
                }else if ("2021000042".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = lanWaJsonService.getLanWaDataByJson(bidRequest);//蓝蛙
                }else if ("2021000043".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yunJuHeJsonService.getYunJuHeDataByJson(bidRequest);//云聚合
                }else if ("2021000044".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = jiaLiangJsonService.getJiaLiangDataByJson(bidRequest);//佳量
                }else if ("2021000045".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = zhongMengJsonService.getZhongMengDataByJson(bidRequest);//众盟
                }else if ("2021000046".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = algorixJsonService.getAlgorixDataByJson(bidRequest);//algorix
                }else if ("2021000047".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = wokeJsonService.getWokeDataByJson(bidRequest);//沃氪
                }else if ("2021000048".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = szydJsonService.getSzydDataByJson(bidRequest);//数字悦动
                }else if ("2021000049".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ruiDiJsonService.getRuidiDataByJson(bidRequest);//瑞迪
                }else if ("2021000050".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = miTuJsonService.getMiTuDataByJson(bidRequest);//觅途
                }else if ("2021000051".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = baiXunJsonService.getBaiXunDataByJson(bidRequest);//百寻
                }else if ("2021000052".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = tongZhouJsonService.getTongZhouDataByJson(bidRequest);//同舟
                }else if ("2021000053".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = douMengJsonService.getDouMengDataByJson(bidRequest);//豆盟
                }
            }else {
                bidResponse = testJsonService.getTestDataByJson(bidRequest);//测试数据
            }
        }

        //素材替换
        if (null!=bidResponse.getId()){
            List<TzSeat> seatList = new ArrayList<>();//重新组合的竞价对象集合
            List<TzBid> bidList = new ArrayList<>();
            TzSeat seat = new TzSeat();//竞价对象
            TzBid tb = new TzBid();//广告集合
            TzNative tzNative = new TzNative();//信息流内容
            TzVideo tzVideo = new TzVideo();//视频内容
            ArrayList<TzImage> images = new ArrayList<>();//图片集合
            TzImage tzImage = new TzImage();//单个图片

            List<TzSeat> seatbids = bidResponse.getSeatbid();//返回竞价对象集合
            for (int i=0;i<seatbids.size();i++){
                List<TzBid> bids = seatbids.get(i).getBid();
                for (int j=0;j<bids.size();j++){
                    String dsp_id = upper.get("dsp_id").toString();//联盟id
                    String tag_id = upper.get("tag_id").toString();//联盟广告位id

                    //获取天卓返回的素材类型
                    Integer type = bids.get(j).getAd_type();
                    if (type==null){
                        type = 8;//给个默认值
                    }
                    //设置替换素材类型,默认是1图片
                    String ad_type = "1";
                    if (type.equals(8)||type.equals(9)){//信息流
                        if (null!=bids.get(j).getNATIVE()){
                            TzVideo native_video = bids.get(j).getNATIVE().getVideo();//信息流视频素材
                            if (null != native_video){
                                ad_type = "2";//视频是2，图片是1
                            }
                        }
                    }else {//其他
                        TzVideo video = bids.get(j).getVideo();//其他视频素材
                        if (null != video){
                            ad_type = "2";//视频是2，图片是1
                        }
                    }

                    Integer width = bids.get(j).getW();//宽
                    Integer height = bids.get(j).getH();//高
                    String title = bids.get(j).getTitle();//标题
                    String desc = bids.get(j).getDesc();//描述

                    //请求redis,获取上游的广告素材
                    String key = dsp_id+"_"+tag_id+"_"+ad_type+"_"+width+"_"+height;
                    Object adObject = redisUtil.get(key);//根据广告位id从redis中查询数据
                    if (null!=adObject){
                        Map ad = JSONObject.parseObject(adObject.toString().replace("=", ":"), HashMap.class);
                        String ad_url = ad.get("material").toString();//redis返回的替换素材url
//                Integer redis_type = Integer.valueOf(ad.get("type").toString());//redis返回的素材类型
                        Integer status = Integer.valueOf(ad.get("status").toString());//素材替换开关
                        //素材替换开关是1的，代表开，去替换，否则不替换
                        if (status == 1){
                            String keyWord = ad.get("keywords").toString();//关键字
                            String[] keyWords = keyWord.split(",");
                            for (int k=0;k<keyWords.length;k++){
                                //如果标题或者描述包含关键字,则进行关键字替换
                                if (title.contains(keyWords[k])||desc.contains(keyWords[k])) {
                                    //分信息流和其他
                                    if (type==8||type==9){//信息流
                                        //分图片和视频
                                        if (null!=bids.get(j).getNATIVE().getVideo()){//视频
                                            tzVideo.setUrl(ad_url);
                                            tzNative.setVideo(tzVideo);
                                            tb.setNATIVE(tzNative);
                                            bidList.add(tb);//
                                            seat.setBid(bidList);
                                            seatList.add(seat);
                                            bidResponse.setSeatbid(seatList);//广告集合对象
                                        }else {//图片
                                            tzImage.setUrl(ad_url);
                                            images.add(tzImage);
                                            tzNative.setImages(images);
                                            tb.setNATIVE(tzNative);
                                            bidList.add(tb);//
                                            seat.setBid(bidList);
                                            seatList.add(seat);
                                            bidResponse.setSeatbid(seatList);//广告集合对象
                                        }
                                    }else {//其他
                                        //分图片和视频
                                        if (null!=bids.get(j).getVideo()){//视频
                                            tzVideo.setUrl(ad_url);
                                            tb.setVideo(tzVideo);
                                            bidList.add(tb);//
                                            seat.setBid(bidList);
                                            seatList.add(seat);
                                            bidResponse.setSeatbid(seatList);//广告集合对象
                                        }else {//图片
                                            tzImage.setUrl(ad_url);
                                            images.add(tzImage);
                                            tb.setImages(images);
                                            bidList.add(tb);//
                                            seat.setBid(bidList);
                                            seatList.add(seat);
                                            bidResponse.setSeatbid(seatList);//广告集合对象
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return bidResponse;

    }

}
