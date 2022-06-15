package com.mk.adx.controller;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.config.TopicConfig;
import com.mk.adx.entity.json.response.ResponseFeedBack;
import com.mk.adx.util.AESUtil;
import com.mk.adx.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 重定向请求
 *
 * @author yjn
 * @version 1.0
 * @date 2021/4/27 19:07
 */
@Controller
@Slf4j
@ResponseBody
@RequestMapping(value = "/api/feedback")
public class FeedbackController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static int ExpireTime = 6000;   // redis中存储的过期时间60s


    /**
     * @Author yjn
     * @Description ssp-feedback-price 竞价监测
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/winPrice" , method = RequestMethod.GET)
    public ResponseFeedBack price(@RequestParam String price, @RequestParam String winNotice, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(price).split(",");
        String priceUrl = split[0];
        String requestId = split[1];
        String pos_id = split[2];
        String ip = split[3];
        String name = split[4];
        String appName = split[5];
        String gbk = URLDecoder.decode(winNotice, "GBK");
        String base64 = Base64.decode(gbk);
        String decrypt = AESUtil.decrypt(base64);
        String key = "ssp-feedback-price-"+requestId;
//        redisUtil.set(key,priceUrl,ExpireTime);//写入redis
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pos_id", pos_id);
        map.put("requestId", requestId);
        map.put("priceUrl", priceUrl);
        map.put("winNotice", winNotice);
        map.put("ip", ip);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_WIN, json.toString());//下游媒体请求存入相应topic
        response.sendRedirect(priceUrl);//重定向到上游的pvurl
        log.info(appName + "应用----：" + name + "竞价链接" + priceUrl + "," + requestId + "," + ip + "," + time + "竞价成功价格：" + decrypt);
        return new ResponseFeedBack();
    }


    /**
     * @Author yjn
     * @Description ssp-feedback-pv 曝光监测
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/pv" , method = RequestMethod.GET)
    public ResponseFeedBack pv(@RequestParam String pv, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        log.info("加密："+pv);
        String[] split = Base64.decode(pv).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("pv_price", split[1]);
        map.put("ip", split[2]);
        map.put("pv_ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("pv", 1);
      //  map.put("pv_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_PV, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[8]  + "曝光ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }
    /**
     * @Author yjn
     * @Description ssp-feedback-click 点击监测
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/checkClicks" , method = RequestMethod.GET)
    public ResponseFeedBack clickViews(@RequestParam String checkClicks, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        log.info("加密："+checkClicks);
        String[] split = Base64.decode(checkClicks).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("click_price", split[1]);
        map.put("ip", split[2]);
        map.put("cl_ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("click", 1);
     //   map.put("click_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_CLICK, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[8] + "点击监测ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-vedio_start
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/vedio/start" , method = RequestMethod.GET)
    public ResponseFeedBack vedioStart(@RequestParam String vedioStart, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(vedioStart).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("vedio_start_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("video_start", 1);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_VEDIO_START, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "视频开始ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-vedio_end
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/vedio/end" , method = RequestMethod.GET)
    public ResponseFeedBack vedioEnd(@RequestParam String vedioEnd, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(vedioEnd).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("vedio_end_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("video_end", 1);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_VEDIO_END, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "视频结束ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-download_start
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/download/start" , method = RequestMethod.GET)
    public ResponseFeedBack downloadStart(@RequestParam String downloadStart, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(downloadStart).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("download_start_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("download_start", 1);
      //  map.put("download_start_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_DOWNLOAD_START, json.toString());//下游媒体请求存入相应topic
       // response.sendRedirect(download);//重定向到上游的下载开始
        log.info("应用----：" + split[10] + "下载开始ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-download_end
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/download/end" , method = RequestMethod.GET)
    public ResponseFeedBack downloadEnd(@RequestParam String downloadEnd, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(downloadEnd).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("download_end_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("download_end", 1);
     //   map.put("download_end_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_DOWNLOAD_END, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "下载结束ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-install_start
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/install/start" , method = RequestMethod.GET)
    public ResponseFeedBack installStart(@RequestParam String installStart, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(installStart).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("install_start_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("install_start", 1);
      //  map.put("install_start_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_INSTALL_START, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "安装开始ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description  ssp-feedback-install_end
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/install/end" , method = RequestMethod.GET)
    public ResponseFeedBack installEnd(@RequestParam String installEnd, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(installEnd).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("install_end_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("install_end", 1);
     //   map.put("install_end_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_INSTALL_END, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "安装结束ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-activation
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/activation" , method = RequestMethod.GET)
    public ResponseFeedBack activation(@RequestParam String activation, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(activation).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("activation_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("activation", 1);
       // map.put("activation_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_ACTIVATION, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "激活时间ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-deeplink
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/deeplink" , method = RequestMethod.GET)
    public ResponseFeedBack deeplink(@RequestParam String deeplink, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(deeplink).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("deeplink_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("deeplink", 1);
     //   map.put("deeplink_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_DEEPLINK, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "唤醒ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description  ssp-feedback-ideeplink
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/ideeplink" , method = RequestMethod.GET)
    public ResponseFeedBack ideeplink(@RequestParam String ideeplink, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(ideeplink).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("ideeplink_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("ideeplink", 1);
      //  map.put("ideeplink_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_IDEEPLINK, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[10] + "唤醒失败ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }


// --------------------------------------- 测试数据 ---------------------------------------
    /**
     * @Author yjn
     * @Description ssp-feedback-price 竞价监测
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/winPrice/test" , method = RequestMethod.GET)
    public ResponseFeedBack priceTest(@RequestParam String price, @RequestParam String winNotice, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        String decode = Base64.decode(price);
        String[] split = decode.split(",");
        String priceUrl = split[0];
        String requestId = split[1];
        String ip = split[2];
        String name = split[3];
        String appName = split[4];
        String gbk = URLDecoder.decode(winNotice, "GBK");
        String base64 = Base64.decode(gbk);
        String decrypt = AESUtil.decrypt(base64);
        String key = "ssp-feedback-price-test"+requestId;
//        redisUtil.set(key,priceUrl,ExpireTime);//写入redis
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", requestId);
        map.put("priceUrl", priceUrl);
        map.put("winNotice", winNotice);
        map.put("ip", ip);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_WIN_TEST, json.toString());//下游媒体请求存入相应topic
        // response.sendRedirect(priceUrl);//重定向到上游的pvurl
        log.info("测试监测链接" + appName + "应用----：" + name + "竞价链接" + priceUrl + "," + requestId + "," + ip + "," + time + "竞价成功价格：" + decrypt);
        return new ResponseFeedBack();
    }


    /**
     * @Author yjn
     * @Description ssp-feedback-pv 曝光监测
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/pv/test" , method = RequestMethod.GET)
    public ResponseFeedBack pvfTest(@RequestParam String pv) throws IOException{
        long time = System.currentTimeMillis();
        log.info("加密："+pv);
        String[] split = Base64.decode(pv).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("pv_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("pv", 1);
        //  map.put("pv_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_PV, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[8]  + "曝光ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-click 点击监测
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/checkClicks/test" , method = RequestMethod.GET)
    public ResponseFeedBack clickViewsfTest(@RequestParam String checkClicks) throws IOException {
        long time = System.currentTimeMillis();
        log.info("加密："+checkClicks);
        String[] split = Base64.decode(checkClicks).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("click_price", split[1]);
        map.put("ip", split[2]);
        map.put("publish_id", split[3]);
        map.put("media_id", split[4]);
        map.put("pos_id", split[5]);
        map.put("slot_type", split[6]);
        map.put("dsp_id", split[7]);
        map.put("dsp_media_id", split[8]);
        map.put("dsp_pos_id", split[9]);
        map.put("click", 1);
        //   map.put("click_url", split[10]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_CLICK, json.toString());//下游媒体请求存入相应topic
        log.info("应用----：" + split[8] + "点击监测ID"+ split[0] + "," + split[2] + "," + time);
        return new ResponseFeedBack();
    }

//    /**
//     * @Author yjn
//     * @Description ssp-feedback-vedio_start
//     * @Date 2021/04/27 9:51
//     */
//    @RequestMapping(value="/vedio/start/test" , method = RequestMethod.GET)
//    public ResponseFeedBack vedioStartTest(@RequestParam String vedioStart, HttpServletResponse response) throws IOException {
//        long time = System.currentTimeMillis();
//        String decode = Base64.decode(vedioStart);
//        String[] split = decode.split(",");
//        String vedio = split[0];
//        String requestId = split[1];
//        String ip = split[2];
//        String name = split[3];
//        String appName = split[4];
//        String key = "ssp-feedback-vedio_start-"+requestId;
//        redisUtil.set(key,vedio);//写入redis
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("requestId", requestId);
//        map.put("vedioStart", vedio);
//        map.put("ip", ip);
//        map.put("timestamp",time);
//        JSONObject json = new JSONObject(map);
//        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_VEDIO_START, json.toString());//下游媒体请求存入相应topic
//        response.sendRedirect(vedio);//重定向到上游的视频播放开始
//        log.info("测试监测链接" + appName + "应用----：" + name + "视频开始" + vedio + "," + requestId + "," + ip + "," + time);
//       return new ResponseFeedBack();
//    }

