package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.yunjuhe.*;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.entity.json.response.yunjuhe.Feature;
import com.mk.adx.service.YunJuHeJsonService;
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
 * @Description 云聚合
 * @Date 2022/3/21 9:48
 */
@Slf4j
@Service("yunJuHeJsonService")
public class YunJuHeJsonServiceImpl implements YunJuHeJsonService {

    private static final String name = "yunjuhe";

    private static final String source = "云聚合";

    @SneakyThrows
    @Override
    public MkBidResponse getYunJuHeDataByJson(MkBidRequest request) {
        YunJuHeBidRequest yjh = new YunJuHeBidRequest();//总请求
        yjh.setVersion("0.0.1");//Api 版本号 0.0.1

        //App 信息
        YunJuHeApp app = new YunJuHeApp();
        app.setAppId(request.getAdv().getApp_id());
        app.setName(request.getApp().getName());
        app.setPackage(request.getApp().getBundle());
        app.setVersion(request.getApp().getVer());
        yjh.setApp(app);//

        //设备信息
        YunJuHeDevice device = new YunJuHeDevice();
        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            device.setOsType(1);//安卓
            device.setAdid(request.getDevice().getAndroid_id());//Android ID 手机唯一标识
            device.setAndroidIdMd5(request.getDevice().getAndroid_id_md5());//
            device.setVendor(request.getDevice().getMake());//设备厂商
            device.setImei(request.getDevice().getImei());//android Q 之前设备标识必填，Android 必传
            device.setImeiMd5(request.getDevice().getImei_md5());//
            device.setOaid(request.getDevice().getOaid());//Android Q 之后广告标识符，Android 必传
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOsType(2);//ios
            device.setIdfa(request.getDevice().getIdfa());
            device.setUdid(request.getDevice().getOpen_udid());//OS 设备的 OpenUDID,小于 ios 6 必传
        }else {
            device.setOsType(0);//未知

        }

        //设备类型
        if ("phone".equals(request.getDevice().getDevicetype())){
            device.setType(1);//手机
        }else if ("ipad".equals(request.getDevice().getDevicetype())){
            device.setType(2);//平板
        }else {
            device.setType(9);//其他
        }

        //系统版本号
        if (StringUtils.isNotEmpty(request.getDevice().getOsv())){
            device.setOsVersion(request.getDevice().getOsv());
        }else {
            device.setOsVersion("4.3.3");
        }
        device.setLanguage(request.getDevice().getLanguage());//系统语言
        device.setModel(request.getDevice().getModel());//设备型号，系统原始值，不要做修改
        device.setBrand(request.getDevice().getMake());//设备品牌
        device.setWidth(request.getDevice().getW());//设备屏幕宽度
        device.setHeight(request.getDevice().getH());//设备屏幕高度
        device.setDensity(Float.parseFloat(String.valueOf(request.getDevice().getDeny())) );//每英寸像素,获取方法
        device.setOrientation(0);//屏幕方向 0：unknown 1：竖屏 2：横屏
        if(null != request.getDevice().getPpi()){
            device.setScreenDpi(request.getDevice().getPpi());//设备屏幕像素密度，如：160
        }else{
            device.setScreenDpi(160);//设备屏幕像素密度，如：160
        }
        device.setScreenSize("5.5");//屏幕尺寸 例:4.7 , 5.5 单位:英寸
        device.setBssid(request.getDevice().getMac());//所连接的 WIFI 设备的 MAC 地址 ,路由器 WIFI 的MAC 地址
        device.setIsSupportDp(true);//是否支持 DP
        yjh.setDevice(device);//

