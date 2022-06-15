package com.mk.adx.AsyncConfig;

import com.alibaba.fastjson.JSONObject;
import com.mk.adx.client.AdminClient;
import com.mk.adx.client.DspClient;
import com.mk.adx.entity.json.request.tz.TzAdv;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.service.*;
import com.mk.adx.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 异步请求时，公共的逻辑
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/11 18:20
 */
@Slf4j
@Configuration
public class AsyncRequestUtil {

    @Autowired
    private DspClient dspClient;

    @Autowired
    private AdminClient adminClient;

    @Autowired
    private YdJsonService ydJsonService;

    @Autowired
    private YdzxJsonService ydzxJsonService;

    @Autowired
    private SdJsonService sdJsonService;

    @Autowired
    private TestJsonService testJsonService;

    @Autowired
    private BaiduJsonService baiduJsonService;

    @Autowired
    private ChuanGuangService chuanGuangService;

    @Autowired
    private CdJsonService cdJsonService;

    @Autowired
    private YqJsonService yqJsonService;

    @Autowired
    private YxJsonService yxJsonService;

    @Autowired
    private CqJsonService cqJsonService;

    @Autowired
    private SmJsonService smJsonService;

    @Autowired
    private ZslyJsonService zslyJsonService;

    @Autowired
    private HailiangJsonService hailiangJsonService;

    @Autowired
    private MtRtaJsonService mtRtaJsonService;

    @Autowired
    private RzJsonService rzJsonService;

    @Autowired
    private WbRtaJsonService wbRtaJsonService;

    @Autowired
    private XingYunJsonService xingYunJsonService;

    @Autowired
    private QyJsonService qyJsonService;

    @Autowired
    private ZhimengJsonService zhimengJsonService;

    @Autowired
    private YuanYinJsonService yuanYinJsonService;

    @Autowired
    private MgRtaJsonService mgRtaJsonService;

    @Autowired
    private InvenoService invenoService;

    @Autowired
    private OnewayJsonService onewayJsonService;

    @Autowired
    private UcJsonService ucJsonService;

    @Autowired
    private YiLiangJsonService yiLiangJsonService;

    @Autowired
    private YouYiJsonService  youYiJsonService;

    @Autowired
    private LanWaJsonService lanWaJsonService;

    @Autowired
    private YunJuHeJsonService yunJuHeJsonService;

    @Autowired
    private DspJsonService dspJsonService;

    @Autowired
    private InsertKafka insertKafka;

    @Autowired
    private ZhongMengJsonService zhongMengJsonService;

    @Autowired
    private JiaLiangJsonService jiaLiangJsonService;

    @Autowired
    private AlgorixJsonService algorixJsonService;

    @Autowired
    private WokeJsonService wokeJsonService;

    @Autowired
    private SzydJsonService szydJsonService;

    @Autowired
    private MiTuJsonService miTuJsonService;

    @Autowired
    private RuiDiJsonService ruiDiJsonService;

    @Autowired
    private BaiXunJsonService baiXunJsonService;

    @Autowired
    private TongZhouJsonService tongZhouJsonService;

    @Autowired
    private DouMengJsonService douMengJsonService;


    @Resource
    private RedisUtil redisUtil;

    public Map<Integer, TzBidResponse> totalRequest(Map<String, Integer> map, Map distribute, TzBidRequest request, int status){
        String timeoutStr = distribute.get("timeout").toString();//后台配置超时时间
        TzAdv tzAdv = new TzAdv();
        Map upper = new HashMap();
        Map<Integer, TzBidResponse> mapObj = new HashMap<>();

        //循环获取相应数据
        for (String str : map.keySet()) {
            String adv_id = str;//联盟广告位id(adv_id)
            Integer rate = map.get(str);//比例

            //先从redis查询，如果为空则从数据库先查再放入redis
            //根据联盟广告位id查询上游数据,无效流量直接返回
            upper = JSONObject.parseObject((redisUtil.get(adv_id).toString().replace("=", ":")), HashMap.class);//根据联盟id（key）去查询相应信息
            if (null == upper) {
                //根据联盟广告位id查询上游数据
                upper = adminClient.selectUpperBySlotId(adv_id);

                //将上游数据存入redis
                HashMap hashMap = new HashMap();
                hashMap.put("dsp_id", upper.get("dsp_id").toString());
                hashMap.put("app_id", upper.get("app_id").toString());
                hashMap.put("app_name", upper.get("app_name").toString());
                hashMap.put("bundle", upper.get("package").toString());
                hashMap.put("version", upper.get("version").toString());
                hashMap.put("tag_id", upper.get("tag_id").toString());
                hashMap.put("size", upper.get("dsp_size").toString());
                hashMap.put("slot_type", upper.get("slot_type").toString());
                hashMap.put("os", upper.get("os").toString());
                redisUtil.set(adv_id, JSONObject.toJSONString(hashMap));
            }

            //将上游数据存入adv中
            tzAdv.setApp_name(upper.get("app_name").toString());
            tzAdv.setDsp_id(upper.get("dsp_id").toString());
            tzAdv.setApp_id(upper.get("app_id").toString());
            tzAdv.setTag_id(upper.get("tag_id").toString());
            tzAdv.setSize(upper.get("size").toString());
            tzAdv.setBundle(upper.get("bundle").toString());
            tzAdv.setSlot_type(upper.get("slot_type").toString());
            tzAdv.setVersion(upper.get("version").toString());
            tzAdv.setOs(upper.get("os").toString());
            tzAdv.setPrice(Integer.valueOf(distribute.get("price").toString().split("\\.")[0]));
            tzAdv.setTest(Integer.valueOf(distribute.get("test").toString()));
            request.setAdv(tzAdv);

            //2、处理kafka请求数据
            request = insertKafka.insertKafka(tzAdv,request);

            //3、根据adv_id处理请求service
            if(null != tzAdv){
                TzBidResponse response = asyncRequest(request);//获得返回数据
                if (null!=response.getId()){
                    Long selectTimeout = response.getProcess_time_ms();//请求上游花费时间
                    //上游返回数据时间小于配置时间才给下游返回(如果配置时间为空则不卡时间)
                    if (StringUtils.isNotEmpty(timeoutStr)){
                        Long timeout = Long.parseLong(timeoutStr);
                        if (selectTimeout <= timeout){
                            mapObj.put(rate,response);//比例和返回集合
                        }
                    }else {
                        mapObj.put(rate,response);//比例和返回集合
                    }
                }
            }
        }

        return mapObj;
    }


