package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.client.DspClient;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.MD5Util;
import com.mk.adx.service.MtRtaJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @Author mzs
 * @Description 美团
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("mtRtaJsonService")
public class MtRtaJsonServiceImpl implements MtRtaJsonService {

    private static final String name = "mt";
    private static final String source = "美团";

    @Autowired
    private DspClient dspClient;
    /**
     * @Author mzs
     * @Description 美团
     * @Date 2021/12/03 17:00
     */
    //    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<TzBidResponse> getMtRtaDataByJson(TzBidRequest request, Map parames) {

        Map bidRequest = new HashMap();
        Map device = new HashMap();
        bidRequest.put("channel","102351");
        bidRequest.put("platform","meituan");
        bidRequest.put("itemTask","");
        String os = request.getDevice().getOs();//终端操作系统类型:0=>Android,1=>iOS
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {
            device.put("imei",request.getDevice().getImei());
            device.put("oaid",request.getDevice().getOaid());
            device.put("imeiMd5",request.getDevice().getImei_md5());
            if(StringUtils.isNotEmpty(request.getDevice().getOaid())){
                device.put("oaidMd5", MD5Util.getMD5(request.getDevice().getOaid()));
            }
        } else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
        device.put("idfa",request.getDevice().getIdfa());
        device.put("idfaMd5",request.getDevice().getIdfa_md5());
        }
        bidRequest.put("device",device);
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求美团RTA广告参数" + JSONObject.parseObject(content));
        String url = "http://rta.alicpd.top/v1/rta/check";
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
        log.info("请求上游美团RTA花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("美团RTA返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            if (JSONObject.parseObject(response).getBoolean("status")) {
                List<TzMacros> tzMacros1 = new ArrayList();
                TzMacros tzMacros = new TzMacros();
                List<TzSeat> seatList = new ArrayList<>();
                String id = request.getId();
                //多层解析json
                List<TzBid> bidList = new ArrayList<>();
                TzBid tb = new TzBid();
                TzVideo video = new TzVideo();
                String[] pImages = parames.get("images").toString().split(",");
                String[] sizes = parames.get("size").toString().split(",");
                    if ("1".equals(request.getImp().get(0).getAd_slot_type())) {
                        TzNative tzNative = new TzNative();
                        tb.setAd_type(8);//信息流-广告素材类型
                        ArrayList<TzImage> images = new ArrayList<>();
                        if (null != pImages) {
                            for (int im = 0; im < pImages.length; im++) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(pImages[im]);
                                tzImage.setW(Integer.valueOf(sizes[im].split("\\*")[0]));
                                tzImage.setH(Integer.valueOf(sizes[im].split("\\*")[1]));
                                images.add(tzImage);
                            }
                        }

                        tzNative.setTitle(parames.get("title").toString());
                        tzNative.setDesc(parames.get("descs").toString());
                        tzNative.setImages(images);

                        // 信息流视频
//                            if (StringUtils.isNotEmpty(metaGroup.getJSONObject(meta).getString("videoUrl"))) {
//                                video.setUrl(metaGroup.getJSONObject(meta).getString("videoUrl"));
//                                video.setH(metaGroup.getJSONObject(meta).getInteger("materialHeight"));
//                                video.setW(metaGroup.getJSONObject(meta).getInteger("materialWidth"));
//                                video.setDuration(metaGroup.getJSONObject(meta).getInteger("videoDuration"));
//                                tzNative.setVideo(video);
//                            }
                            tb.setNATIVE(tzNative);
                    } else {
                            tb.setAd_type(5);//开屏-广告素材类型
                            ArrayList<TzImage> images = new ArrayList<>();
                            if (null != pImages) {
                                for (int mim = 0; mim < pImages.length; mim++) {
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(pImages[mim]);
                                    tzImage.setW(Integer.valueOf(sizes[mim].split("\\*")[0]));
                                    tzImage.setH(Integer.valueOf(sizes[mim].split("\\*")[1]));
                                    images.add(tzImage);
                                }
                                tb.setImages(images);
                            }

                            tb.setTitle(parames.get("title").toString());
                            tb.setDesc(parames.get("descs").toString());
                    }

                        String interactionType =parames.get("interactionType").toString();//  1--浏览 2--下载 3--deeplink 0--其他
                        if ("1".equals(interactionType)) {
                            tb.setClicktype("0");//点击
                        } else if ("2".equals(interactionType)) {
                            tb.setClicktype("2");//唤醒deeplink
                        }  else {
                            tb.setClicktype("1");//
                        }


                        if (StringUtils.isNotEmpty(parames.get("deeplink").toString())) {
                            tb.setDeeplink_url(parames.get("deeplink").toString());
                            if (StringUtils.isNotEmpty(parames.get("deeplinks").toString())) {
                                List<String> deep_linkS = new ArrayList<>();
                                deep_linkS.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                                deep_linkS.add(parames.get("deeplinks").toString());
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_success_deeplinks(deep_linkS);//唤醒成功
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DEEP_LINK%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
//                            if (null != metaGroup.getJSONObject(meta).getJSONArray("arrDeepfailTrackUrl")) {
//                                List<String> deep_linkF = new ArrayList<>();
//                                deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_F%%");
//                                JSONArray urls2 = metaGroup.getJSONObject(meta).getJSONArray("arrDeepfailTrackUrl");
//                                for (int dpf = 0; dpf < urls2.size(); dpf++) {
//                                    deep_linkF.add(urls2.get(dpf).toString());
//                                }
//                                String encode2 = urls2.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                                tb.setCheck_fail_deeplinks(deep_linkF);//唤醒失败
//                                tzMacros = new TzMacros();
//                                tzMacros.setMacro("%%DEEP_LINK_F%%");
//                                tzMacros.setValue(Base64.encode(encode2));
//                                tzMacros1.add(tzMacros);
//                            }
                        }


                        tb.setClick_url(parames.get("click_url").toString()); // 点击跳转url地址

                        if (StringUtils.isNotEmpty(parames.get("check_views").toString())) {
                            List<String> check_views = new ArrayList<>();
                            String checkv = parames.get("check_views").toString();
                            check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                            check_views.add(checkv);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_VIEWS%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (StringUtils.isNotEmpty(parames.get("click_clicks").toString())) {
                            List<String> clickList = new ArrayList<>();
                            String clickClicks = parames.get("click_clicks").toString();
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                            clickList.add(clickClicks);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_CLICKS%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (StringUtils.isNotEmpty(parames.get("video_start").toString())) {
                            List<String> voidStartList = new ArrayList<>();
                            String video_start = parames.get("video_start").toString();
                            voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                            voidStartList.add(video_start);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                           // tb.setcheckv(voidStartList);//视频开始播放
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%VEDIO_START%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (StringUtils.isNotEmpty(parames.get("video_end").toString())) {
                            List<String> voidEndList = new ArrayList<>();
                            String video_end = parames.get("video_end").toString();
                            voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                            voidEndList.add(video_end);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            //tb.set(voidEndList);//视频播放结束
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%VEDIO_END%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (StringUtils.isNotEmpty(parames.get("down_start").toString())) {
                            List<String> downLoadList = new ArrayList<>();
                            String down_start = parames.get("down_start").toString();
                            downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                            downLoadList.add(down_start);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_downloads(downLoadList);//开始下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_LOAD%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                         if (StringUtils.isNotEmpty(parames.get("down_end").toString())) {
                            List<String> downLoadDList = new ArrayList<>();
                            String down_end = parames.get("down_end").toString();
                            downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                            downLoadDList.add(down_end);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_downloads(downLoadDList);//结束下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_END%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (StringUtils.isNotEmpty(parames.get("install_start").toString())) {
                            List<String> installList = new ArrayList<>();
                            String install_start = parames.get("install_start").toString();
                            installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                            installList.add(install_start);
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_installs(installList);//开始安装
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%INSTALL_START%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }
                        if (StringUtils.isNotEmpty(parames.get("install_end").toString())) {
                            List<String> installEList = new ArrayList<>();
                            String install_end = parames.get("install_end").toString();
                            installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                            installEList.add(install_end);
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
                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seatList.add(seat);
                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("美团RTA总返回" + JSONObject.toJSONString(bidResponse));
            }else {
                return null;
            }
        }
        return AsyncResult.forValue(bidResponse);
    }
}
