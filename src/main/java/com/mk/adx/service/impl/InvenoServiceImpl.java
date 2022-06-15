package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.inveno.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.InvenoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description Inveno
 * @Date 2021/12/23 16:01
 */
@Slf4j
@Service("InvenoService")
public class InvenoServiceImpl implements InvenoService {

    private static final String name = "Inveno";

    private static final String source = "英威诺";

    /**
     * @Author mzs
     * @Description 英威诺
     * @Date 2021/12/23 16:01
     */
    @SneakyThrows
    @Override
    public TzBidResponse getInvenoDataByJson(TzBidRequest request) {
        InvenoBidRequest bidRequest = new InvenoBidRequest();
        InvenoApp app = new InvenoApp();
        InvenoDevice device = new InvenoDevice();
        InvenoNetWork netWork = new InvenoNetWork();
        InvenoGps gps = new InvenoGps();
        InvenoUser user = new InvenoUser();
        InvenoAdspaces adspaces = new InvenoAdspaces();
        InvenoDeviceId deviceId = new InvenoDeviceId();
        List<InvenoAdspaces> adspacesList = new ArrayList();
        List<InvenoDeviceId> deviceIdList = new ArrayList();


        //应⽤具体信息 App
        app.setApp_id(request.getAdv().getApp_id()); //
        app.setChannel_id("tianzhuo");
        app.setApp_name(request.getAdv().getApp_name()); //
        app.setPackage_name(request.getAdv().getBundle());
        app.setApp_version(request.getAdv().getVersion());
        app.setReport_pv_method(0);

        //设备具体信息 Device
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs_type(2);
            if(StringUtils.isNotEmpty(request.getDevice().getImei())){
                deviceId = new InvenoDeviceId();
                deviceId.setDevice_id_type(1);
                deviceId.setDevice_id(request.getDevice().getImei());
                deviceId.setHash_type(0);
                deviceIdList.add(deviceId);
            }
            if(StringUtils.isNotEmpty(request.getDevice().getAndroid_id())){
                deviceId = new InvenoDeviceId();
                deviceId.setDevice_id_type(3);
                deviceId.setDevice_id(request.getDevice().getAndroid_id());
                deviceId.setHash_type(0);
                deviceIdList.add(deviceId);
            }
            if(StringUtils.isNotEmpty(request.getDevice().getOaid())){
                deviceId = new InvenoDeviceId();
                deviceId.setDevice_id_type(9);
                deviceId.setDevice_id(request.getDevice().getOaid());
                deviceId.setHash_type(0);
                deviceIdList.add(deviceId);
            }
            device.setBoot_mark("ec7f4f33-411a-47bc-8067-744a4e7e0723");
            device.setUpdate_mark("1004697.709999999");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs_type(1);
            if(StringUtils.isNotEmpty(request.getDevice().getIdfa())){
                deviceId = new InvenoDeviceId();
                deviceId.setDevice_id_type(2);
                deviceId.setDevice_id(request.getDevice().getIdfa());
                deviceId.setHash_type(0);
                deviceIdList.add(deviceId);
            }
            device.setBoot_mark("1623815045.970028");
            device.setUpdate_mark("1581141691.570419583");
        }else{
            device.setOs_type(0);
        }
        if(StringUtils.isNotEmpty(request.getDevice().getMac())){
            deviceId = new InvenoDeviceId();
            deviceId.setDevice_id_type(4);
            deviceId.setDevice_id(request.getDevice().getMac());
            deviceId.setHash_type(0);
            deviceIdList.add(deviceId);
        }
        device.setDevice_id(deviceIdList);
        device.setOs_version(request.getDevice().getOsv());
        device.setBrand(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setDevice_type(2);
        }else if("ipad".equals(devicetype)){
            device.setDevice_type(1);
        }else{
            device.setDevice_type(2);
        }
        device.setLanguage("zh_cn");
        device.setScreen_width(request.getDevice().getW());
        device.setScreen_height(request.getDevice().getH());
        device.setScreen_density(request.getDevice().getDeny());

