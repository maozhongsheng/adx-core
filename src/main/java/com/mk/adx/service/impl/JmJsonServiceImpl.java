package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.jiaming.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.service.JmJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yjn
 * @Description 中视力美-嘉铭科技(正式)
 * @Date 2020/10/28 9:48
 */
@Slf4j
@Service("jmJsonService")
public class JmJsonServiceImpl implements JmJsonService {

    @SneakyThrows
    @Override
    public TzBidResponse getJmDataByJson(TzBidRequest request) {
        List<JmImp> impList = new ArrayList<>();//imp集合
        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){
                JmBanner banner = new JmBanner();//banner
                if (null!=request.getImp().get(i).getBanner()){
                    if (null!=request.getImp().get(i).getBanner().getW()){
                        banner.setW(request.getImp().get(i).getBanner().getW());//广告位宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getH()){
                        banner.setH(request.getImp().get(i).getBanner().getH());//广告位高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getWmax()){
                        banner.setWmax(request.getImp().get(i).getBanner().getWmax());//最大宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getHmax()){
                        banner.setHmax(request.getImp().get(i).getBanner().getHmax());//最大高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getWmin()){
                        banner.setWmin(request.getImp().get(i).getBanner().getWmin());//最小宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getHmin()){
                        banner.setHmin(request.getImp().get(i).getBanner().getHmin());//最小高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getId()){
                        banner.setId(request.getImp().get(i).getBanner().getId());//横幅广告id
                    }
                    if (null!=request.getImp().get(i).getBanner().getMimes()){
                        banner.setMimes(request.getImp().get(i).getBanner().getMimes());//==内容支持MIME的类型,image/jpeg, image/jpg, image/png, image/gif
                    }
                }

                JmVideo video = new JmVideo();//video
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

                JmNative NATIVE = new JmNative();//Native
                if (null!=request.getImp().get(i).getNATIVE()){
                    if (null!=request.getImp().get(i).getNATIVE().getRequest()){
                        NATIVE.setRequest(request.getImp().get(i).getNATIVE().getRequest());//内容规范	保留字段
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getVer()){
                        NATIVE.setVer(request.getImp().get(i).getNATIVE().getVer());//版本
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getW()){
                        NATIVE.setW(request.getImp().get(i).getNATIVE().getW());//广告位宽度
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getH()){
                        NATIVE.setH(request.getImp().get(i).getNATIVE().getH());//广告位高度
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getIw()){
                        NATIVE.setIh(request.getImp().get(i).getNATIVE().getIh());//icon高度
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getIw()){
                        NATIVE.setTitle_max(request.getImp().get(i).getNATIVE().getTitle_max());//标题最大字数
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getIw()){
                        NATIVE.setDesc_max(request.getImp().get(i).getNATIVE().getDesc_max());//描述最大字数
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getImage_nums()){
                        NATIVE.setImage_nums(request.getImp().get(i).getNATIVE().getImage_nums());//图片数量	默认:单张图片:单张图片,2张图片,3张图片,4张图片
                    }
//                    NATIVE.setPosid(request.getImp().get(i).getNATIVE().getPosid());//允许展示的模版ID	如有使用线下约定
//                    if (null!=request.getImp().get(i).getNATIVE().getPage_index()){
//                        NATIVE.setPage_index(request.getImp().get(i).getNATIVE().getPage_index());//页编号
//                    }
                }

                JmImp imp = new JmImp();//Imp
                imp.setId(request.getImp().get(i).getId());//曝光id
                imp.setNATIVE(NATIVE);//native数据
//                imp.setTagid(request.getImp().get(i).getTagid());//广告位id
//                imp.setAd_slot_type(request.getImp().get(i).getAd_slot_type());//广告位类型	1:信息流2:banner3:开屏4:视频5:横幅6:插屏7:暂停8:贴片
                if("1".equals(request.getImp().get(i).getAd_slot_type())){
                    imp.setTagid("2021143");
                    imp.setAd_slot_type("1");
                }else if ("3".equals(request.getImp().get(i).getAd_slot_type())){
                    imp.setTagid("2021183");
                    imp.setAd_slot_type("3");
                }
                imp.setBidfloor(request.getImp().get(i).getBidfloor());//底价,单位：分/CPM
                imp.setBidfloorcur(request.getImp().get(i).getBidfloorcur());//货币种类	默认为：CNY（人民币）
//                imp.setSecure(request.getImp().get(i).getSecure());//安全状态	需要安全的HTTPS URL,0 不安全, 1 安全,默认为0
                if (null!=request.getImp().get(i).getAllowstyle()){
                    imp.setAllowstyle(request.getImp().get(i).getAllowstyle());//广告位接受的模版类型ID
                }
                imp.setAd_type(request.getImp().get(i).getAd_type());//广告位可以接受的广告类型,0,无限制1:点击跳转2:点击下载3:LBA4:仅展示5:深链接6:电话
                impList.add(imp);
            }
        }

        JmSite site = new JmSite();//site
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

        JmApps app = new JmApps();//Apps
        if (null!=request.getApp()){
            app.setId("858");
//            app.setId(request.getApp().getId());//应用id
            app.setName(request.getApp().getName());//媒体app名称
            app.setBundle(request.getApp().getBundle());//应用程序包或包名称
            app.setVer(request.getApp().getVer());//app应用版本
            app.setDomain(request.getApp().getDomain());//交互app的domain
            app.setStoreurl(request.getApp().getStoreurl());//应用商店安装应用程序URL
            app.setCat(request.getApp().getCat());//当前内容类别
            app.setSectioncat(request.getApp().getSectioncat());//描述当前网站片段的内容类别
            app.setPagecat(request.getApp().getPagecat());//当前页面内容类别
            app.setPrivacypolicy(request.getApp().getPrivacypolicy());//==app是否有隐私策略,0：否，1：是
            app.setPaid(request.getApp().getPaid());//0：app是免费的，1：app是付费的
            app.setKeywords(request.getApp().getKeywords());//逗号分隔关键字列表
        }

        JmDevice device = new JmDevice();
        if (null!=request.getDevice()){
            JmGeo geo = new JmGeo();//Geo
            if (null!=request.getDevice().getGeo()){
                geo.setLat(request.getDevice().getGeo().getLat());//经度
                geo.setLon(request.getDevice().getGeo().getLon());//纬度
                geo.setType(request.getDevice().getGeo().getType());//源的位置数据	建议当纬度/经度
                geo.setCountry(request.getDevice().getGeo().getCountry());//国家	使用 ISO-3166-1 Alpha-3数据
                geo.setRegion(request.getDevice().getGeo().getRegion());//省份
                geo.setCity(request.getDevice().getGeo().getCity());//城市
                geo.setZip(request.getDevice().getGeo().getZip());//zip或者邮政编码
                geo.setUtcoffset(request.getDevice().getGeo().getUtcoffset());//本地时间戳
               // geo.setProvince(request.getDevice().getGeo().getProvince());//省份代码ISO-3166-2
            }

            device.setUa(request.getDevice().getUa());
            device.setGeo(geo);//设备位置信息
            device.setDnt(request.getDevice().getDnt());//是否跟踪	0跟踪，1不跟踪
          //  device.setLmt(request.getDevice().getLmt());//是否允许获取 IDFA	IOS 操作 系统必传 0:未确定，开发者尚未请求用户 许可; 1:受限制，用户可以退出 IDFA 在设备级别的所有应用程序; 2: 被拒绝，用户选择退出 IDFA; 3.授权，可以继续获取 IDFA
            device.setIp(request.getDevice().getIp());//ipv4地址
            device.setIp6(request.getDevice().getIpv6());//ipv6地址
            device.setDevicetype(request.getDevice().getDevicetype());//设备类型	手机：phone平板：ipadPC：pc互联网电视：tv
            device.setMake(request.getDevice().getMake());//制造厂商
            device.setModel(request.getDevice().getModel());//品牌型号
            device.setOs(request.getDevice().getOs());//系统类型	取值如下：Windows: "windows"Android: "android"iPhone: "ios"苹果电脑: "mac"
            device.setOsv(request.getDevice().getOsv());//系统版本
            device.setHwv(request.getDevice().getHwv());//硬件设备版本
            if (null!=request.getDevice().getH()){
                device.setH(request.getDevice().getH());//物理屏幕高度
            }
            if (null!=request.getDevice().getW()){
                device.setW(request.getDevice().getW());//物理屏幕宽度
            }
            if (null!=request.getDevice().getPpi()){
                device.setPpi(request.getDevice().getPpi());//屏幕密度大小
            }
            device.setPxratio(request.getDevice().getPxratio());//物理设备大小比值
//            device.setDeny(request.getDevice().getDeny());//设备屏幕密度	安卓:context.getResources().getDisplayMetrics().densityiOS:UIScreen.scale取值例如:2.0
            device.setJs(request.getDevice().getJs());//是否支持JavaScript, 0：否，1：是
            device.setFlashver(request.getDevice().getFlashver());//版本的Flash支持的浏览器
            device.setLanguage(request.getDevice().getLanguage());//浏览语言ISO-639-1-alpha-2
            device.setCarrier(request.getDevice().getCarrier());//网络运营商	获取方法参照中国移动：70120中国联通：70123中国电信：70121
            if (request.getDevice().getConnectiontype() == 2){
                device.setConnectiontype("wifi");//网络链接类型	取值如下：eth，wifi，3g，4g，5g，未知留空
            }
            device.setGaid(request.getDevice().getGaid());//安卓advertising id，示例：AdvertisingIdClient.Info info = AdvertisingIdClie nt.getAdvertisingIdInfo(context);String gpId = info.getId()
            device.setIdfa(request.getDevice().getIdfa());//IDFA，明文传输，默认为空字符串
            device.setIdfa_md5(request.getDevice().getIdfa_md5());//
            device.setIdfa_sha1(request.getDevice().getIdfa_sha1());//
            device.setImei(request.getDevice().getImei());//IMEI，明文传输，默认为空字符串
            device.setImei_md5(request.getDevice().getImei_md5());//
            device.setImei_sha1(request.getDevice().getImei_sha1());//
            device.setAndroid_id(request.getDevice().getAndroid_id());//安卓id	android系统必填,Settings.Secure.getString(context.getContentReso lver(),Settings.Secure.ANDROID_ID);
            device.setAndroid_id_md5(request.getDevice().getAndroid_id_md5());//
            device.setAndroid_id_sha1(request.getDevice().getAndroid_id_sha1());//
//            device.setDevice_id(request.getDevice().getDevice_id());//Hardware device ID
//            device.setDevice_id_md5(request.getDevice().getDevice_id_md5());//
//            device.setDevice_id_sha1(request.getDevice().getDevice_id_sha1());//
            device.setMac(request.getDevice().getMac());//MAC地址，明文传输，默认为空字符串
            device.setMac_md5(request.getDevice().getMac_md5());//
            device.setMac_sha1(request.getDevice().getMac_sha1());//
            if (null!=request.getDevice().getOrientation()){
                device.setOrientation(request.getDevice().getOrientation());//横竖屏:0°,90°,180°,270°,-1未知
            }
            device.setOpen_udid(request.getDevice().getOpen_udid());//iOS < 6 -> idfa
        }

        JmUser user = new JmUser();//User
        if (null!=request.getUser()){
            user.setId(request.getUser().getId());//用户id
            user.setBuyeruid(request.getUser().getBuyeruid());//投标人的uid
            user.setYob(request.getUser().getYob());//出生年份4位整数
            user.setGender(request.getUser().getGender());//性别
            user.setKeywords(request.getUser().getKeywords());//逗号分隔的列表关键字
            user.setAge(request.getUser().getAge());//年龄
        }

        JmBidRequest bidRequest = new JmBidRequest();//总请求
        bidRequest.setId(request.getId());//请求id	接入方自定义，确保唯一性。否则影响填充
        bidRequest.setImp(impList);//广告位曝光对象，暂时只支持一个
        bidRequest.setSite(site);//媒体站点信息
        bidRequest.setApp(app);//应用信息
        bidRequest.setDevice(device);//设备信息
        bidRequest.setUser(user);//用户信息
        bidRequest.setAdx_id("64");
        bidRequest.setAdx_name("limei");
