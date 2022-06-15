package com.mk.adx.AsyncConfig;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.client.SspClient;
import com.mk.adx.entity.json.request.SspMediaRequestToKafka;
import com.mk.adx.entity.json.request.tz.TzAdv;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.tz.TzKafka;
import com.mk.adx.entity.json.response.SspMediaResponseToKafka;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.entity.json.response.tz.TzImage;
import com.mk.adx.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 2、请求参数和返回参数，插入kafka数据
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/11 18:20
 */
@Slf4j
@Configuration
public class InsertKafka {

    @Autowired
    private SspClient sspClient;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Resource
    private RedisUtil redisUtil;
    /**
     * 请求数据放入kafka的相关信息
     * @param request
     * @return
     */
    public TzBidRequest insertKafka(TzAdv tzAdv, TzBidRequest request){
        //所查信息存入请求实体和请求kafka中
        SspMediaRequestToKafka requestToKafka = new SspMediaRequestToKafka();
        TzKafka tzKafka = new TzKafka();
        Date date = new Date();
        SimpleDateFormat dfh = new SimpleDateFormat("HH");

        String tagid = request.getImp().get(0).getTagid();
        String media_id = "";
        String publish = "";
        Object redismedia_id = redisUtil.get("adx_selectmediaIdBytagId_" + tagid);
        if(null != redismedia_id){
            media_id = redismedia_id.toString();
            publish = redisUtil.get("adx_selectpublishIdBymediaId_" + media_id).toString();
        }else{
            media_id = sspClient.getmediaId(tagid);//根据广告位id查询媒体id
            publish = sspClient.getagentId(media_id);
            redisUtil.set("adx_selectmediaIdBytagId_"+tagid,media_id);
            redisUtil.set("adx_selectpublishIdBymediaId_"+media_id,publish);
        }
        requestToKafka.setPublish_id(publish);//代理商id（暂时不需要）
        requestToKafka.setMedia_id(media_id);//媒体id
        requestToKafka.setAdx_id("");//ssp账户id
        requestToKafka.setPos_id(tagid);//广告位id
        requestToKafka.setAd_id(request.getImp().get(0).getId());//曝光id
        requestToKafka.setOem_id("");//omeid
        requestToKafka.setToken("");//token
        requestToKafka.setMedia_type("");//媒体类型
        requestToKafka.setSettle_type("");//结算类型
        requestToKafka.setPlatform("");//平台类型
        if("0".equals(request.getDevice().getOs()) || "android".equals(request.getDevice().getOs()) || "ANDROID".equals(request.getDevice().getOs())){
            requestToKafka.setSystem_type("0");//系统类型
        }else if ("1".equals(request.getDevice().getOs()) || "ios".equals(request.getDevice().getOs()) || "IOS".equals(request.getDevice().getOs())){
            requestToKafka.setSystem_type("1");//系统类型
        }
        requestToKafka.setSlot_type(request.getImp().get(0).getAd_slot_type());//广告位类型:1,信息流 2,banner 3,开屏 4,视频 5,横幅 6,插屏 7,暂停 8,贴片
        requestToKafka.setSlot_tag("");//广告位分类
        requestToKafka.setMedia_tag("");//媒体分类
        if (null!=request.getMedia_version()){
            requestToKafka.setVersion(request.getMedia_version());//版本号
        }else {
            requestToKafka.setVersion("");
        }
        requestToKafka.setPlant_id("");//分发计划id
        if (null != request.getImp().get(0).getBidfloor()) {
            requestToKafka.setBid_floor(request.getImp().get(0).getBidfloor().intValue());//广告位底价，单位是：分/CPM
        }else {
            requestToKafka.setBid_floor(0);
        }
        requestToKafka.setDsp_id(tzAdv.getDsp_id());//dspid
        requestToKafka.setDsp_pos_id(tzAdv.getTag_id());//dsp广告位id
        requestToKafka.setDsp_media_id(tzAdv.getApp_id());//dsp媒体id
        requestToKafka.setRequest_id(request.getId());//竞价请求id
        requestToKafka.setDate(date);//日期
        requestToKafka.setDate_hour(dfh.format(date));//小时
        requestToKafka.setVersion(request.getMedia_version());//系统版本号
        requestToKafka.setReq(1);
       // kafkaTemplate.send(TopicConfig.SSP_MEDIA_REQUEST,JSONObject.toJSON(requestToKafka).toString());//下游媒体请求存入相应topic
        log.info("+++kafka请求json数据==="+ JSONObject.toJSONString(requestToKafka));
        //存入数据到kafka中
        tzKafka.setMedia_id(media_id);
        tzKafka.setPos_id(tagid);
        tzKafka.setDsp_id(tzAdv.getDsp_id());
        tzKafka.setDsp_media_id(tzAdv.getApp_id());
        tzKafka.setDsp_pos_id(tzAdv.getTag_id());
        tzKafka.setPublish_id(publish);
        tzKafka.setSlot_type(request.getImp().get(0).getAd_slot_type());
        request.setTzkafka(tzKafka);

        return request;
    }