    /**
     * 3、根据adv_id处理请求service
     * @param bidRequest
     * @return
     */
    private TzBidResponse asyncRequest(TzBidRequest bidRequest){
        TzBidResponse bidResponse = null;//返回数据
        Map parames = dspClient.getselectDspRta(bidRequest.getImp().get(0).getTagid());
        if(null != parames){
            //bidResponse = mtRtaJsonService.getMtRtaDataByJson(bidRequest,parames);
            bidResponse = dspJsonService.getDspDataByJson(bidRequest,parames);
        }else {
            if (1 == bidRequest.getAdv().getTest()) {
                if ("2021000008".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ydJsonService.getYdDataByJson(bidRequest);//有道
                } else if ("2021000009".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ydzxJsonService.getYdzxDataByJson(bidRequest);//一点咨询
                } else if ("2021000010".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = sdJsonService.getSdDataByJson(bidRequest);//时代广告-滴滴
                } else if ("2021000014".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = baiduJsonService.getBaiduDataByJson(bidRequest);//百度
                } else if ("2021000018".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = chuanGuangService.chuanGuangDataByJson(bidRequest);//传广
                } else if ("2021000019".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = cdJsonService.getCdDataByJson(bidRequest);//创典
                } else if ("2021000021".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yqJsonService.getYqDataByJson(bidRequest);//益起
                } else if ("2021000022".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yxJsonService.getYxDataByJson(bidRequest);//云袭
                } else if ("2021000023".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = cqJsonService.getCqDataByJson(bidRequest);//长青
                } else if ("2021000024".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = smJsonService.getSmDataByJson(bidRequest);//思盟
                } else if ("2021000025".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = zslyJsonService.getZslyDataByJson(bidRequest);//掌上乐游
                } else if ("2021000026".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = hailiangJsonService.getHailiangDataByJson(bidRequest);//嗨量
                } else if ("2021000027".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = rzJsonService.getRzDataByJson(bidRequest);//仁泽
                }else if ("2021000029".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = xingYunJsonService.getXyDataByJson(bidRequest);//星云
                }else if ("2021000031".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = qyJsonService.getQyDataByJson(bidRequest);//青云
                }else if ("2021000030".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = zhimengJsonService.getZhimengDataByJson(bidRequest);//知乎-知盟
                }else if ("2021000032".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yuanYinJsonService.getYuanYinDataByJson(bidRequest);//上海缘音
                }else if ("2021000033".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = invenoService.getInvenoDataByJson(bidRequest);//深圳英威诺
                }else if ("2021000034".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = onewayJsonService.getOnewayDataByJson(bidRequest);//广东万唯
                }else if ("2021000036".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ucJsonService.getUcDataByJson(bidRequest);//UC
                }else if ("2021000040".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yiLiangJsonService.getYiLiangDataByJson(bidRequest);//亦量
                }else if ("2021000041".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = youYiJsonService.getYouYiDataByJson(bidRequest);//谊友
                }else if ("2021000042".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = lanWaJsonService.getLanWaDataByJson(bidRequest);//蓝蛙
                }else if ("2021000043".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = yunJuHeJsonService.getYunJuHeDataByJson(bidRequest);//云聚合
                }else if ("2021000044".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = jiaLiangJsonService.getJiaLiangDataByJson(bidRequest);//佳量
                }else if ("2021000045".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = zhongMengJsonService.getZhongMengDataByJson(bidRequest);//众盟
                }else if ("2021000046".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = algorixJsonService.getAlgorixDataByJson(bidRequest);//algorix
                }else if ("2021000047".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = wokeJsonService.getWokeDataByJson(bidRequest);//沃氪
                }else if ("2021000048".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = szydJsonService.getSzydDataByJson(bidRequest);//数字悦动
                }else if ("2021000049".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = ruiDiJsonService.getRuidiDataByJson(bidRequest);//瑞迪
                }else if ("2021000050".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = miTuJsonService.getMiTuDataByJson(bidRequest);//觅途
                }else if ("2021000051".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = baiXunJsonService.getBaiXunDataByJson(bidRequest);//百寻
                }else if ("2021000052".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = tongZhouJsonService.getTongZhouDataByJson(bidRequest);//同舟
                }else if ("2021000053".equals(bidRequest.getAdv().getDsp_id())) {
                    bidResponse = douMengJsonService.getDouMengDataByJson(bidRequest);//豆盟
                }
            }else {
                bidResponse = testJsonService.getTestDataByJson(bidRequest);//测试数据
            }
        }

        return bidResponse;

    }


}
