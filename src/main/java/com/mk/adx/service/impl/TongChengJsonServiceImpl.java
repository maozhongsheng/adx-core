package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.TongCheng.TongChengApp;
import com.mk.adx.entity.json.request.TongCheng.TongChengBidRequest;
import com.mk.adx.entity.json.request.TongCheng.TongChengDevice;
import com.mk.adx.entity.json.request.TongCheng.TongChengImp;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.AESUtil;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.TongChengJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author Jny
 * @Description 同城旅行
 * @Date 2021/12/27 9:48
 */
@Slf4j
@Service("tongChengJsonService")
public class TongChengJsonServiceImpl implements TongChengJsonService {

    private static final String name = "tongcheng";

    private static final String source = "同城";

    @SneakyThrows
    @Override
    public TzBidResponse getTongChengDataByJson(TzBidRequest request) {
        TongChengApp app = new TongChengApp();//app包信息
//        app.setAppName(request.getAdv().getApp_name());//应用名
//        app.setPackageName(request.getAdv().getBundle());//包名
//        app.setVersion(request.getAdv().getVersion());//app版本号

        app.setAppName("com.tongcheng.android");//应用名
        app.setPackageName("同程旅行");//包名
        app.setVersion("3.8");//app版本号

        TongChengDevice device = new TongChengDevice();//设备信息
        device.setIp(request.getDevice().getIp());//设备ip
        device.setMac(request.getDevice().getMac());//设备mac地址
        //设备类型
        String deviceType = request.getDevice().getDevicetype();
        if ("phone".equals(deviceType)){
            device.setDeviceType(0);//手机
        }else if ("ipad".equals(deviceType)){
            device.setDeviceType(1);//平板
        }else if ("pc".equals(deviceType)){
            device.setDeviceType(2);//pc
        }else if ("tv".equals(deviceType)){
            device.setDeviceType(3);//电视
        }else {
            device.setDeviceType(0);
        }

        //操作系统
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            device.setOs("android");
            if (null != request.getDevice().getImei()){
                device.setImei(request.getDevice().getImei());
                device.setOaid(request.getDevice().getImei());
                device.setAdid(request.getDevice().getImei());
            }else if(null != request.getDevice().getOaid()){
                device.setImei(request.getDevice().getOaid());
                device.setOaid(request.getDevice().getOaid());
                device.setAdid(request.getDevice().getOaid());
            }else if(null != request.getDevice().getAndroid_id()){
                device.setImei(request.getDevice().getAndroid_id());
                device.setOaid(request.getDevice().getAndroid_id());
                device.setAdid(request.getDevice().getAndroid_id());
            }else {
                device.setImei(request.getDevice().getImei());
                device.setOaid(request.getDevice().getOaid());
                device.setAdid(request.getDevice().getAndroid_id());
            }
            
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOs("ios");
            device.setIdfa(request.getDevice().getIdfa());
        }

        //系统版本号
        if (StringUtils.isNotEmpty(request.getDevice().getOsv())){
            device.setOsv(request.getDevice().getOsv());
        }else {
            device.setOsv("10.0");
        }

