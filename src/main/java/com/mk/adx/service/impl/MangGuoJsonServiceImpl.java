package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mangguo.MangGuoAdspace;
import com.mk.adx.entity.json.request.mangguo.MangGuoBidRequest;
import com.mk.adx.entity.json.request.mangguo.MangGuoDevice;
import com.mk.adx.entity.json.request.mangguo.MangGuoUser;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.MangGuoJsonService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.MD5Util;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mzs
 * @Description 芒果api
 * @Date 2021/12/27 9:48
 */
@Slf4j
@Service("mangGuoJsonService")
public class MangGuoJsonServiceImpl implements MangGuoJsonService {

    private static final String name = "mangguo";

    private static final String source = "芒果";

    //    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public TzBidResponse getMangGuoDataByJson(TzBidRequest request) {
        MangGuoBidRequest bidRequest = new MangGuoBidRequest();
        MangGuoDevice device = new MangGuoDevice();
        MangGuoAdspace adspace = new MangGuoAdspace();
        MangGuoUser user = new MangGuoUser();

        //adspace对象
        adspace.setAdspace_id(request.getAdv().getTag_id()); // 广告位id
        adspace.setSupport_dl(true);
        adspace.setSupport_wx(false);
        adspace.setAdlen(1);
        adspace.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        adspace.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
        adspace.setMin_cpm(request.getAdv().getPrice());

