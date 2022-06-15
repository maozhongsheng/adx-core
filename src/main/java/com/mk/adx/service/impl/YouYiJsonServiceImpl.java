package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.youyi.YyBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.service.YouYiJsonService;
import com.mk.adx.util.Base64;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 谊友
 * @Date 2021/3/14 13:11
 */
@Slf4j
@Service("youYiJsonService")
public class YouYiJsonServiceImpl implements YouYiJsonService {

    private static final String name = "yiyou";

    private static final String source = "谊友";

    @SneakyThrows
    @Override
    public TzBidResponse getYouYiDataByJson(TzBidRequest request) {
        YyBidRequest bidRequest = new YyBidRequest();
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String response = null;
        CloseableHttpResponse yyresponse = null;
        /*
         * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
         */
        String url = "http://ssp.1rtb.com/req_ad";//友谊请求路径
        URIBuilder uriBuilder = new URIBuilder(url);

        uriBuilder.addParameter("version", "3.0");
        uriBuilder.addParameter("app_id", request.getAdv().getApp_id()); //
        uriBuilder.addParameter("pid", request.getAdv().getTag_id()); //
        uriBuilder.addParameter("is_mobile", "1");
        uriBuilder.addParameter("app_package", request.getAdv().getBundle()); //
        uriBuilder.addParameter("app_name", request.getAdv().getApp_name()); //
        uriBuilder.addParameter("app_ver",  request.getAdv().getVersion());//
        if(null != request.getDevice().getGeo()) {
            uriBuilder.addParameter("device_geo_lat", String.valueOf(request.getDevice().getGeo().getLat()));
            uriBuilder.addParameter("device_geo_lon", String.valueOf(request.getDevice().getGeo().getLon()));
        }
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            uriBuilder.addParameter("device_imei", request.getDevice().getImei());
            uriBuilder.addParameter("device_oaid", request.getDevice().getOaid());
            uriBuilder.addParameter("device_oaidmd5", request.getDevice().getOaid_md5());
            uriBuilder.addParameter("device_adid", request.getDevice().getAndroid_id());
            uriBuilder.addParameter("device_os", "Android");
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            uriBuilder.addParameter("device_adid", request.getDevice().getIdfa());
            uriBuilder.addParameter("device_openudid", request.getDevice().getOpen_udid());
            uriBuilder.addParameter("device_idfv", request.getDevice().getIdfv());
            uriBuilder.addParameter("device_os", "iOS");

        }
        if(null != request.getDevice().getPpi()){
            uriBuilder.addParameter("device_ppi", request.getDevice().getPpi().toString());
        }else{
            uriBuilder.addParameter("device_ppi", "0");
        }

        DecimalFormat format = new DecimalFormat("#.00");
        uriBuilder.addParameter("device_density", format.format(request.getDevice().getDeny()));
        uriBuilder.addParameter("device_mac", request.getDevice().getMac());
        uriBuilder.addParameter("device_type_os", request.getDevice().getOsv());
        String devicetype = request.getDevice().getDevicetype();
        if("phone".equals(devicetype)){
            uriBuilder.addParameter("device_type", "0");
        }else if("ipad".equals(devicetype)){
            uriBuilder.addParameter("device_type", "4");
        }else if("tv".equals(devicetype)){
            uriBuilder.addParameter("device_type", "3");
        }else{
            uriBuilder.addParameter("device_type", "0");
        }

