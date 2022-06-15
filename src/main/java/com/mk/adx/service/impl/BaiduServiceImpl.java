package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.ProtocolStringList;
import com.googlecode.protobuf.format.JsonFormat;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.entity.protobuf.jm.BaiduMobadsApi5;
import com.mk.adx.service.BaiduJsonService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author yjn
 * @Description 百度
 * @Date 2020/10/28 9:48
 */
@Slf4j
@Service("baiduJsonService")
public class BaiduServiceImpl implements BaiduJsonService {
    private static final String name = "baidu";
    private static final String source = "百度";

    private static Pattern NUM_PATTERN = Pattern.compile("[^0-9]");

    @SneakyThrows
    @Override
    public TzBidResponse getBaiduDataByJson(TzBidRequest request) {
        TzBidResponse bidResponse = new TzBidResponse();//总返回

        BaiduMobadsApi5.AdSlot.Builder adslot = BaiduMobadsApi5.AdSlot.newBuilder();
        BaiduMobadsApi5.App.Builder app = BaiduMobadsApi5.App.newBuilder();
        BaiduMobadsApi5.Device.Builder device = BaiduMobadsApi5.Device.newBuilder();
        BaiduMobadsApi5.Network.Builder network = BaiduMobadsApi5.Network.newBuilder();
        BaiduMobadsApi5.Gps.Builder gps = BaiduMobadsApi5.Gps.newBuilder();
        BaiduMobadsApi5.Page.Builder page = BaiduMobadsApi5.Page.newBuilder();
        BaiduMobadsApi5.UserInfo.Builder userinfo = BaiduMobadsApi5.UserInfo.newBuilder();
        BaiduMobadsApi5.UdId.Builder udid = BaiduMobadsApi5.UdId.newBuilder();
        BaiduMobadsApi5.Size.Builder size = BaiduMobadsApi5.Size.newBuilder();
        BaiduMobadsApi5.WiFiAp.Builder wifiAp = BaiduMobadsApi5.WiFiAp.newBuilder();
        if (null != request.getImp()) {
            for (int i = 0; i < request.getImp().size(); i++) {
                adslot.setAdslotId(request.getAdv().getTag_id());
                size.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
                size.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
                adslot.setAdslotSize(size);
            }

        }
        app.setAppId(request.getAdv().getApp_id());
        app.setAppPackage(request.getAdv().getBundle()); //request.getApp().getName()

        //   app.setChannelId(request.get); // 选填！发布渠道ID，渠道接入方必需填写
        BaiduMobadsApi5.Version.Builder version = BaiduMobadsApi5.Version.newBuilder();
        String trim = NUM_PATTERN.matcher(request.getApp().getVer()).replaceAll("").trim();
        String[] versions = request.getAdv().getVersion().split("\\.");
        version.setMajor(Integer.valueOf(versions[0]));
        version.setMinor(Integer.valueOf(versions[1]));
        version.setMicro(Integer.valueOf(versions[2]));
        app.setAppVersion(version);


        String devicetype = request.getDevice().getDevicetype();
        if ("phone".equals(devicetype)) {
            device.setDeviceType(BaiduMobadsApi5.Device.DeviceType.PHONE);
        } else if ("ipad".equals(devicetype)) {
            device.setDeviceType(BaiduMobadsApi5.Device.DeviceType.TABLET);
        } else if ("tv".equals(devicetype)) {
            device.setDeviceType(BaiduMobadsApi5.Device.DeviceType.SMART_TV);
        } else {
            device.setDeviceType(BaiduMobadsApi5.Device.DeviceType.OUTDOOR_SCREEN);
        }
        String os = request.getDevice().getOs();
        if (os.equals("0") || os.equals("android") || os.equals("ANDROID")) {
            device.setOsType(BaiduMobadsApi5.Device.OsType.ANDROID);
            if(StringUtils.isNotEmpty(request.getDevice().getImei())){
                udid.setImei(request.getDevice().getImei());
            }else{
                if(StringUtils.isNotEmpty(request.getDevice().getOaid())) {
                    udid.setOaid(request.getDevice().getOaid());
                }else{
                    udid.setImei("866499047049918");
                }
            }
            if(StringUtils.isNotEmpty(request.getDevice().getAndroid_id())){
                udid.setAndroidId(request.getDevice().getAndroid_id());
            }else{
                return bidResponse;
            }

            if(StringUtils.isNotEmpty(request.getDevice().getMac())){
                udid.setMac(request.getDevice().getMac());
            }
            if(StringUtils.isNotEmpty(request.getDevice().getImei_md5())) {
                udid.setImeiMd5(request.getDevice().getImei_md5());
            }
            if(StringUtils.isNotEmpty(request.getDevice().getAndroid_id_md5())){
                udid.setAndroididMd5(request.getDevice().getAndroid_id_md5());
            }
            //    udid.setOaidMd5(request.getDevice().getOaid());
            device.setUdid(udid);
        } else if (os.equals("1") || os.equals("ios") || os.equals("IOS")) {
            device.setOsType(BaiduMobadsApi5.Device.OsType.IOS);
            //校验ios中的idfa
            if(StringUtils.isNotEmpty(request.getDevice().getIdfa())){
                udid.setIdfa(request.getDevice().getIdfa());
            }else{
                return bidResponse;
            }

            udid.setMac(request.getDevice().getMac());
            udid.setIdfaMd5(request.getDevice().getIdfa_md5());
            device.setUdid(udid);
        }
        version = BaiduMobadsApi5.Version.newBuilder();
        String trim1 = NUM_PATTERN.matcher(request.getDevice().getOsv()).replaceAll("").trim();
        version.setMajor(8);
        version.setMinor(1);
        version.setMicro(0);
        device.setOsVersion(version);
        if(StringUtils.isNotEmpty(request.getDevice().getMake())){
            device.setVendor(ByteString.copyFrom(request.getDevice().getMake().getBytes()));
        }else{
            device.setVendor(ByteString.copyFrom("HW".getBytes()));
        }
        device.setModel(ByteString.copyFrom(request.getDevice().getModel().getBytes()));
        size = BaiduMobadsApi5.Size.newBuilder();
        size.setWidth(request.getDevice().getW());
        size.setHeight(request.getDevice().getH());
        device.setScreenSize(size);


        network.setIpv4(request.getDevice().getIp());  //request.getDevice().getIp()
        Integer connectiontype = request.getDevice().getConnectiontype();
        if (0 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.CONNECTION_UNKNOWN);
        } else if (1 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.ETHERNET);
        } else if (2 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.WIFI);
        } else if (3 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.CELL_UNKNOWN);
        } else if (4 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.CELL_2G);
        } else if (5 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.CELL_3G);
        } else if (6 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.CELL_4G);
        } else if (7 == connectiontype) {
            network.setConnectionType(BaiduMobadsApi5.Network.ConnectionType.CELL_5G);
        }

        String carrier = request.getDevice().getCarrier();
        if ("70120".equals(carrier)) {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.CHINA_MOBILE);
        } else if ("70123".equals(carrier)) {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.CHINA_UNICOM);
        } else if ("70121".equals(carrier)) {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.CHINA_TELECOM);
        } else if ("70122".equals(carrier)) {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.OTHER_OPERATOR);
        } else if ("70124".equals(carrier)) {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.OTHER_OPERATOR);
        } else if ("0".equals(carrier)) {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.UNKNOWN_OPERATOR);
        }else {
            network.setOperatorType(BaiduMobadsApi5.Network.OperatorType.UNKNOWN_OPERATOR);
        }
