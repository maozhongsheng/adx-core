package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tongzhou.TongZhouApp;
import com.mk.adx.entity.json.request.tongzhou.TongZhouBidRequest;
import com.mk.adx.entity.json.request.tongzhou.TongZhouDevice;
import com.mk.adx.entity.json.request.tongzhou.TongZhouImp;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.tz.TzImp;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.TongZhouJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description
 * @date 2022/5/31 15:21
 */
@Slf4j
@Service("tongZhouJsonService")
public class TongZhouJsonServiceImpl implements TongZhouJsonService {

    private static final String name = "深圳同舟";

    private static final String source = "深圳同舟";

    @SneakyThrows
    @Override
    public TzBidResponse getTongZhouDataByJson(TzBidRequest request) {
        TongZhouBidRequest bidRequest = new TongZhouBidRequest();
        TongZhouApp app = new TongZhouApp();
        TongZhouDevice device = new TongZhouDevice();
        List<TongZhouImp> reqimps = new ArrayList();

        //imp
        List<TzImp> tzimp = request.getImp();
        for (int i = 0; i < tzimp.size(); i++) {
            TongZhouImp reqimp = new TongZhouImp();
            reqimp.setImp_id(tzimp.get(i).getId());
            String slot_type = request.getAdv().getSlot_type();
            if("信息流".equals(slot_type)){
                reqimp.setPos_type(4);
            }else if("开屏".equals(slot_type)){
                reqimp.setPos_type(2);
            }else if("插屏".equals(slot_type)){
                reqimp.setPos_type(3);
            }else if("横幅".equals(slot_type)){
                reqimp.setPos_type(1);
            }else if("激励视频".equals(slot_type)){
                reqimp.setPos_type(5);
            }
            reqimp.setPos_id(request.getAdv().getTag_id());
            reqimp.setPos_width(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
            reqimp.setPos_height(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
            reqimp.setDeeplink(1);
            reqimps.add(reqimp);
        }

        //app
        app.setApp_id(request.getAdv().getApp_id());
        app.setApp_name(request.getAdv().getApp_name());
        app.setApp_package(request.getAdv().getBundle());
        app.setApp_ver(request.getAdv().getVersion());
        //device
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setDevice_type(1);
        }else if("ipad".equals(devicetype)){
            device.setDevice_type(2);
        }else if("pc".equals(devicetype)){
            device.setDevice_type(99);
        }else {
            device.setDevice_type(99);
        }
        device.setMake(request.getDevice().getMake());
        device.setBrand(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs(1);
            device.setImei(request.getDevice().getImei());
            device.setImei_md5(request.getDevice().getImei_md5());
            device.setAndroid_id(request.getDevice().getAndroid_id());
            device.setAndroid_id_md5(request.getDevice().getAndroid_id_md5());
            device.setOaid(request.getDevice().getOaid());
            device.setCaid(request.getDevice().getCaid());
            device.setMac(request.getDevice().getMac());
            device.setMac_md5(request.getDevice().getMac_md5());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs(2);
            device.setIdfa(request.getDevice().getIdfa());
            device.setIdfa_md5(request.getDevice().getIdfa_md5());
            device.setOpenudid(request.getDevice().getOpen_udid());
            device.setIdfv(request.getDevice().getIdfv());
        }else{
            device.setOs(99);
        }
        device.setOsv(request.getDevice().getOsv());
       // device.setOs_api_level();
        device.setScreen_width(request.getDevice().getW());
        device.setScreen_height(request.getDevice().getH());
        device.setDensity(request.getDevice().getDeny());
       // device.setDpi();
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());
        }
        device.setOrientation(1);
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            device.setNetwork(0);
        }else if("1".equals(connectiontype)){
            device.setNetwork(1);
        }else if("2".equals(connectiontype)){
            device.setNetwork(1);
        }else if("3".equals(connectiontype)){
            device.setNetwork(98);
        }else if("4".equals(connectiontype)){
            device.setNetwork(2);
        }else if("5".equals(connectiontype)){
            device.setNetwork(3);
        }else if("6".equals(connectiontype)){
            device.setNetwork(4);
        }else if("7".equals(connectiontype)){
            device.setNetwork(5);
        } else {
            device.setNetwork(99);
        }
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            device.setCarrier(1);
        }else if("70123".equals(carrier)){
            device.setCarrier(2);
        }else if("70121".equals(carrier)){
            device.setCarrier(3);
        }else{
            device.setCarrier(99);
        }
        device.setCountry(request.getDevice().getCountry());
        device.setLanguage(request.getDevice().getLanguage());
        device.setTimezone(request.getDevice().getTimeZone());
        if (null != request.getDevice().getGeo()) {
          device.setGet_longitude(request.getDevice().getGeo().getLon());
          device.setGeo_latitude(request.getDevice().getGeo().getLat());
        }
        device.setWifi_mac(request.getDevice().getMac());
        device.setIp(request.getDevice().getIp());
        device.setUa(request.getDevice().getUa());
        bidRequest.setReq_id(request.getId());
        bidRequest.setImp(reqimps);
        bidRequest.setApp(app);
        bidRequest.setDevice(device);


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求深圳同舟广告参数"+JSONObject.parseObject(content));
        String url = "https://uapi.matrixerse.com/v1/ads";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游深圳同舟广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":深圳同舟广告返回参数"+JSONObject.parseObject(response));

        if (null != response && 0 == JSONObject.parseObject(response).getInteger("error_code")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONArray imp = JSONObject.parseObject(response).getJSONArray("ads");
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
                tb.setId(imp.getJSONObject(i).getString("adid"));
                tb.setImpid(imp.getJSONObject(i).getString("impid"));
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    ArrayList<TzImage> images = new ArrayList<>();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray imgs = imp.getJSONObject(i).getJSONArray("imgs");
                    if(null != imgs){
                        for (int j = 0; j < imgs.size(); j++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(imgs.getString(j));
                            tzImage.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
                            tzImage.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
                            images.add(tzImage);
                        }
                    }

                    TzIcon tzIcon = new TzIcon();
                    tzIcon.setUrl(imp.getJSONObject(i).getString("icon"));
                    tzNative.setIcon(tzIcon);
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("title"))) {
                        tzNative.setTitle(imp.getJSONObject(i).getString("title"));
                    }
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("description"))) {
                        tzNative.setDesc(imp.getJSONObject(i).getString("description"));
                    }
                    tzNative.setImages(images);
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("ad_logo"))){
                        TzLogo tzLogo = new TzLogo();
                        tzLogo.setUrl(imp.getJSONObject(i).getString("ad_logo"));
                        tzNative.setLogo(tzLogo);
                    }
                    tb.setNATIVE(tzNative);

                }else if ("4".equals(request.getImp().get(0).getAd_slot_type())){
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONObject videos = imp.getJSONObject(i).getJSONObject("video");
                    if (null != videos) {
                        video.setUrl(videos.getString("video_url"));
                        video.setW(videos.getInteger("video_width"));
                        video.setH(videos.getInteger("video_height"));
                        video.setDuration(videos.getInteger("duration"));
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(videos.getString("video_cover"));
                        video.setConver_image(tzImage);
                        if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("title"))) {
                            tb.setTitle(imp.getJSONObject(i).getString("title"));
                        }
                        if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("description"))) {
                            tb.setDesc(imp.getJSONObject(i).getString("description"));
                        }
                        tb.setAic(imp.getJSONObject(i).getString("icon"));
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
                    JSONArray imgs = imp.getJSONObject(i).getJSONArray("imgs");
                    if(null != imgs){
                        for (int j = 0; j < imgs.size(); j++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(imgs.getString(j));
                            tzImage.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
                            tzImage.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
                            images.add(tzImage);
                        }
                    }
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("title"))) {
                        tb.setTitle(imp.getJSONObject(i).getString("title"));
                    }
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("description"))) {
                        tb.setDesc(imp.getJSONObject(i).getString("description"));
                    }
                    tb.setAic(imp.getJSONObject(i).getString("icon"));
                    tb.setImages(images);
                }


                Integer interaction_type = imp.getJSONObject(i).getInteger("interaction_type");
                if(1 == interaction_type){
                    tb.setClicktype("0");//跳转
                }else if(2 == interaction_type){
                    tb.setClicktype("1");//下载
                }else if(3 == interaction_type){
                    tb.setClicktype("2");//下载
                }else if(4 == interaction_type){
                    tb.setClicktype("4");//下载
                }
                tb.setClick_url(imp.getJSONObject(i).getString("page_url"));
                tb.setDownload_url(imp.getJSONObject(i).getString("download_url"));
                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink"));
                }

                if (null != imp.getJSONObject(i).getJSONArray("ad_show_urls")) {
                    JSONArray apv = imp.getJSONObject(i).getJSONArray("ad_show_urls");
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

                if (null != imp.getJSONObject(i).getJSONArray("ad_click_urls")) {
                    JSONArray aclick = imp.getJSONObject(i).getJSONArray("ad_click_urls");
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
                if (null != imp.getJSONObject(i).getJSONArray("app_download_urls")) {
                    JSONArray sdtrackers = imp.getJSONObject(i).getJSONArray("app_download_urls");
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
                if (null != imp.getJSONObject(i).getJSONArray("app_downloaded_urls")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("app_downloaded_urls");
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
                if (null != imp.getJSONObject(i).getJSONArray("dp_success_urls")) {
                    JSONArray dptrackers = imp.getJSONObject(i).getJSONArray("dp_success_urls");
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
                if (null != imp.getJSONObject(i).getJSONArray("app_install_urls")) {
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("app_install_urls");
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
                if (null != imp.getJSONObject(i).getJSONArray("app_installed_urls")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("app_installed_urls");
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
                List<TzCheckVideoUrls> videochecks = new ArrayList();
                if (null != imp.getJSONObject(i).getJSONArray("video_start_urls")) {
                    List<String> videoStartUrls = new ArrayList<>();
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("video_start_urls");
                    videoStartUrls.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/vedio/start?vedioStart="+ encode);
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        videoStartUrls.add(urls1.get(ins).toString());
                    }
                    TzCheckVideoUrls tzCheckVideoUrls = new TzCheckVideoUrls();
                    tzCheckVideoUrls.setUrl(videoStartUrls);//视频开始
                    tzCheckVideoUrls.setTime(0);
                    videochecks.add(tzCheckVideoUrls);
                }

                if (null != imp.getJSONObject(i).getJSONArray("video_end_urls")) {
                    List<String> videoEndUrls = new ArrayList<>();
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("app_installed_urls");
                    videoEndUrls.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/vedio/end?vedioEnd="+ encode);
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        videoEndUrls.add(urls1.get(ins).toString());
                    }
                    TzCheckVideoUrls tzCheckVideoUrls = new TzCheckVideoUrls();
                    tzCheckVideoUrls.setUrl(videoEndUrls);//视频结束
                    tzCheckVideoUrls.setTime(1);
                    videochecks.add(tzCheckVideoUrls);
                }
                tb.setCheck_video_urls(videochecks);
                tb.setMacros(tzMacros1);
                bidList.add(tb);
            }
            TzSeat seat = new TzSeat();
            seat.setBid(bidList);
            seatList.add(seat);
            bidResponse.setId(id);//请求id
            bidResponse.setBidid(id);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info(request.getImp().get(0).getTagid()+":深圳同舟总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
