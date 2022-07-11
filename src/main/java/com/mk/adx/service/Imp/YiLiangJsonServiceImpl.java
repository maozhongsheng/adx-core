package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.yiliang.*;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.service.YiLiangJsonService;
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
 * @Description 奕量
 * @Date 2021/3/14 13:11
 */
@Slf4j
@Service("yiLiangJsonService")
public class YiLiangJsonServiceImpl implements YiLiangJsonService {

    private static final String name = "yl";

    private static final String source = "奕量";

    @SneakyThrows
    @Override
    public MkBidResponse getYiLiangDataByJson(MkBidRequest request) {
        YiLiangBidRequest bidRequest = new YiLiangBidRequest();
        YiLiangUser user = new YiLiangUser();
        YiLiangDevice device = new YiLiangDevice();
        YiLiangGeo geo = new YiLiangGeo();
        YiLiangMedia media = new YiLiangMedia();
        YiLiangNetWork netWork = new YiLiangNetWork();
        YiLiangPostion postion = new YiLiangPostion();
        //media
        media.setMediaId(request.getAdv().getApp_id()); //
        media.setAppPackage(request.getAdv().getBundle()); //
        media.setAppVersion(Integer.valueOf(request.getAdv().getVersion())); //
        //postion
        postion.setPositionId(request.getAdv().getTag_id()); //
        if("信息流".equals(request.getAdv().getSlot_type())){
            postion.setDisplayType(5);
        }else if("开屏".equals(request.getAdv().getSlot_type())){
            postion.setDisplayType(2);
        }else if("banner".equals(request.getAdv().getSlot_type())){
            postion.setDisplayType(3);
        }else if("插屏".equals(request.getAdv().getSlot_type())){
            postion.setDisplayType(4);
        }else if("激励视频".equals(request.getAdv().getSlot_type())){
            postion.setDisplayType(9);
        }
        postion.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0])); //
        postion.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1])); //
        //device
        device.setMac(request.getDevice().getMac());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            if(null != request.getDevice().getImei()){
                device.setImei(request.getDevice().getImei());
            }else{
                device.setImei("863008042310655");
            }

            device.setDidMd5(request.getDevice().getImei_md5());
            device.setOaid(request.getDevice().getOaid());
            device.setAndroidId(request.getDevice().getAndroid_id());
            device.setAn(request.getDevice().getOsv());
            device.setAv(23);


        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {

        }
        device.setUa(request.getDevice().getUa());
        device.setIp(request.getDevice().getIp());
        device.setMake(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        device.setLanguage("zh-CN");
        device.setScreenWidth(request.getDevice().getW());
        device.setScreenHeight(request.getDevice().getH());
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());
        }
        //network
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            netWork.setConnectType(100);
        }else if("1".equals(connectiontype)){
            netWork.setConnectType(1);
        }else if("2".equals(connectiontype)){
            netWork.setConnectType(100);
        }else if("3".equals(connectiontype)){
            netWork.setConnectType(100);
        }else if("4".equals(connectiontype)){
            netWork.setConnectType(2);
        }else if("5".equals(connectiontype)){
            netWork.setConnectType(3);
        }else if("6".equals(connectiontype)){
            netWork.setConnectType(4);
        }else if("7".equals(connectiontype)){
            netWork.setConnectType(5);
        } else {
            netWork.setConnectType(100);
        }
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            netWork.setCarrier(1);
        }else if("70123".equals(carrier)){
            netWork.setCarrier(2);
        }else if("70121".equals(carrier)){
            netWork.setCarrier(3);
        }else{
            netWork.setCarrier(2);
        }
        //geo
        if(null != request.getDevice().getGeo()){
            geo.setLat(request.getDevice().getGeo().getLat());
            geo.setLng(request.getDevice().getGeo().getLon());
        }
        bidRequest.setApiVersion("1.0");
        bidRequest.setSysVersion("unknow");
        bidRequest.setAppstoreVersion(0);
        bidRequest.setDevice(device);
        bidRequest.setUser(user);
        bidRequest.setNetwork(netWork);
        bidRequest.setGeo(geo);
        bidRequest.setMedia(media);
        bidRequest.setPostion(postion);

        MkBidResponse bidResponse = new MkBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求奕量广告参数"+JSONObject.parseObject(content));
        String url = "https://uapi-ads.vivo.com.cn/u/api/v1/reqAd";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游奕量广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":奕量广告返回参数"+JSONObject.parseObject(response));
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        if(null!=jo){
            if (1 == JSONObject.parseObject(response).getInteger("code")) {
                String id = request.getId();
                JSONArray imp = JSONObject.parseArray(jo.get("data").toString());
                List<MkBid> bidList = new ArrayList<>();
                for (int i = 0; i < imp.size(); i++) {
                    MkBid tb = new MkBid();
                    MkVideo video = new MkVideo();

                    JSONObject ylImages =  imp.getJSONObject(i).getJSONObject("image");
                    ArrayList<MkImage> images = new ArrayList<>();
                    if(null != ylImages) {
                        MkImage tzImage = new MkImage();
                        tzImage.setUrl(ylImages.getString("url"));
                        tzImage.setH(ylImages.getInteger("height"));
                        tzImage.setW(ylImages.getInteger("width"));
                        images.add(tzImage);
                    }
                    tb.setTitle(imp.getJSONObject(i).getString("title"));
                    tb.setDesc(imp.getJSONObject(i).getString("description"));
             //       tb.setAdLogo(imp.getJSONObject(i).getString("adLogo"));
                    tb.setImages(images);
                    if(5 == imp.getJSONObject(i).getInteger("adType")){
                        tb.setAd_type(1);//信息流-广告素材类型
                    }else if (9 == imp.getJSONObject(i).getInteger("adType")){
                        tb.setAd_type(5);//视频-广告素材类型
                        JSONObject ylvideo = imp.getJSONObject(i).getJSONObject("video");
                        if (null != ylvideo) {
                            video.setUrl(ylvideo.getString("videoUrl"));
                            video.setH(ylvideo.getInteger("height"));
                            video.setW(ylvideo.getInteger("width"));
                            video.setDuration(ylvideo.getInteger("duration"));
                            tb.setVideo(video);//视频素材
                        }
                    }else{
                        tb.setAd_type(2);//开屏-广告素材类型
                    }

                    Integer action = imp.getJSONObject(i).getInteger("adStyle");//1:落地⻚，2:资源下载，
                    if(1 == action){
                        tb.setClicktype("1");//跳转
                    }else{
                        tb.setClicktype("0");//点击
                    }
                    Integer price = imp.getJSONObject(i).getInteger("price");
                    if(null != imp.getJSONObject(i).getInteger("bidMode")){
                        if(500 > price){
                            return new MkBidResponse();
                        }

                    }
                    String noticeUrl = imp.getJSONObject(i).getString("noticeUrl");
                    if(null != price){
                        int pric = price + 1;
                        noticeUrl.replace("__WIN_PRICE__",String.valueOf(pric));
                        try {
                            PostUtilDTO pud2 = new PostUtilDTO();//工具类请求参数
                            pud2.setUrl(noticeUrl);//请求路径
                            pud2.setUa(ua);//ua
                            HttppostUtil.doJsonPost(pud2);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                    if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink"))){
                        MkApp tzBidApps = new MkApp();
                        tzBidApps.setBundle(imp.getJSONObject(i).getString("appPackage"));
                        tzBidApps.setApp_name(imp.getJSONObject(i).getString("appName"));
                        tzBidApps.setApp_icon(imp.getJSONObject(i).getString("appIconUrl"));
                        tb.setApp(tzBidApps);
                        tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink"));
                    }


                    tb.setClick_url(imp.getJSONObject(i).getString("targetUrl")); // 点击跳转url地址
                    tb.setDownload_url(imp.getJSONObject(i).getString("downloadUrl")); // 下载url地址
                    if("d085f1e822224af2bd22a52f0411dd66".equals(request.getAdv().getTag_id()) && StringUtils.isNotEmpty(imp.getJSONObject(i).getString("downloadUrl"))){
                        return new MkBidResponse();
                    }

                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    JSONArray trackingList = imp.getJSONObject(i).getJSONArray("trackingList");// 监测地址
                    for (int track = 0; track < trackingList.size(); track++) {
                        if (2 == trackingList.getJSONObject(track).getInteger("trackingEvent")) {
                            JSONArray trackUrls = trackingList.getJSONObject(track).getJSONArray("trackUrls");
                            if (0 < trackUrls.size()) {
                                List<String> check_views = new ArrayList<>();
                                check_views.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                                for (int cv = 0; cv < trackUrls.size(); cv++) {
                                    String replace = trackUrls.get(cv).toString().replace("__TS__", "%%TS%%").replace("__IP__", request.getDevice().getIp());
                                    check_views.add(replace);
                                }
                                tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                            }
                        }
                        if (3 == trackingList.getJSONObject(track).getInteger("trackingEvent")) {
                            JSONArray trackUrls = trackingList.getJSONObject(track).getJSONArray("trackUrls");
                            if (0 < trackUrls.size()) {
                                List<String> clickList = new ArrayList<>();
                                clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                                for (int cc = 0; cc < trackUrls.size(); cc++) {
                                    String replace = trackUrls.get(cc).toString().replace("__TS__ ", "%%TS%%").replace("__IP__", request.getDevice().getIp()).replace("__CLICKAREA__", "1").replace("__X__", "%%DOWN_X%%").replace("__Y__", "%%DOWN_Y%%");
                                    clickList.add(replace);
                                }
                                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            }
                        }
                        if (10 == trackingList.getJSONObject(track).getInteger("trackingEvent")) {
                            JSONArray trackUrls = trackingList.getJSONObject(track).getJSONArray("trackUrls");
                            if (0 < trackUrls.size()) {
                                List<String> downLoadList = new ArrayList<>();
                                downLoadList.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                                for (int dl = 0; dl < trackUrls.size(); dl++) {
                                    downLoadList.add(trackUrls.get(dl).toString());
                                }
                                tb.setCheck_start_downloads(downLoadList);//开始下载
                            }
                        }
                        if (21 == trackingList.getJSONObject(track).getInteger("trackingEvent")) {
                            JSONArray trackUrls = trackingList.getJSONObject(track).getJSONArray("trackUrls");
                            if (0 < trackUrls.size()) {
                                List<String> deep_linkT = new ArrayList<>();
                                deep_linkT.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                                for (int dp = 0; dp < trackUrls.size(); dp++) {
                                    String re = trackUrls.get(dp).toString().replace("__DP_RESULT__", "0");
                                    deep_linkT.add(re);
                                }
                                tb.setCheck_success_deeplinks(deep_linkT);//deeplink调起
                            }
                        }
//                    if(null != imp.getJSONObject(i).getJSONArray("downloadedtk")){
//                        List<String> downLoadDList = new ArrayList<>();
//                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("downloadedtk");
//                        downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
//                        for (int dle = 0; dle < urls1.size(); dle++) {
//                            downLoadDList.add(urls1.get(dle).toString());
//                        }
//                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
//                        tb.setCheck_end_downloads(downLoadDList);//结束下载
//                        tzMacros = new TzMacros();
//                        tzMacros.setMacro("%%DOWN_END%%");
//                        tzMacros.setValue(Base64.encode(encode));
//                        tzMacros1.add(tzMacros);
//                    }
//                    if(null != imp.getJSONObject(i).getJSONArray("installtk")){
//                        List<String> installList = new ArrayList<>();
//                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("installtk");
//                        installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
//                        for (int ins = 0; ins < urls1.size(); ins++) {
//                            installList.add(urls1.get(ins).toString());
//                        }
//                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
//                        tb.setCheck_start_installs(installList);//开始安装
//                        tzMacros = new TzMacros();
//                        tzMacros.setMacro("%%INSTALL_START%%");
//                        tzMacros.setValue(Base64.encode(encode));
//                        tzMacros1.add(tzMacros);
//                    }
//                    if(null != imp.getJSONObject(i).getJSONArray("installedtk")){
//                        List<String> installEList = new ArrayList<>();
//                        JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("installedtk");
//                        installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
//                        for (int ins = 0; ins < urls1.size(); ins++) {
//                            installEList.add(urls1.get(ins).toString());
//                        }
//                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
//                        tb.setCheck_end_installs(installEList);//安装完成
//                        tzMacros = new TzMacros();
//                        tzMacros.setMacro("%%INSTALL_SUCCESS%%");
//                        tzMacros.setValue(Base64.encode(encode));
//                        tzMacros1.add(tzMacros);
//                    }
                    }

                    bidList.add(tb);
                }
                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);
                bidResponse.setSeatbid(bidList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info(request.getImp().get(0).getTagid()+"：奕量总返回数据"+JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }
}
