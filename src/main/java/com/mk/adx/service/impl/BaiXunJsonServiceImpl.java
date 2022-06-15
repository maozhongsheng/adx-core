package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.baixun.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.tz.TzImp;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.BaiXunJsonService;
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
@Service("baiXunJsonService")
public class BaiXunJsonServiceImpl implements BaiXunJsonService {

    private static final String name = "百寻";

    private static final String source = "百寻";

    @SneakyThrows
    @Override
    public TzBidResponse getBaiXunDataByJson(TzBidRequest request) {
        BaiXunBidRequest bidRequest = new BaiXunBidRequest();
        List<BaiXunImp> imps = new ArrayList();

        BaiXunDevice device = new BaiXunDevice();
        BaiXunUser user = new BaiXunUser();
        BaiXunApp app = new BaiXunApp();
        BaiXunSite site = new BaiXunSite();
        BaiXunGeo geo = new BaiXunGeo();
        //imp
        List<TzImp> tzImps = request.getImp();
        for(int tzi = 0; tzi < tzImps.size(); tzi++){
            BaiXunImp imp = new BaiXunImp();
            imp.setId(request.getImp().get(tzi).getId());
            imp.setTagid(request.getAdv().getTag_id()); //
            imp.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
            imp.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
            if("1".equals(tzImps.get(tzi).getAd_slot_type())){
                BaiXunNative baiXunNative = new BaiXunNative();
                BaiXunTitle baiXunTitle = new BaiXunTitle();
                BaiXunDesc baiXunDesc = new BaiXunDesc();
                List<BaiXunImage> baiXunImages = new ArrayList();
                BaiXunImage baiXunImage = new BaiXunImage();
                baiXunTitle.setRequired(1);
                baiXunDesc.setRequired(1);
                baiXunImage.setH(tzImps.get(tzi).getNATIVE().getH());
                baiXunImage.setW(tzImps.get(tzi).getNATIVE().getW());
                baiXunImage.setRequired(1);
                baiXunImage.setSn(1);
                baiXunImages.add(baiXunImage);
                baiXunNative.setTitle(baiXunTitle);
                baiXunNative.setDesc(baiXunDesc);
                baiXunNative.setImages(baiXunImages);
                imp.setNative_ad(baiXunNative);
            }else if("4".equals(tzImps.get(tzi).getAd_slot_type())){
                BaiXunVideo baiXunVideo = new BaiXunVideo();
                baiXunVideo.setH(tzImps.get(tzi).getVideo().getH());
                baiXunVideo.setW(tzImps.get(tzi).getVideo().getW());
                baiXunVideo.setMimes("mp4");
                baiXunVideo.setMinduration(tzImps.get(tzi).getVideo().getMinduration());
                baiXunVideo.setMaxduration(tzImps.get(tzi).getVideo().getMaxduration());
                baiXunVideo.setVideotype(0);
                imp.setVideo(baiXunVideo);
            }else {
                if(null != tzImps.get(tzi).getNATIVE()){
                    BaiXunBanner baiXunBanner = new BaiXunBanner();
                    baiXunBanner.setH(tzImps.get(tzi).getNATIVE().getH());
                    baiXunBanner.setW(tzImps.get(tzi).getNATIVE().getW());
                    baiXunBanner.setMimes("img");
                    String ad_slot_type = tzImps.get(tzi).getAd_slot_type();
                    if("2".equals(ad_slot_type) || "5".equals(ad_slot_type)){
                        baiXunBanner.setType(0);
                    }else if("6".equals(ad_slot_type)){
                        baiXunBanner.setType(2);
                    }else{
                        baiXunBanner.setType(1);
                    }
                    imp.setBanner(baiXunBanner);
                }else{
                    BaiXunBanner baiXunBanner = new BaiXunBanner();
                    baiXunBanner.setH(tzImps.get(tzi).getBanner().getH());
                    baiXunBanner.setW(tzImps.get(tzi).getBanner().getW());
                    baiXunBanner.setMimes("img");
                    String ad_slot_type = tzImps.get(tzi).getAd_slot_type();
                    if("2".equals(ad_slot_type) || "5".equals(ad_slot_type)){
                        baiXunBanner.setType(0);
                    }else if("6".equals(ad_slot_type)){
                        baiXunBanner.setType(2);
                    }else{
                        baiXunBanner.setType(1);
                    }
                    imp.setBanner(baiXunBanner);
                }
            }
            imps.add(imp);
        }

        //app
        app.setId(request.getAdv().getApp_id()); //
        app.setName(request.getAdv().getApp_name());
        app.setBundle(request.getAdv().getBundle());
        app.setVer(request.getAdv().getVersion());

        //device
        device.setUa(request.getDevice().getUa());
        device.setIpv4(request.getDevice().getIp());
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            device.setType(1);
        }else if("ipad".equals(devicetype)){
            device.setType(2);
        }else if("pc".equals(devicetype)){
            device.setType(3);
        }else {
            device.setType(1);
        }
        device.setMake(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        device.setModel_type(request.getDevice().getModel());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs("android");
            device.setImei(request.getDevice().getImei());
            device.setImeimd5(request.getDevice().getImei_md5());
            device.setImeisha1(request.getDevice().getImei_sha1());
            device.setAndroidid(request.getDevice().getAndroid_id());
            device.setAndroididmd5(request.getDevice().getAndroid_id_md5());
            device.setAndroididsha1(request.getDevice().getAndroid_id_sha1());
            device.setOaid(request.getDevice().getOaid());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOs("ios");
            device.setIdfa(request.getDevice().getIdfa());
            device.setIdfamd5(request.getDevice().getIdfa_md5());
            device.setIdfasha1(request.getDevice().getIdfa_sha1());
            device.setIdfv(request.getDevice().getIdfv());

        }
        device.setOsv(request.getDevice().getOsv());
        device.setW(request.getDevice().getW());
        device.setH(request.getDevice().getH());
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            device.setCarrier("46002");
        }else if("70123".equals(carrier)){
            device.setCarrier("46001");
        }else if("70121".equals(carrier)){
            device.setCarrier("46003");
        }else{
            device.setCarrier("46001");
        }
        device.setLanguage(request.getDevice().getLanguage());
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            device.setConnection(0);
        }else if("1".equals(connectiontype)){
            device.setConnection(1);
        }else if("2".equals(connectiontype)){
            device.setConnection(6);
        }else if("3".equals(connectiontype)){
            device.setConnection(1);
        }else if("4".equals(connectiontype)){
            device.setConnection(2);
        }else if("5".equals(connectiontype)){
            device.setConnection(3);
        }else if("6".equals(connectiontype)){
            device.setConnection(4);
        }else if("7".equals(connectiontype)){
            device.setConnection(5);
        } else {
            device.setConnection(6);
        }
        device.setMac(request.getDevice().getMac());
        device.setMacmd5(request.getDevice().getMac_md5());
        device.setMacsha1(request.getDevice().getMac_sha1());
        //geo
        if(null != request.getDevice().getGeo()){
            geo.setLat(request.getDevice().getGeo().getLat());
            geo.setLon(request.getDevice().getGeo().getLon());
        }
        device.setGeo(geo);
        device.setDensity((float)(request.getDevice().getDeny()));
        device.setBoot_mark(request.getDevice().getBootTimeSec());
        device.setUpdate_mark(request.getDevice().getOsUpdateTimeSec());
        device.setSys_compile_ts((int)System.currentTimeMillis());

        bidRequest.setId(request.getId());
        bidRequest.setImp(imps);
        bidRequest.setDevice(device);
        bidRequest.setUser(user);
        bidRequest.setApp(app);
        bidRequest.setSite(site);
        bidRequest.setHttps(0);
        bidRequest.setDeeplink(1);



        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求百寻广告参数"+JSONObject.parseObject(content));
        String url = "http://ds.vipliangmei.com/tianzhuo";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游百寻广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":百寻广告返回参数"+JSONObject.parseObject(response));

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
                tb.setImpid(imp.getJSONObject(i).getString("impid"));
                tb.setPrice((float)imp.getJSONObject(i).getInteger("price"));
                if(null != tzImps.get(0).getNATIVE()){
                    if("1".equals(request.getImp().get(0).getAd_slot_type())){
                        TzNative tzNative = new TzNative();
                        ArrayList<TzImage> images = new ArrayList<>();
                        tb.setAd_type(8);//信息流-广告素材类型
                        JSONObject native_ad = imp.getJSONObject(i).getJSONObject("native_ad");
                        tzNative.setTitle(native_ad.getString("title"));
                        tzNative.setDesc(native_ad.getString("desc"));
                        JSONArray img = native_ad.getJSONArray("images");
                        for(int im = 0;im < img.size(); im++){
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(img.getJSONObject(im).getString("imageurl"));
                            tzImage.setH(img.getJSONObject(im).getInteger("h"));
                            tzImage.setW(img.getJSONObject(im).getInteger("w"));
                            images.add(tzImage);
                        }
                        tzNative.setImages(images);
                        tb.setNATIVE(tzNative);
                    }
                }else if(null != tzImps.get(0).getBanner()){
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
                }else if (null != tzImps.get(0).getVideo()){
                    TzNative tzNative = new TzNative();
                    TzVideo video = new TzVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONObject videos = imp.getJSONObject(i).getJSONObject("video");
                    if (null != videos) {
                        video.setUrl(videos.getString("url"));
                        video.setH(videos.getInteger("h"));
                        video.setW(videos.getInteger("w"));
                        video.setDuration(videos.getInteger("duration"));
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(videos.getString("main"));
                        video.setConver_image(tzImage);
                        tzNative.setTitle(videos.getString("title"));
                        tzNative.setDesc(videos.getString("icon"));
                        tzNative.setVideo(video);
                    }
                }


                if(null != imp.getJSONObject(i).getJSONObject("app")){
                    TzBidApps tzBidApps = new TzBidApps();
                    tzBidApps.setApp_icon(imp.getJSONObject(i).getJSONObject("app").getString("icon"));
                    tzBidApps.setApp_name(imp.getJSONObject(i).getJSONObject("app").getString("name"));
                    tzBidApps.setBundle(imp.getJSONObject(i).getJSONObject("app").getString("bundle"));

                    if (null != imp.getJSONObject(i).getJSONObject("app").getJSONArray("downloadstarttrack")) {
                        JSONArray sdtrackers = imp.getJSONObject(i).getJSONObject("app").getJSONArray("downloadstarttrack");
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
                    if (null != imp.getJSONObject(i).getJSONObject("app").getJSONArray("downloadtrack")) {
                        List<String> downLoadDList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("app").getJSONArray("downloadtrack");
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
                    if (null != imp.getJSONObject(i).getJSONObject("app").getJSONArray("installstarttrack")) {
                        List<String> installList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONObject("app").getJSONArray("installstarttrack");
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
                    if (null != imp.getJSONObject(i).getJSONObject("app").getJSONArray("installtrack")) {
                        List<String> installEList = new ArrayList<>();
                        JSONArray urls1 = imp.getJSONObject(i).getJSONObject("app").getJSONArray("installtrack");
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

                }


                Integer clickaction = imp.getJSONObject(i).getInteger("clickaction");
                if(0 == clickaction){
                    tb.setClicktype("1");//跳转
                    tb.setClick_url(imp.getJSONObject(i).getString("landing")); // 点击跳转url地址
                }else if(1 == clickaction){
                    tb.setClicktype("4");//下载
                    tb.setDownload_url(imp.getJSONObject(i).getString("landing")); // 下载url地址
                }

                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink"));
                }
                JSONObject bext = imp.getJSONObject(i);
                if (null != bext.getJSONArray("imp")) {
                    JSONArray apv = bext.getJSONArray("imp");
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    for (int cv = 0; cv < apv.size(); cv++) {
                        check_views.add(apv.get(cv).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if (null != bext.getJSONArray("clk")) {
                    JSONArray aclick = bext.getJSONArray("clk");
                    List<String> clickList = new ArrayList<>();
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < aclick.size(); cc++) {
                        String replace = aclick.get(cc).toString().replace("__WIDTH__", "%%WIDTH%%").replace("__HEIGHT__", "%%HEIGHT%%").replace("__clk_x__", "%%DOWN_X%%").replace("__clk_y__", "%%DOWN_Y%%").replace("__clk_up_x__", "%%UP_X%%").replace("__clk_up_y__", "%%UP_Y%%").replace("__clk_time__ ", "%%TS%%").replace("__clk_abs_x__ ", "%%ABS_DOWN_X%%").replace("__clk_abs_y__ ", "%%ABS_DOWN_Y%%").replace("__clk_up_abs_x__ ", "%%ABS_UP_X%%").replace("__clk_up_abs_y__ ", "%%ABS_UP_Y%%");
                        clickList.add(replace);
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                }

                if (null != bext.getJSONArray("deeplink_track")) {
                    JSONArray dptrackers = bext.getJSONArray("deeplink_track");
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
            log.info(request.getImp().get(0).getTagid()+":百寻总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