        //⽹络具体信息 Network
        netWork.setIp(request.getDevice().getIp());
        String carrier = request.getDevice().getCarrier();
        if(StringUtils.isNotEmpty(carrier)){//运营商类型
            if("70120".equals(carrier)){
                netWork.setCarrier_id("70120");
            }else if("70123".equals(carrier)){
                netWork.setCarrier_id("70123");
            }else if("70121".equals(carrier)){
                netWork.setCarrier_id("70121");
            }else{
                netWork.setCarrier_id("unknown");
            }
        }else{
            netWork.setCarrier_id("unknown");
        }
        String connectiontype = request.getDevice().getConnectiontype().toString();//网络连接类型
        if("0".equals(connectiontype)){
            netWork.setNetwork_type(0);
        }else if("2".equals(connectiontype)){
            netWork.setNetwork_type(1);
        }else if("4".equals(connectiontype)){
            netWork.setNetwork_type(2);
        }else if("5".equals(connectiontype)){
            netWork.setNetwork_type(3);
        }else if("6".equals(connectiontype)){
            netWork.setNetwork_type(4);
        }else if("7".equals(connectiontype)){
            netWork.setNetwork_type(5);
        } else {
            netWork.setNetwork_type(0);
        }

        //gps
        gps.setType(1);
        gps.setLongitude(request.getDevice().getGeo().getLon());
        gps.setLatitude(request.getDevice().getGeo().getLat());
        gps.setTimestamp((int)System.currentTimeMillis());

        //user
        user.setUser_id(request.getDevice().getImei());

        //ad space
        adspaces.setAdspace_id(request.getAdv().getTag_id()); //
        if("1".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(4);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("2".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(1);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("3".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(2);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("4".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(7);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("5".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(1);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("6".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(3);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("7".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(1);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }else if("8".equals(request.getImp().get(0).getAd_slot_type())){
            adspaces.setAdspace_type(1);//广告位类型。取值：1=横幅广告(Banner)；2=开屏广告；3=插屏广告；4=信息流广告；5=文字链广告；7=视频广告
        }
        adspaces.setAllowed_html(false);
        adspaces.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0])); //
        adspaces.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1])); //
        adspaces.setImpression_num(1);
        adspaces.setOpen_type(0);
        List interaction = new ArrayList();
        interaction.add(2);interaction.add(3);
        adspaces.setInteraction_type(interaction);
        adspaces.setSupport_deeplink(1);
        adspacesList.add(adspaces);


