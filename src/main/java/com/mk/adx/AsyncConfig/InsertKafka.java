package com.mk.adx.AsyncConfig;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.client.SspClient;
import com.mk.adx.config.TopicConfig;
import com.mk.adx.entity.json.request.SspMediaRequestToKafka;
import com.mk.adx.entity.json.request.mk.MkAdv;
import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.request.mk.MkKafka;
import com.mk.adx.entity.json.response.SspMediaResponseToKafka;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.entity.json.response.mk.MkImage;
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
 * @author mzs
 * @version 1.0
 * @date 2022/06/22 18:20
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
    public MkBidRequest insertKafka(MkAdv mkAdv, MkBidRequest request){
        //所查信息存入请求实体和请求kafka中
        SspMediaRequestToKafka requestToKafka = new SspMediaRequestToKafka();
        MkKafka mkKafka = new MkKafka();
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
        requestToKafka.setPos_id(tagid);//广告位id
        if("0".equals(request.getDevice().getOs()) || "android".equals(request.getDevice().getOs()) || "ANDROID".equals(request.getDevice().getOs())){
            requestToKafka.setSystem_type("0");//系统类型
        }else if ("1".equals(request.getDevice().getOs()) || "ios".equals(request.getDevice().getOs()) || "IOS".equals(request.getDevice().getOs())){
            requestToKafka.setSystem_type("1");//系统类型
        }
        requestToKafka.setSlot_type(request.getImp().get(0).getSlot_type());//广告位类型:1,信息流 2,banner 3,开屏 4,视频 5,横幅 6,插屏 7,暂停 8,贴片
        if (null!=request.getApi_version()){
            requestToKafka.setVersion(request.getApi_version());//版本号
        }else {
            requestToKafka.setVersion("");
        }
        requestToKafka.setBid_floor(0);
        requestToKafka.setDsp_id(mkAdv.getDsp_id());//dspid
        requestToKafka.setDsp_pos_id(mkAdv.getTag_id());//dsp广告位id
        requestToKafka.setDsp_media_id(mkAdv.getApp_id());//dsp媒体id
        requestToKafka.setRequest_id(request.getId());//竞价请求id
        requestToKafka.setDate(date);//日期
        requestToKafka.setDate_hour(dfh.format(date));//小时
        requestToKafka.setVersion(request.getApi_version());//系统版本号
        requestToKafka.setReq(1);
        kafkaTemplate.send(TopicConfig.SSP_MEDIA_REQUEST,JSONObject.toJSON(requestToKafka).toString());//下游媒体请求存入相应topic
        log.info("+++kafka请求json数据==="+ JSONObject.toJSONString(requestToKafka));
        //存入数据到kafka中
        mkKafka.setMedia_id(media_id);
        mkKafka.setPos_id(tagid);
        mkKafka.setDsp_id(mkAdv.getDsp_id());
        mkKafka.setDsp_media_id(mkAdv.getApp_id());
        mkKafka.setDsp_pos_id(mkAdv.getTag_id());
        mkKafka.setPublish_id(publish);
        mkKafka.setSlot_type(request.getImp().get(0).getSlot_type());
        request.setMkKafka(mkKafka);

        return request;
    }


    /**
     * 5、返回参数写入kafka
     * @param bidResponse
     * @param request
     * @return
     */
    public SspMediaResponseToKafka ckDataByJson(MkBidResponse bidResponse, MkBidRequest request, Long startTime) {
        SspMediaResponseToKafka responseToKafka = new SspMediaResponseToKafka();
        responseToKafka.setRequest_id(bidResponse.getId());//请求id

        if (null!=bidResponse.getSeatbid()){
            for (int i=0;i<bidResponse.getSeatbid().size();i++) {
             responseToKafka.setPrice(request.getAdv().getPrice());
             responseToKafka.setAdx_price(0);//联盟竞价，单位分
             responseToKafka.setBid_price(0);//媒体竞价，单位分
             responseToKafka.setAgent_id("");//需要代理商ID
             if (null != bidResponse.getSeatbid().get(i).getCheck_views()) {
                 List<MkImage> images = new ArrayList<>();

                 images = bidResponse.getSeatbid().get(i).getImages();//信息流素材图片集合
                 responseToKafka.setDesc(bidResponse.getSeatbid().get(i).getDesc());//描述
                 responseToKafka.setTitle(bidResponse.getSeatbid().get(i).getTitle());

                 String dsp_name = bidResponse.getSeatbid().get(i).getSource();//来源名称
                 responseToKafka.setDsp_name(dsp_name);//来源名称
                 responseToKafka.setClick_type(bidResponse.getSeatbid().get(i).getClicktype());//点击类型
                 if(null != images){
                     responseToKafka.setImages(images);//素材集合
                     responseToKafka.setBid_count(images.size());//素材个数
                 }
                 if (null!=bidResponse.getSeatbid().get(i).getApp()){
                     responseToKafka.setBundle(bidResponse.getSeatbid().get(i).getApp().getBundle());//包名
                 }else {
                     responseToKafka.setBundle("");
                 }
                 responseToKafka.setTimestamp(System.currentTimeMillis());//时间戳
             }


            }
        }
        responseToKafka.setPos_id(request.getMkKafka().getPos_id()); //广告位id
        responseToKafka.setDsp_id(request.getMkKafka().getDsp_id());
        responseToKafka.setPublish_id(request.getMkKafka().getPublish_id());
        responseToKafka.setDsp_media_id(request.getMkKafka().getDsp_media_id());
        responseToKafka.setDsp_pos_id(request.getMkKafka().getDsp_pos_id());
        responseToKafka.setMedia_id(request.getMkKafka().getMedia_id());
        responseToKafka.setSlot_type(request.getMkKafka().getSlot_type());
        responseToKafka.setFill(1);

        //上游返回数据时长
        responseToKafka.setAd_cost_time(bidResponse.getProcess_time_ms().intValue());

        //统计总消耗时长
        Long endTime = System.currentTimeMillis();
        Long tempTime = (endTime - startTime);
        responseToKafka.setCost_time(tempTime.intValue());//消耗时长

        kafkaTemplate.send(TopicConfig.SSP_MEDIA_RESPONSE,JSONObject.toJSON(responseToKafka).toString());//上游媒体返回数据存入相应topic

        log.info("+++kafka返回json数据==="+ JSONObject.toJSONString(responseToKafka));
        log.info(request.getImp().get(0).getTagid()+"-总消耗时间：" + (((tempTime / 86400000) > 0) ? ((tempTime / 86400000) + "d") : "") + ((((tempTime / 86400000) > 0) || ((tempTime % 86400000 / 3600000) > 0)) ? ((tempTime % 86400000 / 3600000) + "h") : ("")) + ((((tempTime / 3600000) > 0) || ((tempTime % 3600000 / 60000) > 0)) ? ((tempTime % 3600000 / 60000) + "m") : ("")) + ((((tempTime / 60000) > 0) || ((tempTime % 60000 / 1000) > 0)) ? ((tempTime % 60000 / 1000) + "s") : ("")) + ((tempTime % 1000) + "ms"));

        return responseToKafka;
    }


}
