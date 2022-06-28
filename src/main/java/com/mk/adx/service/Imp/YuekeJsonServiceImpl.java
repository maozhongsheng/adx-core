package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.mk.MkGeo;
import com.mk.adx.entity.json.request.yueke.*;
import com.mk.adx.entity.json.request.zhimeng.ZhimengBidRequest;
import com.mk.adx.entity.json.request.zhimeng.ZhimengDeviceInfo;
import com.mk.adx.entity.json.request.zhimeng.ZhimengImpInfo;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.service.YuekeJsonService;
import com.mk.adx.service.ZhimengJsonService;
import com.mk.adx.util.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author mzs
 * @Description 阅客
 * @Date 2022/06/27 9:48
 */
@Slf4j
@Service("yuekeJsonService")
public class YuekeJsonServiceImpl implements YuekeJsonService {

    private static final String name = "yueke";

    private static final String source = "阅客";

    @SneakyThrows
    @Override
    public MkBidResponse getYuekeDataByJson(MkBidRequest request) {
        YuekeBidRequest bidRequest = new YuekeBidRequest();
        List<YuekeImp> yuekeimps = new ArrayList();
        //imp
        YuekeImp yuekeImp = new YuekeImp();
        yuekeImp.setId("1");
        yuekeImp.setAw(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        yuekeImp.setAh(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
        yuekeImp.setTagid(request.getAdv().getTag_id());
        yuekeImp.setBidfloor(0);
        if("3".equals(request.getImp().get(0).getSlot_type()) || "5".equals(request.getImp().get(0).getSlot_type()) || "6".equals(request.getImp().get(0).getSlot_type())){
            YuekeBanner yuekeBanner = new YuekeBanner();
            yuekeBanner.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
            yuekeBanner.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
            yuekeBanner.setPos(0);
            yuekeBanner.setType(3);
            yuekeImp.setBanner(yuekeBanner);
        }else if("4".equals(request.getImp().get(0).getSlot_type())){
            YuekeVideo yuekeVideo = new YuekeVideo();
            yuekeVideo.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
            yuekeVideo.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
            yuekeVideo.setMinduration(0);
            yuekeImp.setVideo(yuekeVideo);
        }else if("1".equals(request.getImp().get(0).getSlot_type())){
            YuekeNative yuekeNative = new YuekeNative();
            yuekeNative.setVersion("1.2");
            List<YuekeAssets> assetses = new ArrayList();
            YuekeAssets yuekeAssets = new YuekeAssets();
            yuekeAssets.setId(1);
            YuekeTitle yuekeTitle = new YuekeTitle();
            yuekeTitle.setLen(15);
            yuekeAssets.setTitle(yuekeTitle);
            YuekeData yuekeData = new YuekeData();
            yuekeData.setLen(2);
            yuekeData.setLen(25);
            yuekeAssets.setData(yuekeData);
            YuekeImg yuekeImg = new YuekeImg();
            yuekeImg.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
            yuekeImg.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
            yuekeAssets.setImg(yuekeImg);
            YuekeVideo yuekeVideo = new YuekeVideo();
            yuekeVideo.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
            yuekeVideo.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
            yuekeVideo.setMinduration(0);
            yuekeAssets.setVideo(yuekeVideo);

            assetses.add(yuekeAssets);
            yuekeNative.setAssets(assetses);
        }
        YuekePmp yuekePmp = new YuekePmp();
        yuekePmp.setPrivate_auction(0);
        yuekeImp.setPmp(yuekePmp);
        List<String> mtss = new ArrayList();
        yuekeImp.setMts(mtss);
        yuekeimps.add(yuekeImp);

        //app
        YuekeApp yuekeApp = new YuekeApp();
        yuekeApp.setId(request.getAdv().getApp_id());
        yuekeApp.setName(request.getAdv().getApp_name());
        yuekeApp.setBundle(request.getAdv().getBundle());
        yuekeApp.setVer(request.getAdv().getVersion());

        //device
        YuekeDevice yuekeDevice = new YuekeDevice();
        yuekeDevice.setUa(request.getDevice().getUa());
        //geo
        MkGeo geo = request.getDevice().getGeo();
        YuekeGeo yuekeGeo = new YuekeGeo();
        yuekeGeo.setLat(geo.getLat());
        yuekeGeo.setLon(geo.getLon());
        yuekeDevice.setGeo(yuekeGeo);
        yuekeDevice.setIp(request.getDevice().getIp());
        yuekeDevice.setIpv6(request.getDevice().getIpv6());
        //设备类型
        if ("phone".equals(request.getDevice().getDevicetype())){
            yuekeDevice.setDevicetype(4);
        }else if ("ipad".equals(request.getDevice().getDevicetype())){
            yuekeDevice.setDevicetype(5);
        }else if ("pc".equals(request.getDevice().getDevicetype())){
            yuekeDevice.setDevicetype(2);
        }else {
            yuekeDevice.setDevicetype(0);
        }
        yuekeDevice.setMake(request.getDevice().getMake());
        yuekeDevice.setModel(request.getDevice().getModel());
        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            yuekeDevice.setOs("android");//安卓
            yuekeDevice.setDid(request.getDevice().getImei());
            yuekeDevice.setDidmd5(request.getDevice().getImei_md5());
            yuekeDevice.setDpid(request.getDevice().getAndroid_id());
            yuekeDevice.setDpidmd5(request.getDevice().getAndroid_id_md5());
            yuekeDevice.setOaId(request.getDevice().getOaid());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            yuekeDevice.setOs("ios");//ios
            yuekeDevice.setIfa(request.getDevice().getIdfa());
            yuekeDevice.setIfamd5(request.getDevice().getIdfa_md5());
            yuekeDevice.setStartupTime(request.getDevice().getBootTimeSec());
            yuekeDevice.setUpdateTime(request.getDevice().getOsUpdateTimeSec());
            yuekeDevice.setCountry("CN");
            yuekeDevice.setLanguage("zh-Hans-CN");
            yuekeDevice.setTimeZone(request.getDevice().getTimeZone());
            yuekeDevice.setMemorySize(request.getDevice().getMemorySize());
            yuekeDevice.setDiskSize(request.getDevice().getDiskSize());
            yuekeDevice.setPhoneNameMd5(MD5Util.getMD5(request.getDevice().getPhoneName()));
            yuekeDevice.setBoot_mark(request.getDevice().getBootTimeSec());
            yuekeDevice.setUpdate_mark(request.getDevice().getOsUpdateTimeSec());


        }

        yuekeDevice.setOsv(request.getDevice().getOsv());
        yuekeDevice.setHwv(request.getDevice().getHwv());
        yuekeDevice.setH(request.getDevice().getH());
        yuekeDevice.setW(request.getDevice().getW());
        yuekeDevice.setPpi(request.getDevice().getPpi());
        yuekeDevice.setDpi((int)request.getDevice().getDeny());
        yuekeDevice.setMac(request.getDevice().getMac());
        yuekeDevice.setMacmd5(request.getDevice().getMac_md5());

        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                yuekeDevice.setCarrier("46000");//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                yuekeDevice.setCarrier("46001");//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                yuekeDevice.setCarrier("46003");//中国电信
            }else{
                yuekeDevice.setCarrier("0");//其他
            }
        }else{
            yuekeDevice.setCarrier("0");
        }
        //网络连接类型
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            yuekeDevice.setConnectiontype(0);
        }else if("2".equals(connectiontype)){
            yuekeDevice.setConnectiontype(2);
        }else if("4".equals(connectiontype)){
            yuekeDevice.setConnectiontype(4);
        }else if("5".equals(connectiontype)){
            yuekeDevice.setConnectiontype(5);
        }else if("6".equals(connectiontype)){
            yuekeDevice.setConnectiontype(6);
        }else if("7".equals(connectiontype)){
            yuekeDevice.setConnectiontype(7);
        } else {
            yuekeDevice.setConnectiontype(0);
        }
        yuekeDevice.setOrientation(0);
        yuekeDevice.setAppstore(request.getDevice().getAppstore_ver());

        //ext
        YuekeExt ext = new YuekeExt();
        ext.setRdt(1);
        ext.setHttps(1);
        ext.setDeepLink(1);
        ext.setDownload(1);
        ext.setAdmt(1);
        ext.setVech(1);
        ext.setVecv(1);

        bidRequest.setId(MD5Util.getMD5(request.getId()).toLowerCase(Locale.ROOT));
        bidRequest.setVersion("2.0.0");
        bidRequest.setImp(yuekeimps);
        bidRequest.setApp(yuekeApp);
        bidRequest.setDevice(yuekeDevice);
        bidRequest.setAt(2);
        bidRequest.setTest(1);
        bidRequest.setTmax(500);
        bidRequest.setExt(ext);

        //总返回
        MkBidResponse bidResponse = new MkBidResponse();
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + ":请求阅客广告参数"+JSONObject.parseObject(content));
        String ua = request.getDevice().getUa();
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl("http://adx.8bcd9.com/bid/v6/{adxId}");//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数
        pud.setYueke("1");
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid() + ":请求上游阅客广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid() + ":阅客广告返回参数"+JSONObject.parseObject(response));

        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            //code=10000的时候成功返回，才做解析
            if ("10000".equals(jo.getString("code"))){
                JSONArray imp = jo.getJSONArray("seatbid").getJSONObject(0).getJSONArray("bid");//内容信息
                if (null!=imp){
                    List<MkBid> bidList = new ArrayList<>();
                    for (int i = 0; i < imp.size(); i++) {
                        MkBid tb = new MkBid();
                        tb.setDeeplink_url(imp.getJSONObject(i).getString("deepLink"));//深度链接
                        tb.setClick_url(imp.getJSONObject(i).getString("target"));//内容落地页地址

                        String actionType = imp.getJSONObject(i).getString("actionType");//1=app下载 2=H5广告操作类型
                        if("1".equals(actionType)){
                            tb.setClicktype("4");
                        }else if ("2".equals(actionType)){
                            tb.setClicktype("1");
                        }else {
                            tb.setClicktype("0");
                        }

                        //图片内容处理
                        if("3".equals(request.getImp().get(0).getSlot_type()) || "5".equals(request.getImp().get(0).getSlot_type()) || "6".equals(request.getImp().get(0).getSlot_type())){
                            List<MkImage> list = new ArrayList<>();
                            MkImage mkImage = new MkImage();
                            JSONObject banner = imp.getJSONObject(i).getJSONObject("banner");
                            mkImage.setW(banner.getInteger("w"));
                            mkImage.setH(banner.getInteger("h"));
                            mkImage.setUrl(banner.getString("iurl"));
                            list.add(mkImage);
                            tb.setImages(list);
                            tb.setAd_type(5);//开屏-广告素材类型
                        }else if("4".equals(request.getImp().get(0).getSlot_type())){
                            MkVideo mkVideo = new MkVideo();
                            JSONObject video = imp.getJSONObject(i).getJSONObject("video");
                            mkVideo.setUrl(video.getString("iurl"));
                            mkVideo.setW(video.getInteger("w"));
                            mkVideo.setH(video.getInteger("h"));
                            mkVideo.setDuration(video.getInteger("duration"));
                            tb.setVideo(mkVideo);

                        }else if("1".equals(request.getImp().get(0).getSlot_type())) {
                            List<MkImage> list = new ArrayList<>();
                            MkImage mkImage = new MkImage();
                            JSONObject natives = imp.getJSONObject(i).getJSONObject("native").getJSONArray("assets").getJSONObject(0);
                            JSONObject img = natives.getJSONObject("img");
                            mkImage.setUrl(img.getString("iurl"));
                            mkImage.setW(img.getInteger("w"));
                            mkImage.setH(img.getInteger("h"));
                            list.add(mkImage);
                            tb.setImages(list);
                            JSONObject title = natives.getJSONObject("title");
                            JSONObject data = natives.getJSONObject("data");
                            tb.setTitle(title.getString("text"));
                            tb.setDesc(data.getString("value"));
                            tb.setAd_type(8);//原生-广告素材类型
                        }

                        //监测链接所需要的value
                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

                        //曝光监测
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("els")){
                            List<String> check_views = new ArrayList<>();
                            check_views.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                            JSONArray urls1 = imp.getJSONObject(i).getJSONObject("events").getJSONArray("els");
                            for (int cv = 0; cv < urls1.size(); cv++) {
                                String replace = urls1.get(cv).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                check_views.add(replace);
                            }
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测

                        }

                        //点击监测
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("cls")){
                            List<String> clickList = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("cls");
                            clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                clickList.add(replace);
                            }
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测

                        }

                        //开始下载
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("sdls")){
                            List<String> dl_start = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("sdls");
                            dl_start.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                dl_start.add(replace);
                            }
                            tb.setCheck_start_downloads(dl_start);//开始下载

                        }

                        //结束下载
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("edls")){
                            List<String> dl_end = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("edls");
                            dl_end.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                dl_end.add(replace);
                            }
                            tb.setCheck_end_downloads(dl_end); //结束下载

                        }

                        //开始安装
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("sils")){
                            List<String> in_start = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("sils");
                            in_start.add("http://adx.fxlxz.com/sl/in_start?installStart=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                in_start.add(replace);
                            }
                            tb.setCheck_start_installs(in_start);//开始安装

                        }

                        //结束安装
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("eils")){
                            List<String> in_end = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("eils");
                            in_end.add("http://adx.fxlxz.com/sl/in_end?installEnd=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                in_end.add(replace);
                            }
                            tb.setCheck_end_installs(in_end);//结束安装

                        }

                        //dp吊起成功
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("dcls")){
                            List<String> dp_success = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("dcls");
                            dp_success.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                dp_success.add(replace);
                            }
                            tb.setCheck_success_deeplinks(dp_success); //dp吊起成功

                        }

                        //开始播放
                        List<MkCheckVideoUrls> videourls = new ArrayList();
                        MkCheckVideoUrls mkCheckVideoUrls = new MkCheckVideoUrls();
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("spls")){
                            List<String> v_start = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("spls");
                            v_start.add("http://adx.fxlxz.com/sl/v_start?vedioStart=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                v_start.add(replace);
                            }
                            mkCheckVideoUrls.setTime(0);
                            mkCheckVideoUrls.setUrl(v_start);
                            videourls.add(mkCheckVideoUrls);
                        }

                        //结束播放
                        if(null != imp.getJSONObject(i).getJSONObject("events").getJSONArray("epls")){
                            List<String> v_end = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("events").getJSONArray("epls");
                            v_end.add("http://adx.fxlxz.com/sl/v_end?vedioEnd=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__AMVW__", "__WIDTH__").replace("__AMVH__","__HEIGHT__").replace("__AZMX__","__DOWN_X__").replace("__AZMY__","__DOWN_Y__").replace("__AZCX__","__UP_X__").replace("__AZCY__","__UP_Y__").replace("__TS__","__TS__");
                                v_end.add(replace);
                            }
                            mkCheckVideoUrls.setTime(1);
                            mkCheckVideoUrls.setUrl(v_end);
                            videourls.add(mkCheckVideoUrls);
                        }
                        tb.setCheck_video_urls(videourls);//视频
                        bidList.add(tb);
                    }

                    bidResponse.setId(id);//请求id
                    bidResponse.setBidid(id);//广告主返回id
                    bidResponse.setSeatbid(bidList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info(request.getImp().get(0).getTagid() + ":阅客广告总返回"+JSONObject.toJSONString(bidResponse));
                }

            }
        }

        return bidResponse;
    }
}
