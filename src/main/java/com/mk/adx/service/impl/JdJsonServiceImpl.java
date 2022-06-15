package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.jingdong.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.JdJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yjn
 * @Description 中视力美-京准通
 * @Date 2021/5/21 9:48
 */
@Slf4j
@Service("jdJsonService")
public class JdJsonServiceImpl implements JdJsonService {

    @SneakyThrows
    @Override
    public TzBidResponse getJdDataByJson(TzBidRequest request) {
        List<JdImp> impList = new ArrayList<>();//imp集合
        if (null!=request.getImp()){
            for (int i=0;i<request.getImp().size();i++){
                JdBanner banner = new JdBanner();//banner
                if (null!=request.getImp().get(i).getBanner()){
                    if (null!=request.getImp().get(i).getBanner().getW()){
                        banner.setW(request.getImp().get(i).getBanner().getW());//广告位宽度
                    }
                    if (null!=request.getImp().get(i).getBanner().getH()){
                        banner.setH(request.getImp().get(i).getBanner().getH());//广告位高度
                    }
                    if (null!=request.getImp().get(i).getBanner().getPos()){
                        banner.setPos(request.getImp().get(i).getBanner().getPos());//广告位在屏幕位置
                    }
                }

                JdNative NATIVE = new JdNative();//Native
                if (null!=request.getImp().get(i).getNATIVE()){
                    if (null!=request.getImp().get(i).getNATIVE().getW()){
                        NATIVE.setW(request.getImp().get(i).getNATIVE().getW());//广告位宽度
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getH()){
                        NATIVE.setH(request.getImp().get(i).getNATIVE().getH());//广告位高度
                    }
                    if (null!=request.getImp().get(i).getNATIVE().getImage_nums()){
                        NATIVE.setImgnum(request.getImp().get(i).getNATIVE().getImage_nums());
                    }
                    NATIVE.setCount(1);//所需素材个数，一般为 1；若支持多素材，并且对每个素材图片点击支持独立跳转可大于 1
                }

                JdVideo video = new JdVideo();//video
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
                    if (null!=request.getImp().get(i).getVideo().getStartdelay()){
                        video.setStartdelay(request.getImp().get(i).getVideo().getStartdelay());//视频广告开始播放的延时时间>0: Mid-Roll（值代表延时） 0：Pre-Roll-1: GenericMid-Roll-2: GenericPost-Roll
                    }
                    if (null!=request.getImp().get(i).getVideo().getMimes()){
                        video.setMines(request.getImp().get(i).getVideo().getMimes()); //支持的视频类型，如video/mp4
                    }
                    if (null!=request.getImp().get(i).getVideo().getLinearity()){
                        video.setLinearity(request.getImp().get(i).getVideo().getLinearity()); //指示视频广告在视频流中的播放位置1：Linear/in-Stream，在视频流中展现2：Non-Linear/Overlay，悬浮在视频内容上展现
                    }
                    if (null!=request.getImp().get(i).getVideo().getMinbitrate()){
                        video.setMinbitrate(request.getImp().get(i).getVideo().getMinbitrate()); //最小比特率，单位：Kbps
                    }
                    if (null!=request.getImp().get(i).getVideo().getMaxbitrate()){
                        video.setMaxbitrate(request.getImp().get(i).getVideo().getMaxbitrate()); //最大比特率，单位：Kbps
                    }

                }

                JdPmp pmp = new JdPmp();//pmp
                if (null!=request.getImp().get(i).getPmp()){
                    List<JdDeal> deals = new ArrayList<>();//deals
                    if (null!=request.getImp().get(i).getPmp().getDeals()){
                        for (int j=0;j<request.getImp().get(i).getPmp().getDeals().size();j++){
                            JdDeal deal = new JdDeal();
                            deal.setId(request.getImp().get(i).getPmp().getDeals().get(j).getId());
                            deal.setBidfloor(request.getImp().get(i).getPmp().getDeals().get(j).getBidfloor());
                            deals.add(deal);
                        }
                    }
                    pmp.setDeals(deals);//
                }

                JdImp imp = new JdImp();//Imp
                imp.setId(request.getImp().get(i).getId());//展示id，唯一标识一个展示；由媒体侧生成，请确保全局唯一
                imp.setTagid(request.getImp().get(i).getTagid());//广告位 id，在媒体系统中唯一标识一个广告位。（确保与对接平台上登记的tagID保持一致，否则无法返回广告）
//                if (null!=request.getImp().get(i).getNATIVE()){
//                    imp.setIsdeeplink(true);//是否支持呼起京东app或广告主app。只有native广告支持，true表示返回deeplinkurl,false则不返回。注意不要传递字符串形式。
//                    imp.setIsul(false);//iOS流量使用，只在 isdeeplink传 true 时有效，iOS请求如需返回universallink url，传 true，否则不传。注意不要传递字符串形式。
//                }
                imp.setBidfloor(request.getImp().get(i).getBidfloor());//底价，单位：千次分（人民币）。竞价广告时必须，包段不需要
                if("1".equals(request.getImp().get(i).getAd_slot_type())){//信息流

                }else if ("2".equals(request.getImp().get(i).getAd_slot_type())){//banner
                    imp.setBanner(banner);//Banner 广告对象，这种形式返回素材为 html+JS
//                    imp.setClicktracking(true);//是否使用 click url 宏替换功能，true 表示使用宏，false 表示不使用，注意不要传递字符串形式，json 格式支持bool类型。只有 banner 广告支持 click 宏
                }else if ("3".equals(request.getImp().get(i).getAd_slot_type())){//开屏
                    imp.setNATIVE(NATIVE);//原生广告对象，返回素材为图片地址、文字标题等元素
                    imp.setIsdeeplink(true);//是否支持呼起京东app或广告主app。只有native广告支持，true表示返回deeplinkurl,false则不返回。注意不要传递字符串形式。
                    imp.setIsul(false);//iOS流量使用，只在 isdeeplink传 true 时有效，iOS请求如需返回universallink url，传 true，否则不传。注意不要传递字符串形式。
                }else if ("4".equals(request.getImp().get(i).getAd_slot_type())){//视频
                    imp.setVideo(video);//视频广告，返回素材为 VAST 片段
                }else if ("5".equals(request.getImp().get(i).getAd_slot_type())){//横幅

                }else if ("6".equals(request.getImp().get(i).getAd_slot_type())){//插屏

                }else if ("7".equals(request.getImp().get(i).getAd_slot_type())){//暂停

                }else if ("8".equals(request.getImp().get(i).getAd_slot_type())){//贴片

                }
//                imp.setSecure(request.getImp().get(i).getSecure());//安全状态	需要安全的HTTPS URL,0 不安全, 1 安全,默认为0
                imp.setPmp(pmp);
                impList.add(imp);
            }
        }