        bidRequest.setBid(request.getId());
        bidRequest.setApi_version("2.2.0");
        bidRequest.setUa(request.getDevice().getUa());
        bidRequest.setApp(app);
        bidRequest.setDevice(device);
        bidRequest.setNetwork(netWork);
        bidRequest.setGps(gps);
        bidRequest.setUser(user);
        bidRequest.setAdspaces(adspacesList);


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求英威诺广告参数"+JSONObject.parseObject(content));
        String url = "http://malacca.inveno.com/malacca/sdkPullAds.do";
        String ua = request.getDevice().getUa();
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doInvenoJsonPost(url,content,ua);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求上游英威诺广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("英威诺广告返回参数"+JSONObject.parseObject(response));
        if (0 == JSONObject.parseObject(response).getInteger("error_code")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONArray imp = JSONObject.parseArray(JSONObject.parseObject(response).get("ads").toString());
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                JSONArray ads = imp.getJSONObject(i).getJSONArray("creative");
                for (int j = 0; j < ads.size(); j++){
                    TzBid tb = new TzBid();
                    TzVideo tzVideo = new TzVideo();
                    tb.setId(ads.getJSONObject(j).getString("ad_id"));
                    ArrayList<TzImage> images = new ArrayList<>();
                    JSONObject adm = ads.getJSONObject(j).getJSONObject("adm");
                    if("1".equals(request.getImp().get(0).getAd_slot_type())){
                        //String adm_source = adm.getString("adm_source");
                        TzNative tzNative = new TzNative();
                        tb.setAd_type(8);//信息流-广告素材类型
                        JSONArray invenoImages = adm.getJSONObject("adm_native").getJSONArray("imgs");
                        if(null != invenoImages) {
                            for (int im = 0; im < invenoImages.size(); im++) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(invenoImages.getJSONObject(im).getString("url"));
                                tzImage.setH(invenoImages.getJSONObject(im).getInteger("height"));
                                tzImage.setW(invenoImages.getJSONObject(im).getInteger("width"));
                                images.add(tzImage);
                            }
                        }
                        tzNative.setTitle(adm.getJSONObject("adm_native").getString("ad_title"));
                        tzNative.setDesc(adm.getJSONObject("adm_native").getString("description"));
                        tzNative.setImages(images);

                        // 信息流视频
                        JSONObject video = adm.getJSONObject("adm_native").getJSONObject("video");
                        if(null != video){
                            tzVideo.setUrl(video.getString("videoUrl"));
                            tzVideo.setH(video.getInteger("videoCoverHeight"));
                            tzVideo.setW(video.getInteger("videoCoverWidth"));
                            tzVideo.setDuration(video.getInteger("videoDuration"));
                            tzNative.setVideo(tzVideo);
                        }
                        tb.setNATIVE(tzNative);
                    }else {
                        tb.setTitle(adm.getJSONObject("adm_native").getString("ad_title"));
                        tb.setDesc(adm.getJSONObject("adm_native").getString("description"));
                        tb.setAd_type(2);//开屏-广告素材类型
                        JSONArray invenoImages = adm.getJSONObject("adm_native").getJSONArray("imgs");
                        if(null != invenoImages) {
                            for (int im = 0; im < invenoImages.size(); im++) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(invenoImages.getJSONObject(im).getString("url"));
                                tzImage.setH(invenoImages.getJSONObject(im).getInteger("height"));
                                tzImage.setW(invenoImages.getJSONObject(im).getInteger("width"));
                                images.add(tzImage);
                            }
                            tb.setImages(images);
                        }

                        // 开屏视频
                        JSONObject video = adm.getJSONObject("adm_native").getJSONObject("video");
                        if(null != video){
                            tzVideo.setUrl(video.getString("videoUrl"));
                            tzVideo.setH(video.getInteger("videoCoverHeight"));
                            tzVideo.setW(video.getInteger("videoCoverWidth"));
                            tzVideo.setDuration(video.getInteger("videoDuration"));
                            tb.setVideo(tzVideo);//视频素材
                        }
                    }
                    TzBidApps tzBidApps = new TzBidApps();
                    tzBidApps.setBundle(ads.getJSONObject(j).getString("package_name"));
                    tb.setApp(tzBidApps);
                    String ad_type = ads.getJSONObject(j).getString("adm_type");//广告素材类型。取值：1=HTML素材；3=原生素材（v2.1.1之后该值废弃）；4=图片素材；5=视频素材；6=音频素材；7= GIF图素材；
                    if("1".equals(ad_type)){
                        tb.setAd_type(4);
                    }else if("3".equals(ad_type)){
                        tb.setAd_type(8);
                    }else if("4".equals(ad_type)){
                        tb.setAd_type(8);
                    }else if("5".equals(ad_type)){
                        tb.setAd_type(6);
                    }else if("6".equals(ad_type)){
                        tb.setAd_type(7);
                    }else if("7".equals(ad_type)){
                        tb.setAd_type(8);
                    }else{
                        tb.setAd_type(8);
                    }
                    String action = ads.getJSONObject(j).getString("interaction_type");//交互类型。(HTML素材为非必填)取值：1=不交互；2=浏览；3= 一般下载（除广点通外）；4电话；5=短信；6=邮件；7=广点通下载
                    if("1".equals(action)){
                        tb.setClicktype("0");//点击
                    }else if("2".equals(action)){
                        tb.setClicktype("1");//跳转
                    }else if("3".equals(action)){
                        tb.setClicktype("4");//普通下载
                    }else if("7".equals(action)){
                        tb.setClicktype("3");//广点通下载
                    }else{
                        tb.setClicktype("0");//点击
                    }

                    if(StringUtils.isNotEmpty(ads.getJSONObject(j).getJSONObject("interaction_object").getString("deep_link"))){
                        tb.setDeeplink_url(ads.getJSONObject(j).getJSONObject("interaction_object").getString("deep_link"));
                    }
                    tb.setClick_url(ads.getJSONObject(j).getJSONObject("interaction_object").getString("url")); // 点击跳转url地址

