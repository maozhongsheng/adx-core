package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.xiaomi.*;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.service.XiaoMiJsonService;
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
 * @Description
 * @date 2022/4/7 15:21
 */
@Slf4j
@Service("xiaoMiJsonService")
public class XiaoMiJsonServiceImpl implements XiaoMiJsonService {

    private static final String name = "xiaomi";

    private static final String source = "小米";

    @SneakyThrows
    @Override
    public MkBidResponse getXiaoMiDataByJson(MkBidRequest request) {
        XiaoMiBidRequest bidRequest = new XiaoMiBidRequest();
        XiaoMiDevice xiaoMiDevice = new XiaoMiDevice();
        XiaoMiUser xiaoMiUser = new XiaoMiUser();
        XiaoMiApp xiaoMiApp = new XiaoMiApp();
        XiaoMiImp xiaoMiImp = new XiaoMiImp();
        List<XiaoMiImp> xiaoMiImps = new ArrayList();
        XiaoMiContent xiaoMiContent = new XiaoMiContent();
        XiaoMiContext xiaoMiContext = new XiaoMiContext();

        //device
        xiaoMiDevice.setMake(request.getDevice().getMake());
        xiaoMiDevice.setModel(request.getDevice().getModel());
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            xiaoMiDevice.setOs("android");
            xiaoMiDevice.setAndroidVersion("11.0.2");
            xiaoMiUser.setImei(request.getDevice().getImei());
            xiaoMiUser.setOaid(request.getDevice().getOaid());
            xiaoMiUser.setAndroidId(request.getDevice().getAndroid_id());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            xiaoMiDevice.setOs("ios");
        }
        xiaoMiDevice.setScreenWidth(request.getDevice().getW());
        xiaoMiDevice.setScreenHeight(request.getDevice().getH());
        xiaoMiUser.setUa(request.getDevice().getUa());

        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            xiaoMiUser.setConnectionType("unknown");
        }else if("2".equals(connectiontype)){
            xiaoMiUser.setConnectionType("wifi");
        }else if("4".equals(connectiontype)){
            xiaoMiUser.setConnectionType("2g");
        }else if("5".equals(connectiontype)){
            xiaoMiUser.setConnectionType("3g");
        }else if("6".equals(connectiontype)){
            xiaoMiUser.setConnectionType("4g");
        }else if("7".equals(connectiontype)){
            xiaoMiUser.setConnectionType("5g");
        } else {
            xiaoMiUser.setConnectionType("unknown");
        }
        xiaoMiUser.setIp(request.getDevice().getIp());
        xiaoMiUser.setMac(request.getDevice().getMac());

        //app
        xiaoMiApp.setPackageName(request.getAdv().getBundle());
        xiaoMiApp.setVersion(request.getAdv().getVersion());

        //imp
        xiaoMiImp.setUpId(request.getAdv().getTag_id());
        xiaoMiImp.setAdsCount(1);
        xiaoMiImps.add(xiaoMiImp);

        //content



        bidRequest.setDeviceInfo(xiaoMiDevice);
        bidRequest.setUserInfo(xiaoMiUser);
        bidRequest.setAppInfo(xiaoMiApp);
        bidRequest.setImpRequests(xiaoMiImps);
        bidRequest.setContentInfo(xiaoMiContent);
        bidRequest.setContext(xiaoMiContext);


        MkBidResponse bidResponse = new MkBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求小米广告参数"+JSONObject.parseObject(content));
        String url = "http://api.ad.xiaomi.com/u/api/v3";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游小米广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":小米广告返回参数"+JSONObject.parseObject(response));

        if (0 == JSONObject.parseObject(response).getInteger("code")) {
            String id = request.getId();
            //多层解析json
            JSONArray imp = JSONObject.parseObject(response).getJSONArray("adInfos");
            List<MkBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                MkBid tb = new MkBid();
                ArrayList<MkImage> images = new ArrayList<>();

                JSONArray navives = imp.getJSONObject(i).getJSONArray("assets");
                for (int na = 0; na < navives.size(); na++) {
                    MkImage tzImage = new MkImage();
                    tzImage.setUrl(navives.getJSONObject(na).getString("url"));
                    tzImage.setH(navives.getJSONObject(na).getInteger("height"));
                    tzImage.setW(navives.getJSONObject(na).getInteger("width"));
                    images.add(tzImage);
                }
                tb.setImages(images);

                if("1".equals(request.getImp().get(0).getSlot_type())){
                    tb.setAd_type(1);//信息流-广告素材类型
                }else if ("4".equals(request.getImp().get(0).getSlot_type())){
                    MkVideo video = new MkVideo();
                    tb.setAd_type(5);//视频-广告素材类型
                    JSONArray videos = imp.getJSONObject(i).getJSONArray("assets");
                    if(null != videos){
                        video.setUrl(videos.getJSONObject(0).getString("url"));
                        video.setH(videos.getJSONObject(0).getInteger("height"));
                        video.setW(videos.getJSONObject(0).getInteger("width"));
                    }

                    tb.setTitle(imp.getJSONObject(i).getString("title"));
                    tb.setDesc(imp.getJSONObject(i).getString("summary"));
                    tb.setVideo(video);//视频素材

                }else{
                    tb.setAd_type(2);//视频-广告素材类型
                }


                Integer targetType = imp.getJSONObject(i).getInteger("targetType");
                if(1 == targetType){
                    tb.setClicktype("1");//跳转
                }else if(2 == targetType){
                    tb.setClicktype("2");//下载
                }

                MkApp tzBidApps = new MkApp();
                tzBidApps.setApp_icon(imp.getJSONObject(i).getString("iconUrl"));
                tzBidApps.setBundle(imp.getJSONObject(i).getString("packageName"));
                tb.setApp(tzBidApps);

                tb.setClick_url(imp.getJSONObject(i).getString("landingPageUrl"));
                tb.setDownload_url(imp.getJSONObject(i).getString("actionUrl"));

                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink"));
                }

                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

                if (null != imp.getJSONObject(i).getJSONArray("viewMonitorUrls")) {
                    JSONArray apv = imp.getJSONObject(i).getJSONArray("viewMonitorUrls");
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx.fxlxz.com/sl/pv?pv="+ Base64.encode(encode));
                    for (int cv = 0; cv < apv.size(); cv++) {
                        String replace = apv.get(cv).toString().replace("{HEIGHT}", "%%HEIGHT%%").replace("{WIDTH}", "%%WIDTH%%").replace("{DOWNX}", "%%DOWN_X%%").replace("{DOWNY}", "%%DOWN_Y%%");
                        check_views.add(replace);
                    }
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                }

                if (null != imp.getJSONObject(i).getJSONArray("clickMonitorUrls")) {
                    JSONArray aclick = imp.getJSONObject(i).getJSONArray("clickMonitorUrls");
                    List<String> clickList = new ArrayList<>();
                    clickList.add("http://adx.fxlxz.com/sl/click?click="+ Base64.encode(encode));
                    for (int cc = 0; cc < aclick.size(); cc++) {
                        String replace = aclick.get(cc).toString().replace("{HEIGHT}", "%%HEIGHT%%").replace("{WIDTH}", "%%WIDTH%%").replace("{DOWNX}", "%%DOWN_X%%").replace("{DOWNY}", "%%DOWN_Y%%");
                        clickList.add(replace);
                    }
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测

                }

                if (null != imp.getJSONObject(i).getJSONArray("videoStartMonitorUrls")) {
                    List<MkCheckVideoUrls> videoUrls = new ArrayList();
                    MkCheckVideoUrls tzCheckVideoUrls = new MkCheckVideoUrls();
                    List<String> video = new ArrayList();
                    tzCheckVideoUrls.setTime(0);
                    JSONArray aclick = imp.getJSONObject(i).getJSONArray("videoStartMonitorUrls");
                    video.add("http://adx.fxlxz.com/sl/v_start?vedioStart=" + Base64.encode(encode));
                    for (int cc = 0; cc < aclick.size(); cc++) {
                        video.add(aclick.get(cc).toString());
                    }
                    tzCheckVideoUrls.setUrl(video);
                    videoUrls.add(tzCheckVideoUrls);



                    if (null != imp.getJSONObject(i).getJSONArray("videoFinishMonitorUrls")) {
                        JSONArray aclick2 = imp.getJSONObject(i).getJSONArray("videoFinishMonitorUrls");
                        MkCheckVideoUrls tzCheckVideoUrls2 = new MkCheckVideoUrls();
                        List<String> video2 = new ArrayList<>();
                        video2.add("http://adx.fxlxz.com/sl/v_end?vedioEnd=" + Base64.encode(encode));
                        for (int cc = 0; cc < aclick2.size(); cc++) {
                            video2.add(aclick2.get(cc).toString());
                        }
                        tzCheckVideoUrls2.setTime(1);
                        tzCheckVideoUrls2.setUrl(video2);
                        videoUrls.add(tzCheckVideoUrls2);
                    }
                    tb.setCheck_video_urls(videoUrls);//点击监测URL第三方曝光监测
                }
                bidList.add(tb);
            }
            bidResponse.setId(id);//请求id
            bidResponse.setBidid(id);
            bidResponse.setSeatbid(bidList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info(request.getImp().get(0).getTagid()+":小米总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