        //网络连接类型
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            device.setNetwork(0);
        }else if("2".equals(connectiontype)){
            device.setNetwork(1);
        }else if("4".equals(connectiontype)){
            device.setNetwork(2);
        }else if("5".equals(connectiontype)){
            device.setNetwork(3);
        }else if("6".equals(connectiontype)){
            device.setNetwork(4);
        }else if("7".equals(connectiontype)){
            device.setNetwork(5);
        } else {
            device.setNetwork(0);
        }

        //运营商类型
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                device.setOperator(1);//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                device.setOperator(2);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                device.setOperator(3);//中国电信
            }else{
                device.setOperator(0);//其他
            }
        }else{
            device.setOperator(0);
        }
        device.setWidth(request.getDevice().getW());//设备宽
        device.setHeight(request.getDevice().getH());//设备高
        device.setBrand(request.getDevice().getMake());//设备品牌
        device.setModel(request.getDevice().getModel());//设备型号
        device.setUserAgent(request.getDevice().getUa());//UA

        //坐标
        if(null != request.getDevice().getGeo()){
            device.setLon(request.getDevice().getGeo().getLon());//经度
            device.setLat(request.getDevice().getGeo().getLat());//纬度
        }

        TongChengImp imp = new TongChengImp();//广告合约信息
        imp.setWidth(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
        imp.setHeight(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
        imp.setWidthRatio(9);//广告宽度比
        imp.setHeightRatio(16);//广告高度比


        TongChengBidRequest zbr = new TongChengBidRequest();//同城总请求
        zbr.setApp(app);//app包信息
        zbr.setDevice(device);//设备信息
        zbr.setImp(imp);//广告合约信息
        zbr.setPositionId(request.getAdv().getTag_id());//广告位ID
        //zbr.setPositionId("3zh71prj32bj3");//广告位ID
        zbr.setTimestamp(System.currentTimeMillis());//当前时间戳（13位毫秒时间）
        zbr.setUuid(UUID.randomUUID().toString());//请求唯一ID（32或36位UUID）

        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(zbr);
        log.info("请求同城广告参数"+JSONObject.parseObject(content));

        String password = "BvkUYRj6b1KKborG";//加密密钥
        String ivData = "adp platform api";//加密偏移量
        String s = "广告开放平台";
        String encData = AESUtil.encrypt(content,password,ivData);//加密

        String url = "http://adhub.ly.com/adp/api";
        String ua = request.getDevice().getUa();//ua
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(encData);//请求参数-加密后
        pud.setHeaderTongcheng("adp.api.tz");//header需要参数

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求同城广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("同城广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            JSONObject data = jo.getJSONObject("data");
            if (null!=data) {
                List<TzBid> bidList = new ArrayList<>();
                TzBid tb = new TzBid();
                tb.setAdid(data.getString("adId"));//广告ID
                tb.setPrice(data.getFloat("price"));//出价，单位分

                //处理图片
                List<TzImage> list = new ArrayList<>();
                TzNative tzNative = new TzNative();
                TzImage tzImage = new TzImage();
                tzImage.setUrl(data.getString("imgUrl"));//地址
                if (null != data.getString("width")) {//宽
                    tzImage.setW(Integer.valueOf(data.getString("width")));
                } else {
                    tzImage.setW(1280);
                }
                if (null != data.getString("height")) {//高
                    tzImage.setH(Integer.valueOf(data.getString("height")));
                } else {
                    tzImage.setH(720);
                }
                list.add(tzImage);

                if("1000000590".equals(request.getImp().get(0).getTagid()) && 720 > list.get(0).getH()) {
                    return  new TzBidResponse();
                }

                if("1000000591".equals(request.getImp().get(0).getTagid()) && 720 > list.get(0).getH()) {
                    return  new TzBidResponse();
                }


                //上游没返回adtype，则用请求type判断广告类型
                if ("1".equals(request.getImp().get(0).getAd_slot_type()) || "2".equals(request.getImp().get(0).getAd_slot_type())) {//信息流或banner
                    tzNative.setImages(list);
                    tb.setNATIVE(tzNative);
                    tb.setAd_type(8);//原生-广告素材类型
                } else {
                    tb.setImages(list);
                    tb.setAd_type(5);//开屏-广告素材类型
                }

                tb.setTitle(data.getString("title"));//广告标题
                tb.setDesc(source + "广告");//描述
                int clickType = data.getInteger("targetType");//跳转类型,1:H5,2:小程序,3:DP
                if (clickType == 1 || clickType == 2 || clickType == 3) {
                    tb.setClicktype("1");//跳转类型
                } else {
                    tb.setClicktype("0");//点击类型
                }
                tb.setDeeplink_url(data.getString("deepLinkUrl"));//DP链接
                tb.setClick_url(data.getString("adJumpUrl"));//点击后跳转的url

                //曝光监测
                JSONArray vurl = data.getJSONArray("impressionTrackUrls");//曝光上报链接
                if (null != vurl) {
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    for (int cv = 0; cv < vurl.size(); cv++) {
                        check_views.add(vurl.get(cv).toString());//上游返回曝光监测链接
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                boolean macro = data.getBoolean("macroReplace");//是否进行宏替换
                if (macro == true) {
                    //点击监测
                    JSONArray curl = data.getJSONArray("clickTrackUrls");//点击反馈url数组
                    if (null != curl) {
                        List<String> clickList = new ArrayList<>();
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < curl.size(); cc++) {
                            String replace = curl.get(cc).toString().replace("__DOWN_X__", "-999").replace("__DOWN_Y__", "-999");
                            clickList.add(replace);
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                    //唤醒监测
                    JSONArray dt = data.getJSONArray("deeplinkTrackUrls");//点击反馈url数组
                    if (null != dt) {
                        List<String> checkSuccessDeeplinks = new ArrayList<>();
                        checkSuccessDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        for (int cc = 0; cc < dt.size(); cc++) {
                            String replace = dt.get(cc).toString().replace("__CALL_RESULT__", "1");
                            checkSuccessDeeplinks.add(replace);
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_success_deeplinks(checkSuccessDeeplinks);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                } else {
                    //点击监测
                    JSONArray curl = data.getJSONArray("clickTrackUrls");//点击反馈url数组
                    if (null != curl) {
                        List<String> clickList = new ArrayList<>();
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < curl.size(); cc++) {
                            String replace = curl.get(cc).toString();
                            clickList.add(replace);
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                    //唤醒监测
                    JSONArray dt = data.getJSONArray("deeplinkTrackUrls");//点击反馈url数组
                    if (null != dt) {
                        List<String> checkSuccessDeeplinks = new ArrayList<>();
                        checkSuccessDeeplinks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        for (int cc = 0; cc < dt.size(); cc++) {
                            String replace = dt.get(cc).toString();
                            checkSuccessDeeplinks.add(replace);
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_success_deeplinks(checkSuccessDeeplinks);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }
                }

                tb.setMacros(tzMacros1);
                tb.setImpid(request.getImp().get(0).getId());
                bidList.add(tb);

                TzSeat seat = new TzSeat();//素材集合对象
                seat.setBid(bidList);
                seatList.add(seat);

                bidResponse.setId(id);//请求id
                bidResponse.setBidid(id);//广告主返回id
                bidResponse.setSeatbid(seatList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info(request.getImp().get(0).getTagid()+"同城ADX广告总返回"+JSONObject.toJSONString(bidResponse));
            }
        }

        return bidResponse;
    }
}
