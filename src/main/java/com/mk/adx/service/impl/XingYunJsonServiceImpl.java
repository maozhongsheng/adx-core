package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.xingyun.XyBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.MD5Util;
import com.mk.adx.service.XingYunJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author gj
 * @Description 星云
 * @Date 2021/12/9 9:48
 */
@Slf4j
@Service("xingYunJsonService")
public class XingYunJsonServiceImpl implements XingYunJsonService {

    public static String id = "";

    private static final String name = "xingyun";

    private static final String source = "星云";

    public static String ip = "";

    @SneakyThrows
    @Override
    public TzBidResponse getXyDataByJson(TzBidRequest request) {
        XyBidRequest bidRequest = new XyBidRequest();
        bidRequest.setReq_id(request.getId());//请求id
        bidRequest.setVersion(request.getMedia_version());//接⼝版本，当前是1.3.3
        String os = request.getDevice().getOs();//系统类型
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {
            String imei = request.getDevice().getImei();
            if (imei != null){
                bidRequest.setImei(request.getDevice().getImei());//设备的imei(安卓需要)
            }else {
                bidRequest.setImei(request.getDevice().getOaid());//设备的imei(安卓需要)
            }
            bidRequest.setImei_md5(request.getDevice().getImei_md5());//设备的imei md5值
            bidRequest.setOaid(request.getDevice().getOaid());//设备的oaid(安卓需要)
            bidRequest.setOaid_md5(request.getDevice().getOaid_md5());//设备的oaid md5值(安卓需要)
            bidRequest.setAndroid_id(request.getDevice().getAndroid_id());//设备的android_id
            bidRequest.setAndroid_id_md5(request.getDevice().getAndroid_id_md5());//设备的android_idmd5
            bidRequest.setOs_type("android");//设备操作系统类型
        } else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            bidRequest.setImei(request.getDevice().getImei());//设备的imei
            bidRequest.setImei_md5(request.getDevice().getImei_md5());//设备的imei md5值
            bidRequest.setCaid(request.getDevice().getCaid());//设备的caid
            bidRequest.setIdfv(request.getDevice().getIdfv());//设备的idfv
            bidRequest.setOpenudid(request.getDevice().getOpen_udid());//open_udid(ios需要)
            bidRequest.setOs_type("ios");//设备操作系统类型
        }
        bidRequest.setApp_name(request.getAdv().getApp_name());//应⽤名称
        bidRequest.setApp_package(request.getAdv().getBundle());//应⽤包名
        bidRequest.setApp_version(request.getAdv().getVersion());//应⽤版本号
//        bidRequest.setApp_name(request.getApp().getName());//应⽤名称
//        bidRequest.setApp_package(request.getApp().getBundle());//应⽤包名
//        bidRequest.setApp_version(request.getApp().getVer());//应⽤版本号
        bidRequest.setIp(request.getDevice().getIp());//设备外⽹的ip地址
        bidRequest.setUser_agent(request.getDevice().getUa());//设备实际User-Agent值，WebviewUA
        bidRequest.setMac(request.getDevice().getMac());//设备mac地址
        bidRequest.setModel(request.getDevice().getModel());// 设备型号名称
        bidRequest.setBrand(request.getDevice().getMake());//设备品牌名称
        bidRequest.setOs_version(request.getDevice().getOsv());//设备操作系统版本
        bidRequest.setDevice_width(request.getDevice().getW());//设备宽度
        bidRequest.setDevice_height(request.getDevice().getH());//设备⾼度
        bidRequest.setDpi(480);//设备屏幕图像密度
        bidRequest.setDensity(3);//设备屏幕物理像素密度
        if (0 == request.getDevice().getConnectiontype()) {//网络类型
            bidRequest.setConnection_type("unknow");//未知
        } else if (2 == request.getDevice().getConnectiontype()) {
            bidRequest.setConnection_type("wifi");//wifi
        } else if (4 == request.getDevice().getConnectiontype()) {
            bidRequest.setConnection_type("2g");//移动⽹络2G Cellular Network – 2G
        } else if (5 == request.getDevice().getConnectiontype()) {
            bidRequest.setConnection_type("3g");//移动⽹络3G Cellular Network – 3G
        } else if (6 == request.getDevice().getConnectiontype()) {
            bidRequest.setConnection_type("4g");//移动⽹络4G Cellular Network – 4G
        } else if (7 == request.getDevice().getConnectiontype()) {
            bidRequest.setConnection_type("5g");//移动⽹络5G Cellular Network – 5G
        }

