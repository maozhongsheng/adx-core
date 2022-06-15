package com.mk.adx.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.yiqi.*;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.RedisUtil;
import com.mk.adx.service.YqJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author gj
 * @Description 益起
 * @Date 2021/11/03 10:30
 */
@Slf4j
@Service("yqJsonService")
public class YqJsonServiceImpl implements YqJsonService {
    @Resource
    private RedisUtil redisUtil;

    public static  String id = "";

    private static final String name = "yq";

    private static final String source = "益起";


    public static  String ip = "";

    @SneakyThrows
    @Override
    public TzBidResponse getYqDataByJson(TzBidRequest request) {
        YQBidRequest bidRequest = new YQBidRequest();

        bidRequest.setVer(request.getMedia_version());//当前文档版本名称

        YQApp app = new YQApp();
        app.setAppId(request.getAdv().getApp_id());//应用 ID
        app.setAppName(request.getAdv().getApp_name());//APP 名称
        app.setPkg(request.getAdv().getBundle());//APP 包名
        app.setAppVer(request.getAdv().getVersion());//APP 版 本 号
        app.setAppVerCode(119);//应用程序版本号
        bidRequest.setApp(app);

        YQDevice device = new YQDevice();
        if ("0".equals(request.getDevice().getOs())) {
            device.setUuid(request.getDevice().getImei());//设备唯一标识
            device.setOs(Integer.valueOf(request.getDevice().getOs()));
            device.setBrand(request.getDevice().getMake());//手机品牌
        } else if ("1".equals(request.getDevice().getOs())) {
            device.setUuid(request.getDevice().getIdfa());//设备唯一标识
            device.setOs(Integer.valueOf(request.getDevice().getOs()));
            device.setBrand("apple");//手机品牌
        }
        device.setOsv(request.getDevice().getOsv());//用户设备的操作系统版本号
        device.setOsc(28);//Android 系统 API level
        device.setModel(request.getDevice().getModel());//用户设备型号
        device.setOaid(request.getDevice().getOaid());//移动安全联盟推出的匿名设备标识符。Android10 及以上必需
        device.setImsi("460020084725173");//如果没有完整的 imsi,可以截取 imsi 的前五位
        if ("phone".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(2);//手机
        } else if ("ipad".equals(request.getDevice().getDevicetype())) {
            device.setDeviceType(1);//平板
        }else {
            device.setDeviceType(0);//用户设备类型。取值：0=未知；1=平板；2=手机；
        }
        device.setAndroidId(request.getDevice().getAndroid_id());//Android Id
        device.setMac(request.getDevice().getMac());//mac 地址，安卓设备必填
        device.setSw(request.getDevice().getW());//用户设备屏幕的宽度，以像素为单位
        device.setSh(request.getDevice().getH());//用户设备屏幕的高度，以像素为单位
        device.setDpi(480.0);//用户设备屏幕每英寸有多少个像素
        device.setSsid(null);//无线网 ssid 名称
        device.setWifiMac(null);//WIFI 路由器 MAC 地址
        device.setRomVersion(null);//手机 ROM 版本
        device.setSysComplingTime(null);//系统编译时间:时间戳
        device.setHms(null);//华为手机需要 HMS Core 版本号
        device.setAsv(null);//应用市场版本号
        device.setDst(0);//设备启动时间
        device.setDnm(0);//设备名称的 MD5 值
        device.setDms(0);//物理内存
        device.setDhd(0);//硬盘大小
        device.setSut(0);//系统更新时间
        device.setTz(null);//时区
        bidRequest.setDevice(device);

        YQNetwork network = new YQNetwork();
        network.setIp(request.getDevice().getIp());//客户端真实 IP
        if (0 == request.getDevice().getConnectiontype()) {
            network.setNetwork("未知");//设备的网络类型:Unknown=0
        } else if (2 == request.getDevice().getConnectiontype()) {
            network.setNetwork("wifi");//设备的网络类型:UWifi=1
        } else if (4 == request.getDevice().getConnectiontype()) {
            network.setNetwork("2g");//设备的网络类型:2G=2
        } else if (5 == request.getDevice().getConnectiontype()) {
            network.setNetwork("3g");//设备的网络类型:3G=3
        } else if (6 == request.getDevice().getConnectiontype()) {
            network.setNetwork("4g");//设备的网络类型:4G=4
        } else if (7 == request.getDevice().getConnectiontype()) {
            network.setNetwork("5g");//设备的网络类型:5G=5
        }

        if ("0".equals(request.getDevice().getCarrier())) {
            network.setCarrier(0);//未知
        } else if ("70120".equals(request.getDevice().getCarrier())) {
            network.setCarrier(1);//中国移动
        } else if ("70123".equals(request.getDevice().getCarrier())) {
            network.setCarrier(2);//中国联通
        } else if ("70121".equals(request.getDevice().getCarrier())) {
            network.setCarrier(4);//中国电信
        }
        network.setUa(request.getDevice().getUa());//User-Agent
        bidRequest.setNetwork(network);

        YQAdSpace space = new YQAdSpace();
        if (null != request.getImp()) {
            for (int i = 0; i < request.getImp().size(); i++) {
                space.setSlotId(request.getAdv().getTag_id());//广告位id
            }
        }

        bidRequest.setAdspace(space);

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求益起参数" + JSONObject.parseObject(content));
        String url = "http://api.17admob.com/getad/"+request.getAdv().getTag_id();
        String ua = request.getDevice().getUa();//ua
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
        log.info("请求上游益起花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info("益起返回参数" + JSONObject.parseObject(response));
        if (0 == JSONObject.parseObject(response).getInteger("code")) {
            id = request.getId();
            List<TzSeat> seatList = new ArrayList<>();
            TzSeat tzSeat = new TzSeat();
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();

            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            if (jo.getInteger("code") == 0) {
                JSONArray data = jo.getJSONArray("ads");
                TzBid tb = new TzBid();
                List<TzBid> bidList = new ArrayList<>();
                List<TzImage> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    tb.setTitle(data.getJSONObject(i).getString("title"));//广告标题
                    tb.setDesc(data.getJSONObject(i).getString("desc"));//广告描述
                    tb.setAdLogo(data.getJSONObject(i).getString("logo"));//“广告”字样的 logo 图标
                    tb.setAic(data.getJSONObject(i).getString("icon"));//广告图标地址
                    tb.setClick_url(data.getJSONObject(i).getString("click"));//落地页地址
                    tb.setDeeplink_url(data.getJSONObject(i).getString("dplink"));//deeplink 地址，如果不为空则通过此链接唤醒应用。唤醒失败则调用 clickUrl 的地址

                    TzNative tzNative = new TzNative();
                    if (null!=request.getImp()) {
                        for (int c = 0; c < request.getImp().size(); c++) {
                            if ("1".equals(request.getImp().get(c).getAd_slot_type()) || "2".equals(request.getImp().get(c).getAd_slot_type())) {
                                tb.setAd_type(8);//信息流-广告素材类型
                                JSONArray img = data.getJSONObject(i).getJSONArray("imgs");
                                for (int g = 0; g < img.size(); g++){
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(img.get(g).toString());
                                    list.add(tzImage);
                                }
                                tzNative.setImages(list);
                                tb.setNATIVE(tzNative);
                            } else if ("3".equals(request.getImp().get(c).getAd_slot_type())) {
                                tb.setAd_type(5);//开屏-广告素材类型
                                JSONArray img = data.getJSONObject(i).getJSONArray("imgs");
                                for (int g = 0; g < img.size(); g++){
                                    TzImage tzImage = new TzImage();
                                    tzImage.setUrl(img.get(g).toString());
                                    list.add(tzImage);
                                }
                                tb.setImages(list);
                            }
                        }
                    }

                    TzBidApps apps = new TzBidApps();
                    apps.setApp_name(data.getJSONObject(i).getString("app_name"));//下载类广告应用名称
                    apps.setBundle(data.getJSONObject(i).getString("pkg"));//下载类广告应用包名如果本地已安装，则直接打开 APP，并上报打开 APP(激活)监测
                    tb.setApp(apps);

                    if (data.getJSONObject(i).getInteger("action") == 1) {
                        tb.setClicktype("3");//点击类型：下载
                    } else if (data.getJSONObject(i).getInteger("action") == 2) {
                        tb.setClicktype("1");//点击类型：跳转
                    }

                    JSONArray tracks = data.getJSONObject(i).getJSONArray("tracks");
                    for (int g = 0; g < tracks.size(); g++) {
                        if (tracks.getJSONObject(g).getString("track_type").equals("show")) {
                            List<String> checkViews = new ArrayList<>();//曝光监测地址数组
                            JSONArray murl = tracks.getJSONObject(g).getJSONArray("urls");
                            if (null != murl) {
                                String pv = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_VIEWS%%");
                                tzMacros.setValue(Base64.encode(pv));
                                tzMacros1.add(tzMacros);
                                checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                                for (int m = 0; m < murl.size(); m++) {
                                    checkViews.add(murl.get(m).toString());
                                }
                                tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                            }
                        } else if (tracks.getJSONObject(g).getString("track_type").equals("click")) {
                            List<String> checkClicks = new ArrayList<>();
                            JSONArray cmurl = tracks.getJSONObject(g).getJSONArray("urls");//点击监测地址数组
                            if (null != cmurl) {
                                String checkClicksdd = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_CLICKS%%");
                                tzMacros.setValue(Base64.encode(checkClicksdd));
                                tzMacros1.add(tzMacros);
                                checkClicks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                                for (int cm = 0; cm < cmurl.size(); cm++) {
                                    checkClicks.add(cmurl.get(cm).toString());
                                }
                                tb.setCheck_clicks(checkClicks);
                            }
                        } else if (tracks.getJSONObject(g).getString("track_type").equals("download_start")) {
                            List<String> checkStartDownloads = new ArrayList<>();
                            JSONArray dmurl = tracks.getJSONObject(g).getJSONArray("urls");//app 下载开始的监测地址
                            if (null != dmurl) {
                                String dmurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_START%%");
                                tzMacros.setValue(Base64.encode(dmurls));
                                tzMacros1.add(tzMacros);
                                checkStartDownloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_START%%");
                                for (int cm = 0; cm < dmurl.size(); cm++) {
                                    checkStartDownloads.add(dmurl.get(cm).toString());
                                }
                                tb.setCheck_start_downloads(checkStartDownloads);//曝光监测URL,支持宏替换第三方曝光监测
                            }
                            //tb.setCheck_start_downloads(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//下载开始监测
                        } else if (tracks.getJSONObject(g).getString("track_type").equals("download_end")) {
                            List<String> checkEndDownloads = new ArrayList<>();
                            JSONArray downsuccessurl = tracks.getJSONObject(g).getJSONArray("urls");//app 下载完成的监测地址
                            if (null != downsuccessurl) {
                                String downsuccessurls = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_SUCCESS%%");
                                tzMacros.setValue(Base64.encode(downsuccessurls));
                                tzMacros1.add(tzMacros);
                                checkEndDownloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/end?downloadEnd=%%DOWN_SUCCESS%%");
                                for (int cm = 0; cm < downsuccessurl.size(); cm++) {
                                    checkEndDownloads.add(downsuccessurl.get(cm).toString());
                                }
                                tb.setCheck_end_downloads(checkEndDownloads);
                            }
                            //tb.setCheck_end_downloads(JSONObject.parseArray(tracks.getJSONObject(g).getJSONArray("urls").toString(), String.class));//下载完成监测
                        } else if (tracks.getJSONObject(g).getString("track_type").equals("install_start")) {
                            List<String> check_start_installs = new ArrayList<>();
                            JSONArray check_start_installss = tracks.getJSONObject(g).getJSONArray("urls");
                            if (null != check_start_installss) {
                                String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
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
                        } else if (tracks.getJSONObject(g).getString("track_type").equals("install_end")) {
                            List<String> check_end_installs = new ArrayList<>();
                            JSONArray check_end_installss = tracks.getJSONObject(g).getJSONArray("urls");
                            if (null != check_end_installss) {
                                String iurl = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
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
                        } else if (tracks.getJSONObject(g).getString("track_type").equals("active")) {
                            List<String> check_activations = new ArrayList<>();
                            JSONArray check_activationss = data.getJSONObject(i).getJSONArray("urls");
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

                    }
                    tb.setMacros(tzMacros1);
                    bidList.add(tb);//
                    tzSeat.setBid(bidList);
                    seatList.add(tzSeat);


                }

                bidResponse.setId(id);//请求id
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info("益起总返回" + JSONObject.toJSONString(bidResponse));

            }
        }


        return bidResponse;
    }

}
