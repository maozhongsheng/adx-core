package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.hailiang.*;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkApp;
import com.mk.adx.entity.json.response.mk.MkBid;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.entity.json.response.mk.MkImage;
import com.mk.adx.service.HailiangJsonService;
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
 * @Description 嗨量
 * @Date 2021/11/23 9:48
 */
@Slf4j
@Service("hailiangJsonService")
public class HailiangJsonServiceImpl implements HailiangJsonService {

    private static final String name = "hailiang";

    private static final String source = "嗨量";


    @SneakyThrows
    @Override
    public MkBidResponse getHailiangDataByJson(MkBidRequest request) {
        HailiangBidRequest hbr = new HailiangBidRequest();//嗨量总请求
        hbr.setId(request.getId());//必填，标识⼀次请求的唯⼀ID
        hbr.setTmax(1000);
        hbr.setApi_ver("1.9");
        List<HailiangImp> impList = new ArrayList<>();//imp集合
        if (null!=request.getImp()) {
            for (int i=0;i<request.getImp().size();i++){
                HailiangImp imp = new HailiangImp();
                imp.setId(request.getId());//必填 标识⼀个Imp对象的唯⼀ID
                imp.setSlot_id(request.getAdv().getTag_id());//必填，当前Imp所对应的⼴告位ID（嗨量提供）
                String ad_slot_type = request.getImp().get(i).getSlot_type();//广告位类型
                if ("2".equals(ad_slot_type)){
                    imp.setSlot_type(0);//横幅固定⼴告位
                }else if ("6".equals(ad_slot_type)){
                    imp.setSlot_type(1);//插屏⼴告位i
                }else if ("3".equals(ad_slot_type)){
                    imp.setSlot_type(3);//开屏广告位
                }else if ("1".equals(ad_slot_type)){
                    imp.setSlot_type(4);//原⽣⼴告位
                }else if ("4".equals(ad_slot_type)){
                    imp.setSlot_type(5);//激励视频
                }
                imp.setBid_floor(request.getAdv().getPrice());
                impList.add(imp);
            }
            hbr.setImp(impList);
        }

        HailiangApp app = new HailiangApp();//必填，当前请求发⾃某个应⽤时，必须提供完整的App对象
        if (null!=request.getApp()){
            app.setId(request.getAdv().getApp_id());//
            app.setName(request.getAdv().getApp_name());//媒体app名称
            app.setBundle(request.getAdv().getBundle());//应用程序包或包名称
            app.setVer(request.getAdv().getVersion());//app应用版本
        }
        hbr.setApp(app);

        HailiangDevice device = new HailiangDevice();//设备信息
        if (null!=request.getDevice()){
            device.setUa(request.getDevice().getUa());//必填，描述发起当前请求的浏览器User Agent
            device.setIp(request.getDevice().getIp());//必填，当前设备的IP信息
            device.setMac(request.getDevice().getMac());//条件必填，设备MAC信息
            String os = request.getDevice().getOs();//系统类型
            if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
                device.setOs(2);
                device.setOaid(request.getDevice().getOaid());//条件必填，Android Q以后的⼿机唯⼀标识符。当请求发⾃Android设备时，必填
                if(10 > Integer.valueOf(request.getDevice().getOsv().split("\\.")[0]) && StringUtils.isEmpty(request.getDevice().getImei())){
                    device.setDid("861295046053595");
                }else{
                    device.setDid(request.getDevice().getImei());//条件必填，设备号(IMEI)信息。当请求发⾃Android设备时，必填
                }
                if(10 <= Integer.valueOf(request.getDevice().getOsv().split("\\.")[0]) && StringUtils.isEmpty(request.getDevice().getOaid())){
                    device.setOaid("85f186bc-9c1f-1e96-35f2-77af3d2a35fb");
                }else{
                    device.setOaid(request.getDevice().getOaid());//条件必填，设备号(OAID)信息。当请求发⾃Android设备时，必填
                }
                device.setDpid(request.getDevice().getAndroid_id());//条件必填，Android-id信息。当请求发⾃Android设备时，必填
                device.setIdfa(request.getDevice().getIdfa());//条件必填，Apple设备的IDFA信息。当请求发⾃Apple设备时，必填
                device.setMac(request.getDevice().getMac());//条件必填，设备MAC信息。
            }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
                device.setOs(1);
                device.setIdfa(request.getDevice().getIdfa());//条件必填，Apple设备的IDFA信息。当请求发⾃Apple设备时，必填
                device.setMac(request.getDevice().getMac());//条件必填，设备MAC信息。
            }else {
                device.setOs(0);
            }

