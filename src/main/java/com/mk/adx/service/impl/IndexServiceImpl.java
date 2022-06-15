package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.Index.IndexAdslot;
import com.mk.adx.entity.json.request.Index.IndexApp;
import com.mk.adx.entity.json.request.Index.IndexBidRequest;
import com.mk.adx.entity.json.request.Index.IndexDevice;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.IndexService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


/**
 * @Author gj
 * @Description 因代克斯
 * @Date 2021/8/26 15:30
 */
@Slf4j
@Service("indexJsonService")
public class IndexServiceImpl implements IndexService {


    @Resource
    private RedisUtil redisUtil;

    public static  String id = "";

    private static final String name = "因代克斯";

    public static  String ip = "";

    //    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<TzBidResponse> indexHcDataByJson(TzBidRequest request) {

        IndexBidRequest bidRequest = new IndexBidRequest();
        bidRequest.setRequestId(request.getId());//自定义的请求 id，开发者自行生成需，保证其唯一性
        bidRequest.setApiVersion("2.0");//没有接激励视频的=这个版本号改成2.0
        bidRequest.setUserAgent(request.getDevice().getUa());//User-Agent
        bidRequest.setIp(request.getDevice().getIp());//设备的 ip，用于定位
        bidRequest.setSupportDp(true);

        IndexApp app = new IndexApp();
        app.setAppName(request.getApp().getName());//app 名称
        app.setPackageName(request.getApp().getBundle());//app 包名，(iOS 填写 bundleID,安卓填写包名）
        app.setVersion(request.getApp().getVer());//app 版本
        app.setStoreUrl(request.getApp().getStoreurl());//应用商店下载地址
        bidRequest.setAppInfoParam(app);//app 对象

        IndexDevice device = new IndexDevice();

        if ("0".equals(request.getDevice().getOs())) {
            device.setDeviceId(request.getDevice().getAndroid_id());//设备 id, android 为androidId;ios 系统为 idfa
        } else if ("1".equals(request.getDevice().getOs())) {
            device.setDeviceId(request.getDevice().getIdfa());//设备 id, android 为androidId;ios 系统为 idfa
        }
        device.setImei(request.getDevice().getImei());//设备 IMEI，若 IOS 设备拿不到则填空
        device.setOaid(request.getDevice().getOaid());//oaid 的明文，支持获取 oaid 的Android 设备必传
        device.setWifiMac(request.getDevice().getGeo().getWifi());//WIFI 路由器 MAC 地址
        device.setMac(request.getDevice().getMac());//设备 mac 地址
        device.setImsi(request.getDevice().getMccmnc());//IMSI(SIM 卡串号)

        if ("pc".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(0);//设备类型型 Unknown=0;
        } else if ("phone".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(1);//设备类型型 Phone/手机=1
        } else if ("ipad".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(2);//设备类型型 Tablet/平板=2;
        } else if ("tv".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(3);//设备类型型 TV/智能电视=3
        }

        if ("0".equals(request.getDevice().getOs())) {
            device.setOs("Android");//Android;IOS
        } else if ("1".equals(request.getDevice().getOs())) {
            device.setOs("IOS");//Android;IOS
        }

        device.setOsVersion(request.getDevice().getOsv());//操作系统版本
        device.setVendor(request.getDevice().getMake());//设备厂商，如 Apple
        device.setModel(request.getDevice().getModel());//设备型号，如 iPhone5s
        device.setLanguage(request.getDevice().getLanguage());//设备设置的语言:中文、英文、其他

        if (0 == request.getDevice().getConnectiontype()) {
            device.setConnType(0);//设备的网络类型:Unknown=0
        } else if (2 == request.getDevice().getConnectiontype()) {
            device.setConnType(1);//设备的网络类型:UWifi=1
        } else if (4 == request.getDevice().getConnectiontype()) {
            device.setConnType(2);//设备的网络类型:2G=2
        } else if (5 == request.getDevice().getConnectiontype()) {
            device.setConnType(3);//设备的网络类型:3G=3
        } else if (6 == request.getDevice().getConnectiontype()) {
            device.setConnType(4);//设备的网络类型:4G=4
        } else if (7 == request.getDevice().getConnectiontype()) {
            device.setConnType(5);//设备的网络类型:5G=5
        }

        if ("0".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(0);//运营商:UNKNOWN_OPERATOR=0
        } else if ("70120".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(1);//运营商:CHINA_MOBILE=1
        } else if ("70121".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(2);//运营商:CHINA_TELECOM=2
        } else if ("70123".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(3);//运营商:CHINA_UNICOM=3
        } else if ("70124".equals(request.getDevice().getCarrier())) {
            device.setOperatorType(99);//运营商:OTHER_OPERATOR = 99 其他运营商
        }

        device.setScreenWidth(request.getDevice().getW());//设备屏宽
        device.setScreenHeight(request.getDevice().getH());//设备屏高
//        device.setRomVersion(null);//手机 ROM 版本
//        device.setSysComplingTime(null);//系统编译时间:时间戳（系统更新时间）
        bidRequest.setDeviceInfoParam(device);

