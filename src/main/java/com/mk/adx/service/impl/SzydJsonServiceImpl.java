package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.szyd.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.SzydJsonService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author mzs
 * @Description
 * @date 2022/4/15 15:21
 */
@Slf4j
@Service("szydJsonService")
public class SzydJsonServiceImpl implements SzydJsonService {

    private static final String name = "数字悦动";

    private static final String source = "数字悦动";

    @SneakyThrows
    @Override
    public TzBidResponse getSzydDataByJson(TzBidRequest request) {
        SzydBidRequest bidRequest = new SzydBidRequest();
        SzydDevice device = new SzydDevice();
        SzydImp simp = new SzydImp();
        SzydApp app = new SzydApp();
        SzydGeo geo = new SzydGeo();
        SzydImpExt impExt = new SzydImpExt();
        List<SzydImp> imps = new ArrayList();

        //imp
        simp.setId(request.getId());
        simp.setTagid(request.getAdv().getTag_id()); //request.getAdv().getTag_id()
        simp.setSecure(0);
        simp.setBidfloor(0);
        //impExt
        impExt.setDeeplink(1);
        impExt.setUl(1);
        simp.setExt(impExt);
        imps.add(simp);
        //app
        app.setId(request.getAdv().getApp_id());
        app.setName(request.getAdv().getApp_name());
        app.setVer(request.getAdv().getVersion());
        app.setBundle(request.getAdv().getBundle());
        //device
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs("Android");
            device.setDid(request.getDevice().getImei());
            device.setDidsha1(request.getDevice().getImei_sha1());
            device.setDidmd5(request.getDevice().getImei_md5());
            device.setOid(request.getDevice().getOaid());
            device.setDpid(request.getDevice().getAndroid_id());
            device.setDpidmd5(request.getDevice().getAndroid_id_md5());
            device.setDpidsha1(request.getDevice().getAndroid_id_sha1());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs("iOS");
            device.setIfa(request.getDevice().getIdfa().toUpperCase(Locale.ROOT));
        }
        device.setOsv(request.getDevice().getOsv());
        device.setIp(request.getDevice().getIp());
        device.setIpv6(request.getDevice().getIpv6());
        device.setUa(request.getDevice().getUa());

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
        }else {
            device.setDevicetype(4);
        }

        device.setMake(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        device.setHwv(request.getDevice().getHwv());
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            device.setCarrier("mobile");
        }else if("70123".equals(carrier)){
            device.setCarrier("unicom");
        }else if("70121".equals(carrier)){
            device.setCarrier("telecom");
        }else{
            device.setCarrier("unknown");
        }
        device.setMacmd5(request.getDevice().getMac_md5());
        device.setMacsha1(request.getDevice().getMac_sha1());
        device.setH(request.getDevice().getH());
        device.setW(request.getDevice().getW());
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());
        }
        device.setWifi_mac(request.getDevice().getMac());
        //geo
        if(null != request.getDevice().getGeo()){
            geo.setLat(request.getDevice().getGeo().getLat());
            geo.setLon(request.getDevice().getGeo().getLon());
        }
        device.setGeo(geo);

        bidRequest.setToken("ee2a5cd7-d14a-50a1-8e95-3f471e69d376"); //request.getAdv().getApp_id()
        bidRequest.setId(request.getId());
        bidRequest.setImp(imps);
        bidRequest.setApp(app);
        bidRequest.setDevice(device);

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求数字悦动广告参数"+JSONObject.parseObject(content));
        String url = "http://bid.adx.datads.cn/index";
        String ua = request.getDevice().getUa();
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数
        pud.setHeaderSzyd("gzip");
        pud.setSzyd_ip(request.getDevice().getIp());
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid()+":请求上游数字悦动广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":数字悦动广告返回参数"+JSONObject.parseObject(response));

        if (null != response && null != JSONObject.parseObject(response).getJSONObject("seatbid")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONArray imp =  JSONObject.parseObject(response).getJSONObject("seatbid").getJSONArray("bid");
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
                tb.setId(JSONObject.parseObject(response).getString("bidid"));
                if("1".equals(request.getImp().get(0).getAd_slot_type())){
                    TzNative tzNative = new TzNative();
                    ArrayList<TzImage> images = new ArrayList<>();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray assets = imp.getJSONObject(i).getJSONArray("assets");
                    for (int na = 0; na < assets.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(assets.getJSONObject(na).getJSONObject("img").getString("url"));
                        tzImage.setH(assets.getJSONObject(na).getJSONObject("img").getInteger("h"));
                        tzImage.setW(assets.getJSONObject(na).getJSONObject("img").getInteger("w"));
                        images.add(tzImage);
                    }
                    tzNative.setTitle(imp.getJSONObject(i).getString("title"));
                    tzNative.setDesc(imp.getJSONObject(i).getString("desc"));
                    tzNative.setImages(images);
                    tb.setNATIVE(tzNative);
                }else if ("4".equals(request.getImp().get(0).getAd_slot_type())){
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONArray assets = imp.getJSONObject(i).getJSONArray("assets");
                    JSONObject videos = assets.getJSONObject(0).getJSONObject("video");
                    if (null != videos) {
                        video.setUrl(videos.getString("url"));
                        tb.setTitle(imp.getJSONObject(i).getString("title"));
                        tb.setDesc(imp.getJSONObject(i).getString("desc"));
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
                    JSONArray assets = imp.getJSONObject(i).getJSONArray("assets");
                    for (int na = 0; na < assets.size(); na++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(assets.getJSONObject(na).getJSONObject("img").getString("url"));
                        tzImage.setH(assets.getJSONObject(na).getJSONObject("img").getInteger("h"));
                        tzImage.setW(assets.getJSONObject(na).getJSONObject("img").getInteger("w"));
                        images.add(tzImage);
                    }
                    tb.setImages(images);
                    tb.setTitle(imp.getJSONObject(i).getString("title"));
                    tb.setDesc(imp.getJSONObject(i).getString("desc"));
                }


                Integer action = imp.getJSONObject(i).getInteger("action");
                if(1 == action){
                    tb.setClicktype("1");//跳转
                }else if(2 == action){
                    Integer protocol_type = imp.getJSONObject(i).getInteger("protocol_type");
                    if(0 == protocol_type){
                        tb.setClicktype("4");//下载
                        tb.setDownload_url(imp.getJSONObject(i).getString("landing_url"));
                    }else if(1 == protocol_type){
                        tb.setClicktype("3");//下载
                        tb.setDownload_url(imp.getJSONObject(i).getString("landing_url"));
                    }

                }

                TzBidApps tzApp = new TzBidApps();
                if(0 < imp.getJSONObject(i).getJSONArray("icon_urls").size()){
                    tzApp.setApp_icon(imp.getJSONObject(i).getJSONArray("icon_urls").getString(0));
                }
                tzApp.setBundle(imp.getJSONObject(i).getString("package_name"));
                tzApp.setApp_size(imp.getJSONObject(i).getInteger("app_size"));
                tb.setApp(tzApp);


                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("landing_url"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("landing_url"));
                }
                tb.setClick_url(imp.getJSONObject(i).getString("target_url"));


                JSONObject bext = imp.getJSONObject(i).getJSONObject("tracking");


                if (null != bext.getJSONArray("imp_trackers")) {
                    JSONArray apv = bext.getJSONArray("imp_trackers");
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

                if (null != bext.getJSONArray("click_trackers")) {
                    JSONArray aclick = bext.getJSONArray("click_trackers");
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
                if (null != bext.getJSONArray("download_trackers")) {
                    JSONArray sdtrackers = bext.getJSONArray("download_trackers");
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
                if (null != bext.getJSONArray("dplk_trackers")) {
                    JSONArray dptrackers = bext.getJSONArray("dplk_trackers");
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
                if (null != bext.getJSONArray("downloaded_trackers")) {
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("downloaded_trackers");
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
                if (null != bext.getJSONArray("installed_trackers")) {
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 = bext.getJSONArray("installed_trackers");
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
            log.info(request.getImp().get(0).getTagid()+":数字悦动总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