            if("0".equals(request.getDevice().getCarrier())){//必填，提供完整的Carrier对象来描述当前运营商信息
                device.setCarrier(0);//未知
            }else if("70120".equals(request.getDevice().getCarrier())){
                device.setCarrier(1);//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                device.setCarrier(2);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                device.setCarrier(3);//中国电信
            }else if("70124".equals(request.getDevice().getCarrier())){
                device.setCarrier(4);//中国⽹通
            }
            device.setMake(request.getDevice().getMake());//必填，填充完整的make信息来描述当前设备的制造商信息
            device.setModel(request.getDevice().getModel());//必填，填充完整的model信息来描述当前设备的型号信息
            device.setOsv(request.getDevice().getOsv());//必填，当前设备操作系统的版本号信息
            device.setW(request.getDevice().getW());//必填，当前设备显示屏的宽度
            device.setH(request.getDevice().getH());//必填，当前设备显示屏的⾼度
            device.setStore_ver(request.getApp().getVer());
            if (0 == request.getDevice().getConnectiontype()) {//必填，发起当前请求的设备⽹络连接类型信息
                device.setConnectiontype(0);//未知
            }else if(1 == request.getDevice().getConnectiontype()){
                device.setConnectiontype(1);//Ethernet
            }else if(2 == request.getDevice().getConnectiontype()){
                device.setConnectiontype(2);//wifi
            }else if(4 == request.getDevice().getConnectiontype()){
                device.setConnectiontype(3);//移动⽹络2G Cellular Network – 2G
            }else if(5 == request.getDevice().getConnectiontype()){
                device.setConnectiontype(4);//移动⽹络3G Cellular Network – 3G
            }else if(6 == request.getDevice().getConnectiontype()){
                device.setConnectiontype(5);//移动⽹络4G Cellular Network – 4G
            }else if(7 == request.getDevice().getConnectiontype()){
                device.setConnectiontype(6);//移动⽹络4G Cellular Network – 5G
            }

