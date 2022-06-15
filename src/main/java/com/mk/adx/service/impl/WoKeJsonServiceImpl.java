package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.woke.WoKeBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.WokeJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description
 * @date 2022/4/12 18:21
 */
@Slf4j
@Service("wokeJsonService")
public class WoKeJsonServiceImpl implements WokeJsonService {

    private static final String name = "沃氪";

    private static final String source = "Woke";

    @SneakyThrows
    @Override
    public TzBidResponse getWokeDataByJson(TzBidRequest request) {
        WoKeBidRequest bidRequest = new WoKeBidRequest();
        bidRequest.setApiVersion("1.3");
        bidRequest.setPid(request.getAdv().getTag_id()); //request.getAdv().getTag_id()
        bidRequest.setSecure(1);
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            bidRequest.setDevice_type("0");
        }else if("ipad".equals(devicetype)){
            bidRequest.setDevice_type("1");
        }else if("pc".equals(devicetype)){
            bidRequest.setDevice_type("2");
        }else {
            bidRequest.setDevice_type("-1");
        }
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            bidRequest.setDevice_os("Android");
            bidRequest.setDevice_adid(request.getDevice().getAndroid_id());
            if(StringUtils.isNotEmpty(request.getDevice().getOaid())){
                bidRequest.setDevice_oaid(request.getDevice().getOaid());
                bidRequest.setDevice_oaid_enc("0");
            }else if(StringUtils.isNotEmpty(request.getDevice().getOaid_md5())){
                bidRequest.setDevice_oaid(request.getDevice().getOaid_md5());
                bidRequest.setDevice_oaid_enc("1");
            }
            if(StringUtils.isNotEmpty(request.getDevice().getImei())){
                bidRequest.setDevice_imei(request.getDevice().getImei());
                bidRequest.setDevice_imei_enc("0");
            }else if(StringUtils.isNotEmpty(request.getDevice().getImei_md5())){
                bidRequest.setDevice_imei(request.getDevice().getImei_md5());
                bidRequest.setDevice_imei_enc("1");
            }else if(StringUtils.isNotEmpty(request.getDevice().getImei_sha1())){
                bidRequest.setDevice_imei(request.getDevice().getImei_sha1());
                bidRequest.setDevice_imei_enc("2");
            }
            bidRequest.setDevice_bootMark("ec7f4f33-411a47bc-80 67-744a4e7e0723");
            bidRequest.setDevice_updateMark("004697.709999999");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            bidRequest.setDevice_os("IOS");
            bidRequest.setDevice_ios_openudid(request.getDevice().getOpen_udid());
            if(StringUtils.isNotEmpty(request.getDevice().getIdfa())){
                bidRequest.setDevice_ios_idfa(request.getDevice().getIdfa());
                bidRequest.setDevice_ios_idfa_enc("0");
            }else if(StringUtils.isNotEmpty(request.getDevice().getIdfa_md5())){
                bidRequest.setDevice_ios_idfa(request.getDevice().getIdfa_md5());
                bidRequest.setDevice_ios_idfa_enc("1");
            }else if(StringUtils.isNotEmpty(request.getDevice().getIdfa_sha1())){
                bidRequest.setDevice_ios_idfa(request.getDevice().getIdfa_sha1());
                bidRequest.setDevice_ios_idfa_enc("2");
            }
            if(StringUtils.isNotEmpty(request.getDevice().getIdfv())){
                bidRequest.setDevice_ios_idfv(request.getDevice().getIdfv());
                bidRequest.setDevice_ios_idfv_enc("0");
            }
            bidRequest.setDevice_bootMark("1623815045.970028");
            bidRequest.setDevice_updateMark("1581141691.570419 583");
        }
        bidRequest.setDevice_type_os(request.getDevice().getOsv());
        bidRequest.setDevice_mac(request.getDevice().getMac());
        bidRequest.setDevice_serial("");
        bidRequest.setDevice_imsinum("");
        if(null != request.getDevice().getPpi()){
            bidRequest.setDevice_density(request.getDevice().getPpi().toString());
        }


        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            bidRequest.setDevice_imsi("46000");
        }else if("70123".equals(carrier)){
            bidRequest.setDevice_imsi("46001");
        }else if("70121".equals(carrier)){
            bidRequest.setDevice_imsi("46003");
        }else{
            bidRequest.setDevice_imsi("46000");
        }


        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            bidRequest.setDevice_network("0");
        }else if("1".equals(connectiontype)){
            bidRequest.setDevice_network("1");
        }else if("2".equals(connectiontype)){
            bidRequest.setDevice_network("2");
        }else if("3".equals(connectiontype)){
            bidRequest.setDevice_network("3");
        }else if("4".equals(connectiontype)){
            bidRequest.setDevice_network("4");
        }else if("5".equals(connectiontype)){
            bidRequest.setDevice_network("5");
        }else if("6".equals(connectiontype)){
            bidRequest.setDevice_network("6");
        }else if("7".equals(connectiontype)){
            bidRequest.setDevice_network("7");
        } else {
            bidRequest.setDevice_network("0");
        }

        bidRequest.setDevice_ip(request.getDevice().getIp());
        bidRequest.setDevice_ua(request.getDevice().getUa());
        if(0 != request.getDevice().getDeny()){
            bidRequest.setDevice_dpi(String.valueOf(request.getDevice().getDeny()));
        }
        bidRequest.setDevice_width(request.getDevice().getW().toString());
        bidRequest.setDevice_height(request.getDevice().getH().toString());
        bidRequest.setDevice_orientation("0");
        bidRequest.setDevice_vendor(request.getDevice().getMake());
        bidRequest.setDevice_brand(request.getDevice().getMake());
        bidRequest.setDevice_model(request.getDevice().getModel());
        bidRequest.setDevice_lan("zh-CN");
        bidRequest.setDevice_isroot("0");
        if(null != request.getDevice().getGeo()){
            bidRequest.setDevice_geo_lat(String.valueOf(request.getDevice().getGeo().getLat()));
            bidRequest.setDevice_geo_lon(String.valueOf(request.getDevice().getGeo().getLon()));
        }
        bidRequest.setReception(1);



        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求沃氪广告参数"+JSONObject.parseObject(content));
        String url = "http://api.zixbx.cn/req";
        String ua = request.getDevice().getUa();
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数
        pud.setHeaderWoKe("gzip");
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid()+":请求上游沃氪广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":沃氪广告返回参数"+JSONObject.parseObject(response));

        if (null != response && 200 == JSONObject.parseObject(response).getInteger("code")) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            JSONObject imp = JSONObject.parseObject(response).getJSONObject("res");
            List<TzBid> bidList = new ArrayList<>();
            TzBid tb = new TzBid();
            tb.setId(imp.getString("session_id"));
            if("1".equals(request.getImp().get(0).getAd_slot_type())){
                TzNative tzNative = new TzNative();
                ArrayList<TzImage> images = new ArrayList<>();
                tb.setAd_type(8);//信息流-广告素材类型
                JSONArray navives = imp.getJSONArray("image_urls");
                for (int na = 0; na < navives.size(); na++) {
                    TzImage tzImage = new TzImage();
                    tzImage.setUrl(navives.getString(na));
                    tzImage.setH(imp.getInteger("h"));
                    tzImage.setW(imp.getInteger("w"));
                    images.add(tzImage);
                }
                tzNative.setTitle(imp.getString("title"));
                tzNative.setDesc(imp.getString("sub_title"));
                tzNative.setImages(images);
                tb.setNATIVE(tzNative);
            }else if ("4".equals(request.getImp().get(0).getAd_slot_type())){
                TzVideo video = new TzVideo();
                tb.setAd_type(5);//视频-广告素材类型
                JSONObject videos = imp.getJSONObject("video");
                if (null != videos) {
                    video.setUrl(videos.getString("src"));
                    video.setH(imp.getInteger("h"));
                    video.setW(imp.getInteger("w"));
                    video.setDuration(videos.getInteger("duration"));
                    tb.setTitle(imp.getString("title"));
                    tb.setAic(imp.getString("sub_title"));
                    tb.setVideo(video);//视频素材
                }

            }else{
                ArrayList<TzImage> images = new ArrayList<>();
                String ad_slot_type = request.getImp().get(0).getAd_slot_type();
                if("2".equals(ad_slot_type)){
                    tb.setAd_type(0);//广告素材类型
                }else if("3".equals(ad_slot_type)){
                    tb.setAd_type(5);//广告素材类型
                }else if("5".equals(ad_slot_type)){
                    tb.setAd_type(0);//广告素材类型
                }else if("6".equals(ad_slot_type)){
                    tb.setAd_type(3);//广告素材类型
                }
                JSONArray navives = imp.getJSONArray("image_urls");
                for (int na = 0; na < navives.size(); na++) {
                    TzImage tzImage = new TzImage();
                    tzImage.setUrl(imp.getString("iurl"));
                    tzImage.setH(imp.getInteger("h"));
                    tzImage.setW(imp.getInteger("w"));
                    images.add(tzImage);
                }
                tb.setImages(images);
            }


            String action_type = imp.getString("action_type");
            if("1".equals(action_type)){
                tb.setClicktype("1");//跳转
                tb.setClick_url(imp.getString("landing_url")); // 点击跳转url地址
            }else{
                String download_type = imp.getString("download_type");
                if("0".equals(download_type)){
                    tb.setClicktype("4");//跳转
                    tb.setClick_url(imp.getString("landing_url")); // 点击跳转url地址
                    tb.setDownload_url(imp.getString("landing_url")); // 下载url地址
                }else {
                    tb.setClicktype("3");//跳转
                    tb.setClick_url(imp.getString("landing_url")); // 点击跳转url地址
                    tb.setDownload_url(imp.getString("landing_url")); // 下载url地址
                }

            }

            if(StringUtils.isNotEmpty(imp.getString("deeplink_url"))){
                tb.setDeeplink_url(imp.getString("deeplink_url"));
            }

            if (null != imp.getJSONArray("show_urls")) {
                JSONArray apv = imp.getJSONArray("show_urls");
                List<String> check_views = new ArrayList<>();
                check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                for (int cv = 0; cv < apv.size(); cv++) {
                    String replace = apv.get(cv).toString().replace("__TS__", "%%TS%%");
                    check_views.add(replace);
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%CHECK_VIEWS%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);
            }

            if (null != imp.getJSONArray("click_urls")) {
                JSONArray aclick = imp.getJSONArray("click_urls");
                List<String> clickList = new ArrayList<>();
                clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                for (int cc = 0; cc < aclick.size(); cc++) {
                    String replace = aclick.get(cc).toString().replace("IT_R_AD_WIDTH ", "%%WIDTH%%").replace("IT_R_AD_HEIGHT", "%%HEIGHT%%").replace("IT_CLK_PNT_DOWN_X ", "%%DOWN_X%%").replace("IT_CLK_PNT_DOWN_Y", "%%DOWN_Y%%").replace("IT_CLK_PNT_UP_X", "%%UP_X%%").replace("IT_CLK_PNT_UP_Y", "%%UP_Y%%").replace("IT_RC_TIMESTAMP ", "%%TS%%").replace("IT_N_TIMESTAMP ", "%%TS%%").replace("IT_N_TIMESTAMP_S ", "%%TS%S%%");
                    clickList.add(replace);
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%CHECK_CLICKS%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);

            }
            if (null != imp.getJSONArray("dn_start_urls")) {
                JSONArray sdtrackers = imp.getJSONArray("dn_start_urls");
                List<String> downLoadList = new ArrayList<>();
                downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                for (int dl = 0; dl < sdtrackers.size(); dl++) {
                    downLoadList.add(sdtrackers.get(dl).toString());
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_start_downloads(downLoadList);//开始下载
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%DOWN_LOAD%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);

            }
            if (null != imp.getJSONArray("dpl_succ_urls")) {
                JSONArray dptrackers = imp.getJSONArray("dpl_succ_urls");
                List<String> deep_linkT = new ArrayList<>();
                deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                for (int dp = 0; dp < dptrackers.size(); dp++) {
                    deep_linkT.add(dptrackers.get(dp).toString());
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_success_deeplinks(deep_linkT);//deeplink调起
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%DEEP_LINK%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);

            }
            if (null != imp.getJSONArray("dn_succ_urls")) {
                JSONArray urls1 = imp.getJSONArray("dn_succ_urls");
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
            if (null != imp.getJSONArray("inst_start_urls")) {
                JSONArray urls1 = imp.getJSONArray("inst_start_urls");
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
            if (null != imp.getJSONArray("inst_succ_urls")) {
                JSONArray urls1 = imp.getJSONArray("inst_succ_urls");
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

            tb.setMacros(tzMacros1);
            tb.setImpid(request.getImp().get(0).getId());
            bidList.add(tb);//
            TzSeat seat = new TzSeat();//
            seat.setBid(bidList);
            seatList.add(seat);
            bidResponse.setId(id);//请求id
            bidResponse.setBidid(id);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info(request.getImp().get(0).getTagid()+":沃氪总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
