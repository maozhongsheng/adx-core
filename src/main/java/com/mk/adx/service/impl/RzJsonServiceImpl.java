package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.renze.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.RzJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author mzs
 * @Description 北京仁泽
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("rzJsonService")
public class RzJsonServiceImpl implements RzJsonService {

    public static String id = "";

    private static final String name = "renze";

    private static final String source = "仁泽";


    public static String ip = "";

    @SneakyThrows
    @Override
    public TzBidResponse getRzDataByJson(TzBidRequest request) {
        RzBidRequest bidRequest = new RzBidRequest();
        bidRequest.setVersion(request.getMedia_version());//Api 版本号 0.0.1
        RzApp app = new RzApp();
//        app.setAppId(request.getApp().getId());//APPid
//        app.setName(request.getApp().getName());//App名称
//        app.setPACKAGE(request.getApp().getBundle());//包名
//        app.setVersion(request.getApp().getVer());//版本
        app.setAppId(request.getAdv().getApp_id());//APPid
        app.setName(request.getAdv().getApp_name());//App名称
        app.setPACKAGE(request.getAdv().getBundle());//包名
        app.setVersion(request.getAdv().getVersion());//版本
        bidRequest.setApp(app);
        RzDevice device = new RzDevice();
        String os = request.getDevice().getOs();//系统类型
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {
            device.setOsType(1);//1:Android
            device.setAdid(request.getDevice().getAndroid_id());//androidId android 必填，不填影响填充
            device.setImei(request.getDevice().getImei());//android Q 之前设备标识必填，Android 必传
            device.setOaid(request.getDevice().getOaid());//Android Q 之后广告标识符，Android 必传
        } else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOsType(2);//2: IOS
            device.setUdid(request.getDevice().getOpen_udid());//iOS 设备的 OpenUDID,小于 ios 6 必传
        } else {
            device.setOsType(0);//0: Unknow
        }

        if ("pc".equals(request.getDevice().getDevicetype()) || "tv".equals(request.getDevice().getDevicetype())) {//必填，发起当前请求的设备类型信息
            device.setType(4);//未知
        } else if ("phone".equals(request.getDevice().getDevicetype())) {
            device.setType(1);//⼿机设备
        } else if ("ipad".equals(request.getDevice().getDevicetype())) {
            device.setType(2);//平板设备
        }

        device.setOsVersion(request.getDevice().getOsv());//操作系统版本号
        device.setLanguage(request.getDevice().getLanguage());//系统语言
        device.setModel(request.getDevice().getModel());//设备型号，系统原始值，不要做修改。
        device.setBrand(request.getDevice().getMake());//设备品牌
        device.setVendor("mi");//设备厂商。1)android 设备： 可调用系统接口android.os.Build.MANUFACTURER 直接获得。如果获取不到，填写 unknown( 小写）。2)ios 设备：无需填写
        device.setWidth(request.getDevice().getW());//设备屏幕宽度
        device.setHeight(request.getDevice().getH());//设备屏幕高度
        device.setDensity((float) request.getDevice().getDeny());//每英寸像素,获取方法：安卓：context.getResources().getDisplayMetrics().densityiOS：UIScreen.scale
        device.setIdfa(request.getDevice().getIdfa());//IDFA，Ios 6 之后必填
        //device.setOrientation(request.getDevice().getOrientation());//屏幕方向 0：unknown 1：竖屏 2：横屏
        device.setScreenDpi(160);//设备屏幕像素密度，如：160
        device.setOsl(23);//Android 操作系统 API Level，如 23、22，上游广告为vivo 时必
        bidRequest.setDevice(device);