            if("pc".equals(request.getDevice().getDevicetype()) || "tv".equals(request.getDevice().getDevicetype())){//必填，发起当前请求的设备类型信息
                device.setDevicetype(0);//未知
            }else if("phone".equals(request.getDevice().getDevicetype())){
                device.setDevicetype(1);//⼿机设备
            }else if("ipad".equals(request.getDevice().getDevicetype())){
                device.setDevicetype(2);//平板设备
            }
        }
        hbr.setDevice(device);

        MkBidResponse bidResponse = new MkBidResponse();//总返回
        String content = JSONObject.toJSONString(hbr);
        log.info(request.getImp().get(0).getTagid() + ":请求嗨量参数" + JSONObject.parseObject(content));
        String url = "http://adx-v1.halomobi.com/ssp/request";
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
        log.info(request.getImp().get(0).getTagid() + ":请求上游嗨量花费时间：" +
                (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") +
                ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) +
                ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) +
                ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) +
                ((tempTime % 1000) + "ms"));
        log.info(request.getImp().get(0).getTagid() + ":嗨量返回参数" + JSONObject.parseObject(response));
        if (null != response) {
            //多层解析json
            JSONObject jo = JSONObject.parseObject(response);
            JSONArray data = jo.getJSONArray("seatbid");
            if (data != null) {
                List<MkBid> bidList = new ArrayList<>();
                String id = request.getId();
                for (int i = 0; i < data.size(); i++) {
                    id = request.getId();
                    JSONArray bid = data.getJSONObject(i).getJSONArray("bid");
                    MkBid tb = new MkBid();
                    List<MkApp> appsList = new ArrayList<>();
                    List<MkImage> list = new ArrayList<>();
                    for (int j = 0; j < bid.size(); j++) {
                        tb.setAdid(id);
                        MkApp apps = new MkApp();
                        apps.setBundle(bid.getJSONObject(j).getString("bundle"));
                        apps.setApp_name(bid.getJSONObject(j).getString("appname"));
                        apps.setApp_icon(bid.getJSONObject(j).getString("appicon"));
                        appsList.add(apps);
                        if ("1".equals(request.getImp().get(i).getSlot_type()) || "2".equals(request.getImp().get(i).getSlot_type())) {//信息流或banner
                            tb.setAd_type(1);//原生-广告素材类型
                        }else {
                            tb.setAd_type(2);//开屏-广告素材类型
                        }
                        JSONObject jsonObject = bid.getJSONObject(j).getJSONObject("image");
                        JSONArray jsonArray = bid.getJSONObject(j).getJSONArray("images");
                        if (jsonObject != null) {
                            MkImage tzImage = new MkImage();
                            tzImage.setUrl(jsonObject.getString("url"));
                            tzImage.setW(jsonObject.getInteger("w"));
                            tzImage.setH(jsonObject.getInteger("h"));
                            list.add(tzImage);
                        } else if (jsonArray != null) {
                            for (int g = 0; g < jsonArray.size(); g++) {
                                MkImage tzImage = new MkImage();
                                tzImage.setUrl(jsonArray.get(g).toString());
                                tzImage.setW(bid.getJSONObject(j).getInteger("w"));
                                tzImage.setH(bid.getJSONObject(j).getInteger("h"));
                                list.add(tzImage);
                            }
                            tb.setImages(list);
                        }
                        tb.setAdLogo(bid.getJSONObject(j).getString("logo"));
                        tb.setTitle(bid.getJSONObject(j).getString("title"));
                        tb.setDesc(bid.getJSONObject(j).getString("des"));
                        if (null != bid.getJSONObject(j).getBoolean("is_download")) {
                            tb.setClicktype("3");
                            tb.setDownload_url(bid.getJSONObject(j).getString("file_url"));
                        } else if (null != bid.getJSONObject(j).getBoolean("is_deep")) {
                            tb.setClicktype("2");
                            tb.setDeeplink_url(bid.getJSONObject(j).getString("deeplink"));
                            tb.setClick_url(bid.getJSONObject(j).getString("landing_url"));
                        } else {
                            tb.setClicktype("0");
                            tb.setDeeplink_url(bid.getJSONObject(j).getString("deeplink"));
                            tb.setClick_url(bid.getJSONObject(j).getString("landing_url"));
                        }


                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + "," + request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();


                        List<String> clickList = new ArrayList<>();
                        JSONArray clickUrl = bid.getJSONObject(j).getJSONArray("click_tracks");
                        if (null != clickUrl && 0 < clickUrl.size()) {
                            clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                            for (int click = 0; click < clickUrl.size(); click++) {
                                Long dateTime = System.currentTimeMillis() / 1000;
                                Long dateMidTime = System.currentTimeMillis();
                                clickList.add(clickUrl.get(click).toString().replace("__TS_S__", dateTime.toString()).replace("__TS__", dateMidTime.toString()).replace("__DOWN_X__", "%%DOWN_X%%").replace("__DOWN_Y__ ", "%%DOWN_Y%%").replace("__UP_X__", "%%UP_X%%").replace("__UP_Y__", "%%UP_Y%%").replace("__PNT_DOWN_X__", "%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__", "%%ABS_DOWN_Y%%").replace("__PNT_UP_X__", "%%ABS_UP_X%%").replace("__PNT_UP_Y__", "%%ABS_UP_Y%%"));
                            }
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        }

                        List<String> checkViews = new ArrayList<>();
                        JSONArray monitorUrl = bid.getJSONObject(j).getJSONArray("imp_tracks");
                        if (null != monitorUrl && 0 < monitorUrl.size()) {
                            checkViews.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                            for (int view = 0; view < monitorUrl.size(); view++) {
                                Long dateTime = System.currentTimeMillis() / 1000;
                                Long dateMidTime = System.currentTimeMillis();
                                checkViews.add(monitorUrl.get(view).toString().replace("__TS_S__", dateTime.toString()).replace("__TS__", dateMidTime.toString()).replace("__DOWN_X__", "%%DOWN_X%%").replace("__DOWN_Y__ ", "%%DOWN_Y%%").replace("__UP_X__", "%%UP_X%%").replace("__UP_Y__", "%%UP_Y%%").replace("__PNT_DOWN_X__", "%%ABS_DOWN_X%%").replace("__PNT_DOWN_Y__", "%%ABS_DOWN_Y%%").replace("__PNT_UP_X__", "%%ABS_UP_X%%").replace("__PNT_UP_Y__", "%%ABS_UP_Y%%"));//曝光监测URL,支持宏替换第三方曝光监测
                            }
                            tb.setCheck_views(checkViews);//曝光监测URL,支持宏替换第三方曝光监测
                        }

                        List<String> checkSuccessDeeplinks = new ArrayList<>();
                        JSONArray deeplinkmurl = bid.getJSONObject(j).getJSONArray("dp_tracks");//仅用于唤醒广告，deeplink 链接调起成功
                        if (null != deeplinkmurl && 0 < deeplinkmurl.size()) {
                            checkSuccessDeeplinks.add("http://adx.fxlxz.com/sl/dp_success?deeplink=" + Base64.encode(encode));
                            for (int cm = 0; cm < deeplinkmurl.size(); cm++) {
                                checkSuccessDeeplinks.add(deeplinkmurl.get(cm).toString());
                            }
                            tb.setCheck_success_deeplinks(checkSuccessDeeplinks);
                        }

                        JSONArray dmurl = bid.getJSONObject(j).getJSONArray("down_start_tracks");//app 下载开始的监测地址
                        if (null != dmurl && 0 < dmurl.size()) {
                            List<String> checkStartDownloads = new ArrayList<>();
                            checkStartDownloads.add("http://adx.fxlxz.com/sl/dl_start?downloadStart=" + Base64.encode(encode));
                            for (int cm = 0; cm < dmurl.size(); cm++) {
                                checkStartDownloads.add(dmurl.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_start_downloads(checkStartDownloads);//曝光监测URL,支持宏替换第三方曝光监测
                        }


                        JSONArray downsuccessurl = bid.getJSONObject(j).getJSONArray("down_complete_tracks");//app 下载完成的监测地址
                        if (null != downsuccessurl && 0 < downsuccessurl.size()) {
                            List<String> checkEndDownloads = new ArrayList<>();
                            checkEndDownloads.add("http://adx.fxlxz.com/sl/dl_end?downloadEnd=" + Base64.encode(encode));
                            for (int cm = 0; cm < downsuccessurl.size(); cm++) {
                                checkEndDownloads.add(downsuccessurl.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_end_downloads(checkEndDownloads);
                        }


                        JSONArray check_start_installss = bid.getJSONObject(j).getJSONArray("install_start_tracks");
                        if (null != check_start_installss && 0 < check_start_installss.size()) {
                            List<String> check_start_installs = new ArrayList<>();
                            check_start_installs.add("http://adx.fxlxz.com/sl/in_start?installStart=" + Base64.encode(encode));
                            for (int cm = 0; cm < check_start_installss.size(); cm++) {
                                check_start_installs.add(check_start_installss.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_start_installs(check_start_installs);//开始安装监测URL第三方曝光监测
                        }

                        JSONArray check_end_installss = bid.getJSONObject(j).getJSONArray("install_complete_tracks");
                        if (null != check_end_installss && 0 < check_end_installss.size()) {
                            List<String> check_end_installs = new ArrayList<>();
                            check_end_installs.add("http://adx.fxlxz.com/sl/in_end?installEnd=" + Base64.encode(encode));
                            for (int cm = 0; cm < check_end_installss.size(); cm++) {
                                check_end_installs.add(check_end_installss.get(cm).toString().replace("__CLICK_ID__", "%%CLICK_ID%%"));
                            }
                            tb.setCheck_end_installs(check_end_installs);//安装完成监测URL第三方曝光监测
                        }
                    }
                    bidList.add(tb);
                }
                bidResponse.setId(id);//请求id
                bidResponse.setSeatbid(bidList);//广告集合对象
                bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                log.info(request.getImp().get(0).getTagid() + ":嗨量总返回" + JSONObject.toJSONString(bidResponse));
            }
        }
        return bidResponse;
    }
}