//        network.setCellularId();
//        network.setWifiAps(wifiAp);


      gps.setCoordinateType(BaiduMobadsApi5.Gps.CoordinateType.BD09);
        gps.setLatitude(30.683977); //request.getDevice().getGeo().getLat()
        gps.setLongitude(104.763266); //request.getDevice().getGeo().getLon()
      //  if(StringUtils.isNotEmpty(request.getDevice().getGeo().getLlt())){
            gps.setTimestamp(2110361);  //Integer.valueOf(request.getDevice().getGeo().getLlt())
    //    }
        String gender = "";
        if(null != request.getUser()){
             gender = request.getUser().getGender();
        }
        if ("M".equals(gender) || "male".equals(gender)) {
            userinfo.setGender(1);
        } else if ("F".equals(gender) || "female".equals(gender)) {
            userinfo.setGender(2);
        } else {
            userinfo.setGender(0);
        }


        BaiduMobadsApi5.MobadsRequest.Builder mobadsRequest = BaiduMobadsApi5.MobadsRequest.newBuilder();
        if(32 == request.getId().length()){
            mobadsRequest.setRequestId(request.getId());
        }else{
            String zfcAll = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            char[] zfc = zfcAll.toCharArray();
            String str = RandomStringUtils.random(32,zfc);
            mobadsRequest.setRequestId(str);
        }
        version = BaiduMobadsApi5.Version.newBuilder();
        version.setMajor(5);
        version.setMinor(6);
        version.setMicro(0);
        mobadsRequest.setApiVersion(version);
        mobadsRequest.setApp(app);
        mobadsRequest.setDevice(device);
        mobadsRequest.setNetwork(network);
        mobadsRequest.setGps(gps);
        mobadsRequest.setAdslot(adslot);
      //  mobadsRequest.setAppListEx();
        // mobadsRequest.setIsDebug();  //勿用于线上请求
        // mobadsRequest.setRequestProtocolType();// 选填, https媒体必填！
        // mobadsRequest.setPage(page);
        // mobadsRequest.setUserinfo(userinfo);
        // mobadsRequest.setMediaSupportAbility();// 选填 媒体支持的能力，赋值参考枚举MediaSupportAbility，同时支持多种时按位取或

