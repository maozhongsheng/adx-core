package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.qingyun.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.QyJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author gj
 * @Description 青云
 * @Date 2021/12/7 9:48
 */
@Slf4j
@Service("qyJsonService")
public class QyJsonServiceImpl implements QyJsonService {

    public static String id = "";

    private static final String name = "qingyun";

    private static final String source = "青云";


    public static String ip = "";

    @SneakyThrows
    @Override
    public TzBidResponse getQyDataByJson(TzBidRequest request) {
        QyBidRequest bidRequest = new QyBidRequest();
        bidRequest.setRequest_id(request.getId());//请求 ID由接入方生成，长度 12～36 位，需唯一
        bidRequest.setProtocol_type(1);//协议类型：0-HTTP，1-HTTPS HTTPS 媒体必填，支持返回 HTTPS 物料
        bidRequest.setApi_version(request.getMedia_version());//API 版本号，例如：3.0
        Long dateTime = System.currentTimeMillis();
        bidRequest.setTimestamp(dateTime);//时间戳：单位毫秒
        QyPos pos = new QyPos();
        if (null != request.getImp()) {
            for (int i = 0; i < request.getImp().size(); i++) {
                pos.setId(request.getAdv().getTag_id());//广告位 ID
                pos.setWidth(request.getImp().get(i).getNATIVE().getW());//广告位宽度，默认 0
                pos.setHeight(request.getImp().get(i).getNATIVE().getH());//广告位高度，默认 0
                bidRequest.setPos(pos);
            }
        }
        QyApp app = new QyApp();
        app.setApp_id(request.getAdv().getApp_id());//应用 ID，青云平台生成
        app.setApp_bundle(request.getAdv().getBundle());//应用包名
        app.setApp_version(request.getAdv().getVersion());//应用版本，建议填写
        bidRequest.setApp(app);
        QyDevice device = new QyDevice();
        String os = request.getDevice().getOs();//系统类型
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {
            device.setOs_type(1);//1:Android
            device.setImei(request.getDevice().getImei());//Android 设备唯一标识码
            device.setOaid(request.getDevice().getOaid());//Android 设备唯一标识码Android 10 系统及以上
            device.setAndroid_id(request.getDevice().getAndroid_id());//Android 设备系统 ID
        } else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOs_type(2);//2: IOS
            device.setIdfa(request.getDevice().getIdfa());//IOS 设备唯一标识码
        } else {
            device.setOs_type(3);//3-HarmonyOS
        }
        device.setCaid(request.getDevice().getCaid());//广协 CAID
        device.setMac(request.getDevice().getMac());//设备 MAC 地址
        if ("pc".equals(request.getDevice().getDevicetype()) || "tv".equals(request.getDevice().getDevicetype())) {//必填，发起当前请求的设备类型信息
            device.setDevice_type(0);//未知
        } else if ("phone".equals(request.getDevice().getDevicetype())) {
            device.setDevice_type(1);//⼿机设备
        } else if ("ipad".equals(request.getDevice().getDevicetype())) {
            device.setDevice_type(2);//平板设备
        }
        device.setUser_agent(request.getDevice().getUa());//客户端 WebView 的 UserAgent 信息
        device.setVendor(request.getDevice().getMake());//设备厂商名称，例如：HUAWEI
        device.setModel(request.getDevice().getModel());//设备型号，例如：nova 8
        device.setOs_version(request.getDevice().getOsv());//操作系统三段式或两段式版本号
        device.setScreen_width(request.getDevice().getW());//屏幕宽度
        device.setScreen_height(request.getDevice().getH());//屏幕高度
        bidRequest.setDevice(device);
        QyNetwork network = new QyNetwork();
        network.setIp(request.getDevice().getIp());//网络 IP
        if (0 == request.getDevice().getConnectiontype()) {//网络类型
            network.setConnection_type(0);//未知
        } else if (2 == request.getDevice().getConnectiontype()) {
            network.setConnection_type(1);//wifi
        } else if (4 == request.getDevice().getConnectiontype()) {
            network.setConnection_type(2);//移动⽹络2G Cellular Network – 2G
        } else if (5 == request.getDevice().getConnectiontype()) {
            network.setConnection_type(3);//移动⽹络3G Cellular Network – 3G
        } else if (6 == request.getDevice().getConnectiontype()) {
            network.setConnection_type(4);//移动⽹络4G Cellular Network – 4G
        } else if (7 == request.getDevice().getConnectiontype()) {
            network.setConnection_type(5);//移动⽹络5G Cellular Network – 5G
        }

