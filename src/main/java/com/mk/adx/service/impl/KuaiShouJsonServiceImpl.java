package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.kuaishou.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.KuaiShouJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description 快手
 * @Date 2022/5/5 9:48
 */
@Slf4j
@Service("kuaiShouJsonService")
public class KuaiShouJsonServiceImpl implements KuaiShouJsonService {

    private static final String name = "kuaishou";

    private static final String source = "快手";

    @SneakyThrows
    @Override
    public TzBidResponse getKuaiShouDataByJson(TzBidRequest request) {

        //媒体App信息
        KuaiShouAppInfo appInfo = new KuaiShouAppInfo();
        appInfo.setAppId("");//媒体appid唯一标识，由快手平台分配
        appInfo.setName(request.getAdv().getApp_name());//媒体名称
        appInfo.setPackageName(request.getAdv().getBundle());//媒体包名


        //媒体广告位信息
        List<KuaiShouImpInfo> impInfo = new ArrayList();
        KuaiShouImpInfo imp = new KuaiShouImpInfo();
//        imp.setPosId(Integer.parseInt(request.getAdv().getTag_id()));//广告位 id，由快手平台分配
        imp.setPosId(000);//广告位 id，由快手平台分配
        imp.setAdNum(1);//该广告位请求的广告数，选填，默认为1，信息流场景最大支持一次请求5条广告
        //广告位类型：1:信息流 2:banner 3:开屏 4:视频 5:横幅6:插屏 7:暂停 8:贴片
        String ad_slot_type = request.getImp().get(0).getAd_slot_type();
        int nw = request.getImp().get(0).getNATIVE().getW();//信息流广告位宽
        int nh = request.getImp().get(0).getNATIVE().getH();//信息流广告位高
        int bw = request.getImp().get(0).getBanner().getW();//banner广告位宽
        int bh = request.getImp().get(0).getBanner().getH();//banner广告位高
        int vw = request.getImp().get(0).getVideo().getW();//视频广告位宽
        int vh = request.getImp().get(0).getVideo().getH();//视频广告位高
        if(ad_slot_type.equals("1")){//
            imp.setWidth(nw);//宽
            imp.setHeight(nh);//高
            imp.setAdStyle(1);//1：信息流、2：激励视频、3：全屏 视频、4：开屏、6：draw 视频
        }else if (ad_slot_type.equals("2")||ad_slot_type.equals("3")||ad_slot_type.equals("5")||ad_slot_type.equals("6")){
            imp.setWidth(bw);//宽
            imp.setHeight(bh);//高
            imp.setAdStyle(4);//1：信息流、2：激励视频、3：全屏 视频、4：开屏、6：draw 视频
        }else if (ad_slot_type.equals("4")){
            imp.setWidth(vw);//宽
            imp.setHeight(vh);//高
            imp.setAdStyle(2);//1：信息流、2：激励视频、3：全屏视频、4：开屏、6：draw视频
        }
        impInfo.add(imp);//


        //设备信息
        KuaiShouDeviceInfo deviceInfo = new KuaiShouDeviceInfo();
        //操作系统，0=>Android,1=>iOS
        String os = request.getDevice().getOs();//
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            deviceInfo.setOsType(1);//安卓
            deviceInfo.setImei(request.getDevice().getImei());//安卓设备的IMEI信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
            deviceInfo.setImeiMd5(request.getDevice().getImei_md5());//md5 加密后的 imei 号，建议尽量填写，如果不填写会影响流量变现效 率，imei 和 imeiMd5 至少填写一个，建议优先填写 imei
            deviceInfo.setOaid(request.getDevice().getOaid());//安卓设备的oaid信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
            deviceInfo.setAndroidId(request.getDevice().getAndroid_id());//android，针对安卓设备时，该字段为必填项
            deviceInfo.setAndroidIdMd5(request.getDevice().getAndroid_id_md5());//
            deviceInfo.setDeviceVendor(request.getDevice().getMake());//设备厂商。1) android 设备： 可调用系统接 口 android.os.Build.MANUFACTURER 直接获得。如果获取不到，填写 unknown( 小写）。2) ios 设备：无需填写。mobileInfo.setMac(request.getDevice().getMac());//手机唯一标识
            //设备类型
            String devicetype = request.getDevice().getDevicetype();//设备类型，手机:phone, 平板:ipad, PC:pc,互联网电视:tv
            if("phone".equals(devicetype)){
                deviceInfo.setPlatform(3);//平台类型,1:iphone,2:ipad,3:android_phone,4:android_pad
            }else if("ipad".equals(devicetype)){
                deviceInfo.setPlatform(4);
            } else{
                deviceInfo.setPlatform(3);
            }
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            deviceInfo.setOsType(2);//ios
            deviceInfo.setIdfa(request.getDevice().getIdfa());//苹果idfa，针对苹果设备时，该字段为必填项，idfa、caid不能同时为空
            //设备类型
            String devicetype = request.getDevice().getDevicetype();//设备类型，手机:phone, 平板:ipad, PC:pc,互联网电视:tv
            if("phone".equals(devicetype)){
                deviceInfo.setPlatform(1);//平台类型,1:iphone,2:ipad,3:android_phone,4:android_pad
            }else if("ipad".equals(devicetype)){
                deviceInfo.setPlatform(2);
            } else{
                deviceInfo.setPlatform(1);
            }
        }
        deviceInfo.setOsVersion(request.getDevice().getOsv());//操作系统版本号
        deviceInfo.setLanguage(request.getDevice().getLanguage());//系统语言
        deviceInfo.setScreenWidth(request.getDevice().getW());//手机屏幕宽度
        deviceInfo.setScreenHeight(request.getDevice().getH());//手机屏幕高度
        deviceInfo.setDeviceModel(request.getDevice().getModel());//设备型号，系统原始值，不要做修改。例：ios：iPhone9,1 android：vivo y83a、mi 8
        deviceInfo.setDeviceBrand(request.getDevice().getMake());//设备品牌
        deviceInfo.setDeviceNameMd5("");//设备名称的 MD5
        deviceInfo.setPhysicalMemoryKBytes(16777216);//物理内存 单位 KB
        deviceInfo.setHardDiskSizeKBytes(67108864);//硬盘大小 单位 KB
        deviceInfo.setCountry("CHN");//国家，中国：CHN
        deviceInfo.setTimeZone("GMT+0800");//时区 北京时间 "GMT+0800"