        IndexAdslot impList = new IndexAdslot();//imp集合
        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){
                app.setAppId(request.getImp().get(i).getTagid());//广告位 id(联系平台商务人员获取)
                    if ("1".equals(request.getImp().get(i).getAd_slot_type())){
                        impList.setAdType(2);//广告类型，信息流=2
                    }else if("3".equals(request.getImp().get(i).getAd_slot_type())){
                        impList.setAdType(3);//广告类型，开屏广告=3
                    }else if("4".equals(request.getImp().get(i).getAd_slot_type())){
                        impList.setAdType(5);//广告类型，视频=5
                    }else if("5".equals(request.getImp().get(i).getAd_slot_type())){
                        impList.setAdType(1);//广告类型，横幅=1
                    }else if("6".equals(request.getImp().get(i).getAd_slot_type())){
                        impList.setAdType(4);//广告类型，插屏=4
                    }
//                adslot.setPosition(0);//广告展现位置:顶部=1;底部=2;信息流内=3;中部=4;全屏=5
//                adslot.setWidth(0);//宽度，单位像素
//                adslot.setHeight(0);//高度，单位像素
            }
            }


        bidRequest.setAdSlotInfoParam(impList);


        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求因代克斯参数"+JSONObject.parseObject(content));
        String url = "http://testapi.indexmob.com/ad_api/media_test";
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
        log.info("请求上游花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("因代克斯返回参数"+JSONObject.parseObject(response));
        if (null != response) {
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            JSONObject data = jo.getJSONObject("data");
            String bidid = data.getString("requestId");
            String id = request.getId();//请求id
            JSONObject jj = data.getJSONObject("ads");

            List<TzBid> bidList = new ArrayList<>();
                    TzBid tb = new TzBid();
                    List<TzImage> list = new ArrayList<>();
                    tb.setAdid(jj.getString("adId"));//广告id


            TzNative tzNative = new TzNative();
                    if (null!=request.getImp()) {
                        for (int i = 0; i < request.getImp().size(); i++) {
                            if ("1".equals(request.getImp().get(i).getAd_slot_type()) || "2".equals(request.getImp().get(i).getAd_slot_type())) {
                                tb.setAd_type(8);//信息流
                                JSONArray img = jj.getJSONArray("imageSrcs");
                                for (int g = 0; g < img.size(); g++){
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(img.get(g).toString());
                                    tzImage.setW(1280);
                                    tzImage.setH(720);
                                    list.add(tzImage);

                                }
                                tzNative.setImages(list);
                                tb.setNATIVE(tzNative);
                            } else if ("3".equals(request.getImp().get(i).getAd_slot_type())) {
                                tb.setAd_type(5);//开屏
                                JSONArray img = jj.getJSONArray("imageSrcs");
                                for (int g = 0; g < img.size(); g++){
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(img.get(g).toString());
                                    list.add(tzImage);
                                }
                            }
                        }
                    }



                    tb.setImages(list);//广告图片地址，单个广告可能存在多个图片地址
                    tb.setDownload_url(jj.getString("clickAdUrl"));//广告点击链接,若 InteractionType=3 时，该字段值可能为下载地址，与 downloadUrl 相同
                    tb.setDeeplink_url(jj.getString("deeplink"));//app 端 deeplink 链接，该参数有值优先处理该值，若无值则调用 clickAdUrl


                    JSONArray tracks = jj.getJSONArray("tracks");
                    for (int g = 0; g < tracks.size(); g++){
                        if (tracks.getJSONObject(g).getInteger("type")== 1){
                            List<String> checkViews = new ArrayList<>();//曝光监测地址数组
                            JSONArray murl =  tracks.getJSONObject(g).getJSONArray("urls");
                            if(null != murl) {
                                String pv = murl.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_VIEWS%%");
                                tzMacros.setValue(Base64.encode(pv));
                                tzMacros1.add(tzMacros);
                                checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pvf?pv=%%CHECK_VIEWS%%");
                                for (int m = 0; m < murl.size(); m++) {
                                    checkViews.add(murl.get(m).toString());
                                }
                                tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                            }
                        }else if(tracks.getJSONObject(g).getInteger("type") == 2){
                            List<String> checkClicks = new ArrayList<>();
                            JSONArray cmurl = tracks.getJSONObject(g).getJSONArray("urls");//点击监测地址数组
                            if (null != cmurl) {
                                String checkClicksdd = cmurl.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_CLICKS%%");
                                tzMacros.setValue(Base64.encode(checkClicksdd));
                                tzMacros1.add(tzMacros);
                                checkClicks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicksf?checkClicks=%%CHECK_CLICKS%%");
                                for (int cm = 0 ; cm < cmurl.size() ; cm++ ) {
                                    checkClicks.add(cmurl.get(cm).toString());
                                }
                                tb.setCheck_clicks(checkClicks);
                            }
                        }else if(tracks.getJSONObject(g).getInteger("type") == 3){
                            List<String> checkStartDownloads = new ArrayList<>();
                            JSONArray dmurl = tracks.getJSONObject(g).getJSONArray("urls");//app 下载开始的监测地址
                            if (null != dmurl) {
                                String dmurls = dmurl.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_START%%");
                                tzMacros.setValue(Base64.encode(dmurls));
                                tzMacros1.add(tzMacros);
                                checkStartDownloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_START%%");
                                for (int cm = 0 ; cm < dmurl.size() ; cm++ ) {
                                    checkStartDownloads.add(dmurl.get(cm).toString());
                                }
                                tb.setCheck_start_downloads(checkStartDownloads);//曝光监测URL,支持宏替换第三方曝光监测
                            }
                            //tb.setCheck_start_downloads(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//下载开始监测
                        }else if(tracks.getJSONObject(g).getInteger("type") == 4){
                            List<String> checkEndDownloads = new ArrayList<>();
                            JSONArray downsuccessurl = tracks.getJSONObject(g).getJSONArray("urls");//app 下载完成的监测地址
                            if (null != downsuccessurl) {
                                String downsuccessurls = downsuccessurl.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_SUCCESS%%");
                                tzMacros.setValue(Base64.encode(downsuccessurls));
                                tzMacros1.add(tzMacros);
                                checkEndDownloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/end?downloadEnd=%%DOWN_SUCCESS%%");
                                for (int cm = 0 ; cm < downsuccessurl.size() ; cm++ ) {
                                    checkEndDownloads.add(downsuccessurl.get(cm).toString());
                                }
                                tb.setCheck_end_downloads(checkEndDownloads);
                            }
                            //tb.setCheck_end_downloads(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//下载完成监测
                        }else if(tracks.getJSONObject(g).getInteger("type") == 5){
                            List<String> check_start_installs = new ArrayList<>();
                            JSONArray check_start_installss = tracks.getJSONObject(g).getJSONArray("urls");
                            if (null != check_start_installss) {
                                String iurl = check_start_installss.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%INSTALL_START%%");
                                tzMacros.setValue(Base64.encode(iurl));
                                tzMacros1.add(tzMacros);
                                check_start_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/start?installStart=" + Base64.encode(iurl));
                                for (int cm = 0; cm < check_start_installss.size(); cm++) {
                                    check_start_installs.add(check_start_installss.get(cm).toString());
                                }
                                tb.setCheck_start_installs(check_start_installs);//开始安装监测URL第三方曝光监测
                            }
                            //tb.setCheck_start_installs(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//安装开始监测
                        }else if(tracks.getJSONObject(g).getInteger("type") == 6){
                            List<String> check_end_installs = new ArrayList<>();
                            JSONArray check_end_installss = tracks.getJSONObject(g).getJSONArray("urls");
                            if (null != check_end_installss) {
                                String iurl = check_end_installss.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                                tzMacros.setValue(Base64.encode(iurl));
                                tzMacros1.add(tzMacros);
                                check_end_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/end?installEnd=" + Base64.encode(iurl));
                                for (int cm = 0; cm < check_end_installss.size(); cm++) {
                                    check_end_installs.add(check_end_installss.get(cm).toString());
                                }
                                tb.setCheck_end_installs(check_end_installs);//开始安装监测URL第三方曝光监测
                            }
                            //tb.setCheck_end_installs(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//安装完成监测
                        }else if(tracks.getJSONObject(g).getInteger("type") == 7){
                            tb.setCheck_activations(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//打开 APP(激活)监测
                        }else if(tracks.getJSONObject(g).getInteger("type") == 16){
                            tb.setCheck_fail_deeplinks(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//deeplink 打开失败监测
                        }else if(tracks.getJSONObject(g).getInteger("type") == 17){
                            List<String> checkSuccessDeeplinks = new ArrayList<>();
                            JSONArray deeplinkmurl = tracks.getJSONObject(g).getJSONArray("urls");//仅用于唤醒广告，deeplink 链接调起成功
                            if (null != deeplinkmurl) {
                                String deeplinkmurls = deeplinkmurl.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DEEP_LINK%%");
                                tzMacros.setValue(Base64.encode(deeplinkmurls));
                                tzMacros1.add(tzMacros);
                                checkSuccessDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK%%");
                                for (int cm = 0 ; cm < deeplinkmurl.size() ; cm++ ) {
                                    checkSuccessDeeplinks.add(deeplinkmurl.get(cm).toString());
                                }
                                tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                            }
                            //tb.setCheck_success_deeplinks(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//deeplink 打开成功监测
                        }

                    }


                    tb.setMacros(tzMacros1);
                    bidList.add(tb);//
                tzSeat.setBid(bidList);
                seatList.add(tzSeat);

            bidResponse.setId(id);//请求id
            bidResponse.setBidid(bidid);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
            log.info("总返回"+JSONObject.toJSONString(bidResponse));
        }

        return AsyncResult.forValue(bidResponse);
    }



}
