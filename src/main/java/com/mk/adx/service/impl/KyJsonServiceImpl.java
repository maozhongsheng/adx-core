package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.MD5Util;
import com.mk.adx.service.KyJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @Author yjn
 * @Description 中视力美-快友
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("kyJsonService")
public class KyJsonServiceImpl implements KyJsonService {

    public static  String id = "";
    public static final String name = "快有";
    public static  String ip = "";
    public static  String appName = "";

    /**
     *  快友正式请求
     * @param request
     * @return
     */
    //    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<TzBidResponse> getKyDataByJson(TzBidRequest request, Map upper) {
        id = request.getId();
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            URIBuilder uriBuilder = doKyRequest(request,upper);//调用快友公共请求方发
            HttpGet httpGet = new HttpGet(uriBuilder.build());// 根据带参数的URI对象构建GET请求对象
            /*
             * 添加请求头信息
             */
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");// 浏览器表示
            httpGet.addHeader("Content-Type", "application/json;charset=utf-8");// 传输的类型
            httpGet.addHeader("Accept","*/*");
            httpGet.addHeader("Accept-Encoding","gzip, deflate, br");
            httpGet.addHeader("Connection","keep-alive");
            log.info("请求快友数据"+httpGet.toString());
            // 执行请求
            Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
            response = httpClient.execute(httpGet);
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

            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
            log.info("快友响应内容"+entityStr);
            bidResponse = doKyResponse(entityStr);//处理返回数据公共方法
            if(null != bidResponse.getSeatbid()){
                bidResponse.setId(request.getId());//请求id
                bidResponse.setProcess_time_ms(tempTime);//耗时，单位：ms
            }
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URI解析异常");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        log.info("总返回==="+JSONObject.toJSONString(bidResponse));
        return AsyncResult.forValue(bidResponse);
    }

    /**
     * 快友公共请求方发
     * @param request
     * @return
     */
    private URIBuilder doKyRequest(TzBidRequest request, Map upper) throws URISyntaxException {
        /*
         * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
         */
        appName = request.getApp().getName();
        ip = request.getDevice().getIp();
        String url = "http://open.adview.cn/agent/openRequest.do";//快友请求路径
        URIBuilder uriBuilder = new URIBuilder(url);
        /** 第一种添加参数的形式 */
            /*uriBuilder.addParameter("name", "root");
              uriBuilder.addParameter("password", "123456");*/
        /** 第二种添加参数的形式 */
//            List<NameValuePair> list = new LinkedList<>();
//            BasicNameValuePair param1 = new BasicNameValuePair("name", "root");
//            BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
//            list.add(param1);
//            list.add(param2);
//            uriBuilder.setParameters(list);
        /**
         * 快友真实请求参数
         */
        if (null != request.getMedia_version()){
            uriBuilder.addParameter("ver",request.getMedia_version());//版本号
        }else {
            uriBuilder.addParameter("ver","3.3");
        }
        uriBuilder.addParameter("html5","2");//响应的广告物料类型，0=>任意形似都可以(Native或html5)1=>只接受html5广告2=>不接受html5广告
        uriBuilder.addParameter("supmacro","0");//是否支持宏替换,默认为0不支持
        //token需要的参数
        String appid = "";
        String sn = "";
        String os = "";
        String nop = "";
        String pack = "";
        String time = "";
        String secretKey = "gvobmnv1jx633pe9gx5bnfbfq087aj8j";

        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){
                uriBuilder.addParameter("posId",upper.get("tag_id").toString());
                //广告位类型
                if ("1".equals(request.getImp().get(i).getAd_slot_type())){
                    uriBuilder.addParameter("pt","6");//原生，信息流
                    uriBuilder.addParameter("w","720");//广告位宽度，单位为像素
                    uriBuilder.addParameter("h","1280");//广告高度，单位为像素
                }else if ("2".equals(request.getImp().get(i).getAd_slot_type())){

                }else if ("3".equals(request.getImp().get(i).getAd_slot_type())){
                    uriBuilder.addParameter("pt","4");//开屏
                    uriBuilder.addParameter("w","640");//广告位宽度，单位为像素
                    uriBuilder.addParameter("h","960");//广告高度，单位为像素
                }else if ("4".equals(request.getImp().get(i).getAd_slot_type())){
                    uriBuilder.addParameter("pt","5");//视频
                    if (null!=request.getImp().get(i).getVideo()){//video
                        uriBuilder.addParameter("w","640");//广告位宽度，单位为像素
                        uriBuilder.addParameter("h","960");//广告高度，单位为像素
                    }
                }else if ("5".equals(request.getImp().get(i).getAd_slot_type())){//横幅
                    if (null!=request.getImp().get(i).getBanner()){//banner
                        if (null!=request.getImp().get(i).getBanner().getW()){
                            uriBuilder.addParameter("w",request.getImp().get(i).getBanner().getW().toString());//广告位宽度，单位为像素
                        }
                        if (null!=request.getImp().get(i).getBanner().getH()){
                            uriBuilder.addParameter("h",request.getImp().get(i).getBanner().getH().toString());//广告高度，单位为像素
                        }
                    }
                }else if ("6".equals(request.getImp().get(i).getAd_slot_type())){
                    uriBuilder.addParameter("pt","1");//插屏
                }else if ("7".equals(request.getImp().get(i).getAd_slot_type())){

                }else if ("8".equals(request.getImp().get(i).getAd_slot_type())){

                }

                if (null!=request.getImp().get(i).getReq_num()){
                    uriBuilder.addParameter("n", request.getImp().get(i).getReq_num().toString());//请求广告的数量
                }else {
                    uriBuilder.addParameter("n", "1");
                }
            }
        }

        if (null!=request.getApp()){//app
            uriBuilder.addParameter("appid",upper.get("app_id").toString());//appid
            pack = request.getApp().getBundle();
            uriBuilder.addParameter("pack",pack);//媒体的包名，即本 app 的包名
        }

        if (null!=request.getDevice()){//device
            uriBuilder.addParameter("bdr",request.getDevice().getOsv());//终端操作系统版本号
            if ("phone".equals(request.getDevice().getDevicetype())){
                uriBuilder.addParameter("tab","0");//终端设备类型:0=>手机,1=>平板
            }else if ("ipad".equals(request.getDevice().getDevicetype())){
                uriBuilder.addParameter("tab","1");//终端设备类型:0=>手机,1=>平板
            }
            uriBuilder.addParameter("ip",request.getDevice().getIp());//终端设备外网的IP 地址
            os = request.getDevice().getOs();//终端操作系统类型:0=>Android,1=>iOS
            if(os.equals("0")||os.equals("android")){
                uriBuilder.addParameter("os","0");
            }else if (os.equals("1")||os.equals("ios")){
                uriBuilder.addParameter("os","1");
            }
           if(null != request.getDevice().getGeo()){
               if(0 < request.getDevice().getGeo().getLat()){
                   uriBuilder.addParameter("lat", String.valueOf(request.getDevice().getGeo().getLat()));//纬度
               }
               if(0 < request.getDevice().getGeo().getLon()){
                   uriBuilder.addParameter("lon", String.valueOf(request.getDevice().getGeo().getLon()));//经度
               }
           }
            uriBuilder.addParameter("tp",request.getDevice().getModel());//终端设备型号
            uriBuilder.addParameter("brd",request.getDevice().getMake());//终端设备品牌
            uriBuilder.addParameter("ua",request.getDevice().getUa());//终端设备实际webview User Agent 值
            uriBuilder.addParameter("sw",request.getDevice().getW().toString());//设备屏宽
            uriBuilder.addParameter("sh",request.getDevice().getH().toString());//设备屏高
            if (request.getDevice().getDeny()>0){
                uriBuilder.addParameter("deny", String.valueOf(request.getDevice().getDeny()));//设备屏幕密度
            }
            if ("0".equals(request.getDevice().getOs())||"Android".equals(request.getDevice().getOs())){//android-设备唯一标识
                //if (null!=request.getDevice().getImei()||request.getDevice().getImei().equals("")){
                if (!StringUtils.isEmpty(request.getDevice().getImei())){
                    sn = request.getDevice().getImei();
                    uriBuilder.addParameter("imei",request.getDevice().getImei());//安卓设备的imei号
                    uriBuilder.addParameter("didmd5",request.getDevice().getImei_md5());//IMEI的md5值
                    uriBuilder.addParameter("didsha1",request.getDevice().getImei_sha1());//IMEI的sha1值
                }else if (null==request.getDevice().getImei()||request.getDevice().getImei().equals("")){
                    sn = request.getDevice().getAndroid_id();
                }else if (""==request.getDevice().getImei()&&""==request.getDevice().getAndroid_id()){
                    sn = request.getDevice().getOaid();
                }
                if(null != request.getDevice().getOaid()){
                    uriBuilder.addParameter("oaid",request.getDevice().getOaid());//移动安全联盟推出的匿名设备标识符
                }else{
                    uriBuilder.addParameter("oaid","");//移动安全联盟推出的匿名设备标识符
                }

                uriBuilder.addParameter("sn",sn);//设备的唯一标识
                uriBuilder.addParameter("andid",request.getDevice().getAndroid_id());//Android设备的Android ID
                uriBuilder.addParameter("dpidmd5",request.getDevice().getAndroid_id_md5());//AndroidID或IDFA的MD5值
                uriBuilder.addParameter("dpidsha1",request.getDevice().getAndroid_id_sha1());//Android设备的sha1
            }else {//ios
                uriBuilder.addParameter("idfv",request.getDevice().getIdfv());//iOS系统的idfv 值
                uriBuilder.addParameter("sn",request.getDevice().getImei());//ios-设备唯一标识
            }
            uriBuilder.addParameter("mac",request.getDevice().getMac());//MAC地址，明文传输,默认为空字符串
            uriBuilder.addParameter("macmd5",request.getDevice().getMac_md5());//要求明文大写后MD5
            uriBuilder.addParameter("macsha1",request.getDevice().getMac_sha1());//要求明文大写后SHA1

            nop = request.getDevice().getCarrier();
            if (nop.equals("70120")){//中国移动
                uriBuilder.addParameter("nop","46000");//手机运营商代号
            }else if(nop.equals("70123")){//中国联通
                uriBuilder.addParameter("nop","46001");//手机运营商代号
            }else if(nop.equals("70121")){//中国电信
                uriBuilder.addParameter("nop","46003");//手机运营商代号
            }else{
                uriBuilder.addParameter("nop",nop);//手机运营商代号
            }

            if (2==request.getDevice().getConnectiontype()){//联网类型
                uriBuilder.addParameter("nt","wifi");
            }else if (4==request.getDevice().getConnectiontype()){
                uriBuilder.addParameter("nt","2g");
            }else if (5==request.getDevice().getConnectiontype()){
                uriBuilder.addParameter("nt","3g");
            }else if (6==request.getDevice().getConnectiontype()){
                uriBuilder.addParameter("nt","4g");
            }else if (7==request.getDevice().getConnectiontype()){
                uriBuilder.addParameter("nt","5g");
            }
            uriBuilder.addParameter("country",request.getDevice().getCountry());//国家
            uriBuilder.addParameter("language",request.getDevice().getLanguage());//语言

        }
       // if (null == request.getTest()|| request.getTest().equals("0")){
            uriBuilder.addParameter("tm","0");//正式模式：0,测试模式：1