        //Network 对象
        YunJuHeNetwork network = new YunJuHeNetwork();
        network.setIp(request.getDevice().getIp());//客户端 ip 地址，若是服务器请求，必填
        network.setIpv6(request.getDevice().getIpv6());//ipv6 版本，与 ip 一起必须存在一个有效值
        network.setMac(request.getDevice().getMac());//mac 地址
        //网络连接类型
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            network.setNet(0);
        }else if("2".equals(connectiontype)){
            network.setNet(1);
        }else if("4".equals(connectiontype)){
            network.setNet(2);
        }else if("5".equals(connectiontype)){
            network.setNet(3);
        }else if("6".equals(connectiontype)){
            network.setNet(4);
        }else if("7".equals(connectiontype)){
            network.setNet(5);
        } else {
            network.setNet(0);
        }
        //运营商类型
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                network.setCarrier(1);//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                network.setCarrier(3);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                network.setCarrier(2);//中国电信
            }
        }else{
            network.setCarrier(0);
        }
        network.setUa(request.getDevice().getUa());//客户端 UA
        network.setCountry(request.getDevice().getCountry());//国家，使用 ISO-3166-1 Alpha-3
        network.setMcc("460");//移动国家码,如:460
        network.setMnc("00");//移动网络码,如:00
        yjh.setNetwork(network);//

        //Imp 对象
        YunJuHeImp imp = new YunJuHeImp();
        imp.setPosId(request.getAdv().getTag_id());//广告位 id

        imp.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));//广告位宽度
        imp.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));//广告位高度
        List<Integer> typeList = new ArrayList<>();
        typeList.add(0);
        imp.setOpType(typeList);//广告操作类型0：无限制 1:app 下载 2:H5 (在 app 内 webview 打开目标链接) 3:Deeplink 4:电话广告 5：广点通下载广告,6 微信小程序拉起 7.广点通跳转 8.浏览器打开目标链接
        yjh.setImp(imp);



        //总返回
        MkBidResponse bidResponse = new MkBidResponse();
        String content = JSONObject.toJSONString(yjh);
        log.info("请求云聚合广告参数"+JSONObject.parseObject(content));
        String url = "http://union.ad.yunjuhe.cn/Public/ad";
        String ua = request.getDevice().getUa();//ua
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数
        pud.setHeaderYunJuHe("gzip");//上游名称

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        log.info("请求上游云聚合广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("云聚合广告返回参数"+JSONObject.parseObject(response));

        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        //code=0的时候成功返回，才做解析
        if (0 == jo.getInteger("error")){
            JSONObject jj = jo.getJSONObject("data");//广告信息
            String errmsg = jo.getString("errmsg");
            if (null!=jj || !"".equals(jj.toJSONString()) || !"".equals(errmsg)){
                String rid = jj.getString("rid");//请求id
                JSONObject adInfo = jj.getJSONObject("adInfo");//广告数据
                if (null!=jj.getInteger("status")&&0==jj.getInteger("status")){
                    List<MkBid> bidList = new ArrayList<>();
                    MkBid tb = new MkBid();
                    //区分信息流和开屏等其他类型广告
                    List<MkImage> list = new ArrayList<>();
                    if (null!=request.getImp()) {
                        for (int j = 0; j < request.getImp().size(); j++) {
                            JSONArray features = adInfo.getJSONObject("material").getJSONArray("features");//素材详情
                            List<Feature> imgs = JSONObject.parseArray(features.toJSONString(), Feature.class);
                            Integer type = adInfo.getJSONObject("material").getInteger("type");//广告素材总体类型:1 视频 2 单图 3 多图 4 html 文本 5 音频
                            MkVideo tzVideo = new MkVideo();//视频素材
                            MkImage tzImage = new MkImage();//图片素材
                            for (int g = 0; g < imgs.size(); g++) {
                                Integer imgType = imgs.get(g).getType();//广告素材类型 1.视频 2.图片 3 文本 4 html
                                if (type == 2) {//单图
                                    if (imgType == 2) {
                                        tzImage.setUrl(imgs.get(g).getMaterialUrl());//素材的 URL
                                        tzImage.setW(imgs.get(g).getWidth());//素材宽
                                        tzImage.setH(imgs.get(g).getHeight());//素材高
                                        list.add(tzImage);//信息流素材-图片集合
                                    }
                                } else if (type == 1) {//视频
                                    if (imgType == 1) {
                                        tzVideo.setUrl(imgs.get(g).getMaterialUrl());//素材的 URL
                                        tzVideo.setW(imgs.get(g).getVWidth());//素材宽
                                        tzVideo.setH(imgs.get(g).getVHeight());//素材高
                                    }
                                }
                            }
                            tb.setTitle(adInfo.getString("title"));//广告标题
                            tb.setDesc(adInfo.getString("desc"));//广告描述
                            if ("1".equals(request.getImp().get(0).getSlot_type()) || "2".equals(request.getImp().get(0).getSlot_type())) {//信息流或banner
                                tb.setAd_type(1);//原生-广告素材类型
                            }else {
                                tb.setAd_type(2);//开屏-广告素材类型
                            }
                            if (type == 1) {
                                tb.setVideo(tzVideo);//视频
                            } else {
                                tb.setImages(list);//图片集合
                            }
                        }
                    }
                    Integer opType = adInfo.getInteger("opType");//广告操作类型1:app 下载 2:H5(在 app 内 webview 打开目标链接) 3:Deeplink 4:电话广告 5：广点通下载广告,6 微信小程序拉起 7.广点通跳转 8.浏览器打开目标链接
                    if(2 == opType){
                        tb.setClicktype("0");//点击
                    }else if(1 == opType){
                        tb.setClicktype("4");//下载
                    }else if(5 == opType){
                        tb.setClicktype("3");//,二次下载类广告(广点通特有)
                    } else if(3 == opType){
                        tb.setClicktype("2");//拉活-deeplink
                    }else{
                        tb.setClicktype("0");//点击
                    }


                    String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

                    tb.setClick_url(adInfo.getJSONObject("conversion").getString("appUrl")); // 点击跳转url地址

                    tb.setDeeplink_url(adInfo.getJSONObject("conversion").getString("deeplinkUrl"));//deeplink 唤醒地址deeplink 唤醒广告打开页面


                    JSONArray tk_dp_try = adInfo.getJSONObject("convUrls").getJSONArray("dplinkTry");//deeplink被点击(尝试唤起)，如果为空，无需上报
                    JSONArray tk_dp_fail = adInfo.getJSONObject("convUrls").getJSONArray("dplinkFail");//deeplink_url 不为空时，唤醒失败时监测
//                        for (int t = 0; t < tk_dp_try.size(); t++) {
//                            deep_linkT.add(tk_dp_try.get(t).toString());
//                        }

                    JSONArray tk_dp_success = adInfo.getJSONObject("convUrls").getJSONArray("dplink");//deeplink_url 不为空时，唤醒成功时监测
                    if(null != tk_dp_success){
                        List<String> deep_linkT = new ArrayList<>();
                        deep_linkT.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                        for (int dp = 0; dp < tk_dp_success.size(); dp++) {
                            deep_linkT.add(tk_dp_success.get(dp).toString());
                        }
                        tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
                    }


                    //展示监测
                    if(null != adInfo.getJSONArray("showUrls")){
                        List<String> check_views = new ArrayList<>();
                        check_views.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                        JSONArray urls1 = adInfo.getJSONArray("showUrls");
                        for (int cv = 0; cv < urls1.size(); cv++) {
                            String replace = "";
                            if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {//android
                                if (null!=request.getDevice().getImei()){
                                    replace = urls1.get(cv).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__IMEI__", request.getDevice().getImei()).replace("TS",System.currentTimeMillis()+"");
                                }else if (null!=request.getDevice().getOaid()){
                                    replace = urls1.get(cv).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__OAID__", request.getDevice().getOaid()).replace("TS",System.currentTimeMillis()+"");
                                }
                            }else {
                                replace = urls1.get(cv).toString().replace("MAC", request.getDevice().getMac()).replace("__IDFA__",request.getDevice().getIdfa()).replace("TS",System.currentTimeMillis()+"");
                            }
                            check_views.add(replace);
                        }
                        tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    }

                    //点击监测
                    if(null != adInfo.getJSONArray("clickUrls")){
                        List<String> clickList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONArray("clickUrls");
                        clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                        for (int cc = 0; cc < urls1.size(); cc++) {
                            String replace = "";
                            if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {//android
                                if (null!=request.getDevice().getImei()){
                                    replace = urls1.get(cc).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__IMEI__", request.getDevice().getImei()).replace("TS",System.currentTimeMillis()+"");
                                }else if (null!=request.getDevice().getOaid()){
                                    replace = urls1.get(cc).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__OAID__", request.getDevice().getOaid()).replace("TS",System.currentTimeMillis()+"");
                                }
                            }else {
                                replace = urls1.get(cc).toString().replace("MAC", request.getDevice().getMac()).replace("__IDFA__",request.getDevice().getIdfa()).replace("TS",System.currentTimeMillis()+"");
                            }

                            clickList.add(replace);
                        }
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    }

                    //开始下载上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("dlBegin")){
                        List<String> downLoadList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("dlBegin");
                        downLoadList.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                        for (int dl = 0; dl < urls1.size(); dl++) {
                            downLoadList.add(urls1.get(dl).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        tb.setCheck_start_downloads(downLoadList);//开始下载
                    }

                    //下载完成上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("dlEnd")){
                        List<String> downLoadDList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("dlEnd");
                        downLoadDList.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode));
                        for (int dle = 0; dle < urls1.size(); dle++) {
                            downLoadDList.add(urls1.get(dle).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        tb.setCheck_end_downloads(downLoadDList);//结束下载
                    }

                    //开始安装上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("isBegin")){
                        List<String> installList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("isBegin");
                        installList.add("http://adx.fxlxz.com/sl/in_start?installStart=" + Base64.encode(encode));
                        for (int ins = 0; ins < urls1.size(); ins++) {
                            installList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        tb.setCheck_start_installs(installList);//开始安装
                    }

                    //安装完成上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("isEnd")){
                        List<String> installEList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("isEnd");
                        installEList.add("http://adx.fxlxz.com/sl/in_end?installEnd=" + Base64.encode(encode));
                        for (int ins = 0; ins < urls1.size(); ins++) {
                            installEList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        tb.setCheck_end_installs(installEList);//安装完成
                    }

                    //视频开始播放追踪 url 数组
                    List<MkCheckVideoUrls> videourls = new ArrayList();
                    MkCheckVideoUrls mkCheckVideoUrls = new MkCheckVideoUrls();
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("pyBegin")){
                        List<String> voidStartList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("pyBegin");
                        voidStartList.add("http://adx.fxlxz.com/sl/v_start?vedioStart=" + Base64.encode(encode));
                        for (int vs = 0; vs < urls1.size(); vs++) {
                            voidStartList.add(urls1.get(vs).toString());
                        }
                        mkCheckVideoUrls.setTime(0);
                        mkCheckVideoUrls.setUrl(voidStartList);
                        videourls.add(mkCheckVideoUrls);
                    }

                    //视频播放完成追踪 url 数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("pyEnd")){
                        List<String> voidEndList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("pyEnd");
                        voidEndList.add("http://adx.fxlxz.com/sl/v_end?vedioEnd=" + Base64.encode(encode));
                        for (int ve = 0; ve < urls1.size(); ve++) {
                            voidEndList.add(urls1.get(ve).toString());
                        }
                        mkCheckVideoUrls.setTime(1);
                        mkCheckVideoUrls.setUrl(voidEndList);
                        videourls.add(mkCheckVideoUrls);
                    }

                    tb.setCheck_video_urls(videourls);//视频

                    bidList.add(tb);
                    bidResponse.setId(id);//请求id
                    bidResponse.setBidid(jo.getString("unique"));//广告主返回id 请求唯一标识符
                    bidResponse.setSeatbid(bidList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info("云聚合广告总返回"+JSONObject.toJSONString(bidResponse));
                }
            }

        }

        return bidResponse;
    }
}
