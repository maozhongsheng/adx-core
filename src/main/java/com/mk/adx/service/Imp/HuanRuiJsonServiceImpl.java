package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.hailiang.HailiangApp;
import com.mk.adx.entity.json.request.hailiang.HailiangBidRequest;
import com.mk.adx.entity.json.request.hailiang.HailiangDevice;
import com.mk.adx.entity.json.request.hailiang.HailiangImp;
import com.mk.adx.entity.json.request.huanrui.*;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.service.HailiangJsonService;
import com.mk.adx.service.HuanRuiJsonService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 环睿
 * @Date 2021/11/23 9:48
 */
@Slf4j
@Service("huanRuiJsonService")
public class HuanRuiJsonServiceImpl implements HuanRuiJsonService {

    private static final String name = "huanurui";

    private static final String source = "环睿";


    @SneakyThrows
    @Override
    public MkBidResponse getHuanRuiDataByJson(MkBidRequest request) {
        HuanRuiBidRequest bidRequest = new HuanRuiBidRequest();
        HuanRuiDevice device = new HuanRuiDevice();
        HuanRuiApp app = new HuanRuiApp();
        HuanRuiGps gps = new HuanRuiGps();
        HuanRuiNet net = new HuanRuiNet();
        //app
        app.setAppid(request.getAdv().getApp_id());
        app.setSlotid(request.getAdv().getTag_id());
        app.setName(request.getAdv().getApp_name());
        app.setPkgname(request.getAdv().getBundle());
        app.setVer(request.getAdv().getVersion());
        app.setOrientation(1);
        //gps
        if(null != request.getDevice().getGeo()){
            gps.setLat(String.valueOf(request.getDevice().getGeo().getLat()));
            gps.setLon(String.valueOf(request.getDevice().getGeo().getLon()));
        }else{
            gps.setLat("0");
            gps.setLon("0");
        }
        gps.setCoordinateType(0);
        //net
        net.setIp(request.getDevice().getIp());
        if (0 == request.getDevice().getConnectiontype()) {//必填，发起当前请求的设备⽹络连接类型信息
            net.setNetwork("other");//未知
        }else if(1 == request.getDevice().getConnectiontype()){
            net.setNetwork("other");//Ethernet
        }else if(2 == request.getDevice().getConnectiontype()){
            net.setNetwork("wifi");//wifi
        }else if(4 == request.getDevice().getConnectiontype()){
            net.setNetwork("2g");//移动⽹络2G Cellular Network – 2G
        }else if(5 == request.getDevice().getConnectiontype()){
            net.setNetwork("3g");//移动⽹络3G Cellular Network – 3G
        }else if(6 == request.getDevice().getConnectiontype()){
            net.setNetwork("4g");//移动⽹络4G Cellular Network – 4G
        }else if(7 == request.getDevice().getConnectiontype()){
            net.setNetwork("5g");//移动⽹络4G Cellular Network – 5G
        }
        net.setMac(request.getDevice().getMac());
        if("0".equals(request.getDevice().getCarrier())){//必填，提供完整的Carrier对象来描述当前运营商信息
            net.setOperator("0");
        }else if("70120".equals(request.getDevice().getCarrier())){
            net.setOperator("1");//中国移动
        }else if("70123".equals(request.getDevice().getCarrier())){
            net.setOperator("2");//中国联通
        }else if("70121".equals(request.getDevice().getCarrier())){
            net.setOperator("3");//中国电信
        }else if("70124".equals(request.getDevice().getCarrier())){
            net.setOperator("0");
        }
        //device
        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs(0);
            device.setOaid(request.getDevice().getOaid());//条件必填，Android Q以后的⼿机唯⼀标识符。当请求发⾃Android设备时，必填
            device.setImei(request.getDevice().getImei());
            device.setImei_md5(request.getDevice().getImei_md5());
            device.setAndroidid(request.getDevice().getAndroid_id());
            device.setAndroidid_md5(request.getDevice().getAndroid_id_md5());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOs(1);
            device.setIdfa(request.getDevice().getIdfa());//条件必填，Apple设备的IDFA信息。当请求发⾃Apple设备时，必填
            device.setIdfa_md5(request.getDevice().getIdfa_md5());//条件必填，设备MAC信息。
            device.setBoot_time(request.getDevice().getBootTimeSec());
            device.setOp_up_time(request.getDevice().getOsUpdateTimeSec());
            device.setDisk_size(request.getDevice().getDiskSize());
            device.setBattery_status("Unkown");
            device.setMemory_size(request.getDevice().getMemorySize());
            device.setCpu_number(request.getDevice().getCpuNumber());
            device.setTime_zone(request.getDevice().getTimeZone());
            device.setLmt(3);
        }
        device.setOsv(request.getDevice().getOsv());
        device.setBrand(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        if(null != request.getDevice().getPpi()){
            device.setDpi(request.getDevice().getPpi());
        }
        device.setSw(request.getDevice().getW());
        device.setSh(request.getDevice().getH());
        device.setUa(request.getDevice().getUa());
        device.setSsid("");
        device.setWifi_mac("");
        device.setRom_version("");
        device.setSys_compling_time("");
        device.setCan_deepLink(1);
        device.setPhone_name(request.getDevice().getPhoneName());
        device.setLanguage(request.getDevice().getLanguage());
        device.setCountry("CH");
        device.setApp_store_version(request.getDevice().getAppstore_ver());
        device.setHms_ver(request.getDevice().getVercodeofhms());
        device.setHwag_ver(request.getDevice().getAppstore_ver());



        bidRequest.setVer("2.3.2");
        bidRequest.setTag(request.getId());
        bidRequest.setApp(app);
        bidRequest.setDevice(device);
        bidRequest.setGps(gps);
        bidRequest.setNet(net);

        MkBidResponse bidResponse = new MkBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + ":请求环睿参数" + JSONObject.parseObject(content));
        String url = "http://api.s1.hrmobi.cn/ads";
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
        log.info(request.getImp().get(0).getTagid() + ":请求上游环睿花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info(request.getImp().get(0).getTagid() + ":环睿返回参数" + JSONObject.parseObject(response));

        if ("SUCCESS".equals(JSONObject.parseObject(response).getString("msg"))) {
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            JSONArray bid = jo.getJSONArray("ads");
            List<MkBid> bidList = new ArrayList<>();
            String id = request.getId();
            for (int i = 0; i < bid.size(); i++) {
                MkBid tb = new MkBid();
                List<MkApp> appsList = new ArrayList<>();
                List<MkImage> list = new ArrayList<>();
                for (int j = 0; j < bid.size(); j++) {
                    tb.setAdid(id);
                    MkApp apps = new MkApp();
                    apps.setBundle(bid.getJSONObject(j).getString("bundle"));
                    apps.setApp_name(bid.getJSONObject(j).getString("appname"));
                    apps.setApp_icon(bid.getJSONObject(j).getString("appicon"));
                    appsList.add(apps);
                    if ("1".equals(request.getImp().get(i).getSlot_type()) || "2".equals(request.getImp().get(i).getSlot_type())) {//信息流或banner
                        tb.setAd_type(1);//原生-广告素材类型
                    }else {
                        tb.setAd_type(2);//开屏-广告素材类型
                    }
                    JSONArray image = bid.getJSONObject(j).getJSONArray("imgs");
                    for (int k = 0; k < image.size(); k++) {
                        MkImage tzImage = new MkImage();
                        tzImage.setUrl(image.getString(k));
                        list.add(tzImage);
                    }
                    tb.setImages(list);
                    JSONObject video = bid.getJSONObject(j).getJSONObject("video");
                    if(null != video){
                        MkVideo mkVideo = new MkVideo();
                        mkVideo.setUrl(video.getString("url"));
                        mkVideo.setDuration(Integer.valueOf(video.getFloat("length").toString()));
                        tb.setVideo(mkVideo);
                    }

                    tb.setAdLogo(bid.getJSONObject(j).getString("logo"));
                    tb.setTitle(bid.getJSONObject(j).getString("title"));
                    tb.setDesc(bid.getJSONObject(j).getString("text"));
                    tb.setAic(bid.getJSONObject(j).getString("icon"));
                    if (1 == bid.getJSONObject(j).getInteger("adct")) {
                        tb.setClicktype("4");
                    } else if (2 == bid.getJSONObject(j).getInteger("adct")) {
                        tb.setClicktype("1");
                    }
                    if(1 == bid.getJSONObject(j).getInteger("is_gdt")){
                        tb.setClicktype("3");
                    }
                    tb.setDownload_url(bid.getJSONObject(j).getString("clkurl"));
                    tb.setDeeplink_url(bid.getJSONObject(j).getString("dpurl"));
                    tb.setClick_url(bid.getJSONObject(j).getString("clkurl"));


                    MkApp mkApp = new MkApp();
                    mkApp.setApp_name(bid.getJSONObject(j).getString("app_name"));
                    mkApp.setBundle(bid.getJSONObject(j).getString("pkg_name"));
                    tb.setApp(mkApp);

                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

                    List<MkCheckVideoUrls> videourls = new ArrayList();
                    MkCheckVideoUrls mkCheckVideoUrls = new MkCheckVideoUrls();
                    JSONArray monitors = bid.getJSONObject(j).getJSONArray("monitors");
                    for (int k = 0; k < monitors.size(); k++) {
                        if(0 == monitors.getJSONObject(k).getInteger("event")){
                            List<String> checkViews = new ArrayList<>();
                            JSONArray monitorUrl = monitors.getJSONObject(k).getJSONArray("urls");
                            checkViews.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                            for (int view = 0; view < monitorUrl.size(); view++) {
                                checkViews.add(monitorUrl.get(view).toString().replace("__event_time_start__", "__TS__").replace("__event_time_second__", "__TS_S__").replace("__down_x__", "__DOWN_X__").replace("__down_y__ ", "__DOWN_Y__").replace("__UP_X__", "__UP_X__").replace("__UP_Y__", "__UP_Y__"));//曝光监测URL,支持宏替换第三方曝光监测
                            }
                            tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                        }

                        if(1 == monitors.getJSONObject(k).getInteger("event")){
                            List<String> clickList = new ArrayList<>();
                            JSONArray clickUrl = monitors.getJSONObject(k).getJSONArray("urls");
                            clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                            for (int click = 0; click < clickUrl.size(); click++) {
                                clickList.add(clickUrl.get(click).toString().replace("__event_time_start__", "__TS__").replace("__event_time_second__", "__TS_S__").replace("__down_x__", "__DOWN_X__").replace("__down_y__ ", "__DOWN_Y__").replace("__UP_X__", "__UP_X__").replace("__UP_Y__", "__UP_Y__"));
                            }
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        }



                        if(2 == monitors.getJSONObject(k).getInteger("event")){
                            JSONArray dmurl = monitors.getJSONObject(k).getJSONArray("urls");
                            List<String> checkStartDownloads = new ArrayList<>();
                            checkStartDownloads.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                            for (int cm = 0; cm < dmurl.size(); cm++) {
                                checkStartDownloads.add(dmurl.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_start_downloads(checkStartDownloads);//下载开始
                        }

                        if(3 == monitors.getJSONObject(k).getInteger("event")){
                            JSONArray downsuccessurl = monitors.getJSONObject(k).getJSONArray("urls");
                            List<String> checkEndDownloads = new ArrayList<>();
                            checkEndDownloads.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode));
                            for (int cm = 0; cm < downsuccessurl.size(); cm++) {
                                checkEndDownloads.add(downsuccessurl.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_end_downloads(checkEndDownloads);
                        }
                        if(4 == monitors.getJSONObject(k).getInteger("event")){
                            JSONArray check_start_installss = monitors.getJSONObject(k).getJSONArray("urls");
                            List<String> check_start_installs = new ArrayList<>();
                            check_start_installs.add("http://adx.fxlxz.com/sl/in_start?installStart=" + Base64.encode(encode));
                            for (int cm = 0; cm < check_start_installss.size(); cm++) {
                                check_start_installs.add(check_start_installss.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_start_installs(check_start_installs);//开始安装监测URL第三方曝光监测
                        }
                        if(5 == monitors.getJSONObject(k).getInteger("event")){
                            JSONArray check_end_installss =  monitors.getJSONObject(k).getJSONArray("urls");
                            List<String> check_end_installs = new ArrayList<>();
                            check_end_installs.add("http://adx.fxlxz.com/sl/in_end?installEnd=" + Base64.encode(encode));
                            for (int cm = 0; cm < check_end_installss.size(); cm++) {
                                check_end_installs.add(check_end_installss.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_end_installs(check_end_installs);//安装完成监测URL第三方曝光监测
                        }


                        if(9 == monitors.getJSONObject(k).getInteger("event")){
                            List<String> checkSuccessDeeplinks = new ArrayList<>();
                            JSONArray deeplinkmurl =  monitors.getJSONObject(k).getJSONArray("urls");
                             checkSuccessDeeplinks.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                             for (int cm = 0; cm < deeplinkmurl.size(); cm++) {
                                 checkSuccessDeeplinks.add(deeplinkmurl.get(cm).toString());
                             }
                             tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                        }



                        if(11 == monitors.getJSONObject(k).getInteger("event")){
                            List<String> v_start = new ArrayList<>();
                            JSONArray urls1 =  monitors.getJSONObject(k).getJSONArray("urls");
                            v_start.add("http://adx.fxlxz.com/sl/v_start?vedioStart=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                v_start.add(replace);
                            }
                            mkCheckVideoUrls.setTime(0);
                            mkCheckVideoUrls.setUrl(v_start);
                            videourls.add(mkCheckVideoUrls);
                        }


                        if(15 == monitors.getJSONObject(k).getInteger("event")){
                            List<String> v_end = new ArrayList<>();
                            JSONArray urls1 =  monitors.getJSONObject(k).getJSONArray("urls");
                            v_end.add("http://adx.fxlxz.com/sl/v_end?vedioEnd=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                v_end.add(replace);
                            }
                            mkCheckVideoUrls.setTime(1);
                            mkCheckVideoUrls.setUrl(v_end);
                            videourls.add(mkCheckVideoUrls);
                        }

                    }
                    tb.setCheck_video_urls(videourls);//视频
                }
            bidList.add(tb);
            bidResponse.setId(id);//请求id
            bidResponse.setSeatbid(bidList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info(request.getImp().get(0).getTagid() + ":环睿总返回" + JSONObject.toJSONString(bidResponse));
            }
        }
        return bidResponse;
    }
}
