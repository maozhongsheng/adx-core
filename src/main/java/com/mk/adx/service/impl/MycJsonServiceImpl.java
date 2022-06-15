package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.moyicheng.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.RedisUtil;
import com.mk.adx.service.MycJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author mzs
 * @Description 摩邑诚
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("mycJsonService")
public class MycJsonServiceImpl implements MycJsonService {

    @Resource
    private RedisUtil redisUtil;

    private static final String name = "myc";
    /**
     * @Author mzs
     * @Description 摩邑诚 正式
     * @Date 2021/6/28 9:48
     */
    //    @Async("getAsyncExecutor")
    @SneakyThrows
    @Override
    public Future<TzBidResponse> getMycDataByJson(TzBidRequest request) {
        List<MycImp> impList = new ArrayList<>();//imp集合
        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){
                MycBanner banner = new MycBanner();//banner
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
                    if (null!=request.getImp().get(i).getBanner().getHmin()){
                        banner.setHmin(request.getImp().get(i).getBanner().getHmin());//最小高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getMimes()){
                        banner.setMimes(request.getImp().get(i).getBanner().getMimes());//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif
                    }
                }

                MycVideo video = new MycVideo();//video
                if (null!=request.getImp().get(i).getVideo()){
                    video.setFormat(2);//返回广告协议
                    if (null!=request.getImp().get(i).getVideo().getPos()){
                        video.setPos(request.getImp().get(i).getVideo().getPos());//广告在屏幕上曝光位置，见附录5.4
                    }
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
                MycNative NATIVE = new MycNative();//Native
                if (null!=request.getImp().get(i).getNATIVE()){
                    List<MycAsset> list =new ArrayList<>();//imp集合
                    MycAsset mycAsset = new MycAsset();
                    Mmnative mmnative = new Mmnative();
                    List<String> list1 = new ArrayList<>();
                    list1.add("image/jpeg");
                    MycNative nn = new MycNative();
                    mycAsset.setId(i);

                    mycAsset.setRequired(1);
                    MycImg mycImg = new MycImg();
                    mycImg.setW(request.getImp().get(i).getNATIVE().getW());
                    mycImg.setH(request.getImp().get(i).getNATIVE().getH());
                    mycImg.setMimes(list1);
                    mycImg.setType(3);
                    mycAsset.setImg(mycImg);
                    list.add(mycAsset);
                    nn.setAssets(list);
                    mmnative.setNATIVE(nn);
                    if (null!=request.getImp().get(i).getNATIVE().getRequest()){
                        NATIVE.setRequest(JSONObject.toJSONString(mmnative));//内容规范	保留字段
                    }

                    if (null!=request.getImp().get(i).getNATIVE().getVer()){
                        NATIVE.setVer(request.getImp().get(i).getNATIVE().getVer());//版本
                    }
                }

                MycImp imp = new MycImp();//Imp
                imp.setId(request.getImp().get(i).getId());//曝光id
                String tagid = request.getImp().get(i).getTagid();//广告位id
                if (tagid.equals("1000000217")||tagid.equals("1000000218")){
                    imp.setTagid("3570001");
                }
                if (null != request.getImp().get(i).getBidfloor()){
                    imp.setBidfloor(request.getImp().get(i).getBidfloor());//底价,单位：分/CPM
                }
                if("1".equals(request.getImp().get(i).getAd_slot_type())){//信息流

                }else if ("2".equals(request.getImp().get(i).getAd_slot_type())){//banner
                    imp.setBanner(banner);//Banner 广告对象，这种形式返回素材为 html+JS
//                    imp.setClicktracking(true);//是否使用 click url 宏替换功能，true 表示使用宏，false 表示不使用，注意不要传递字符串形式，json 格式支持bool类型。只有 banner 广告支持 click 宏
                }else if ("3".equals(request.getImp().get(i).getAd_slot_type())){//开屏
                    imp.setNATIVE(NATIVE);//原生广告对象，返回素材为图片地址、文字标题等元素
                }else if ("4".equals(request.getImp().get(i).getAd_slot_type())){//视频
                    imp.setVideo(video);//视频广告，返回素材为 VAST 片段
                }else if ("5".equals(request.getImp().get(i).getAd_slot_type())){//横幅

                }else if ("6".equals(request.getImp().get(i).getAd_slot_type())){//插屏

                }else if ("7".equals(request.getImp().get(i).getAd_slot_type())){//暂停

                }else if ("8".equals(request.getImp().get(i).getAd_slot_type())){//贴片

                }

                imp.setDplink(1);
                impList.add(imp);
            }
        }

        MycSite site = new MycSite();//site
        if (null != request.getSite()){
            site.setId(request.getSite().getId());//交互网站ID
            site.setName(request.getSite().getName());//媒体网站名称
            site.setDomain(request.getSite().getDomain());//交互网站的domain
            site.setSectioncat(request.getSite().getSectioncat());//描述当前网站片段的内容类别
            site.setPagecat(request.getSite().getPagecat());//描述当前网站页的内容类别
            site.setPage(request.getSite().getPage());//当前页面URL
            site.setRef(request.getSite().getRef());//Referrer URL
            site.setSearch(request.getSite().getSearch());//当前页面导航搜索字符串
            if (null!=request.getSite().getMobile()){
                site.setMobile(request.getSite().getMobile());//==移动设备优化表示,0：否，1：是
            }else {
                site.setMobile(0);
            }
            if (null!=request.getSite().getPrivacypolicy()){
                site.setPrivacypolicy(request.getSite().getPrivacypolicy());//==是否网站的隐私政策,0：否，1：是
            }else {
                site.setPrivacypolicy(0);
            }
            site.setKeywords(request.getSite().getKeywords());//逗号分隔关键字列表
        }

        MycApp app = new MycApp();//Apps
        if (null!=request.getApp()){
            app.setId(request.getApp().getId());//应用id
            app.setName(request.getApp().getName());//媒体app名称
            app.setBundle(request.getApp().getBundle());//应用程序包或包名称
            app.setVer(request.getApp().getVer());//app应用版本
            app.setDomain(request.getApp().getDomain());//交互app的domain
            app.setStoreurl(request.getApp().getStoreurl());//应用商店安装应用程序URL
            app.setCat(request.getApp().getCat());//当前内容类别
        }

        MycDevice device = new MycDevice();
        if (null!=request.getDevice()){
            MycGeo geo = new MycGeo();//Geo
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
            device.setIp(request.getDevice().getIp());//ipv4地址
            if("phone".equals(request.getDevice().getDevicetype())){
                device.setDevicetype(4);//设备类型	手机
            }else
            if("ipad".equals(request.getDevice().getDevicetype())){
                device.setDevicetype(5);//设备类型	平板
            }
            else{
                device.setDevicetype(0);
            }
            device.setMake(request.getDevice().getMake());//制造厂商
            device.setModel(request.getDevice().getModel());//品牌型号
            device.setOs(request.getDevice().getOs());//系统类型	取值如下：Windows: "windows"Android: "android"iPhone: "ios"苹果电脑: "mac"
            device.setOsv(request.getDevice().getOsv());//系统版本
            if (null!=request.getDevice().getH()){
                device.setH(request.getDevice().getH());//物理屏幕高度
            }
            if (null!=request.getDevice().getW()){
                device.setW(request.getDevice().getW());//物理屏幕宽度
            }
            if (null!=request.getDevice().getPpi()){
                device.setPpi(request.getDevice().getPpi());//屏幕密度大小
            }
            device.setJs(request.getDevice().getJs());//是否支持JavaScript, 0：否，1：是
            device.setCarrier(request.getDevice().getCarrier());//网络运营商	获取方法参照中国移动：70120中国联通：70123中国电信：70121
            if (request.getDevice().getConnectiontype() == 2){
                device.setConnectiontype(request.getDevice().getConnectiontype());//网络链接类型	取值如下：eth，wifi，3g，4g，5g，未知留空
            }
            device.setIdfa(request.getDevice().getIdfa());//IDFA，明文传输，默认为空字符串
            device.setIdfa_md5(request.getDevice().getIdfa_md5());//
            device.setImei(request.getDevice().getImei());//IMEI，明文传输，默认为空字符串
            device.setImei_md5(request.getDevice().getImei_md5());//
            device.setAid(request.getDevice().getAndroid_id());//安卓id	android系统必填,Settings.Secure.getString(context.getContentReso lver(),Settings.Secure.ANDROID_ID);
            device.setAid_md5(request.getDevice().getAndroid_id_md5());//
            device.setMac(request.getDevice().getMac());//MAC地址，明文传输，默认为空字符串
            device.setMac_md5(request.getDevice().getMac_md5());//
            device.setMccmnc(request.getDevice().getMccmnc());

            if (null!=request.getDevice().getOrientation()){
                device.setOrientation(request.getDevice().getOrientation());//横竖屏:0°,90°,180°,270°,-1未知
            }
        }

        MycUser user = new MycUser();//User
        if (null!=request.getUser()){
            user.setId(request.getUser().getId());//用户id
            user.setYob(request.getUser().getYob());//出生年份4位整数
            user.setGender(request.getUser().getGender());//性别
            user.setKeywords(request.getUser().getKeywords());//逗号分隔的列表关键字
        }
        MycBidRequest bidRequest = new MycBidRequest();//总请求
        bidRequest.setId(request.getId());//请求id	接入方自定义，确保唯一性。否则影响填充
        bidRequest.setImp(impList);//广告位曝光对象，暂时只支持一个
        bidRequest.setApp(app);//应用信息
        bidRequest.setDevice(device);//设备信息
        bidRequest.setUser(user);//用户信息
        bidRequest.setVersion(request.getMedia_version());
        bidRequest.setAt(request.getAt());
        bidRequest.setTmax(350);

        bidRequest.setSecure(0);

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info("请求摩邑诚参数"+JSONObject.parseObject(content));
        String url = "http://ads.mycrtb.com/mbid/?pubid=269";
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
        log.info("请求上游花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("摩邑诚返回参数"+JSONObject.parseObject(response));
        if (null != response) {
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
                Integer group = seatids.getJSONObject(i).getInteger("group");
                String seatss = seatids.getJSONObject(i).getString("seat");
                for (int j = 0; j < bids.size(); j++) {
                    TzBid tb = new TzBid();
                    if (request.getImp().get(0).getAd_slot_type().equals("1")) {//信息流
                        tb.setAd_type(8);//信息流-广告素材类型
                    }else {
                        tb.setAd_type(5);//开屏-广告素材类型
                    }
                    tb.setId(bids.getJSONObject(j).getString("id"));
                    tb.setImpid(bids.getJSONObject(j).getString("impid"));
                    if (null != bids.getJSONObject(j).getInteger("price")) {
                        tb.setPrice(bids.getJSONObject(j).getInteger("price"));
                    }
                    tb.setAdid(bids.getJSONObject(j).getString("adid"));
                    tb.setNurl(bids.getJSONObject(j).getString("nurl"));
                    // tb.setAdm(bids.getJSONObject(j).getString("adm"));


                    String admsqq = bids.getJSONObject(j).getString("adm");
                    admsqq = RplStr(admsqq, "\\", "");
                    JSONObject jos = JSONObject.parseObject(admsqq);
                    if (null != jos.getString("native")) {
                        JSONObject aa = JSONObject.parseObject(jos.get("native").toString());
                        JSONObject link = JSONObject.parseObject(aa.get("link").toString());

                        List<String> clickList = new ArrayList<>();
                        JSONArray clicktrackers = link.getJSONArray("clicktrackers");
                        if (null != clicktrackers) {
                            String checkClicks = clicktrackers.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicksf?checkClicks="+ Base64.encode(checkClicks));
                            clickList.add(clicktrackers.get(0).toString());
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        }


                        List<String> checkViews = new ArrayList<>();
                        JSONArray imptrackers = aa.getJSONArray("imptrackers");
                        if (null != imptrackers) {
                            String pv = imptrackers.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pvf?pv="+Base64.encode(pv));
                            checkViews.add(imptrackers.get(0).toString());
                            tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                        }

                        String fallback = link.getString("fallback");//deeplink URL
                        if(StringUtils.isNotEmpty(fallback)){
                            String fallbacks = fallback + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            String deepUrl = "http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink="+Base64.encode(fallbacks);//天卓deeplinkurl
                            tb.setDeeplink_url(deepUrl);
                        }
                        String url1 = link.getString("url");//落地页URL
                        if(StringUtils.isNotEmpty(url1)){
                            String url2 = url1 + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            String clickUrl = "http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/click?clickurl="+Base64.encode(url2);//天卓deeplinkurl
                            tb.setClick_url(clickUrl);
                        }
                        TzNative tzNative = new TzNative();
                        List<TzImage> imageList = new ArrayList<>();
                        JSONArray assets = aa.getJSONArray("assets");//展现跟踪链接
                        String title = "";
                        String desc = "";
                        if (null != assets) {
                            for (int as = 0; as < assets.size(); as++) {
                                Integer asid = assets.getJSONObject(as).getInteger("id");
                                JSONObject imgs = assets.getJSONObject(as).getJSONObject("img");
                                TzImage image = new TzImage();
                                image.setH(imgs.getInteger("h"));
                                image.setW(imgs.getInteger("w"));
                                image.setUrl(imgs.getString("url"));
                                image.setId(asid.toString());
                                imageList.add(image);
                                if(null != assets.getJSONObject(as).getJSONObject("title")){
                                    JSONObject titles =assets.getJSONObject(as).getJSONObject("title");
                                    title = titles.getString("text");
                                }
                                if(null != assets.getJSONObject(as).getJSONObject("data")){
                                    JSONObject data = assets.getJSONObject(as).getJSONObject("data");
                                    desc = data.getString("value");
                                }


                            }
                            tzNative.setTitle(title);
                            tzNative.setDesc(desc);
                            tzNative.setImages(imageList);
                            tb.setNATIVE(tzNative);
                        }
                        }
                    else if(null != jos.getString("video")){
                        List<TzImage> imageList = new ArrayList<>();
                        JSONObject  vv  = JSONObject.parseObject(jos.get("video").toString());
                        JSONObject link = JSONObject.parseObject(vv.get("link").toString());
                        String fallback = link.getString("fallback");//deeplink URL
                        if(StringUtils.isNotEmpty(fallback)){
                            String fallbacks = fallback + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            String deepUrl = "http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink="+Base64.encode(fallbacks);//天卓deeplinkurl
                            tb.setDeeplink_url(deepUrl);
                        }
                        String url1 = link.getString("url");//落地页URL
                        if(StringUtils.isNotEmpty(url1)){
                            String url2 = url1 + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            String clickUrl = "http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/click?clickurl="+Base64.encode(url2);//天卓deeplinkurl
                            tb.setClick_url(clickUrl);
                        }
                        List<String> checkViews = new ArrayList<>();
                        JSONArray imptrackers = vv.getJSONArray("imptrackers");
                        if (null != imptrackers) {
                            String pv = imptrackers.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pvf?pv="+Base64.encode(pv));
                            checkViews.add(imptrackers.get(0).toString());
                            tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                        }

                        List<String> clickList = new ArrayList<>();
                        JSONArray clicktrackers = link.getJSONArray("clicktrackers");
                        if (null != clicktrackers) {
                            String checkClicks = clicktrackers.get(0).toString() + "," + id + "," + request.getDevice().getIp() + "," + name + "," + request.getApp().getName();
                            clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicksf?checkClicks="+Base64.encode(checkClicks));
                            clickList.add(clicktrackers.get(0).toString());
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        }

                        TzVideo tzVideo = new TzVideo();
                        tzVideo.setUrl(vv.getString("video_url"));
                        tzVideo.setH(vv.getInteger("height"));
                        tzVideo.setW(vv.getInteger("width"));
                        tzVideo.setDuration(vv.getInteger("duration"));
                        JSONObject cover = vv.getJSONObject("cover");
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(cover.getString("url"));
                        tzImage.setH(cover.getInteger("h"));
                        tzImage.setW(cover.getInteger("w"));
                        tzVideo.setConver_image(tzImage);
                        Object tracking_event = vv.get("tracking_event");
                        JSONArray tracking_events = JSONObject.parseArray(tracking_event.toString());
                        if (null != tracking_events) {
                            for (int tr = 0; tr < tracking_events.size(); tr++) {
                                TzImage image = new TzImage();
                                image.setType(tracking_events.getJSONObject(tr).getInteger("type"));
                                image.setUrl(tracking_events.getJSONObject(tr).getString("url"));
                                imageList.add(image);
                            }
                        }

                        tb.setImages(imageList);
                        tb.setVideo(tzVideo);
                    }

                    if (null != bids.getJSONObject(j).getJSONArray("adomain")) {
                        tb.setAdomain(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("adomain").toString(), String.class));//
                    }
                    tb.setIurl(bids.getJSONObject(j).getString("iurl"));
                    tb.setCid(bids.getJSONObject(j).getString("cid"));
                    tb.setCrid(bids.getJSONObject(j).getString("crid"));
                    if (null != bids.getJSONObject(j).getJSONArray("cat")) {
                        tb.setCat(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("cat").toString(), String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("attr")) {
                        tb.setAttr(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("attr").toString(), String.class));//
                    }
                    tb.setDealid(bids.getJSONObject(j).getString("dealid"));
                    tb.setH(bids.getJSONObject(j).getInteger("h"));//
                    tb.setW(bids.getJSONObject(j).getInteger("w"));//
                    tb.setTitle(bids.getJSONObject(j).getString("title"));//
                    tb.setSub_title(bids.getJSONObject(j).getString("sub_title"));//
                    tb.setDesc(bids.getJSONObject(j).getString("desc"));//
                    tb.setStyle_id(bids.getJSONObject(j).getString("style_id"));//
                    tb.setAndroid_url(bids.getJSONObject(j).getString("android_url"));//
                    tb.setIos_url(bids.getJSONObject(j).getString("ios_url"));//
                    tb.setDownload_md5(bids.getJSONObject(j).getString("download_md5"));//
                    tb.setAd_type(bids.getJSONObject(j).getInteger("ad_type"));//
                    tb.setSource(bids.getJSONObject(j).getString("source"));//
                    tb.setValid_time(bids.getJSONObject(j).getInteger("valid_time"));//

                    tb.setClicktype(bids.getJSONObject(j).getInteger("ctype").toString());//

                    TzBidApps tba = new TzBidApps();
                    JSONObject apps = bids.getJSONObject(j).getJSONObject("app");//
                    if (null != apps) {
                        tba.setApp_name(apps.getString("app_name"));
                        tba.setBundle(apps.getString("bundle"));
                        tba.setApp_icon(apps.getString("app_icon"));
                        tba.setApp_size(apps.getInteger("app_size"));
                        tb.setApp(tba);
                    }

//                    TzVideo video = new TzVideo();
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("url"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("h"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("w"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("duration"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("conver_image"));
//                    tb.setVideo(video);

                    List<TzCheckVideoUrls> tcvuList = new ArrayList<>();
                    JSONArray check_video_urls = bids.getJSONObject(j).getJSONArray("check_video_urls");
                    if (null != check_video_urls) {
                        for (int r = 0; r < check_video_urls.size(); r++) {
                            TzCheckVideoUrls tcvu = new TzCheckVideoUrls();
                            //tcvu.setUrl(check_video_urls.getJSONObject(r).getString("url"));
                            tcvu.setTime(check_video_urls.getJSONObject(r).getInteger("time"));
                            tcvuList.add(tcvu);
                        }
                        tb.setCheck_video_urls(tcvuList);//
                    }

                    List<TzMacros> macrosList = new ArrayList<>();
                    JSONArray macros = bids.getJSONObject(j).getJSONArray("macros");
                    if (null != macros) {
                        for (int m = 0; m < macros.size(); m++) {
                            TzMacros tm = new TzMacros();
                            tm.setMacro(macros.getJSONObject(m).getString("macro"));
                            tm.setValue(macros.getJSONObject(m).getString("value"));
                            if (null != macros.getJSONObject(m).getJSONArray("expression")) {
                                tm.setExpression(JSONObject.parseArray(macros.getJSONObject(m).getJSONArray("expression").toString(), String.class));
                            }
                            macrosList.add(tm);
                        }
                        tb.setMacros(macrosList);//
                    }
                    tb.setSource(name);//素材来源
                    bidList.add(tb);//
                }
                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seat.setSeat(seatss);
                seatList.add(seat);
            }

            bidResponse.setId(id);//请求id
            bidResponse.setBidid(bidid);
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
            log.info("总返回"+JSONObject.toJSONString(bidResponse));
        }

        return AsyncResult.forValue(bidResponse);
    }
    //把字符str1中的str2替换为str3
    public static String RplStr(String str1,String str2,String str3){
        String strtmp="";
        int i=0,f;
        for(i=0;;i+=str2.length()){
            f=str1.indexOf(str2,i);
            if (f==-1) {
                strtmp+=str1.substring(i);
                break;
            }else{
                strtmp+=str1.substring(i,f);
                strtmp+=str3;
                i=f;
            }
        }
        return strtmp;
    }

}
