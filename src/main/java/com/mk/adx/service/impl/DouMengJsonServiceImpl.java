package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.doumeng.DouMengApp;
import com.mk.adx.entity.json.request.doumeng.DouMengBidRequest;
import com.mk.adx.entity.json.request.doumeng.DouMengDevice;
import com.mk.adx.entity.json.request.doumeng.DouMengNetwork;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.DouMengJsonService;
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
@Service("douMengJsonService")
public class DouMengJsonServiceImpl implements DouMengJsonService {

    private static final String name = "豆盟";

    private static final String source = "豆盟";

    @SneakyThrows
    @Override
    public TzBidResponse getDouMengDataByJson(TzBidRequest request) {
        DouMengBidRequest bidRequest = new DouMengBidRequest();
        DouMengDevice device = new DouMengDevice();
        DouMengNetwork network = new DouMengNetwork();
        DouMengApp app = new DouMengApp();

        //device
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOsType("android");
            device.setImei(request.getDevice().getImei());
            device.setImeiMd5(request.getDevice().getImei_md5());
            device.setOaid(request.getDevice().getOaid());
            device.setOaidMd5(request.getDevice().getOaid_md5());
            device.setAndroidId(request.getDevice().getAndroid_id());
            device.setSerialNo(request.getDevice().getAndroid_id());
            device.setAndroidApi(10);
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOsType("ios");
            device.setIdfa(request.getDevice().getIdfa());
        }
        device.setMac(request.getDevice().getMac());
        device.setModel(request.getDevice().getModel());
        device.setVendor(request.getDevice().getMake());
        device.setScreenWidth(request.getDevice().getW().toString());
        device.setScreenHeight(request.getDevice().getH().toString());
        device.setOsVersion(request.getDevice().getOsv());
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setDeviceType("mobile");
        }else if("ipad".equals(devicetype)){
            device.setDeviceType("flat");
        }else {
            device.setDeviceType("mobile");
        }
        device.setScreenOrientation("vertical");
        device.setBrand(request.getDevice().getMake());
        device.setDensity(String.valueOf(request.getDevice().getDeny()));
        device.setPpi(String.valueOf(request.getDevice().getPpi()));
        device.setBootMark(request.getDevice().getBootTimeSec());
        device.setUpdateMark(request.getDevice().getOsUpdateTimeSec());
        if(StringUtils.isNotEmpty(request.getDevice().getOsUpdateTimeSec())){
            device.setOsUpdateTime(Long.valueOf(request.getDevice().getOsUpdateTimeSec()));
        }
        if(StringUtils.isNotEmpty(request.getDevice().getBootTimeSec())){
            device.setOsStartupTime(Long.valueOf(request.getDevice().getBootTimeSec()));
        }
        device.setPhoneName(request.getDevice().getPhoneName());
        device.setSysMemory(request.getDevice().getMemorySize());
        device.setSysDiskSize(request.getDevice().getDiskSize());
        device.setCpuNum(request.getDevice().getCpuNumber());
        device.setHardwareMachine(request.getDevice().getHardware_machine());