        if ("0".equals(request.getDevice().getCarrier())) {//⼿机运营商代号
            bidRequest.setNetwork("");//未知
        } else if ("70120".equals(request.getDevice().getCarrier())) {
            bidRequest.setNetwork("46000");//中国移动
        } else if ("70123".equals(request.getDevice().getCarrier())) {
            bidRequest.setNetwork("46001");//中国联通
        } else if ("70121".equals(request.getDevice().getCarrier())) {
            bidRequest.setNetwork("46003");//中国电信
        }else {
            bidRequest.setNetwork("");//其它
        }

        String media_id = request.getAdv().getApp_id();//应用id
        String position_id = request.getAdv().getTag_id();//广告位id

//        String media_id = request.getApp().getId();//应用id
//        String position_id = null;
//        if (null!=request.getImp()) {
//            for (int i=0;i<request.getImp().size();i++) {
//                position_id = request.getImp().get(i).getTagid();//广告位id
//            }
//        }

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求星云参数" + JSONObject.parseObject(content));
        String url = "https://api.nebulae.com.cn/ads"+"/"+media_id+"/"+position_id;
        log.info("URL："+url);
        String ua = request.getDevice().getUa();
//      String media_id = "6695273";//应用id
//      String position_id = "669527302101";//广告id
        String APP_KEY = "7e6ae875";//APP_KEY
        String APP_SECRET = "fd8b30c2d1639d3c79e0d0463c4eec70";//APP_SECRET
//        String APP_KEY = "7021bc5d";//APP_KEY
//        String APP_SECRET = "10bd0ac54dc0e0842a4513a12122a0b3";//APP_SECRET
        String METHOD = "POST";//请求方式
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss 'GMT'", Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String DATE = df.format(new Date());//GMT格式日期
        String API = "/ads"+"/"+media_id+"/"+position_id;
        String md5 = MD5Util.getMD5(APP_SECRET);
        int CONTENT_LENGTH = content.length();
//        log.info("加密前："+METHOD+"&"+API+"&"+DATE+"&"+CONTENT_LENGTH+"&"+md5);
        String  SIGNATURE = MD5Util.getMD5(METHOD+"&"+API+"&"+DATE+"&"+CONTENT_LENGTH+"&"+md5);
//        log.info("加密后："+SIGNATURE);
        String  authorization = APP_KEY+":"+SIGNATURE;
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonsPost(url, content, ua,authorization,DATE);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求上游星云花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("星云返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            id = request.getId();
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);

