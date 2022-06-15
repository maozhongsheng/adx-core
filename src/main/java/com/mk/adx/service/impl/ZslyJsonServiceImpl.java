package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.zhangshangleyou.ZslyBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.ZslyJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description 掌上乐游
 * @Date 2021/11/23 9:48
 */
@Slf4j
@Service("zslyJsonService")
public class ZslyJsonServiceImpl implements ZslyJsonService {

    private static final String name = "zsly";

    private static final String source = "掌上乐游";

    /**
     * @Author Jny
     * @Description 掌上乐游
     * @Date 2021/11/5 17:20
     */
    @SneakyThrows
    @Override
    public TzBidResponse getZslyDataByJson(TzBidRequest request) {
        ZslyBidRequest zbr = new ZslyBidRequest();//掌上乐游总请求
        zbr.setApi_type("API");//接入方式：API:服务器对接(默认)SDK:客户端对接
        zbr.setRequestId(request.getId());//请求id
        zbr.setSlot_id(request.getAdv().getTag_id());//广告位ID，请联系商务提供

        if ("phone".equals(request.getDevice().getDevicetype())){//设备类型
            zbr.setDevice_type(1);//手机
        }else if ("ipad".equals(request.getDevice().getDevicetype())){
            zbr.setDevice_type(2);//平板
        }

        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            zbr.setOs_type("ANDROID");//安卓
            //设备标识:用于设备唯一标识(必须明文)对应IOS 设备,该值为idfa,对于android设备,该值为imei,安卓10 无imei,用oaid
            if (StringUtils.isNotEmpty(request.getDevice().getImei())){
                zbr.setDevice_id(request.getDevice().getImei());
            }else {
                zbr.setDevice_id(request.getDevice().getOaid());
            }
            zbr.setImei(request.getDevice().getImei());//国际移动设备识别码原始值,32 位小写的MD5 值,原始值与md5 值二选一,必传
            zbr.setImei_md5(request.getDevice().getImei_md5());
            zbr.setOaid(request.getDevice().getOaid());//匿名设备标识符,安卓10 无imei,用oaid32 位小写的MD5 值,原始值与md5 值二选一,必传
            zbr.setAndroid_id(request.getDevice().getAndroid_id());//Android ID 手机唯一标识
            zbr.setApi_level(28);//安卓API 等级(IOS 不填)
            zbr.setSerialno("");//移动设备序列号
            zbr.setDpi(request.getDevice().getPpi());//设备屏幕像素密度如:160同ios 系统的ppi
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            zbr.setOs_type("IOS");//ios
            zbr.setDevice_id(request.getDevice().getIdfa());//设备标识
            zbr.setSerialno("");//移动设备序列号
            zbr.setIdfa(request.getDevice().getIdfa());
        }

        //系统版本号
        if (StringUtils.isNotEmpty(request.getDevice().getOsv())){
            zbr.setOs_version(request.getDevice().getOsv());
        }else {
            zbr.setOs_version("4.3.3");
        }

        zbr.setIs_deeplink(1);//是否支持deep_link。默认支持 0:不支持1:支持
        zbr.setMac(request.getDevice().getMac());//本机无线网卡的MAC 地址
        zbr.setSsid("");//SSID ⽆线⽹名称
        zbr.setBssid("");//所连接的WIFI 设备的MAC 地址,路由器WIFI 的MAC 地址
        zbr.setVendor(request.getDevice().getMake());//设备厂商
        zbr.setBrand(request.getDevice().getModel());//品牌
        zbr.setModel(request.getDevice().getModel());//设备型号
        zbr.setOrientation(0);//屏幕方向0:未知1:竖屏2:横屏
        zbr.setW(request.getDevice().getW());//屏幕宽
        zbr.setH(request.getDevice().getH());//屏幕高
        zbr.setDensity(request.getDevice().getDeny());//设备屏幕密度,如：2.0
        zbr.setScreen_size(6.1);//屏幕尺寸,例:4.7 , 5.5,单位:英寸
        zbr.setIp(request.getDevice().getIp());//客户端IPv4 地址注意：ip 为外网ip
        zbr.setUa(request.getDevice().getUa());//浏览器User-Agent，空格不要转义，请求广告、上报监测的ua，使用Webview 或浏览器ua，不要使用系统原始ua，或自定义ua，请求广告、上报监测中的ua 要保持一致，否则数据会被过滤

