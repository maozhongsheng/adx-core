package com.mk.adx.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mitu.MituBidRequest;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.MD5Util;
import com.mk.adx.service.MiTuJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author mzs
 * @Description 觅途
 * @Date 2022/4/22 16:30
 */
@Slf4j
@Service("miTuJsonService")
public class MiTuServiceImpl implements MiTuJsonService {


    private static final String name = "mitu";

    private static final String source = "觅途";

    @SneakyThrows
    @Override
    public TzBidResponse getMiTuDataByJson(TzBidRequest request) {
        MituBidRequest bidRequest = new MituBidRequest();
        String slot_type = request.getAdv().getSlot_type();
        if("信息流".equals(slot_type)){
            bidRequest.setAd_type(4);
        }else if("开屏".equals(slot_type)){
            bidRequest.setAd_type(2);
        }else if("插屏".equals(slot_type)){
            bidRequest.setAd_type(3);
        }else if("Banner".equals(slot_type)){
            bidRequest.setAd_type(1);
        }else if("激励视频".equals(slot_type)){
            bidRequest.setAd_type(5);
        }
        bidRequest.setApp_name(request.getAdv().getApp_name());
        bidRequest.setApp_version(request.getAdv().getVersion());
        bidRequest.setApp_version_code(119);
        bidRequest.setPkg_name(request.getAdv().getBundle());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            bidRequest.setPlatform(1);
            bidRequest.setOs("Android");
            bidRequest.setAndroid_id(request.getDevice().getAndroid_id());
            bidRequest.setAuidmd5(request.getDevice().getAndroid_id_md5());
            bidRequest.setAuidsha1(request.getDevice().getAndroid_id_sha1());
            bidRequest.setImei(request.getDevice().getImei());
            bidRequest.setImeimd5(request.getDevice().getImei_md5());
            bidRequest.setImeisha1(request.getDevice().getImei_sha1());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            bidRequest.setPlatform(2);
            bidRequest.setOs("iOS");
            bidRequest.setIdfa(request.getDevice().getIdfa());
            bidRequest.setIdfv(request.getDevice().getIdfv());
            bidRequest.setOpenudid(request.getDevice().getOpen_udid());
            bidRequest.setCaid(request.getDevice().getCaid());
            bidRequest.setOs_compiling_time(request.getDevice().getBootTimeSec());
            bidRequest.setOs_update_time(request.getDevice().getOsUpdateTimeSec());
            bidRequest.setOs_startup_time(request.getDevice().getBootTimeSec());
            bidRequest.setPhone_name(request.getDevice().getPhoneName());
            bidRequest.setPhone_name_md5(MD5Util.getMD5(request.getDevice().getPhoneName()));
            bidRequest.setSys_memory(String.valueOf(request.getDevice().getMemorySize()));
            bidRequest.setSys_disksize(String.valueOf(request.getDevice().getDiskSize()));
            bidRequest.setCpu_num(request.getDevice().getCpuNumber());
            bidRequest.setHardware_machine(request.getDevice().getHardware_machine());
        }else{
            bidRequest.setPlatform(0);
        }
        bidRequest.setCountry("CN");
        bidRequest.setLanguage(request.getDevice().getLanguage());
        bidRequest.setMake(request.getDevice().getMake());
        bidRequest.setModel(request.getDevice().getModel());
        bidRequest.setBrand(request.getDevice().getMake());
        bidRequest.setHwv(request.getDevice().getHwv());
        bidRequest.setOs_version(request.getDevice().getOsv());
        bidRequest.setOsapilevel(10);
        bidRequest.setIp(request.getDevice().getIp());
        bidRequest.setUa(request.getDevice().getUa());
        bidRequest.setMac(request.getDevice().getMac());
        bidRequest.setMacmd5(request.getDevice().getMac_md5().toLowerCase(Locale.ROOT));
        bidRequest.setDensity(String.valueOf(request.getDevice().getDeny()));
        if(null != request.getDevice().getPpi()){
            bidRequest.setPpi(request.getDevice().getPpi());
        }
        bidRequest.setDpi(240);
        bidRequest.setScreen_width(request.getDevice().getW());
        bidRequest.setScreen_height(request.getDevice().getH());
        bidRequest.setScreen_orient(1);
       // bidRequest.setApp_store_version();
       // bidRequest.setRom_version();
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            bidRequest.setNet_type(0);
        }else if("1".equals(connectiontype)){
            bidRequest.setNet_type(0);
        }else if("2".equals(connectiontype)){
            bidRequest.setNet_type(1);
        }else if("3".equals(connectiontype)){
            bidRequest.setNet_type(0);
        }else if("4".equals(connectiontype)){
            bidRequest.setNet_type(2);
        }else if("5".equals(connectiontype)){
            bidRequest.setNet_type(3);
        }else if("6".equals(connectiontype)){
            bidRequest.setNet_type(4);
        }else if("7".equals(connectiontype)){
            bidRequest.setNet_type(5);
        } else {
            bidRequest.setNet_type(0);
        }
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            bidRequest.setCarrier(1);
        }else if("70123".equals(carrier)){
            bidRequest.setCarrier(2);
        }else if("70121".equals(carrier)){
            bidRequest.setCarrier(3);
        }else{
            bidRequest.setCarrier(2);
        }
        bidRequest.setBssid("");
        bidRequest.setWifi_mac(request.getDevice().getMac());
        bidRequest.setSerialno("");
        bidRequest.setGps_type(3);
        if(null != request.getDevice().getGeo()){
            bidRequest.setLatitude(request.getDevice().getGeo().getLat());
            bidRequest.setLongitude(request.getDevice().getGeo().getLon());
            bidRequest.setGps_ts(request.getDevice().getGeo().getUtcoffset());
        }
        bidRequest.setAd_width(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        bidRequest.setAd_height(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
        bidRequest.setSupport_deeplink(1);
        bidRequest.setSupport_universal(1);



        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + ":请求觅途参数" + JSONObject.parseObject(content));
        String url = "http://jinli.himetoo.com:8080/mad/base/api/media?ad_code=1084";
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
        log.info(request.getImp().get(0).getTagid() + ":请求觅途传广花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info(request.getImp().get(0).getTagid() + ":觅途返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            String id = request.getId();
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            if (0 == jo.getInteger("code")) {
                JSONObject data = jo.getJSONObject("data");
                TzBid tb = new TzBid();
                List<TzBid> bidList = new ArrayList<>();
                List<TzImage> list = new ArrayList<>();
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    ArrayList<TzImage> images = new ArrayList<>();
                    TzLogo tzLogo = new TzLogo();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray assets = data.getJSONArray("imgs");
                    for (int na = 0; na < assets.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(assets.getString(na));
                        tzImage.setH(data.getInteger("ad_height"));
                        tzImage.setW(data.getInteger("ad_width"));
                        images.add(tzImage);
                    }
                    tzLogo.setUrl(data.getString("ad_logo"));
                    tzNative.setTitle(data.getString("title"));
                    tzNative.setDesc(data.getString("desc"));
                    tzNative.setImages(images);
                    tb.setNATIVE(tzNative);
                }else if ("4".equals(request.getImp().get(0).getAd_slot_type())){
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONObject videos = data.getJSONObject("video");
                    if (null != videos) {
                        video.setUrl(videos.getString("url"));
                        video.setW(videos.getInteger("width"));
                        video.setH(videos.getInteger("height"));
                        tb.setTitle(data.getString("title"));
                        tb.setDesc(data.getString("desc"));
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
                    JSONArray assets = data.getJSONArray("imgs");
                    for (int na = 0; na < assets.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(assets.getString(na));
                        tzImage.setH(data.getInteger("ad_height"));
                        tzImage.setW(data.getInteger("ad_width"));
                        images.add(tzImage);
                    }
                    tb.setImages(images);
                    tb.setTitle(data.getString("title"));
                    tb.setDesc(data.getString("desc"));
                    tb.setAdLogo(data.getString("ad_logo"));
                }


                TzBidApps apps = new TzBidApps();
                JSONObject appinfo = data.getJSONObject("appinfo");
                apps.setApp_name(appinfo.getString("app_name"));//应用名称
                apps.setBundle(appinfo.getString("package_name"));//应用包名
                tb.setApp(apps);




                Integer interaction_type = data.getInteger("interaction_type");
                if(0 == interaction_type){
                    tb.setClicktype("1");//跳转
                }else if(1 == interaction_type){
                    tb.setClicktype("4");//下载
                    Integer is_gdt = data.getInteger("is_gdt");
                    if(null != is_gdt){
                        if(1 == is_gdt){
                            tb.setClicktype("3");//广点通下载
                        }
                    }
                }else if(2 == interaction_type){
                    tb.setClicktype("2");//deeplink
                }


                if(StringUtils.isNotEmpty(data.getString("deeplink"))){
                    tb.setDeeplink_url(data.getString("deeplink"));
                }
                tb.setClick_url(data.getString("clk"));



                JSONObject bext = data.getJSONObject("event");
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
                if (null != bext.getJSONArray("start_down")) {
                    JSONArray sdtrackers = bext.getJSONArray("start_down");
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
                if (null != bext.getJSONArray("open_app_success")) {
                    JSONArray dptrackers = bext.getJSONArray("open_app_success");
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
                if (null != bext.getJSONArray("down_done")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("down_done");
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
                if (null != bext.getJSONArray("start_install")) {
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("start_install");
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
                if (null != bext.getJSONArray("install_done")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("install_done");
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
                bidList.add(tb);//
                tzSeat.setBid(bidList);
                seatList.add(tzSeat);
                bidResponse.setId(id);//请求id
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info(request.getImp().get(0).getTagid() + ":觅途总返回" + JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }

}
