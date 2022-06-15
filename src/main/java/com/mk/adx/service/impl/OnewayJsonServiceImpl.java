package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.oneway.OnewayBidRequest;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.OnewayJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description OneWay
 * @Date 2021/12/28 14:11
 */
@Slf4j
@Service("onewayJsonService")
public class OnewayJsonServiceImpl implements OnewayJsonService {

    private static final String name = "OneWay";

    private static final String source = "万维";

    /**
     * @Author Jny
     * @Description 万维
     * @Date 2021/11/5 17:20
     */
    @SneakyThrows
    @Override
    public TzBidResponse getOnewayDataByJson(TzBidRequest request) {
        OnewayBidRequest bidRequest = new OnewayBidRequest();//oneway总请求
        bidRequest.setApiVersion("1.5.8");//对接 API 版本, 建议使用最新版本
        bidRequest.setPlacementId(request.getAdv().getTag_id());//广告位 ID 由平台分配获取
        bidRequest.setBundleId(request.getAdv().getBundle());//应用包名 eg: com.app.xyx
        bidRequest.setBundleVersion(request.getAdv().getVersion());//应用版本 eg: 2.1.0
        bidRequest.setAppName(request.getAdv().getApp_name());//应用名称
        bidRequest.setSubAffId("tz");//应用子渠道标识
        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            bidRequest.setImei(request.getDevice().getImei());//Android必填
            if(StringUtils.isNotEmpty(request.getDevice().getAndroid_id())){
                bidRequest.setAndroidId(request.getDevice().getAndroid_id());//Android必填
            }else{
                bidRequest.setAndroidId("BEFA1397FB07DE47");//Android必填
            }
            bidRequest.setOaid(request.getDevice().getOaid());//(andorid版本>=10必填) 移动安全联盟 匿名设备标识符
            bidRequest.setApiLevel(30);//Android API level (iOS不填)
            bidRequest.setOs(1);//操作系统 1: Android 2: iOS
            bidRequest.setBootMark("ec7f4f33-411a-47bc-8067-744a4e7e0723");
            bidRequest.setBootMark("1004697.709999999");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            bidRequest.setDeviceId(request.getDevice().getIdfa());//设备广告ID (iOS填IDFA, Android填Google Play services Advertising ID, Android获取不到可不填)
            bidRequest.setOs(2);//操作系统 1: Android 2: iOS
            bidRequest.setBootMark("1623815045.970028");
            bidRequest.setBootMark("1581141691.570419583");
        }
        bidRequest.setOsVersion(request.getDevice().getOsv());
        Integer connectiontype = request.getDevice().getConnectiontype();
        if(2 == connectiontype){
            bidRequest.setConnectionType("wifi");
            bidRequest.setNetworkType(18);
        }else if(4 == connectiontype || 5 == connectiontype || 6 == connectiontype){
            bidRequest.setConnectionType("cellular");
            bidRequest.setNetworkType(13);
        }else if(7 == connectiontype) {
            bidRequest.setConnectionType("cellular");
            bidRequest.setNetworkType(20);
        }else{
            bidRequest.setConnectionType("none");
            bidRequest.setNetworkType(13);
        }
        bidRequest.setNetworkOperator("46000");
        bidRequest.setSimOperator("46000");
        bidRequest.setImsi("460001357924680");
        bidRequest.setDeviceMake(request.getDevice().getMake());
        bidRequest.setDeviceModel(request.getDevice().getModel());
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            bidRequest.setDeviceType(1);
        }else if("ipad".equals(devicetype)){
            bidRequest.setDeviceType(2);
        }else if("pc".equals(devicetype)){
            bidRequest.setDeviceType(4);
        }else if("tv".equals(devicetype)){
            bidRequest.setDeviceType(3);
        }
        bidRequest.setOrientation("V");
        bidRequest.setMac(request.getDevice().getMac());
        //bidRequest.setWifiBSSID();
        //bidRequest.setWifiSSID();
        bidRequest.setScreenWidth(request.getDevice().getW());
        bidRequest.setScreenHeight(request.getDevice().getH());