        uriBuilder.addParameter("device_brand",request.getDevice().getMake());
        uriBuilder.addParameter("device_model", request.getDevice().getModel());
        uriBuilder.addParameter("device_width", request.getDevice().getW().toString());
        uriBuilder.addParameter("device_height", request.getDevice().getH().toString());
        String carrier = request.getDevice().getCarrier();
        if("70120".equals(carrier)){
            uriBuilder.addParameter("device_imsi", "46000");
        }else if("70123".equals(carrier)){
            uriBuilder.addParameter("device_imsi", "46001");
        }else if("70121".equals(carrier)){
            uriBuilder.addParameter("device_imsi", "46003");
        }else{
            uriBuilder.addParameter("device_imsi", "46001");
        }

        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            uriBuilder.addParameter("device_network", "1");
        }else if("2".equals(connectiontype)){
            uriBuilder.addParameter("device_network", "1");
        }else if("4".equals(connectiontype)){
            uriBuilder.addParameter("device_network", "4");
        }else if("5".equals(connectiontype)){
            uriBuilder.addParameter("device_network", "3");
        }else if("6".equals(connectiontype)){
            uriBuilder.addParameter("device_network", "2");
        }else if("7".equals(connectiontype)){
            uriBuilder.addParameter("device_network", "6");
        } else {
            uriBuilder.addParameter("device_network", "1");
        }

        uriBuilder.addParameter("device_ip", request.getDevice().getIp());
        uriBuilder.addParameter("device_ua", request.getDevice().getUa());
        uriBuilder.addParameter("device_orientation", "0");


        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //请求超时时间设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(500).setConnectionRequestTimeout(100).setSocketTimeout(500).build();
        httpGet.setConfig(requestConfig);

        /*
         * 添加请求头信息
         */
        // 浏览器表示
        httpGet.addHeader("User-Agent", request.getDevice().getUa());
        // 传输的类型
        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpGet.addHeader("Accept", "*/*");
        httpGet.addHeader("Accept-Encoding", "gzip");
        httpGet.addHeader("Connection", "keep-alive");
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        log.info(request.getImp().get(0).getTagid()+":请求谊友广告参数"+httpGet);
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        yyresponse = httpClient.execute(httpGet);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        HttpEntity entity = yyresponse.getEntity();
        if(null != entity){
            response = EntityUtils.toString(entity, "UTF-8");
            log.info(request.getImp().get(0).getTagid()+":谊友广告返回参数"+JSONObject.parseObject(response));
            JSONObject BidRes = JSONObject.parseObject(response);
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            String id = request.getId();
            //多层解析json
            List<TzBid> bidList = new ArrayList<>();
            TzBid tb = new TzBid();
            tb.setId(BidRes.getString("req_id"));
            TzVideo video = new TzVideo();
            if("1".equals(request.getImp().get(0).getAd_slot_type())){
                TzNative tzNative = new TzNative();
                tb.setAd_type(8);//信息流-广告素材类型
                JSONArray yyImages =  BidRes.getJSONArray("srcUrls");
                ArrayList<TzImage> images = new ArrayList<>();
                TzLogo tzLogo = new TzLogo();
                tzLogo.setUrl(BidRes.getString("from_logo"));
                tzNative.setLogo(tzLogo);
                   if(null != yyImages) {
                       for (int y = 0; y<yyImages.size(); y++) {
                           TzImage tzImage = new TzImage();
                           tzImage.setUrl(yyImages.getString(y));
                           tzImage.setH(BidRes.getInteger("height"));
                           tzImage.setW(BidRes.getInteger("width"));
                           images.add(tzImage);
                       }
                   }
                tzNative.setTitle(BidRes.getString("title"));
                tzNative.setDesc(BidRes.getString("content"));
                tzNative.setImages(images);
                tb.setNATIVE(tzNative);
            }else{
                tb.setTitle(BidRes.getString("title"));
                tb.setDesc(BidRes.getString("content"));
                tb.setAdLogo(BidRes.getString("from_logo"));
                JSONArray yyImages =  BidRes.getJSONArray("srcUrls");
                ArrayList<TzImage> images = new ArrayList<>();
                if (null != yyImages) {
                    for (int y = 0; y<yyImages.size(); y++) {
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(yyImages.getString(y));
                        tzImage.setH(BidRes.getInteger("height"));
                        tzImage.setW(BidRes.getInteger("width"));
                        images.add(tzImage);
                    }
                    tb.setImages(images);
                }
            }

            Integer action = BidRes.getInteger("target_type");//1:落地⻚，2:资源下载，
            String from = BidRes.getString("from");
            if(1 == action && "GDT".equals(from)){
                tb.setClicktype("3");//广点通
            }else if(1 == action){
                tb.setClicktype("4");//下载
            }else{
                tb.setClicktype("0");//点击
            }
//            TzBidApps tzBidApps = new TzBidApps();
//            tzBidApps.setBundle(imp.getJSONObject(i).getString("appPackage"));
//            tzBidApps.setApp_name(imp.getJSONObject(i).getString("appName"));
//            tzBidApps.setApp_icon(imp.getJSONObject(i).getString("appIconUrl"));
//            tb.setApp(tzBidApps);


            tb.setDeeplink_url(BidRes.getString("deep_link"));
            tb.setClick_url(BidRes.getJSONArray("dUrl").getString(0)); // 点击跳转url地址


            if(null != BidRes.getJSONArray("monitorUrl")){
                List<String> check_views = new ArrayList<>();
                check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                JSONArray urls1 = BidRes.getJSONArray("monitorUrl");
                for (int cv = 0; cv < urls1.size(); cv++) {
                    check_views.add(urls1.get(cv).toString());
                }
                String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%CHECK_VIEWS%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);
            }
            if(null != BidRes.getJSONArray("clickUrl")){
                List<String> clickList = new ArrayList<>();
                JSONArray urls1 =  BidRes.getJSONArray("clickUrl");
                clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                for (int cc = 0; cc < urls1.size(); cc++) {
                    String replace = urls1.get(cc).toString().replace("__WIDTH__", BidRes.getInteger("width").toString()).replace("__HEIGHT__", BidRes.getInteger("height").toString()).replace("__DOWN_X__", "%%DOWN_X%%").replace("__DOWN_Y__", "%%DOWN_Y%%").replace("__UP_X__", "%%UP_X%%").replace("__UP_Y__",  "%%UP_Y%%").replace("__MS_EVENT_SEC__", "%%TS%S%%").replace("__MS_EVENT_MSEC__", "%%TS%%");
                    clickList.add(replace);
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%CHECK_CLICKS%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);
            }
            if(null != BidRes.getJSONArray("dp_succ")) {
                List<String> deep_linkT = new ArrayList<>();
                deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                JSONArray urls1 = BidRes.getJSONArray("dp_succ");
                for (int dp = 0; dp < urls1.size(); dp++) {
                    deep_linkT.add(urls1.get(dp).toString());
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_success_deeplinks(deep_linkT);//唤醒成功监测URL，支持宏替换 第三方曝光监测
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%DEEP_LINK%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);
            }
            if(null != BidRes.getJSONArray("dp_fail")) {
                List<String> deep_linkT = new ArrayList<>();
                deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%DEEP_LINK_FAIL%%");
                JSONArray urls1 = BidRes.getJSONArray("dp_fail");
                for (int dp = 0; dp < urls1.size(); dp++) {
                    deep_linkT.add(urls1.get(dp).toString());
                }
                String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                tb.setCheck_success_deeplinks(deep_linkT);//唤醒失败监测URL，支持宏替换 第三方曝光监测
                tzMacros = new TzMacros();
                tzMacros.setMacro("%%DEEP_LINK_FAIL%%");
                tzMacros.setValue(Base64.encode(encode));
                tzMacros1.add(tzMacros);
            }

            if(null != BidRes.getJSONArray("dn_start")) {
                List<String> downLoadList = new ArrayList<>();
                JSONArray urls1 =  BidRes.getJSONArray("dn_start");
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
            if(null != BidRes.getJSONArray("dn_succ")) {
                List<String> downLoadDList = new ArrayList<>();
                JSONArray urls1 =  BidRes.getJSONArray("dn_succ");
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
            if(null != BidRes.getJSONArray("dn_inst_start")) {
                List<String> installList = new ArrayList<>();
                JSONArray urls1 =  BidRes.getJSONArray("dn_inst_start");
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
            if(null != BidRes.getJSONArray("dn_inst_succ")) {
                List<String> installEList = new ArrayList<>();
                JSONArray urls1 = BidRes.getJSONArray("dn_inst_succ");
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
            log.info(request.getImp().get(0).getTagid()+"：谊友总返回数据"+JSONObject.toJSONString(bidResponse));
        }
        return bidResponse;
    }
}