//    /**
//     * @Author yjn
//     * @Description ssp-feedback-vedio_end
//     * @Date 2021/04/27 9:51
//     */
//    @RequestMapping(value="/vedio/end/test" , method = RequestMethod.GET)
//    public ResponseFeedBack vedioEndTest(@RequestParam String vedioEnd, HttpServletResponse response) throws IOException {
//        long time = System.currentTimeMillis();
//        String decode = Base64.decode(vedioEnd);
//        String[] split = decode.split(",");
//        String vedio = split[0];
//        String requestId = split[1];
//        String ip = split[2];
//        String name = split[3];
//        String appName = split[4];
//        String key = "ssp-feedback-vedio_end-"+requestId;
//        redisUtil.set(key,vedio);//写入redis
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("requestId", requestId);
//        map.put("vedioEnd", vedio);
//        map.put("ip", ip);
//        map.put("timestamp",time);
//        JSONObject json = new JSONObject(map);
//        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_VEDIO_END, json.toString());//下游媒体请求存入相应topic
//        response.sendRedirect(vedio);//重定向到上游的视频播放结束
//        log.info("测试监测链接" + appName + "应用----：" + name + "视频结束" + vedio + "," + requestId + "," + ip + "," + time);
//       return new ResponseFeedBack();
//    }

    /**
     * @Author yjn
     * @Description ssp-feedback-download_start
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/download/start/test" , method = RequestMethod.GET)
    public ResponseFeedBack downloadStartTest(@RequestParam String downloadStart) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(downloadStart).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("ip", split[1]);
        map.put("publish_id", split[2]);
        map.put("media_id", split[3]);
        map.put("pos_id", split[4]);
        map.put("slot_type", split[5]);
        map.put("dsp_id", split[6]);
        map.put("dsp_media_id", split[7]);
        map.put("dsp_pos_id", split[8]);
        map.put("download_start", 1);
        map.put("download_start_url", split[11]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_DOWNLOAD_START_TEST, json.toString());//下游媒体请求存入相应topic
        log.info("测试监测链接" + split[9] + "应用----：" + split[10] + "下载开始" + split[11] + "," + split[0] + "," + split[1] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description ssp-feedback-download_end
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/download/end/test" , method = RequestMethod.GET)
    public ResponseFeedBack downloadEndTest(@RequestParam String downloadEnd) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(downloadEnd).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("ip", split[1]);
        map.put("publish_id", split[2]);
        map.put("media_id", split[3]);
        map.put("pos_id", split[4]);
        map.put("slot_type", split[5]);
        map.put("dsp_id", split[6]);
        map.put("dsp_media_id", split[7]);
        map.put("dsp_pos_id", split[8]);
        map.put("download_end", 1);
        map.put("download_end_url", split[11]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_DOWNLOAD_END_TEST, json.toString());//下游媒体请求存入相应topic
        log.info("测试监测链接" + split[9] + "应用----：" + split[10] + "下载结束" + split[11] + "," + split[0] + "," + split[1] + "," + time);
        return new ResponseFeedBack();
    }

//    /**
//     * @Author yjn
//     * @Description ssp-feedback-install_start
//     * @Date 2021/04/27 9:51
//     */
//    @RequestMapping(value="/install/start/test" , method = RequestMethod.GET)
//    public ResponseFeedBack installStartTest(@RequestParam String installStart, HttpServletResponse response) throws IOException {
//        long time = System.currentTimeMillis();
//        String decode = Base64.decode(installStart);
//        String[] split = decode.split(",");
//        String install = split[0];
//        String requestId = split[1];
//        String ip = split[2];
//        String name = split[3];
//        String appName = split[4];
//        String key = "ssp-feedback-install_start-"+requestId;
//        redisUtil.set(key,install,ExpireTime);//写入redis
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("requestId", requestId);
//        map.put("installStart", install);
//        map.put("ip", ip);
//        map.put("timestamp",time);
//        JSONObject json = new JSONObject(map);
//        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_INSTALL_START, json.toString());//下游媒体请求存入相应topic
//        response.sendRedirect(install);//重定向到上游的安装开始
//        log.info("测试监测链接" + appName + "应用----：" + name + "安装开始" + install + "," + requestId + "," + ip + "," + time);
//       return new ResponseFeedBack();
//    }