        //网络连接类型
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            zbr.setConnection_type("UNKNOWN");
        }else if("1".equals(connectiontype)){
            zbr.setConnection_type("ETHERNET");
        }else if("2".equals(connectiontype)){
            zbr.setConnection_type("WIFI");
        }else if("3".equals(connectiontype)){
            zbr.setConnection_type("CELL_UNKNOWN");
        }else if("4".equals(connectiontype)){
            zbr.setConnection_type("2G");
        }else if("5".equals(connectiontype)){
            zbr.setConnection_type("3G");
        }else if("6".equals(connectiontype)){
            zbr.setConnection_type("4G");
        }else if("7".equals(connectiontype)){
            zbr.setConnection_type("5G");
        } else {
            zbr.setConnection_type("UNKNOWN");
        }

        //运营商类型
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                zbr.setOperator_type("CMCC");//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                zbr.setOperator_type("CUCC");//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                zbr.setOperator_type("CTCC");//中国电信
            }else{
                zbr.setOperator_type("OTHER");//其他
            }
        }else{
            zbr.setOperator_type("OTHER");
        }

        zbr.setImsi("");//国际移动用户识别码
        zbr.setMcc("460");//移动国家码,如:460中国
        zbr.setMnc("00");//移动网络吗,如:00
        zbr.setCoordinate_type(1);;//GPS 坐标类型1:全球卫星定位系统坐标系2:国家测绘局坐标系3:百度坐标系
        //坐标
        if(null != request.getDevice().getGeo()){
            zbr.setLng(request.getDevice().getGeo().getLon());//经度
            zbr.setLat(request.getDevice().getGeo().getLat());//纬度
        }

        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(zbr);
        log.info("请求掌上乐游广告参数"+JSONObject.parseObject(content));
        String url = "http://api.touchxd.com/api/v2/ad/1073";
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
        log.info("请求上游掌上乐游广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("掌上乐游广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        //code=0的时候成功返回，才做解析
        if (0 == jo.getInteger("code")){
            String bidid = jo.getString("request_id");//请求id
            JSONArray imp = JSONObject.parseArray(jo.getString("ads"));
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                TzBid tb = new TzBid();
//                tb.setId(jo.getString("request_id"));//曝光id
//                tb.setPrice(imp.getJSONObject(i).getInteger("price"));//价格

                //区分信息流和开屏等其他类型广告
                TzNative tzNative = new TzNative();
                List<TzImage> list = new ArrayList<>();
                if (null!=request.getImp()) {
                    for (int j = 0; j < request.getImp().size(); j++) {
                        JSONArray imgs = imp.getJSONObject(i).getJSONArray("images");
                        for (int g = 0; g < imgs.size(); g++){
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(imgs.get(g).toString());
                            tb.setW(imp.getJSONObject(i).getInteger("width"));//素材宽
                            tb.setH(imp.getJSONObject(i).getInteger("height"));//素材高
                            list.add(tzImage);//信息流素材-图片集合
                        }
                        if ("1".equals(request.getImp().get(i).getAd_slot_type())) {//信息流素材-图片集合
                            tzNative.setTitle(imp.getJSONObject(i).getString("title"));//广告标题
                            tzNative.setDesc(imp.getJSONObject(i).getString("desc"));//广告描述
                            tzNative.setImages(list);
                            tb.setNATIVE(tzNative);
                            tb.setAd_type(8);//信息流-广告素材类型
                        }else {
                            tb.setAd_type(5);//开屏-广告素材类型
                            tb.setTitle(imp.getJSONObject(i).getString("title"));//广告标题
                            tb.setDesc(imp.getJSONObject(i).getString("desc"));//广告描述
                            tb.setImages(list);//其他类型素材-图片集合
                        }
                    }
                }

                Integer interaction_type = imp.getJSONObject(i).getInteger("interaction_type");//广告类型0:NONE 无动作，即广告点击后无需进行任何响应1:浏览类,落地页跳转2:下载类广告3. 二次下载类广告(广点通特有)4.deeplink，唤醒App5.微信小程序拉起
                if(0 == interaction_type){
                    tb.setClicktype("0");//点击
                }else if(1 == interaction_type){
                    tb.setClicktype("1");//跳转
                }else if(2 == interaction_type){
                    tb.setClicktype("4");//下载
                }else if(4 == interaction_type){
                    tb.setClicktype("2");//拉活
                }else if(3 == interaction_type){
                    tb.setClicktype("3");//广点通下载
                }else{
                    tb.setClicktype("0");//点击
                }

                tb.setClick_url(imp.getJSONObject(i).getString("click_url")); // 点击跳转url地址

                if(StringUtils.isNotEmpty(imp.getJSONObject(i).getString("deeplink_url"))){
                    tb.setDeeplink_url(imp.getJSONObject(i).getString("deeplink_url"));//deeplink 唤醒地址deeplink 唤醒广告打开页面
//                    imp.getJSONObject(i).getString("fallback_type");//deeplink 唤醒广告退化类型 1:浏览器打开页面 2:APP 下载
//                    imp.getJSONObject(i).getString("fallback_url");//deeplink 唤醒广告退化链接

                    List<String> deep_linkT = new ArrayList<>();
                    deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
//                    JSONArray tk_dp_try = imp.getJSONObject(i).getJSONArray("tk_dp_try");//deeplink_url 不为空时，尝试唤起时监测
                    JSONArray tk_dp_success = imp.getJSONObject(i).getJSONArray("tk_dp_success");//deeplink_url 不为空时，唤醒成功时监测
//                    JSONArray tk_dp_fail = imp.getJSONObject(i).getJSONArray("tk_dp_fail");//deeplink_url 不为空时，唤醒失败时监测
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



                if(null != imp.getJSONObject(i).getJSONArray("tk_show")){
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    JSONArray urls1 = imp.getJSONObject(i).getJSONArray("tk_show");
                    for (int cv = 0; cv < urls1.size(); cv++) {
//                        String replace = urls1.get(cv).toString().replace("__RESPONSE_TIME__", endTime.toString()).replace("__READY_TIME__", System.currentTimeMillis() + "").replace("__SHOW_TIME__", System.currentTimeMillis() + "");
//                        check_views.add(replace);
                        check_views.add(urls1.get(cv).toString());
                    }
                    String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(null != imp.getJSONObject(i).getJSONArray("tk_click")){
                    List<String> clickList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_click");
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    for (int cc = 0; cc < urls1.size(); cc++) {
//                        String replace = urls1.get(cc).toString().replace("__CLICK_TIME__", System.currentTimeMillis() + "");
//                        clickList.add(replace);
                        clickList.add(urls1.get(cc).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(null != imp.getJSONObject(i).getJSONArray("tk_video_begin")){
                    List<String> voidStartList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_video_begin");
                    voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                    for (int vs = 0; vs < urls1.size(); vs++) {
                        voidStartList.add(urls1.get(vs).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    //    tb.setCheck_views(voidStartList);//视频开始播放
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%VEDIO_START%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }


                if(null != imp.getJSONObject(i).getJSONArray("tk_video_end")){
                    List<String> voidEndList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_video_end");
                    voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                    for (int ve = 0; ve < urls1.size(); ve++) {
                        voidEndList.add(urls1.get(ve).toString());
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    //    tb.setCheck_clicks(voidEndList);//视频播放结束
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%VEDIO_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(null != imp.getJSONObject(i).getJSONArray("tk_download_begin")){
                    List<String> downLoadList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_download_begin");
                    downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                    for (int dl = 0; dl < urls1.size(); dl++) {
                        downLoadList.add(urls1.get(dl).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_downloads(downLoadList);//开始下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_LOAD%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(null != imp.getJSONObject(i).getJSONArray("tk_download_end")){
                    List<String> downLoadDList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_download_end");
                    downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                    for (int dle = 0; dle < urls1.size(); dle++) {
                        downLoadDList.add(urls1.get(dle).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_end_downloads(downLoadDList);//结束下载
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%DOWN_END%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }

                if(null != imp.getJSONObject(i).getJSONArray("tk_install_begin")){
                    List<String> installList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_install_begin");
                    installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        installList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_start_installs(installList);//开始安装
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%INSTALL_START%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                if(null != imp.getJSONObject(i).getJSONArray("tk_install_end")){
                    List<String> installEList = new ArrayList<>();
                    JSONArray urls1 =  imp.getJSONObject(i).getJSONArray("tk_install_end");
                    installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                    for (int ins = 0; ins < urls1.size(); ins++) {
                        installEList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                    }
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_end_installs(installEList);//安装完成
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);
                }
                tb.setMacros(tzMacros1);
                tb.setSource(source+": "+imp.getJSONObject(i).getString("adsource"));
                tb.setImpid(request.getImp().get(0).getId());
                bidList.add(tb);
            }
            TzSeat seat = new TzSeat();//素材集合对象
            seat.setBid(bidList);
            seatList.add(seat);

            bidResponse.setId(id);//请求id
            bidResponse.setBidid(bidid);//广告主返回id
            bidResponse.setSeatbid(seatList);//广告集合对象
//            bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info("掌上乐游广告总返回"+JSONObject.toJSONString(bidResponse));
        }

        return bidResponse;
    }
}
