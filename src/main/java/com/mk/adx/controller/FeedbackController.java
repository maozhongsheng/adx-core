package com.mk.adx.controller;

import com.mk.adx.entity.json.response.ResponseFeedBack;
import com.mk.adx.mapper.DataAllMapper;
import com.mk.adx.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 监测请求
 *
 * @author mzs
 * @version 1.0
 * @date 2022/6/20 19:07
 */
@Controller
@Slf4j
@ResponseBody
@RequestMapping(value = "/api/jc")
public class FeedbackController {

    private static Map da = new HashMap();   // redis中存储的过期时间60s

    @Autowired
    private DataAllMapper dataAllMapper;

    /**
     * @Author mzs
     * @Description ssp-feedback-pv 曝光监测
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/pv" , method = RequestMethod.GET)
    public ResponseFeedBack pv(@RequestParam String pv, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(pv).split(",");
        da.put("req_id",split[0]);
        da.put("pv_s",1);
        da.put("pv_time",System.currentTimeMillis());
        dataAllMapper.uppv(da);
        return new ResponseFeedBack();
    }
    /**
     * @Author mzs
     * @Description ssp-feedback-click 点击监测
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/click" , method = RequestMethod.GET)
    public ResponseFeedBack clickViews(@RequestParam String click, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(click).split(",");
        da.put("req_id",split[0]);
        da.put("clikc_s",1);
        da.put("clikc_time",System.currentTimeMillis());
        dataAllMapper.upck(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description ssp-feedback-vedio_start
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/vedio/start" , method = RequestMethod.GET)
    public ResponseFeedBack vedioStart(@RequestParam String vedioStart, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(vedioStart).split(",");
        da.put("req_id",split[0]);
        da.put("v_start_s",1);
        dataAllMapper.upvs(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description ssp-feedback-vedio_end
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/vedio/end" , method = RequestMethod.GET)
    public ResponseFeedBack vedioEnd(@RequestParam String vedioEnd, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(vedioEnd).split(",");
        da.put("req_id",split[0]);
        da.put("v_end_s",1);
        dataAllMapper.upve(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description ssp-feedback-download_start
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/download/start" , method = RequestMethod.GET)
    public ResponseFeedBack downloadStart(@RequestParam String downloadStart, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(downloadStart).split(",");
        da.put("req_id",split[0]);
        da.put("download_start_s",1);
        dataAllMapper.upds(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description ssp-feedback-download_end
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/download/end" , method = RequestMethod.GET)
    public ResponseFeedBack downloadEnd(@RequestParam String downloadEnd, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(downloadEnd).split(",");
        da.put("req_id",split[0]);
        da.put("download_end_s",1);
        dataAllMapper.upde(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description ssp-feedback-install_start
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/install/start" , method = RequestMethod.GET)
    public ResponseFeedBack installStart(@RequestParam String installStart, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(installStart).split(",");
        da.put("req_id",split[0]);
        da.put("install_start_s",1);
        dataAllMapper.upis(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description  ssp-feedback-install_end
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/install/end" , method = RequestMethod.GET)
    public ResponseFeedBack installEnd(@RequestParam String installEnd, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(installEnd).split(",");
        da.put("req_id",split[0]);
        da.put("install_end_s",1);
        dataAllMapper.upie(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description ssp-feedback-deeplink
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/deeplink" , method = RequestMethod.GET)
    public ResponseFeedBack deeplink(@RequestParam String deeplink, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(deeplink).split(",");
        da.put("req_id",split[0]);
        da.put("deeplink_s",1);
        dataAllMapper.updp(da);
        return new ResponseFeedBack();
    }

    /**
     * @Author mzs
     * @Description  ssp-feedback-ideeplink
     * @Date 2022/06/20 9:51
     */
    @RequestMapping(value="/ideeplink" , method = RequestMethod.GET)
    public ResponseFeedBack ideeplink(@RequestParam String ideeplink, HttpServletResponse response) throws IOException {
        String[] split = Base64.decode(ideeplink).split(",");
        da.put("req_id",split[0]);
        da.put("ideeplink_s",1);
        dataAllMapper.upidp(da);
        return new ResponseFeedBack();
    }
}