//        }else {
//            uriBuilder.addParameter("tm","1");
//        }
        time = String.valueOf(System.currentTimeMillis());
        uriBuilder.addParameter("time", time);//请求时间的时间戳

        String token = appid+sn+os+nop+pack+time+secretKey;
        uriBuilder.addParameter("token", MD5Util.MD5Encode(token,false));//授权使用API 的授权码

        return uriBuilder;
    }

    /**
     * 快友公共返回方发
     * @param
     * @return
     */
    private TzBidResponse doKyResponse(String entityStr) {
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        List<TzSeat> seatList = new ArrayList<>();//竞价集合对象，若是竞价至少有一个
        //多层解析json
        JSONObject jo = JSONObject.parseObject(entityStr);
        Integer res = jo.getInteger("res");//广告回应情况标识:0=>失败， 1=> 成功
        String mg = jo.getString("mg");//广告回应失败的原因（适用于失败的情况）
        Integer co = jo.getInteger("co");//返回的广告条数
        log.info("请求失败消息" + mg);
        if (null != co) {
            log.info("返回广告数量" + co.toString());
        }
        if (1 == res) {//返回成功
            Object ad = jo.get("ad");//广告信息，正确返回 0 或多个广告
            JSONArray ads = JSONObject.parseArray(ad.toString());
            List<TzBid> bidList = new ArrayList<>();
            for (int i = 0; i < ads.size(); i++) {
                TzBid bid = new TzBid();//广告对象
                bid.setAdid(ads.getJSONObject(i).getString("posId"));//广告位id,原生视频广告时返回。
                bid.setAd_type(ads.getJSONObject(i).getInteger("at"));//返回的广告素材类型
                Integer at = ads.getJSONObject(i).getInteger("at");
                bid.setAd_type(at);//返回的广告素材类型
                bid.setAs(ads.getJSONObject(i).getString("as"));//广告尺寸。at =8 时不用返回。

                TzBidApps tba = new TzBidApps();//应用信息
                tba.setApp_name(ads.getJSONObject(i).getString("dan"));//下载类型：应用名称
                tba.setBundle(ads.getJSONObject(i).getString("dpn"));//广告应用的包名
                tba.setApp_icon(ads.getJSONObject(i).getString("dai"));//下载类型：应用图标
                tba.setApp_size(ads.getJSONObject(i).getInteger("das"));//下载类型：应用包大小
                bid.setApp(tba);

                if (null != ads.getJSONObject(i).getString("adWinPrice")) {
                    float price = Float.parseFloat(ads.getJSONObject(i).getString("adWinPrice")) / 10000;
                    //bid.setPrice(price);//广告CPM 价格。编码格式的 CPM 价格 *10000 ，如价格为 CPM 价格 0.6
                }

                if (at == 4 || at == 10) {
                    bid.setXs(ads.getJSONObject(i).getString("xs"));//广告物料，xs 为 HTML 代码段,ad_type=4|10 时填充
                }
                if (at == 3 || at == 5 || at == 6 || at == 7) {
                    if (null != ads.getJSONObject(i).getJSONObject("video")) {
                        TzVideo video = new TzVideo();
                        video.setUrl(ads.getJSONObject(i).getJSONObject("video").getString("videourl"));
                        video.setH(ads.getJSONObject(i).getJSONObject("video").getInteger("height"));
                        video.setW(ads.getJSONObject(i).getJSONObject("video").getInteger("width"));
                        video.setDuration(ads.getJSONObject(i).getJSONObject("video").getInteger("duration"));
                        bid.setVideo(video);//视频对象
                    }
                }
                if (at == 0 || at == 1 || at == 2 || at == 3 || at == 5) {
                    bid.setAd_type(5);//开屏
                    bid.setImage(ads.getJSONObject(i).getJSONArray("api").getString(0));//
                    bid.setAic(ads.getJSONObject(i).getString("aic"));//广告图标（Icon 的 url地址), ad_type =0|1|2|3|5 时填充
                    bid.setAte(ads.getJSONObject(i).getString("ate"));//插屏广告描述, ad_type =0|1|2|3|5 时填充
                    bid.setTitle(ads.getJSONObject(i).getString("ati"));//广告标题
                    bid.setSub_title(ads.getJSONObject(i).getString("ast"));//广告副标题
                    bid.setAbi(ads.getJSONObject(i).getString("abi"));//广告行为转化图标URL, ad_type =0|1|2|3|5 时填充
                    bid.setAdLogo(ads.getJSONObject(i).getString("adLogo"));//广告来源Logo, ad_type =0|1|2|3|5 时填充
                }
                if (at == 8 || at == 9) {
                    bid.setAd_type(8);//信息流
                    TzNative tzNative = new TzNative();
                    tzNative.setVer(ads.getJSONObject(i).getJSONObject("native").getString("ver"));//原生广告协议版本号

                    TzIcon icon = new TzIcon();
                    if (null != ads.getJSONObject(i).getJSONObject("native").getJSONObject("icon")) {
                        icon.setUrl(ads.getJSONObject(i).getJSONObject("native").getJSONObject("icon").getString("url"));//
                        icon.setType(ads.getJSONObject(i).getJSONObject("native").getJSONObject("icon").getInteger("type"));//
                        icon.setW(ads.getJSONObject(i).getJSONObject("native").getJSONObject("icon").getInteger("w"));//
                        icon.setH(ads.getJSONObject(i).getJSONObject("native").getJSONObject("icon").getInteger("h"));//
                        tzNative.setIcon(icon);//icon数据
                    }

                    TzLogo logo = new TzLogo();
                    if (null != ads.getJSONObject(i).getJSONObject("native").getJSONObject("logo")) {
                        logo.setUrl(ads.getJSONObject(i).getJSONObject("native").getJSONObject("logo").getString("url"));//
                        logo.setType(ads.getJSONObject(i).getJSONObject("native").getJSONObject("logo").getInteger("type"));//
                        logo.setW(ads.getJSONObject(i).getJSONObject("native").getJSONObject("logo").getInteger("w"));//
                        logo.setH(ads.getJSONObject(i).getJSONObject("native").getJSONObject("logo").getInteger("h"));//
                        tzNative.setLogo(logo);//logo数据
                    }

                    List<TzImage> imageList = new ArrayList<>();
                    Object imageObj = ads.getJSONObject(i).getJSONObject("native").get("images");//图片信息
                    JSONArray images = JSONObject.parseArray(imageObj.toString());
//                        JSONArray images = ads.getJSONObject(i).getJSONArray("images");//图片信息
                    if (null != images) {
                        for (int j = 0; j < images.size(); j++) {
                            TzImage image = new TzImage();
                            image.setUrl(images.getJSONObject(j).getString("url"));//图片地址
                            image.setType(images.getJSONObject(j).getInteger("type"));//图片类型
                            image.setW(images.getJSONObject(j).getInteger("w"));//图片宽
                            image.setH(images.getJSONObject(j).getInteger("h"));//图片高
                            imageList.add(image);
                        }
                    }
                    tzNative.setImages(imageList);//图片信息

                    TzVideo video = new TzVideo();
                    if (null != ads.getJSONObject(i).getJSONObject("native").getJSONObject("video")) {
                        video.setUrl(ads.getJSONObject(i).getJSONObject("native").getJSONObject("video").getString("url"));//视频广告素材地址URL
                        video.setW(ads.getJSONObject(i).getJSONObject("native").getJSONObject("video").getInteger("width"));//视频宽
                        video.setH(ads.getJSONObject(i).getJSONObject("native").getJSONObject("video").getInteger("height"));//视频高
                        video.setDuration(ads.getJSONObject(i).getJSONObject("native").getJSONObject("video").getInteger("duration"));//视频播放时长，单位秒
                        tzNative.setVideo(video);//视频素材
                    }
                    tzNative.setTitle(ads.getJSONObject(i).getJSONObject("native").getString("title"));//广告标题
                    tzNative.setDesc(ads.getJSONObject(i).getJSONObject("native").getString("desc"));//广告描述内容

                    bid.setNATIVE(tzNative);
                }

                /**
                 * 广告落地页相关字段
                 */
                String dlUrl = ads.getJSONObject(i).getString("dl");
                if(StringUtils.isNotEmpty(dlUrl)){
                    String dlUrls = dlUrl + "," + id + "," + ip + "," + name + "," + appName;
                    bid.setDeeplink_url("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink="+ Base64.encode(dlUrls));//包含deeplink的点击跳转地址，无法打开则使用 al
                }
               if(StringUtils.isNotEmpty(ads.getJSONObject(i).getString("al"))){
                   String al = ads.getJSONObject(i).getString("al") + "," + id + "," + ip + "," + name + "," + appName;
                   bid.setClick_url("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/click?clickurl="+Base64.encode(al));//点击跳转地址
               }
                bid.setFallback(ads.getJSONObject(i).getString("fallback"));//跳转替换地址。如果al 链接不支持，使用本址,ad_type=8 时可能返回该字段
                /**
                 * 小程序广告相关字段
                 */
                bid.setAptAppId(ads.getJSONObject(i).getString("aptAppId"));//小程序在微信开放平台注册并获取的id
                bid.setAptOrgId(ads.getJSONObject(i).getString("aptOrgId"));//小程序获取的appid
                bid.setAptPath(ads.getJSONObject(i).getString("aptPath"));//小程序的入口路径，由小程序开发者指定，如果没有是进入到小程序的首页
                bid.setAptType(ads.getJSONObject(i).getString("aptType"));//小程序类型
                bid.setAptUL(ads.getJSONObject(i).getString("aptUL"));//universalLink 微信 iOS SDK注册 的时候需要用到 的参数1
                /**
                 * 广告汇报相关字段
                 */
                if (null != ads.getJSONObject(i).getJSONArray("ec")) {
                    List<String> check_clicks = new ArrayList<>();
                    String check_clickss = JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("ec").toString(), String.class).toString();
                    String checkClicks = check_clickss.substring(1,check_clickss.length()-1) + "," + id + "," + ip + "," + name + "," + appName;
                    check_clicks.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicksf?checkClicks="+Base64.encode(checkClicks));
                    check_clicks.add(check_clickss.substring(1,check_clickss.length()-1));
                    bid.setCheck_clicks(check_clicks);//点击监控URL ，需要客户端逐条访问。
                }
                if (null != ads.getJSONObject(i).getJSONObject("es")) {
                    List<String> checkViews = new ArrayList<>();
                    String es = JSONObject.parseArray(ads.getJSONObject(i).getJSONObject("es").getJSONArray("0").toString(), String.class).toString();
                    String pv = es.substring(1,es.length()-1) + "," + id + "," + ip + "," + name + "," + appName;
                    checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pvf?pv="+Base64.encode(pv));
                    checkViews.add(es.substring(1,es.length()-1));
                    bid.setCheck_views(checkViews);//展示监控URL ，需要客户端按延迟逐条访问，仅在后台进行，预防展示跳转。
                }
                if (null != ads.getJSONObject(i).getJSONArray("dlsuc")) {
                    bid.setCheck_success_deeplinks(JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("dlsuc").toString(), String.class));//唤醒成功监测URL第三方曝光监测
                }
                if (null != ads.getJSONObject(i).getJSONArray("dlfail")) {
                    bid.setCheck_fail_deeplinks(JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("dlfail").toString(), String.class));//唤醒失败监测URL第三方曝光监测
                }
                bid.setAltype(ads.getJSONObject(i).getInteger("altype"));//0- 普通落地页1- 广点通专用落地页，需要替换广点通的点击坐标宏以及转化上报
                bid.setGdt_conversion_link(ads.getJSONObject(i).getString("gdt_conversion_link"));//广点通专用转化上报链接，需要替换转化上报宏字段
                if (null != ads.getJSONObject(i).getJSONArray("surl")) {
                    List<String> check_start_downloads = new ArrayList<>();
                    String check_start_downloadss = JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("surl").toString(), String.class).toString();
                    String surl = check_start_downloadss.substring(1,check_start_downloadss.length()-1) + "," + id + "," + ip + "," + name + "," + appName;
                    check_start_downloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart="+Base64.encode(surl));
                    bid.setCheck_start_downloads(check_start_downloads);//开始下载监测URL第三方曝光监测
                }
                if (null != ads.getJSONObject(i).getJSONArray("furl")) {
                    List<String> check_end_downloads = new ArrayList<>();
                    String check_end_downloadss = JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("furl").toString(), String.class).toString();
                    String furl = check_end_downloadss.substring(1,check_end_downloadss.length()-1) + "," + id + "," + ip + "," + name + "," + appName;
                    check_end_downloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/end?downloadEnd="+Base64.encode(furl));
                    bid.setCheck_end_downloads(check_end_downloads);//结束下载监测URL第三方曝光监测
                }
                if (null != ads.getJSONObject(i).getJSONArray("iurl")) {
                    List<String> check_start_installs = new ArrayList<>();
                    String check_start_installss = JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("iurl").toString(), String.class).toString();
                    String iurl = check_start_installss.substring(1,check_start_installss.length()-1) + "," + id + "," + ip + "," + name + "," + appName;
                    check_start_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/start?installStart="+Base64.encode(iurl));
                    bid.setCheck_start_installs(check_start_installs);//开始安装监测URL第三方曝光监测
                }
                if (null != ads.getJSONObject(i).getJSONArray("ourl")) {
                    List<String> check_end_installs = new ArrayList<>();
                    String check_end_installss = JSONObject.parseArray(ads.getJSONObject(i).getJSONArray("ourl").toString(), String.class).toString();
                    String ourl = check_end_installss.substring(1,check_end_installss.length()-1) + "," + id + "," + ip + "," + name + "," + appName;
                    check_end_installs.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/end?installEnd="+Base64.encode(ourl));
                    bid.setCheck_end_installs(check_end_installs);//结束安装监测URL第三方曝光监测
                }
                bid.setSource(name);//素材来源
                bidList.add(bid);//素材集合
            }
            TzSeat seat = new TzSeat();//竞价集合对象，若是竞价至少有一个
            seat.setBid(bidList);
            seat.setSeat(jo.getString("seat"));
            seatList.add(seat);

            bidResponse.setSeatbid(seatList);//广告集合对象
        } else if (0 == res) {//返回失败
            log.info("快友回应失败，无数据！！！");
        }
        return bidResponse;
    }

}
