package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.zhongmeng.*;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.ZhongMengJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description 众盟
 * @Date 2022/4/7 9:48
 */
@Slf4j
@Service("zhongMengJsonService")
public class ZhongMengJsonServiceImpl implements ZhongMengJsonService {

    private static final String name = "zhongmeng";

    private static final String source = "众盟";

    @SneakyThrows
    @Override
    public TzBidResponse getZhongMengDataByJson(TzBidRequest request) {
        //ReqInfo对象
        ZhongMengReqInfo reqInfo = new ZhongMengReqInfo();
        reqInfo.setAccessToken(request.getAdv().getApp_id().replace(":","="));//应用分配的token(需众盟运营同学分配)
        reqInfo.setAdSlotId(request.getAdv().getTag_id());//分配的广告位ID(需众盟同学分配)-信息流

        //广告位基本信息
        ZhongMengAdSlotInfo adSlotInfo = new ZhongMengAdSlotInfo();
        adSlotInfo.setMimes("jpg,gif,png,mp4,img");//广 告 位 支 持 的 物 料 类 型 e.g. jpg/gif/png/mp4/webp/flv/swf/txt/icon/c 其中:txt 指文字链,icon指图文,c指富文本 纯图片素材请求可以等价设置mimes为 img
        adSlotInfo.setSlotWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));//广告位宽度 Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        adSlotInfo.setSlotHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));//广告位高度(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
        //视频时候必填
//        if (null != request.getImp().get(0).getVideo()){
            adSlotInfo.setMinDuration(1);//视频广告位允许最短时⻓(s)
            adSlotInfo.setMaxDuration(30);//视频广告位允许最大时⻓(s)
//        }

        //手机的相关配置
        ZhongMengMobileInfo mobileInfo = new ZhongMengMobileInfo();
        mobileInfo.setOsVersion(request.getDevice().getOsv());//操作系统版本 e.g. 6.0.2
        mobileInfo.setAppVersion(request.getApp().getVer());//软件应用版本 e.g. 3.2.9
        mobileInfo.setMobileModel(request.getDevice().getModel());// string 手机设备型号 e.g. xiaomi5
        mobileInfo.setVendor(request.getDevice().getMake());//手机设备厂商 e.g. XiaoMi