//        bidRequest.setAdx_id(request.getAdx_id());//adx分配id
//        bidRequest.setAdx_name(request.getAdx_name());//adx分配名称

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String content = JSONObject.toJSONString(bidRequest);
        log.info(content);
//        String url = "http://test-adx-bid.jm-ssp.cn:7061/api?id=jClptlwoiVWX&name=limei";//测试url
        String url = "http://adx-bid.jm-ssp.cn/api?id=A69NoE9EHldK&name=limei";//正式url
        String ua=request.getDevice().getUa();
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数

        String response = HttppostUtil.doJsonPost(pud);
         if (null != response){
            List<TzSeat> seatList = new ArrayList<>();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            String id = jo.getString("id");//请求id
            Object jj = jo.get("seatbid");
            JSONArray seatids = JSONObject.parseArray(jj.toString());
            for (int i=0;i<seatids.size();i++){
                List<TzBid> bidList = new ArrayList<>();
                JSONArray bids = seatids.getJSONObject(i).getJSONArray("bid");
                for (int j=0;j<bids.size();j++){
                    TzBid tb = new TzBid();
                    tb.setId(bids.getJSONObject(j).getString("id"));
                    tb.setImpid(bids.getJSONObject(j).getString("impid"));
                    if (null!=bids.getJSONObject(j).getInteger("price")){
                        tb.setPrice(bids.getJSONObject(j).getInteger("price"));
                    }
                    tb.setAdid(bids.getJSONObject(j).getString("adid"));
                    tb.setNurl(bids.getJSONObject(j).getString("nurl"));
                    tb.setAdm(bids.getJSONObject(j).getString("adm"));
                    if (null != bids.getJSONObject(j).getJSONArray("adomain")){
                        tb.setAdomain(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("adomain").toString(),String.class));//
                    }
                    tb.setIurl(bids.getJSONObject(j).getString("iurl"));
                    tb.setCid(bids.getJSONObject(j).getString("cid"));
                    tb.setCrid(bids.getJSONObject(j).getString("crid"));
                    if (null != bids.getJSONObject(j).getJSONArray("cat")){
                        tb.setCat(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("cat").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("attr")){
                        tb.setAttr(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("attr").toString(),String.class));//
                    }
                    tb.setDealid( bids.getJSONObject(j).getString("dealid"));
                    tb.setH(bids.getJSONObject(j).getInteger("h"));//
                    tb.setW(bids.getJSONObject(j).getInteger("w"));//
                    tb.setTitle(bids.getJSONObject(j).getString("title"));//
                    tb.setSub_title(bids.getJSONObject(j).getString("sub_title"));//
                    tb.setDesc(bids.getJSONObject(j).getString("desc"));//
                    tb.setStyle_id(bids.getJSONObject(j).getString("style_id"));//
                    tb.setAndroid_url(bids.getJSONObject(j).getString("android_url"));//
                    tb.setIos_url(bids.getJSONObject(j).getString("ios_url"));//
                    tb.setDownload_md5(bids.getJSONObject(j).getString("download_md5"));//
                    tb.setClick_url(bids.getJSONObject(j).getString("click_url"));//
                    tb.setDeeplink_url(bids.getJSONObject(j).getString("deeplink_url"));//
                    tb.setAd_type(bids.getJSONObject(j).getInteger("ad_type"));//
                    tb.setSource(bids.getJSONObject(j).getString("source"));//
                    tb.setValid_time(bids.getJSONObject(j).getInteger("valid_time"));//
                    log.info(bids.getJSONObject(j).getJSONArray("check_views").toString());
                    if (null != bids.getJSONObject(j).getJSONArray("check_views")){
                        tb.setCheck_views(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_views").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_clicks")){
                        tb.setCheck_clicks(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_clicks").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_start_downloads")){
                        tb.setCheck_start_downloads(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_start_downloads").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_end_downloads")){
                        tb.setCheck_end_downloads(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_end_downloads").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_start_installs")){
                        tb.setCheck_start_installs(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_start_installs").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_end_installs")){
                        tb.setCheck_end_installs(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_end_installs").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_activations")){
                        tb.setCheck_activations(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_activations").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_success_deeplinks")){
                        tb.setCheck_success_deeplinks(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_success_deeplinks").toString(),String.class));//
                    }
                    if (null != bids.getJSONObject(j).getJSONArray("check_fail_deeplinks")){
                        tb.setCheck_fail_deeplinks(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("check_fail_deeplinks").toString(),String.class));//
                    }
                    tb.setClicktype(bids.getJSONObject(j).getString("clicktype"));//


                    TzBidApps tba = new TzBidApps();
                    JSONObject apps = bids.getJSONObject(j).getJSONObject("app");//
                    if (null != apps){
                        tba.setApp_name(apps.getString("app_name"));
                        tba.setBundle(apps.getString("bundle"));
                        tba.setApp_icon(apps.getString("app_icon"));
                        tba.setApp_size(apps.getInteger("app_size"));

                        tb.setApp(tba);
                    }

                    List<TzImage> imageList = new ArrayList<>();
                    JSONArray images = bids.getJSONObject(j).getJSONArray("images");//
                    for (int u=0;u<images.size();u++){
                        TzImage image = new TzImage();
                        image.setUrl(images.getJSONObject(u).getString("url"));
                        image.setH(images.getJSONObject(u).getInteger("h"));
                        image.setW(images.getJSONObject(u).getInteger("w"));
                        imageList.add(image);
                    }
                    tb.setImages(imageList);//

//                    TzVideo video = new TzVideo();
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("url"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("h"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("w"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("duration"));
//                    video.setUrl(bids.getJSONObject(j).getJSONObject("video").getString("conver_image"));
//                    tb.setVideo(video);//

                    List<TzCheckVideoUrls> tcvuList = new ArrayList<>();
                    JSONArray check_video_urls = bids.getJSONObject(j).getJSONArray("check_video_urls");
                    if (null != check_video_urls){
                        for (int r=0;r<check_video_urls.size();r++){
                            TzCheckVideoUrls tcvu = new TzCheckVideoUrls();
                           // tcvu.setUrl(check_video_urls.getJSONObject(r).getString("url"));
                            tcvu.setTime(check_video_urls.getJSONObject(r).getInteger("time"));
                            tcvuList.add(tcvu);
                        }
                        tb.setCheck_video_urls(tcvuList);//
                    }

//                    TzAdvertiser ta = new TzAdvertiser();
//                    ta.setId(bids.getJSONObject(j).getJSONObject("advertiser").getString("id"));
//                    ta.setId(bids.getJSONObject(j).getJSONObject("advertiser").getString("industry"));
//                    ta.setId(bids.getJSONObject(j).getJSONObject("advertiser").getString("sub_industry"));
//                    tb.setAdvertiser(ta);//

                    List<TzMacros> macrosList = new ArrayList<>();
                    JSONArray macros = bids.getJSONObject(j).getJSONArray("macros");
                    if (null!=macros){
                        for (int m=0;m<macros.size();m++){
                            TzMacros tm = new TzMacros();
                            tm.setMacro(macros.getJSONObject(m).getString("macro"));
                            tm.setValue(macros.getJSONObject(m).getString("value"));
                            if (null != macros.getJSONObject(m).getJSONArray("expression")){
                                tm.setExpression(JSONObject.parseArray(macros.getJSONObject(m).getJSONArray("expression").toString(),String.class));
                            }
                            macrosList.add(tm);
                        }
                        tb.setMacros(macrosList);//
                    }

                    bidList.add(tb);//
                }
                TzSeat seat = new TzSeat();//
                seat.setBid(bidList);
                seat.setSeat(jo.getString("seat"));
                seatList.add(seat);
            }

            bidResponse.setId(jo.getString("id"));//请求id
            bidResponse.setSeatbid(seatList);//广告集合对象
            bidResponse.setDebug_info(jo.getString("debug_info"));//debug信息
//            bidResponse.setProcess_time_ms(jo.getInteger("process_time_ms"));//耗时，单位：ms
        }

        return bidResponse;
    }

}
