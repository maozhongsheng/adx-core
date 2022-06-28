package com.mk.adx.service.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.zhimeng.ZhimengBidRequest;
import com.mk.adx.entity.json.request.zhimeng.ZhimengDeviceInfo;
import com.mk.adx.entity.json.request.zhimeng.ZhimengImpInfo;
import com.mk.adx.entity.json.response.mk.MkBid;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.entity.json.response.mk.MkImage;
import com.mk.adx.service.ZhimengJsonService;
import com.mk.adx.util.AES;
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
 * @Description 知盟(知乎)
 * @Date 2022/06/27 9:48
 */
@Slf4j
@Service("zhimengJsonService")
public class ZhimengJsonServiceImpl implements ZhimengJsonService {

    private static final String name = "zhimeng";

    private static final String source = "知盟";

    @SneakyThrows
    @Override
    public MkBidResponse getZhimengDataByJson(MkBidRequest request) {
        ZhimengBidRequest zmb = new ZhimengBidRequest();//知盟总请求
        if("ab6fa82a".equals(request.getAdv().getTag_id())){
            zmb.setProtocol_version("1.1.1");//协议版本号
        }else{
            zmb.setProtocol_version("1.0.0");//协议版本号
        }
        String channel = request.getAdv().getApp_id();//渠道ID
        zmb.setChannel(channel);//渠道ID（可在平台媒体管理中查看渠道ID）
        zmb.setRequest_id(request.getId());//请求ID

        /**
         * 位置信息
         */
        List<ZhimengImpInfo> listImp = new ArrayList<>();
        ZhimengImpInfo zmi = new ZhimengImpInfo();
//        zmi.setPos_id("119401");
        zmi.setPos_id(request.getAdv().getTag_id());//展示位置ID（可在平台媒体管理中查看内容位ID）
        zmi.setApp_name(request.getAdv().getApp_name());//app名称
        zmi.setPackage_name(request.getAdv().getBundle());//app包名
        listImp.add(zmi);
        zmb.setImp_info(listImp);

        /**
         * 设备信息
         */
        ZhimengDeviceInfo zmd = new ZhimengDeviceInfo();
        //设备类型
        if ("phone".equals(request.getDevice().getDevicetype())){
            zmd.setType(2);//手机
        }else if ("ipad".equals(request.getDevice().getDevicetype())){
            zmd.setType(3);//平板
        }else if ("pc".equals(request.getDevice().getDevicetype())){
            zmd.setType(1);//pc
        }else {
            zmd.setType(0);//未知
        }

        //网络连接类型
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            zmd.setNetwork(0);
        }else if("2".equals(connectiontype)){
            zmd.setNetwork(1);
        }else if("4".equals(connectiontype)){
            zmd.setNetwork(2);
        }else if("5".equals(connectiontype)){
            zmd.setNetwork(3);
        }else if("6".equals(connectiontype)){
            zmd.setNetwork(4);
        }else if("7".equals(connectiontype)){
            zmd.setNetwork(5);
        } else {
            zmd.setNetwork(0);
        }

        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            zmd.setOs(2);//安卓
            zmd.setImei(request.getDevice().getImei());//国际移动设备识别码原始值,32 位小写的MD5 值,原始值与md5 值二选一,必传
            zmd.setOaid(request.getDevice().getOaid());//匿名设备标识符,安卓10 无imei,用oaid32 位小写的MD5 值,原始值与md5 值二选一,必传
            zmd.setAndroid_id(request.getDevice().getAndroid_id());//Android ID 手机唯一标识
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            zmd.setOs(1);//ios
            zmd.setIdfa(request.getDevice().getIdfa());//iOS设备idfa，与idfa_md5至少填一个
        }

        //系统版本号
        if (StringUtils.isNotEmpty(request.getDevice().getOsv())){
            zmd.setOs_version(request.getDevice().getOsv());
        }else {
            zmd.setOs_version("4.3.3");
        }
        zmd.setBrand(request.getDevice().getMake());//设备品牌
        zmd.setModel(request.getDevice().getModel());//设备型号
        zmd.setIp(request.getDevice().getIp());//IP地址
        zmd.setUa(request.getDevice().getUa());//User-Agent
        //运营商类型
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                zmd.setOperator(1);//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                zmd.setOperator(3);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                zmd.setOperator(2);//中国电信
            }else{
                zmd.setOperator(0);//其他
            }
        }else{
            zmd.setOperator(0);
        }
        zmd.setMac(request.getDevice().getMac());//设备MAC地址
        zmb.setDevice_info(zmd);//设备信息

        /**
         * 密文
         */
        String url = request.getAdv().getVersion();
        if("ab6fa82a".equals(request.getAdv().getTag_id())) {
            JsonObject data = new JsonObject();//创建Json的加密对象
            data.addProperty("channel", channel);
            data.addProperty("domain", "zhihu");
            data.addProperty("timestamp", String.valueOf(System.currentTimeMillis()));
            System.out.println("原始数据:" + data.toString());

            String plaintext = URLEncoder.encode(data.toString(), "UTF-8");

            String rstData = AESUtil.pkcs7padding(plaintext);//进行PKCS7Padding填充

            //不同的app的key和url不一样
            String key = request.getAdv().getSize();
            //加密
            AES aes = new AES();
            byte[] cfb = aes.encrypt(rstData, key, "CFB");//进行java的AES的CFB加密
            byte[] iv = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            String ciphertext = Base64.encode(cfb.toString() + iv.toString());//终极密文
            zmb.setCiphertext(ciphertext);
        }
        //总返回
        MkBidResponse bidResponse = new MkBidResponse();
        String content = JSONObject.toJSONString(zmb);
        log.info(request.getImp().get(0).getTagid() + ":请求知盟广告参数"+JSONObject.parseObject(content));
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
        log.info(request.getImp().get(0).getTagid() + ":请求上游知盟广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info(request.getImp().get(0).getTagid() + ":知盟广告返回参数"+JSONObject.parseObject(response));

        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);
        if (null!=jo){
            //code=0的时候成功返回，才做解析
            if (1 == jo.getInteger("code")){
//            String bidid = jo.getString("pos_id");//请求时候的广告位id
                JSONArray imp = jo.getJSONArray("content_info");//内容信息
                if (null!=imp){
                    List<MkBid> bidList = new ArrayList<>();
                    for (int i = 0; i < imp.size(); i++) {
                        MkBid tb = new MkBid();
                        tb.setTitle(imp.getJSONObject(i).getString("title"));//标题
                        tb.setDesc(imp.getJSONObject(i).getString("description"));//描述
                        tb.setDeeplink_url(imp.getJSONObject(i).getString("deep_link"));//深度链接
                        tb.setClick_url(imp.getJSONObject(i).getString("landing_url"));//内容落地页地址
                        if (null!=imp.getJSONObject(i).getInteger("operation_type")){
                            int operation_type = imp.getJSONObject(i).getInteger("operation_type");//1=app下载 2=H5广告操作类型
                            if(1==operation_type){
                                tb.setClicktype("4");
                            }else if (2==operation_type){
                                tb.setClicktype("1");
                            }
                        }else {
                            tb.setClicktype("0");
                        }

                        //图片内容处理
                        JSONArray imgs = imp.getJSONObject(i).getJSONArray("img");//图片集合
                        List<MkImage> list = new ArrayList<>();
                        for (int j = 0; j < imgs.size(); j++){
                            MkImage mkImage = new MkImage();
                            mkImage.setUrl(imgs.getJSONObject(i).getString("url"));//地址
                            if (null!=imgs.getJSONObject(i).getInteger("width")){//宽
                                mkImage.setW(imgs.getJSONObject(i).getInteger("width"));
                            }else {
                                mkImage.setW(1280);
                            }
                            if (null!=imgs.getJSONObject(i).getInteger("height")){//高
                                mkImage.setH(imgs.getJSONObject(i).getInteger("height"));
                            }else {
                                mkImage.setH(720);
                            }
                            list.add(mkImage);
                        }
                        //上游没返回adtype，则用请求type判断广告类型
                        if ("1".equals(request.getImp().get(i).getSlot_type()) || "2".equals(request.getImp().get(i).getSlot_type())) {//信息流或banner
                            tb.setAd_type(8);//原生-广告素材类型
                        }else {
                            tb.setAd_type(5);//开屏-广告素材类型
                        }
                        tb.setImages(list);

                        //曝光监测
                        if(null != imp.getJSONObject(i).getJSONObject("tracker").getJSONArray("imps")){
                            List<String> check_views = new ArrayList<>();
                            String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            check_views.add("http://adx.fxlxz.com/sl/pv?pv=" + Base64.encode(encode));
                            JSONArray urls1 = imp.getJSONObject(i).getJSONObject("tracker").getJSONArray("imps");
                            for (int cv = 0; cv < urls1.size(); cv++) {
                                check_views.add(urls1.get(cv).toString());
                            }
                            tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测

                        }

                        //点击监测
                        if(null != imp.getJSONObject(i).getJSONObject("tracker").getJSONArray("clicks")){
                            List<String> clickList = new ArrayList<>();
                            JSONArray urls1 =  imp.getJSONObject(i).getJSONObject("tracker").getJSONArray("clicks");
                            String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getMkKafka().getPublish_id() + "," + request.getMkKafka().getMedia_id() + "," + request.getMkKafka().getPos_id() + "," + request.getMkKafka().getSlot_type() + "," + request.getMkKafka().getDsp_id() + "," + request.getMkKafka().getDsp_media_id() + "," + request.getMkKafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                            clickList.add("http://adx.fxlxz.com/sl/click?click=" + Base64.encode(encode));
                            for (int cc = 0; cc < urls1.size(); cc++) {
                                String replace = urls1.get(cc).toString().replace("__WIDTH__", "%%WIDTH%%").replace("__HEIGHT__","%%HEIGHT%%").replace("__DOWN_X__","%%DOWN_X%%").replace("__DOWN_Y__","%%DOWN_Y%%").replace("__UP_X__","%%UP_X%%").replace("__UP_Y__","%%UP_Y%%");
                                clickList.add(replace);
                            }
                            tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测

                        }
                        tb.setSource(source+": "+imp.getJSONObject(i).getString("adsource"));
                        bidList.add(tb);
                    }

                    bidResponse.setId(id);//请求id
                    bidResponse.setBidid(id);//广告主返回id
                    bidResponse.setSeatbid(bidList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info(request.getImp().get(0).getTagid() + ":知盟广告总返回"+JSONObject.toJSONString(bidResponse));
                }

            }
        }

        return bidResponse;
    }
}