//        mobileInfo.setAppStoreVersion("v12.0.1");//应用商店版本号， 必填 不可 为空 （oppo 、vivo 、华为 等）
//        mobileInfo.setSysVersion("10.0");//ROM版本号
        //网络连接类型-取值范围：0:Unknown,1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G,7:5G
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            mobileInfo.setConnectionType(100);//网络连接类型 1:wifi 2:2g 3:3g 4:4g 5:5g 100:未知
        }else if("2".equals(connectiontype)){
            mobileInfo.setConnectionType(1);
        }else if("4".equals(connectiontype)){
            mobileInfo.setConnectionType(2);
        }else if("5".equals(connectiontype)){
            mobileInfo.setConnectionType(3);
        }else if("6".equals(connectiontype)){
            mobileInfo.setConnectionType(4);
        }else if("7".equals(connectiontype)){
            mobileInfo.setConnectionType(5);
        } else {
            mobileInfo.setConnectionType(100);
        }
        //运营商类型-中国移动:70120,中国联通:70123，中国电信:70121,广电:70122 其他:70124,未识别:0
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                mobileInfo.setOperatorType(1);//网络运营商类型 0:未知 1:中国移动 2: 电信 3:联通 99:其他
            }else if("70123".equals(request.getDevice().getCarrier())){
                mobileInfo.setOperatorType(3);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                mobileInfo.setOperatorType(2);//中国电信
            }else{
                mobileInfo.setOperatorType(99);//其他
            }
        }else{
            mobileInfo.setOperatorType(99);
        }
        //操作系统，0=>Android,1=>iOS
        String os = request.getDevice().getOs();//
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            mobileInfo.setOsType(0);//安卓
            mobileInfo.setImsi("");//安卓设备唯一标识(获取不到传空字符 串)
            mobileInfo.setImei(request.getDevice().getImei());//安卓设备的IMEI信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
            mobileInfo.setOaid(request.getDevice().getOaid());//安卓设备的oaid信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
            mobileInfo.setAndroidId(request.getDevice().getAndroid_id());//android，针对安卓设备时，该字段为必填项
            mobileInfo.setVerCodeOfHms("1.1");//HMS Core 版本号， 必填， 明文项
            mobileInfo.setMac(request.getDevice().getMac());//手机唯一标识
            if (request.getDevice().getMake().equals("oppo")){
                mobileInfo.setBookMark("");//
                mobileInfo.setUpdateMark("");//
            }
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            mobileInfo.setOsType(1);//ios
            mobileInfo.setIdfa(request.getDevice().getIdfa());//苹果idfa，针对苹果设备时，该字段为必填项，idfa、caid不能同时为空
            mobileInfo.setIdfv(request.getDevice().getIdfv());//苹果idfv，针对苹果设备时，该字段为必填项
            mobileInfo.setOpenUdid("");//苹果唯一标识(获取不到传空字符串)
        }
        //设备类型
        String devicetype = request.getDevice().getDevicetype();//设备类型，手机:phone, 平板:ipad, PC:pc,互联网电视:tv
        if("phone".equals(devicetype)){
            mobileInfo.setDeviceType(1);//设备类型:1:手机 2:平板 3:智能电视 4: 户外屏
        }else if("ipad".equals(devicetype)){
            mobileInfo.setDeviceType(2);
        }else if("pc".equals(devicetype)){
            mobileInfo.setDeviceType(3);
        } else{
            mobileInfo.setDeviceType(1);
        }
        mobileInfo.setDeny(2l);//屏幕密度(混合预算设置) e.g.2.0

        //当前网络连接信息
        ZhongMengNetworkInfo networkInfo = new ZhongMengNetworkInfo();
        networkInfo.setUa(request.getDevice().getUa());//系统webview的user-agent
        networkInfo.setIp(request.getDevice().getIp());//IP地址

        //定位数据
        ZhongMengCoordinateInfo coordinateInfo = new ZhongMengCoordinateInfo();
        coordinateInfo.setCoordinateType(2);//坐标类型0:全球卫星定位系统坐标 1:国家测绘局坐标系 2:百度坐标系 3. 高德坐标系 4. 腾讯坐标系 5. 谷歌坐标系 100.其他
        coordinateInfo.setLat(0);//纬度
        coordinateInfo.setLng(0);//经度

        //众盟总请求
        ZhongMengBidRequest bidRequest = new ZhongMengBidRequest();
        bidRequest.setReqInfo(reqInfo);//
        bidRequest.setAdSlotInfo(adSlotInfo);//
        bidRequest.setMobileInfo(mobileInfo);//
        bidRequest.setNetworkInfo(networkInfo);//
        bidRequest.setCoordinateInfo(coordinateInfo);//


        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求众盟广告参数"+JSONObject.parseObject(content));
        String url = "http://adalliance.zmeng123.com/zmtmobads/v5/getAd.do";
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
        log.info(request.getImp().get(0).getTagid() + "请求上游众盟广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid() + "众盟广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();////请求id

        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            String requestId = jo.getString("requestId");//请求id
            //code=0的时候成功返回，才做解析
            if (0 == jo.getInteger("errorCode")){
                List<TzBid> bidList = new ArrayList<>();
                JSONArray ja = jo.getJSONArray("ads");//广告信息-集合
                for (int i=0;i<ja.size();i++){
                    TzBid tb = new TzBid();

                    String adslotId = ja.getJSONObject(i).getString("adslotId");//广告位ID
                    String adKey = ja.getJSONObject(i).getString("adKey");//广告唯一标识
                    JSONArray materialMetas = ja.getJSONObject(i).getJSONArray("materialMetas");//物料单元
                    JSONArray adTracking = ja.getJSONObject(i).getJSONArray("adTracking");//广告监测数据
                    String deeplink = "";

                    for (int j=0;j<materialMetas.size();j++){
                        if (materialMetas.getJSONObject(j).getInteger("creativeType") == 3 || materialMetas.getJSONObject(j).getInteger("creativeType") == 100){//图文
                            List<TzImage> list = new ArrayList<>();//图片集合
                            JSONArray imageSrcs = materialMetas.getJSONObject(j).getJSONArray("imageSrcs");//图片数组
                            for (int k=0; k < imageSrcs.size();k++){
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(imageSrcs.get(k).toString());//图⽚地址，外⽹地址，多图时按顺序返回展示
                                tzImage.setW(materialMetas.getJSONObject(j).getInteger("materialWidth"));//图⽚宽度，图⽚物料的宽度，单位：像素
                                tzImage.setH(materialMetas.getJSONObject(j).getInteger("materialHeight"));//图⽚⾼度，图⽚物料的⾼度，单位：像素
                                list.add(tzImage);

                                if (request.getImp().get(0).getAd_slot_type().equals("1")){//信息流
                                    TzNative tzNative = new TzNative();
                                    tb.setAd_type(8);//信息流-广告素材类型
                                    tzNative.setTitle(materialMetas.getJSONObject(j).getString("title"));//广告标题
                                    tzNative.setDesc(materialMetas.getJSONObject(j).getString("desc"));//广告描述
                                    tzNative.setImages(list);
                                    tb.setNATIVE(tzNative);
                                }else {
                                    tb.setAd_type(5);//开屏-广告素材类型
                                    tb.setTitle(materialMetas.getJSONObject(j).getString("title"));//广告标题
                                    tb.setDesc(materialMetas.getJSONObject(j).getString("desc"));//广告描述
                                    tb.setImages(list);//其他类型素材-图片集合
                                }
                            }
                        }else if (materialMetas.getJSONObject(j).getInteger("creativeType") == 4){//视频
                            TzNative tzNative = new TzNative();
                            TzVideo tzVideo = new TzVideo();
                            tzVideo.setUrl(materialMetas.getJSONObject(j).getString("videoUrl"));
                            tzVideo.setW(materialMetas.getJSONObject(j).getInteger("materialWidth"));
                            tzVideo.setH(materialMetas.getJSONObject(j).getInteger("materialHeight"));

                            if (request.getImp().get(0).getAd_slot_type().equals("1")) {//信息流
//                                tzNative.setTitle(materialMetas.getJSONObject(j).getString("title"));//广告标题
//                                tzNative.setDesc(materialMetas.getJSONObject(j).getString("desc"));//广告描述
                                tzNative.setVideo(tzVideo);
                                tb.setNATIVE(tzNative);
                                tb.setAd_type(8);//信息流-视频素材
                            }else {
                                tb.setAd_type(5);//开屏-广告素材类型
                                tb.setTitle(materialMetas.getJSONObject(j).getString("title"));//广告标题
                                tb.setDesc(materialMetas.getJSONObject(j).getString("desc"));//广告描述
                                tb.setVideo(tzVideo);//其他类型素材-视频素材
                            }
                        }

                        //交互类型: 0:无动作,纯展示 1:使用浏览器打开网⻚ 2:下载应用 6:根据deeplink打开应用 100:未知
                        Integer action_type = materialMetas.getJSONObject(j).getInteger("interactionType");
                        if(1 == action_type){
                            tb.setClicktype("0");//点击
                        }else if(2 == action_type){
                            tb.setClicktype("4");//下载
                        }else if(6 == action_type){
                            tb.setClicktype("2");//拉活
                        }else{
                            tb.setClicktype("0");//点击
                        }

                        tb.setClick_url(materialMetas.getJSONObject(j).getString("landingUrl"));//广告落地页
                        deeplink = materialMetas.getJSONObject(j).getString("dpUrl");//deeplink

                    }


                    for (int l=0;l<adTracking.size();l++){
                        //监测类型 0:广告被点击 1:广告被展现 2:广告被关闭 3:广告加载 4:广告被跳过 100:视频开始播放 101:视频全屏
                        //102:视频播放结束 103:点击预览图播放视频 104:视频播放25% 105:视频播放50% 106:视频播放75% 107:视频静音播放 108:视频播放0%
                        //109:视频播放100% 111:视频播放暂停 1000:开始下载推广APP 1001:下载完成推广APP 1002:开始安装推广APP 1003:安装完成推广APP
                        //1004: 激 活 推 广 APP 10000:deeplink调起成功 10001 ：deeplink调起失败
                        Integer teType = adTracking.getJSONObject(l).getInteger("trackingEventType");

                        if (teType == 10000){//deeplink调起成功
                            //deeplink以及监测
                            if(StringUtils.isNotEmpty(deeplink)){
                                tb.setDeeplink_url(deeplink);//deeplink 唤醒地址deeplink 唤醒广告打开页面
                                List<String> deep_linkT = new ArrayList<>();
                                deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                                JSONArray tk_dp_success =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");//deeplink_url 不为空时，唤醒成功时监测
                                if (null != tk_dp_success){
                                    for (int dp = 0; dp < tk_dp_success.size(); dp++) {
                                        deep_linkT.add(tk_dp_success.get(dp).toString());
                                    }
                                }

                                String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DEEP_LINK%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }

                        }else if (teType == 10001){//deeplink调起失败
                            tb.setDeeplink_url(deeplink);//deeplink 唤醒地址deeplink 唤醒广告打开页面
                            List<String> deep_linkF = new ArrayList<>();
                            deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK_FAIL%%");
                            JSONArray tk_dp_fail =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");//deeplink_url 不为空时，唤醒失败时监测
                            if (null != tk_dp_fail){
                                for (int f = 0; f < tk_dp_fail.size(); f++) {
                                    deep_linkF.add(tk_dp_fail.get(f).toString());
                                }
                            }

                            String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_success_deeplinks(deep_linkF);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DEEP_LINK_FAIL%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);

                        }else if (teType == 1){//广告被展现
                            //曝光上报地址列表
                            if(null != adTracking.getJSONObject(l).getJSONArray("trackingUrls")){
                                List<String> check_views = new ArrayList<>();
                                check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                                JSONArray urls1 = adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                                for (int cv = 0; cv < urls1.size(); cv++) {
                                    String replace = urls1.get(cv).toString().replace("__ORIGINTIME__", endTime.toString());
                                    check_views.add(replace);
                                }
                                String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_VIEWS%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }else if (teType == 0){//广告被点击
                            //点击上报地址列表
                            if(null != adTracking.getJSONObject(l).getJSONArray("trackingUrls")){
                                List<String> clickList = new ArrayList<>();
                                JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                                clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                                for (int cc = 0; cc < urls1.size(); cc++) {
                                    String replace = urls1.get(cc).toString().replace("__ORIGINTIME__", endTime.toString());
                                    clickList.add(replace);
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%CHECK_CLICKS%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }else if (teType == 1000){//开始下载推广APP
                            //开始下载上报数组
                            JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                            if(null != urls1){
                                List<String> downLoadList = new ArrayList<>();
                                downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                                for (int dl = 0; dl < urls1.size(); dl++) {
                                    downLoadList.add(urls1.get(dl).toString());
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_start_downloads(downLoadList);//开始下载
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_LOAD%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }else if (teType == 1001){//下载完成推广APP
                            //下载完成上报数组
                            JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                            if(null != urls1){
                                List<String> downLoadDList = new ArrayList<>();
                                downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                                for (int dle = 0; dle < urls1.size(); dle++) {
                                    downLoadDList.add(urls1.get(dle).toString());
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_end_downloads(downLoadDList);//结束下载
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%DOWN_END%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }else if (teType == 1002){//开始安装推广APP
                            //开始安装上报数组
                            JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                            if(null != urls1){
                                List<String> installList = new ArrayList<>();
                                installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                                for (int ins = 0; ins < urls1.size(); ins++) {
                                    installList.add(urls1.get(ins).toString());
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tb.setCheck_start_installs(installList);//开始安装
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%INSTALL_START%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }else if (teType == 1003){//安装完成推广APP
                            //安装完成上报数组
                            JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                            if(null != urls1){
                                List<String> installEList = new ArrayList<>();
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
                        }else if (teType == 100){//视频开始播放
                            //视频开始播放追踪 url 数组
                            JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                            if(null != urls1){
                                List<String> voidStartList = new ArrayList<>();
                                voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                                for (int vs = 0; vs < urls1.size(); vs++) {
                                    voidStartList.add(urls1.get(vs).toString());
                                }

                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%VEDIO_START%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }else if (teType == 102){//视频播放结束
                            //视频播放完成追踪 url 数组
                            JSONArray urls1 =  adTracking.getJSONObject(l).getJSONArray("trackingUrls");
                            if(null != urls1){
                                List<String> voidEndList = new ArrayList<>();
                                voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                                for (int ve = 0; ve < urls1.size(); ve++) {
                                    voidEndList.add(urls1.get(ve).toString());
                                }
                                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                                tzMacros = new TzMacros();
                                tzMacros.setMacro("%%VEDIO_END%%");
                                tzMacros.setValue(Base64.encode(encode));
                                tzMacros1.add(tzMacros);
                            }
                        }
                    }

                    tb.setMacros(tzMacros1);
                    tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);

                    TzSeat seat = new TzSeat();//素材集合对象
                    seat.setBid(bidList);
                    seatList.add(seat);

                    bidResponse.setId(requestId);//请求id
                    bidResponse.setBidid(requestId);//广告主返回id 请求唯一标识符
                    bidResponse.setSeatbid(seatList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info(request.getImp().get(0).getTagid() + "众盟广告总返回"+JSONObject.toJSONString(bidResponse));

                }
            }
        }

        return bidResponse;
    }
}
