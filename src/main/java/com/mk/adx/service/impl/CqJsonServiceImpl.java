package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.changqing.CqBidRequest;
import com.mk.adx.entity.json.request.changqing.CqDevice;
import com.mk.adx.entity.json.request.changqing.CqNetWork;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.CqJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 长青
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("cqJsonService")
public class CqJsonServiceImpl implements CqJsonService {

    private static final String name = "cq";
    private static final String source = "长青";
    /**
     * @Author mzs
     * @Description 长青
     * @Date 2021/6/28 9:48
     */
    @SneakyThrows
    @Override
    public TzBidResponse getCqDataByJson(TzBidRequest request) {
        CqBidRequest bidRequest = new CqBidRequest();
        CqDevice device = new CqDevice();
        CqNetWork netWork = new CqNetWork();

        String os = request.getDevice().getOs();//终端操作系统类型:0=>Android,1=>iOS
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {
            device.setOsType(1);
            device.setImei(request.getDevice().getImei());
            device.setAndroidId(request.getDevice().getAndroid_id());
            device.setOaid(request.getDevice().getOaid());
            device.setBootMark("ec7f4f33-411a-47bc-8067-744a4e7e0723");
            device.setUpdateMark("1004697.709999999");
        } else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            device.setOsType(2);
            device.setIdfa(request.getDevice().getIdfa());
            device.setBootMark("1623815045.970028");
            device.setUpdateMark("1581141691.570419");
        }
        device.setMac(request.getDevice().getMac());
        device.setModel(request.getDevice().getModel());
        device.setVendor(request.getDevice().getMake());
        device.setScreenWidth(request.getDevice().getW());
        device.setScreenHeight(request.getDevice().getH());
        device.setOsVersion(request.getDevice().getOsv());
        device.setUa(request.getDevice().getUa());
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());
        }
        device.setScreenOrientation(0);
        device.setBrand(request.getDevice().getMake());
        device.setImsi("");


        netWork.setIp(request.getDevice().getIp());

        Integer connectiontype = request.getDevice().getConnectiontype();
        if (1 == connectiontype) {
            netWork.setConnectionType(101);
        } else if (2 == connectiontype) {
            netWork.setConnectionType(100);
        } else if (3 == connectiontype) {
            netWork.setConnectionType(1);
        } else if (4 == connectiontype) {
            netWork.setConnectionType(2);
        } else if (5 == connectiontype) {
            netWork.setConnectionType(3);
        } else if (6 == connectiontype) {
            netWork.setConnectionType(4);
        } else if (7 == connectiontype) {
            netWork.setConnectionType(5);
        } else {
            netWork.setConnectionType(0);
        }
        String carrier = request.getDevice().getCarrier();
        if (StringUtils.isNotEmpty(carrier)) {
            if ("70120".equals(carrier)) {
                netWork.setOperatorType(1);
            } else if ("70123".equals(carrier)) {
                netWork.setOperatorType(3);
            } else if ("70121".equals(carrier)) {
                netWork.setOperatorType(2);
            } else {
                netWork.setOperatorType(0);
            }

        }
        if (null != request.getDevice().getGeo()) {
            netWork.setLat(request.getDevice().getGeo().getLat());
        } else {
            netWork.setLat(0);
        }

        if (null != request.getDevice().getGeo()) {
            netWork.setLon(request.getDevice().getGeo().getLon());
        } else {
            netWork.setLon(0);
        }


        bidRequest.setRequestId(request.getId());
        bidRequest.setDevice(device);
        bidRequest.setNetwork(netWork);
        bidRequest.setChannelId(request.getAdv().getTag_id());


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求长青广告参数" + JSONObject.parseObject(content));
        String url = "http://api.egcmedia.com/api/ads";   //http://8.134.52.3/ads/bid
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
        log.info("请求上游长青广告花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("长青广告返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            if ("0".equals(JSONObject.parseObject(response).getString("errorCode"))) {
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
                    TzVideo video = new TzVideo();

                    JSONArray metaGroup = imp.getJSONObject(i).getJSONArray("metaGroup");
                    for (int meta = 0; meta < metaGroup.size(); meta++) {

                        if ("1".equals(request.getImp().get(0).getAd_slot_type())) {
                            TzNative tzNative = new TzNative();
                            TzIcon tzIcon = new TzIcon();
                            tb.setAd_type(8);//信息流-广告素材类型
                            JSONArray CqImages = metaGroup.getJSONObject(meta).getJSONArray("imageUrl");
                            ArrayList<TzImage> images = new ArrayList<>();
                            if (null != CqImages) {
                                for (int im = 0; im < CqImages.size(); im++) {
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(CqImages.getString(im));
                                    tzImage.setH(metaGroup.getJSONObject(meta).getInteger("materialHeight"));
                                    tzImage.setW(metaGroup.getJSONObject(meta).getInteger("materialWidth"));
                                    images.add(tzImage);
                                }
                            }

                            tzNative.setTitle(metaGroup.getJSONObject(meta).getString("adTitle"));
                            JSONArray CqDes = metaGroup.getJSONObject(meta).getJSONArray("descs");
                            if (null != CqDes) {
                                tzNative.setDesc(CqDes.get(0).toString());
                            }

                            JSONArray CqIcon = metaGroup.getJSONObject(meta).getJSONArray("iconUrls");
                            if (null != CqIcon) {
                                tzIcon.setUrl(CqIcon.getString(0));
                                tzNative.setIcon(tzIcon);
                            }
                            tzNative.setImages(images);

                            // 信息流视频
                            if (StringUtils.isNotEmpty(metaGroup.getJSONObject(meta).getString("videoUrl"))) {
                                video.setUrl(metaGroup.getJSONObject(meta).getString("videoUrl"));
                                video.setH(metaGroup.getJSONObject(meta).getInteger("materialHeight"));
                                video.setW(metaGroup.getJSONObject(meta).getInteger("materialWidth"));
                                video.setDuration(metaGroup.getJSONObject(meta).getInteger("videoDuration"));
                                tzNative.setVideo(video);
                            }
                            tb.setNATIVE(tzNative);
                        } else {


                            tb.setAd_type(5);//开屏-广告素材类型

                            JSONArray CqImages = metaGroup.getJSONObject(meta).getJSONArray("imageUrl");
                            ArrayList<TzImage> images = new ArrayList<>();
                            if (null != CqImages) {
                                for (int cqim = 0; cqim < CqImages.size(); cqim++) {
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(CqImages.getString(cqim));
                                    tzImage.setH(metaGroup.getJSONObject(meta).getInteger("materialHeight"));
                                    tzImage.setW(metaGroup.getJSONObject(meta).getInteger("materialWidth"));
                                    images.add(tzImage);
                                }
                                tb.setImages(images);
                            }

                            tb.setTitle(metaGroup.getJSONObject(meta).getString("adTitle"));
                            JSONArray CqDes = metaGroup.getJSONObject(meta).getJSONArray("descs");
                            if (null != CqDes) {
                                tb.setDesc(CqDes.get(0).toString());
                            }
                            JSONArray CqIcon = metaGroup.getJSONObject(meta).getJSONArray("iconUrls");
                            if (null != CqIcon) {
                                tb.setAic(CqIcon.get(0).toString());
                            }
                        }


                        Integer interactionType = metaGroup.getJSONObject(meta).getInteger("interactionType");//  1--浏览 2--下载 3--deeplink 0--其他
                        if (1 == interactionType) {
                            tb.setClicktype("0");//点击
                        } else if (2 == interactionType) {
                            tb.setClicktype("3");//下载
                        } else if (3 == interactionType) {
                            tb.setClicktype("2");//拉活 (deeplink)
                        } else {
                            tb.setClicktype("1");//跳转
                        }


                        if (StringUtils.isNotEmpty(metaGroup.getJSONObject(meta).getString("deepLink"))) {
                            tb.setDeeplink_url(metaGroup.getJSONObject(meta).getString("deepLink"));


                            if (null != metaGroup.getJSONObject(meta).getJSONArray("arrDeepsuccTrackUrl")) {
                                List<String> deep_linkS = new ArrayList<>();
                                deep_linkS.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                                JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("arrDeepsuccTrackUrl");
                                for (int dps = 0; dps < urls1.size(); dps++) {
                                    deep_linkS.add(urls1.get(dps).toString());
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_success_deeplinks(deep_linkS);//唤醒成功
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DEEP_LINK%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }


                            if (null != metaGroup.getJSONObject(meta).getJSONArray("arrDeepfailTrackUrl")) {
                                List<String> deep_linkF = new ArrayList<>();
                                deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_F%%");
                                JSONArray urls2 = metaGroup.getJSONObject(meta).getJSONArray("arrDeepfailTrackUrl");
                                for (int dpf = 0; dpf < urls2.size(); dpf++) {
                                    deep_linkF.add(urls2.get(dpf).toString());
                                }
                                String encode2 = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_fail_deeplinks(deep_linkF);//唤醒失败
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DEEP_LINK_F%%");
                                tzMacros.setValue(Base64.encode(encode2));
                                tzMacros1.add(tzMacros);
                            }
                        }

                        tb.setClick_url(metaGroup.getJSONObject(meta).getString("clickUrl")); // 点击跳转url地址

                        if (null != metaGroup.getJSONObject(meta).getJSONArray("winNoticeUrls")) {
                            List<String> check_views = new ArrayList<>();
                            check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                            JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("winNoticeUrls");
                            for (int cv = 0; cv < urls1.size(); cv++) {
                                //  String replace = urls1.get(cv).toString().replace("__RESPONSE_TIME__", endTime.toString()).replace("__READY_TIME__", System.currentTimeMillis() + "").replace("__SHOW_TIME__", System.currentTimeMillis() + "");
                                check_views.add(urls1.get(cv).toString());
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_VIEWS%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (null != metaGroup.getJSONObject(meta).getJSONArray("winCNoticeUrls")) {
                            List<String> clickList = new ArrayList<>();
                            JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("winCNoticeUrls");
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                // String replace = urls1.get(cc).toString().replace("__CLICK_TIME__", System.currentTimeMillis() + "");
                                clickList.add(urls1.get(cc).toString());
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

                        if (null != metaGroup.getJSONObject(meta).getJSONArray("arrDownloadTrackUrl")) {
                            List<String> downLoadList = new ArrayList<>();
                            JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("arrDownloadTrackUrl");
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
                        if (null != metaGroup.getJSONObject(meta).getJSONArray("arrDownloadedTrakUrl")) {
                            List<String> downLoadDList = new ArrayList<>();
                            JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("arrDownloadedTrakUrl");
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
                        if (null != metaGroup.getJSONObject(meta).getJSONArray("arrIntallTrackUrl")) {
                            List<String> installList = new ArrayList<>();
                            JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("arrIntallTrackUrl");
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
                        if (null != metaGroup.getJSONObject(meta).getJSONArray("arrIntalledTrackUrl")) {
                            List<String> installEList = new ArrayList<>();
                            JSONArray urls1 = metaGroup.getJSONObject(meta).getJSONArray("arrIntalledTrackUrl");
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
                }


                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seatList.add(seat);


                bidResponse.setId(id);//请求id
                bidResponse.setBidid("");
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("长青广告总返回" + JSONObject.toJSONString(bidResponse));
            }
        }
        return bidResponse;
    }
}
