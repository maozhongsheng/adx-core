package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.WbRtaJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @Author mzs
 * @Description 微博
 * @Date 2021/12/8 13:48
 */
@Slf4j
@Service("wbRtaJsonService")
public class WbRtaJsonServiceImpl implements WbRtaJsonService {

    private static final String name = "wb";
    private static final String source = "微博";

    /**
     * @Author mzs
     * @Description 微博
     * @Date 2021/12/08 13:49
     */
    //    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<TzBidResponse> getWbRtaDataByJson(TzBidRequest request) {
        Map bidRequest = new HashMap();
        Map device = new HashMap();
        Map oth = new HashMap();

        String os = request.getDevice().getOs();//终端操作系统类型:0=>Android,1=>iOS
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {
            device.put("mie", request.getDevice().getImei());
            device.put("oaid",request.getDevice().getOaid());
            device.put("m5mie",request.getDevice().getImei_md5());
            device.put("andid",request.getDevice().getAndroid_id());
            oth.put("op",0);
        } else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.put("idfa",request.getDevice().getIdfa());
            oth.put("op",1);
        }else{
            oth.put("op",2);
        }
        device.put("ip",request.getDevice().getIp()); //
        device.put("mac",request.getDevice().getMac());
        device.put("sh",request.getDevice().getH());
        device.put("sw",request.getDevice().getW());
        device.put("ua",request.getDevice().getUa());
        oth.put("ts",System.currentTimeMillis());
        oth.put("ct",0);

        bidRequest.put("apid",request.getAdv().getApp_id()); //   A000V
        bidRequest.put("asid",request.getAdv().getTag_id()); //  S000S
        bidRequest.put("dev",device);
        bidRequest.put("oth",oth);
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求微博RTA广告参数" + JSONObject.parseObject(content));
        String url = "http://dsp.51ota.cn:8096/rta/"+request.getAdv().getApp_id();
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doWbRTAJsonPost(url, content, request);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求上游微博RTA花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("微博RTA返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            if (200 == JSONObject.parseObject(response).getInteger("code")) {
                List<TzMacros> tzMacros1 = new ArrayList();
                TzMacros tzMacros = new TzMacros();
                List<TzSeat> seatList = new ArrayList<>();
                String id = request.getId();
                //多层解析json
                List<TzBid> bidList = new ArrayList<>();
                TzBidApps tzBidApps = new TzBidApps();
                TzBid tb = new TzBid();
                JSONArray datas = JSONObject.parseObject(response).getJSONArray("data");
                for (int d = 0; d<datas.size(); d++) {
                    JSONObject data = datas.getJSONObject(d);
                    tzBidApps.setBundle(data.getString("pkgname")); //唤醒包名
                    tb.setApp(tzBidApps);
                    String imgurl = data.getString("imgurl"); //图面
                    if ("1".equals(request.getImp().get(0).getAd_slot_type())) {
                        TzNative tzNative = new TzNative();
                        tb.setAd_type(8);//信息流-广告素材类型
                        ArrayList<TzImage> images = new ArrayList<>();
                        if (StringUtils.isNotEmpty(imgurl)) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(imgurl);
                                tzImage.setW(request.getImp().get(0).getNATIVE().getW());
                                tzImage.setH(request.getImp().get(0).getNATIVE().getH());
                                images.add(tzImage);
                        }
                        tzNative.setTitle(data.getString("title"));
                        tzNative.setDesc(data.getString("des"));
                        tzNative.setImages(images);
                        tb.setNATIVE(tzNative);
                    } else {
                        tb.setAd_type(5);//开屏-广告素材类型
                        ArrayList<TzImage> images = new ArrayList<>();
                        if (StringUtils.isNotEmpty(imgurl)) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(imgurl);
                                tzImage.setW(request.getImp().get(0).getBanner().getW());
                                tzImage.setH(request.getImp().get(0).getBanner().getH());
                                images.add(tzImage);
                            tb.setImages(images);
                        }

                        tb.setTitle(data.getString("title"));
                        tb.setDesc(data.getString("des"));
                    }

                    String interactionType = data.getInteger("action").toString();//  1--页面加载 2--下载 3--deeplink 0--其他
                    if ("1".equals(interactionType)) {
                        tb.setClicktype("0");//点击
                    } else if ("3".equals(interactionType)) {
                        tb.setClicktype("2");//唤醒deeplink
                        tb.setDeeplink_url(data.getString("target_url"));
                        List<String> deep_linkS = new ArrayList<>();
                        JSONArray deep = data.getJSONArray("dp_trackers");
                        deep_linkS.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        for (int de = 0; de<deep.size(); de++) {
                            deep_linkS.add(deep.get(de).toString());
                        }
                        String encode = deep.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
                        tb.setCheck_success_deeplinks(deep_linkS);//唤醒成功
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    } else if ("2".equals(interactionType)) {
                        tb.setClicktype("4");//普通下载
                    }else{
                        tb.setClicktype("1");//跳转
                    }


                    String clickurl = data.getString("target_url");
                    if(StringUtils.isNotEmpty(clickurl)){
                        String encode = clickurl + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
                        tb.setClick_url("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/click?clickurl=%%CLICK_URL%%"); // 点击跳转url地址
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CLICK_URL%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);

                    }

                    if(null != data.getJSONArray("imp_trackers")){
                        List<String> check_views = new ArrayList<>();
                        check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pvf?pv=%%CHECK_VIEWS%%");
                        JSONArray urls1 = data.getJSONArray("imp_trackers");
                        for (int cv = 0; cv < urls1.size(); cv++) {
                            check_views.add(urls1.get(cv).toString());
                        }
                        String encode =  urls1.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
                        tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                    if(null != data.getJSONArray("click_trackers")){
                        List<String> clickList = new ArrayList<>();
                        JSONArray urls1 =  data.getJSONArray("click_trackers");
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicksf?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < urls1.size(); cc++) {
                            clickList.add(urls1.get(cc).toString());
                        }
                        String encode = urls1.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }


                    tb.setMacros(tzMacros1);
                    tb.setSource(source);
                    tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);//

                }
                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seatList.add(seat);
                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("微博RTA总返回" + JSONObject.toJSONString(bidResponse));
            }
        }
        return AsyncResult.forValue(bidResponse);
    }
}