//        device.setHmsVer();
//        device.setHwagVer();
//        device.setMiuiVersion();

        //network
        network.setIp(request.getDevice().getIp());
        network.setIp6(request.getDevice().getIp());
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            network.setConnectionType("unknown");
        }else if("1".equals(connectiontype)){
            network.setConnectionType("unknown");
        }else if("2".equals(connectiontype)){
            network.setConnectionType("wifi");
        }else if("3".equals(connectiontype)){
            network.setConnectionType("wifi");
        }else if("4".equals(connectiontype)){
            network.setConnectionType("2g");
        }else if("5".equals(connectiontype)){
            network.setConnectionType("3g");
        }else if("6".equals(connectiontype)){
            network.setConnectionType("4g");
        }else if("7".equals(connectiontype)){
            network.setConnectionType("5g");
        } else {
            network.setConnectionType("unknown");
        }
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            network.setOperatorType("cm");
        }else if("70123".equals(carrier)){
            network.setOperatorType("cu");
        }else if("70121".equals(carrier)){
            network.setOperatorType("ct");
        }else{
            network.setOperatorType("unknown");
        }
        if(null != request.getDevice().getGeo()){
            network.setLat(String.valueOf(request.getDevice().getGeo().getLat()));
            network.setLon(String.valueOf(request.getDevice().getGeo().getLon()));
            if(StringUtils.isNotEmpty(request.getDevice().getGeo().getLlt())){
                network.setGpsTs(Long.valueOf(request.getDevice().getGeo().getLlt()));
            }
        }

        //app
        app.setId(request.getAdv().getApp_id());
        app.setName(request.getAdv().getApp_name());
        app.setBundle(request.getAdv().getBundle());
        app.setVersionName(request.getAdv().getVersion());
        app.setVersionCode(Integer.valueOf(request.getAdv().getVersion().split("\\.")[0]));


        bidRequest.setAppKey(request.getAdv().getApp_id());
        bidRequest.setAdSpaceKey(request.getAdv().getTag_id());
        bidRequest.setDevice(device);
        bidRequest.setNetwork(network);
        bidRequest.setApiVersion("V1.2.2");
        bidRequest.setAdSpaceWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        bidRequest.setAdSpaceHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
        bidRequest.setApp(app);


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求豆盟广告参数"+JSONObject.parseObject(content));
        String url = "https://adx.doumpaq.com/ailurus/advert/getAdvert";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游豆盟广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":豆盟广告返回参数"+JSONObject.parseObject(response));

        if (null != response && 1 == JSONObject.parseObject(response).getInteger("status")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONArray bid = JSONObject.parseObject(response).getJSONArray("entity");
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < bid.size(); i++) {
                TzBid tb = new TzBid();
                tb.setId(request.getId());
                JSONObject imp = bid.getJSONObject(i);
                JSONArray repImages = imp.getJSONArray("images");
                if("1".equals(request.getImp().get(0).getAd_slot_type()) && "picture".equals(imp.getString("materialType"))){
                    TzNative tzNative = new TzNative();
                    ArrayList<TzImage> images = new ArrayList<>();
                    tb.setAd_type(8);//信息流-广告素材类型
                    for (int na = 0; na < repImages.size(); na++) {
                         TzImage tzImage = new TzImage();
                         tzImage.setUrl(repImages.getString(na));
                         tzImage.setW(imp.getInteger("width"));
                         tzImage.setH(imp.getInteger("height"));
                         images.add(tzImage);
                         TzLogo tzLogo = new TzLogo();
                         tzLogo.setUrl(imp.getString("logo"));
                         tzNative.setLogo(tzLogo);
                    }
                    tzNative.setTitle(imp.getString("title"));
                    tzNative.setDesc(imp.getString("desc"));
                    tzNative.setImages(images);
                    tb.setNATIVE(tzNative);
                }else if ("4".equals(request.getImp().get(0).getAd_slot_type()) && "videoPicture".equals(imp.getString("materialType"))){
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONObject videos = imp.getJSONObject("videoResponse");
                    if (null != videos) {
                        video.setUrl(videos.getString("url"));
                        video.setW(imp.getInteger("width"));
                        video.setH(imp.getInteger("height"));
                        video.setDuration(videos.getInteger("duration"));
                        tb.setTitle(imp.getString("title"));
                        tb.setDesc(imp.getString("desc"));
                        tb.setAdLogo(imp.getString("logo"));
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
                    for (int na = 0; na < repImages.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(repImages.getString(na));
                        tzImage.setW(imp.getInteger("width"));
                        tzImage.setH(imp.getInteger("height"));
                        images.add(tzImage);
                    }
                    tb.setImages(images);
                    tb.setTitle(imp.getString("title"));
                    tb.setDesc(imp.getString("desc"));
                    tb.setAdLogo(imp.getString("logo"));
                }


                String clickUrlType = imp.getString("clickUrlType");
                if("browse".equals(clickUrlType)){
                    tb.setClicktype("2");//跳转
                }else if("download".equals(clickUrlType)){
                    tb.setClicktype("4");//下载
                }else if("deepLinkDownload".equals(clickUrlType)){
                    tb.setClicktype("2");//dp
                }else if("gdt".equals(clickUrlType)){
                    tb.setClicktype("3");//广点通
                }

                if(StringUtils.isNotEmpty(imp.getString("deepLinkUrl"))){
                    tb.setDeeplink_url(imp.getString("deepLinkUrl"));
                }
                if(StringUtils.isNotEmpty(imp.getString("clickUrl"))){
                    tb.setClick_url(imp.getString("clickUrl"));
                }
                JSONObject bext = imp.getJSONObject("trackUrls");
                if (null != bext.getJSONArray("show")) {
                    JSONArray apv = bext.getJSONArray("show");
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

                if (null != bext.getJSONArray("click")) {
                    JSONArray aclick = bext.getJSONArray("click");
                    List<String> clickList = new ArrayList<>();
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < aclick.size(); cc++) {
                        String replace = aclick.get(cc).toString().replace("_SHOW_WIDTH_", "%%WIDTH%%").replace("_SHOW_HEIGHT_", "%%HEIGHT%%").replace("__DOWN_X__", "%%DOWN_X%%").replace("__DOWN_Y__", "%%DOWN_Y%%").replace("__UP_X__", "%%UP_X%%").replace("__UP_Y__", "%%UP_Y%%").replace("_CLICK_TIME_ ", "%%TS%%").replace("_CLICK_SECOND_TIME_ ", "%%TS%S%%").replace("_REAL_IP_ ", request.getDevice().getIp());
                        clickList.add(replace);
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                }
                if (null != bext.getJSONArray("downloadStart")) {
                    JSONArray sdtrackers = bext.getJSONArray("downloadStart");
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
                if (null != bext.getJSONArray("downloadSuccess")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("downloadSuccess");
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

                if (null != bext.getJSONArray("openDeepLink")) {
                    JSONArray dptrackers = bext.getJSONArray("openDeepLink");
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
                if (null != bext.getJSONArray("installStart")) {
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("installStart");
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
                if (null != bext.getJSONArray("installSuccess")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("installSuccess");
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
            log.info(request.getImp().get(0).getTagid()+":豆盟总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