        if ("0".equals(request.getDevice().getCarrier())) {//必填，提供完整的Carrier对象来描述当前运营商信息
            network.setOperator_type(0);//未知
        } else if ("70120".equals(request.getDevice().getCarrier())) {
            network.setOperator_type(1);//中国移动
        } else if ("70123".equals(request.getDevice().getCarrier())) {
            network.setOperator_type(2);//中国联通
        } else if ("70121".equals(request.getDevice().getCarrier())) {
            network.setOperator_type(3);//中国电信
        }else {
            network.setOperator_type(4);//其它
        }
        bidRequest.setNetwork(network);
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求青云参数" + JSONObject.parseObject(content));
        String url = "https://api.aiqygogo.com/ad/get/request.do";
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
        log.info("请求上游青云花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("青云返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            id = request.getId();
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);

            if (jo.getInteger("code") == 0) {
                JSONObject data = jo.getJSONObject("data");
                if (data != null) {
                    id = request.getId();
                    TzBid tb = new TzBid();
                    List<TzBidApps> appsList = new ArrayList<>();
                    List<TzBid> bidList = new ArrayList<>();
                    List<TzImage> list = new ArrayList<>();
                    tb.setAdid(data.getString("pos_id"));
                    JSONArray ads = data.getJSONArray("ads");
                    for (int i = 0; i < ads.size(); i++) {
                        JSONObject materials = ads.getJSONObject(i).getJSONObject("material");
                                tb.setAdid(materials.getString("ad_id"));//广告 ID
                                tb.setTitle(materials.getString("title"));//广告标题
                                tb.setDesc(materials.getString("description"));//广告描述
                                tb.setAdLogo(materials.getString("icon_url"));
                                if (null != materials.getJSONArray("img_list")) {
                                    tb.setAd_type(8);//信息流-广告素材类型
                                    /**
                                     * 信息流图片
                                     */
                                    TzNative tzNative = new TzNative();
                                    List<TzImage> tzImages = new ArrayList<>();//天卓图片素材
                                    JSONArray qyImages = materials.getJSONArray("img_list");//图片素材
                                    TzImage image = new TzImage();
                                    if (null != request.getImp()) {
                                        for (int s = 0; s < request.getImp().size(); s++) {
                                            image.setUrl(qyImages.get(s).toString());
                                            image.setW(materials.getInteger("img_width"));
                                            image.setH(materials.getInteger("img_height"));
                                        }
                                    }
                                    tzImages.add(image);
                                    tzNative.setImages(tzImages);
                                    tb.setNATIVE(tzNative);
                                } else {
                                    tb.setAd_type(5);//开屏-广告素材类型
                                    /**
                                     * 开屏流图片
                                     */
                                    List<TzImage> tzImages = new ArrayList<>();//天卓图片素材
                                    if (null != materials.getString("img_url")) {
                                        String qyImage = materials.getString("img_url");//图片素材
                                        TzImage image = new TzImage();
                                        if (null != request.getImp()) {
                                            for (int a = 0; a < request.getImp().size(); a++) {
                                                image.setUrl(qyImage);
                                                image.setW(materials.getInteger("img_width"));
                                                image.setH(materials.getInteger("img_height"));
                                            }
                                        }
                                        tzImages.add(image);
                                    }
                                    tb.setImages(tzImages);
                                }
                                if (materials.getInteger("interaction_type") == 1) {//交互类型，1-打开网页
                                    tb.setClicktype("1");
                                } else if (materials.getInteger("interaction_type") == 2) {//交互类型，2-点击下载
                                    tb.setClicktype("4");
                                } else if (materials.getInteger("interaction_type") == 3) {//交互类型，3-APP 唤醒
                                    tb.setClicktype("2");
                                } else {
                                    tb.setClicktype("0");
                                }
                                TzBidApps apps = new TzBidApps();
                                apps.setBundle(materials.getString("app_package"));//下载 APP 包名
                                apps.setApp_name(materials.getString("app_name"));//APP 名称
                                apps.setApp_size(materials.getInteger("app_size"));//下载 APP 大小，单位：KB
                                appsList.add(apps);

                        if(StringUtils.isNotEmpty(ads.getJSONObject(i).getString("deep_link_url"))){
                            tb.setDeeplink_url(ads.getJSONObject(i).getString("deep_link_url"));//deeplink 唤醒地址deeplink 唤醒广告打开页面
                        } else if(StringUtils.isNotEmpty(ads.getJSONObject(i).getString("click_url"))) {
                            tb.setClick_url(ads.getJSONObject(i).getString("click_url")); // 点击跳转url地址
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("click_links")){
                            List<String> clickList = new ArrayList<>();
                            JSONArray urls1 =  ads.getJSONObject(i).getJSONArray("click_links");
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                clickList.add(urls1.get(cc).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_CLICKS%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("impression_links")){
                            List<String> check_views = new ArrayList<>();
                            check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                            JSONArray urls1 = ads.getJSONObject(i).getJSONArray("impression_links");
                            for (int cv = 0; cv < urls1.size(); cv++) {
                                check_views.add(urls1.get(cv).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_VIEWS%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("download_links")){
                            List<String> downLoadList = new ArrayList<>();
                            JSONArray urls1 =  ads.getJSONObject(i).getJSONArray("download_links");
                            downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                            for (int dl = 0; dl < urls1.size(); dl++) {
                                downLoadList.add(urls1.get(dl).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_downloads(downLoadList);//开始下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_LOAD%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("downloaded_links")){
                            List<String> downLoadDList = new ArrayList<>();
                            JSONArray urls1 =  ads.getJSONObject(i).getJSONArray("downloaded_links");
                            downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                            for (int dle = 0; dle < urls1.size(); dle++) {
                                downLoadDList.add(urls1.get(dle).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_downloads(downLoadDList);//结束下载
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DOWN_END%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("install_links")){
                            List<String> installList = new ArrayList<>();
                            JSONArray urls1 =  ads.getJSONObject(i).getJSONArray("install_links");
                            installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                            for (int ins = 0; ins < urls1.size(); ins++) {
                                installList.add(urls1.get(ins).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_installs(installList);//开始安装
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%INSTALL_START%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("installed_links")){
                            List<String> installEList = new ArrayList<>();
                            JSONArray urls1 =  ads.getJSONObject(i).getJSONArray("installed_links");
                            installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                            for (int ins = 0; ins < urls1.size(); ins++) {
                                installEList.add(urls1.get(ins).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_installs(installEList);//安装完成
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("video_play_links")){
                            List<String> voidStartList = new ArrayList<>();
                            JSONArray urls1 =  ads.getJSONObject(i).getJSONArray("video_play_links");
                            voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                            for (int vs = 0; vs < urls1.size(); vs++) {
                                voidStartList.add(urls1.get(vs).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            //    tb.setCheck_views(voidStartList);//视频开始播放
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%VEDIO_START%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if(null != ads.getJSONObject(i).getJSONArray("video_finish_links")){
                            List<String> voidEndList = new ArrayList<>();
                            JSONArray urls1 = ads.getJSONObject(i).getJSONArray("video_finish_links");
                            voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                            for (int ve = 0; ve < urls1.size(); ve++) {
                                voidEndList.add(urls1.get(ve).toString().replace("__DOWN_X__ ","%%DOWN_X%%").replace("__DOWN_Y__  ","%%DOWN_Y%%").replace("__UP_X__  ","%%UP_X%%").replace("__UP_Y__ ","%%UP_Y%%"));
                            }
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            //    tb.setCheck_clicks(voidEndList);//视频播放结束
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%VEDIO_END%%");
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
                    log.info("青云总返回" + JSONObject.toJSONString(bidResponse));
                }
        }
        }
        return bidResponse;
    }
}