    /**
     * 5、返回参数写入kafka
     * @param bidResponse
     * @param request
     * @return
     */
    public SspMediaResponseToKafka ckDataByJson(TzBidResponse bidResponse, TzBidRequest request, Long startTime) {
        SspMediaResponseToKafka responseToKafka = new SspMediaResponseToKafka();
        responseToKafka.setRequest_id(bidResponse.getId());//请求id

        if (null!=bidResponse.getSeatbid()){
            for (int i=0;i<bidResponse.getSeatbid().size();i++) {
                if (null != bidResponse.getSeatbid().get(i).getBid()) {
                    for (int j=0;j<bidResponse.getSeatbid().get(i).getBid().size();j++){
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_start_downloads()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_start_downloads().get(0)));//下载开始
//                        }else {
//                            responseToKafka.setDownload_start(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_end_downloads()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_end_downloads().get(0)));//下载结束
//                        }else {
//                            responseToKafka.setDownload_end(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_start_installs()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_start_installs().get(0)));//安装开始
//                        }else {
//                            responseToKafka.setInstall_start(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_end_installs()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_end_installs().get(0)));//安装结束
//                        }else {
//                            responseToKafka.setInstall_end(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_activations()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_activations().get(0)));//激活
//                        }else {
//                            responseToKafka.setActivation(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_success_deeplinks()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_success_deeplinks().get(0)));//唤醒成功
//                        }else {
//                            responseToKafka.setDeeplink(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_fail_deeplinks()) {
//                            responseToKafka.setDownload_start(Integer.parseInt(bidResponse.getSeatbid().get(i).getBid().get(j).getCheck_fail_deeplinks().get(0)));//唤醒失败
//                        }else {
//                            responseToKafka.setIdeeplink(0);
//                        }

//                        if(id.equals("1000000092")){
//                            responseToKafka.setPrice(18);//消耗，单位元   快友
//                        }else if(id.equals("1000000099")){
//                            responseToKafka.setPrice(2);//消耗，单位元    有道
//                        }else if(id.equals("1000000105")){
//                            responseToKafka.setPrice(3);//消耗，单位元   一点咨询
//                        }else if(id.equals("1000000113")){
//                            responseToKafka.setPrice(8);//消耗，单位元   一点咨询 (爆米花)
//                        }else if(id.equals("1000000112")){
//                            responseToKafka.setPrice(6);//消耗，单位元   一点咨询 (爆米花)
//                        }else if(id.equals("1000000117")){
//                            responseToKafka.setPrice(15);//消耗，单位元  点点记账-开屏
//                        }else if(tid.equals("1000000244") && id.equals("1000000114")){
//                            responseToKafka.setPrice(12);//消耗，单位元 两步路-安卓-开屏
//                        }else if(tid.equals("1000000245") && id.equals("1000000114")){
//                            responseToKafka.setPrice(4);//消耗，单位元  两步路-安卓-信息流
//                        }else {
//
//                        }
                        responseToKafka.setPrice(request.getAdv().getPrice());

//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getVideo().getDuration()) {
//                            responseToKafka.setVideo_start(bidResponse.getSeatbid().get(i).getBid().get(j).getVideo().getDuration());//视频时长
//                        }else {
//                            responseToKafka.setVideo_start(0);
//                        }
//                        if (null != bidResponse.getSeatbid().get(i).getBid().get(j).getVideo().getDuration()) {
//                            responseToKafka.setVideo_end(bidResponse.getSeatbid().get(i).getBid().get(j).getVideo().getDuration());//视频时长
//                        }else {
//                            responseToKafka.setVideo_end(0);
//                        }
                        responseToKafka.setAdx_price(0);//联盟竞价，单位分
                        responseToKafka.setBid_price(0);//媒体竞价，单位分
//                        responseToKafka.setDau(0);//日活
                        responseToKafka.setAgent_id("");//需要代理商ID

                        if (null != bidResponse.getSeatbid().get(i).getBid()) {
                            List<TzImage> images = new ArrayList<>();
                            Integer at = bidResponse.getSeatbid().get(i).getBid().get(j).getAd_type();//素材类型
                            if (null!=at){
                                if (at == 8 || at == 9){
                                    images = bidResponse.getSeatbid().get(i).getBid().get(j).getNATIVE().getImages();//信息流素材图片集合
                                    if(null!=bidResponse.getSeatbid().get(i).getBid().get(j).getNATIVE().getDesc()){
                                        responseToKafka.setDesc(bidResponse.getSeatbid().get(i).getBid().get(j).getNATIVE().getDesc());//描述
                                    }else {
                                        responseToKafka.setDesc("");
                                    }
                                }else {
                                    images = bidResponse.getSeatbid().get(i).getBid().get(j).getImages();//其它素材图片集合
                                    if (null!=bidResponse.getSeatbid().get(i).getBid().get(j).getDesc()){
                                        responseToKafka.setDesc(bidResponse.getSeatbid().get(i).getBid().get(j).getDesc());//描述
                                    }else {
                                        responseToKafka.setDesc("");
                                    }
                                }
                            }
                            if (null!=bidResponse.getSeatbid().get(i).getBid().get(j).getId()){
                                responseToKafka.setDeal_id(bidResponse.getSeatbid().get(i).getBid().get(j).getId());//交易ID，pd流量合同号
                            }else {
                                responseToKafka.setDeal_id("");//交易ID，pd流量合同号
                            }
                            if (null!=bidResponse.getSeatbid().get(i).getBid().get(j).getAdid()){
                                responseToKafka.setAd_id(bidResponse.getSeatbid().get(i).getBid().get(j).getAdid());//广告id
                            }else {
                                responseToKafka.setAd_id("");
                            }
                            if (null!=bidResponse.getSeatbid().get(i).getBid().get(j).getTitle()){
                                responseToKafka.setTitle(bidResponse.getSeatbid().get(i).getBid().get(j).getTitle());//标题
                            }else if(null != bidResponse.getSeatbid().get(i).getBid().get(j).getNATIVE()){
                                if(null != bidResponse.getSeatbid().get(i).getBid().get(j).getNATIVE().getTitle()){
                                    responseToKafka.setTitle(bidResponse.getSeatbid().get(i).getBid().get(j).getNATIVE().getTitle());
                                }
                            }else{
                                responseToKafka.setTitle("");
                            }

                            String dsp_name = bidResponse.getSeatbid().get(i).getBid().get(j).getSource();//来源名称
                            responseToKafka.setDsp_name(dsp_name);//来源名称
                            /**
                             * 根据dspname处理dspid
                             */
//                            if (dsp_name.equals("yd")){
//                                responseToKafka.setDsp_id(2021000008);
//                            }else if (dsp_name.equals("ydzx")){
//                                responseToKafka.setDsp_id(2021000009);
//                            }else if (dsp_name.equals("sd")){
//                                responseToKafka.setDsp_id(2021000010);
//                            }else if (dsp_name.equals("ky")){
//                                responseToKafka.setDsp_id(2021000007);
//                            }else if (dsp_name.equals("myc")){
//                                responseToKafka.setDsp_id(2021000006);
//                            } else if (dsp_name.equals("baidu")){
//                                responseToKafka.setDsp_id(2021000014);
//                            }
                            responseToKafka.setClick_type(bidResponse.getSeatbid().get(i).getBid().get(j).getClicktype());//点击类型
                            if(null != images){
                                responseToKafka.setImages(images);//素材集合
                                responseToKafka.setBid_count(images.size());//素材个数
                            }
                            if (null!=bidResponse.getSeatbid().get(i).getBid().get(j).getApp()){
                                responseToKafka.setBundle(bidResponse.getSeatbid().get(i).getBid().get(j).getApp().getBundle());//包名
                            }else {
                                responseToKafka.setBundle("");
                            }
                            responseToKafka.setTimestamp(System.currentTimeMillis());//时间戳
                        }
                    }
                }
            }
        }
        responseToKafka.setPos_id(request.getTzkafka().getPos_id()); //广告位id
        responseToKafka.setDsp_id(request.getTzkafka().getDsp_id());
        responseToKafka.setPublish_id(request.getTzkafka().getPublish_id());
        responseToKafka.setDsp_media_id(request.getTzkafka().getDsp_media_id());
        responseToKafka.setDsp_pos_id(request.getTzkafka().getDsp_pos_id());
        responseToKafka.setMedia_id(request.getTzkafka().getMedia_id());
        responseToKafka.setSlot_type(request.getTzkafka().getSlot_type());
        responseToKafka.setFill(1);

        //上游返回数据时长
        responseToKafka.setAd_cost_time(bidResponse.getProcess_time_ms().intValue());

        //统计总消耗时长
        Long endTime = System.currentTimeMillis();
        Long tempTime = (endTime - startTime);
        responseToKafka.setCost_time(tempTime.intValue());//消耗时长

      //  kafkaTemplate.send(TopicConfig.SSP_MEDIA_RESPONSE,JSONObject.toJSON(responseToKafka).toString());//上游媒体返回数据存入相应topic

        log.info("+++kafka返回json数据==="+ JSONObject.toJSONString(responseToKafka));
        log.info(request.getImp().get(0).getTagid()+"-总消耗时间：" + (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") + ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) + ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) + ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) + ((tempTime % 1000) + "ms"));

        return responseToKafka;
    }


}
