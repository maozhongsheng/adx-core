package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tengxun.*;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.util.MD5Util;
import com.mk.adx.service.TengXunJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description 腾讯-优量汇
 * @Date 2022/5/26 9:48
 */
@Slf4j
@Service("tengXunJsonService")
public class TengXunJsonServiceImpl implements TengXunJsonService {

    private static final String name = "tengxun";

    private static final String source = "腾讯";

    @SneakyThrows
    @Override
    public TzBidResponse getTengXunDataByJson(TzBidRequest request) {
        //pos对象
        TengXunPos pos = new TengXunPos();
        int pos_id = Integer.parseInt(request.getAdv().getTag_id());
        int width = Integer.valueOf(request.getAdv().getSize().split("\\*")[0]);
        int height = Integer.valueOf(request.getAdv().getSize().split("\\*")[1]);
        pos.setId(pos_id);//广告位ID
        pos.setWidth(width);//广告位宽
        pos.setHeight(height);//广告位高
        pos.setAd_count(1);//请求广告数量


        //media对象
        TengXunMedia media = new TengXunMedia();
        media.setApp_id(request.getAdv().getApp_id());//在联盟平台创建媒体时分配的应用ID。不填写或填写错误将不返回广告
        media.setApp_bundle_id(request.getAdv().getBundle());//应用包名


        //device对象
        TengXunDevice device = new TengXunDevice();
        //os版本。不填将不返回广告，填写错误或填写unknown会影响流量变现效果
        if (null!=request.getDevice().getOsv()){
            device.setOs_version(request.getDevice().getOsv());
        }else {
            device.setOs_version("unknown");
        }
        //设备型号。不填将不返回广告，填写错误或填写unknown会影响流量变现效果
        if (null!=request.getDevice().getModel()){
            device.setModel(request.getDevice().getModel());
        }else {
            device.setModel("unknown");
        }
        //设备厂商。安卓设备不填将不返回广告，填写错误或填写unknown会影响流量变现效果。
        if (null!=request.getDevice().getMake()){
            device.setManufacturer(request.getDevice().getMake());
        }else {
            device.setManufacturer("unknown");
        }
        //设备类型。不填将不返回广告，填写错误或填写unknown会影响流量变现效果
        String devicetype = request.getDevice().getDevicetype();//设备类型，手机:phone, 平板:ipad, PC:pc,互联网电视:tv
        if("phone".equals(devicetype)){
            device.setDevice_type(1);//设备类型:1:手机 2:平板 3:智能电视 4: 户外屏
        }else if("ipad".equals(devicetype)){
            device.setDevice_type(2);
        }else{
            device.setDevice_type(0);
        }
        device.setOrientation(0);//APP横竖屏。激励视频广告位该参数为必填
        //操作系统，0=>Android,1=>iOS
        String os = request.getDevice().getOs();
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            device.setOs("android");//操作系统。不填将不返回广告，填写错误会影响流量变现效果
            device.setImei(request.getDevice().getImei());//android设备的imei，保留原始值。在能取到的情况下必须填写，不填写或填写错误会严重影响流量变现效果
            device.setAndroid_id(request.getDevice().getAndroid_id());//android设备的Android ID，保留原始值。在能取到的情况下必须填写，不填写或填写错误会影响流量变现效果。
            device.setOaid(request.getDevice().getOaid());//android设备的OAID，保留原始值，部分厂商部分安卓系统版本提供，MSA官方链接为：http://msa-alliance.cn/
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOs("ios");//操作系统。不填将不返回广告，填写错误会影响流量变现效果
            device.setIdfa(request.getDevice().getIdfa());//ios设备的idfa，保留原始值。不填将不返回广告，填写错误会严重影响流量变现效果。
            device.setDevice_start_sec(String.valueOf(System.currentTimeMillis()));//设备启动时间（秒）
            device.setCountry(request.getDevice().getCountry());//国家
            device.setLanguage(request.getDevice().getLanguage());//语言
            if (null!=request.getDevice().getPhoneName()){
                device.setDevice_name_md5(MD5Util.getMD5(request.getDevice().getPhoneName()));//设备名称的MD5值
            }else {
                device.setDevice_name_md5(MD5Util.getMD5("Jny的iphone"));//设备名称的MD5值
            }
            device.setHardware_machine("unknown");//设备machine值
            device.setHardware_model("unknown");//设备model值
            device.setPhysical_memory_byte("16");//物理内存
            device.setHarddisk_size_byte("256");//硬盘大小
            device.setSystem_update_sec(String.valueOf(System.currentTimeMillis()));//系统更新时间
            device.setTime_zone("zh_cn");//时区
        }


