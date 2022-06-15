package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.tz.TzGeo;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.exception.BusinessException;
import com.mk.adx.exception.code.BaseResponseCode;
import com.mk.adx.util.AESUtil;
import com.mk.adx.util.Base64;
import com.mk.adx.service.YdJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author yjn
 * @Description 中视力美-网易有道智选
 * @Date 2021/6/21 9:48
 */
@Slf4j
@Service("ydJsonService")
public class YdJsonServiceImpl implements YdJsonService {

    private static final  String name = "yd";
    private static final  String source = "有道";

    @SneakyThrows
    @Override
    public TzBidResponse getYdDataByJson(TzBidRequest request) {
        String ad_slot_type = "";
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            /*
             * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
             */
            String url = "http://gorgon.youdao.com/gorgon/request.s";//有道请求路径
            URIBuilder uriBuilder = new URIBuilder(url);
            /**
             * 有道真实请求参数
             */
            if (null!=request.getImp()){
                for (int r=0;r<request.getImp().size();r++){
                    ad_slot_type =  request.getImp().get(r).getAd_slot_type();
                }
                uriBuilder.addParameter("id", request.getAdv().getTag_id());//移动广告位ID

            }

            if(StringUtils.isNotEmpty(request.getMedia_version())){
                uriBuilder.addParameter("av", "v1.2.3");//版本号
            }else{
                uriBuilder.addParameter("av", "v"+request.getAdv().getVersion());//版本号

            }
            if (null != request.getDevice()) {//device
                /**
                 * 网络类型
                 */
                if (0 == request.getDevice().getConnectiontype()) {//Unknown
                    uriBuilder.addParameter("ct", "0");//网络连接类型
                    uriBuilder.addParameter("dct", "-1");//子网络连接类型
                } else if (1 == request.getDevice().getConnectiontype()) {//Ethernet
                    uriBuilder.addParameter("ct", "1");
                    uriBuilder.addParameter("dct", "-1");//
                } else if (2 == request.getDevice().getConnectiontype()) {//WiFi
                    uriBuilder.addParameter("ct", "2");
                    uriBuilder.addParameter("dct", "-1");//
                } else if (3 == request.getDevice().getConnectiontype()) {//Cellular Network -Unknown
                    uriBuilder.addParameter("ct", "2");
                    uriBuilder.addParameter("dct", "-1");//
                } else if (4 == request.getDevice().getConnectiontype()) {//2G
                    uriBuilder.addParameter("ct", "3");
                    uriBuilder.addParameter("dct", "11");//
                } else if (5 == request.getDevice().getConnectiontype()) {//3G
                    uriBuilder.addParameter("ct", "3");
                    uriBuilder.addParameter("dct", "12");//
                } else if (6 == request.getDevice().getConnectiontype()) {//4G
                    uriBuilder.addParameter("ct", "3");
                    uriBuilder.addParameter("dct", "13");//
                } else if (7 == request.getDevice().getConnectiontype()) {//5G
                    uriBuilder.addParameter("ct", "3");
                    uriBuilder.addParameter("dct", "50");//
                }
                uriBuilder.addParameter("udid", request.getDevice().getAndroid_id());//设备ID，如AndroidID 或IDFA，要求明文大写
                uriBuilder.addParameter("auidmd5", request.getDevice().getAndroid_id_md5());//MD5 后的AndroidID
//                if(request.getDevice().getImei().equals("1000000000122333333")){
//                    uriBuilder.addParameter("imei", "866712033266183");
//                }else{
//                    //IMEI（International Mobile Equipment Identity）是移动设备国际身份码的缩写
//                    uriBuilder.addParameter("imei", request.getDevice().getImei());
//                }
                uriBuilder.addParameter("imei", "866712033266183");
                uriBuilder.addParameter("imeimd5", request.getDevice().getImei_md5());//MD5 后的IMEI，要求明文MD5 后，再大写
                uriBuilder.addParameter("oaid", request.getDevice().getOaid());//开放匿名ID
                uriBuilder.addParameter("rip", request.getDevice().getIp());//用户原始IP 地址

                TzGeo geo = new TzGeo();//地理位置
                if (null != request.getDevice().getGeo()) {
                    String latString = String.valueOf(request.getDevice().getGeo().getLat());
                    String lonString = String.valueOf(request.getDevice().getGeo().getLon());
                    String ll = latString + "," + lonString;//经度和纬度拼接

                    uriBuilder.addParameter("ll", ll);//位置信息，GPS 或者网络位置，经纬度逗号分隔（经度在前，纬度在后），WGS-84 坐标系
                    uriBuilder.addParameter("lla", ll);//经纬度精确度，单位：米。若当前获取的ll（位置信息）为小数点后3 位/4 位小数，则填为100 米/10 米，以此类推
                    uriBuilder.addParameter("wifi", request.getDevice().getGeo().getWifi());//wifi 信息，用户将当前连接或者附近的wifi 的ssid 和mac传送过来
                    uriBuilder.addParameter("llp", request.getDevice().getGeo().getLlp());//定位所用的provider，n(network) 为网络定位，g(gps) 为gps 定位,p(passive) 为其他app 里面的定位信息，f(fused)为系统返回的最佳定位
                }
            }

            // 根据带参数的URI对象构建GET请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            //请求超时时间设置
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(500).setConnectionRequestTimeout(100).setSocketTimeout(500).build();
            httpGet.setConfig(requestConfig);

            /*
             * 添加请求头信息
             */
            // 浏览器表示
            httpGet.addHeader("User-Agent", request.getDevice().getUa());
            // 传输的类型
            httpGet.addHeader("Content-Type", "application/json;charset=utf-8");
            httpGet.addHeader("Accept", "*/*");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.addHeader("Connection", "keep-alive");
            log.info(httpGet.toString());
            httpGet.addHeader("Accept","*/*");
            httpGet.addHeader("Accept-Encoding","gzip, deflate, br");
            httpGet.addHeader("Connection","keep-alive");
            log.info("请求网易有道数据"+httpGet.toString());

            // 执行请求
            Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
            response = httpClient.execute(httpGet);
            Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
            // 计算并打印耗时
            Long tempTime = (endTime - startTime);
            bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
           log.info("花费时间：" +
                    (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                    ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                    ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                    ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                    ((tempTime % 1000) + "ms"));

            Header[] allHeaders = response.getAllHeaders();
            String adtype = "";//广告返回类型
            String clickthrough = "";//点击地址
            String imptracker = "";//广告跟踪
            String launchpage = "";//landpage
            Long creativeid = null;//推广创意 ID，长整型

            for (int m = 0; m < allHeaders.length; m++) {
//                System.out.println(allHeaders[m]);
                if ("X-Adstate".equals(allHeaders[m].getName())) {
//                    System.out.println(allHeaders[m].getValue());
                    HashMap hashMap = JSON.parseObject(allHeaders[m].getValue(), HashMap.class);
                    String code = hashMap.get("code").toString();
                    if ("40001".equals(code)) {
                        throw new BusinessException(BaseResponseCode.NULL_SDK_REQUEST);
                    }
//                    switch (code) {
//                       case "21000" :
//                           throw new BusinessException(BaseResponseCode.SINGLE_SUCCESS);
//
//                        case "40001" :
//                            throw new BusinessException(BaseResponseCode.NULL_SDK_REQUEST);
//
//                        default :
//                            System.out.println("Invalid grade");
//                   }

                }
                if ("X-Adtype".equals(allHeaders[m].getName())) { //广告返回类型
                    adtype = allHeaders[m].getValue();
                }
                if ("X-Clickthrough".equals(allHeaders[m].getName())) { //点击地址
                    clickthrough = allHeaders[m].getValue();
                }
                if ("X-Imptracker".equals(allHeaders[m].getName())) { //广告跟踪
                    imptracker = allHeaders[m].getValue();
                }
                if ("X-Launchpage".equals(allHeaders[m].getName())) { //landpage
                    launchpage = allHeaders[m].getValue();
                }
                if ("X-Creativeid".equals(allHeaders[m].getName())) { //推广创意 ID，长整型
                    creativeid = Long.valueOf(allHeaders[m].getValue());
                }
            }

            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();

//            if (null!=request.getTest() && 1==request.getTest()){//test
//                entityStr = TestResponseConfig.ydTestResponse;//测试数据
//            }else {
                // 使用Apache提供的工具类进行转换成字符串
                entityStr = EntityUtils.toString(entity, "UTF-8");
        //    }

            List<TzSeat> seatList = new ArrayList<>();//竞价集合对象，若是竞价至少有一个
            //多层解析json
            log.info("有道返回参数"+JSONObject.parseObject(entityStr));
            JSONObject jo = JSONObject.parseObject(entityStr);
            if(null != jo) {
                List<TzBid> bidList = new ArrayList<>();
                TzBid bid = new TzBid();
                String id = request.getId();//请求id
                bid.setAdid(jo.getInteger("creativeid").toString());  //广告ID
//                if("NATIVE".equals(jo.getString("adCat"))){
//                    bid.setAd_type(8);
//                }

                if("1".equals(ad_slot_type)){
                    bid.setAd_type(8);
                }else if("2".equals(ad_slot_type)){
                    bid.setAd_type(2);
                } else if("3".equals(ad_slot_type)){
                    bid.setAd_type(5);
                }else if("4".equals(ad_slot_type)){
                    bid.setAd_type(6);
                }else if("5".equals(ad_slot_type)){
                    bid.setAd_type(0);
                }else if("6".equals(ad_slot_type)){
                    bid.setAd_type(3);
                }else if("8".equals(ad_slot_type)){
                    bid.setAd_type(7);
                }else{
                    bid.setAd_type(8);
                }
                List<TzMacros> tzMacros1 = new ArrayList();
                TzMacros tzMacros = new TzMacros();
                bid.setClick_url(jo.getString("clk"));//点击链接


//                if(request.getApp().getId().equals("1000000099")){
//                    List<String> clickList = new ArrayList<>();
//                    JSONArray clicktrackers = jo.getJSONArray("clktrackers");
//                    if (null != clicktrackers) {
//                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=" + "%%CLICK_CLICKS%%");
//                        for (int cl = 0 ; cl < clicktrackers.size() ; cl++ ) {
//                            clickList.add(clicktrackers.get(cl).toString());
//                        }
//                        bid.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
//                        String checkClicks = clicktrackers.get(0).toString() + "," + id + "," + request.getAdv().getApp_id() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                        tzMacros = new TzMacros();
//                        tzMacros.setMacro("%%CLICK_CLICKS%%");
//                        tzMacros.setValue(Base64.encode(checkClicks));
//                        tzMacros1.add(tzMacros);
//                    }
//
//                    List<String> checkViews = new ArrayList<>();
//                    JSONArray imptrackers = jo.getJSONArray("imptracker");
//                    if (null != imptrackers) {
//                        // checkViews.add("http://adx-test-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv="+ "%%CLICK_VIEWS%%");
//                        checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=" + "%%CLICK_VIEWS%%");
//                        // checkViews.add(imptrackers.get(0).toString());
//                        for (int cv = 0 ; cv < imptrackers.size() ; cv++ ) {
//                            checkViews.add(imptrackers.get(cv).toString());
//                        }
//                        bid.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
//                        String pv = imptrackers.get(0).toString() + "," + id + "," + request.getAdv().getApp_id() + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getBundle();
//                        tzMacros = new TzMacros();
//                        tzMacros.setMacro("%%CLICK_VIEWS%%");
//                        tzMacros.setValue(Base64.encode(pv));
//                        tzMacros1.add(tzMacros);
//                    }
//                }else{
                    List<String> clickList = new ArrayList<>();
                    JSONArray clicktrackers = jo.getJSONArray("clktrackers");
                    if (null != clicktrackers) {
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cl = 0 ; cl < clicktrackers.size() ; cl++ ) {
                            clickList.add(clicktrackers.get(cl).toString());
                        }
                        bid.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        String checkClicks = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(checkClicks));
                        tzMacros1.add(tzMacros);
                    }

                    List<String> checkViews = new ArrayList<>();
                    JSONArray imptrackers = jo.getJSONArray("imptracker");
                    if (null != imptrackers) {
                        checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                        for (int cv = 0 ; cv < imptrackers.size() ; cv++ ) {
                            checkViews.add(imptrackers.get(cv).toString());
                        }
                        bid.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                        String pv = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(pv));
                        tzMacros1.add(tzMacros);
                    }
       //         }



