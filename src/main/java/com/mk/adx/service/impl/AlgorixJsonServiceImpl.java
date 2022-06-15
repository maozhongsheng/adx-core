package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.algorix.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.AlgorixJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description
 * @date 2022/4/7 15:21
 */
@Slf4j
@Service("algorixJsonService")
public class AlgorixJsonServiceImpl implements AlgorixJsonService {

    private static final String name = "algorix";

    private static final String source = "algorix";

    @SneakyThrows
    @Override
    public TzBidResponse getAlgorixDataByJson(TzBidRequest request) {
        AlgorixBidRequest bidRequest = new AlgorixBidRequest();
        AlgorixDevice device = new AlgorixDevice();
        AlgorixUser user = new AlgorixUser();
        AlgorixApp app = new AlgorixApp();
        AlgorixExt ext = new AlgorixExt();
        AlgorixDeviceExt deviceExt = new AlgorixDeviceExt();
        AlgorixGeo geo = new AlgorixGeo();
        AlgorixImp algorixImp = new AlgorixImp();
        List<AlgorixImp> imps = new ArrayList();

        //imp
        algorixImp.setId("1");
        algorixImp.setTagid(request.getAdv().getTag_id()); //
        algorixImp.setSecure(1);
        //app
        app.setId(request.getAdv().getApp_id()); //
        app.setName(request.getAdv().getApp_name());
        app.setVer(request.getAdv().getVersion());
        //app ext
        ext.setSupportdeeplink(1);
        //device
        device.setUa(request.getDevice().getUa());
        device.setIp(request.getDevice().getIp());


        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            device.setCarrier("ChinaMobile");
        }else if("70123".equals(carrier)){
            device.setCarrier("ChinaUnicom");
        }else if("70121".equals(carrier)){
            device.setCarrier("ChinaTelecom");
        }else{
            device.setCarrier("unknown");
        }


