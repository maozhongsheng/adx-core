package com.mk.adx.service.impl;

import com.mk.adx.entity.protobuf.jm.JmBidRequest;
import com.mk.adx.entity.protobuf.jm.JmBidResponse;
import com.mk.adx.entity.protobuf.tz.TzBidRequest;
import com.mk.adx.entity.protobuf.tz.TzBidResponse;
import com.mk.adx.service.JmProtoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.springframework.stereotype.Service;

/**
 * @Author yjn
 * @Description 中视力美-嘉铭科技
 * @Date 2020/10/28 9:48
 */
@Slf4j
@Service("jmProtoService")
public class JmProtoServiceImpl implements JmProtoService {

    @SneakyThrows
    @Override
    public TzBidResponse.BidResponse getJmDataByProto(byte[] content) {
        TzBidRequest.BidRequest br = TzBidRequest.BidRequest.parseFrom(content);//反序列化

        JmBidRequest.BidRequest.Imp.Native.Builder native1 = JmBidRequest.BidRequest.Imp.Native.newBuilder();//native
        native1.setRequest(br.getImp(0).getNative().getRequest());//内容规范	保留字段
        native1.setVer(br.getImp(0).getNative().getVer());//版本
        native1.setW(br.getImp(0).getNative().getW());//广告位宽度
        native1.setH(br.getImp(0).getNative().getH());//广告位高度
        native1.setIw(br.getImp(0).getNative().getIw());//icon宽度
        native1.setIh(br.getImp(0).getNative().getIh());//icon高度
        native1.setTitleMax(br.getImp(0).getNative().getTitleMax());//标题最大字数
        native1.setDescMax(br.getImp(0).getNative().getDescMax());//描述最大字数
        native1.setImageNums(br.getImp(0).getNative().getImageNums());//图片数量	默认:单张图片:单张图片,2张图片,3张图片,4张图片
        native1.addPosid("1");//允许展示的模版ID	如有使用线下约定
        native1.setPageIndex(1);//页编号


        JmBidRequest.BidRequest.Imp.Builder imp = JmBidRequest.BidRequest.Imp.newBuilder();//imp
        imp.setId(br.getImp(0).getId());//曝光id
        imp.setNative(native1);//native数据
        imp.setTagid(br.getImp(0).getTagid());//广告位id{信息流测试数据}
        imp.setBidfloor(br.getImp(0).getBidfloor());//底价,单位：分/CPM
        imp.setBidfloorcur(br.getImp(0).getBidfloorcur());//货币种类	默认为：CNY（人民币）
        imp.setSecure(br.getImp(0).getSecure());//安全状态	需要安全的HTTPS URL,0 不安全, 1 安全,默认为0
//        imp.setPmp(pmp);//PMP交易数据
//        imp.addAllowstyle("");//广告位接受的模版类型ID
        imp.setAdType(br.getImp(0).getAdType());//广告位可以接受的广告类型,0,无限制1:点击跳转2:点击下载3:LBA4:仅展示5:深链接6:电话
        imp.setAdSlotType(br.getImp(0).getAdSlotType());//广告位类型	1:信息流2:banner3:开屏4:视频5:横幅6:插屏7:暂停8:贴片


        JmBidRequest.BidRequest.Apps.Builder app = JmBidRequest.BidRequest.Apps.newBuilder();//app
        app.setId(br.getApp().getId());//应用id
//        app.setName("");//应用名称
//        app.setBundle("");//包名
//        app.setVer("");//版本
//        app.setDomain("");//app的domain
//        app.setStoreurl("");//应用商店安装应用url
        app.addCat(br.getApp().getCat(0));//当前内容类别
        app.addSectioncat(br.getApp().getSectioncat(0));//描述当前网站片段的内容类别
        app.addPagecat(br.getApp().getPagecat(0));//当前页面内容类别
//        app.setPrivacypolicy(0);//是否有隐私政策	0：否，1：是
//        app.setPaid(0);//app是否付费	0：免费，1：付费
//        app.setKeywords("");//关键词	多个关键词使用英文逗号分隔


        JmBidRequest.BidRequest.Geo.Builder geo = JmBidRequest.BidRequest.Geo.newBuilder();//geo
        geo.setLat(br.getDevice().getGeo().getLat());//经度
        geo.setLon(br.getDevice().getGeo().getLat());//纬度
        geo.setType(br.getDevice().getGeo().getType());//源的位置数据	建议当纬度/经度
        geo.setCountry(br.getDevice().getGeo().getCountry());//国家	使用 ISO-3166-1 Alpha-3
        geo.setRegion(br.getDevice().getGeo().getRegion());//省份
        geo.setCity(br.getDevice().getGeo().getCity());//城市
        geo.setZip(br.getDevice().getGeo().getZip());//zip或者邮政编码
        geo.setUtcoffset(br.getDevice().getGeo().getUtcoffset());//本地时间戳
        geo.setProvince("CN");//省份代码ISO-3166-2


        JmBidRequest.BidRequest.Device.Builder device = JmBidRequest.BidRequest.Device.newBuilder();//device
        device.setUa(br.getDevice().getUa());
        device.setGeo(geo);//设备位置信息
        device.setDnt(br.getDevice().getDnt());//是否跟踪	0跟踪，1不跟
//        device.setIpv6("");//ipv6地址踪
        device.setLmt(3);//是否允许获取 IDFA	IOS 操作 系统必传 0:未确定，开发者尚未请求用户 许可; 1:受限制，用户可以退出 IDFA 在设备级别的所有应用程序; 2: 被拒绝，用户选择退出 IDFA; 3.授权，可以继续获取 IDFA
//        device.setIp("");//ipv4地址
        device.setDevicetype(br.getDevice().getDevicetype());//设备类型	手机：phone平板：ipadPC：pc互联网电视：tv
        device.setMake(br.getDevice().getMake());//制造厂商
        device.setModel(br.getDevice().getModel());//品牌型号
        device.setOs(br.getDevice().getOs());//系统类型	取值如下：Windows: "windows"Android: "android"iPhone: "ios"苹果电脑: "mac"
        device.setOsv(br.getDevice().getOsv());//系统版本
//        device.setHwv("");//硬件设备版本
        device.setH(br.getDevice().getH());//物理屏幕高度
        device.setW(br.getDevice().getW());//物理屏幕宽度
        device.setPpi(br.getDevice().getPpi());//屏幕密度大小
        device.setPxratio(br.getDevice().getPxratio());//物理设备大小比值
        device.setDeny(2);//设备屏幕密度	安卓:context.getResources().getDisplayMetrics().densityiOS:UIScreen.scale取值例如:2.0
//        device.setJs(0);//是否支持js	0:否，1:是
//        device.setFlashver("");//所支持flash版本
//        device.setLanguage("");//语言	使用 alpha-2/ISO 639- 1
        device.setCarrier(br.getDevice().getCarrier());//网络运营商	获取方法参照中国移动：70120中国联通：70123中国电信：70121
        device.setConnectiontype(br.getDevice().getConnectiontype());//网络链接类型	取值如下：eth，wifi，3g，4g，5g，未知留空
//        device.setGaid("");//安卓advertising id	示例：AdvertisingIdClient.Info info = AdvertisingIdClie nt.getAdvertisingIdInfo(context);String gpId = info.getId()
//        device.setIdfa("");//保持原值,ios系统必填
//        device.setIdfaMd5("");//
//        device.setIdfaSha1("");//
//        device.setImei("862733048847100");//imei号
//        device.setImeiMd5("");//
//        device.setImeiSha1("");//
        device.setAndroidId(br.getDevice().getAndroidId());//安卓id	android系统必填,Settings.Secure.getString(context.getContentReso lver(),Settings.Secure.ANDROID_ID);
//        device.setAndroidIdMd5("");//
//        device.setAndroidIdSha1("");//
//        device.setDeviceId("");//设备id
//        device.setDeviceIdMd5("");//
//        device.setDeviceIdSha1("");//
//        device.setMac("");//mac地址
//        device.setMacMd5("");//
//        device.setMacSha1("");//
        device.setOrientation(br.getDevice().getOrientation());//横竖屏:0°,90°,180°,270°,-1未知
//        device.setOpenUdid("");//ios的uuid
//        device.setReferer("");//header头中referer信息
        device.setDeviceSize(br.getDevice().getDeviceSize());//设备分辨率
//        device.setOaid("");//安卓oaid
//        device.setVaid("");//开发者匿名设备标识符 Android Q 以上会有该值
//        device.setVivostorever("");//vivo 应用商店包名,目前仅供 vivo 使用，vivo广告必填
//        device.setElapseTime(0);//开机使用时长单位ms vivo广告必填
//        device.setIdfv("");//IOS 设备当前的 IDFV 值
        device.setPhoneName("jny");//设备名称,例如:张三的 iPhone
//        device.setBootTimeSec("");//设备最近一次开机时间，IOS操作系统必传;秒级时间戳， 小数点后保留 6 位，示例: 1595214620.383940
//        device.setOsUpdateTimeSec("");//系统最近一次更新时间，IOS操作系统必传;秒级时间戳， 小数点后保留 6 位，示例: 1595214620.383940
        device.setDiskSize(64);//手机容量大小，单位:GB 示例:16 . IOS操作系统必传
        device.setBatteryStatus("Full");//电池充电状态，IOS操作系统必传:Unkown:未知;Unplugged:不充电;Charging:充电;Full:满电
        device.setBatteryPower(60);//电池电量百分比，IOS操作系统必传 例如:60
        device.setMemorySize(64);//手机内存大小，单位 GB; IOS操作系统必传 例如:8
        device.setCpuNumber(4);//手机 CPU 个数，IOS操作系统必传: 例如:4
        device.setCpuFrequency(2);//手机 CPU 频率，单位:GHz IOS操作系统必传 例如:2.2
//        device.setModelCode("");//设备型号码，IOS操作系统必 传 例如:D22AP
//        device.setTimeZone("");//当前设备时区，IOS操作系统必传 例如:28800
//        device.setLaccu(0);//定位精准度，IOS操作系统必传0: 定位精准，可以获取到小数 点 4 位及以上;1: 定位不准确


        JmBidRequest.BidRequest.User.Builder user = JmBidRequest.BidRequest.User.newBuilder();//user
        user.setId(br.getUser().getId());//
//        user.setBuyeruid("");//投标人uid
        user.setYob(br.getUser().getYob());//出生年月
        user.setGender(br.getUser().getGender());//性别
//        user.setKeywords("");//关键词，逗号分割
        user.setAge(br.getUser().getAge());//年龄
//        user.setTags("");//用户标签，逗号分割


        /**
         * 总请求
         */
        JmBidRequest.BidRequest.Builder builder = JmBidRequest.BidRequest.newBuilder();
        builder.setId(br.getId());//请求id	接入方自定义，确保唯一性。否则影响填充
        builder.addImp(imp);//广告位曝光对象，暂时只支持一个
//        bidRequest.setSite(site);//媒体站点信息
        builder.setApps(app);//应用信息
        builder.setDevice(device);//设备信息
        builder.setUser(user);//用户信息
        builder.setAdxId(br.getAdxId());//adx分配id
        builder.setAdxName(br.getAdxName());//adx分配名称
//        bidRequest.setMeidaVersion("");//协议版本号

        JmBidRequest.BidRequest bidRequest = builder.build();
        log.info(String.valueOf(bidRequest));
        byte[] contentBr = bidRequest.toByteArray();
        String url = "http://test-adx-bid.jm-ssp.cn:7061/api?id=jClptlwoiVWX&name=limei";
        HttpEntity entityResponse =null;//调用httppost公共方法

        TzBidResponse.BidResponse.Builder bidResponse = TzBidResponse.BidResponse.newBuilder();
        if (null != entityResponse){
           JmBidResponse.BidResponse response = JmBidResponse.BidResponse.parseFrom(entityResponse.getContent());//返回数据

            TzBidResponse.BidResponse.Seat.Bid.BidApps.Builder apps = TzBidResponse.BidResponse.Seat.Bid.BidApps.newBuilder();//bidapps
            apps.setAppName(response.getSeat(0).getBid(0).getApp().getAppName());
            apps.setBundle(response.getSeat(0).getBid(0).getApp().getBundle());
            apps.setAppIcon(response.getSeat(0).getBid(0).getApp().getAppIcon());
            apps.setAppSize(response.getSeat(0).getBid(0).getApp().getAppSize());

            TzBidResponse.BidResponse.Seat.Bid.Image.Builder image = TzBidResponse.BidResponse.Seat.Bid.Image.newBuilder();//Image
            image.setUrl(response.getSeat(0).getBid(0).getImages(0).getUrl());
            image.setH(response.getSeat(0).getBid(0).getImages(0).getH());
            image.setW(response.getSeat(0).getBid(0).getImages(0).getW());

            TzBidResponse.BidResponse.Seat.Bid.Video.Builder video = TzBidResponse.BidResponse.Seat.Bid.Video.newBuilder();//Video
            video.setUrl(response.getSeat(0).getBid(0).getVideo().getUrl());
            video.setH(response.getSeat(0).getBid(0).getVideo().getH());
            video.setW(response.getSeat(0).getBid(0).getVideo().getW());
            video.setDuration(response.getSeat(0).getBid(0).getVideo().getDuration());
            video.setConverImage(image);

            TzBidResponse.BidResponse.Seat.Bid.CheckVideoUrls.Builder checkVideoUrls = TzBidResponse.BidResponse.Seat.Bid.CheckVideoUrls.newBuilder();//CheckVideoUrls
            checkVideoUrls.setUrl(response.getSeat(0).getBid(0).getCheckVideoUrls(0).getUrl());
            checkVideoUrls.setTime(response.getSeat(0).getBid(0).getCheckVideoUrls(0).getTime());

            TzBidResponse.BidResponse.Seat.Bid.Advertiser.Builder advertiser = TzBidResponse.BidResponse.Seat.Bid.Advertiser.newBuilder();//Advertiser
            advertiser.setId(response.getSeat(0).getBid(0).getAdvertiser().getId());
            advertiser.setIndustry(response.getSeat(0).getBid(0).getAdvertiser().getIndustry());
            advertiser.setSubIndustry(response.getSeat(0).getBid(0).getAdvertiser().getSubIndustry());

            TzBidResponse.BidResponse.Seat.Bid.Macros.Builder macros = TzBidResponse.BidResponse.Seat.Bid.Macros.newBuilder();//Macros
            macros.setMacro(response.getSeat(0).getBid(0).getMacros(0).getMacro());
            macros.setValue(response.getSeat(0).getBid(0).getMacros(0).getValue());
            macros.addExpression(response.getSeat(0).getBid(0).getMacros(0).getExpression(0));

            TzBidResponse.BidResponse.Seat.Bid.Builder bid = TzBidResponse.BidResponse.Seat.Bid.newBuilder();//Bid
            bid.setId(response.getSeat(0).getBid(0).getId());
            bid.setImpid(response.getSeat(0).getBid(0).getImpid());
            bid.setPrice(response.getSeat(0).getBid(0).getPrice());
            bid.setAdid(response.getSeat(0).getBid(0).getAdid());
            bid.setNurl(response.getSeat(0).getBid(0).getNurl());
            bid.setAdm(response.getSeat(0).getBid(0).getAdm());
            bid.addAdomain(response.getSeat(0).getBid(0).getAdomain(0));
            bid.setCid(response.getSeat(0).getBid(0).getCid());
            bid.setCrid(response.getSeat(0).getBid(0).getCrid());
            bid.addCat(response.getSeat(0).getBid(0).getCat(0));
            bid.addAttr(response.getSeat(0).getBid(0).getAttr(0));
            bid.setDealid(response.getSeat(0).getBid(0).getDealid());
            bid.setH(response.getSeat(0).getBid(0).getH());
            bid.setW(response.getSeat(0).getBid(0).getW());
            bid.setTitle(response.getSeat(0).getBid(0).getTitle());
            bid.setSubTitle(response.getSeat(0).getBid(0).getSubTitle());
            bid.setDesc(response.getSeat(0).getBid(0).getDesc());
            bid.setStyleId(response.getSeat(0).getBid(0).getStyleId());
            bid.setAndroidUrl(response.getSeat(0).getBid(0).getAndroidUrl());
            bid.setIosUrl(response.getSeat(0).getBid(0).getIosUrl());
            bid.setDownloadMd5(response.getSeat(0).getBid(0).getDownloadMd5());
            bid.setClickUrl(response.getSeat(0).getBid(0).getClickUrl());
            bid.setDeeplinkUrl(response.getSeat(0).getBid(0).getDeeplinkUrl());
            bid.setAdType(response.getSeat(0).getBid(0).getAdType());
            bid.setAdms(response.getSeat(0).getBid(0).getAdms());
            bid.setSource(response.getSeat(0).getBid(0).getSource());
            bid.setValidTime(response.getSeat(0).getBid(0).getValidTime());
            bid.addCheckViews(response.getSeat(0).getBid(0).getCheckViews(0));
            bid.addCheckClicks(response.getSeat(0).getBid(0).getCheckClicks(0));
            bid.addCheckStartDownloads(response.getSeat(0).getBid(0).getCheckStartDownloads(0));
            bid.addCheckEndDownloads(response.getSeat(0).getBid(0).getCheckEndDownloads(0));
            bid.addCheckStartInstalls(response.getSeat(0).getBid(0).getCheckStartInstalls(0));
            bid.addCheckActivations(response.getSeat(0).getBid(0).getCheckActivations(0));
            bid.addCheckSuccessDeeplinks(response.getSeat(0).getBid(0).getCheckSuccessDeeplinks(0));
            bid.addCheckFailDeeplinks(response.getSeat(0).getBid(0).getCheckFailDeeplinks(0));
            bid.setClicktype(response.getSeat(0).getBid(0).getClicktype());
            bid.setApp(apps);
            bid.addImages(image);
            bid.setVideo(video);
            bid.addCheckVideoUrls(checkVideoUrls);
            bid.setAdvertiser(advertiser);
            bid.addMacros(macros);

            TzBidResponse.BidResponse.Seat.Builder seat = TzBidResponse.BidResponse.Seat.newBuilder();//seat
            seat.addBid(bid);
            seat.setSeat(response.getSeat(0).getSeat());

           bidResponse.setId(response.getId());
           bidResponse.addSeatbid(seat);
           bidResponse.setDebugInfo(response.getDebugInfo());
           bidResponse.setProcessTimeMs(response.getProcessTimeMs());
           bidResponse.setMediaVersion(response.getMediaVersion());
        }
        return bidResponse.build();
    }
}