//        BaiduMobadsApi5.MobadsRequest mrequest = mobadsRequest.build();
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        try {
//            mrequest.writeTo(output);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
       // String content = JSONObject.toJSONString(mobadsRequest);
        String jsonFormat1 = JsonFormat.printToString(mobadsRequest.build());
        log.info(request.getImp().get(0).getTagid()+"-请求百度参数" + jsonFormat1);
        String url = "http://mobads.baidu.com/api_5";//正式
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        URI uri = new URI(url);
        HttpPost request3 = new HttpPost(uri);
        String ua = request.getDevice().getUa();//请求媒体的ua
        HttpResponse response = HttppostUtil.doProtobufPost(request3, mobadsRequest.build(), ua);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid()+"-请求上游百度花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));

        String id = request.getId();//请求id
        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        if (null!=response){
            BaiduMobadsApi5.MobadsResponse mobadsResponse = BaiduMobadsApi5.MobadsResponse.parseFrom(response.getEntity().getContent()); //httpEntity1.getBytes(StandardCharsets.UTF_8)
            String jsonFormat = JsonFormat.printToString(mobadsResponse);
            log.info(request.getImp().get(0).getTagid()+"-百度返回参数:" + JSONObject.parseObject(jsonFormat));
            if(0 == mobadsResponse.getErrorCode()) {
                List<TzSeat> seatList = new ArrayList<>();
                List<TzBid> bidList = new ArrayList<>();
                TzBid tb = new TzBid();
                List<TzImage> image = new ArrayList();
                TzIcon tzIcon = new TzIcon();
                TzBidApps tzBidApps = new TzBidApps();

                BaiduMobadsApi5.Ad ads = mobadsResponse.getAds(0);
                tb.setAdid(ads.getAdslotId());// 对应请求时填写的广告位ID
                tb.setValid_time(mobadsResponse.getExpirationTime()); // 广告清单过期时间戳，单位秒
                // String adKey = ads.getAdKey();// 对当前返回广告的签名，可以唯一标识广告
                BaiduMobadsApi5.MaterialMeta materialMeta = ads.getMetaGroup(0);
//        int chargeType = ads.getChargeType();  // 广告计费模式，0：CPM，1：CPC
//        long startTime1 = ads.getStartTime(); // 曝光控制的开始时间，Unix时间戳，该字段仅供特殊媒体需要预加载时使用
//        long endTime1 = ads.getEndTime();// 曝光控制的结束时间，Unix时间戳，该字段仅供特殊媒体需要预加载时使用

              BaiduMobadsApi5.MaterialMeta.CreativeType creativeType = materialMeta.getCreativeType(); // 创意类型  NO_TYPE = 0; // 无创意类型，主要针对原生自定义素材广告，不再制定返回广告的创意类型，根据广告位设置对返回字段进行读取即可TEXT = 1;  // 纯文字广告，一般由title、description构成IMAGE = 2;  // 纯图片广告，一般由单张image_src构成IMAGE_TEXT = 5;  // 图文混合广告，一般由image_src和title、description构成ICON_IMAGE = 6;  // 图标图片广告,一般由image_src和icon_src、title、description构成
              BaiduMobadsApi5.MaterialMeta.InteractionType interactionType = materialMeta.getInteractionType();// 交互类型NO_INTERACTION = 0;  // 无动作，即广告广告点击后无需进行任何响应SURFING = 1;  // 使用浏览器打开网页DOWNLOAD = 2;  // 下载应用DEEPLINK = 3;  // deeplink唤醒
              int number = interactionType.getNumber();
              if (0 == number || 1 == number) {
                  tb.setClicktype("0");
              } else if (2 == number) {
                  tb.setClicktype("4");
              } else if (3 == number) {
                  tb.setDeeplink_url(materialMeta.getDeeplinkUrl().toString());
                  tb.setClicktype("2");
              }else{
                  tb.setClicktype("0");
              }
//              if(materialMeta.getClickUrl().contains("cpro.baidu.com")){
//                  return  new TzBidResponse();
//              }
              tb.setClick_url(materialMeta.getClickUrl()); // 点击跳转url地址
              // ProtocolStringList winNoticeUrlList = materialMeta.getWinNoticeUrlList(); // 曝光日志URL列表，在曝光后必须在客户端逐个汇报完

              if (0 == ads.getAdslotType() || 46 == ads.getAdslotType()) { //横幅
                  if (0 == ads.getAdslotType()) {
                      tb.setAd_type(0);
                  } else if (11 == ads.getAdslotType()) {
                      tb.setAd_type(3);
                  } else if (46 == ads.getAdslotType()) {
                      tb.setAd_type(5);
                  }
                  if(StringUtils.isNotEmpty(materialMeta.getAdTitle())){
                      tb.setTitle(materialMeta.getAdTitle());
                  }else{
                      tb.setTitle(materialMeta.getBrandName());
                  }
                  // tb.setDesc(materialMeta.getDescription(0).toString());
                  if(null != materialMeta.getIconSrcList()){
                      for (int icon = 0; icon< materialMeta.getIconSrcList().size() ; icon++ ) {
                          tb.setAic(materialMeta.getIconSrc(icon));
                      }
                  }

                    ProtocolStringList imageSrcList = materialMeta.getImageSrcList();
                    for (int ima = 0; ima < imageSrcList.size(); ima++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(imageSrcList.get(ima));
                        tzImage.setW(materialMeta.getMaterialWidth());
                        tzImage.setH(materialMeta.getMaterialHeight());
                        image.add(tzImage);
                    }
                    tb.setImages(image);

                    if (4 == creativeType.getNumber()) {
                        TzVideo video = new TzVideo();
                        video.setUrl(materialMeta.getVideoUrl());//视频广告素材地址URL
                        video.setW(materialMeta.getMaterialWidth());//视频宽
                        video.setH(materialMeta.getMaterialHeight());//视频高
                        video.setDuration(materialMeta.getVideoDuration());//视频播放时长，单位秒
                        tb.setVideo(video);//视频素材
                    }


              } else if (13 == ads.getAdslotType() || 11 == ads.getAdslotType()) {
                  tb.setAd_type(8);
                  TzNative tzNative = new TzNative();
                  if(StringUtils.isNotEmpty(materialMeta.getAdTitle())){
                      tzNative.setTitle(materialMeta.getAdTitle());
                  }else{
                      tzNative.setTitle(materialMeta.getBrandName());
                  }
                 // tzNative.setDesc(materialMeta.getDescription(0).toString());
                  if(null != materialMeta.getIconSrcList()){
                      for (int icon = 0; icon< materialMeta.getIconSrcList().size() ; icon++ ) {
                          tb.setAic(materialMeta.getIconSrc(icon));
                      }
                  }
                  tzNative.setIcon(tzIcon);
                  ProtocolStringList imageSrcList = materialMeta.getImageSrcList();
                  for (int ima = 0; ima < imageSrcList.size(); ima++) {
                      TzImage tzImage = new TzImage();
                      tzImage.setUrl(imageSrcList.get(ima));
                      tzImage.setW(materialMeta.getMaterialWidth());
                      tzImage.setH(materialMeta.getMaterialHeight());
                      image.add(tzImage);
                  }

                    tzNative.setImages(image);
                    if (4 == creativeType.getNumber()) {
                        TzVideo video = new TzVideo();
                        video.setUrl(materialMeta.getVideoUrl());//视频广告素材地址URL
                        video.setW(materialMeta.getMaterialWidth());//视频宽
                        video.setH(materialMeta.getMaterialHeight());//视频高
                        video.setDuration(materialMeta.getVideoDuration());//视频播放时长，单位秒
                        tzNative.setVideo(video);//视频素材
                    }
                    tzNative.setTitle(materialMeta.getAdTitle());
                    if(null != materialMeta.getDescriptionList()){
                        for (int desc = 0; desc< materialMeta.getDescriptionList().size() ; desc++ ){
                            tzNative.setDesc(materialMeta.getDescription(desc).toStringUtf8());
                        }
                    }
                    tb.setNATIVE(tzNative);
                }


                tzBidApps.setBundle(materialMeta.getAppPackage());
                tzBidApps.setApp_size(materialMeta.getAppSize());
                tzBidApps.setApp_name(materialMeta.getApkName());

//        materialMeta.getVideoUrl();// 广告视频物料地址
//        materialMeta.getVideoDuration(); // 广告视频物料时长
//        materialMeta.getMetaIndex();// 当前元数据在一条广告元素组中的索引结构
//        int fallbackType = materialMeta.getFallbackType();// deeplink唤醒广告退化类型，1：浏览器打开页面，2：下载
//        String fallbackUrl = materialMeta.getFallbackUrl().toString();// deeplink唤醒广告退化链接
//        BaiduMobadsApi5.MaterialMeta.ImageSize imageSize = materialMeta.getImageSize();// 图片物料尺寸,默认多个图片物料尺寸相同。目前仅激励视频，同时返回视频、图片物料时，此属性有效。其他情况统一采用material_width、material_height表示
              materialMeta.getBrandName();// 广告品牌名称，下载类为app名，非下载类为推广的品牌名
              tb.setAs(Integer.valueOf(materialMeta.getMaterialSize()).toString()); // 图片、视频物料大小

              ProtocolStringList winNoticeUrlList = materialMeta.getWinNoticeUrlList();
              List<String> checkViews = new ArrayList<>();
              for(int view = 0; view < winNoticeUrlList.size(); view++){
                  checkViews.add(winNoticeUrlList.get(view));//曝光监测URL,支持宏替换第三方曝光监测
              }
              checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
              tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
              String pv = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
              tzMacros = new TzMacros();
              tzMacros.setMacro("%%CHECK_VIEWS%%");
              tzMacros.setValue(Base64.encode(pv));
              tzMacros1.add(tzMacros);


              List<String> clickList = new ArrayList<>();
              clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
              String check_Clicks = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
              tzMacros = new TzMacros();
              tzMacros.setMacro("%%CHECK_CLICKS%%");
              tzMacros.setValue(Base64.encode(check_Clicks));
              tzMacros1.add(tzMacros);
              List<BaiduMobadsApi5.Tracking> adTrackingList = ads.getAdTrackingList();
              for (int t = 0; t < adTrackingList.size(); t++) {
                  if(0 == adTrackingList.get(t).getTrackingEvent().getNumber()){
                          for(int click = 0 ; click <  adTrackingList.get(t).getTrackingUrlList().size(); click++ ){
                              clickList.add(adTrackingList.get(t).getTrackingUrlList().get(click));
                          }
                  }
                  if(102002 == adTrackingList.get(t).getTrackingEvent().getNumber()) {
                      List<String> downLoadList = new ArrayList<>();
                      ProtocolStringList urls1 =  adTrackingList.get(t).getTrackingUrlList();
                      downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                      for (int dl = 0; dl < urls1.size(); dl++) {
                          downLoadList.add(urls1.get(dl));
                      }
                      String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                      tb.setCheck_start_downloads(downLoadList);//开始下载
                      tzMacros = new TzMacros();
                      tzMacros.setMacro("%%DOWN_LOAD%%");
                      tzMacros.setValue(Base64.encode(encode));
                      tzMacros1.add(tzMacros);
                  }
                  if(102005 == adTrackingList.get(t).getTrackingEvent().getNumber()) {
                      List<String> downLoadDList = new ArrayList<>();
                      ProtocolStringList urls1 =  adTrackingList.get(t).getTrackingUrlList();
                      downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                      for (int dle = 0; dle < urls1.size(); dle++) {
                          downLoadDList.add(urls1.get(dle));
                      }
                      String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                      tb.setCheck_end_downloads(downLoadDList);//结束下载
                      tzMacros = new TzMacros();
                      tzMacros.setMacro("%%DOWN_END%%");
                      tzMacros.setValue(Base64.encode(encode));
                      tzMacros1.add(tzMacros);
                  }
                  if(102007 == adTrackingList.get(t).getTrackingEvent().getNumber()) {
                      List<String> installList = new ArrayList<>();
                      ProtocolStringList urls1 =  adTrackingList.get(t).getTrackingUrlList();
                      installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                      for (int ins = 0; ins < urls1.size(); ins++) {
                          installList.add(urls1.get(ins));
                      }
                      String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                      tb.setCheck_start_installs(installList);//开始安装
                      tzMacros = new TzMacros();
                      tzMacros.setMacro("%%INSTALL_START%%");
                      tzMacros.setValue(Base64.encode(encode));
                      tzMacros1.add(tzMacros);
                  }
                  if(102010 == adTrackingList.get(t).getTrackingEvent().getNumber()) {
                      List<String> installEList = new ArrayList<>();
                      ProtocolStringList urls1 =  adTrackingList.get(t).getTrackingUrlList();
                      installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                      for (int ins = 0; ins < urls1.size(); ins++) {
                          installEList.add(urls1.get(ins));
                      }
                      String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                      tb.setCheck_end_installs(installEList);//安装完成
                      tzMacros = new TzMacros();
                      tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                      tzMacros.setValue(Base64.encode(encode));
                      tzMacros1.add(tzMacros);
                  }
                }
                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
              if("7804578".equals(request.getAdv().getTag_id()) && null == tb.getCheck_clicks()){
                    return bidResponse;
              }
              if("7804597".equals(request.getAdv().getTag_id()) && null == tb.getCheck_clicks()){
                    return bidResponse;
              }
                tb.setSource(source);
                tb.setApp(tzBidApps);
                tb.setMacros(tzMacros1);
                bidList.add(tb);//素材集合
                TzSeat seat = new TzSeat();
                seat.setBid(bidList);
                seatList.add(seat);

                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);
                bidResponse.setId(request.getId());
                bidResponse.setBidid(mobadsResponse.getSearchKey());

            }
            log.info(request.getImp().get(0).getTagid()+"-百度总返回数据:"+JSONObject.toJSONString(bidResponse));
        }

        return bidResponse;
    }
}