        JdApp app = new JdApp();//Apps
        if (null!=request.getApp()){
            app.setBundle(request.getApp().getBundle());//应用程序包或包名称
            app.setCat(request.getApp().getCat());//当前内容类别
            app.setKeywords(request.getApp().getKeywords());//逗号分隔关键字列表
            app.setPagecat(request.getApp().getPagecat());//当前页面内容类别
            app.setSdkversion(request.getApp().getVer());//sdk 版本，使用SDK 方式接入必填
        }

        JdSite site = new JdSite();//site
        if (null != request.getSite()){
            if (null!=request.getSite().getDomain()){
                site.setDomain(request.getSite().getDomain().get(0));//交互网站的domain
            }
            site.setPage(request.getSite().getPage());//当前页面URL
            site.setRef(request.getSite().getRef());//Referrer URL
            site.setKeywords(request.getSite().getKeywords());//逗号分隔关键字列表
            site.setPagecat(request.getSite().getPagecat());//描述当前网站页的内容类别
            site.setSearch(request.getSite().getSearch());//当前页面导航搜索字符串
        }

        List<JdCaid> caids = new ArrayList<>();
        JdCaid caid = new JdCaid();
//        caid.setVersion("1.0");
//        caid.setId("test111");

        JdGeo geo = new JdGeo();//Geo
        if (null!=request.getDevice().getGeo()){
            float lat = request.getDevice().getGeo().getLat();
            float lon = request.getDevice().getGeo().getLon();
            geo.setLat(lat);//经度
            geo.setLon(lon);//纬度
            if (0!=lat){
                Base64.encode(String.valueOf(lat));
            }
            if (0!=lon){
                Base64.encode(String.valueOf(lon));
            }
        }