//        BigDecimal bigDecimal = new BigDecimal(request.getDevice().getDeny());
//        BigDecimal b2 = new BigDecimal(400);
//        bidRequest.setScreenDensity(bigDecimal.add(b2).intValue());
//        if(null != request.getDevice().getPpi()){
//            BigDecimal inch1 = new BigDecimal(request.getDevice().getPpi());
//            BigDecimal inch2 = new BigDecimal(4.5);
//            bidRequest.setScreenInch(inch1.add(inch2).floatValue());
//        }
        bidRequest.setUserAgent(request.getDevice().getUa());
        bidRequest.setIp(request.getDevice().getIp());
        bidRequest.setLanguage("zh_CN");
        bidRequest.setTimeZone("GMT+08:00");
        bidRequest.setLongitude(request.getDevice().getGeo().getLon());
        bidRequest.setLatitude(request.getDevice().getGeo().getLat());



        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求万维广告参数"+JSONObject.parseObject(content));
        String url = "https://ads.oneway.mobi/getCampaign?publishId=" + request.getAdv().getApp_id().split(",")[0] + "&token=" + request.getAdv().getApp_id().split(",")[1] + "&ts="+System.currentTimeMillis();
        String ua = request.getDevice().getUa();
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doOnwWayJsonPost(url,content,ua);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求上游万维广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("万维广告返回参数"+JSONObject.parseObject(response));
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            if (jo.getBoolean("success") == true) {
                List<TzMacros> tzMacros1 = new ArrayList();
                TzMacros tzMacros = new TzMacros();
                List<TzSeat> seatList = new ArrayList<>();
                String id = request.getId();
                //多层解析json
                // String msg = jo.getString("msg");//广告响应说明
                JSONObject imp = jo.getJSONObject("data");
                List<TzBid> bidList = new ArrayList<>();
                TzBid tb = new TzBid();
                TzVideo tzVideo = new TzVideo();
                tb.setId(imp.getString("sessionId"));
                TzIcon tzIcon = new TzIcon();
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray owImages =  imp.getJSONArray("images");
                    ArrayList<TzImage> images = new ArrayList<>();
                    if(null != owImages) {
                        for (int im = 0; im < owImages.size(); im++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(owImages.getJSONObject(im).getString("url"));
                            tzImage.setH(owImages.getJSONObject(im).getInteger("h"));
                            tzImage.setW(owImages.getJSONObject(im).getInteger("w"));
                            images.add(tzImage);
                        }
                        tzNative.setTitle(imp.getString("title"));
                        tzNative.setImages(images);
                    }
                    tzIcon.setUrl(imp.getString("appIcon"));
                    // 信息流视频
                    JSONObject video = imp.getJSONObject("video");
                    if(null != video){
                        TzImage tzImage = new TzImage();
                        tzVideo.setUrl(video.getString("url"));
                        tzVideo.setDuration(Math.round(video.getFloat("duration")));
                        tzImage.setUrl(video.getString("coverUrl"));
                        tzVideo.setConver_image(tzImage);
                        tzNative.setVideo(tzVideo);
                    }
                    tb.setNATIVE(tzNative);
                }else {
                    tb.setTitle(imp.getString("title"));
                    tb.setAd_type(2);//开屏-广告素材类型
                    JSONArray owImages =  imp.getJSONArray("images");
                    ArrayList<TzImage> images = new ArrayList<>();
                    if(null != owImages) {
                        for (int im = 0; im < owImages.size(); im++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(owImages.getJSONObject(im).getString("url"));
                            tzImage.setH(owImages.getJSONObject(im).getInteger("h"));
                            tzImage.setW(owImages.getJSONObject(im).getInteger("w"));
                            images.add(tzImage);
                        }
                        tb.setImages(images);
                    }
                    tb.setAic(imp.getString("appIcon"));

                    // 开屏视频
                    JSONObject video = imp.getJSONObject("video");
                    if (null != video) {
                        TzImage tzImage = new TzImage();
                        tzVideo.setUrl(video.getString("url"));
                        tzVideo.setDuration(Math.round(video.getFloat("duration")));
                        tzImage.setUrl(video.getString("coverUrl"));
                        tzVideo.setConver_image(tzImage);
                        tb.setVideo(tzVideo);//视频素材
                    }
                }



                TzBidApps tzBidApps = new TzBidApps();
                tzBidApps.setBundle(imp.getString("appStoreId"));
                tzBidApps.setApp_name(imp.getString("appName"));
                tzBidApps.setApp_icon(imp.getString("appIcon"));
                tb.setApp(tzBidApps);

                Integer action = imp.getInteger("interactionType");
                if(0 == action){
                    tb.setClicktype("0");//点击
                }else if(1 == action){
                    tb.setClicktype("1");//跳转
                }else if(2 == action){
                    tb.setClicktype("4");//普通下载
                }else if(3 == action){
                    tb.setClicktype("3");//广点通
                }else{
                    tb.setClicktype("0");//点击
                }
