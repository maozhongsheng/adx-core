package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.lanwa.LanWaBidRequest;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBid;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.entity.json.response.mk.MkCheckVideoUrls;
import com.mk.adx.entity.json.response.mk.MkImage;
import com.mk.adx.service.LanWaJsonService;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mzs
 * @Description 蓝蛙
 * @Date 2022/3/14 9:48
 */
@Slf4j
@Service("lanWaJsonService")
public class LanWaJsonServiceImpl implements LanWaJsonService {

    private static final String name = "lanwa";

    private static final String source = "蓝蛙";

    @SneakyThrows
    @Override
    public MkBidResponse getLanWaDataByJson(MkBidRequest request) {
        LanWaBidRequest lwb = new LanWaBidRequest();//蓝蛙总请求
        lwb.setApp_name(request.getApp().getName());//应用名称
        lwb.setApp_version(request.getApp().getVer());//应用版本,来源 于 manifest 的 versionName ，而不是versionCode如3.5.6
        lwb.setApp_version_code(119);//应用版本号,来源于 manifest 的 versionCode。如 119
        lwb.setPkg_name(request.getApp().getBundle());//应用包名
        String os = request.getDevice().getOs();//系统类型
        if ("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            lwb.setPlatform(1);
            lwb.setOs("Android ");
            lwb.setOsapilevel(11);
            lwb.setAndroid_id(request.getDevice().getAndroid_id());//Android ID
            lwb.setAuidmd5(request.getDevice().getAndroid_id_md5());//
            lwb.setAuidsha1(request.getDevice().getAndroid_id_sha1());//
            lwb.setImei(request.getDevice().getImei());//Android 系统必填，Android手机设备的imei
            lwb.setImeimd5(request.getDevice().getImei_md5());//
            lwb.setImeisha1(request.getDevice().getImei_sha1());//
            lwb.setOaid(request.getDevice().getOaid());//广告标识符，安卓10以上必填
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {
            lwb.setPlatform(2);
            lwb.setOs("iOS ");
            lwb.setIdfa(request.getDevice().getIdfa());//IOS系统必填，原生大写
            lwb.setIdfv(request.getDevice().getIdfv());//IOS系统必填
            lwb.setOpenudid(request.getDevice().getOpen_udid());//IOS系统必填,设备识别码
        }else {
            lwb.setPlatform(0);//平台：0 - 未知 1 - 安卓 2 - IOS 3 - 其他
        }
        lwb.setMake(request.getDevice().getMake());//设备制造商，例如 Samsung
        lwb.setModel(request.getDevice().getModel());//设备硬件型号，例如 iPhone
        lwb.setBrand(request.getDevice().getMake());//手机品牌
        lwb.setHwv(request.getDevice().getHwv());//硬件型号版本，例如 iPhone 中的 7S，默认值："unkown"
        lwb.setOs_version(request.getDevice().getOsv());//系统版本
        lwb.setIp(request.getDevice().getIp());//IP地址
        lwb.setUa(request.getDevice().getUa());//系统 user-agent
        lwb.setMac(request.getDevice().getMac());//设备mac地址
        lwb.setMacmd5(request.getDevice().getMac_md5());//
        lwb.setDensity(String.valueOf(request.getDevice().getDeny()));//屏幕密度，比例值（dpi/160） eg: 1.5
        if (request.getDevice().getPpi()!=null){
            lwb.setPpi(request.getDevice().getPpi());//像素密度，表示每英寸的像素数。eg:401
        }else {
            lwb.setPpi(401);
        }

        lwb.setDpi(240);//像素点密度，表示每英寸的点数。eg:240
        lwb.setScreen_width(request.getDevice().getW());//屏幕的宽度
        lwb.setScreen_height(request.getDevice().getH());//屏幕的高度
        lwb.setScreen_orient(1);//屏幕的方向：1 - 竖屏 2 - 横屏

        //客户端网络类型:0.unkown 1.wifi 2.2G 3.3G 4.4G 5.5G
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            lwb.setNet_type(0);
        }else if("2".equals(connectiontype)){
            lwb.setNet_type(1);
        }else if("4".equals(connectiontype)){
            lwb.setNet_type(2);
        }else if("5".equals(connectiontype)){
            lwb.setNet_type(3);
        }else if("6".equals(connectiontype)){
            lwb.setNet_type(4);
        }else if("7".equals(connectiontype)){
            lwb.setNet_type(5);
        } else {
            lwb.setNet_type(0);
        }

        //运营商类型
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                lwb.setCarrier(1);//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                lwb.setCarrier(2);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                lwb.setCarrier(3);//中国电信
            }else{
                lwb.setCarrier(4);//其他
            }
        }else{
            lwb.setCarrier(0);
        }

