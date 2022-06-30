package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.onen.*;
import com.mk.adx.entity.json.request.yidianzixun.*;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.service.OneNJsonService;
import com.mk.adx.service.YdzxJsonService;
import com.mk.adx.util.AESUtil;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 1n
 * @Date 2020/10/28 9:48
 */
@Slf4j
@Service("oneNJsonService")
public class OneNJsonServiceImpl implements OneNJsonService {

    private static final String name = "1n";
    private static final String source = "1n";

    @SneakyThrows
    @Override
    public MkBidResponse getOneNDataByJson(MkBidRequest request) {
        OneNBidRequest bidRequest = new OneNBidRequest();
        OneNUser oneNUser = new OneNUser();
        OneNApp oneNApp = new OneNApp();
        OneNDevice oneNDevice = new OneNDevice();
        OneNAdSlot oneNAdSlot = new OneNAdSlot();

        //user
        oneNUser.setGender(0);

        //app
        oneNApp.setAppId(request.getAdv().getTag_id());
        oneNApp.setAppName(request.getAdv().getApp_name());
        oneNApp.setPackageName(request.getAdv().getBundle());
        oneNApp.setVersion(request.getAdv().getVersion());

        //device
        String os = request.getDevice().getOs();//操作系统，0=>Android,1=>iOS
        if(os.equals("0") || os.equals("android")|| os.equals("ANDROID")){
            oneNDevice.setDeviceId(request.getDevice().getAndroid_id());
            oneNDevice.setImei(request.getDevice().getImei());
            oneNDevice.setImeiMd5(request.getDevice().getImei_md5());
            oneNDevice.setOaid(request.getDevice().getOaid());
            oneNDevice.setOs("Android");
            oneNDevice.setRomVersion("10");
        }else if (os.equals("1")||os.equals("ios")|| os.equals("IOS")){
            oneNDevice.setDeviceId(request.getDevice().getIdfa());
            oneNDevice.setImei("");
            oneNDevice.setOpenUdid(request.getDevice().getOpen_udid());
            oneNDevice.setPhoneName(request.getDevice().getPhoneName());
            oneNDevice.setOs("IOS");
            oneNDevice.setBatteryPower(0);
            oneNDevice.setBootTimeSec(Integer.valueOf(request.getDevice().getBootTimeSec()));
            oneNDevice.setOsUpdateTimeSec(Integer.valueOf(request.getDevice().getOsUpdateTimeSec()));
            oneNDevice.setDiskSize(request.getDevice().getDiskSize());
            oneNDevice.setBatteryStatus(0);
            oneNDevice.setMemorySize(request.getDevice().getMemorySize());
            oneNDevice.setCpuNumber(request.getDevice().getCpuNumber());
            oneNDevice.setModelCode(request.getDevice().getModelCode());
            oneNDevice.setTimeZone(request.getDevice().getTimeZone());
            oneNDevice.setLmt(0);
        }
        oneNDevice.setWifiMac(request.getDevice().getMac());
        oneNDevice.setMac(request.getDevice().getMac());
        String dtype = request.getDevice().getDevicetype();//设备类型 手机：phone平板：ipadPC：pc互联网电视：tv
        if (dtype.equals("phone")){
            oneNDevice.setDeviceType(1);//手机
        }else if (dtype.equals("ipad")){
            oneNDevice.setDeviceType(2);//平板
        }else if (dtype.equals("pc")){
            oneNDevice.setDeviceType(3);//PC
        }else {
            oneNDevice.setDeviceType(0);//未知
        }
        oneNDevice.setOsVersion(request.getDevice().getOsv());
        oneNDevice.setVendor(request.getDevice().getMake());
        oneNDevice.setModel(request.getDevice().getModel());
        oneNDevice.setLanguage("中文");
        String carrier = request.getDevice().getCarrier();  //运营商-中国移动:70120,中国联通:70123，中国电信:70121,广电:70122 其他:70124,未识别:0
        if(StringUtils.isNotEmpty(carrier)){
            if (carrier.equals("70120")){
                oneNDevice.setOperatorType(1);
            }else if (carrier.equals("70123")){
                oneNDevice.setOperatorType(3);
            }else if (carrier.equals("70121")){
                oneNDevice.setOperatorType(2);
            }else {
                oneNDevice.setOperatorType(99);
            }
        }else{
            oneNDevice.setOperatorType(0);
        }
        oneNDevice.setScreenWidth(request.getDevice().getW());
        oneNDevice.setScreenHeight(request.getDevice().getH());
        oneNDevice.setOrientation(1);
        oneNDevice.setDpi((float) request.getDevice().getDeny());
        oneNDevice.setSysComplingTime(request.getDevice().getBootTimeSec());
        oneNDevice.setAppStoreVersion(request.getDevice().getAppstore_ver());
        oneNDevice.setHmsVersion(request.getDevice().getVercodeofhms());

        //adslot
        String slot_type = request.getImp().get(0).getSlot_type();
        if("1".equals(slot_type)){
            oneNAdSlot.setAdType(2);
            oneNAdSlot.setPosition(3);
        }else if("5".equals(slot_type)){
            oneNAdSlot.setAdType(1);
            oneNAdSlot.setPosition(3);
        }else if("3".equals(slot_type)){
            oneNAdSlot.setAdType(3);
            oneNAdSlot.setPosition(5);
        }else if("4".equals(slot_type)){
            oneNAdSlot.setAdType(5);
            oneNAdSlot.setPosition(3);
        }else if("6".equals(slot_type)){
            oneNAdSlot.setAdType(4);
            oneNAdSlot.setPosition(3);
        }
        oneNAdSlot.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        oneNAdSlot.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));

        bidRequest.setRequestId(request.getId());
        bidRequest.setApiVersion("2.3");
        bidRequest.setSourceType("app");
        bidRequest.setUserAgent(request.getDevice().getUa());
        bidRequest.setIp(request.getDevice().getIp());
        bidRequest.setUserInfoParam(oneNUser);
        bidRequest.setAppInfoParam(oneNApp);
        bidRequest.setDeviceInfoParam(oneNDevice);
        bidRequest.setAdSlotInfoParam(oneNAdSlot);
        bidRequest.setIsSupportDp(true);

        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid()+":请求1n数据"+content);

        MkBidResponse bidResponse = new MkBidResponse();//总返回
        String url = "http://ssp.1nmob.com/ad_api/media_ad";//测试url
        String ua = request.getDevice().getUa();//ua
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid()+"：请求上游1n花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info(request.getImp().get(0).getTagid()+"：1n返回参数"+JSONObject.parseObject(response));
        JSONObject jo = JSONObject.parseObject(response);
         if (200 == jo.getJSONObject("data").getInteger("statusCode")) {
             //多层解析json
             JSONObject imp = jo.getJSONObject("data").getJSONObject("ads");
             String id = request.getId();//请求id
             List<MkBid> bidList = new ArrayList<>();
             MkBid tb = new MkBid();

             Integer InteractionType = imp.getInteger("interactionType");//广告点击类型：1：跳转 2：下载
             if (InteractionType == 1) {
                 tb.setClicktype("1");//跳转
             } else if (InteractionType == 2) {
                 tb.setClicktype("2");//dp
             } else if (InteractionType == 3) {
                 tb.setClicktype("4");//普通下载
                 tb.setDownload_url(imp.getString("clickAdUrl"));//下载地址
             } else if (InteractionType == 4) {
                 tb.setClicktype("4");//下载
             }

             /**
              * 视频
              */
             MkVideo video = new MkVideo();
             if (null != imp.getJSONObject("video")) {
                 video.setUrl(imp.getJSONObject("video").getString("videoUrl"));//视频广告素材地址URL
                 video.setW(imp.getJSONObject("video").getInteger("videoWidth"));//视频宽
                 video.setH(imp.getJSONObject("video").getInteger("videoHeight"));//视频高
                 video.setDuration(imp.getJSONObject("video").getInteger("videoDuration"));//视频播放时长，单位秒
                 tb.setVideo(video);//视频素材
             }

             /**
              * 图片
              */
             List<MkImage> mkImages = new ArrayList<>();
             if (null != imp.getJSONArray("imageSrcs")) {
                 JSONArray images = imp.getJSONArray("imageSrcs");
                 for (int ima = 0; ima < images.size(); ima++) {
                     MkImage image = new MkImage();
                     image.setUrl(images.get(ima).toString());
                     mkImages.add(image);
                 }
             }
             tb.setImages(mkImages);

             if ("1".equals(request.getImp().get(0).getSlot_type()) || "2".equals(request.getImp().get(0).getSlot_type())) {//信息流或banner
                 tb.setAd_type(1);//原生-广告素材类型
             } else if ("3".equals(request.getImp().get(0).getSlot_type())) {
                 tb.setAd_type(2);//开屏-广告素材类型
             } else if ("4".equals(request.getImp().get(0).getSlot_type())) {
                 tb.setAd_type(5);//视频-广告素材类型
             }


             tb.setClick_url(imp.getString("clickAdUrl"));//用户点击后需要跳转到的落地页


             String deeplinkurl = imp.getString("deeplink");
             if (StringUtils.isNotEmpty(deeplinkurl)) {
                 tb.setDeeplink_url(deeplinkurl);//deeplink链接，如返回结果中包括deeplink链接则调起第三方应用，否则跳转落地页

             }

             tb.setTitle(imp.getString("title"));
             tb.setDesc(imp.getString("descriptions"));
             tb.setAic(imp.getString("iconSrcs"));

             MkApp mkBidApps = new MkApp();
             mkBidApps.setApp_name(imp.getString("appName"));
             mkBidApps.setBundle(imp.getString("packageName"));
             tb.setApp(mkBidApps);


             String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

             JSONArray tracks = imp.getJSONArray("tracks");
             for (int i = 0; i < tracks.size(); i++) {
                 Integer type = tracks.getJSONObject(i).getInteger("type");


                 if (1 == type) {
                     List<String> checkViews = new ArrayList<>();//曝光监测地址数组
                     checkViews.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             String replace =  urls.getString(j).replace("__DOWN_X__","__DOWN_X__").replace("__DOWN_Y__","__DOWN_Y__").replace("__UP_X__","__UP_X__").replace("__UP_Y__","__UP_Y__").replace("__TIME_START__","__TS__");
                             checkViews.add(replace);
                         }
                     }
                     tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                 }

                 if (2 == type) {
                     List<String> checkClicks = new ArrayList<>();
                     checkClicks.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             String replace =  urls.getString(j).replace("__DOWN_X__","__DOWN_X__").replace("__DOWN_Y__","__DOWN_Y__").replace("__UP_X__","__UP_X__").replace("__UP_Y__","__UP_Y__").replace("__TIME_START__","__TS__");
                             checkClicks.add(replace);
                         }
                     }
                     tb.setCheck_clicks(checkClicks);//点击监测URL,支持宏替换第三方曝光监测
                 }

                 if (3 == type) {
                     List<String> checkStartDownloads = new ArrayList<>();
                     checkStartDownloads.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             checkStartDownloads.add(urls.getString(j));
                         }
                     }
                     tb.setCheck_start_downloads(checkStartDownloads);//下载开始
                 }

                 if (4 == type) {
                     List<String> checkEndDownloads = new ArrayList<>();
                     checkEndDownloads.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             checkEndDownloads.add(urls.getString(j));
                         }
                     }
                     tb.setCheck_end_downloads(checkEndDownloads);//下载结束
                 }


                 if (5 == type) {
                     List<String> installList = new ArrayList<>();
                     installList.add("http://adx.fxlxz.com/sl/in_start?installStart=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             installList.add(urls.getString(j));
                         }
                     }
                     tb.setCheck_start_installs(installList);//安装开始
                 }

                 if (6 == type) {
                     List<String> installEList = new ArrayList<>();
                     installEList.add("http://adx.fxlxz.com/sl/in_end?installEnd=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             installEList.add(urls.getString(j));
                         }
                     }
                     tb.setCheck_end_installs(installEList);//安装结束
                 }

                 if (7 == type) {
                     List<String> checkSuccessDeeplinks = new ArrayList<>();
                     checkSuccessDeeplinks.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                     JSONArray urls = tracks.getJSONObject(i).getJSONArray("urls");
                     if (null != urls) {
                         for (int j = 0; j < urls.size(); j++) {
                             checkSuccessDeeplinks.add(urls.getString(j));
                         }
                     }
                     tb.setCheck_success_deeplinks(checkSuccessDeeplinks);//安装结束
                 }

             }

             bidList.add(tb);//素材集合
             bidResponse.setId(id);//请求id
             bidResponse.setBidid(id);//请求id
             bidResponse.setSeatbid(bidList);//广告集合对象
             bidResponse.setProcess_time_ms(tempTime);
             log.info(request.getImp().get(0).getTagid() + "：1n总返回数据" + JSONObject.toJSONString(bidResponse));
         }
        return bidResponse;
    }

}
