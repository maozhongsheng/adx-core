package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.yidianzixun.*;
import com.mk.adx.entity.json.response.mk.*;
import com.mk.adx.service.YdzxJsonService;
import com.mk.adx.util.AESUtil;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 一点资讯
 * @Date 2020/10/28 9:48
 */
@Slf4j
@Service("ydzxJsonService")
public class YdzxJsonServiceImpl implements YdzxJsonService {

    private static final String name = "ydzx";
    private static final String source = "一点资讯";

    @SneakyThrows
    @Override
    public MkBidResponse getYdzxDataByJson(MkBidRequest request) {
        List<YdzxImp> impList = new ArrayList<>();//imp集合
        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){
                YdzxImp imp = new YdzxImp();//Imp
                String os = request.getDevice().getOs();
                if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
                    imp.setSlot_id(request.getAdv().getTag_id());//广告位的唯一标识符，由一点提供
                }
                imp.setBidfloor(2);   //可变
                impList.add(imp);
            }
        }

        YdzxDevice device = new YdzxDevice();
        if (null!=request.getDevice()){
            YdzxGeo geo = new YdzxGeo();//Geo
            if (null!=request.getDevice().getGeo()){
                geo.setLat(request.getDevice().getGeo().getLat());//经度
                geo.setLon(request.getDevice().getGeo().getLon());//纬度
            }
//
//            YdzxSite site = new YdzxSite();//site
//            if (null != request.getSite()){
//                site.setDomain(request.getSite().getDomain());//网站域名
//                site.setPage(request.getSite().getPage());//当前页面URL
//                site.setRef(request.getSite().getRef());//Referrer URL
//            }

            device.setGeo(geo);//设备位置信息
            device.setUa(request.getDevice().getUa());//浏览器的User-Agent属性字符串
            device.setIp(request.getDevice().getIp());//ipv4地址   223.104.160.99
            device.setIpv6(request.getDevice().getIpv6());//ipv6地址
            String dtype = request.getDevice().getDevicetype();//设备类型 手机：phone平板：ipadPC：pc互联网电视：tv
            if (dtype.equals("phone")){
                device.setDevicetype(1);//手机
            }else if (dtype.equals("ipad")){
                device.setDevicetype(2);//平板
            }else if (dtype.equals("pc")){
                device.setDevicetype(3);//PC
            }else if (dtype.equals("tv")){
                device.setDevicetype(4);//智能电视
            }else {
                device.setDevicetype(0);//未知
            }
            String os = request.getDevice().getOs();//操作系统，0=>Android,1=>iOS
            if( os.equals("0") || os.equals("android")|| os.equals("ANDROID")){
                device.setOs("android");
                device.setDid(request.getDevice().getImei());//硬件设备ID，对Android而言是imei，对ios而言是idfa原值和md5值必传其一
                device.setDidmd5(request.getDevice().getImei_md5());//
                device.setDidsha1(request.getDevice().getImei_sha1());//
            }else if (os.equals("1")||os.equals("ios")|| os.equals("IOS")){
                device.setOs("ios");
                device.setDid(request.getDevice().getIdfv());//硬件设备ID，对Android而言是imei，对ios而言是idfa原值和md5值必传其一
            }
            device.setOsv(request.getDevice().getOsv());//系统版本
            if (null!=request.getDevice().getW()){
                device.setW(request.getDevice().getW());//物理屏幕宽度
            }
            if (null!=request.getDevice().getH()){
                device.setH(request.getDevice().getH());//物理屏幕高度
            }
            device.setMake(request.getDevice().getMake());//制造厂商
            device.setModel(request.getDevice().getModel());//品牌型号
            if (null!=request.getDevice().getPpi()){
                device.setPpi(request.getDevice().getPpi());//屏幕密度大小
            }
            device.setPxrate(request.getDevice().getPxratio());//物理设备大小比值
            device.setConnectiontype(request.getDevice().getConnectiontype());//设备连接类型，取值范围：1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G,7:5G

            String carrier = request.getDevice().getCarrier();  //运营商-中国移动:70120,中国联通:70123，中国电信:70121,广电:70122 其他:70124,未识别:0
            if(StringUtils.isNotEmpty(carrier)){
                if (carrier.equals("70120")){
                    device.setOperatortype(1);
                }else if (carrier.equals("70121")){
                    device.setOperatortype(4);
                }else if (carrier.equals("70122")){
                    device.setOperatortype(1);
                }else if (carrier.equals("70123")){
                    device.setOperatortype(3);
                }else if (carrier.equals("70124")){
                    device.setOperatortype(99);
                }else {
                    device.setOperatortype(0);
                }
            }else{
                device.setOperatortype(0);
            }
            device.setLanguage(request.getDevice().getLanguage());//浏览语言ISO-639-1-alpha-2
            device.setOaid(request.getDevice().getOaid());//移动安全联盟推出的匿名设备标识符
            device.setMac(request.getDevice().getMac());//MAC地址，明文传输，默认为空字符串
            device.setMac_md5(request.getDevice().getMac_md5());//
            device.setMac_sha1(request.getDevice().getMac_sha1());//
            if(null != request.getDevice().getAppstore_ver()){
                device.setVercodeofag(request.getDevice().getAppstore_ver());
            }
            if(null != request.getDevice().getVercodeofhms()){
                device.setVercodeofhms(request.getDevice().getVercodeofhms());
            }
            }

        YdzxApp app = new YdzxApp();//App
        if (null!=request.getApp()){
            app.setName(request.getAdv().getApp_name());//媒体app名称
            app.setBundle(request.getAdv().getBundle());//应用程序包或包名称
            app.setVer(request.getAdv().getVersion());//app应用版本
        }

        YdzxUser user = new YdzxUser();//User
        if (null!=request.getUser()){
            user.setAge(request.getUser().getAge());//年龄
            String gender = request.getUser().getGender();//性别
            if (gender.equals("M")){
                user.setGender(1);
            }else if (gender.equals("O")){
                user.setGender(2);
            }else if (gender.equals("f")){
                user.setGender(3);
            }
        }

        YdzxExt ext = new YdzxExt();
        ext.setDeeplinkSupported(true);

        YdzxBidRequest bidRequest = new YdzxBidRequest();//总请求
        bidRequest.setId(request.getId());//请求id	接入方自定义，确保唯一性。否则影响填充
        bidRequest.setImp(impList);//广告位曝光对象，暂时只支持一个
        bidRequest.setDevice(device);//设备信息
        bidRequest.setApp(app);//应用信息
        bidRequest.setUser(user);//用户信息
        bidRequest.setTest(false);//测试
        bidRequest.setExt(ext);//媒体方是否支持deeplink 吊起true支持，false不支持，默认不支持
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求一点资讯数据"+content);

        MkBidResponse bidResponse = new MkBidResponse();//总返回
        String url = "http://dsp2.yidianzixun.com/bid?adxType=zhongshilimei_1120&rtbVersion=3";//测试url
        String ua = request.getDevice().getUa();//ua
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid()+"：请求上游一点资讯花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info(request.getImp().get(0).getTagid()+"：一点资讯返回参数"+JSONObject.parseObject(response));
         if (null != response) {
             //多层解析json
             JSONObject jo = JSONObject.parseObject(response);
             String id = request.getId();//请求id
             Object jj = jo.get("seatBid");
             if (null == jj) {
                 return bidResponse;
             }
             JSONArray seatids = JSONObject.parseArray(jj.toString());
             for (int i = 0; i < seatids.size(); i++) {
                 List<MkBid> bidList = new ArrayList<>();
                 JSONArray bids = seatids.getJSONObject(i).getJSONArray("bid");
                 for (int j = 0; j < bids.size(); j++) {
                     MkBid tb = new MkBid();
                     if (null != bids.getJSONObject(j).getInteger("price")) {
                         tb.setPrice(bids.getJSONObject(j).getInteger("price"));//CPM出价，单位分
                     }

                     PostUtilDTO puds = new PostUtilDTO();//工具类请求参数
                     puds.setUrl(bids.getJSONObject(j).getString("nurl").replace("%%WIN_PRICE%%", "0"));//请求路径
                     puds.setUa(ua);//ua
                     HttppostUtil.doJsonPost(puds);

                     String Asckey = AESUtil.encrypt("204");
                     String encode = Base64.encode(Asckey);
                     String gbk = URLEncoder.encode(encode, "GBK");


                     int ctype = bids.getJSONObject(j).getInteger("ctype");//广告点击类型：1：跳转 2：下载
                     if (ctype == 1) {
                         tb.setClicktype("1");//跳转
                     } else if (ctype == 2) {
                         tb.setClicktype("3");//下载
                     } else {
                         tb.setClicktype("0");//下载
                     }
                     int templateid = bids.getJSONObject(j).getInteger("templateid");//参与竞价的广告位模板类型
                     if (templateid == 1 || templateid == 2 || templateid == 3 || templateid == 4 || templateid == 5) {
                         tb.setAd_type(1);//信息流-广告素材类型
                     } else if (templateid == 6) {
                         tb.setAd_type(2);//开屏-广告素材类型
                     }
                     if ("4".equals(request.getImp().get(0).getSlot_type())) {
                         tb.setAd_type(5);//开屏-广告素材类型
                     }
                     /**
                      * 视频
                      */
                     MkVideo video = new MkVideo();
                     if (null != bids.getJSONObject(j).getJSONObject("video")) {
                         video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("videourl"));//视频广告素材地址URL
                         video.setW(bids.getJSONObject(j).getJSONObject("video").getInteger("w"));//视频宽
                         video.setH(bids.getJSONObject(j).getJSONObject("video").getInteger("h"));//视频高
                         video.setDuration(bids.getJSONObject(j).getJSONObject("video").getInteger("videoduration"));//视频播放时长，单位秒
                         tb.setVideo(video);//视频素材
                     }

                     /**
                      * 图片
                      */
                     List<MkImage> tzImages = new ArrayList<>();//天卓图片素材
                     if (null != bids.getJSONObject(j).getJSONArray("aurl")) {
                         JSONArray ydzxImages = bids.getJSONObject(j).getJSONArray("aurl");//一点资讯图片素材
                         for (int ima = 0; ima < ydzxImages.size(); ima++) {
                             MkImage image = new MkImage();
                             image.setUrl(ydzxImages.get(ima).toString());
                             image.setW(1280);
                             image.setH(720);
                             tzImages.add(image);
                         }
                     }

                     tb.setImages(tzImages);


                     tb.setClick_url(bids.getJSONObject(j).getString("curl"));//用户点击后需要跳转到的落地页
                     tb.setDownload_url(bids.getJSONObject(j).getString("durl"));//下载地址

                     String deeplinkurl = bids.getJSONObject(j).getString("deeplinkurl");
                     if (StringUtils.isNotEmpty(deeplinkurl)) {
                         tb.setDeeplink_url(deeplinkurl);//deeplink链接，如返回结果中包括deeplink链接则调起第三方应用，否则跳转落地页

                     }

                     tb.setTitle(bids.getJSONObject(j).getString("title"));//广告标题
                     tb.setSource(bids.getJSONObject(j).getString("source"));//广告主名称

                     if (null != bids.getJSONObject(j).getJSONObject("appInfo")) {
                         JSONObject appInfo = bids.getJSONObject(j).getJSONObject("appInfo"); //app信息
                         MkApp mkBidApps = new MkApp();
                         mkBidApps.setApp_name(appInfo.getString("appName"));
                         tb.setApp(mkBidApps);
                     }

                     String encode2 = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

                     List<String> checkClicks = new ArrayList<>();
                     JSONArray cmurl = bids.getJSONObject(j).getJSONArray("cmurl");//点击监测地址数组
                     if (null != cmurl) {
                         checkClicks.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode2));
                         for (int cm = 0; cm < cmurl.size(); cm++) {
                             checkClicks.add(cmurl.get(cm).toString());
                         }
                         tb.setCheck_clicks(checkClicks);
                     }

                     List<String> checkViews = new ArrayList<>();//曝光监测地址数组
                     JSONArray murl = bids.getJSONObject(j).getJSONArray("murl");
                     if (null != murl) {
                         checkViews.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode2));
                         for (int mu = 0; mu < murl.size(); mu++) {
                             String s = murl.get(mu).toString();
                             if (s.indexOf("%%WIN_PRICE%%") != -1) {
                                 String replace = s.replace("%%WIN_PRICE%%", "0");
                                 checkViews.add(replace);
                             } else {
                                 checkViews.add(s);
                             }
                         }
                         tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                     }

                     List<String> checkStartDownloads = new ArrayList<>();
                     JSONArray dmurl = bids.getJSONObject(j).getJSONArray("dmurl");//app 下载开始的监测地址
                     if (null != dmurl) {
                         checkStartDownloads.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode2));
                         for (int cm = 0; cm < cmurl.size(); cm++) {
                             checkStartDownloads.add(cmurl.get(cm).toString());
                         }
                         tb.setCheck_start_downloads(checkStartDownloads);//曝光监测URL,支持宏替换第三方曝光监测
                     }

                     List<String> checkEndDownloads = new ArrayList<>();
                     JSONArray downsuccessurl = bids.getJSONObject(j).getJSONArray("downsuccessurl");//app 下载完成的监测地址
                     if (null != downsuccessurl) {
                         checkEndDownloads.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode2));
                         for (int dle = 0; dle < downsuccessurl.size(); dle++) {
                             checkEndDownloads.add(downsuccessurl.get(dle).toString());
                         }
                         tb.setCheck_end_downloads(checkEndDownloads);
                     }

                     List<String> checkSuccessDeeplinks = new ArrayList<>();
                     JSONArray deeplinkmurl = bids.getJSONObject(j).getJSONArray("deeplinkmurl");//仅用于唤醒广告，deeplink 链接调起成功
                     if (null != deeplinkmurl) {
                         checkSuccessDeeplinks.add("http://adx.fxlxz.com/sl/dp_success?deeplink="+ Base64.encode(encode2));
                         for (int dp = 0; dp < deeplinkmurl.size(); dp++) {
                             checkSuccessDeeplinks.add(deeplinkmurl.get(dp).toString());
                         }
                         tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                     }

                     bidList.add(tb);//素材集合
                 }
                 bidResponse.setId(id);//请求id
                 bidResponse.setBidid(id);//请求id
                 bidResponse.setSeatbid(bidList);//广告集合对象
                 bidResponse.setProcess_time_ms(tempTime);
             }

             log.info(request.getImp().get(0).getTagid() + "：一点资讯总返回数据" + JSONObject.toJSONString(bidResponse));
         }
        return bidResponse;
    }

}