        RzNetwork network = new RzNetwork();
        network.setIp(request.getDevice().getIp());//ip
        network.setMac(request.getDevice().getMac());//mac 地址
        if (0 == request.getDevice().getConnectiontype()) {//网络类型
            network.setNet(0);//未知
        } else if (2 == request.getDevice().getConnectiontype()) {
            network.setNet(1);//wifi
        } else if (4 == request.getDevice().getConnectiontype()) {
            network.setNet(2);//移动⽹络2G Cellular Network – 2G
        } else if (5 == request.getDevice().getConnectiontype()) {
            network.setNet(3);//移动⽹络3G Cellular Network – 3G
        } else if (6 == request.getDevice().getConnectiontype()) {
            network.setNet(4);//移动⽹络4G Cellular Network – 4G
        } else if (7 == request.getDevice().getConnectiontype()) {
            network.setNet(5);//移动⽹络5G Cellular Network – 5G
        }

        if ("0".equals(request.getDevice().getCarrier())) {//必填，提供完整的Carrier对象来描述当前运营商信息
            network.setCarrier(0);//未知
        } else if ("70120".equals(request.getDevice().getCarrier())) {
            network.setCarrier(1);//中国移动
        } else if ("70123".equals(request.getDevice().getCarrier())) {
            network.setCarrier(3);//中国联通
        } else if ("70121".equals(request.getDevice().getCarrier())) {
            network.setCarrier(2);//中国电信
        }
        network.setUa(request.getDevice().getUa());//客户端 UA
        bidRequest.setNetwork(network);

        RzGeo geo = new RzGeo();
        geo.setLat(request.getDevice().getGeo().getLat());//纬度，gcj 坐标系，不传可能影响广告填充
        geo.setLng(request.getDevice().getGeo().getLon());//经度，gcj 坐标系，不传可能影响广告填充
        bidRequest.setGeo(geo);