        JdExt ext = new JdExt();
//        ext.setWifi("");//应反作弊需求添加， 表示扫描到的WIFI 列表（3 个或者至少 1 个）, 以’,’ 逗号分割
//        ext.setRadius("");//应反作弊需求添加，表示客户端所在位置的定位半径，单位米

        JdDevice device = new JdDevice();
        if (null!=request.getDevice()){
            device.setOs(request.getDevice().getOs());//系统类型	取值如下：Windows: "windows"Android: "android"iPhone: "ios"苹果电脑: "mac"
            device.setOsv(request.getDevice().getOsv());//系统版本
           // device.setOsupdatetime(request.getDevice().getOsUpdateTimeSec());//系统更新时间，参考 2.5.1
            device.setDid(request.getDevice().getImei());//设备的IMEI
            device.setIfa(request.getDevice().getIdfa());//IDFA
            device.setIfamd5(request.getDevice().getIdfa_md5());
//            device.setCaid(caids);//对于ios 的ifa、ifamd5、caid 至少有一个必填， 可同时存在，优先级：ifa>ifamd5>caid
            device.setIp(request.getDevice().getIp());//设备ip 地址
            if (null!=request.getDevice().getIp()){
                device.setIpenc(Base64.encode(request.getDevice().getIp()));//编码或加密后的ip 地址，采用 base64编码或对称方式加密，对于ip 和ipenc 至少一个必填
            }
            device.setUa(request.getDevice().getUa());//user agent
            device.setConnectiontype(request.getDevice().getConnectiontype());//设备连接类型，取值范围：0:Unknown,1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G，7：5G
            device.setMake(request.getDevice().getMake());//设备制造商
            device.setModel(request.getDevice().getModel());//设备硬件型号，例如 iPhone。
            device.setHwmodel(request.getDevice().getHwv());//硬件型号，代码参考 2.5.7
//            device.setHwname(request.getDevice().getPhoneName());//md5 后的设备名称，代码参考 2.5.4(需要MD5加密)
//            device.setHwmachine("");//系统型号，代码参考 2.5.6
            device.setHwv(request.getDevice().getHwv());//硬件型号版本，例如 iPhone5S 中的 5S
            device.setCarrier(request.getDevice().getCarrier());//运营商名称，取值 mobile:中国移动，unicom:联通， telecom：电信，代码参考 2.5.8
            device.setFlashver(request.getDevice().getFlashver());//应反作弊需求添加，表示浏览器支持的Flash 版本
            device.setLanguage(request.getDevice().getLanguage());//应反作弊需求添加，表示浏览器语言，使用ISO-639 标准，代码参考 2.5.3
            device.setScreenheight(0);//应反作弊需求添加，表示屏幕的物理高度，以像素为单位
            device.setScreenwidth(0);//应反作弊需求添加，表示屏幕的物理宽度，以像素为单位
            device.setPpi(request.getDevice().getPpi());//应反作弊需求添加，表示以像素每英寸表示的屏幕尺寸
            device.setMacidmd5(request.getDevice().getMac_md5());//应反作弊需求添加，表示设备 mac 地址，使用md5 哈希算法加密
            device.setGeo(geo);//应反作弊需求添加，Geo 对象，表示用户或用户设备当前地理位置信息。【注意：要求按照 WGS84 坐标系输出经纬度】
            device.setExt(ext);//应反作弊需求添加，表示客户端所在位置的定位半径，单位米
            device.setCountrycode("");//国家代码，代码参考 2.5.2
            device.setSysmemory("");//系统内存，代码参考 2.5.9
            device.setSysdisksize("");//硬盘容量，代码参考 2.5.10
            device.setMachinetype("");//机器类型，IPad，模拟器
            device.setJailbreak("");//是否越狱，0:否，1:是
        }

