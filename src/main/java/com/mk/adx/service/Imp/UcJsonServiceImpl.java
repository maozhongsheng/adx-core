package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.uc.*;
import com.mk.adx.entity.json.response.mk.MkBid;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.entity.json.response.mk.MkImage;
import com.mk.adx.service.UcJsonService;
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
 * @Description UC
 * @Date 2021/11/25 13:48
 */
@Slf4j
@Service("ucJsonService")
public class UcJsonServiceImpl implements UcJsonService {

    private static final String name = "uc";
    private static final String source = " uc官方";
    /**
     * @Author mzs
     * @Description uc
     * @Date 2021/6/28 9:48
     */
    @SneakyThrows
    @Override
    public MkBidResponse getUcDataByJson(MkBidRequest request) {
        MkBidResponse bidResponse = new MkBidResponse();//总返回
        UcBidRequest bidRequest = new UcBidRequest();
        UcDevice device = new UcDevice();
        UcApp app = new UcApp();
        UcGps gps = new UcGps();
        UcPos pos = new UcPos();
        List<UcPos> ucPostList = new ArrayList();//post集合
        String os = request.getDevice().getOs();//终端操作系统类型:0=>Android,1=>iOS
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){
            device.setOs("android");
            app.setFr("android");
            device.setAndroid_id(request.getDevice().getAndroid_id());
            device.setOaid(request.getDevice().getOaid());
            device.setOaid_md5(request.getDevice().getOaid_md5());
            if(StringUtils.isNotEmpty(request.getDevice().getImei())){
                device.setDevid(request.getDevice().getImei());
                device.setImei(request.getDevice().getImei());
                device.setImei_md5(request.getDevice().getImei_md5());
            }else{
                device.setDevid(request.getDevice().getAndroid_id());
            }
            device.setOaid(request.getDevice().getOaid());
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)){
            device.setOs("ios");
            app.setFr("iphone");
            device.setAaid("CD7D878A870C-97D4-89AA-3EB3-D48AF066");
            device.setCaid("");
            device.setUdid("");
            device.setOpen_udid("");
            device.setIdfa(request.getDevice().getIdfa());
        }
        device.setDevice(request.getDevice().getModel());
        device.setOsv(request.getDevice().getOsv());
        device.setCpu("");
        device.setMac(request.getDevice().getMac());
        if (null!=request.getDevice().getW()){
            device.setSw(request.getDevice().getW().toString());
        }
        if (null!=request.getDevice().getH()){
            device.setSh(request.getDevice().getH().toString());
        }
        device.setIs_jb("0");

        Integer connectiontype = request.getDevice().getConnectiontype();
        if(2 == connectiontype){
             device.setAccess("Wi-Fi");
        }else if(4 == connectiontype){
             device.setAccess("2G");
        }else if(5 == connectiontype){
             device.setAccess("3G");
        }else if(6 == connectiontype){
             device.setAccess("4G");
        }else{
             device.setAccess("Unknown");
        }
        String carrier = request.getDevice().getCarrier();
        if(StringUtils.isNotEmpty(carrier)){
            if("70120".equals(carrier)){
                device.setCarrier("ChinaMobile");
            }else if("70123".equals(carrier)){
                device.setCarrier("ChinaUnicom");
            }else if("70121".equals(carrier)){
                device.setCarrier("ChinaTelecom");
            }else if("70124".equals(carrier)){
                device.setCarrier("ChinaTietong");
            }else{
                device.setCarrier("Unknown");
            }

        }
        device.setCp("na");
        device.setAid("");
        device.setClient_ip(request.getDevice().getIp());

        app.setPkg_name(request.getAdv().getBundle()); //包名
        app.setPkg_ver(request.getAdv().getVersion()); //版本号
        app.setApp_name(request.getAdv().getApp_name()); //app名称
        app.setUa(request.getDevice().getUa());//ua
        app.setIs_ssl("1");
        app.setApp_country("CN");
        gps.setGps_time(String.valueOf(System.currentTimeMillis()));
        if(null != request.getDevice().getGeo()){
            gps.setLng(String.valueOf(request.getDevice().getGeo().getLon()));
            gps.setLat(String.valueOf(request.getDevice().getGeo().getLat()));
        }
        pos.setSlot_type("0");
        pos.setSlot_id(request.getAdv().getTag_id());//截取id前7位去请求  Integer.valueOf(t_id.substring(0,7))
        pos.setReq_cnt("1");
        pos.setWid(request.getAdv().getApp_id());//应用id  Integer.valueOf(request.getAdv().getApp_id())
        ucPostList.add(pos);


        bidRequest.setAd_device_info(device);
        bidRequest.setAd_app_info(app);
        bidRequest.setAd_gps_info(gps);
        bidRequest.setAd_pos_info(ucPostList);

        //定以16位byte数组
        String content = JSONObject.toJSONString(bidRequest);

        log.info("请求UC广告参数"+JSONObject.parseObject(content));
        String url = "https://huichuan.sm.cn/nativead";
        String ua = request.getDevice().getUa();//ua
        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.douc(url,content,ua);//发请求
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段后，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        log.info("请求上游UC广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("UC广告返回参数"+JSONObject.parseObject(response));
        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        //code=0的时候成功返回，才做解析
        if (0 == jo.getInteger("code")){
            JSONArray imp = jo.getJSONArray("slot_ad");//所有⼴告位的返回⼴告列表
            List<MkBid> bidList = new ArrayList<>();
            for (int i = 0; i < imp.size(); i++) {
                MkBid tb = new MkBid();
                //图片内容处理
                JSONObject ad_content = imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONObject("ad_content");//⼴告内容填充
                List<MkImage> list = new ArrayList<>();
                MkImage tzImage = new MkImage();
                tzImage.setUrl(ad_content.getString("img_1"));//地址
                if (null!=ad_content.getString("img_1_w")){//宽
                    tzImage.setW(Integer.valueOf(ad_content.getString("img_1_w")));
                }else {
                    tzImage.setW(1280);
                }
                if (null!=ad_content.getString("img_1_h")){//高
                    tzImage.setH(Integer.valueOf(ad_content.getString("img_1_h")));
                }else {
                    tzImage.setH(720);
                }
                list.add(tzImage);
                tb.setImages(list);
                //上游没返回adtype，则用请求type判断广告类型
                if ("1".equals(request.getImp().get(i).getSlot_type()) || "2".equals(request.getImp().get(i).getSlot_type())) {//信息流或banner
                    tb.setAd_type(1);//原生-广告素材类型
                }else {
                    tb.setAd_type(2);//开屏-广告素材类型
                }
                tb.setTitle(imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONObject("ad_content").getString("title"));//标题
                tb.setDesc(imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONObject("ad_content").getString("description"));//描述
                tb.setAdid(imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getString("ad_id"));//⼴告id
                JSONArray turl = imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONArray("turl");//⼴告主⽬标url数组
                if (null!=turl){
                    tb.setClick_url(turl.get(0).toString());//点击地址
                    tb.setDownload_url(turl.get(0).toString());//下载地址
                }

                /**
                 * ⼴告点击的⾏为
                 * tab：新窗⼝打开
                 * download：下载
                 */
                String action = imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONObject("ad_action").getString("action");//
                if ("tab".equals(action)){
                    tb.setClicktype("1");
                }else if ("download".equals(action)){
                    tb.setClicktype("4");
                }


                String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();

                //曝光监测
                JSONArray vurl = imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONArray("vurl");//展现反馈url数组
                if(null != vurl){
                    List<String> check_views = new ArrayList<>();
                    check_views.add("http://adx.fxlxz.com/sl/pv?pv="+ Base64.encode(encode));
                    for (int cv = 0; cv < vurl.size(); cv++) {
                        check_views.add(vurl.get(cv).toString().replace("{TS}","%%TS%%"));//上游返回时间宏替换
                    }
                    tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                }

                //点击监测
                JSONArray curl = imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONArray("curl");//点击反馈url数组
                if(null != curl){
                    List<String> clickList = new ArrayList<>();
                    clickList.add("http://adx.fxlxz.com/sl/click?click="+ Base64.encode(encode));
                    for (int cc = 0; cc < curl.size(); cc++) {
                        String replace = curl.get(cc).toString().replace("{TS}","%%TS%%");
                        clickList.add(replace);
                    }
                    tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                }

                String up_source = imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getJSONObject("ad_content").getString("description");//上游来源
                tb.setSource(source+": "+up_source);
                bidList.add(tb);

                imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getString("style");//⼴告样式id
                imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getString("furl");//失败反馈url（参⻅反馈协议展现处理）
                imp.getJSONObject(i).getJSONArray("ad").getJSONObject(0).getString("eurl");//⽤于客户端反馈某些事件打点


            }



            bidResponse.setId(id);//请求id
            bidResponse.setSeatbid(bidList);//广告集合对象
            bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
            log.info("UC广告总返回"+JSONObject.toJSONString(bidResponse));
        }

        return bidResponse;
    }
}