                    JSONArray eventTracks = ads.getJSONObject(j).getJSONArray("event_track");
                    for (int et = 0; et < eventTracks.size(); et++ ) {
                        if("22".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_success_deeplinks()){
                                List<String> check_success_deeplinks = tb.getCheck_success_deeplinks();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_success_deeplinks.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_success_deeplinks.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_success_deeplinks(check_success_deeplinks);
                            }else{
                                List<String> deep_linkT = new ArrayList<>();
                                deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    deep_linkT.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    deep_linkT.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_success_deeplinks(deep_linkT);//唤醒成功
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DEEP_LINK%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }

                        }
                        if("1".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_views()){
                                List<String> check_views = tb.getCheck_views();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_views.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_views.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_views(check_views);
                            }else{
                                List<String> check_views = new ArrayList<>();
                                check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_views.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_views.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_VIEWS%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }
                        if("2".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_clicks()){
                                List<String> check_clicks = tb.getCheck_clicks();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_clicks.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_clicks.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_clicks(check_clicks);
                            }else{
                                List<String> clickList = new ArrayList<>();
                                clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    clickList.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    clickList.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_CLICKS%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }

//                         if("19".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
//                             if(null != tb.getcheckv){
//
//                             }else{
//                                 List<String> voidStartList = new ArrayList<>();
//                                 voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
//                                 voidStartList.add(eventTracks.getJSONObject(et).getString("notify_url"));
//                                 String encode = eventTracks.getJSONObject(et).getString("notify_url") + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                                 tb.setCheck_views(voidStartList);//视频开始播放
//                                 tzMacros = new TzMacros();
//                                 tzMacros.setMacro("%%VEDIO_START%%");
//                                 tzMacros.setValue(Base64.encode(encode));
//                                 tzMacros1.add(tzMacros);
//                             }
//                         }
//
//                         if("22".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
//                             List<String> voidEndList = new ArrayList<>();
//                             voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
//                             voidEndList.add(eventTracks.getJSONObject(et).getString("notify_url"));
//                             String encode = eventTracks.getJSONObject(et).getString("notify_url") + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                             tb.setCheck_clicks(voidEndList);//视频播放结束
//                             tzMacros = new TzMacros();
//                             tzMacros.setMacro("%%VEDIO_END%%");
//                             tzMacros.setValue(Base64.encode(encode));
//                             tzMacros1.add(tzMacros);
//                         }

                        if("11".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_start_downloads()){
                                List<String> check_start_downloads = tb.getCheck_start_downloads();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_start_downloads.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_start_downloads.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_start_downloads(check_start_downloads);
                            }else{
                                List<String> downLoadList = new ArrayList<>();
                                downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    downLoadList.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    downLoadList.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_start_downloads(downLoadList);//开始下载
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_LOAD%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }
                        if("12".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_end_downloads()){
                                List<String> check_end_downloads = tb.getCheck_end_downloads();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_end_downloads.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_end_downloads.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_end_downloads(check_end_downloads);
                            }
                            List<String> downLoadDList = new ArrayList<>();
                            downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                            if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                downLoadDList.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                            }else{
                                downLoadDList.add(eventTracks.getJSONObject(et).getString("notify_url"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_downloads(downLoadDList);//结束下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_END%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);

                        }
                        if("23".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_start_installs()){
                                List<String> check_start_installs = tb.getCheck_start_installs();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_start_installs.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_start_installs.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_start_installs(check_start_installs);
                            }else{
                                List<String> installList = new ArrayList<>();
                                installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    installList.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    installList.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_start_installs(installList);//开始安装
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%INSTALL_START%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }

                        }
                        if("13".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
                            if(null != tb.getCheck_end_installs()){
                                List<String> check_end_installs = tb.getCheck_end_installs();
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    check_end_installs.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    check_end_installs.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                tb.setCheck_end_installs(check_end_installs);
                            }else{
                                List<String> installEList = new ArrayList<>();
                                installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                                if(null != eventTracks.getJSONObject(et).getJSONArray("missFields")){
                                    installEList.add(eventTracks.getJSONObject(et).getString("notify_url")+"&gtm=%%TS%%");
                                }else{
                                    installEList.add(eventTracks.getJSONObject(et).getString("notify_url"));
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_end_installs(installEList);//安装完成
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }
                    }


                tb.setMacros(tzMacros1);
                tb.setSource(source);
                tb.setImpid(request.getImp().get(0).getId());
                bidList.add(tb);//
                }
            }
            TzSeat seat = new TzSeat();
            seat.setBid(bidList);
            seatList.add(seat);
            bidResponse.setId(id);//请求id
            bidResponse.setBidid(id);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info("英威诺广告总返回"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
