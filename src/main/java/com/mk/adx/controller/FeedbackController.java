package com.mk.adx.controller;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.config.TopicConfig;
import com.mk.adx.entity.json.response.ResponseFeedBack;
import com.mk.adx.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 重定向请求
 *
 * @author mzs
 * @version 1.0
 * @date 2022/06/22 19:07
 */
@Controller
@Slf4j
@ResponseBody
public class FeedbackController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static int ExpireTime = 6000;   // redis中存储的过期时间60s



    /**
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/click" , method = RequestMethod.GET)
    public ResponseFeedBack clickViews(@RequestParam String click, HttpServletResponse response) throws IOException {
        long time = System.currentTimeMillis();
        log.info("加密："+click);
        String[] split = Base64.decode(click).split(",");
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/v_start" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/v_end" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/dl_start" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/dl_end" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/in_start" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/in_end" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/dp_success" , method = RequestMethod.GET)
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
     * @author mzs
     * @version 1.0
     * @date 2022/06/22 19:07
     */
    @RequestMapping(value="/dp_fail" , method = RequestMethod.GET)
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
}
