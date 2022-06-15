package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.service.DspJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author mzs
 * @Description dsp
 * @Date 2022/1/10 11:27
 */
@Slf4j
@Service("dspJsonService")
public class DspJsonServiceImpl implements DspJsonService {

    private static final String name = "dsp";
    private static final String source = "DSP";

    /**
     * @Author mzs
     * @Description dsp
     * @Date 2021/12/03 17:00
     */
    @SneakyThrows
    @Override
    public TzBidResponse getDspDataByJson(TzBidRequest request, Map parames) {
        TzBidResponse bidResponse = new TzBidResponse();//总返回
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
                        String[] split = pImages[im].split("\\.");
                        if("png".equals(split[split.length-1]) || "jpg".equals(split[split.length-1])){
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(pImages[im]);
                            tzImage.setW(Integer.valueOf(sizes[im].split("\\*")[0]));
                            tzImage.setH(Integer.valueOf(sizes[im].split("\\*")[1]));
                            images.add(tzImage);
                        }else if("mp4".equals(split[split.length-1])){
                            video.setUrl(pImages[im]);
                            video.setW(Integer.valueOf(sizes[im].split("\\*")[0]));
                            video.setH(Integer.valueOf(sizes[im].split("\\*")[1]));
                            tzNative.setVideo(video);
                        }
                    }
                }
                tzNative.setTitle(parames.get("title").toString());
                tzNative.setDesc(parames.get("descs").toString());
                tzNative.setImages(images);

                // 信息流视频
//                    if (StringUtils.isNotEmpty(metaGroup.getJSONObject(meta).getString("videoUrl"))) {
//                        video.setUrl(metaGroup.getJSONObject(meta).getString("videoUrl"));
//                        video.setH(metaGroup.getJSONObject(meta).getInteger("materialHeight"));
//                        video.setW(metaGroup.getJSONObject(meta).getInteger("materialWidth"));
//                        video.setDuration(metaGroup.getJSONObject(meta).getInteger("videoDuration"));
//                        tzNative.setVideo(video);
//                    }
                tb.setNATIVE(tzNative);
            } else {
                tb.setAd_type(5);//开屏-广告素材类型
                ArrayList<TzImage> images = new ArrayList<>();
                if (null != pImages) {
                    for (int mim = 0; mim < pImages.length; mim++) {
                        String[] split = pImages[mim].split("\\.");
                        if("png".equals(split[split.length-1]) || "jpg".equals(split[split.length-1])){
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(pImages[mim]);
                            tzImage.setW(Integer.valueOf(sizes[mim].split("\\*")[0]));
                            tzImage.setH(Integer.valueOf(sizes[mim].split("\\*")[1]));
                            images.add(tzImage);
                            tb.setImages(images);
                        }else if("mp4".equals(split[split.length-1])){
                            TzVideo tzVideo = new TzVideo();
                            tzVideo.setUrl(pImages[mim]);
                            tzVideo.setW(Integer.valueOf(sizes[mim].split("\\*")[0]));
                            tzVideo.setH(Integer.valueOf(sizes[mim].split("\\*")[1]));
                            tb.setVideo(tzVideo);
                        }
                    }
                }
                tb.setTitle(parames.get("title").toString());
                tb.setDesc(parames.get("descs").toString());
            }
                String interactionType =parames.get("interactionType").toString();//  1--浏览 2--下载 3--deeplink 0--其他
                if ("1".equals(interactionType)) {
                    tb.setClicktype("4");//点击
                } else if ("2".equals(interactionType)) {
                    tb.setClicktype("2");//唤醒deeplink
                }  else {
                    tb.setClicktype("4");//
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
//                    if (null != metaGroup.getJSONObject(meta).getJSONArray("arrDeepfailTrackUrl")) {
//                        List<String> deep_linkF = new ArrayList<>();
//                        deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_F%%");
//                        JSONArray urls2 = metaGroup.getJSONObject(meta).getJSONArray("arrDeepfailTrackUrl");
//                        for (int dpf = 0; dpf < urls2.size(); dpf++) {
//                            deep_linkF.add(urls2.get(dpf).toString());
//                        }
//                        String encode2 = urls2.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                        tb.setCheck_fail_deeplinks(deep_linkF);//唤醒失败
//                        tzMacros = new TzMacros();
//                        tzMacros.setMacro("%%DEEP_LINK_F%%");
//                        tzMacros.setValue(Base64.encode(encode2));
//                        tzMacros1.add(tzMacros);
//                    }
                }


                tb.setClick_url(parames.get("click_url").toString()); // 点击跳转url地址

                if (null != parames.get("check_views")) {
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
                if (null != parames.get("click_clicks")) {
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
                List<TzCheckVideoUrls> tzCheckVideoUrls = new ArrayList();
                if (null != parames.get("video_start")) {
                    List<String> voidStartList = new ArrayList<>();
                    String video_start = parames.get("video_start").toString();
                    voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                    voidStartList.add(video_start);
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                   // tb.setcheckv(voidStartList);//视频开始播放
                    TzCheckVideoUrls tzCheckVideo = new TzCheckVideoUrls();
                    tzCheckVideo.setTime(0);
               //     tzCheckVideo.setUrl(encode);
                    tzCheckVideoUrls.add(tzCheckVideo);
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%VEDIO_START%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if (null != parames.get("video_end")) {
                    List<String> voidEndList = new ArrayList<>();
                    String video_end = parames.get("video_end").toString();
                    voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                    voidEndList.add(video_end);
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    //tb.set(voidEndList);//视频播放结束
                    TzCheckVideoUrls tzCheckVideo = new TzCheckVideoUrls();
                    tzCheckVideo.setTime(1);
                   // tzCheckVideo.setUrl(encode);
                    tzCheckVideoUrls.add(tzCheckVideo);
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%VEDIO_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                tb.setCheck_video_urls(tzCheckVideoUrls);
                if (null != parames.get("down_start")) {
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
                 if (null != parames.get("down_end")) {
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
                if (null  != parames.get("install_start")) {
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
                if (null != parames.get("install_end")) {
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
        log.info("DSP总返回" + JSONObject.toJSONString(bidResponse));
        return bidResponse;
    }
}