//    /**
//     * @Author yjn
//     * @Description  ssp-feedback-install_end
//     * @Date 2021/04/27 9:51
//     */
//    @RequestMapping(value="/install/end/test" , method = RequestMethod.GET)
//    public ResponseFeedBack installEndTest(@RequestParam String installEnd, HttpServletResponse response) throws IOException {
//        long time = System.currentTimeMillis();
//        String decode = Base64.decode(installEnd);
//        String[] split = decode.split(",");
//        String install = split[0];
//        String requestId = split[1];
//        String ip = split[2];
//        String name = split[3];
//        String appName = split[4];
//        String key = "ssp-feedback-install_end-"+requestId;
//        redisUtil.set(key,install,ExpireTime);//写入redis
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("requestId", requestId);
//        map.put("installEnd", install);
//        map.put("ip", ip);
//        map.put("timestamp",time);
//        JSONObject json = new JSONObject(map);
//        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_INSTALL_END, json.toString());//下游媒体请求存入相应topic
//        response.sendRedirect(install);//重定向到上游的安装结束
//        log.info("测试监测链接" + appName + "应用----：" + name + "安装结束" + install + "," + requestId + "," + ip + "," + time);
//       return new ResponseFeedBack();
//    }

//    /**
//     * @Author yjn
//     * @Description ssp-feedback-activation
//     * @Date 2021/04/27 9:51
//     */
//    @RequestMapping(value="/activation/test" , method = RequestMethod.GET)
//    public ResponseFeedBack activationTest(@RequestParam String activation, HttpServletResponse response) throws IOException {
//        long time = System.currentTimeMillis();
//        String decode = Base64.decode(activation);
//        String[] split = decode.split(",");
//        String activ = split[0];
//        String requestId = split[1];
//        String ip = split[2];
//        String name = split[3];
//        String appName = split[4];
//        String key = "ssp-feedback-activation-"+requestId;
//        redisUtil.set(key,activ,ExpireTime);//写入redis
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("requestId", requestId);
//        map.put("activation", activ);
//        map.put("ip", ip);
//        map.put("timestamp",time);
//        JSONObject json = new JSONObject(map);
//        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_ACTIVATION, json.toString());//下游媒体请求存入相应topic
//        response.sendRedirect(activ);//重定向到上游的激活
//        log.info("测试监测链接" + appName + "应用----：" + name + "激活时间" + activ + "," + requestId + "," + ip + "," + time);
//       return new ResponseFeedBack();
//    }

    /**
     * @Author yjn
     * @Description ssp-feedback-deeplink
     * @Date 2021/04/27 9:51
     */
    @RequestMapping(value="/deeplink/test" , method = RequestMethod.GET)
    public ResponseFeedBack deeplinkTest(@RequestParam String deeplink) throws IOException {
        long time = System.currentTimeMillis();
        String[] split = Base64.decode(deeplink).split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", split[0]);
        map.put("ip", split[1]);
        map.put("publish_id", split[2]);
        map.put("media_id", split[3]);
        map.put("pos_id", split[4]);
        map.put("slot_type", split[5]);
        map.put("dsp_id", split[6]);
        map.put("dsp_media_id", split[7]);
        map.put("dsp_pos_id", split[8]);
        map.put("download_end", 1);
        map.put("download_end_url", split[11]);
        map.put("timestamp",time);
        JSONObject json = new JSONObject(map);
        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_DEEPLINK_TEST, json.toString());//下游媒体请求存入相应topic
        log.info("测试监测链接" + split[9] + "应用----：" + split[10] + "deeplink链接" + split[11] + "," + split[0] + "," + split[1] + "," + time);
        return new ResponseFeedBack();
    }

    /**
     * @Author yjn
     * @Description  ssp-feedback-ideeplink
     * @Date 2021/04/27 9:51
     */
//    @RequestMapping(value="/ideeplink/test" , method = RequestMethod.GET)
//    public ResponseFeedBack ideeplinkTest(@RequestParam String ideeplink, HttpServletResponse response) throws IOException {
//        long time = System.currentTimeMillis();
//        String decode = Base64.decode(ideeplink);
//        String[] split = decode.split(",");
//        String ideep = split[0];
//        String requestId = split[1];
//        String ip = split[2];
//        String name = split[3];
//        String appName = split[4];
//        String key = "ssp-feedback-ideeplink-"+requestId;
//        redisUtil.set(key,ideep,ExpireTime);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("requestId", requestId);
//        map.put("ideeplink", ideep);
//        map.put("ip", ip);
//        map.put("timestamp",time);
//        JSONObject json = new JSONObject(map);
//        kafkaTemplate.send(TopicConfig.SSP_FEEDBACK_IDEEPLINK, json.toString());//下游媒体请求存入相应topic
//        response.sendRedirect(ideep);//重定向到上游的deeplink
//        log.info("测试监测链接" + appName + "应用----：" + name + "ideeplink链接" + ideep + "," + requestId + "," + ip + "," + time);
//       return new ResponseFeedBack();
//    }
}