                List<String> downloadStart = new ArrayList<>();
                JSONArray apkDownloadTrackers = jo.getJSONArray("apkDownloadTrackers");
                if (null != apkDownloadTrackers) {
                    downloadStart.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                    for(int dl = 0 ; dl < apkDownloadTrackers.size() ; dl++ ){
                        downloadStart.add(apkDownloadTrackers.get(dl).toString());
                    }
                    bid.setCheck_start_downloads(downloadStart);//曝光监测URL,支持宏替换第三方曝光监测
                    String downLoad = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_LOAD%%");
                    tzMacros.setValue(Base64.encode(downLoad));
                    tzMacros1.add(tzMacros);
                }



                String dpSuccessTrackers = jo.getString("dpSuccessTrackers");
                if(StringUtils.isNotEmpty(dpSuccessTrackers)){
                    String dpSuccessTrackerss = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    // String deepUrl = "http://adx-test-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink="+Base64.encode(dpSuccessTrackerss);//天卓deeplinkurl
                    String deepUrl = "http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink="+Base64.encode(dpSuccessTrackerss);//天卓deeplinkurl
                    bid.setDeeplink_murl(deepUrl);//deep Link 调起成功的上报地址
                }

              //    bid.setDeeplink_url(jo.getString("deeplink"));
                if (null != jo.getJSONArray("dptrackers")) {
                    bid.setCheck_success_deeplinks(JSONObject.parseArray(jo.getJSONArray("dptrackers").toString(), String.class));//deep link 点击跟踪链接数组
                }
                if("1".equals(ad_slot_type)){
                    TzNative tzNative = new TzNative();
                    List<TzImage> image = new ArrayList();
                    TzImage tzImage = new TzImage();
                    TzIcon tzIcon = new TzIcon();
                    tzIcon.setUrl(jo.getString("iconimage"));
                    tzNative.setTitle(jo.getString("title"));
                   // tzNative.setDesc(jo.getString("text"));
                    if(StringUtils.isNotEmpty(jo.getString("text"))){
                        tzNative.setDesc(jo.getString("text"));
                    }else{
                        tzNative.setDesc("");
                    }
                    tzImage.setUrl(jo.getString("mainimage"));
                    tzImage.setH(720);
                    tzImage.setW(1280);
                    image.add(tzImage);
                    tzNative.setIcon(tzIcon);
                    tzNative.setImages(image);
                    bid.setNATIVE(tzNative);
                }else{
                    List<TzImage> tzImages = new ArrayList();
                    TzImage tzImage = new TzImage();
                    tzImage.setUrl(jo.getString("mainimage"));
                    tzImage.setH(1280);
                    tzImage.setW(720);
                    tzImages.add(tzImage);
                    bid.setImages(tzImages);
                    bid.setTitle(jo.getString("title"));
                  //  bid.setDesc(jo.getString("text"));
                    if(StringUtils.isNotEmpty(jo.getString("text"))){
                        bid.setDesc(jo.getString("text"));
                    }else{
                        bid.setDesc("");
                    }
                }
                if(null != jo.get("dpNotInstallTrackers")){
                    List<String> lll = new ArrayList<>();
                    lll.add(jo.getString("dpNotInstallTrackers"));
                    bid.setCheck_end_downloads(lll);
                }
                if("0".equals(jo.getInteger("ydAdType").toString())){
                    bid.setClicktype("0");//广告类型，0：落地页广告；1：下载类型广告
                }else if("1".equals(jo.getInteger("ydAdType").toString())){
                    bid.setClicktype("1");//广告类型，0：落地页广告；1：下载类型广告
                }else{
                    bid.setClicktype("0");//广告类型，0：落地页广告；1：下载类型广告
                }