            if (jo.getInteger("ret") == 1) {
                JSONArray bids = jo.getJSONArray("bids");
                if (bids != null) {
                    id = request.getId();
                    TzBid tb = new TzBid();
                    TzBidApps app = new TzBidApps();
                    List<TzBid> bidList = new ArrayList<>();
                    for (int j = 0; j < bids.size(); j++) {
                        Integer ad_type = bids.getJSONObject(j).getInteger("ad_type");
                        if(2 == ad_type){//deeplink⼴告
                            tb.setClicktype("2");
                            tb.setDeeplink_url(bids.getJSONObject(j).getString("deeplink_url"));//deeplink⼴告链接 ad_type为2时必传
                        }else if(4 == ad_type) {//点击下载
                            tb.setClicktype("4");
                            tb.setClick_url(bids.getJSONObject(j).getString("url"));//点击链接
                            app.setApp_name(bids.getJSONObject(j).getString("app_name"));//应⽤名
                            app.setBundle(bids.getJSONObject(j).getString("app_package"));//应⽤包名
                            app.setApp_size(bids.getJSONObject(j).getInteger("app_size"));//应⽤⼤⼩
                            tb.setApp(app);
                        }else {
                            tb.setClicktype("0");
                        }

                        Integer ad_style = bids.getJSONObject(j).getInteger("ad_style");
                        if (2 == ad_style){
                            if (null != bids.getJSONObject(j).getJSONArray("image_urls")) {
                                tb.setAd_type(8);//信息流-广告素材类型
                                /**
                                 * 信息流图片
                                 */
                                TzNative tzNative = new TzNative();
                                List<TzImage> tzImages = new ArrayList<>();//天卓图片素材
                                JSONArray xyImages = bids.getJSONObject(j).getJSONArray("image_urls");//图片素材
                                TzImage image = new TzImage();
                                if (null != request.getImp()) {
                                    for (int s = 0; s < request.getImp().size(); s++) {
                                        image.setUrl(xyImages.get(s).toString());
                                        image.setW(bids.getJSONObject(j).getInteger("width"));
                                        image.setH(bids.getJSONObject(j).getInteger("height"));
                                    }
                                }
                                tzImages.add(image);
                                tzNative.setImages(tzImages);
                                tb.setNATIVE(tzNative);
                            }
                        } else if (1 == ad_style || 3 == ad_style || 4 == ad_style){
                            tb.setAd_type(5);//开屏-广告素材类型
                            tb.setAic(bids.getJSONObject(j).getString("icon"));//品牌logo、⼴告主头像等
                            /**
                             * 开屏流图片
                             */
                            List<TzImage> tzImages = new ArrayList<>();//天卓图片素材
                            if (null != bids.getJSONObject(j).getString("image_url")) {
                                String xyImage = bids.getJSONObject(j).getString("image_url");//图片素材
                                TzImage image = new TzImage();
                                if (null != request.getImp()) {
                                    for (int a = 0; a < request.getImp().size(); a++) {
                                        image.setUrl(xyImage);
                                        image.setW(bids.getJSONObject(j).getInteger("width"));
                                        image.setH(bids.getJSONObject(j).getInteger("height"));
                                    }
                                }
                                tzImages.add(image);
                            }
                            tb.setImages(tzImages);
                        }

                        tb.setTitle(bids.getJSONObject(j).getString("title"));//⼴告标题
                        tb.setDesc(bids.getJSONObject(j).getString("desc"));//⼴告描述

                        if(null != bids.getJSONObject(j).getJSONArray("show_urls") && bids.getJSONObject(j).getJSONArray("show_urls").size() != 0){//展示上报
                            List<String> check_views = new ArrayList<>();
                            check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                            JSONArray urls1 = bids.getJSONObject(j).getJSONArray("show_urls");
                            for (int cv = 0; cv < urls1.size(); cv++) {
                                Long dateTime = System.currentTimeMillis() / 1000;
                                Long dateMidTime = System.currentTimeMillis();
                                check_views.add(urls1.get(cv).toString().replace("__DOWN_POS_X__","%%DOWN_X%%").replace("__DOWN_POS_Y__","%%DOWN_Y%%").replace("__UP_POS_X__","%%UP_X%%").replace("__UP_POS_Y__","%%UP_Y%%").replace("__DOWN_AD_X__","%%ABS_DOWN_X%%").replace("__DOWN_AD_Y__","%%ABS_DOWN_Y%%").replace("__UP_AD_X__","%%ABS_UP_X%%").replace("__UP_AD_Y__","%%ABS_UP_Y%%").replace("__TS_SECOND__",dateTime.toString()).replace("__TS__",dateMidTime.toString()));
                            }
                            String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_VIEWS%%");
                            tzMacros.setValue(com.mk.adx.util.Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != bids.getJSONObject(j).getJSONArray("click_urls") && bids.getJSONObject(j).getJSONArray("click_urls").size() != 0){//点击上报
                            List<String> clickList = new ArrayList<>();
                            JSONArray urls1 =  bids.getJSONObject(j).getJSONArray("click_urls");
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                Long dateTime = System.currentTimeMillis() / 1000;
                                Long dateMidTime = System.currentTimeMillis();
                                clickList.add(urls1.get(cc).toString().replace("__DOWN_POS_X__","%%DOWN_X%%").replace("__DOWN_POS_Y__","%%DOWN_Y%%").replace("__UP_POS_X__","%%UP_X%%").replace("__UP_POS_Y__","%%UP_Y%%").replace("__DOWN_AD_X__","%%ABS_DOWN_X%%").replace("__DOWN_AD_Y__","%%ABS_DOWN_Y%%").replace("__UP_AD_X__","%%ABS_UP_X%%").replace("__UP_AD_Y__","%%ABS_UP_Y%%").replace("__TS_SECOND__",dateTime.toString()).replace("__TS__",dateMidTime.toString()));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_CLICKS%%");
                            tzMacros.setValue(com.mk.adx.util.Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        List<String> checkSuccessDeeplinks = new ArrayList<>();
                        JSONArray deeplinkmurl = bids.getJSONObject(j).getJSONArray("deeplink_report");//仅用于唤醒广告，deeplink 链接调起成功
                        if (null != deeplinkmurl && 0 < deeplinkmurl.size()) {
                            for (int cm = 0; cm < deeplinkmurl.size(); cm++) {
                                checkSuccessDeeplinks.add(deeplinkmurl.get(cm).toString());
                            }
                            String deeplinkmurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DEEP_LINK%%");
                            tzMacros.setValue(com.mk.adx.util.Base64.encode(deeplinkmurls));
                            tzMacros1.add(tzMacros);
                            checkSuccessDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                            tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                        }

                        if(null != bids.getJSONObject(j).getJSONArray("start_download_urls") && bids.getJSONObject(j).getJSONArray("start_download_urls").size() != 0){//开始下载上报
                            List<String> downLoadList = new ArrayList<>();
                            JSONArray urls1 =  bids.getJSONObject(j).getJSONArray("start_download_urls");
                            downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                            for (int dl = 0; dl < urls1.size(); dl++) {
                                downLoadList.add(urls1.get(dl).toString());
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_downloads(downLoadList);//开始下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_LOAD%%");
                            tzMacros.setValue(com.mk.adx.util.Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != bids.getJSONObject(j).getJSONArray("finish_download_urls") && bids.getJSONObject(j).getJSONArray("finish_download_urls").size() != 0){//下载完成上报
                            List<String> downLoadDList = new ArrayList<>();
                            JSONArray urls1 =  bids.getJSONObject(j).getJSONArray("finish_download_urls");
                            downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                            for (int dle = 0; dle < urls1.size(); dle++) {
                                downLoadDList.add(urls1.get(dle).toString());
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_downloads(downLoadDList);//结束下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_END%%");
                            tzMacros.setValue(com.mk.adx.util.Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != bids.getJSONObject(j).getJSONArray("start_install_urls") && bids.getJSONObject(j).getJSONArray("start_install_urls").size() != 0){//开始安装上报
                            List<String> installList = new ArrayList<>();
                            JSONArray urls1 =  bids.getJSONObject(j).getJSONArray("start_install_urls");
                            installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                            for (int ins = 0; ins < urls1.size(); ins++) {
                                installList.add(urls1.get(ins).toString());
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_installs(installList);//开始安装
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%INSTALL_START%%");
                            tzMacros.setValue(com.mk.adx.util.Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != bids.getJSONObject(j).getJSONArray("finish_install_urls") && bids.getJSONObject(j).getJSONArray("finish_install_urls").size() != 0){//安装完成上报
                            List<String> installEList = new ArrayList<>();
                            JSONArray urls1 =  bids.getJSONObject(j).getJSONArray("finish_install_urls");
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

                    }
                    tb.setSource(source);
                    tb.setMacros(tzMacros1);
                    bidList.add(tb);//
                    tzSeat.setBid(bidList);
                    seatList.add(tzSeat);

                    
                    bidResponse.setId(id);//请求id
                    bidResponse.setSeatbid(seatList);//广告集合对象
                    bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info("星云总返回" + JSONObject.toJSONString(bidResponse));
                }
            }
        }
        return bidResponse;
    }


}
