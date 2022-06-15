package com.mk.adx.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.yunxi.YxApp;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.YxJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 云袭
 * @Date 2021/11/15 16:00
 */
@Slf4j
@Service("yxJsonService")
public class YxJsonServiceImpl implements YxJsonService {

    private static final String name = "yx";

    private static final String source = "云袭";

    @SneakyThrows
    @Override
    public TzBidResponse getYxDataByJson(TzBidRequest request) {
        YxApp bidRequest = new YxApp();
        bidRequest.setVer(100);//API版本
        bidRequest.setSlotcode(request.getAdv().getTag_id());//
        bidRequest.setReqid(request.getId());//请求id
        bidRequest.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0])); //
        bidRequest.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));//
        bidRequest.setApp_name(request.getAdv().getApp_name());//app应用名称
        bidRequest.setBundle(request.getAdv().getBundle());//包名、bundle id(app 必需)
        bidRequest.setUa(request.getDevice().getUa());//user agent，URL编码
        bidRequest.setApp_version(request.getAdv().getVersion());//app版本

        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            bidRequest.setDevice_type(2);
        }else if("ipad".equals(devicetype)){
            bidRequest.setDevice_type(1);
        }else if("pc".equals(devicetype)){
            bidRequest.setDevice_type(3);
        }else if("tv".equals(devicetype)){
            bidRequest.setDevice_type(4);
        }else{
            bidRequest.setDevice_type(0);
        }
        bidRequest.setMake(request.getDevice().getMake());
        bidRequest.setModel(request.getDevice().getModel());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
             bidRequest.setOs("android");
             if(StringUtils.isNotEmpty(request.getDevice().getAndroid_id())){
                 bidRequest.setAnid(request.getDevice().getAndroid_id());
             }else{
                 bidRequest.setAnid("82f83b86dd927e3c");
             }
             bidRequest.setOaid(request.getDevice().getOaid());
            if(StringUtils.isNotEmpty(request.getDevice().getImei())){
                bidRequest.setImei(request.getDevice().getImei());
            }else{
                bidRequest.setImei("351710058880864");
            }
            bidRequest.setBoot_mark("ec7f4f33-411a-47bc-8067-744a4e7e0723");
            bidRequest.setUpdate_mark("1004697.709999999");
            bidRequest.setImsi("460001357924680");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)){
            bidRequest.setOs("ios");
            bidRequest.setIdfa(request.getDevice().getIdfa());
            bidRequest.setBoot_mark("1623815045.970028");
            bidRequest.setUpdate_mark("1581141691.570419583");
        }
        bidRequest.setMac(request.getDevice().getMac());
        bidRequest.setOsv(request.getDevice().getOsv());
        bidRequest.setSh(request.getDevice().getH());
        bidRequest.setSw(request.getDevice().getW());
        if(0.0 != request.getDevice().getDeny()){
            bidRequest.setPxratio((float) (request.getDevice().getDeny()));
        }
        bidRequest.setLanguage("zh-CN");

        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            bidRequest.setMnc(1);
        }else if("70123".equals(carrier)){
            bidRequest.setMnc(3);
        }else if("70121".equals(carrier)){
            bidRequest.setMnc(2);
        }else{
            bidRequest.setMnc(0);
        }

        Integer connectiontype = request.getDevice().getConnectiontype();
        if(2 == connectiontype){
            bidRequest.setConnectiontype(2);
        }else if(4 == connectiontype || 5 == connectiontype || 6 == connectiontype || 7 == connectiontype){
            bidRequest.setConnectiontype(1);
        }else{
            bidRequest.setConnectiontype(0);
        }
        if(null != request.getDevice().getGeo()){
            bidRequest.setLat(String.valueOf(request.getDevice().getGeo().getLat()));
            bidRequest.setLon(String.valueOf(request.getDevice().getGeo().getLon()));
            bidRequest.setRegion("");
            bidRequest.setCity("");
        }
        bidRequest.setCip(request.getDevice().getIp());
        bidRequest.setBrand(request.getDevice().getMake());
        bidRequest.setPkgs("com.jingdong.app,com.baidu.searchbox");
        bidRequest.setHmscore("v6.1.0.313");
        bidRequest.setOrientation(1);
        bidRequest.setSsid("");
        bidRequest.setWifimac(request.getDevice().getMac());
        bidRequest.setRomversion(request.getDevice().getOsv());
        bidRequest.setSyscomplingtime("");

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求云袭广告参数"+JSONObject.parseObject(content));
        String url = "http://s.lockty.com/a/";
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
        log.info("请求上游云袭广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("云袭广告返回参数"+JSONObject.parseObject(response));

        if (200 == JSONObject.parseObject(response).getInteger("code")) {
            if(null != JSONObject.parseObject(response).getJSONArray("ads")){
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            JSONArray imp = JSONObject.parseArray(jo.getString("ads"));
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
                tb.setId(imp.getJSONObject(i).getString("reqid"));
                TzVideo video = new TzVideo();
                if (304 == imp.getJSONObject(i).getInteger("ast")) {
                    TzNative tzNative = new TzNative();
                    tb.setAd_type(8);//信息流-广告素材类型
                    JSONArray YxImages = imp.getJSONObject(i).getJSONObject("native").getJSONArray("imgs");
                    ArrayList<TzImage> images = new ArrayList<>();
                    TzLogo tzLogo = new TzLogo();
                    tzLogo.setUrl(imp.getJSONObject(i).getString("logo"));
                    tzNative.setLogo(tzLogo);
                    if (null != YxImages) {
                        for (int im = 0; im < YxImages.size(); im++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(YxImages.getJSONObject(im).getString("url"));
                            tzImage.setH(YxImages.getJSONObject(im).getInteger("h"));
                            tzImage.setW(YxImages.getJSONObject(im).getInteger("w"));
                            images.add(tzImage);
                        }
                    }
                    tzNative.setTitle(imp.getJSONObject(i).getJSONObject("native").getString("title"));
                    JSONArray YxDes = imp.getJSONObject(i).getJSONObject("native").getJSONArray("des");
                    if (null != YxDes) {
                        tzNative.setDesc(YxDes.get(0).toString());
                    }
                        tzNative.setDesc2(imp.getJSONObject(i).getJSONObject("native").getString("bt"));

                    tzNative.setImages(images);

                        // 信息流视频
//                    JSONArray jsonArray = imp.getJSONObject(i).getJSONObject("native").getJSONArray("video");
//                    if(!"".equals(jsonArray)){
//                            video.setUrl(jsonArray.getJSONObject(0).getString("url"));
//                            video.setH(jsonArray.getJSONObject(0).getInteger("h"));
//                            video.setW(jsonArray.getJSONObject(0).getInteger("w"));
//                            video.setDuration(jsonArray.getJSONObject(0).getInteger("duration"));
//                            tzNative.setVideo(video);
//                    }
                    tb.setNATIVE(tzNative);
                }else {
                    tb.setAd_type(5);//开屏-广告素材类型
                        JSONArray YxImages = imp.getJSONObject(i).getJSONArray("imgs");
                        ArrayList<TzImage> images = new ArrayList<>();
                        if (null != YxImages) {
                            for (int yxim = 0; yxim < YxImages.size(); yxim++) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(YxImages.getString(yxim));
                                tzImage.setH(imp.getJSONObject(i).getInteger("h"));
                                tzImage.setW(imp.getJSONObject(i).getInteger("w"));
                                images.add(tzImage);
                            }
                            tb.setImages(images);
                        }
                }




                Integer act = imp.getJSONObject(i).getInteger("act");//点击广告的交互行为；1：跳转 2：下载
                if(1 == act){
                    tb.setClicktype("1");//跳转
                }else if(2 == act){
                    tb.setClicktype("3");//下载
                }else{
                    tb.setClicktype("0");//点击
                }








                if(!"".equals(imp.getJSONObject(i).getJSONArray("deeplink"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getJSONArray("deeplink").getString(0));

                    List<String> deep_linkT = new ArrayList<>();
                    deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("dpsurl");
                    for (int dp = 0; dp < urls1.size(); dp++) {
                        deep_linkT.add(urls1.get(dp).toString());
                    }
                    String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_success_deeplinks(deep_linkT);//唤醒成功
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DEEP_LINK%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

//                    List<String> deep_linkF = new ArrayList<>();
//                    deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_F%%");
//                    JSONArray urls2 = imp.getJSONObject(i).getJSONArray("dpfurl");
//
//                        for (int dpf = 0; dpf < urls2.size(); dpf++) {
//                            deep_linkF.add(urls2.get(dpf).toString());
//
//                    }
//                    String encode2 =  urls2.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                    tb.setCheck_success_deeplinks(deep_linkF);//唤醒失败
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%DEEP_LINK_F%%");
//                    tzMacros.setValue(Base64.encode(encode2));
//                    tzMacros1.add(tzMacros);
                }


                String clickurl = imp.getJSONObject(i).getJSONArray("lp").getString(0);
                if(StringUtils.isNotEmpty(clickurl)){
                    clickurl.replace(".AD_DOWN_X.","%%DOWN_X%%").replace(".AD_DOWN_Y.","%%DOWN_Y%%").replace(".AD_UP_X.","%%UP_X%%").replace(".AD_UP_Y.","%%UP_Y%%").replace(".UTC_TS.","%%TS%%");
                 //   String encode = clickurl + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
                    tb.setClick_url(clickurl); // 点击跳转url地址   "http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/click?clickurl=%%CLICK_URL%%"
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%CLICK_URL%%");
//                    tzMacros.setValue(Base64.encode(encode));
//                    tzMacros1.add(tzMacros);

                }

                if(null != imp.getJSONObject(i).getJSONArray("imp")){
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("imp");
                    for (int cv = 0; cv < urls1.size(); cv++) {
                        String replace = urls1.get(cv).toString().replace(".AD_DOWN_X.","%%DOWN_X%%").replace(".AD_DOWN_Y.","%%DOWN_Y%%").replace(".AD_UP_X.","%%UP_X%%").replace(".AD_UP_Y.","%%UP_Y%%").replace(".UTC_TS.","%%TS%%");
                        check_views.add(replace);
                    }
                    String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(null != imp.getJSONObject(i).getJSONArray("hit")){
                    List<String> clickList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("hit");
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < urls1.size(); cc++) {
                        String replace = urls1.get(cc).toString().replace(".AD_DOWN_X.","%%DOWN_X%%").replace(".AD_DOWN_Y.","%%DOWN_Y%%").replace(".AD_UP_X.","%%UP_X%%").replace(".AD_UP_Y.","%%UP_Y%%").replace(".UTC_TS.","%%TS%%");
                        clickList.add(replace);
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

//                if(null != imp.getJSONObject(i).getJSONArray("starttk")){
//                    List<String> voidStartList = new ArrayList<>();
//                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("starttk");
//                    voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
//                    for (int vs = 0; vs < urls1.size(); vs++) {
//                        voidStartList.add(urls1.get(vs).toString());
//                    }
//                    String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                    //    tb.setCheck_views(voidStartList);//视频开始播放
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%VEDIO_START%%");
//                    tzMacros.setValue(Base64.encode(encode));
//                    tzMacros1.add(tzMacros);
//                }
//
//
//                if(null != imp.getJSONObject(i).getJSONArray("endtk")){
//                    List<String> voidEndList = new ArrayList<>();
//                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("endtk");
//                    voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
//                    for (int ve = 0; ve < urls1.size(); ve++) {
//                        voidEndList.add(urls1.get(ve).toString());
//                    }
//                    String encode = urls1.get(0).toString() + "," + id + "," + request.getImp().get(0).getTagid() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                    //    tb.setCheck_clicks(voidEndList);//视频播放结束
//                    tzMacros = new TzMacros();
//                    tzMacros.setMacro("%%VEDIO_END%%");
//                    tzMacros.setValue(Base64.encode(encode));
//                    tzMacros1.add(tzMacros);
//                }

                if(null != imp.getJSONObject(i).getJSONArray("surl")){
                    List<String> downLoadList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("surl");
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
                if(null != imp.getJSONObject(i).getJSONArray("furl")){
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("furl");
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
                if(null != imp.getJSONObject(i).getJSONArray("burl")){
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("burl");
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
                if(null != imp.getJSONObject(i).getJSONArray("iurl")){
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("iurl");
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


            TzSeat seat = new TzSeat();//
            seat.setBid(bidList);
            seatList.add(seat);


            bidResponse.setId(id);//请求id
            bidResponse.setBidid("");
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info("云袭广告总返回"+JSONObject.toJSONString(bidResponse));
        }}
        return bidResponse;
    }
}