        //network对象
        TengXunNetwork network = new TengXunNetwork();
        //网络连接类型-取值范围：0:Unknown,1:Ethernet,2:WiFi,3:Cellular Network -Unknown,4: 2G, 5:3G,6:4G,7:5G
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            network.setConnect_type(0);//网络连接类型 可能取值:0 - 未知, 1 - wifi, 2 - 2G, 3 - 3G, 4 - 4G
        }else if("2".equals(connectiontype)){
            network.setConnect_type(1);
        }else if("4".equals(connectiontype)){
            network.setConnect_type(2);
        }else if("5".equals(connectiontype)){
            network.setConnect_type(3);
        }else if("6".equals(connectiontype)){
            network.setConnect_type(4);
        }else if("7".equals(connectiontype)){
            network.setConnect_type(5);
        } else {
            network.setConnect_type(0);
        }
        //运营商类型-中国移动:70120,中国联通:70123，中国电信:70121,广电:70122 其他:70124,未识别:0
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                network.setCarrier(1);//网络运营商类型 可能取值:0 - 未知, 1 - 移动, 2 - 联通, 3 - 电信
            }else if("70123".equals(request.getDevice().getCarrier())){
                network.setCarrier(2);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                network.setCarrier(3);//中国电信
            }else{
                network.setCarrier(0);//其他
            }
        }else{
            network.setCarrier(0);
        }
        network.setIp(request.getDevice().getIp());//IP地址-请从服务端获取IP，不要从客户端获取IP
        network.setUa(request.getDevice().getUa());//客户端WebView的UserAgent信息


        //geo对象-不是必填，先不用