        JdUser user = new JdUser();//User
        if (null!=request.getUser()){
            JdData data = new JdData();//data
            if (null!=request.getUser().getData()){
                List<JdSegment> segment = new ArrayList<>();
                if (null!=request.getUser().getData().get(0).getSegment()){
                    JdSegment sg = new JdSegment();//segment
                    List<JdValue> value = new ArrayList<>();//value(待定)
                    if (null!=request.getUser().getData().get(0).getSegment().getValue()){
                        JdValue va = new JdValue();
//                        va.setId("dummy_value_id");//用户标签枚举值id，id 和 name 一一对应
//                        va.setName("dummy_value_name");//用户标签枚举值名称，如：汽车品牌 A。
                        va.setWeight(0.8);//用户标签枚举值置信度打分值，范围在 0~1 之间，0 代表置信度最低，1 代表置信度最高
                        value.add(va);
                    }

                    sg.setId(request.getUser().getData().get(0).getSegment().getId());//用户标签id，id 和 name 一一对应
                    sg.setName(request.getUser().getData().get(0).getSegment().getName());//用户标签名称，如：汽车爱好者
                    sg.setValue(value);//用户标签枚举值。如果该标签没有枚举值，此时 Value 中的 id 和name 需与 Segment 的 id 和name 保持一致。
                    segment.add(sg);
                }

                data.setId(request.getUser().getData().get(0).getId());//
                data.setName(request.getUser().getData().get(0).getName());//
                data.setSegment(segment);
            }

            user.setId(request.getUser().getId());//用户id
            user.setGender(request.getUser().getGender());//性别
            user.setKeywords(request.getUser().getKeywords());//逗号分隔的列表关键字
            user.setData(data);
        }

        List<JdContent> contents = new ArrayList<>();
        JdContent content = new JdContent();//content
        if (null!=request.getContent()){
            content.setTitle(request.getContent().getTitle());//广告位所在内容页或者信息流上下文的标题
            content.setCat(request.getContent().getCat());//广告位所在内容页或者信息流上下文的标签、类别
            content.setKeywords(request.getContent().getKeywords());//广告位所在内容页或者信息流上下文的关键词
            content.setContext(request.getContent().getContext());//内容类型，取值 1：Video， 2：Game， 3：Music， 4：Application，5：Text，6：Other， 7：Unknown
            contents.add(content);
        }

        JdBidRequest bidRequest = new JdBidRequest();//总请求
        bidRequest.setId(request.getId());//请求id，唯一标识一次广告请求；由媒体侧生成，请确保全局唯一
        bidRequest.setVersion(request.getMedia_version());//协议版本号
        bidRequest.setImp(impList);//展现广告资源位描述
        if (null != request.getApp().getBundle()){//app和site互斥，只能有一个
            bidRequest.setApp(app);//应用信息
        }else {
            bidRequest.setSite(site);//媒体站点信息
        }
        bidRequest.setDevice(device);//设备信息
        bidRequest.setUser(user);//用户信息
        bidRequest.setContent(contents);//内容页或者信息流上下文信息

        TzBidResponse bidResponse = new TzBidResponse();//总返回
        String lastContent = JSONObject.toJSONString(bidRequest);
        log.info("======"+lastContent);
        String url = "https://dsp-test-x.jd.com/adx/wifiboost";//测试url
        String ua = request.getDevice().getUa();
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(lastContent);//请求参数

