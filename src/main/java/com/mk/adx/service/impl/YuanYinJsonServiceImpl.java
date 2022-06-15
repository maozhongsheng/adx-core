package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.yuanyin.*;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.YuanYinJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 创典
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("YuanYinJsonService")
public class YuanYinJsonServiceImpl implements YuanYinJsonService {

    private static final String name = "yy";

    private static final String source = "缘音";

    /**
     * @Author mzs
     * @Description 缘音
     * @Date 2021/12/17 10:30
     */
    @SneakyThrows
    @Override
    public TzBidResponse getYuanYinDataByJson(TzBidRequest request) {
        YyBidRequest bidRequest = new YyBidRequest();
        YyApp app = new YyApp();
        YyDevice device = new YyDevice();
        YyNetWork netWork = new YyNetWork();
        YyProfile profile = new YyProfile();

        //应⽤具体信息 App
        app.setAppName(request.getAdv().getApp_name());
        app.setPackageName(request.getAdv().getBundle());
        app.setAppVersion(request.getAdv().getVersion());
        app.setDpSupport(1);
        app.setVideoSupport(1);
        app.setContentSupport(1);


        //设备具体信息 Device
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs("Android");
            device.setOaid(request.getDevice().getOaid());
            device.setImei(request.getDevice().getImei());
            device.setMd5Imei(request.getDevice().getImei_md5());
            device.setAndroidId(request.getDevice().getAndroid_id());
            device.setMd5AndroidId(request.getDevice().getAndroid_id_md5());
            device.setImsi("");
            app.setStoreUrl("应⽤宝");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs("iOS");
            device.setIdfa(request.getDevice().getIdfa());
            device.setMd5Idfa(request.getDevice().getIdfa_md5());
            app.setStoreUrl("AppStore");
        }
        device.setOv(request.getDevice().getOsv());
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setDType(0);
        }else if("ipad".equals(devicetype)){
            device.setDType(1);
        }else{
            device.setDType(2);
        }

        device.setBrand(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        device.setVendor(request.getDevice().getMake());
        device.setMac(request.getDevice().getMac());
        device.setUa(request.getDevice().getUa());
        device.setScreenWidth(request.getDevice().getW());
        device.setScreenHeight(request.getDevice().getH());
        device.setScreenOrientation(1);
        device.setBootMark("");
        device.setUpdateMark("");
        device.setDpi(request.getDevice().getPpi());
        device.setDensity(request.getDevice().getDeny());
        device.setApiLevel(21);


        //⽹络具体信息 Network
        String carrier = request.getDevice().getCarrier();
        if(StringUtils.isNotEmpty(carrier)){//运营商类型
            if("70120".equals(carrier)){
                netWork.setOperatorType(1);
            }else if("70123".equals(carrier)){
                netWork.setOperatorType(2);
            }else if("70121".equals(carrier)){
                netWork.setOperatorType(3);
            }else{
                netWork.setOperatorType(2);
            }
        }else{
            netWork.setOperatorType(1);
        }
        String connectiontype = request.getDevice().getConnectiontype().toString();//网络连接类型
        if("0".equals(connectiontype)){
            netWork.setConnectionType(0);
        }else if("2".equals(connectiontype)){
            netWork.setConnectionType(1);
        }else if("4".equals(connectiontype)){
            netWork.setConnectionType(2);
        }else if("5".equals(connectiontype)){
            netWork.setConnectionType(3);
        }else if("6".equals(connectiontype)){
            netWork.setConnectionType(4);
        }else if("7".equals(connectiontype)){
            netWork.setConnectionType(5);
        } else {
            netWork.setConnectionType(0);
        }
        netWork.setLon(request.getDevice().getGeo().getLon());
        netWork.setLat(request.getDevice().getGeo().getLat());
        netWork.setClientIp(request.getDevice().getIp());
        //⽤户具体信息 Profile


        bidRequest.setReqId(request.getId());
        bidRequest.setApiVer("1.0");
        bidRequest.setMediaId(request.getAdv().getApp_id()); //
        bidRequest.setPosId(request.getAdv().getTag_id()); //
        bidRequest.setApp(app);
        bidRequest.setDevice(device);
        bidRequest.setNetwork(netWork);
        bidRequest.setProfile(profile);



        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求缘音广告参数"+JSONObject.parseObject(content));
        String url = "http://ssp.weriseapp.com/frontend/ad/get";
        String ua = request.getDevice().getUa();
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求上游缘音广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("缘音广告返回参数"+JSONObject.parseObject(response));
        if(null != JSONObject.parseObject(response)){
        if (0 < JSONObject.parseArray(JSONObject.parseObject(response).get("adInfos").toString()).size()) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
           // String msg = jo.getString("msg");//广告响应说明
            JSONArray imp = JSONObject.parseArray(jo.get("adInfos").toString());
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
                TzVideo tzVideo = new TzVideo();
                tb.setId(imp.getJSONObject(i).getString("impid"));
                JSONObject material = imp.getJSONObject(i).getJSONObject("material");
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray yyImages =  material.getJSONArray("imgList");
                    ArrayList<TzImage> images = new ArrayList<>();
                    if(null != yyImages) {
                        for (int im = 0; im < yyImages.size(); im++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(yyImages.getString(im));
                            tzImage.setH(material.getInteger("materialHeight"));
                            tzImage.setW(material.getInteger("materialWidth"));
                            images.add(tzImage);
                        }
                        tzNative.setTitle(material.getString("title"));
                        tzNative.setDesc(material.getString("desc"));
                        tzNative.setImages(images);
                    }
                    // 信息流视频
                    JSONObject video = material.getJSONObject("video");
                    if(null != video){
                        tzVideo.setUrl(imp.getJSONObject(i).getString("vurl"));
                        tzVideo.setH(material.getInteger("materialHeight"));
                        tzVideo.setW(material.getInteger("materialWidth"));
                        tzVideo.setDuration(imp.getJSONObject(i).getInteger("duration"));
                        tzNative.setVideo(tzVideo);
                    }
                    tb.setNATIVE(tzNative);
                }else {
                    tb.setTitle(material.getString("title"));
                    tb.setDesc(material.getString("desc"));
                    tb.setAd_type(2);//开屏-广告素材类型
                    JSONArray yyImages =  material.getJSONArray("imgList");
                    ArrayList<TzImage> images = new ArrayList<>();
                    if(null != yyImages) {
                        for (int yyim = 0; yyim < yyImages.size(); yyim++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(yyImages.getString(yyim));
                            tzImage.setH(material.getInteger("materialHeight"));
                            tzImage.setW(material.getInteger("materialWidth"));
                            images.add(tzImage);
                        }
                        tb.setImages(images);
                    }

                    // 开屏视频
                    JSONObject video = material.getJSONObject("video");
                    if (null != video) {
                        tzVideo.setUrl(imp.getJSONObject(i).getString("vurl"));
                        tzVideo.setH(material.getInteger("materialHeight"));
                        tzVideo.setW(material.getInteger("materialWidth"));
                        tzVideo.setDuration(imp.getJSONObject(i).getInteger("duration"));
                        tb.setVideo(tzVideo);//视频素材
                    }
                }


                TzBidApps tzBidApps = new TzBidApps();
                tzBidApps.setBundle(material.getString("packageName"));
                tzBidApps.setApp_name(material.getString("appName"));
                tzBidApps.setApp_size(material.getInteger("appSize"));
                tb.setApp(tzBidApps);

                Integer action = imp.getJSONObject(i).getInteger("adAction");//⼴告点击交互类型，1为下载, 2为打开落地页
                if(1 == action){
                    tb.setClicktype("3");//下载
                }else if(2 == action){
                    tb.setClicktype("1");//跳转
                }else{
                    tb.setClicktype("0");//点击
                }
                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink"));
                }
                tb.setClick_url(imp.getJSONObject(i).getString("landingUrl")); // 点击跳转url地址
                JSONObject eventTracks = imp.getJSONObject(i).getJSONObject("eventTracks");

                if(0 < eventTracks.getJSONArray("dp").size()) {
                    List<String> deep_linkT = new ArrayList<>();
                    deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                    JSONArray urls1 = eventTracks.getJSONArray("dp");
                    for (int dp = 0; dp < urls1.size(); dp++) {
                        deep_linkT.add(urls1.get(dp).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_success_deeplinks(deep_linkT);//唤醒成功
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DEEP_LINK%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(0 < eventTracks.getJSONArray("imp").size()) {
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    JSONArray urls1 = eventTracks.getJSONArray("imp");
                    for (int cv = 0; cv < urls1.size(); cv++) {
                        check_views.add(urls1.get(cv).toString());
                    }
                    String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(0 < eventTracks.getJSONArray("clk").size()) {
                    List<String> clickList = new ArrayList<>();
                    JSONArray urls1 =  eventTracks.getJSONArray("clk");
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < urls1.size(); cc++) {
                        String replace = urls1.get(cc).toString().replace("__CLICK_DOWN_X__", "%%DOWN_X%%").replace("__CLICK_DOWN_Y__", "%%DOWN_Y%%").replace("__CLICK_UP_X__", "%%UP_X%%").replace("__CLICK_UP_Y_", "%%UP_Y%%").replace("__WIDTH__", "%%WIDTH%%").replace("__HEIGHT__", "%%HEIGHT%%");
                        clickList.add(replace);
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

//                if(null != imp.getJSONObject(i).getJSONArray("starttk")){
//                    List<String> voidStartList = new ArrayList<>();
//                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("starttk");
//                    voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
//                    for (int vs = 0; vs < urls1.size(); vs++) {
//                        voidStartList.add(urls1.get(vs).toString());
//                    }
//                    String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                    //    tb.setCheck_views(voidStartList);//视频开始播放
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%VEDIO_START%%");
//                    tzMacros.setValue(Base64.encode(encode));
//                    tzMacros1.add(tzMacros);
//                }
//
//
//                if(null != imp.getJSONObject(i).getJSONArray("endtk")){
//                    List<String> voidEndList = new ArrayList<>();
//                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("endtk");
//                    voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
//                    for (int ve = 0; ve < urls1.size(); ve++) {
//                        voidEndList.add(urls1.get(ve).toString());
//                    }
//                    String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                    //    tb.setCheck_clicks(voidEndList);//视频播放结束
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%VEDIO_END%%");
//                    tzMacros.setValue(Base64.encode(encode));
//                    tzMacros1.add(tzMacros);
//                }

                if(0 < eventTracks.getJSONArray("ds").size()) {
                    List<String> downLoadList = new ArrayList<>();
                    JSONArray urls1 =  eventTracks.getJSONArray("ds");
                    downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                    for (int dl = 0; dl < urls1.size(); dl++) {
                        downLoadList.add(urls1.get(dl).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_downloads(downLoadList);//开始下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_LOAD%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(0 < eventTracks.getJSONArray("dc").size()) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = eventTracks.getJSONArray("dc");
                    downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                    for (int dle = 0; dle < urls1.size(); dle++) {
                        downLoadDList.add(urls1.get(dle).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_end_downloads(downLoadDList);//结束下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(0 < eventTracks.getJSONArray("is").size()) {
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 =  eventTracks.getJSONArray("is");
                    installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        installList.add(urls1.get(ins).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_installs(installList);//开始安装
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%INSTALL_START%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(0 < eventTracks.getJSONArray("ic").size()) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = eventTracks.getJSONArray("ic");
                    installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        installEList.add(urls1.get(ins).toString());
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
            }
            TzSeat seat = new TzSeat();
            seat.setBid(bidList);
            seatList.add(seat);
            bidResponse.setId(id);//请求id
            bidResponse.setBidid(id);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info("缘音广告总返回"+JSONObject.toJSONString(bidResponse));
        }
    }
        return bidResponse;
    }
}