//            String orientation = imp.getString("orientation");//推广应用素材横竖屏方向
//            Integer clickMode = imp.getInteger("clickMode");//点击模式 0: 默认(落地页下载按钮可点) 1: 落地页全屏可点 2: 播放过程中全屏可点，落地页全屏可点
//            Integer skipSecondss = imp.getInteger("skipSecondss"); //秒数, 显示多少秒之后才可跳过
//            Integer autoLanding = imp.getInteger("autoLanding"); //是否是自动跳转; 如果为true 则视频播放完成自动跳转clickUrl
//            Integer ignoreCMacro = imp.getInteger("ignoreCMacro");//是否忽略点击宏参数替换
//            Integer endhtml = imp.getInteger("endhtml");//如果有返回值且autoLanding=false 则视频播放完成加载该html
//            Integer expirationTime = imp.getInteger("expirationTime");//广告有效时间(单位: 秒), 过期判定为无效上报

                tb.setDeeplink_url(imp.getString("deeplink"));
                if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                    tb.setClick_url(imp.getString("clickUrl").replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%")); // 点击跳转url地址
                }else{
                    tb.setClick_url(imp.getString("clickUrl").replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%")); // 点击跳转url地址
                }




                JSONObject eventTracks = imp.getJSONObject("trackingEvents");

                if(null != eventTracks.getJSONArray("dp")) {
                    List<String> deep_linkT = new ArrayList<>();
                    deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                    JSONArray urls1 = eventTracks.getJSONArray("dp");
                    for (int dp = 0; dp < urls1.size(); dp++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            deep_linkT.add(urls1.get(dp).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            deep_linkT.add(urls1.get(dp).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_success_deeplinks(deep_linkT);//唤醒成功
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DEEP_LINK%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(null != eventTracks.getJSONArray("dpFail")) {
                    List<String> deep_linkT = new ArrayList<>();
                    deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_FAIL%%");
                    JSONArray urls1 = eventTracks.getJSONArray("dpFail");
                    for (int dp = 0; dp < urls1.size(); dp++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            deep_linkT.add(urls1.get(dp).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            deep_linkT.add(urls1.get(dp).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_success_deeplinks(deep_linkT);//唤醒失败
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DEEP_LINK_FAIL%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(null != eventTracks.getJSONArray("show")) {
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    JSONArray urls1 = eventTracks.getJSONArray("show");
                    for (int cv = 0; cv < urls1.size(); cv++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            check_views.add(urls1.get(cv).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            check_views.add(urls1.get(cv).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }
                    }
                    String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(null != eventTracks.getJSONArray("click")) {
                    List<String> clickList = new ArrayList<>();
                    JSONArray urls1 =  eventTracks.getJSONArray("click");
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < urls1.size(); cc++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            clickList.add(urls1.get(cc).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            clickList.add(urls1.get(cc).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }

                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

//            if(null != imp.getJSONObject(i).getJSONArray("starttk")){
//                List<String> voidStartList = new ArrayList<>();
//                JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("starttk");
//                voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
//                for (int vs = 0; vs < urls1.size(); vs++) {
//                    voidStartList.add(urls1.get(vs).toString());
//                }
//                String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                //    tb.setCheck_views(voidStartList);//视频开始播放
//                tzMacros = new TzMacros();
//                tzMacros.setMacro("%%VEDIO_START%%");
//                tzMacros.setValue(Base64.encode(encode));
//                tzMacros1.add(tzMacros);
//            }
//
//
//            if(null != imp.getJSONObject(i).getJSONArray("endtk")){
//                List<String> voidEndList = new ArrayList<>();
//                JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("endtk");
//                voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
//                for (int ve = 0; ve < urls1.size(); ve++) {
//                    voidEndList.add(urls1.get(ve).toString());
//                }
//                String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                //    tb.setCheck_clicks(voidEndList);//视频播放结束
//                tzMacros = new TzMacros();
//                tzMacros.setMacro("%%VEDIO_END%%");
//                tzMacros.setValue(Base64.encode(encode));
//                tzMacros1.add(tzMacros);
//            }

                if(null != eventTracks.getJSONArray("apkDownloadStart")) {
                    List<String> downLoadList = new ArrayList<>();
                    JSONArray urls1 =  eventTracks.getJSONArray("apkDownloadStart");
                    downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                    for (int dl = 0; dl < urls1.size(); dl++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            downLoadList.add(urls1.get(dl).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            downLoadList.add(urls1.get(dl).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_downloads(downLoadList);//开始下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_LOAD%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(null != eventTracks.getJSONArray("apkDownloadFinish")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = eventTracks.getJSONArray("apkDownloadFinish");
                    downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                    for (int dle = 0; dle < urls1.size(); dle++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            downLoadDList.add(urls1.get(dle).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            downLoadDList.add(urls1.get(dle).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_end_downloads(downLoadDList);//结束下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
//            if(null != eventTracks.getJSONArray("is")) {
//                List<String> installList = new ArrayList<>();
//                JSONArray urls1 =  eventTracks.getJSONArray("is");
//                installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
//                for (int ins = 0; ins < urls1.size(); ins++) {
//                    installList.add(urls1.get(ins).toString());
//                }
//                String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                tb.setCheck_start_installs(installList);//开始安装
//                tzMacros = new TzMacros();
//                tzMacros.setMacro("%%INSTALL_START%%");
//                tzMacros.setValue(Base64.encode(encode));
//                tzMacros1.add(tzMacros);
//            }
                if(null != eventTracks.getJSONArray("packageAdded")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = eventTracks.getJSONArray("packageAdded");
                    installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        if(null != imp.getBoolean("ignoreCMacro") && imp.getBoolean("ignoreCMacro")){
                            installEList.add(urls1.get(ins).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%"));
                        }else{
                            installEList.add(urls1.get(ins).toString().replace("__TIMESTAMP__","%%TS%%").replace("__C_DOWN_X__","%%ABS_DOWN_X%%").replace("__C_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__C_UP_X__","%%ABS_UP_X%%").replace("__C_UP_Y__","%%ABS_UP_Y%%").replace("__C_DOWN_OFFSET_X__","%%DOWN_X%%").replace("__C_DOWN_OFFSET_Y__","%%DOWN_Y%%").replace("__C_UP_OFFSET_X__","%%UP_X%%").replace("__C_UP_OFFSET_Y__","%%UP_Y%%").replace("__WIDTH__","%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%"));
                        }
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_end_installs(installEList);//安装完成
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }


                tb.setMacros(tzMacros1);
                tb.setSource(source);
                tb.setImpid(request.getImp().get(0).getId());
                bidList.add(tb);//

                TzSeat seat = new TzSeat();
                seat.setBid(bidList);
                seatList.add(seat);
                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("万维广告总返回"+JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }
}