        //user对象
        if(null != request.getUser()){
            String gender = request.getUser().getGender();
            if("M".equals(gender)){
                user.setGender(1);
            }else if("F".equals(gender)){
                user.setGender(2);
            }else{
                user.setGender(0);
            }
            user.setAge(request.getUser().getAge());

        }
        //设备具体信息 Device
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setImei(request.getDevice().getImei());
            device.setImei_md5(request.getDevice().getImei_md5());
            device.setOaid(request.getDevice().getOaid());
            device.setAndroid_id(request.getDevice().getAndroid_id());
            device.setAndroid_id_md5(request.getDevice().getAndroid_id_md5());
            device.setDevice_type(32);
            device.setOs("android");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setIdfa(request.getDevice().getIdfa());
            device.setOpenudid(request.getDevice().getOpen_udid());
            device.setDevice_type(34);
            device.setOs("ios");
            if(StringUtils.isNotEmpty(request.getDevice().getHardware_machine())){
                device.setHardware_machine(request.getDevice().getHardware_machine());
            }else{
                device.setHardware_machine(request.getDevice().getModel());
            }
//            if(StringUtils.isEmpty(request.getDevice().getBootTimeSec()) || StringUtils.isEmpty(request.getDevice().getOsUpdateTimeSec()) || StringUtils.isEmpty(request.getDevice().getPhoneName())){
//                return new TzBidResponse();
//            }
            device.setStartup_time(request.getDevice().getBootTimeSec());
            device.setMb_time(request.getDevice().getOsUpdateTimeSec());
            device.setCountry_code("CN");
            if(0 != request.getDevice().getMemorySize()){
                device.setMem_total(request.getDevice().getMemorySize());
            }else{
                device.setMem_total(16000000);
            }
            if(0 != request.getDevice().getDiskSize()){
                device.setDisk_total(request.getDevice().getDiskSize());
            }else{
                device.setDisk_total(256000000);
            }
            if(StringUtils.isNotEmpty(request.getDevice().getTimeZone())){
                device.setLocal_tz_name(request.getDevice().getTimeZone());
            }else{
                device.setLocal_tz_name("28800");
            }
            if(StringUtils.isNotEmpty(request.getDevice().getModelCode())){
                device.setHardware_model(request.getDevice().getModelCode());
            }else{
                device.setHardware_model("D22AP");
            }
            device.setOs_version(request.getDevice().getOsv());
            device.setLanguage("zh -Hans-CN");
            device.setPhone_name(request.getDevice().getPhoneName());
            device.setAuth_status(3);
            if(0 != request.getDevice().getCpuNumber()){
                device.setCpu_num(request.getDevice().getCpuNumber());
            }else{
                device.setCpu_num(4);
            }


        }
        device.setCookie("");
        device.setMac(request.getDevice().getMac());
        if(7 > Integer.valueOf(request.getDevice().getOsv().split("\\.")[0])){
            return new TzBidResponse();
        }
        device.setOsver(request.getDevice().getOsv());
        device.setBrand(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        device.setSw(request.getDevice().getW());
        device.setSh(request.getDevice().getH());
        device.setIp(request.getDevice().getIp());
        device.setUa(request.getDevice().getUa());
        device.setReferer("");
        String carrier = request.getDevice().getCarrier();
        if(StringUtils.isNotEmpty(carrier)){//运营商类型
            if("70120".equals(carrier)){
                device.setCarrier_type(0);
                if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
                    device.setCarrier_name("中国移动");
                }
            }else if("70123".equals(carrier)){
                device.setCarrier_type(1);
                if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
                    device.setCarrier_name("中国联通");
                }
            }else if("70121".equals(carrier)){
                device.setCarrier_type(3);
                if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
                    device.setCarrier_name("中国电信");
                }
            }else{
                device.setCarrier_type(0);
                if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
                    device.setCarrier_name("中国联通");
                }
            }
        }else{
            device.setCarrier_type(0);
        }
        String connectiontype = request.getDevice().getConnectiontype().toString();//网络连接类型
        if("0".equals(connectiontype)){
            device.setConnection_type(0);
        }else if("2".equals(connectiontype)){
            device.setConnection_type(1);
        }else if("4".equals(connectiontype)){
            device.setConnection_type(4);
        }else if("5".equals(connectiontype)){
            device.setConnection_type(2);
        }else if("6".equals(connectiontype)){
            device.setConnection_type(3);
        }else if("7".equals(connectiontype)){
            device.setConnection_type(5);
        } else {
            device.setConnection_type(3);
        }
        device.setOrientation(1);
        if(null != request.getDevice().getGeo()){
            device.setLg(request.getDevice().getGeo().getLon());
            device.setLt(request.getDevice().getGeo().getLat());
        }
        device.setPkgname(request.getAdv().getBundle());
        device.setApp_version(request.getAdv().getVersion());
        device.setSsid("");
        device.setWifi_mac("20:a6:cd:7e:e3:60");
        device.setRom_version(request.getDevice().getOsv());
        device.setSys_compling_time("1545362006000");
        long i1 = System.currentTimeMillis();
        bidRequest.setSign(MD5Util.getMD5(request.getId() + "_" + i1 + "_" + "e519c0a45378ea4b9c9d160ab87e3a16"));
        bidRequest.setTs(i1);
        bidRequest.setMid_id(request.getAdv().getApp_id());
        bidRequest.setReq_id(request.getId());
        bidRequest.setVersion("v3.5");
        bidRequest.setAdspace(adspace);
        bidRequest.setDevice(device);
        bidRequest.setUser(user);

        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(bidRequest);
        log.info("芒果广告请求参数"+JSONObject.parseObject(content));
        String url = "http://anet.da.mgtv.com/ssp/api/ad";
        String ua = request.getDevice().getUa();//ua
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
        log.info("请求上游芒果广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+"-芒果广告返回参数:" + JSONObject.parseObject(response));
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            if (200 == jo.getInteger("error_code")) {
                List<TzMacros> tzMacros1 = new ArrayList();
                TzMacros tzMacros = new TzMacros();
                List<TzSeat> seatList = new ArrayList<>();
                String id = request.getId();
                //多层解析json
                JSONArray imp = jo.getJSONArray("ads");
                List<TzBid> bidList = new ArrayList<>();
                for (int i = 0; i<imp.size(); i++) {
                    TzBid tb = new TzBid();
                    TzVideo tzVideo = new TzVideo();
                    tb.setId(jo.getString("id"));
                    if ("1".equals(request.getImp().get(0).getAd_slot_type())) {
                        TzNative tzNative = new TzNative();
                        tb.setAd_type(8);//信息流-广告素材类型
                        if ("1".equals(imp.getJSONObject(i).getString("material_type"))) { //字符串，1. 图片 (IMAGE)；2．视频 （VIDEO）
                            ArrayList<TzImage> images = new ArrayList<>();
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(imp.getJSONObject(i).getString("material_url"));
                            tzImage.setH(imp.getJSONObject(i).getInteger("material_height"));
                            tzImage.setW(imp.getJSONObject(i).getInteger("material_width"));
                            images.add(tzImage);
                            tzNative.setImages(images);
                        } else if ("2".equals(imp.getJSONObject(i).getString("material_type"))) {
                            // 信息流视频
                            tzVideo.setUrl(imp.getJSONObject(i).getString("material_url"));
                            tzVideo.setH(imp.getJSONObject(i).getInteger("material_height"));
                            tzVideo.setW(imp.getJSONObject(i).getInteger("material_width"));
                            tzVideo.setDuration(imp.getJSONObject(i).getInteger("duration"));
                            tzNative.setVideo(tzVideo);
                        }
                        tzNative.setTitle(imp.getJSONObject(i).getString("title"));
                        tzNative.setDesc(imp.getJSONObject(i).getString("desc"));
                        tb.setNATIVE(tzNative);
                    } else {
                        if ("1".equals(imp.getJSONObject(i).getString("material_type"))) { //字符串，1. 图片 (IMAGE)；2．视频 （VIDEO）
                            tb.setAd_type(2);//开屏-广告素材类型
                            ArrayList<TzImage> images = new ArrayList<>();
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(imp.getJSONObject(i).getString("material_url"));
                            tzImage.setH(imp.getJSONObject(i).getInteger("material_height"));
                            tzImage.setW(imp.getJSONObject(i).getInteger("material_width"));
                            images.add(tzImage);
                            tb.setImages(images);
                        } else if ("2".equals(imp.getJSONObject(i).getString("material_type"))) {
                            // 开屏视频
                            tzVideo.setUrl(imp.getJSONObject(i).getString("material_url"));
                            tzVideo.setH(imp.getJSONObject(i).getInteger("material_height"));
                            tzVideo.setW(imp.getJSONObject(i).getInteger("material_width"));
                            tzVideo.setDuration(imp.getJSONObject(i).getInteger("duration"));
                            tb.setVideo(tzVideo);//视频素材
                            tb.setTitle(imp.getJSONObject(i).getString("title"));
                            tb.setDesc(imp.getJSONObject(i).getString("desc"));
                        }

                    }
                    Integer action = imp.getJSONObject(i).getInteger("click_action");
                    if (1 == action) {
                        tb.setClicktype("1");//点击
                    } else if (2 == action) {
                        tb.setClicktype("4");//跳转
                    } else {
                        tb.setClicktype("0");//点击
                    }
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("schema_url"));
                    List<String> deep_linkT = new ArrayList<>();
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("schema_url"))){
                        deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                    tb.setClick_url(imp.getJSONObject(i).getString("landing_page_url")); // 点击跳转url地址
                    JSONArray eventTracks = imp.getJSONObject(i).getJSONArray("impression_urls");

                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    String encodev = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encodev));
                    tzMacros1.add(tzMacros);

                    for (int et = 0; et < eventTracks.size(); et++) {
                        if (103 == (eventTracks.getJSONObject(et).getInteger("event"))) {
                            deep_linkT.add(eventTracks.getJSONObject(et).getString("url"));
                        }
                        if (104 == (eventTracks.getJSONObject(et).getInteger("event"))) {
                            List<String> deep_linkF = new ArrayList<>();
                            deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_FAIL%%");
                            deep_linkF.add(eventTracks.getJSONObject(et).getString("url"));
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_fail_deeplinks(deep_linkF);//唤醒失败
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DEEP_LINK_FAIL%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (0 == (eventTracks.getJSONObject(et).getInteger("event"))) {
                            check_views.add(eventTracks.getJSONObject(et).getString("url"));
                        }
                    }
                    tb.setCheck_success_deeplinks(deep_linkT);//唤醒成功
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    if (null != imp.getJSONObject(i).getJSONArray("click_urls") && 0 < imp.getJSONObject(i).getJSONArray("click_urls").size()) {
                        List<String> clickList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("click_urls");
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < urls1.size(); cc++) {
                            clickList.add(urls1.get(cc).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

//                     if("19".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
//                         if(null != tb.getcheckv){
//
//                         }else{
//                             List<String> voidStartList = new ArrayList<>();
//                             voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
//                             voidStartList.add(eventTracks.getJSONObject(et).getString("notify_url"));
//                             String encode = eventTracks.getJSONObject(et).getString("notify_url") + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                             tb.setCheck_views(voidStartList);//视频开始播放
//                             tzMacros = new TzMacros();
//                             tzMacros.setMacro("%%VEDIO_START%%");
//                             tzMacros.setValue(Base64.encode(encode));
//                             tzMacros1.add(tzMacros);
//                         }
//                     }
//
//                     if("22".equals(eventTracks.getJSONObject(et).getString("event_type"))) {
//                         List<String> voidEndList = new ArrayList<>();
//                         voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
//                         voidEndList.add(eventTracks.getJSONObject(et).getString("notify_url"));
//                         String encode = eventTracks.getJSONObject(et).getString("notify_url") + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                         tb.setCheck_clicks(voidEndList);//视频播放结束
//                         tzMacros = new TzMacros();
//                         tzMacros.setMacro("%%VEDIO_END%%");
//                         tzMacros.setValue(Base64.encode(encode));
//                         tzMacros1.add(tzMacros);
//                     }

                    if (null != imp.getJSONObject(i).getJSONArray("download_track_urls") && 0 < imp.getJSONObject(i).getJSONArray("download_track_urls").size()) {
                        List<String> downLoadList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("download_track_urls");
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
                    if (null != imp.getJSONObject(i).getJSONArray("downloaded_track_urls") && 0 < imp.getJSONObject(i).getJSONArray("downloaded_track_urls").size()) {
                        List<String> downLoadDList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("downloaded_track_urls");
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
                    if (null != imp.getJSONObject(i).getJSONArray("installed_track_urls") && 0 < imp.getJSONObject(i).getJSONArray("installed_track_urls").size()) {
                        List<String> installList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("installed_track_urls");
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
                    if (null != imp.getJSONObject(i).getJSONArray("open_track_urls") && 0 < imp.getJSONObject(i).getJSONArray("open_track_urls").size()) {
                        List<String> installEList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("open_track_urls");
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
                log.info(request.getImp().get(0).getTagid()+"-芒果广告总返回:"+JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }
}