//        TengXunGeo geo = new TengXunGeo();

        //总请求
        TengXunBidRequest bidRequest = new TengXunBidRequest();
        bidRequest.setApi_version("3.0");//
        bidRequest.setPos(JSON.toJSONString(pos));//广告位相关信息
        bidRequest.setMedia(JSON.toJSONString(media));//媒体相关信息
        bidRequest.setDevice(JSON.toJSONString(device));//用户设备相关信息
        bidRequest.setNetwork(JSON.toJSONString(network));//用户设备网络相关信息

        //-------------------------分割线----------------------------------------------------

        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(bidRequest);
        log.info(request.getImp().get(0).getTagid() + "请求腾讯广告参数"+JSONObject.parseObject(content));
        String url = "http://mi.gdt.qq.com/api/v3";
        String ua = request.getDevice().getUa();//ua
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setSzyd_ip(request.getDevice().getIp());//腾讯ip
        pud.setContent(content);//请求参数

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info(request.getImp().get(0).getTagid() + "请求上游腾讯广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid() + "腾讯广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();//请求id

        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            //ret=0的时候成功返回，才做解析
            if (0 == jo.getInteger("ret")){
                List<TzBid> bidList = new ArrayList<>();
                JSONObject data = jo.getJSONObject("data");//广告数据，见data JSON对象
                JSONObject posData = data.getJSONObject(String.valueOf(pos_id));//名称是本次请求的广告位ID。参数值是该广告位的广告数据，见{pos.id} JSON对象
                JSONArray ja = posData.getJSONArray("list");//该数组的每个元素对应一条广告的数据，返回广告个数不超过请求广告个数；数组元素（广告）定义见 list JSON数组元素（广告）
                for (int i=0;i<ja.size();i++){
                    TzBid tb = new TzBid();

                    String ad_id = ja.getJSONObject(i).getString("ad_id");//广告ID
                    Integer crt_type = ja.getJSONObject(i).getInteger("crt_type");//素材类型:可能取值：11 – 图文（2图2文）20 – 视频（1横版视频2图2文或1竖版视频2图2文）24 - 图文（3等比例小图2文）
                    if (crt_type == 20){//视频
                        TzNative tzNative = new TzNative();
                        TzVideo tzVideo = new TzVideo();
                        tzVideo.setUrl(ja.getJSONObject(i).getString("video_url"));
                        tzVideo.setW(ja.getJSONObject(i).getInteger("video_width"));
                        tzVideo.setH(ja.getJSONObject(i).getInteger("video_height"));

                        if (request.getImp().get(0).getAd_slot_type().equals("1")) {//信息流
                            tzNative.setTitle(ja.getJSONObject(i).getString("title"));//广告标题
                            tzNative.setDesc(ja.getJSONObject(i).getString("description"));//广告描述
                            tzNative.setVideo(tzVideo);
                            tb.setNATIVE(tzNative);
                            tb.setAd_type(8);//信息流-视频素材
                        }else {
                            tb.setAd_type(5);//开屏-广告素材类型
                            tb.setTitle(ja.getJSONObject(i).getString("title"));//广告标题
                            tb.setDesc(ja.getJSONObject(i).getString("description"));//广告描述
                            tb.setVideo(tzVideo);//其他类型素材-视频素材
                        }
                    }else if (crt_type == 24) {//三图两文
                        List<TzImage> list = new ArrayList<>();//图片集合
                        JSONArray img_list = ja.getJSONObject(i).getJSONArray("img_list");//图片数组
                        for (int k=0; k < img_list.size();k++){
                            TzImage tzImage = new TzImage();
                            tzImage.setUrl(img_list.get(k).toString());//图⽚地址，外⽹地址，多图时按顺序返回展示
                            tzImage.setW(ja.getJSONObject(i).getInteger("img_width"));//图⽚宽度，图⽚物料的宽度，单位：像素
                            tzImage.setH(ja.getJSONObject(i).getInteger("img_height"));//图⽚⾼度，图⽚物料的⾼度，单位：像素
                            list.add(tzImage);

                            if (request.getImp().get(0).getAd_slot_type().equals("1")){//信息流
                                TzNative tzNative = new TzNative();
                                tb.setAd_type(8);//信息流-广告素材类型
                                tzNative.setTitle(ja.getJSONObject(i).getString("title"));//广告标题
                                tzNative.setDesc(ja.getJSONObject(i).getString("description"));//广告描述
                                tzNative.setImages(list);
                                tb.setNATIVE(tzNative);
                            }else {
                                tb.setAd_type(5);//开屏-广告素材类型
                                tb.setTitle(ja.getJSONObject(i).getString("title"));//广告标题
                                tb.setDesc(ja.getJSONObject(i).getString("description"));//广告描述
                                tb.setImages(list);//其他类型素材-图片集合
                            }
                        }
                    } else{//其他图文
                        List<TzImage> list = new ArrayList<>();//图片集合
                        TzImage tzImage = new TzImage();
                        tzImage.setUrl(ja.getJSONObject(i).getString("img_url"));//图⽚地址，外⽹地址，多图时按顺序返回展示
                        tzImage.setW(ja.getJSONObject(i).getInteger("img_width"));//图⽚宽度，图⽚物料的宽度，单位：像素
                        tzImage.setH(ja.getJSONObject(i).getInteger("img_height"));//图⽚⾼度，图⽚物料的⾼度，单位：像素
                        list.add(tzImage);

                        if (request.getImp().get(0).getAd_slot_type().equals("1")){//信息流
                            TzNative tzNative = new TzNative();
                            tb.setAd_type(8);//信息流-广告素材类型
                            tzNative.setTitle(ja.getJSONObject(i).getString("title"));//广告标题
                            tzNative.setDesc(ja.getJSONObject(i).getString("description"));//广告描述
                            tzNative.setImages(list);
                            tb.setNATIVE(tzNative);
                        }else {
                            tb.setAd_type(5);//开屏-广告素材类型
                            tb.setTitle(ja.getJSONObject(i).getString("title"));//广告标题
                            tb.setDesc(ja.getJSONObject(i).getString("description"));//广告描述
                            tb.setImages(list);//其他类型素材-图片集合
                        }
                    }

                    //点击类型：
                    //0 - 打开网页
                    //1 - app下载
                    Integer interact_type = ja.getJSONObject(i).getInteger("interact_type");
                    if(0 == interact_type){
                        tb.setClicktype("0");//点击
                    }else if(1 == interact_type){
                        tb.setClicktype("3");//下载
                    }else{
                        tb.setClicktype("0");//点击
                    }

                    tb.setClick_url(ja.getJSONObject(i).getString("landing_page_url"));//落地页url，点击后跳转对应落地页；所有广告必定返回
                    String package_url = ja.getJSONObject(i).getString("package_url");//应用下载url


                    //曝光上报地址列表
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                    String impression_link = ja.getJSONObject(i).getString("impression_link");//上游曝光监测链接
                    check_views.add(impression_link);
                    String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_VIEWS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                    //点击上报地址列表
                    List<String> clickList = new ArrayList<>();
                    String click_link = ja.getJSONObject(i).getString("click_link");
                    clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                    String replace = click_link.replace("__REQ_WIDTH__", String.valueOf(width)).replace("__REQ_HEIGHT__", String.valueOf(height)).replace("__WIDTH__", String.valueOf(width)).replace("__HEIGHT__", String.valueOf(height)).replace("__DOWN_X__", "-999").replace("__DOWN_Y__", "-999").replace("__UP_X__", "-999").replace("__UP_Y__", "-999");
                    clickList.add(replace);
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                    tzMacros = new TzMacros();
                    tzMacros.setMacro("%%CHECK_CLICKS%%");
                    tzMacros.setValue(Base64.encode(encode));
                    tzMacros1.add(tzMacros);

                    //转化上报地址-仅当返回APP下载类广告（interact_type=1）或当返回应用直达广告（customized_invoke_url参数存在）时，该参数有效
                    if(interact_type == 1) {
                        String conversion_link = ja.getJSONObject(i).getString("conversion_link");//转化上报地址

                        //246 – 应用直达广告唤起APP成功
                        tb.setDeeplink_url(conversion_link);//deeplink 唤醒地址deeplink 唤醒广告打开页面
                        List<String> deep_linkT = new ArrayList<>();
                        deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        String tk_dp_success = conversion_link.replace("__ACTION_ID__", "246");//deeplink_url 不为空时，唤醒成功时监测
                        deep_linkT.add(tk_dp_success);
                        tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);

                        //5 - 下载开始
                        List<String> downLoadList = new ArrayList<>();
                        downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                        String start_download = conversion_link.replace("__ACTION_ID__", "5");
                        downLoadList.add(start_download);
                        tb.setCheck_start_downloads(downLoadList);//开始下载
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DOWN_LOAD%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);

                        //7 - 下载完成
                        List<String> downLoadListEnd = new ArrayList<>();
                        downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                        String end_download = conversion_link.replace("__ACTION_ID__", "7");
                        downLoadList.add(end_download);
                        tb.setCheck_end_downloads(downLoadListEnd);//完成下载
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DOWN_LOAD%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);

                        //6 - 安装完成
                        List<String> installEList = new ArrayList<>();
                        installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                        String end_install = conversion_link.replace("__ACTION_ID__", "6");
                        installEList.add(end_install);
                        tb.setCheck_end_installs(installEList);//安装完成
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    tb.setMacros(tzMacros1);
                    tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);

                    TzSeat seat = new TzSeat();//素材集合对象
                    seat.setBid(bidList);
                    seatList.add(seat);

                    bidResponse.setId(request.getId());//请求id
                    bidResponse.setBidid(ad_id);//广告主返回id 请求唯一标识符
                    bidResponse.setSeatbid(seatList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info(request.getImp().get(0).getTagid() + "众盟广告总返回"+JSONObject.toJSONString(bidResponse));
                }
            }
        }

        return bidResponse;
    }
}
