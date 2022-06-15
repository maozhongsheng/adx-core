package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.ruidi.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.RuiDiJsonService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description
 * @date 2022/4/25 15:21
 */
@Slf4j
@Service("ruiDiJsonService")
public class RuidiJsonServiceImpl implements RuiDiJsonService {

    private static final String name = "瑞迪";

    private static final String source = "瑞迪";

    @SneakyThrows
    @Override
    public TzBidResponse getRuidiDataByJson(TzBidRequest request) {
        RdBidRequest bidRequest = new RdBidRequest();
        RdAd rdAd = new RdAd();
        RdVersion rever = new RdVersion();
        RdApp app = new RdApp();
        RdDevice device = new RdDevice();
        RdNetWork netWork = new RdNetWork();
        RdGps gps = new RdGps();
        RdWifi wifi = new RdWifi();
        RdAdSize rdAdSize = new RdAdSize();
        RdUdid rdUdid = new RdUdid();

        //imp
        rdAdSize.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0])); //
        rdAdSize.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1])); //
        rdAd.setAdslot_id(request.getAdv().getTag_id()); //
        rdAd.setAdslot_size(rdAdSize);
        //api ver
        rever.setMajor(2);
        rever.setMinor(0);
        rever.setMicro(3);
        //app
        app.setApp_id(request.getAdv().getApp_id()); //request.getAdv().getApp_id()
        app.setApp_package(request.getAdv().getBundle());
        RdVersion rdVersion = new RdVersion();
        rdVersion.setMicro(Integer.valueOf(request.getAdv().getVersion().split("\\.")[0])); //
        app.setApp_version(rdVersion);
        //device
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setDevice_type(1);
        }else if("ipad".equals(devicetype)){
            device.setDevice_type(2);
        }else if("pc".equals(devicetype)){
            device.setDevice_type(3);
        }else{
            device.setDevice_type(1);
        }
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs_type(1);
            rdUdid.setImei(request.getDevice().getImei());
            rdUdid.setImei_md5(request.getDevice().getImei_md5());
            rdUdid.setImei_sha1(request.getDevice().getImei_sha1());
            rdUdid.setOaid(request.getDevice().getOaid());
            rdUdid.setAndroid_id(request.getDevice().getAndroid_id());
            rdUdid.setAndroidid_md5(request.getDevice().getAndroid_id_md5());
            rdUdid.setAndroidid_sha1(request.getDevice().getAndroid_id_sha1());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs_type(2);
            device.setStartup_time(request.getDevice().getBootTimeSec());
            device.setPhone_name(request.getDevice().getPhoneName());
            device.setMem_total(request.getDevice().getMemorySize());
            device.setDisk_total(request.getDevice().getDiskSize());
            device.setMb_time(request.getDevice().getOsUpdateTimeSec());
            device.setMode_code(request.getDevice().getModelCode());
            device.setAuth_status(3);
            device.setCpu_num(request.getDevice().getCpuNumber());
            device.setBattery_status("Unkow");
            device.setBattery_power(60);
            device.setCpu_frequency(2.2);
            device.setHwv(request.getDevice().getHwv());
            rdUdid.setIdfa(request.getDevice().getIdfa());
            rdUdid.setIdfa_md5(request.getDevice().getIdfa_md5());
            rdUdid.setIdfv(request.getDevice().getIdfv());
            rdUdid.setOpenudid(request.getDevice().getOpen_udid());

        }
        rdUdid.setMac(request.getDevice().getMac());
        RdVersion rdVersion2 = new RdVersion();
        rdVersion2.setMajor(Integer.valueOf(request.getDevice().getOsv().split("\\.")[0]));
        rdVersion2.setMinor(0);
        rdVersion2.setMicro(0);
        device.setOs_version(rdVersion2);
        device.setVendor(request.getDevice().getMake());
        device.setBrand(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        RdAdSize rdAdSize1 = new RdAdSize();
        rdAdSize1.setWidth(request.getDevice().getW());
        rdAdSize1.setHeight(request.getDevice().getH());
        device.setScreen_size(rdAdSize1);
        device.setUser_agent(request.getDevice().getUa());
        device.setOrientation(2);
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());
        }
        device.setDensity(request.getDevice().getDeny());
        device.setSys_compiling_time(1611822793000L);
        device.setCountry("CN");
        device.setLanguage("zh");
        device.setTimezone("Asia/Shanghai");
        device.setUdid(rdUdid);
        //network
        netWork.setIpv4(request.getDevice().getIp());
        netWork.setIpv6(request.getDevice().getIpv6());


        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            netWork.setConnection_type(0);
        }else if("1".equals(connectiontype)){
            netWork.setConnection_type(1);
        }else if("2".equals(connectiontype)){
            netWork.setConnection_type(100);
        }else if("3".equals(connectiontype)){
            netWork.setConnection_type(101);
        }else if("4".equals(connectiontype)){
            netWork.setConnection_type(2);
        }else if("5".equals(connectiontype)){
            netWork.setConnection_type(3);
        }else if("6".equals(connectiontype)){
            netWork.setConnection_type(4);
        }else if("7".equals(connectiontype)){
            netWork.setConnection_type(5);
        } else {
            netWork.setConnection_type(999);
        }
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            netWork.setOperator_type(1);
        }else if("70123".equals(carrier)){
            netWork.setOperator_type(3);
        }else if("70121".equals(carrier)){
            netWork.setOperator_type(2);
        }else{
            netWork.setOperator_type(99);
        }
        //gps
        if(null != request.getDevice().getGeo()){
            gps.setLongitude(request.getDevice().getGeo().getLon());
            gps.setLatitude(request.getDevice().getGeo().getLat());
        }
        //wifi
        wifi.setAp_mac(request.getDevice().getMac());


        bidRequest.setRequest_id(request.getId());
        bidRequest.setAdslot(rdAd);
        bidRequest.setApi_version(rever);
        bidRequest.setApp(app);
        bidRequest.setDevice(device);
        bidRequest.setNetwork(netWork);
        bidRequest.setGps(gps);
        bidRequest.setWifi(wifi);
        bidRequest.setRequest_protocol_type(1);
        bidRequest.setSupport_deeplink(true);




        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求瑞迪广告参数"+JSONObject.parseObject(content));
        String url = "http://ad.token-ad.com:8081/api/ad";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游瑞迪广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":瑞迪广告返回参数"+JSONObject.parseObject(response));

        if (null != response && 0 == JSONObject.parseObject(response).getInteger("error_code")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONArray imp =  JSONObject.parseObject(response).getJSONArray("ads");
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
                tb.setId(JSONObject.parseObject(response).getString("bidid"));
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    ArrayList<TzImage> images = new ArrayList<>();
                    TzLogo tzLogo = new TzLogo();
                    TzIcon tzIcon = new TzIcon();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray assets = imp.getJSONObject(i).getJSONArray("image_src");
                    for (int na = 0; na < assets.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(assets.getString(na));
                        tzImage.setH(imp.getJSONObject(i).getInteger("material_height"));
                        tzImage.setW(imp.getJSONObject(i).getInteger("material_width"));
                        images.add(tzImage);
                    }
                    tzLogo.setUrl(imp.getJSONObject(i).getString("mob_adlogo"));
                    if(null != imp.getJSONObject(i).getJSONArray("icon_src") && 0 <  imp.getJSONObject(i).getJSONArray("icon_src").size()){
                        tzIcon.setUrl(imp.getJSONObject(i).getJSONArray("icon_src").getString(0));
                    }
                    tzNative.setIcon(tzIcon);
                    tzNative.setTitle(imp.getJSONObject(i).getString("ad_title"));
                    tzNative.setDesc(imp.getJSONObject(i).getString("description"));
                    tzNative.setImages(images);
                    tb.setNATIVE(tzNative);
                }else if ("4".equals(request.getImp().get(0).getAd_slot_type())){
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    video.setUrl(imp.getJSONObject(i).getString("video_url"));
                    video.setDuration(imp.getJSONObject(i).getInteger("video_duration"));
                    video.setH(imp.getJSONObject(i).getInteger("material_height"));
                    video.setW(imp.getJSONObject(i).getInteger("material_width"));
                    tb.setTitle(imp.getJSONObject(i).getString("ad_title"));
                    tb.setDesc(imp.getJSONObject(i).getString("description"));
                    tb.setVideo(video);//视频素材
                    if(null != imp.getJSONObject(i).getJSONArray("icon_src") && 0 <  imp.getJSONObject(i).getJSONArray("icon_src").size()){
                        tb.setAic(imp.getJSONObject(i).getJSONArray("icon_src").getString(0));
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
                    JSONArray assets = imp.getJSONObject(i).getJSONArray("image_src");
                    for (int na = 0; na < assets.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(assets.getString(na));
                        tzImage.setH(imp.getJSONObject(i).getInteger("material_height"));
                        tzImage.setW(imp.getJSONObject(i).getInteger("material_width"));
                        images.add(tzImage);
                    }
                    tb.setImages(images);
                    tb.setTitle(imp.getJSONObject(i).getString("ad_title"));
                    tb.setDesc(imp.getJSONObject(i).getString("description"));
                    if(null != imp.getJSONObject(i).getJSONArray("icon_src") && 0 <  imp.getJSONObject(i).getJSONArray("icon_src").size()){
                        tb.setAic(imp.getJSONObject(i).getJSONArray("icon_src").getString(0));
                    }
                }


                Integer interaction_type = imp.getJSONObject(i).getInteger("interaction_type");
                if(0 == interaction_type){
                    tb.setClicktype("0");//点击
                }else if(1 == interaction_type){
                    tb.setClicktype("1");//跳转
                }else if(2 == interaction_type){
                    tb.setClicktype("4");//下载
                }else if(3 == interaction_type){
                    tb.setClicktype("2");//dp

                }

                TzBidApps tzApp = new TzBidApps();
                tzApp.setBundle(imp.getJSONObject(i).getString("app_package"));
                tzApp.setApp_size(imp.getJSONObject(i).getInteger("app_size"));
                tb.setApp(tzApp);


                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink_url"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink_url"));
                }
                tb.setClick_url(imp.getJSONObject(i).getString("link_url"));


                JSONObject bext = imp.getJSONObject(i).getJSONObject("ad_tracking");


                if (null != bext.getJSONArray("ad_exposure_url")) {
                    JSONArray apv = bext.getJSONArray("ad_exposure_url");
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

                if (null != bext.getJSONArray("ad_click_url")) {
                    JSONArray aclick = bext.getJSONArray("ad_click_url");
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
                if (null != bext.getJSONArray("app_ad_download_begin_url")) {
                    JSONArray sdtrackers = bext.getJSONArray("app_ad_download_begin_url");
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
                if (null != bext.getJSONArray("dp_success_url")) {
                    JSONArray dptrackers = bext.getJSONArray("dp_success_url");
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
                if (null != bext.getJSONArray("app_ad_download_finish_url")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("app_ad_download_finish_url");
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
                if (null != bext.getJSONArray("app_ad_install_begin_url")) {
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("app_ad_install_begin_url");
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
                if (null != bext.getJSONArray("app_ad_install_finish_url")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("app_ad_install_finish_url");
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
            log.info(request.getImp().get(0).getTagid()+":瑞迪总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }


}
