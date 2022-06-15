package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.jialiang.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.JiaLiangJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description 佳量
 * @Date 2022/4/7 9:48
 */
@Slf4j
@Service("jiaLiangJsonService")
public class JiaLiangJsonServiceImpl implements JiaLiangJsonService {

    private static final String name = "jialiang";

    private static final String source = "佳量";

    @SneakyThrows
    @Override
    public TzBidResponse getJiaLiangDataByJson(TzBidRequest request) {
        //imp对象
        JiaLiangImp imp = new JiaLiangImp();
        imp.setAw(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));//本次流量售卖位置的真实宽度数值信息，单位：像素
        imp.setAh(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));//本次流量售卖位置的真实⾼度数值信息，单位：像素
        String ad_slot_type = request.getImp().get(0).getAd_slot_type();//天卓-广告位类型：1:信息流 2:banner 3:开屏 4:视频 5:横幅6:插屏 7:暂停 8:贴片
        if (ad_slot_type.equals("3")){
            imp.setAd_type(1);//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
        }else if (ad_slot_type.equals("2")){
            imp.setAd_type(2);//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
        }else if (ad_slot_type.equals("1")){
            imp.setAd_type(3);//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
        }else if (ad_slot_type.equals("6")){
            imp.setAd_type(4);//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
        }else if (ad_slot_type.equals("4")){
            imp.setAd_type(6);//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
        }

        //app对象
        JiaLiangApp app = new JiaLiangApp();
        app.setName(request.getAdv().getApp_name());//应⽤名称，流量应⽤名称
        app.setBundle(request.getAdv().getBundle());//应⽤包名 - 流量应⽤包名，例如：com.jialiangad.app

        //Geo对象
        JiaLiangGeo geo = new JiaLiangGeo();
        geo.setLat(Float.valueOf(0));//纬度，默认值0
        geo.setLong(Float.valueOf(0));//经度，默认值0
        geo.setCoordinate(0);//坐标系；0：未知；1：WGS84全球卫星定位系统坐标系；2：GCJ02国家测绘局坐标系；3：BD09百度坐标系


        //设备对象
        JiaLiangDevice device = new JiaLiangDevice();
        device.setUa(request.getDevice().getUa());//移动端访问时的User-Agent信息，务必是合理有效的浏览器代理信息
        device.setGeo(geo);//定位信息，⽬前仅⽀持中国⼤陆的地区，海外地区暂不⽀持
        device.setIpv4(request.getDevice().getIp());//pv4地址，公⽹IP地址，⼀定不能是内⽹IP，且该地址务必⼀定是移动客户端的⽹络地址，与ipv6不可同时为空
        device.setIpv6(request.getDevice().getIpv6());//ipv6地址，公⽹IP地址，⼀定不能是内⽹IP，且该地址务必⼀定是移动客户端的⽹络地址，与ipv4不可同时为空
        String devicetype = request.getDevice().getDevicetype();//设备类型，手机:phone, 平板:ipad, PC:pc,互联网电视:tv

        String os = request.getDevice().getOs();//操作系统，0=>Android,1=>iOS
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            device.setOs(2);//安卓
            device.setImei(request.getDevice().getImei());//安卓设备的IMEI信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
            device.setImei_md5(request.getDevice().getImei_md5());//
            device.setOaid(request.getDevice().getOaid());//安卓设备的oaid信息，针对安卓设备时，该字段为必填项，安卓设备 imei、oaid不能同时为空
            device.setOaid_md5(request.getDevice().getOaid_md5());//
            device.setAndroid(request.getDevice().getAndroid_id());//android，针对安卓设备时，该字段为必填项
            device.setAndroid_md5(request.getDevice().getAndroid_id_md5());//
            device.setMac(request.getDevice().getMac());//MAC地址，安卓务必要获取到，不然不建议发请求；例如：02:00:00:00:00:00

            //安卓-设备类型
            if("phone".equals(devicetype)){
                device.setDevice_type(6);//设备类型；0：未知；1：PC；2：苹果⼿机APP；3：苹果平板APP；4：苹果⼿机H5；5：苹果平板H5；6：安卓⼿机APP；7：安卓平板APP；8：安卓⼿机H5；9：安卓平板H5
            }else if("ipad".equals(devicetype)){
                device.setDevice_type(7);
            }else if("pc".equals(devicetype)){
                device.setDevice_type(1);
            } else{
                device.setDevice_type(0);
            }
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOs(1);//ios
            device.setIdfa(request.getDevice().getIdfa());//苹果idfa，针对苹果设备时，该字段为必填项，idfa、caid不能同时为空
            device.setIdfa_md5(request.getDevice().getIdfa_md5());//苹果idfa的md5值，针对苹果设备时，该字段为必填项
            device.setIdfv(request.getDevice().getIdfv());//苹果idfv，针对苹果设备时，该字段为必填项

            //ios-设备类型
            if("phone".equals(devicetype)){
                device.setDevice_type(2);//设备类型；0：未知；1：PC；2：苹果⼿机APP；3：苹果平板APP；4：苹果⼿机H5；5：苹果平板H5；6：安卓⼿机APP；7：安卓平板APP；8：安卓⼿机H5；9：安卓平板H5
            }else if("ipad".equals(devicetype)){
                device.setDevice_type(3);
            }else if("pc".equals(devicetype)){
                device.setDevice_type(1);
            } else{
                device.setDevice_type(0);
            }
        }else {
            device.setOs(5);//未知
        }
        device.setRh(request.getDevice().getH());//屏幕分辨率⾼度；例如：1080
        device.setRw(request.getDevice().getW());//屏幕分辨率宽度；例如：1920
        if(null != request.getDevice().getPpi()){
            device.setPpi(request.getDevice().getPpi());//设备屏幕像素密度，如：160
        }else{
            device.setPpi(160);//设备屏幕像素密度，如：406
        }

        //佳量总请求
        JiaLiangBidRequest bidRequest = new JiaLiangBidRequest();
        bidRequest.setRequest_id(request.getId());//本次请求ID，便于流程追踪，建议⽣成⽅式：随机字符串的md5加密⼩写
        bidRequest.setVersion(request.getAdv().getVersion());//当前API版本号，请填写本⽂档中的最新接⼝版本号，如：1.0.2
        bidRequest.setApp_id(request.getAdv().getApp_id());//媒体应⽤ID，由佳量⼴告提供
        bidRequest.setPid(request.getAdv().getTag_id());//媒体⼴告位ID，由佳量⼴告提供
        bidRequest.setImp(imp);//⼴告位描述信息
        bidRequest.setApp(app);//应⽤信息描述
        bidRequest.setDevice(device);//设备信息描述


        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求佳量广告参数"+JSONObject.parseObject(content));
        String url = "http://api.jialiangad.com/api/media/ssp";
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
        log.info("请求上游佳量广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid() + "佳量广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();////请求id

        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        //code=200的时候成功返回，才做解析
        if (200 == jo.getInteger("code")){
            List<TzBid> bidList = new ArrayList<>();
            JSONArray ja = jo.getJSONArray("data");//广告信息-集合
            for (int i=0;i<ja.size();i++){
                TzBid tb = new TzBid();

                Integer ad_type = ja.getJSONObject(i).getInteger("ad_type");//⼴告类型，1：开屏；2：Banner；3：信息流；4：插屏；5：普通视频；6：激励视频
                Integer info_type = ja.getJSONObject(i).getInteger("info_type");//⼴告素材类型；1：图⽂⼴告；2：纯⽂字⼴告；3：HTML⼴告；4：视频⼴告
                JSONObject img = ja.getJSONObject(i).getJSONObject("img");//图⽚⼴告信息
                JSONObject video = ja.getJSONObject(i).getJSONObject("video");//视频⼴告信息

                if (info_type == 1){//图文
                    List<TzImage> list = new ArrayList<>();
                    JSONArray urls = img.getJSONArray("url");//多个图片路径
                    for (int j=0; j < urls.size();j++){
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(urls.get(j).toString());//图⽚地址，外⽹地址，多图时按顺序返回展示
                        tzImage.setW(img.getInteger("w"));//图⽚宽度，图⽚物料的宽度，单位：像素
                        tzImage.setH(img.getInteger("h"));//图⽚⾼度，图⽚物料的⾼度，单位：像素
                        list.add(tzImage);

                        if (ad_type == 3) {//信息流
                            TzNative tzNative = new TzNative();
                            tzNative.setTitle(ja.getJSONObject(i).getJSONObject("ad_info").getString("title"));//广告标题
                            tzNative.setDesc(ja.getJSONObject(i).getJSONObject("ad_info").getString("desc"));//广告描述
                            tzNative.setImages(list);
                            tb.setNATIVE(tzNative);
                            tb.setAd_type(8);//信息流-广告素材类型
                        }else {
                            tb.setAd_type(5);//开屏-广告素材类型
                            tb.setTitle(ja.getJSONObject(i).getJSONObject("ad_info").getString("title"));//广告标题
                            tb.setDesc(ja.getJSONObject(i).getJSONObject("ad_info").getString("desc"));//广告描述
                            tb.setImages(list);//其他类型素材-图片集合
                        }

                        }
                    }else if (info_type == 4){//视频
                            TzNative tzNative = new TzNative();
                            TzVideo tzVideo = new TzVideo();
                            tzVideo.setUrl(video.getString("url"));
                            tzVideo.setW(video.getInteger("w"));
                            tzVideo.setH(video.getInteger("h"));

                            if (ad_type == 3) {//信息流
                                tzNative.setTitle(ja.getJSONObject(i).getJSONObject("ad_info").getString("title"));//广告标题
                                tzNative.setDesc(ja.getJSONObject(i).getJSONObject("ad_info").getString("desc"));//广告描述
                                tzNative.setVideo(tzVideo);
                                tb.setNATIVE(tzNative);
                                tb.setAd_type(8);//信息流-视频素材
                            }else {
                                tb.setAd_type(5);//开屏-广告素材类型
                                tb.setTitle(ja.getJSONObject(i).getJSONObject("ad_info").getString("title"));//广告标题
                                tb.setDesc(ja.getJSONObject(i).getJSONObject("ad_info").getString("desc"));//广告描述
                                tb.setVideo(tzVideo);//其他类型素材-视频素材
                            }
                    }

                    //点击⼴告后的交互⾏为；1：⽆交互；2：跳转类；3：下载类；4：⼴点通下载类（需要执⾏⼆次请求）；5：⼴点通浏 览类；6：deepLink打开类（如deepLink链接不为空则⽆论 是否该类型都要优先打开deepLink链接）
                    Integer action_type = ja.getJSONObject(i).getInteger("action_type");
                    if(1 == action_type){
                        tb.setClicktype("0");//点击
                    }else if(2 == action_type){
                        tb.setClicktype("1");//跳转
                    }else if(3 == action_type){
                        tb.setClicktype("4");//下载
                    }else if(6 == action_type){
                        tb.setClicktype("2");//拉活
                    }else if(4 == action_type){
                        tb.setClicktype("3");//广点通下载
                    }else{
                        tb.setClicktype("0");//点击
                    }

                    tb.setClick_url(ja.getJSONObject(i).getString("click_url"));//点击跳转地址

                    String deeplink = ja.getJSONObject(i).getString("deep_link");//deeplink
                    //deeplink以及监测
                    if(StringUtils.isNotEmpty(deeplink)){
                        tb.setDeeplink_url(deeplink);//deeplink 唤醒地址deeplink 唤醒广告打开页面
                        List<String> deep_linkT = new ArrayList<>();
                        List<String> deep_linkF = new ArrayList<>();
                        deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        deep_linkF.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_FAIL%%");
//                        JSONArray tk_dp_try =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("dlt");//deeplink被点击(尝试唤起)，如果为空，无需上报
                        JSONArray tk_dp_success =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("dls");//deeplink_url 不为空时，唤醒成功时监测
                        JSONArray tk_dp_fail =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("dlf");//deeplink_url 不为空时，唤醒失败时监测
//                        if (null != tk_dp_try){
//                            for (int t = 0; t < tk_dp_try.size(); t++) {
//                                deep_linkT.add(tk_dp_try.get(t).toString());
//                            }
//                        }

                        if (null != tk_dp_success){
                            for (int dp = 0; dp < tk_dp_success.size(); dp++) {
                                deep_linkT.add(tk_dp_success.get(dp).toString());
                            }
                            String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DEEP_LINK%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }

                        if (null != tk_dp_fail){
                            for (int f = 0; f < tk_dp_fail.size(); f++) {
                                deep_linkF.add(tk_dp_fail.get(f).toString());
                            }
                            String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_success_deeplinks(deep_linkF);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%DEEP_LINK_FAIL%%");
                            tzMacros.setValue(Base64.encode(encode));
                            tzMacros1.add(tzMacros);
                        }


                    }

                    //曝光上报地址列表
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("ims")){
                        List<String> check_views = new ArrayList<>();
                        check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                        JSONArray urls1 = ja.getJSONObject(i).getJSONObject("events").getJSONArray("ims");
                        for (int cv = 0; cv < urls1.size(); cv++) {
                            String replace = urls1.get(cv).toString().replace("__TS__", endTime.toString());
                            check_views.add(replace);
                            check_views.add(urls1.get(cv).toString());
                        }
                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //点击上报地址列表
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("cls")){
                        List<String> clickList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("cls");
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < urls1.size(); cc++) {
                            String replace = urls1.get(cc).toString().replace("__TS__", endTime.toString());
                            clickList.add(replace);
                            clickList.add(urls1.get(cc).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //开始下载上报数组
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("dws")){
                        List<String> downLoadList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("dws");
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

                    //下载完成上报数组
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("dwc")){
                        List<String> downLoadDList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("dwc");
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

                    //开始安装上报数组
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("ins")){
                        List<String> installList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("ins");
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

                    //安装完成上报数组
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("inc")){
                        List<String> installEList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("inc");
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

                    //视频开始播放追踪 url 数组
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("vds")){
                        List<String> voidStartList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("vds");
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

                    //视频播放完成追踪 url 数组
                    if(null != ja.getJSONObject(i).getJSONObject("events").getJSONArray("vde")){
                        List<String> voidEndList = new ArrayList<>();
                        JSONArray urls1 =  ja.getJSONObject(i).getJSONObject("events").getJSONArray("vde");
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

                    tb.setMacros(tzMacros1);
                    tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);

                    TzSeat seat = new TzSeat();//素材集合对象
                    seat.setBid(bidList);
                    seatList.add(seat);

                    bidResponse.setId(id);//请求id
                    bidResponse.setBidid(ja.getJSONObject(i).getString("request_id"));//广告主返回id 请求唯一标识符
                    bidResponse.setSeatbid(seatList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info(request.getImp().get(0).getTagid() + "佳量广告总返回"+JSONObject.toJSONString(bidResponse));

                }
            }

        return bidResponse;
    }
}
