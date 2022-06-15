package com.mk.adx.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.chuanguang.ChuanGuangBidRequest;
import com.mk.adx.entity.json.request.chuanguang.ChuanGuangDevice;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.ChuanGuangService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author gj
 * @Description 传广
 * @Date 2021/10/26 10:30
 */
@Slf4j
@Service("chuanGuangService")
public class ChuanGuangServiceImpl implements ChuanGuangService {

    @Resource
    private RedisUtil redisUtil;

    public static  String id = "";

    private static final String name = "chuanguang";

    private static final String source = "传广";


    public static  String ip = "";

    @SneakyThrows
    @Override
    public TzBidResponse chuanGuangDataByJson(TzBidRequest request) {
        ChuanGuangBidRequest bidRequest = new ChuanGuangBidRequest();
        bidRequest.setTestMode(request.getTest());//是否测试环境，0：正式，1：测试，默认为0
        bidRequest.setAdCount(1);//请求的广告数量,返回小于等于n条的广告
        bidRequest.setApiVersion(request.getMedia_version());//协议版本号
        bidRequest.setSspId(request.getAdv().getApp_id());//媒体Id
        bidRequest.setAdId(request.getAdv().getTag_id());//广告位 id
        if (null != request.getImp()) {
            for (int i = 0; i < request.getImp().size(); i++) {
                if ("1".equals(request.getImp().get(i).getAd_slot_type())) {
                    bidRequest.setAdType(4);//广告类型，信息流=4
                    bidRequest.setAdWith(request.getImp().get(i).getNATIVE().getW());//宽
                    bidRequest.setAdHeight(request.getImp().get(i).getNATIVE().getH());//高
                } else if ("2".equals(request.getImp().get(i).getAd_slot_type())) {
                    bidRequest.setAdType(3);//广告类型，banner =3
                    bidRequest.setAdWith(request.getImp().get(i).getBanner().getW());//素材宽，单位px
                    bidRequest.setAdHeight(request.getImp().get(i).getBanner().getH());//素材高，单位px
                } else if ("3".equals(request.getImp().get(i).getAd_slot_type())) {
                    bidRequest.setAdType(1);//广告类型，开屏广告=1
                    bidRequest.setAdWith(request.getImp().get(i).getBanner().getW());//宽
                    bidRequest.setAdHeight(request.getImp().get(i).getBanner().getH());//高
                } else if ("4".equals(request.getImp().get(i).getAd_slot_type())) {
                    bidRequest.setAdType(5);//广告类型，视频=5
                    bidRequest.setAdWith(request.getImp().get(i).getVideo().getW());//视频播放器的宽度，单位px
                    bidRequest.setAdHeight(request.getImp().get(i).getVideo().getH());//视频播放器的宽度，单位px
                } else if ("6".equals(request.getImp().get(i).getAd_slot_type())) {
                    bidRequest.setAdType(2);//广告类型，插屏=2
                    bidRequest.setAdWith(request.getImp().get(i).getBanner().getW());//宽
                    bidRequest.setAdHeight(request.getImp().get(i).getBanner().getH());//高
                }
            }
        }

        ChuanGuangDevice device = new ChuanGuangDevice();
        device.setMake(request.getDevice().getMake());//制造厂商，默认为空
        device.setModel(request.getDevice().getModel());//型号，如"iphonea1530”，默认为空String
        if ("Android".equals(request.getDevice().getOs())) {
            device.setOs(1);//Android
            device.setAndroidId(request.getDevice().getAndroid_id());
        } else if ("iOS".equals(request.getDevice().getOs())) {
            device.setOs(2);//IOS
            device.setIdfa(request.getDevice().getIdfa());
        }

        device.setOsVer(request.getDevice().getOsv());//操作系统版本号
        if ("phone".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(0);//手机
        } else if ("ipad".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(1);//平板
        }

        if ("0".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(0);//运营商:UNKNOWN_OPERATOR=0
        } else if ("70120".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(1);//运营商:CHINA_MOBILE=1
            device.setOperatorNop("70120");
        } else if ("70123".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(2);//运营商:CHINA_TELECOM=2
            device.setOperatorNop("70123");
        } else if ("70121".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(3);//运营商:CHINA_UNICOM=3
            device.setOperatorNop("70121");
        }
        device.setScreenWidth(request.getDevice().getW());//物理屏幕宽度的像素
        device.setScreenHeight(request.getDevice().getH());//物理屏幕高度的像素
        device.setDeny(request.getDevice().getDeny());//设备屏幕密度
        device.setDpi(480);//手机dpi值
        device.setImsi("460020084725173");//设备 imsi 值
        device.setImei(request.getDevice().getImei());//手机真实物理序列号

        if (0 == request.getDevice().getConnectiontype()) {
            device.setNetworkType(4);//设备的网络类型:Unknown=0
        } else if (2 == request.getDevice().getConnectiontype()) {
            device.setNetworkType(3);//设备的网络类型:UWifi=1
        } else if (4 == request.getDevice().getConnectiontype()) {
            device.setNetworkType(1);//设备的网络类型:2G=2
        } else if (5 == request.getDevice().getConnectiontype()) {
            device.setNetworkType(2);//设备的网络类型:3G=3
        } else if (6 == request.getDevice().getConnectiontype()) {
            device.setNetworkType(5);//设备的网络类型:4G=4
        } else if (7 == request.getDevice().getConnectiontype()) {
            device.setNetworkType(6);//设备的网络类型:5G=5
        }
        device.setMac(request.getDevice().getMac());//MAC地址，明文传输,默认为空字符串
        device.setAppName(request.getAdv().getApp_name());//交互APP名称
        device.setApkVersion(request.getAdv().getVersion());//apk版本
        device.setPackageName(request.getAdv().getBundle());//媒体的包名，即本app的包名
        device.setIp(request.getDevice().getIp());//终端设备外网的IP地址
        device.setUserAgent(request.getDevice().getUa());//终端设备实际 webview User-Agent 值

        bidRequest.setDevice(device);


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求传广参数" + JSONObject.parseObject(content));
        String url = "http://150.158.237.171/api/ad";
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
        log.info("请求上游传广花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("传广返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            id = request.getId();
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            if (jo.getJSONObject("data").getInteger("code") == 200) {
                JSONArray data = jo.getJSONObject("data").getJSONArray("adList");
                TzBid tb = new TzBid();
                List<TzBid> bidList = new ArrayList<>();
                List<TzImage> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    tb.setAdid(data.getJSONObject(i).getString("adKey"));//广告id
                    tb.setAd_type(data.getJSONObject(i).getInteger("adtp"));//素材类型
                    if(request.getImp().get(0).getAd_slot_type().equals("1")){
                        tb.setAd_type(8);//信息流-广告素材类型
                        JSONArray jsonArray = data.getJSONObject(i).getJSONArray("imgs");
                        TzNative tzNative = new TzNative();
                        for (int j = 0; j < jsonArray.size(); j++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(jsonArray.get(j).toString());
                            tzImage.setW(data.getJSONObject(i).getInteger("adWidth"));
                            tzImage.setH(data.getJSONObject(i).getInteger("adHeight"));
                            list.add(tzImage);
                        }
                        tzNative.setImages(list);
                        tb.setNATIVE(tzNative);
                        JSONArray imgs = data.getJSONObject(i).getJSONArray("imgs");
                        for (int g = 0; g < imgs.size(); g++) {
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(imgs.get(g).toString());
                            list.add(tzImage);
                        }
                    }else {
                        tb.setAd_type(5);//开屏-广告素材类型
                        JSONArray jsonArray = data.getJSONObject(i).getJSONArray("imgs");
                        TzImage image = new TzImage();
                        for (int j = 0; j < jsonArray.size(); j++) {
                            image.setUrl(jsonArray.get(j).toString());
                            image.setW(data.getJSONObject(i).getInteger("adWidth"));
                            image.setH(data.getJSONObject(i).getInteger("adHeight"));
                            list.add(image);
                        }
                        tb.setImages(list);
                    }


                    tb.setAdm(data.getJSONObject(i).getString("adll"));//广告图片
                    tb.setTitle(data.getJSONObject(i).getString("adTitle"));//广告文字标题
                    tb.setDesc(data.getJSONObject(i).getString("adContent"));//广告文字内容
                    TzBidApps apps = new TzBidApps();
                    apps.setApp_name(data.getJSONObject(i).getString("appname"));//应用名称
                    apps.setBundle(data.getJSONObject(i).getString("packname"));//应用包名
                    tb.setApp(apps);
                    if (data.getJSONObject(i).getString("deeplinkType").equals("1")) {
                        tb.setClicktype(data.getJSONObject(i).getString("deeplinkType"));//落地页类型
                    } else if (data.getJSONObject(i).getString("bornt").equals("0")) {
                        tb.setClicktype(data.getJSONObject(i).getString("bornt"));//落地页类型
                    } else if(data.getJSONObject(i).getString("bornt").equals("3")){
                        List<String> clickList = new ArrayList<>();
                        JSONArray clickUrl = data.getJSONObject(i).getJSONArray("clickTrace");
                        if (null != clickUrl && 0 < clickUrl.size()) {
                            for (int click = 0; click < clickUrl.size(); click++) {
                                String reqId = data.getJSONObject(i).getString("reqId");
                                clickList.add(clickUrl.get(click).toString().replace("DOWN_CLICK_ID",reqId));
                                clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CLICK_CLICKS%%");
                            }
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            String check_Clicks = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CLICK_CLICKS%%");
                            tzMacros.setValue(Base64.encode(check_Clicks));
                            tzMacros1.add(tzMacros);
                        }
                    }else if(data.getJSONObject(i).getString("bornt").equals("1") || data.getJSONObject(i).getString("bornt").equals("2")){
                        List<String> checkViews = new ArrayList<>();
                        JSONArray monitorUrl = data.getJSONObject(i).getJSONArray("showTrace");
                        if (null != monitorUrl && 0 < monitorUrl.size()) {
                            for (int view = 0; view < monitorUrl.size(); view++) {
                                checkViews.add(monitorUrl.get(view).toString());//曝光监测URL,支持宏替换第三方曝光监测
                            }
                            checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=" + "%%CLICK_VIEWS%%");
                            tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                            String pv = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CLICK_VIEWS%%");
                            tzMacros.setValue(Base64.encode(pv));
                            tzMacros1.add(tzMacros);
                        }


                        List<String> clickList = new ArrayList<>();
                        JSONArray clickUrl = data.getJSONObject(i).getJSONArray("clickTrace");
                        if (null != clickUrl && 0 < clickUrl.size()) {
                            for (int click = 0; click < clickUrl.size(); click++) {
                                clickList.add(clickUrl.get(click).toString());
                            }
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CLICK_CLICKS%%");
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            String check_Clicks = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CLICK_CLICKS%%");
                            tzMacros.setValue(Base64.encode(check_Clicks));
                            tzMacros1.add(tzMacros);
                        }

                        List<String> checkSuccessDeeplinks = new ArrayList<>();
                        JSONArray deeplinkmurl = data.getJSONObject(i).getJSONArray("dpurlsTrace");//仅用于唤醒广告，deeplink 链接调起成功
                        if (null != deeplinkmurl && 0 < deeplinkmurl.size()) {
                            for (int cm = 0; cm < deeplinkmurl.size(); cm++) {
                                checkSuccessDeeplinks.add(deeplinkmurl.get(cm).toString());
                            }
                            String deeplinkmurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DEEP_LINK%%");
                            tzMacros.setValue(Base64.encode(deeplinkmurls));
                            tzMacros1.add(tzMacros);
                            checkSuccessDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                            tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                        }

                        List<String> checkFailDeeplinks = new ArrayList<>();
                        JSONArray deepfailmurl = data.getJSONObject(i).getJSONArray("dpurlsFailTrace");//仅用于唤醒广告，deeplink 链接调起失败
                        if (null != deepfailmurl && 0 < deepfailmurl.size()) {
                            for (int cm = 0; cm < deepfailmurl.size(); cm++) {
                                checkFailDeeplinks.add(deepfailmurl.get(cm).toString());
                            }
                            String deepfailmurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_LOAD%%");
                            tzMacros.setValue(Base64.encode(deepfailmurls));
                            tzMacros1.add(tzMacros);
                            checkFailDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DOWN_LOAD%%");
                            tb.setCheck_fail_deeplinks(checkFailDeeplinks);
                        }


                        List<String> checkStartDownloads = new ArrayList<>();
                        JSONArray dmurl = data.getJSONObject(i).getJSONArray("downStTrace");//app 下载开始的监测地址
                        if (null != dmurl && 0 < dmurl.size()) {
                            for (int cm = 0; cm < dmurl.size(); cm++) {
                                checkStartDownloads.add(dmurl.get(cm).toString());
                            }
                            checkStartDownloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_START%%");
                            tb.setCheck_start_downloads(checkStartDownloads);//曝光监测URL,支持宏替换第三方曝光监测
                            String dmurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_START%%");
                            tzMacros.setValue(Base64.encode(dmurls));
                            tzMacros1.add(tzMacros);
                        }

                        List<String> checkEndDownloads = new ArrayList<>();
                        JSONArray downsuccessurl = data.getJSONObject(i).getJSONArray("downCpTrace");//app 下载完成的监测地址
                        if (null != downsuccessurl && 0 < downsuccessurl.size()) {
                            for (int cm = 0; cm < downsuccessurl.size(); cm++) {
                                checkEndDownloads.add(downsuccessurl.get(cm).toString());
                            }
                            String downsuccessurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_SUCCESS%%");
                            tzMacros.setValue(Base64.encode(downsuccessurls));
                            tzMacros1.add(tzMacros);
                            checkEndDownloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/end?downloadEnd=%%DOWN_SUCCESS%%");
                            tb.setCheck_end_downloads(checkEndDownloads);
                        }

                        List<String> check_start_installs = new ArrayList<>();
                        JSONArray check_start_installss = data.getJSONObject(i).getJSONArray("urls");
                        if (null != check_start_installss && 0 < check_start_installss.size()) {
                            for (int cm = 0; cm < check_start_installss.size(); cm++) {
                                check_start_installs.add(check_start_installss.get(cm).toString());
                            }
                            String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%INSTALL_START%%");
                            tzMacros.setValue(Base64.encode(iurl));
                            tzMacros1.add(tzMacros);
                            check_start_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/start?installStart=" + Base64.encode(iurl));
                            tb.setCheck_start_installs(check_start_installs);//开始安装监测URL第三方曝光监测
                        }

                        List<String> check_end_installs = new ArrayList<>();
                        JSONArray check_end_installss = data.getJSONObject(i).getJSONArray("urls");
                        if (null != check_end_installss && 0 < check_end_installss.size()) {
                            for (int cm = 0; cm < check_end_installss.size(); cm++) {
                                check_end_installs.add(check_end_installss.get(cm).toString());
                            }
                            String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                            tzMacros.setValue(Base64.encode(iurl));
                            tzMacros1.add(tzMacros);
                            check_end_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/end?installEnd=" + Base64.encode(iurl));
                            tb.setCheck_end_installs(check_end_installs);//安装完成监测URL第三方曝光监测
                        }

                        List<String> check_activations = new ArrayList<>();
                        JSONArray check_activationss = data.getJSONObject(i).getJSONArray("sttrace");
                        if (null != check_activationss && 0 < check_activationss.size()) {
                            for (int cm = 0; cm < check_activationss.size(); cm++) {
                                check_activations.add(check_activationss.get(cm).toString());
                            }
                            String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%ACTIVATION_TIME%%");
                            tzMacros.setValue(Base64.encode(iurl));
                            tzMacros1.add(tzMacros);
                            check_activations.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/activation?activation=" + Base64.encode(iurl));
                            tb.setCheck_activations(check_activations);//安装完成监测URL第三方曝光监测
                        }
                    }
                    tb.setClick_url(data.getJSONObject(i).getString("clickUrl"));//点击跳转的url
                    tb.setDeeplink_url(data.getJSONObject(i).getString("deeplink"));//deeplink链接



                    tb.setMacros(tzMacros1);
                    bidList.add(tb);//
                    tzSeat.setBid(bidList);
                    seatList.add(tzSeat);
                }


                bidResponse.setId(id);//请求id
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("传广总返回" + JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }

}
