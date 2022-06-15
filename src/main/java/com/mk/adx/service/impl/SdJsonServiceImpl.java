package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.shidai.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.SdJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 时代广告-滴滴
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("sdJsonService")
public class SdJsonServiceImpl implements SdJsonService {

    private static final String name = "sd";
    private static final String source = "时代滴滴";
    /**
     * @Author mzs
     * @Description 时代广告-滴滴
     * @Date 2021/6/28 9:48
     */
    @SneakyThrows
    @Override
    public TzBidResponse getSdDataByJson(TzBidRequest request) {
        TzBidResponse bidResponse = new TzBidResponse();//总返回
        List<SdImp> impList = new ArrayList<>();//imp集合
        SdApp app = new SdApp();//Apps
        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){

                SdBanner banner = new SdBanner();//banner
                if (null!=request.getImp().get(i).getBanner()){
                    if (null!=request.getImp().get(i).getBanner().getW()){
                        banner.setW(request.getImp().get(i).getBanner().getW());//广告位宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getH()){
                        banner.setH(request.getImp().get(i).getBanner().getH());//广告位高度
                    }

                    if (null!=request.getImp().get(i).getBanner().getWmin()){
                        banner.setWmin(request.getImp().get(i).getBanner().getWmin());//最小宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getWmax()){
                        banner.setWmax(request.getImp().get(i).getBanner().getWmax());//最小宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getHmin()){
                        banner.setHmin(request.getImp().get(i).getBanner().getHmin());//最小高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getHmax()){
                        banner.setHmax(request.getImp().get(i).getBanner().getHmax());//最小高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getMimes()){
                        banner.setMimes(request.getImp().get(i).getBanner().getMimes());//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif
                    }
                }

                SdVideo video = new SdVideo();//video
                if (null!=request.getImp().get(i).getVideo()){
                    if (null!=request.getImp().get(i).getVideo().getH()){
                        video.setH(request.getImp().get(i).getVideo().getH());//广告位高
                    }
                    if (null!=request.getImp().get(i).getVideo().getW()){
                        video.setW(request.getImp().get(i).getVideo().getW());//广告位宽
                    }
                    if (null!=request.getImp().get(i).getVideo().getMaxduration()){
                        video.setMaxduration(request.getImp().get(i).getVideo().getMaxduration());//最大的视频广告持续时间以秒为单位
                    }
                    if (null!=request.getImp().get(i).getVideo().getMinduration()){
                        video.setMinduration(request.getImp().get(i).getVideo().getMinduration());//最小的视频广告持续时间以秒为单位
                    }
                    if (null!=request.getImp().get(i).getVideo().getMimes()){
                        video.setMimes(request.getImp().get(i).getVideo().getMimes());//==内容支持MIME的类型,video/mp4
                    }
                }




                SdImp imp = new SdImp();//Imp
                String impId = request.getImp().get(i).getId() + System.currentTimeMillis();
                imp.setId(impId);//曝光id

                imp.setTagid(request.getAdv().getTag_id());
                if(request.getAdv().getSlot_type().equals("信息流")){
                    imp.setType(4);
                }else if(request.getAdv().getSlot_type().equals("开屏")){
                    imp.setType(2);
                }
                if(0 != request.getAdv().getPrice()){
                    imp.setBidfloor(request.getAdv().getPrice()/10 + 100);//底价,单位：分/CPM
                }
                imp.setDplink(1);
                banner.setW(Integer.valueOf(request.getAdv().getSize().split("\\*")[0]));
                banner.setH(Integer.valueOf(request.getAdv().getSize().split("\\*")[1]));
                imp.setBanner(banner);
                imp.setVideo(video);
                impList.add(imp);

            }
        }

        if (null!=request.getApp()){
            app.setId(request.getAdv().getApp_id());//
            app.setName(request.getAdv().getApp_name());//媒体app名称
            app.setBundle(request.getAdv().getBundle());//应用程序包或包名称
            app.setVer(request.getAdv().getVersion());//app应用版本
        }


        SdDevice device = new SdDevice();
        if (null!=request.getDevice()){
            SdGeo geo = new SdGeo();//Geo
            if (null!=request.getDevice().getGeo()){
                geo.setLat(request.getDevice().getGeo().getLat());//经度
                geo.setLon(request.getDevice().getGeo().getLon());//纬度
                geo.setType(request.getDevice().getGeo().getType());//源的位置数据	建议当纬度/经度
                geo.setCountry(request.getDevice().getGeo().getCountry());//国家	使用 ISO-3166-1 Alpha-3数据
                geo.setRegion(request.getDevice().getGeo().getRegion());//省份
                geo.setCity(request.getDevice().getGeo().getCity());//城市

            }
            device.setUa(request.getDevice().getUa());
            device.setGeo(geo);//设备位置信息
            device.setIp(request.getDevice().getIp());//ipv4地址    request.getDevice().getIp()  39.190.239.168
            if("phone".equals(request.getDevice().getDevicetype())){
                device.setDevicetype(1);//设备类型	手机
            }else
            if("tablet".equals(request.getDevice().getDevicetype())){
                device.setDevicetype(2);//设备类型	平板
            }
            else{
                device.setDevicetype(0);
            }
            device.setMake(request.getDevice().getMake());//制造厂商
            device.setModel(request.getDevice().getModel());
            String os = request.getDevice().getOs();//终端操作系统类型:0=>Android,1=>iOS
            if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
                device.setOs("ANDROID");
                device.setDid(request.getDevice().getImei());//IMEI，明文传输，默认为空字符串
                device.setDidmd5(request.getDevice().getImei_md5());//
                device.setDpid(request.getDevice().getAndroid_id());//安卓id	android系统必填,Settings.Secure.getString(context.getContentReso lver(),Settings.Secure.ANDROID_ID);
                device.setDpidmd5(request.getDevice().getAndroid_id_md5());//
                device.setMac(request.getDevice().getMac());//MAC地址，明文传输，默认为空字符串
                device.setMacmd5(request.getDevice().getMac_md5());//
                device.setOaid(request.getDevice().getOaid());
                device.setStartup_time("xsdsld-411a-47bc-sdds-744a4e7e0723");
                device.setMb_time("1004697.709999999");
            }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)){
                device.setOs("IOS");
                if(StringUtils.isEmpty(request.getDevice().getIdfa())){
                    return bidResponse;
                }
                device.setIdfa(request.getDevice().getIdfa());//IDFA，明文传输，默认为空字符串
                device.setIdfamd5(request.getDevice().getIdfa_md5());//
                device.setCaid(request.getDevice().getCaid());
                device.setOpenudid(request.getDevice().getOpen_udid());
                device.setIdfv(request.getDevice().getIdfv());
                device.setStartup_time("1623815045.970028");
                device.setMb_time("1581141691.570419583");
                device.setDisk_total(Long.valueOf("250685575168"));
                device.setMem_total(Long.valueOf("17179869184"));
            }
            device.setOsv(request.getDevice().getOsv());//系统版本
            if (null != request.getDevice().getH()){
                device.setH(request.getDevice().getH());//物理屏幕高度
            }
            if (null != request.getDevice().getW()){
                device.setW(request.getDevice().getW());//物理屏幕宽度
            }
            if (null != request.getDevice().getPpi()){
                device.setPpi(request.getDevice().getPpi());//屏幕尺寸
            }
            if (0 != request.getDevice().getDeny()){
                device.setDip(request.getDevice().getDeny());//屏幕密度大小
            }
            if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
                if("70120".equals(request.getDevice().getCarrier())){
                    device.setCarrier(46000);
                }else if("70123".equals(request.getDevice().getCarrier())){
                    device.setCarrier(46001);
                }else if("70121".equals(request.getDevice().getCarrier())){
                    device.setCarrier(46003);
                }else{
                    device.setCarrier(46000);
                }
            }else{
                device.setCarrier(46000);
            }

            device.setConnectiontype(request.getDevice().getConnectiontype());//网络链接类型  0:Unknown,1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G,7:5G
            if (null!=request.getDevice().getOrientation()){
                device.setOrientation(request.getDevice().getOrientation());//横竖屏:0°,90°,180°,270°,-1未知
            }
        }
        device.setAppstore_ver(request.getDevice().getAppstore_ver());

        SdUser user = new SdUser();//User
        if (null!=request.getUser()){
            user.setId(request.getUser().getId());//用户id
            user.setYob(request.getUser().getYob());//出生年份4位整数
            user.setGender(request.getUser().getGender());//性别
            user.setKeywords(request.getUser().getKeywords());//逗号分隔的列表关键字
        }
        SdBidRequest bidRequest = new SdBidRequest();//总请求
        bidRequest.setId(request.getId());//请求id	接入方自定义，确保唯一性。否则影响填充
        bidRequest.setImp(impList);//广告位曝光对象，暂时只支持一个
        bidRequest.setApp(app);//应用信息
        bidRequest.setDevice(device);//设备信息
        bidRequest.setUser(user);//用户信息
        bidRequest.setVersion("1.0.0");
        bidRequest.setTmax(300);
        if(null != request.getTest()){
            bidRequest.setTest(request.getTest());
        }else{
            bidRequest.setTest(0);
        }

        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求时代广告参数"+JSONObject.parseObject(content));
        String url = "http://adx.timewit.cn/ads/bid";   //http://8.134.52.3/ads/bid
        String ua = request.getDevice().getUa();
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
        log.info("请求上游时代广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("时代广告返回参数"+JSONObject.parseObject(response));
        if (null != response) {
            List<TzMacros> tzMacros1 = new ArrayList();
            TzMacros tzMacros = new TzMacros();
            List<TzSeat> seatList = new ArrayList<>();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            String id = request.getId();//请求id
            String bidid = jo.getString("bidid");//请求id
            Object jj = jo.get("seatbid");
            JSONArray seatids = JSONObject.parseArray(jj.toString());
            for (int i = 0; i < seatids.size(); i++) {
                List<TzBid> bidList = new ArrayList<>();
                JSONArray bids = seatids.getJSONObject(i).getJSONArray("bid");
                for (int j = 0; j < bids.size(); j++) {
                    TzBid tb = new TzBid();
                    tb.setId(bids.getJSONObject(j).getString("id"));
                    tb.setImpid(bids.getJSONObject(j).getString("impid"));
                    if (null != bids.getJSONObject(j).getInteger("price")) {
                        tb.setPrice(bids.getJSONObject(j).getInteger("price"));
                    }
                    tb.setAdid(bids.getJSONObject(j).getString("adid"));
                    tb.setNurl(bids.getJSONObject(j).getString("nurl"));
                    if(null != bids.getJSONObject(j).getJSONArray("adomain")){
                        tb.setAdomain(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("adomain").toString(),String.class));
                    }
                    tb.setCrid(bids.getJSONObject(j).getString("crid"));
                    tb.setDealid(bids.getJSONObject(j).getString("dealid"));

                    if("3".equals(request.getImp().get(i).getAd_slot_type())){
                        tb.setAd_type(5);//开屏-广告素材类型
                        if(null != bids.getJSONObject(j).getJSONObject("logo")){
                            String adLogo = bids.getJSONObject(j).getJSONObject("logo").getString("url"); //Logo 图片地址
                            String source = bids.getJSONObject(j).getJSONObject("logo").getString("source"); //Logo 来源描述
                            tb.setAdLogo(adLogo);
                            tb.setAte(source);
                        }

                        if(null != bids.getJSONObject(j).getJSONObject("icon")){
                            String iconUrl = bids.getJSONObject(j).getJSONObject("icon").getString("url"); //图片地址
                            String w = bids.getJSONObject(j).getJSONObject("icon").getString("w"); //图片宽
                            String h = bids.getJSONObject(j).getJSONObject("icon").getString("h"); //图片高
                            String type = bids.getJSONObject(j).getJSONObject("icon").getString("type"); //图片类型，例如：image/jpg
                            String size = bids.getJSONObject(j).getJSONObject("icon").getString("size"); //图片大小，单位 kb
                            tb.setAic(iconUrl);
                        }

                        JSONArray sdImages =  bids.getJSONObject(j).getJSONArray("images");
                        ArrayList<TzImage> images = new ArrayList<>();
                        if(null != sdImages) {
                            for (int im = 0; im < sdImages.size(); im++) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(sdImages.getJSONObject(im).getString("url"));
                                tzImage.setW(sdImages.getJSONObject(im).getInteger("w"));
                                tzImage.setH(sdImages.getJSONObject(im).getInteger("h"));
                                // tzImage.setType(sdImages.getJSONObject(im).getInteger("type"));
                                // sdImages.getJSONObject(im).getString("size");
                                images.add(tzImage);
                            }
                            tb.setTitle(bids.getJSONObject(j).getString("title"));
                            tb.setDesc(bids.getJSONObject(j).getString("desc"));
                            tb.setImages(images);
                        }

                         // 开屏视频
                        if (null != bids.getJSONObject(j).getJSONObject("video")) {
                            TzVideo video = new TzVideo();
                            video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("url"));//视频广告素材地址URL
                            video.setW(bids.getJSONObject(j).getJSONObject("video").getInteger("w"));//视频宽
                            video.setH(bids.getJSONObject(j).getJSONObject("video").getInteger("h"));//视频高
                            video.setDuration(bids.getJSONObject(j).getJSONObject("video").getInteger("duration"));//视频播放时长，单位秒
                            tb.setVideo(video);//视频素材
                        }
                    }else{
                        TzNative tzNative = new TzNative();
                        TzIcon tzIcon = new TzIcon();
                        tb.setAd_type(8);//信息流-广告素材类型
                        if(null != bids.getJSONObject(j).getJSONObject("icon")){
                            tzIcon.setUrl(bids.getJSONObject(j).getJSONObject("icon").getString("url"));//图片地址
                            tzIcon.setW(bids.getJSONObject(j).getJSONObject("icon").getInteger("w")); //图片宽
                            tzIcon.setH(bids.getJSONObject(j).getJSONObject("icon").getInteger("h")); //图片高
                            tzNative.setIcon(tzIcon);
                        }
                        TzLogo tzLogo = new TzLogo();
                        if(null != bids.getJSONObject(j).getJSONObject("logo")){
                           tzLogo.setUrl( bids.getJSONObject(j).getJSONObject("logo").getString("url")); //Logo 图片地址
                            tzNative.setLogo(tzLogo);
                        }

                        JSONArray sdImages =  bids.getJSONObject(j).getJSONArray("images");
                        ArrayList<TzImage> images = new ArrayList<>();
                        if(null != sdImages) {
                            for (int im = 0; im < sdImages.size(); im++) {
                                TzImage tzImage = new TzImage();
                                tzImage.setUrl(sdImages.getJSONObject(im).getString("url"));
                                tzImage.setW(sdImages.getJSONObject(im).getInteger("w"));
                                tzImage.setH(sdImages.getJSONObject(im).getInteger("h"));
                                // tzImage.setType(sdImages.getJSONObject(im).getInteger("type"));
                                // sdImages.getJSONObject(im).getString("size");
                                images.add(tzImage);
                            }
                            tzNative.setTitle(bids.getJSONObject(j).getString("title"));
                            tzNative.setDesc(bids.getJSONObject(j).getString("desc"));
                            tzNative.setImages(images);
                        }

                        // 信息流视频
                        if (null != bids.getJSONObject(j).getJSONObject("video")) {
                            TzVideo video = new TzVideo();
                            video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("url"));//视频广告素材地址URL
                            video.setW(bids.getJSONObject(j).getJSONObject("video").getInteger("w"));//视频宽
                            video.setH(bids.getJSONObject(j).getJSONObject("video").getInteger("h"));//视频高
                            video.setDuration(bids.getJSONObject(j).getJSONObject("video").getInteger("duration"));//视频播放时长，单位秒
                            tzNative.setVideo(video);
                        }
                        tb.setNATIVE(tzNative);
                    }

                        if(null != bids.getJSONObject(j).getJSONObject("app")) {
                        TzBidApps tzBidApps = new TzBidApps();
                        tzBidApps.setApp_name(bids.getJSONObject(j).getJSONObject("app").getString("name"));//下载类型：应用名称
                        tzBidApps.setBundle(bids.getJSONObject(j).getJSONObject("app").getString("bundle"));//广告应用的包名
                        tzBidApps.setApp_size(bids.getJSONObject(j).getJSONObject("app").getInteger("size"));//下载类型：应用包大小
                        if(null != bids.getJSONObject(j).getJSONObject("app").getJSONObject("icon")) {
                            tzBidApps.setApp_icon(bids.getJSONObject(j).getJSONObject("app").getJSONObject("icon").getString("url"));//下载类型：应用图标
                        }
                        tb.setApp(tzBidApps);
                    }

                    Integer isdown = bids.getJSONObject(j).getInteger("isdown");//是否是下载类：0-不是，1-是
                    if(0 == isdown){
                        tb.setClicktype("1");//跳转
                    }else if(1 == isdown){
                        tb.setClicktype("3");//下载
                    }else{
                        tb.setClicktype("0");
                    }
                    if(1 == bids.getJSONObject(j).getInteger("isdeep")){ //是否Deeplink唤起⼴告：0-不是，1-是
                        tb.setClicktype("2");//Deeplink唤起⼴告
                    }

                    tb.setClick_url(bids.getJSONObject(j).getString("clickurl")); // 点击跳转url地址

                    if(StringUtils.isNotEmpty(bids.getJSONObject(j).getString("deeplink"))){
                        tb.setDeeplink_url(bids.getJSONObject(j).getString("deeplink")); //deeplink 地址
                    }

                    Object tracking = bids.getJSONObject(j).get("tracking");
                    JSONArray report = JSONObject.parseArray(tracking.toString());

                    for (int re = 0 ; re<report.size(); re++) {
                        String event = report.getJSONObject(re).getString("event");
                        if("IMP".equals(event)){
                            List<String> check_views = new ArrayList<>();
                            check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            for (int cv = 0; cv < urls1.size(); cv++) {
                                urls1.get(cv).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                check_views.add(urls1.get(cv).toString());
                            }
                            String check_Views =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_VIEWS%%");
                            tzMacros.setValue(Base64.encode(check_Views));
                            tzMacros1.add(tzMacros);
                        }else if("CLICK".equals(event)){
                            List<String> clickList = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                urls1.get(cc).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                clickList.add(urls1.get(cc).toString());
                            }
                            String check_Clicks = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_CLICKS%%");
                            tzMacros.setValue(Base64.encode(check_Clicks));
                            tzMacros1.add(tzMacros);
                        }else if("DOWN_START".equals(event)){
                            List<String> checkStart = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            checkStart.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%CHECK_START_DOWNLOADS%%");
                            for (int ds = 0; ds < urls1.size(); ds++) {
                                urls1.get(ds).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                checkStart.add(urls1.get(ds).toString());
                            }
                            String checkStartDownload = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_downloads(checkStart);//开始下载监测URL 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_START_DOWNLOADS%%");
                            tzMacros.setValue(Base64.encode(checkStartDownload));
                            tzMacros1.add(tzMacros);
                        }else if("DOWN_END".equals(event)){
                            List<String> checkEnd = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            checkEnd.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/end?downloadEnd=%%CHECK_END_DOWNLOADS%%");
                            for (int de = 0; de < urls1.size(); de++) {
                                urls1.get(de).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                checkEnd.add(urls1.get(de).toString());
                            }
                            String checkEndDownload = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_downloads(checkEnd);//结束下载监测URL 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_END_DOWNLOADS%%");
                            tzMacros.setValue(Base64.encode(checkEndDownload));
                            tzMacros1.add(tzMacros);
                        }else if("INSTALL_START".equals(event)){
                            List<String> checkStartIn = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            checkStartIn.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/start?installStart=%%CHECK_START_INSTALLS%%");
                            for (int is = 0; is < urls1.size(); is++) {
                                urls1.get(is).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                checkStartIn.add(urls1.get(is).toString());
                            }
                            String checkStartInstall =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_start_installs(checkStartIn);//开始安装监测URL 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_START_INSTALLS%%");
                            tzMacros.setValue(Base64.encode(checkStartInstall));
                            tzMacros1.add(tzMacros);
                        }else if("INSTALL_END".equals(event)){
                            List<String> checkEndIn = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            checkEndIn.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/install/end?installEnd=%%CHECK_END_INSTALLS%%");
                            for (int ie = 0; ie < urls1.size(); ie++) {
                                urls1.get(ie).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                checkEndIn.add(urls1.get(ie).toString());
                            }
                            String checkEndInstall =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_end_installs(checkEndIn);//结束安装监测URL 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_END_INSTALLS%%");
                            tzMacros.setValue(Base64.encode(checkEndInstall));
                            tzMacros1.add(tzMacros);
                        }else if("DP_SUCC".equals(event)){
                            List<String> checkSuccess = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            checkSuccess.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%CHECK_SUCCESS_DEEPLINKS%%");
                            for (int ds = 0; ds < urls1.size(); ds++) {
                                urls1.get(ds).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                checkSuccess.add(urls1.get(ds).toString());
                            }
                            String checkSuccessd = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_success_deeplinks(checkSuccess);//唤醒成功监测URL 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_SUCCESS_DEEPLINKS%%");
                            tzMacros.setValue(Base64.encode(checkSuccessd));
                            tzMacros1.add(tzMacros);
                        }else if("DP_FAIL".equals(event)){
                            List<String> checkFailDeeplinksL = new ArrayList<>();
                            JSONArray urls1 = report.getJSONObject(re).getJSONArray("urls");
                            checkFailDeeplinksL.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/ideeplink?ideeplink=%%CHECK_FAIL_DEEPLINKS%%");
                            for (int df = 0; df < urls1.size(); df++) {
                                urls1.get(df).toString().replace("__TS__","%%TS%%").replace("__TS_S__","%%TS%S%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%").replace("__PNT_DOWN_X__","%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__","%%ABS_DOWN_Y%%").replace("__PNT_UP_X__","%%ABS_UP_X%%").replace("__PNT_UP_Y__","%%ABS_UP_Y%%");
                                checkFailDeeplinksL.add(urls1.get(df).toString());
                            }
                            String checkFaild = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            tb.setCheck_fail_deeplinks(checkFailDeeplinksL);//唤醒失败监测URL 第三方曝光监测
                            tzMacros = new TzMacros();
                            tzMacros.setMacro("%%CHECK_FAIL_DEEPLINKS%%");
                            tzMacros.setValue(Base64.encode(checkFaild));
                            tzMacros1.add(tzMacros);
                        }
                    }

                        tb.setMacros(tzMacros1);
                        tb.setSource(source);
                        tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);//
                }
                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seatList.add(seat);
            }

            bidResponse.setId(id);//请求id
            bidResponse.setBidid(bidid);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setDebug_info(jo.getString("nbr"));//debug信息
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info("时代广告总返回"+JSONObject.toJSONString(bidResponse));
        }

        return bidResponse;
    }
}