        //网络状态信息
        KuaiShouNetworkInfo networkInfo = new KuaiShouNetworkInfo();
        networkInfo.setIp(request.getDevice().getIp());//客户端公网 ip 地址，服务端通过 API接入必须填写，不可以填服务端地 址；如果是客户端直接通过 API 接口 请求广告，不要填写这个字段
        networkInfo.setMac(request.getDevice().getMac());//设备 mac 地址
        //网络连接类型-取值范围：0:Unknown,1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G,7:5G
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            networkInfo.setConnectionType(0);//0: 无法探测; 1:蜂窝数据接入，未知网络类型; 2: 蜂窝数据2G网络; 3: 蜂窝数据3G网 络; 4: 蜂窝数据4G网络; 5: 蜂窝数据5G网络; 6: LTE网络; 100: Wi-Fi 网 络接入; 101: 以太网接入
        }else if("2".equals(connectiontype)){
            networkInfo.setConnectionType(100);
        }else if("4".equals(connectiontype)){
            networkInfo.setConnectionType(2);
        }else if("5".equals(connectiontype)){
            networkInfo.setConnectionType(3);
        }else if("6".equals(connectiontype)){
            networkInfo.setConnectionType(4);
        }else if("7".equals(connectiontype)){
            networkInfo.setConnectionType(5);
        } else {
            networkInfo.setConnectionType(0);
        }
        //运营商类型-中国移动:70120,中国联通:70123，中国电信:70121,广电:70122 其他:70124,未识别:0
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                networkInfo.setOperatorType(1);//运营商类型，选填 0: 未知运营商; 1:中国移动; 2: 中国电信; 3: 中国联通;99: 其他运营商
            }else if("70123".equals(request.getDevice().getCarrier())){
                networkInfo.setOperatorType(3);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                networkInfo.setOperatorType(2);//中国电信
            }else{
                networkInfo.setOperatorType(99);//其他
            }
        }else{
            networkInfo.setOperatorType(99);
        }


        //快手总请求
        KuaiShouBidRequest bidRequest = new KuaiShouBidRequest();
        bidRequest.setProtocolVersion("1.0");//协议版本号
        bidRequest.setAppInfo(appInfo);//媒体App信息
        bidRequest.setImpInfo(impInfo);//媒体广告位信息
        bidRequest.setDeviceInfo(deviceInfo);//设备信息
        bidRequest.setNetworkInfo(networkInfo);//网络状态信息

        //分割线---------------------------------------------------------------------------------

        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求快手广告参数"+JSONObject.parseObject(content));
        String url = "https://open.e.kuaishou.com/rest/e/v2/open/univ";
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
        log.info(request.getImp().get(0).getTagid() + "请求上游快手广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid() + "快手广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();////请求id

        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        //响应状态码，1代表成功，其它代表错误
        if (1 == jo.getInteger("result")){
            List<TzBid> bidList = new ArrayList<>();
            JSONArray ja = jo.getJSONArray("impAdInfo");//下发广告信息-集合
            for (int i=0;i<ja.size();i++){
                TzBid tb = new TzBid();

                int posId = ja.getJSONObject(i).getInteger("posId");//广告位ID
                JSONArray adInfos = ja.getJSONObject(i).getJSONArray("adInfo");//下发广告具体信息
                String deeplink = "";

                for (int j=0;j<adInfos.size();j++){
                    JSONObject adBaseInfo = adInfos.getJSONObject(j).getJSONObject("adBaseInfo");//广告基本信息
                    JSONObject adConversionInfo = adInfos.getJSONObject(j).getJSONObject("adConversionInfo");//广告操作相关信息
                    JSONObject adMaterialInfo = adInfos.getJSONObject(j).getJSONObject("adMaterialInfo");//广告素材信息
                    JSONObject downloadSafeInfo = adInfos.getJSONObject(j).getJSONObject("downloadSafeInfo");// app 下载类广告安全信息
                    JSONObject unDownloadConf = adInfos.getJSONObject(j).getJSONObject("unDownloadConf");//非下载类配置
                    JSONArray adTrackInfo = adInfos.getJSONObject(j).getJSONArray("adTrackInfo");//第三方监控信息
                    JSONObject adProductInfo = adInfos.getJSONObject(j).getJSONObject("adProductInfo");//广告商品信息
                    JSONObject adRewardInfo = adInfos.getJSONObject(j).getJSONObject("adRewardInfo");//激励视频的配置
                    JSONObject fullScreenVideoInfo = adInfos.getJSONObject(j).getJSONObject("fullScreenVideoInfo");//全屏广告配置信息
                    JSONObject adSplashInfo = adInfos.getJSONObject(j).getJSONObject("adSplashInfo");//开屏广告配置信息
                    JSONObject adinsertScreenInfo = adInfos.getJSONObject(j).getJSONObject("adinsertScreenInfo");//插屏广告配置信息

                    //adMaterialInfo--广告素材信息
                    int materialType = adMaterialInfo.getInteger("materialType");//广告素材总体类型 1：视频 2：单图 3：多图
                    JSONArray materialFeature = adMaterialInfo.getJSONArray("materialFeature");//具体素材信息-数组集合
                    List<TzImage> list = new ArrayList<>();//图片集合
                    for (int k=0; k < materialFeature.size();k++){
                        if (materialFeature.getJSONObject(k).getInteger("featureType") == 2){//图片
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(materialFeature.getJSONObject(k).getString("materialUrl"));//图⽚地址，外⽹地址，多图时按顺序返回展示
                            tzImage.setW(materialFeature.getJSONObject(k).getJSONObject("materialSize").getInteger("width"));//素材宽度
                            tzImage.setH(materialFeature.getJSONObject(k).getJSONObject("materialSize").getInteger("height"));//素材高度
                            list.add(tzImage);

                            if (request.getImp().get(0).getAd_slot_type().equals("1")){//信息流
                                TzNative tzNative = new TzNative();
                                tb.setAd_type(8);//信息流-广告素材类型
                                tzNative.setTitle(adBaseInfo.getString("adActionDescription"));//广告标题
                                tzNative.setDesc(adBaseInfo.getString("adDescription"));//广告描述
                                tzNative.setImages(list);
                                tb.setNATIVE(tzNative);
                            }else {
                                tb.setAd_type(5);//开屏-广告素材类型
                                tb.setTitle(adBaseInfo.getString("adActionDescription"));//广告标题
                                tb.setDesc(adBaseInfo.getString("adDescription"));//广告描述
                                tb.setImages(list);//其他类型素材-图片集合
                            }
                        }else if (materialFeature.getJSONObject(k).getInteger("featureType") == 1){//视频
                            TzNative tzNative = new TzNative();
                            TzVideo tzVideo = new TzVideo();
                            tzVideo.setUrl(materialFeature.getJSONObject(k).getString("materialUrl"));
                            tzVideo.setW(materialFeature.getJSONObject(k).getInteger("videoWidth"));
                            tzVideo.setH(materialFeature.getJSONObject(k).getInteger("videoHeight"));

                            if (request.getImp().get(0).getAd_slot_type().equals("1")) {//信息流
                                tzNative.setTitle(adBaseInfo.getString("adActionDescription"));//广告标题
                                tzNative.setDesc(adBaseInfo.getString("adDescription"));//广告描述
                                tzNative.setVideo(tzVideo);
                                tb.setNATIVE(tzNative);
                                tb.setAd_type(8);//信息流-视频素材
                            }else {
                                tb.setAd_type(5);//开屏-广告素材类型
                                tb.setTitle(adBaseInfo.getString("adActionDescription"));//广告标题
                                tb.setDesc(adBaseInfo.getString("adDescription"));//广告描述
                                tb.setVideo(tzVideo);//其他类型素材-视频素材
                            }
                        }
                    }


                    //广告操作类型 1：app 下载 2：H5
                    Integer action_type = adBaseInfo.getInteger("adOperationType");
                    if(1 == action_type){
                        tb.setClicktype("4");//点击
                    }

                    tb.setClick_url(adConversionInfo.getString("appDownloadUrl"));//广告落地页-广告app下载链接 针对app下载类广告
                    deeplink = adConversionInfo.getString("deeplinkUrl");//deeplink

                    //展示监测
                    String showUrl = adBaseInfo.getString("showUrl");
                    if(null != showUrl){
                        List<String> check_views = new ArrayList<>();
                        check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                        String replace = showUrl.replace("__MTS__", endTime.toString());
                        check_views.add(replace);
                        check_views.add(showUrl);

                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //点击监测
                    String clickUrl = adBaseInfo.getString("clickUrl");
                    if(null != clickUrl){
                        List<String> clickList = new ArrayList<>();
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        String replace = "";
                        if(ad_slot_type.equals("1")){//信息流
                            replace = clickUrl.replace("__WIDTH__", String.valueOf(nw)).replace("__HEIGHT__", String.valueOf(nh)).replace("__P_DURATION__", endTime.toString());
                        }else if (ad_slot_type.equals("2")||ad_slot_type.equals("3")||ad_slot_type.equals("5")||ad_slot_type.equals("6")){//banner
                            replace = clickUrl.replace("__WIDTH__", String.valueOf(bw)).replace("__HEIGHT__",String.valueOf(bh));
                        }else if (ad_slot_type.equals("4")){//视频
                            replace = clickUrl.replace("__WIDTH__", String.valueOf(vw)).replace("__HEIGHT__",String.valueOf(vh));
                        }
                        clickList.add(replace);
                        clickList.add(clickUrl);

                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //转化链路上报 url，客户端需要进行宏替换：
                    //将 __ACTION__ 宏替换为具体的行为，具体定义如
                    //下：
                    //30 : 开始下载
                    //31 : 下载完成
                    //32 : 安装完成
                    //33 : 下载暂停
                    //34 : 下载继续
                    //35 ：下载删除
                    //399 : 开始播放
                    //21：播放 3s（第一遍）
                    //22：播放 5s（第一遍）
                    //400 : 播放完成
                    //600 : winnotice(仅 RTB 场景) 3：广告关闭
                    //4 : 减少此类广告
                    //将 __PR__ 宏替换为二价计费后的计费价格（单位：
                    //分/千次展现, 整数格式）, 需要价格加密将
                    //__PRTYPE__宏替换为 encrypt, 加密算法见后
                    //广告关闭时将"__P_DURATION__"替换为用户点击
                    //广告时广告播放的时长
                    //广告关闭时将"__P_RATE__"替换为用户点击广告时
                    //播放的百分比
                    String convUrl = adBaseInfo.getString("convUrl");

                    //开始下载上报数组
                    List<String> downLoadList = new ArrayList<>();
                    downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                    String replace_ds = clickUrl.replace("__ACTION__", String.valueOf(30));
                    downLoadList.add(replace_ds);
                    downLoadList.add(convUrl);

                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_downloads(downLoadList);//开始下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_LOAD%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                    //下载完成上报数组
                    List<String> downLoadDList = new ArrayList<>();
                    downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                    String replace_de = clickUrl.replace("__ACTION__", String.valueOf(31));
                    downLoadList.add(replace_de);
                    downLoadDList.add(convUrl);

                    tb.setCheck_end_downloads(downLoadDList);//结束下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                    //安装完成上报数组
                    List<String> installEList = new ArrayList<>();
                    installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                    String replace_ie = clickUrl.replace("__ACTION__", String.valueOf(32));
                    downLoadList.add(replace_ie);
                    installEList.add(convUrl);

                    tb.setCheck_end_installs(installEList);//安装完成
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                    //视频开始播放追踪 url 数组
                    List<String> voidStartList = new ArrayList<>();
                    voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                    String replace_vs = clickUrl.replace("__ACTION__", String.valueOf(399));
                    downLoadList.add(replace_vs);
                    voidStartList.add(convUrl);

                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%VEDIO_START%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                    //视频播放完成追踪 url 数组
                    List<String> voidEndList = new ArrayList<>();
                    voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                    String replace_ve = clickUrl.replace("__ACTION__", String.valueOf(400));
                    downLoadList.add(replace_ve);
                    voidEndList.add(convUrl);

                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%VEDIO_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                }

                tb.setMacros(tzMacros1);
                tb.setImpid(request.getImp().get(0).getId());
                bidList.add(tb);

                TzSeat seat = new TzSeat();//素材集合对象
                seat.setBid(bidList);
                seatList.add(seat);

                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);//广告主返回id 请求唯一标识符
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info(request.getImp().get(0).getTagid() + "快手广告总返回"+JSONObject.toJSONString(bidResponse));

                }
            }

        return bidResponse;
    }
}