//        lwb.setBssid("Tp-link");//无线网 ssid 名称
//        lwb.setWifi_mac("");//wifi 路由的 mac 地址
//        lwb.setSerialno("");//系统设备序列号（获取不到需要报备）

        lwb.setSupport_deeplink(1);//是否支持deeplink:0.不支持 1.支持
        lwb.setSupport_universal(0);//是否支持universal link:0.不支持 1.支持




        //总返回
        MkBidResponse bidResponse = new MkBidResponse();
        String content = JSONObject.toJSONString(lwb);
        log.info(request.getImp().get(0).getTagid()+":请求蓝蛙广告参数"+JSONObject.parseObject(content));
        String url = "http://ssp.ibluefrog.com/media/launch?ad_code=2923";
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
        log.info(request.getImp().get(0).getTagid()+":请求上游蓝蛙广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid()+":蓝蛙广告返回参数"+JSONObject.parseObject(response));

        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        jo.getString("msg");//响应消息

        //code=0的时候成功返回，才做解析
        if (0 == jo.getInteger("code")){
            JSONObject jj = jo.getJSONObject("data");//广告信息
            List<MkBid> bidList = new ArrayList<>();
            MkBid tb = new MkBid();
            //区分信息流和开屏等其他类型广告
            List<MkImage> list = new ArrayList<>();
            if (null!=request.getImp()) {
                for (int j = 0; j < request.getImp().size(); j++) {
                    JSONArray imgs = jj.getJSONArray("imgs");//图片地址数组
                    for (int g = 0; g < imgs.size(); g++){
                        MkImage tzImage = new MkImage();
                        tzImage.setUrl(imgs.get(g).toString());
                        tzImage.setW(jj.getInteger("ad_width"));//素材宽
                        tzImage.setH(jj.getInteger("ad_height"));//素材高
                        tb.setAdLogo(jj.getString("logo"));//广告图标
                        list.add(tzImage);//信息流素材-图片集合
                    }
                    tb.setTitle(jj.getString("title"));//广告标题
                    tb.setDesc(jj.getString("desc"));//广告描述
                    tb.setImages(list);//其他类型素材-图片集合
                    if ("4".equals(jj.getInteger("ad_type"))) {//信息流素材-图片集合
                        tb.setAd_type(8);//信息流-广告素材类型
                    }else {
                        tb.setAd_type(5);//开屏-广告素材类型
                    }
                }
            }

            Integer interaction_type = jj.getInteger("interaction_type");//广告操作行为：0 - 落地页 1 - 下载 2 - 唤醒
            if(0 == interaction_type){
                tb.setClicktype("0");//点击
            }else if(1 == interaction_type){
                tb.setClicktype("4");//下载
            }else if(2 == interaction_type){
                tb.setClicktype("2");//拉活
            }else{
                tb.setClicktype("0");//点击
            }

//            Integer rp = jj.getInteger("replace");//是否需要宏替换:0 - 不需要 1 - 需要

            tb.setClick_url(jj.getString("clk")); // 点击跳转url地址

            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();


            //deeplink以及监测
            if(StringUtils.isNotEmpty(jj.getString("deeplink"))){
                tb.setDeeplink_url(jj.getString("deeplink"));//deeplink 唤醒地址deeplink 唤醒广告打开页面
                List<String> deep_linkT = new ArrayList<>();
                deep_linkT.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                JSONArray tk_dp_try = jj.getJSONObject("event").getJSONArray("open_app_click");//deeplink被点击(尝试唤起)，如果为空，无需上报
                JSONArray tk_dp_success = jj.getJSONObject("event").getJSONArray("open_app_success");//deeplink_url 不为空时，唤醒成功时监测
                JSONArray tk_dp_fail = jj.getJSONObject("event").getJSONArray("open_app_false");//deeplink_url 不为空时，唤醒失败时监测
//                for (int t = 0; t < tk_dp_try.size(); t++) {
//                    deep_linkT.add(tk_dp_try.get(t).toString());
//                }
                for (int dp = 0; dp < tk_dp_success.size(); dp++) {
                    deep_linkT.add(tk_dp_success.get(dp).toString());
                }
//                for (int f = 0; f < tk_dp_fail.size(); f++) {
//                    deep_linkT.add(tk_dp_fail.get(f).toString());
//                }
                tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
            }

            //展示监测
            if(null != jj.getJSONObject("event").getJSONArray("show")){
                List<String> check_views = new ArrayList<>();
                check_views.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                JSONArray urls1 = jj.getJSONObject("event").getJSONArray("show");
                for (int cv = 0; cv < urls1.size(); cv++) {
                    String replace = urls1.get(cv).toString().replace("__MTS__", "__TS__");
                    check_views.add(replace);
                }
                tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
            }

            //点击监测
            if(null != jj.getJSONObject("event").getJSONArray("click")){
                List<String> clickList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("click");
                clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                for (int cc = 0; cc < urls1.size(); cc++) {
                    String replace = urls1.get(cc).toString().replace("__MTS__", "__TS__").replace("__DOWN_OFFSET_X__","__DOWN_X__").replace("__DOWN_OFFSET_Y__","__DOWN_Y__").replace("__OFFSET_X__","__UP_X__").replace("__OFFSET_Y__","__UP_Y__");
                    clickList.add(replace);
                }
                tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
            }

            //开始下载上报数组
            if(null != jj.getJSONObject("event").getJSONArray("start_down")){
                List<String> downLoadList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("start_down");
                downLoadList.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                for (int dl = 0; dl < urls1.size(); dl++) {
                    downLoadList.add(urls1.get(dl).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                }
                tb.setCheck_start_downloads(downLoadList);//开始下载
            }

            //下载完成上报数组
            if(null != jj.getJSONObject("event").getJSONArray("down_done")){
                List<String> downLoadDList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("down_done");
                downLoadDList.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode));
                for (int dle = 0; dle < urls1.size(); dle++) {
                    downLoadDList.add(urls1.get(dle).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                }
                tb.setCheck_end_downloads(downLoadDList);//结束下载
            }

            //开始安装上报数组
            if(null != jj.getJSONObject("event").getJSONArray("start_install")){
                List<String> installList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("start_install");
                installList.add("http://adx.fxlxz.com/sl/in_start?installStart=" + Base64.encode(encode));
                for (int ins = 0; ins < urls1.size(); ins++) {
                    installList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                }
                tb.setCheck_start_installs(installList);//开始安装
            }

            //安装完成上报数组
            if(null != jj.getJSONObject("event").getJSONArray("install_done")){
                List<String> installEList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("install_done");
                installEList.add("http://adx.fxlxz.com/sl/in_end?installEnd=" + Base64.encode(encode));
                for (int ins = 0; ins < urls1.size(); ins++) {
                    installEList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                }
                tb.setCheck_end_installs(installEList);//安装完成
            }

            //视频开始播放追踪 url 数组
            List<MkCheckVideoUrls> videourls = new ArrayList();
            MkCheckVideoUrls mkCheckVideoUrls = new MkCheckVideoUrls();
            if(null != jj.getJSONObject("event").getJSONArray("start_tracks")){
                List<String> voidStartList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("start_tracks");
                voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                for (int vs = 0; vs < urls1.size(); vs++) {
                    voidStartList.add(urls1.get(vs).toString());
                }
                mkCheckVideoUrls.setTime(0);
                mkCheckVideoUrls.setUrl(voidStartList);
                videourls.add(mkCheckVideoUrls);
            }

            //视频播放完成追踪 url 数组
            if(null != jj.getJSONObject("event").getJSONArray("complete_tracks")){
                List<String> voidEndList = new ArrayList<>();
                JSONArray urls1 =  jj.getJSONObject("event").getJSONArray("complete_tracks");
                voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                for (int ve = 0; ve < urls1.size(); ve++) {
                    voidEndList.add(urls1.get(ve).toString());
                }
                mkCheckVideoUrls.setTime(1);
                mkCheckVideoUrls.setUrl(voidEndList);
                videourls.add(mkCheckVideoUrls);
            }
            tb.setCheck_video_urls(videourls);//视频

            bidList.add(tb);

            bidResponse.setId(id);//请求id
            bidResponse.setBidid(jo.getString("unique"));//广告主返回id 请求唯一标识符
            bidResponse.setSeatbid(bidList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info(request.getImp().get(0).getTagid()+":蓝蛙广告总返回"+JSONObject.toJSONString(bidResponse));

        }

        return bidResponse;
    }
}