        RzImp imp = new RzImp();
        if (null != request.getImp()) {
            for (int i = 0; i < request.getImp().size(); i++) {
                imp.setPosId(request.getAdv().getTag_id());//必填，当前Imp所对应的⼴告位ID（嗨量提供）
                imp.setWidth(request.getImp().get(i).getNATIVE().getW());
                imp.setHeight(request.getImp().get(i).getNATIVE().getH());
            }
        }
        bidRequest.setImp(imp);

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求仁泽参数" + JSONObject.parseObject(content));
        String url = "http://union.ad.yunjuhe.cn/Public/ad";
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
        log.info("请求上游仁泽花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("仁泽返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            if (jo.getInteger("error") == 0) {
                if(!StringUtils.isEmpty(jo.getJSONObject("data").getJSONObject("adInfo"))) {
                    String id = request.getId();
                    JSONObject data = jo.getJSONObject("data");
                    TzBid tb = new TzBid();
                    List<TzBid> bidList = new ArrayList<>();
                    tb.setId(data.getString("rid"));//本次请求的 requestid
                    tb.setAdid(data.getString("postId"));//广告id
                    JSONObject adInfo = data.getJSONObject("adInfo");
                    tb.setTitle(adInfo.getString("name"));//广告标题
                    tb.setDesc(adInfo.getString("desc"));//广告自身广告语
                    JSONObject appInfo = adInfo.getJSONObject("appInfo");
                    if (null != appInfo) {
                        TzBidApps apps = new TzBidApps();
                        apps.setApp_name(appInfo.getString("name"));//应用名称
                        apps.setBundle(appInfo.getString("package"));//广告应用的包名
                        apps.setApp_icon(appInfo.getString("iconUrl"));//下载类型：应用图标
                        apps.setApp_size(appInfo.getInteger("size"));//下载类型：应用包大小
                        tb.setApp(apps);
                    }
                    JSONObject material = adInfo.getJSONObject("material");
                    int type = material.getInteger("type");//广告素材总体类型: 1 视频 2 单图 3 多图 4 html 文本 5 音频
                    JSONArray features = material.getJSONArray("features");
                    for (int j = 0; j < features.size(); j++) {
                        if (type == 1 || type == 3 || type == 5) {
                            tb.setAd_type(8);//信息流-广告素材类型
                            /**
                             * 信息流图片
                             */
                            TzNative tzNative = new TzNative();
                            List<TzImage> tzImages = new ArrayList<>();//天卓图片素材
                            if (null != features.getJSONObject(j).getString("materialUrl")) {
                                String rzImages = features.getJSONObject(j).getString("materialUrl");//一点资讯图片素材
                                TzImage image = new TzImage();
                                image.setUrl(rzImages);
                                if (null != request.getImp()) {
                                    for (int i = 0; i < request.getImp().size(); i++) {
                                        Integer w = request.getImp().get(i).getNATIVE().getW();
                                        Integer h = request.getImp().get(i).getNATIVE().getH();
                                        image.setW(w);
                                        image.setH(h);
                                    }
                                }
                                image.setType(features.getJSONObject(j).getInteger("type"));
                                tzImages.add(image);
                            }
                            tzNative.setImages(tzImages);
                            tb.setNATIVE(tzNative);
                        } else if (type == 2) {
                            tb.setAd_type(5);//开屏-广告素材类型
                            /**
                             * 开屏流图片
                             */
                            List<TzImage> tzImages = new ArrayList<>();//天卓图片素材
                            if (null != features.getJSONObject(j).getString("materialUrl")) {
                                String ydzxImages = features.getJSONObject(j).getString("materialUrl");//一点资讯图片素材
                                TzImage image = new TzImage();
                                image.setUrl(ydzxImages);
                                if (null != request.getImp()) {
                                    for (int i = 0; i < request.getImp().size(); i++) {
                                        Integer w = request.getImp().get(i).getNATIVE().getW();
                                        Integer h = request.getImp().get(i).getNATIVE().getH();
                                        image.setW(w);
                                        image.setH(h);
                                    }
                                }
                                image.setType(features.getJSONObject(j).getInteger("type"));
                                tzImages.add(image);
                            }
                            tb.setImages(tzImages);
                        }
                    }

                    if (adInfo.getInteger("opType") == 1) {
                        tb.setClicktype("4");//点击类型：app下载
                    } else if (adInfo.getInteger("opType") == 3) {
                        tb.setClicktype("2");//点击类型：Deeplink
                    } else if (adInfo.getInteger("opType") == 5) {
                        tb.setClicktype("3");//点击类型：广点通下载广告
                    }

                    List<String> clickList = new ArrayList<>();
                    JSONArray clickUrl = adInfo.getJSONArray("clickUrls");
                    if (null != clickUrl && 0 < clickUrl.size()) {
                        for (int click = 0; click < clickUrl.size(); click++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            clickList.add(clickUrl.get(click).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CLICK_CLICKS%%");
                        }
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        String check_Clicks = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CLICK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(check_Clicks));
                        tzMacros1.add(tzMacros);
                    }

                    List<String> checkViews = new ArrayList<>();
                    JSONArray monitorUrl = adInfo.getJSONArray("showUrls");
                    if (null != monitorUrl && 0 < monitorUrl.size()) {
                        for (int view = 0; view < monitorUrl.size(); view++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            checkViews.add(monitorUrl.get(view).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()).replace("__SHOW_TIME__", "%%TS%%"));
                        }
                        checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=" + "%%CLICK_VIEWS%%");
                        tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                        String pv = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CLICK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(pv));
                        tzMacros1.add(tzMacros);
                    }

                    JSONObject convUrls = adInfo.getJSONObject("convUrls");

                    List<String> checkStartDownloads = new ArrayList<>();
                    JSONArray dmurl = convUrls.getJSONArray("dlBegin");//app 下载开始的监测地址
                    if (null != dmurl && 0 < dmurl.size()) {
                        for (int cm = 0; cm < dmurl.size(); cm++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            checkStartDownloads.add(dmurl.get(cm).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
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
                    JSONArray downsuccessurl = convUrls.getJSONArray("dlEnd");//app 下载完成的监测地址
                    if (null != downsuccessurl && 0 < downsuccessurl.size()) {
                        for (int cm = 0; cm < downsuccessurl.size(); cm++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            checkEndDownloads.add(downsuccessurl.get(cm).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
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
                    JSONArray check_start_installss = convUrls.getJSONArray("isBegin");
                    if (null != check_start_installss && 0 < check_start_installss.size()) {
                        for (int cm = 0; cm < check_start_installss.size(); cm++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            check_start_installs.add(check_start_installss.get(cm).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
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
                    JSONArray check_end_installss = convUrls.getJSONArray("isEnd");
                    if (null != check_end_installss && 0 < check_end_installss.size()) {
                        for (int cm = 0; cm < check_end_installss.size(); cm++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            check_end_installs.add(check_end_installss.get(cm).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                        }
                        String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                        tzMacros.setValue(Base64.encode(iurl));
                        tzMacros1.add(tzMacros);
                        check_end_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/end?installEnd=" + Base64.encode(iurl));
                        tb.setCheck_end_installs(check_end_installs);//安装完成监测URL第三方曝光监测
                    }

                    if (null != convUrls.getJSONArray("pyBegin")) {
                        List<String> voidStartList = new ArrayList<>();
                        JSONArray urls1 = convUrls.getJSONArray("pyBegin");
                        voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                        for (int vs = 0; vs < urls1.size(); vs++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            voidStartList.add(urls1.get(vs).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        //    tb.setCheck_views(voidStartList);//视频开始播放
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%VEDIO_START%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }


                    if (null != convUrls.getJSONArray("pyEnd")) {
                        List<String> voidEndList = new ArrayList<>();
                        JSONArray urls1 = convUrls.getJSONArray("pyEnd");
                        voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                        for (int ve = 0; ve < urls1.size(); ve++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            voidEndList.add(urls1.get(ve).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        //    tb.setCheck_clicks(voidEndList);//视频播放结束
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%VEDIO_END%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    List<String> checkSuccessDeeplinks = new ArrayList<>();
                    JSONArray deeplinkmurl = convUrls.getJSONArray("dplink");//仅用于唤醒广告，deeplink 链接调起成功
                    if (null != deeplinkmurl && 0 < deeplinkmurl.size()) {
                        for (int cm = 0; cm < deeplinkmurl.size(); cm++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            checkSuccessDeeplinks.add(deeplinkmurl.get(cm).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                        }
                        String deeplinkmurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(deeplinkmurls));
                        tzMacros1.add(tzMacros);
                        checkSuccessDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                    }

                    if (null != convUrls.getJSONArray("dplinkTry")) {
                        List<String> deep_linkF = new ArrayList<>();
                        JSONArray urls2 = convUrls.getJSONArray("dplinkTry");
                        for (int dpf = 0; dpf < urls2.size(); dpf++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            deep_linkF.add(urls2.get(dpf).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                        }
                        String encode2 = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK_F%%");
                        tzMacros.setValue(Base64.encode(encode2));
                        tzMacros1.add(tzMacros);
                        deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_F%%");
                        tb.setCheck_success_deeplinks(deep_linkF);//唤醒失败
                    }

                    List<String> check_activations = new ArrayList<>();
                    JSONArray check_activationss = convUrls.getJSONArray("active");
                    if (null != check_activationss && 0 < check_activationss.size()) {
                        for (int cm = 0; cm < check_activationss.size(); cm++) {
                            Long dateTime = System.currentTimeMillis() / 1000;
                            Long dateMidTime = System.currentTimeMillis();
                            check_activations.add(check_activationss.get(cm).toString().replace("__TS__", dateTime.toString()).replace("__TS_MS__", dateMidTime.toString()));
                        }
                        String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%ACTIVATION_TIME%%");
                        tzMacros.setValue(Base64.encode(iurl));
                        tzMacros1.add(tzMacros);
                        check_activations.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/activation?activation=" + Base64.encode(iurl));
                        tb.setCheck_activations(check_activations);//激活
                    }

                    tb.setMacros(tzMacros1);
                    bidList.add(tb);//
                    tzSeat.setBid(bidList);
                    seatList.add(tzSeat);


                    bidResponse.setId(id);//请求id
                    bidResponse.setSeatbid(seatList);//广告集合对象
                    bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info("仁泽总返回" + JSONObject.toJSONString(bidResponse));
                }
            }
        }
            return bidResponse;
        }
    }