                bid.setTitle(jo.getString("styleName"));//广告样式名称
                //bid.setDeeplink_url("openapp://http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink="+"%%DEEPLINK_URL%%"+"&requestId="+id);//deep Link 调起成功的上报地址
                if(StringUtils.isNotEmpty(jo.getString("deeplink"))){
                    bid.setDeeplink_url(jo.getString("deeplink"));//deep Link 调起成功的上报地址
                }else{
                    bid.setDeeplink_url("openapp://www.163.com");//deep Link 调起成功的上报地址
                }

                 tzMacros = new TzMacros();
                tzMacros.setMacro("%%TS%%");
                String time =   (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                        ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                        ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                        ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                        ((tempTime % 1000));
                tzMacros.setValue(Base64.encode(AESUtil.encrypt(time)));
                tzMacros1.add(tzMacros);
                bid.setMacros(tzMacros1);

                List<String> checkFailDeeplinks = new ArrayList<>();
                String dpFailedTrackers =  jo.getString("dpFailedTrackers");//deep link 调起失败跟踪链接数组。当 deeplink 调起失败时返回此字段
                if(StringUtils.isNotEmpty(dpFailedTrackers)){
                    // checkFailDeeplinks.add(dpFailedTrackers);
                    checkFailDeeplinks.add(dpFailedTrackers);
                    bid.setCheck_fail_deeplinks(checkFailDeeplinks);
                }

                String appName = jo.getString("appName");//广告类型为下载类型时，并且 appName 不为空时，会返回此字段。该字段会将样式中的同名字段覆盖
                String packageName = jo.getString("packageName");//当广告类型为下载类型时，并且 packageName 不为空时，会返回此字段。该字段会将样式中的同名字段覆盖

//                List<String> checkStartDownloads = new ArrayList<>();
//                String apkStartDownloadTrackers = jo.getString("apkStartDownloadTrackers");//用户开始下载 APP 的打点上报地址
//                if(StringUtils.isNotEmpty(apkStartDownloadTrackers)){
//                    // checkStartDownloads.add(apkStartDownloadTrackers);
//                    checkStartDownloads.add(apkStartDownloadTrackers);
//                    bid.setCheck_start_downloads(checkStartDownloads);
//                }


//                List<String> checkEndInstalls = new ArrayList<>();
//                String apkInstallTrackers = jo.getString("apkInstallTrackers");//用户点击安装并完成安装的打点上报地址
//                if(StringUtils.isNotEmpty(apkInstallTrackers)){
//                    // checkEndInstalls.add(apkInstallTrackers);
//                    checkEndInstalls.add(apkInstallTrackers);
//                    bid.setCheck_end_installs(checkEndInstalls);
//                }
                bid.setSource(source);//素材来源
                bidList.add(bid);

                TzSeat seat = new TzSeat();//竞价集合对象，若是竞价至少有一个
                seat.setBid(bidList);
                seat.setSeat(jo.getString("seat"));
                seatList.add(seat);
                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);//请求id
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//耗时，单位：ms
            }
            log.info("有道总返回数据"+JSONObject.toJSONString(bidResponse));
           //     log.info(tempTime.toString());//输出请求时间
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URI解析异常");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }

        // 打印响应内容
        log.info(entityStr);
        return bidResponse;
    }
}
