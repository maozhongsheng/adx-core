package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.chuangdian.CdBidRequest;
import com.mk.adx.entity.json.request.chuangdian.CdDevice;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.MD5Util;
import com.mk.adx.service.CdJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author mzs
 * @Description 创典
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("cdJsonService")
public class CdJsonServiceImpl implements CdJsonService {

    private static final String name = "cd";

    private static final String source = "创典";

    /**
     * @Author mzs
     * @Description 创典
     * @Date 2021/11/5 17:20
     * @return
     */
    @SneakyThrows
    @Override
    public TzBidResponse getCdDataByJson(TzBidRequest request) {
        CdBidRequest bidRequest = new CdBidRequest();

        bidRequest.setVersion("3.0");
        String timestamp = System.currentTimeMillis() + "";
        bidRequest.setTime(timestamp);

        bidRequest.setToken(MD5Util.getMD5(request.getAdv().getApp_id() + request.getAdv().getBundle() + timestamp));
        bidRequest.setReqid(UUID.randomUUID().toString().replace("-",""));
        bidRequest.setAppid(request.getAdv().getApp_id());
        bidRequest.setAppver(request.getAdv().getVersion());
        bidRequest.setAdspotid(request.getAdv().getTag_id());
        bidRequest.setIp(request.getDevice().getIp());
        bidRequest.setIpv6(request.getDevice().getIpv6());
        bidRequest.setUa(request.getDevice().getUa());
        bidRequest.setMake(request.getDevice().getMake());
        bidRequest.setModel(request.getDevice().getModel());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            bidRequest.setOs(2);
            bidRequest.setOaid(request.getDevice().getOaid());
            bidRequest.setImei(request.getDevice().getImei());
            bidRequest.setImei_md5(request.getDevice().getImei_md5());
            bidRequest.setMac(request.getDevice().getMac());
            bidRequest.setAndroidid(request.getDevice().getAndroid_id());
            bidRequest.setAndroidid_md5(request.getDevice().getAndroid_id_md5());
            bidRequest.setImsi("");
            bidRequest.setImsi_md5("");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            bidRequest.setOs(1);
            bidRequest.setIdfa(request.getDevice().getIdfa());
            bidRequest.setIdfa_md5(request.getDevice().getIdfa_md5());
            bidRequest.setIdfv(request.getDevice().getIdfv());
            bidRequest.setIdfv_md5("");
        }else{
            bidRequest.setOs(0);
        }
        bidRequest.setOsv(request.getDevice().getOsv());

        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){//运营商类型
            if("70120".equals(request.getDevice().getCarrier())){
                bidRequest.setCarrier("46008");
            }else if("70123".equals(request.getDevice().getCarrier())){
                bidRequest.setCarrier("46010");
            }else if("70121".equals(request.getDevice().getCarrier())){
                bidRequest.setCarrier("46009");
            }else{
                bidRequest.setCarrier("46008");
            }
        }else{
            bidRequest.setCarrier("46008");
        }

        String connectiontype = request.getDevice().getConnectiontype().toString();//网络连接类型
        if("0".equals(connectiontype)){
            bidRequest.setNetwork(0);
        }else if("1".equals(connectiontype)){
            bidRequest.setNetwork(0);
        }else if("2".equals(connectiontype)){
            bidRequest.setNetwork(1);
        }else if("3".equals(connectiontype)){
            bidRequest.setNetwork(6);
        }else if("4".equals(connectiontype)){
            bidRequest.setNetwork(2);
        }else if("5".equals(connectiontype)){
            bidRequest.setNetwork(3);
        }else if("6".equals(connectiontype)){
            bidRequest.setNetwork(4);
        }else if("7".equals(connectiontype)){
            bidRequest.setNetwork(5);
        } else {
            bidRequest.setNetwork(0);
        }
        bidRequest.setSw(request.getDevice().getW());
        bidRequest.setSh(request.getDevice().getH());
        if(null !=  request.getDevice().getPpi()){
            bidRequest.setPpi(request.getDevice().getPpi());
        }
        bidRequest.setImpsize(1);
        if(null != request.getDevice().getGeo()){
            bidRequest.setLat(request.getDevice().getGeo().getLat());
            bidRequest.setLon(request.getDevice().getGeo().getLon());
        }
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            bidRequest.setDevicetype(1);
        }else if("ipad".equals(devicetype)){
            bidRequest.setDevicetype(2);
        }else if("tv".equals(devicetype)){
            bidRequest.setDevicetype(3);
        }
        bidRequest.setDonottrack(0);
        CdDevice cdDevice = new CdDevice();


        if(14 < Integer.valueOf(request.getDevice().getOsv().split("\\.")[0])){
            cdDevice.setAaid("");
            cdDevice.setBoot_mark("");
            cdDevice.setUpdate_mark("");
        }

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求创典广告参数"+JSONObject.parseObject(content));
        String url = "http://123.207.134.151/raddus";
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
        log.info("请求上游创典广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("创典广告返回参数"+JSONObject.parseObject(response));
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            if (200 == jo.getInteger("code")) {
                List<TzMacros> tzMacros1 = new ArrayList();
                TzMacros tzMacros = new TzMacros();
                List<TzSeat> seatList = new ArrayList<>();
                String id = request.getId();
                //多层解析json
                // String msg = jo.getString("msg");//广告响应说明
                JSONArray imp = JSONObject.parseArray(jo.get("imp").toString());
                List<TzBid> bidList = new ArrayList<>();
                for (int i = 0; i < imp.size(); i++) {
                    TzBid tb = new TzBid();
                    tb.setId(imp.getJSONObject(i).getString("impid"));
                    TzVideo video = new TzVideo();
                    Integer h = 0;
                    Integer w = 0;
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("height"))){
                        h = Integer.valueOf(imp.getJSONObject(i).getString("height"));
                    }
                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("width"))){
                        w = Integer.valueOf(imp.getJSONObject(i).getString("width"));
                    }
                    if("1000000590".equals(request.getImp().get(0).getTagid()) && 720 > h) {
                        return  new TzBidResponse();
                    }

                    if("1000000591".equals(request.getImp().get(0).getTagid()) && 720 > h) {
                        return  new TzBidResponse();
                    }


                    if("2".equals(imp.getJSONObject(i).getString("adtype"))){
                        //    String base_img = imp.getJSONObject(i).getString("base_img");   //打底单图，预缓存素材未缓存时使⽤(富媒体⼴告可能会 有该字段。预留配置字段，暂⽆返回值)。
                        //    String mask_img = imp.getJSONObject(i).getString("mask_img");   //富媒体遮罩效果图 (部分富媒体⼴告会有该字段，⽐如 Gallery)。
                        //    String slogan = imp.getJSONObject(i).getString("slogan");   //富媒体可点击标语 (富媒体⼴告会有该字段)

                        TzNative tzNative = new TzNative();
                        tb.setAd_type(8);//信息流-广告素材类型
                        JSONArray CdImages =  imp.getJSONObject(i).getJSONArray("image");
                        ArrayList<TzImage> images = new ArrayList<>();
                        TzLogo tzLogo = new TzLogo();
                        tzLogo.setUrl(imp.getJSONObject(i).getString("logo"));
                        tzNative.setLogo(tzLogo);
                        if("7".equals(imp.getJSONObject(i).getString("creative_type")) || "8".equals(imp.getJSONObject(i).getString("creative_type"))){
                            if(null != CdImages) {
                                for (int im = 0; im < CdImages.size(); im++) {
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(CdImages.getString(im));
                                    tzImage.setH(h);
                                    tzImage.setW(w);
                                    images.add(tzImage);
                                }
                                tzNative.setTitle(imp.getJSONObject(i).getString("title"));
                                tzNative.setDesc(imp.getJSONObject(i).getString("desc"));
                                tzNative.setImages(images);
                            }

                        }else{
                            // 信息流视频
                            video.setUrl(imp.getJSONObject(i).getString("vurl"));
                            video.setH(h);
                            video.setW(w);
                            video.setDuration(imp.getJSONObject(i).getInteger("duration"));
                            tzNative.setVideo(video);
                        }
                        tb.setNATIVE(tzNative);
                    }else {
                        tb.setTitle(imp.getJSONObject(i).getString("title"));
                        tb.setDesc(imp.getJSONObject(i).getString("desc"));
                        tb.setAdLogo(imp.getJSONObject(i).getString("logo"));
                        if("1".equals(imp.getJSONObject(i).getString("adtype"))){
                            tb.setAd_type(5);//开屏-广告素材类型
                        }else if("4".equals(imp.getJSONObject(i).getString("adtype"))){
                            tb.setAd_type(0);//开屏-广告素材类型
                        }else if("5".equals(imp.getJSONObject(i).getString("adtype"))){
                            tb.setAd_type(3);//开屏-广告素材类型
                        }

                        if ("1".equals(imp.getJSONObject(i).getString("creative_type")) || "3".equals(imp.getJSONObject(i).getString("creative_type")) || "4".equals(imp.getJSONObject(i).getString("creative_type"))) {
                            JSONArray CdImages = imp.getJSONObject(i).getJSONArray("image");
                            ArrayList<TzImage> images = new ArrayList<>();
                            if (null != CdImages) {
                                for (int cdim = 0; cdim < CdImages.size(); cdim++) {
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(CdImages.getString(cdim));
                                    tzImage.setH(h);
                                    tzImage.setW(w);
                                    images.add(tzImage);
                                }
                                tb.setImages(images);
                            }

                        } else if ("2".equals(imp.getJSONObject(i).getString("creative_type"))) {
                            // 开屏视频
                            if (null != imp.getJSONObject(i).getJSONObject("video")) {
                                video.setUrl(imp.getJSONObject(i).getString("vurl"));
                                video.setH(h);
                                video.setW(w);
                                video.setDuration(imp.getJSONObject(i).getInteger("duration"));
                                tb.setVideo(video);//视频素材
                            }
                        }
                    }

                    if(null != imp.getJSONObject(i).getJSONObject("ext")) {
                        tb.setAptAppId(imp.getJSONObject(i).getJSONObject("ext").getString("appId"));//下载类型：应用名称
                        tb.setAptOrgId(imp.getJSONObject(i).getJSONObject("ext").getString("userName"));//下载类型：应用名称
                        tb.setAptPath(imp.getJSONObject(i).getJSONObject("ext").getString("path"));//下载类型：应用名称
                        tb.setAptType(imp.getJSONObject(i).getJSONObject("ext").getString("miniprogramType"));//下载类型：应用名称
                    }

                    TzBidApps tzBidApps = new TzBidApps();
                    tzBidApps.setBundle(imp.getJSONObject(i).getString("package_name"));
                    tzBidApps.setApp_name(imp.getJSONObject(i).getString("app_name"));
                    tb.setApp(tzBidApps);

                    Integer action = imp.getJSONObject(i).getInteger("action");//1:落地⻚，2:资源下载，
                    if(1 == action){
                        tb.setClicktype("1");//跳转
                    }else if(2 == action){
                        tb.setClicktype("3");//下载
                    }else{
                        tb.setClicktype("0");//点击
                    }

                    //    Integer isgdt = imp.getJSONObject(i).getInteger("isgdt");//是否为⼴点通的⼴告，0:不是，1:是。如果没有该字段表 示⾮⼴点通⼴告

                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink"))){
                        tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink"));

                        List<String> deep_linkT = new ArrayList<>();
                        deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("deeplinktk");
                        for (int dp = 0; dp < urls1.size(); dp++) {
                            deep_linkT.add(urls1.get(dp).toString());
                        }
                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }


                    tb.setClick_url(imp.getJSONObject(i).getString("link")); // 点击跳转url地址


                    if(null != imp.getJSONObject(i).getJSONArray("imptk")){
                        List<String> check_views = new ArrayList<>();
                        check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                        JSONArray urls1 = imp.getJSONObject(i).getJSONArray("imptk");
                        for (int cv = 0; cv < urls1.size(); cv++) {
                            String replace = urls1.get(cv).toString().replace("__RESPONSE_TIME__", endTime.toString()).replace("__READY_TIME__", System.currentTimeMillis() + "").replace("__SHOW_TIME__", System.currentTimeMillis() + "");
                            check_views.add(replace);
                        }
                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                    if(null != imp.getJSONObject(i).getJSONArray("clicktk")){
                        List<String> clickList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("clicktk");
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < urls1.size(); cc++) {
                            String replace = urls1.get(cc).toString().replace("__CLICK_TIME__", System.currentTimeMillis() + "");
                            clickList.add(replace);
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    if(null != imp.getJSONObject(i).getJSONArray("starttk")){
                        List<String> voidStartList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("starttk");
                        voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                        for (int vs = 0; vs < urls1.size(); vs++) {
                            voidStartList.add(urls1.get(vs).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        //    tb.setCheck_views(voidStartList);//视频开始播放
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%VEDIO_START%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }


                    if(null != imp.getJSONObject(i).getJSONArray("endtk")){
                        List<String> voidEndList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("endtk");
                        voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                        for (int ve = 0; ve < urls1.size(); ve++) {
                            voidEndList.add(urls1.get(ve).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%VEDIO_END%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    if(null != imp.getJSONObject(i).getJSONArray("downloadtk")){
                        List<String> downLoadList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("downloadtk");
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
                    if(null != imp.getJSONObject(i).getJSONArray("downloadedtk")){
                        List<String> downLoadDList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("downloadedtk");
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
                    if(null != imp.getJSONObject(i).getJSONArray("installtk")){
                        List<String> installList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("installtk");
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
                    if(null != imp.getJSONObject(i).getJSONArray("installedtk")){
                        List<String> installEList = new ArrayList<>();
                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("installedtk");
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
                    tb.setSource(source+": "+imp.getJSONObject(i).getString("adsource"));
                    tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);//
                }


                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seatList.add(seat);


                bidResponse.setId(id);//请求id
                bidResponse.setBidid("");
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("创典广告总返回"+JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }
}