        device.setMake(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs("Android");
            device.setDidsha1(request.getDevice().getImei_sha1());
            device.setDidmd5(request.getDevice().getImei_md5());
            device.setDpidmd5(request.getDevice().getAndroid_id_md5());
            device.setDpidsha1(request.getDevice().getAndroid_id_sha1());
            deviceExt.setOaid(request.getDevice().getOaid());
            deviceExt.setImei(request.getDevice().getImei());
            deviceExt.setAndroidid(request.getDevice().getAndroid_id());
            app.setBundle(request.getAdv().getBundle());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs("iOS");
            device.setIfa(request.getDevice().getIdfa());
            deviceExt.setIdfv(request.getDevice().getIdfv());
            app.setBundle(request.getAdv().getBundle());
        }
        device.setOsv(request.getDevice().getOsv());
        device.setHwv(request.getDevice().getHwv());
        device.setW(request.getDevice().getW());
        device.setH(request.getDevice().getH());
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());
        }else{
            device.setPpi(0);
        }
        device.setPxratio(request.getDevice().getPxratio());

        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            device.setConnectiontype(0);
        }else if("1".equals(connectiontype)){
            device.setConnectiontype(1);
        }else if("2".equals(connectiontype)){
            device.setConnectiontype(2);
        }else if("3".equals(connectiontype)){
            device.setConnectiontype(3);
        }else if("4".equals(connectiontype)){
            device.setConnectiontype(4);
        }else if("5".equals(connectiontype)){
            device.setConnectiontype(5);
        }else if("6".equals(connectiontype)){
            device.setConnectiontype(6);
        }else if("7".equals(connectiontype)){
            device.setConnectiontype(7);
        } else {
            device.setConnectiontype(2);
        }



        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setDevicetype(4);
        }else if("ipad".equals(devicetype)){
            device.setDevicetype(5);
        }else if("pc".equals(devicetype)){
            device.setDevicetype(2);
        }else {
            device.setDevicetype(0);
        }

        device.setMacmd5(request.getDevice().getMac_md5());
        device.setMacsha1(request.getDevice().getMac_sha1());
        device.setMccmnc(request.getDevice().getMccmnc());

        if(null != request.getDevice().getGeo()){
            geo.setLat(request.getDevice().getGeo().getLat());
            geo.setLon(request.getDevice().getGeo().getLon());
            geo.setCountry("CHN");
        }
        deviceExt.setMac(request.getDevice().getMac());
        deviceExt.setScreenorientation("1");
        deviceExt.setBootmark("");
        deviceExt.setUpgrademark("");

        user.setId("");
        user.setYob(1);
        user.setGender("");


        imps.add(algorixImp);
        app.setExt(ext);
        device.setExt(deviceExt);
        device.setGeo(geo);

        bidRequest.setId(request.getId());
        bidRequest.setImp(imps);
        bidRequest.setApp(app);
        bidRequest.setDevice(device);
        bidRequest.setUser(user);
        bidRequest.setTmax(500);


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求algorix广告参数"+JSONObject.parseObject(content));
        String url = "https://xyz.svr-algorix.cn/rtb/cn?sid=102487";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游algorix广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":algorix广告返回参数"+JSONObject.parseObject(response));

        if (null != response && null != JSONObject.parseObject(response).getJSONArray("seatbid")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONArray bid = JSONObject.parseObject(response).getJSONArray("seatbid");
            JSONArray imp = bid.getJSONObject(0).getJSONArray("bid");
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
                tb.setId(bid.getJSONObject(0).getString("seat"));

                JSONObject adm = JSONObject.parseObject(imp.getJSONObject(i).getString("adm"));
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    ArrayList<TzImage> images = new ArrayList<>();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray navives = adm.getJSONObject("native").getJSONArray("assets");
                    for (int na = 0; na < navives.size(); na++) {
                        if(null != navives.getJSONObject(na).getJSONObject("img")) {
                            if (3 == navives.getJSONObject(na).getJSONObject("img").getInteger("type")) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(navives.getJSONObject(na).getJSONObject("img").getString("url"));
                                tzImage.setH(navives.getJSONObject(na).getJSONObject("img").getInteger("h"));
                                tzImage.setW(navives.getJSONObject(na).getJSONObject("img").getInteger("w"));
                                images.add(tzImage);
                            } else {
                                TzIcon tzIcon = new TzIcon();
                                tzIcon.setUrl(navives.getJSONObject(na).getJSONObject("img").getString("url"));
                                tzIcon.setW(navives.getJSONObject(na).getJSONObject("img").getInteger("w"));
                                tzIcon.setH(navives.getJSONObject(na).getJSONObject("img").getInteger("h"));
                                tzNative.setIcon(tzIcon);
                            }
                        }
                        if(null != navives.getJSONObject(na).getJSONObject("title")) {
                            tzNative.setTitle(navives.getJSONObject(na).getJSONObject("title").getString("text"));
                        }
                        if(null != navives.getJSONObject(na).getJSONObject("data")) {
                            tzNative.setDesc(navives.getJSONObject(na).getJSONObject("data").getString("value"));
                        }
                    }
                    tzNative.setImages(images);
                    tb.setNATIVE(tzNative);

                }else if ("4".equals(request.getImp().get(0).getAd_slot_type())){
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONObject videos = adm.getJSONObject("video");
                    if (null != videos) {
                        video.setUrl(videos.getString("url"));
                        video.setH(videos.getInteger("h"));
                        video.setW(videos.getInteger("w"));
                        video.setDuration(videos.getInteger("duration"));
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(videos.getString("main"));
                        video.setConver_image(tzImage);
                        tb.setTitle(videos.getString("title"));
                        tb.setDesc(videos.getString("desc"));
                        tb.setAic(videos.getString("icon"));
                        tb.setVideo(video);//视频素材
                    }

                }else{
                    ArrayList<TzImage> images = new ArrayList<>();
                    String ad_slot_type = request.getImp().get(0).getAd_slot_type();
                    if("2".equals(ad_slot_type)){
                        tb.setAd_type(0);//广告素材类型
                    }else if("3".equals(ad_slot_type)){
                        tb.setAd_type(5);//广告素材类型
                    }else if("5".equals(ad_slot_type)){
                        tb.setAd_type(0);//广告素材类型
                    }else if("6".equals(ad_slot_type)){
                        tb.setAd_type(3);//广告素材类型
                    }
                    TzImage tzImage = new TzImage();
                    tzImage.setUrl(imp.getJSONObject(i).getString("iurl"));
                    tzImage.setH(imp.getJSONObject(i).getInteger("h"));
                    tzImage.setW(imp.getJSONObject(i).getInteger("w"));
                    images.add(tzImage);
                    tb.setImages(images);
                }


                JSONObject bext = imp.getJSONObject(i).getJSONObject("ext");
                Integer actiontype = bext.getInteger("actiontype");
                if(1 == actiontype){
                    tb.setClicktype("1");//跳转
                    tb.setClick_url(bext.getString("clickurl")); // 点击跳转url地址
                }else{
                    tb.setClicktype("4");//下载
                    tb.setDownload_url(bext.getString("clickurl")); // 下载url地址
                }

                if(StringUtils.isNotEmpty(bext.getString("deeplink"))){
                    tb.setDeeplink_url(bext.getString("deeplink"));
                }

                if (null != bext.getJSONArray("imptrackers")) {
                    JSONArray apv = bext.getJSONArray("imptrackers");
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    for (int cv = 0; cv < apv.size(); cv++) {
                        String replace = apv.get(cv).toString().replace("__TS__", "%%TS%%");
                        check_views.add(replace);
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if (null != bext.getJSONArray("clicktrackers")) {
                    JSONArray aclick = bext.getJSONArray("clicktrackers");
                    List<String> clickList = new ArrayList<>();
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < aclick.size(); cc++) {
                        String replace = aclick.get(cc).toString().replace("__WIDTH__", "%%WIDTH%%").replace("__HEIGHT__", "%%HEIGHT%%").replace("__DOWN_X__", "%%DOWN_X%%").replace("__DOWN_Y__", "%%DOWN_Y%%").replace("__UP_X__", "%%UP_X%%").replace("__UP_Y__", "%%UP_Y%%").replace("__TS__ ", "%%TS%%");
                        clickList.add(replace);
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                }
                if (null != bext.getJSONArray("sdtrackers")) {
                    JSONArray sdtrackers = bext.getJSONArray("sdtrackers");
                    List<String> downLoadList = new ArrayList<>();
                    downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                    for (int dl = 0; dl < sdtrackers.size(); dl++) {
                        downLoadList.add(sdtrackers.get(dl).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_downloads(downLoadList);//开始下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_LOAD%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                }
                if (null != bext.getJSONArray("dptrackers")) {
                    JSONArray dptrackers = bext.getJSONArray("dptrackers");
                    List<String> deep_linkT = new ArrayList<>();
                    deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                    for (int dp = 0; dp < dptrackers.size(); dp++) {
                        deep_linkT.add(dptrackers.get(dp).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_success_deeplinks(deep_linkT);//deeplink调起
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DEEP_LINK%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                }
                if (null != bext.getJSONArray("fdtrackers")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("fdtrackers");
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
//                if (null != bext.getJSONArray("fitrackers")) {
//                    List<String> installList = new ArrayList<>();
//                    JSONArray urls1 = bext.getJSONArray("fitrackers");
//                    installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
//                    for (int ins = 0; ins < urls1.size(); ins++) {
//                        installList.add(urls1.get(ins).toString());
//                    }
//                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
//                    tb.setCheck_start_installs(installList);//开始安装
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%INSTALL_START%%");
//                    tzMacros.setValue(Base64.encode(encode));
//                    tzMacros1.add(tzMacros);
//                }
                if (null != bext.getJSONArray("fitrackers")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("fitrackers");
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
            log.info(request.getImp().get(0).getTagid()+":algorix总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