        String response = HttppostUtil.doJsonPost(pud);
        if (null != response){

            TzSeat tzSeat = new TzSeat();
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            String id = jo.getString("id");//请求id
            String bidid = jo.getString("bidid");//京东生成的 bid id
            JSONObject seatbid = jo.getJSONObject("seatbid");//响应席位
            Object jj = seatbid.get("bid");
            JSONArray bids = JSONObject.parseArray(jj.toString());
                List<TzBid> bidList = new ArrayList<>();
                for (int j=0;j<bids.size();j++){
                    TzBid tb = new TzBid();//出价对象，广告集合
                    tb.setId(bids.getJSONObject(j).getString("id"));//京东生成的id
                    tb.setAdid(bids.getJSONObject(j).getString("adid"));//京东生成的素材校验id
                    tb.setImpid(bids.getJSONObject(j).getString("impid"));//对应请求中的imp 中的id
                    tb.setPrice(bids.getJSONObject(j).getInteger("price"));//竞价价格，单位：分（人民币）非竞价广告无此字段返回
                    tb.setNurl(bids.getJSONObject(j).getString("nurl"));//告知京东赢得 bid ，并通过宏替换%%WIN_PRICE%%提供二价。非竞价广告无此字段返回。
                    if (null!=bids.getJSONObject(j).getString("ad_type")){
                        tb.setAd_type(Integer.parseInt(bids.getJSONObject(j).getString("ad_type")));//广告类型，1 代表非商品，3 代表商品
                    }
                    if (null==bids.getJSONObject(j).getJSONObject("adm")){//banner和video
                        tb.setAdm(bids.getJSONObject(j).getString("adm"));
                    }else {//native
                        TzAdm tzAdm = new TzAdm();
                        List<TzItem> itemList = new ArrayList<>();
                        JSONObject adm = bids.getJSONObject(j).getJSONObject("adm");//响应席位
                        Object item = adm.get("items");
                        JSONArray items = JSONObject.parseArray(item.toString());
                        for (int i=0;i<items.size();i++){
                            TzItem tzItem = new TzItem();//出价对象，广告集合
                            tzItem.setTitle(items.getJSONObject(i).getString("title"));//标题
                            tzItem.setDesc(items.getJSONObject(i).getString("desc"));//描述
                            tzItem.setSource(items.getJSONObject(i).getString("ad_resource"));//广告来源，用于媒体侧展示广告来源，如：JD 或者拉下载的APP 名称
                            tzItem.setIndex(items.getJSONObject(i).getString("id"));//序号
                            tzItem.setClicktype(items.getJSONObject(i).getString("media_style"));//媒体端广告样式类型：1：跳转类广告，使用 click_url浏览器打开落地页或者 dpl_url在app打开落地页2：下载类广告，需使用原生方法处理download_url ，并单独触发 click_url 点击监测
                            tzItem.setDownload_url(items.getJSONObject(i).getString("download_url"));//当media_style为 下 载 时，download_url 为应用下载链接
                            tzItem.setClick_url(items.getJSONObject(i).getString("click_url"));//点击跳转h5 页面地址
                            tzItem.setDeeplink_url(items.getJSONObject(i).getString("dpl_url"));//Deeplink协议
                            if (null!=items.getJSONObject(i).getString("img")){
                                tzItem.setImage(items.getJSONObject(i).getString("img"));//单个图片
                            }else {
                                List<TzImage> imageList = new ArrayList<>();
                                JSONArray images = bids.getJSONObject(j).getJSONArray("imgs");//图片集合
                                for (int u=0;u<images.size();u++){
                                    TzImage image = new TzImage();
                                    image.setId(images.getJSONObject(u).getString("id"));
                                    image.setUrl(images.getJSONObject(u).getString("url"));
                                    imageList.add(image);
                                }
                                tzItem.setIamges(imageList);//图片素材集合
                            }
//                        tb.setVideo(items.getJSONObject(i).getString("video"));//视频素材 url
                            if (null != bids.getJSONObject(j).getJSONArray("exposal_urls")){
                                tzItem.setCheck_views(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("exposal_urls").toString(),String.class));//曝光监测 url列表, 会有多个，每个 url都需要发送。必须替换 URL里面的%%WIN_PRICE%%二价宏。
                            }
                            if (null != bids.getJSONObject(j).getJSONArray("click_monitor_urls")){
                                tzItem.setCheck_clicks(JSONObject.parseArray(bids.getJSONObject(j).getJSONArray("click_monitor_urls").toString(),String.class));//点击监测url 列表，可能会有多个，每个都需要触发上报。
                            }
                            itemList.add(tzItem);
                        }
                        tzAdm.setItems(itemList);
                       // tb.setAdms(tzAdm);//素材对象
                    }
                    bidList.add(tb);
                }

            List<TzSeat> seatList = new ArrayList<>();//广告集合对象
            TzSeat seat = new TzSeat();//
            seat.setBid(bidList);
            seatList.add(seat);

            //最后的返回
            bidResponse.setId(id);//请求id
            bidResponse.setBidid(bidid);//京东生成的 bid id
            bidResponse.setSeatbid(seatList);//广告集合对象
        }
        log.info("+++==="+JSONObject.toJSONString(bidResponse));
        return bidResponse;
    }
}
