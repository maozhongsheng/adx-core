package com.mk.adx.AsyncConfig;

import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.AsyncConfig.asyncService.RandomRateService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * 随机事件分发器-流量分发-打底开关
 *
 * @author yjn
 * @version 1.0
 * @date 2021/11/11 18:20
 */
@Slf4j
@Configuration
public class RandomUtil {

    @Autowired
    private RandomRateService randomRateService;

    /**
     * 随机方法-得到随机advid和并发advid，并取得最后返回
     * @param distribute 参数
     * @return
     * @throws IOException
     */
    @SneakyThrows
    public MkBidResponse randomRequest(Map distribute, MkBidRequest request){

        MkBidResponse bidResponse = new MkBidResponse();//最后返回数据
        Map<Integer,MkBidResponse> ranMapObj = new HashMap<>();//存比例和返回数据
        Map<Integer,MkBidResponse> conMapObj = new HashMap<>();//存比例和返回数据
        Map<String, Integer> map = new HashMap<>();//存储请求开关集合
        Map<String, Integer> low_map = new HashMap<>();//存储并发开关集合
        Map<String, Integer> ran_map = new HashMap<>();//存储随机到的数据
        Map<String, Integer> make_map = new HashMap<>();//存储匹配机型集合

        String directional_status = distribute.get("directional_status").toString();//请求开关状态
        String[] dir_status = directional_status.split(",");

        String[] low_status = new String[3];
        if (null!=distribute.get("lowest_status")){
            String lowest_status = distribute.get("lowest_status").toString();//并发开关状态
            low_status = lowest_status.split(",");
        }

        String advert_id = distribute.get("dsp_slot_id").toString();//联盟广告位id
        String[] advert_ids = advert_id.split(",");

        String requet_rate = distribute.get("requet_rate").toString();//流量百分比
        String[] rates = requet_rate.split(",");

        //机型 1:苹果，2：华为，3：vivo，4：oppo，5：小米，6：魅族，7：荣耀，8：金立，9：三星
        Object makeObject = distribute.get("make");//(结构)1,2,3|2,3,4|3,4,5
        if (null!=makeObject){
            String makes = makeObject.toString();
            log.info(request.getId()+"机型数组"+makes);
            String[] make_status = makes.split("|");//得到每个上游的机型，不同联盟的用|分开，然后不通机型的用，分开
            //请求媒体的机型
            String makeParse = "";//转成数字
            String make = request.getDevice().getMake();
            if (make.contains("ios")||make.contains("IOS")){
                makeParse = "1";
            }else if (make.contains("huawei")||make.contains("HUAWEI")||make.contains("华为")){
                makeParse = "2";
            }else if (make.contains("vivo")||make.contains("VIVO")){
                makeParse = "3";
            }else if (make.contains("oppo")||make.contains("OPPO")){
                makeParse = "4";
            }else if (make.contains("xiaomi")||make.contains("XIAOMI")||make.contains("小米")){
                makeParse = "5";
            }else if (make.contains("meizu")||make.contains("MEIZU")||make.contains("魅族")){
                makeParse = "6";
            }else if (make.contains("honor")||make.contains("HONOR")||make.contains("荣耀")){
                makeParse = "7";
            }else if (make.contains("jinli")||make.contains("JINLI")||make.contains("金立")){
                makeParse = "8";
            }else if (make.contains("samsung")||make.contains("SAMSUNG")||make.contains("三星")){
                makeParse = "9";
            }
            //机型优先级最高，根据机型匹配去请求广告，如果有返回直接返回，如果没有返回再去随机和并发
            if (null!=make_status){
                for (int k=0; k<make_status.length; k++){
                    if (make_status[k].contains(makeParse)){
                        if (!make_status[k].equals(0)){
                            make_map.put(advert_ids[k], Integer.valueOf(rates[k]));
                        }
                    }
                }
            }
            //***机型匹配的请求，与并发请求时一样---如果匹配机型不为空优先，否则走下面随机和并发
            if (!make_map.isEmpty()){
                Future<Map<Integer, MkBidResponse>> conMap = null;//
                conMap = randomRateService.concurrentRequest(make_map,distribute,request);
                conMapObj = conMap.get();
                //如果机型匹配的不为空那么直接返回，如果为空，再走下面随机和打底的联盟
                if (!conMapObj.isEmpty()){
                    Integer max = 0;//设置比较值最大值
                    for (Integer rate : conMapObj.keySet()) {
                        if (conMapObj.keySet().size()==1){
                            bidResponse = conMapObj.get(rate);//如果只有一个返回，直接返回
                            return bidResponse;
                        }else {
                            if (rate>max){
                                max = rate;
                                bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                            }
                        }
                    }
                    return bidResponse;
                }else {
                    /**
                     * 随机和并发
                     */
                    Integer rateSumOn = 0;//总配置比例-开关开
                    Integer rateSumOff = 0;//总配置比例-开关关

                    /**
                     * 1、获取随机advid和并发advid
                     */
                    for (int i=0; i<rates.length; i++){
                        if(!StringUtils.isEmpty(rates[i])){
                            if ("1".equals(dir_status[i])){//请求开光状态是1的分配流量
                                //请求开关开的百分比总和
                                rateSumOn += Integer.valueOf(rates[i]);

                                //联盟广告位id当key，百分比为value
                                map.put(advert_ids[i], Integer.valueOf(rates[i]));

                                //并发开光状态是1的记录
                                if (null!=low_status){
                                    if ("1".equals(low_status[i])) {
                                        low_map.put(advert_ids[i], Integer.valueOf(rates[i]));//联盟广告位id当key，百分比为value
                                    }
                                }

                                //并发的map去除机型的map，为了不重复请求
                                for (String mm : make_map.keySet()) {
                                    //并发中如果包含机型的，则去除
                                    if (low_map.containsKey(mm)){
                                        Iterator<String> iter = low_map.keySet().iterator();
                                        while(iter.hasNext()){
                                            if (iter.next().equals(mm)){
                                                iter.remove();
                                            }
                                        }
                                    }
                                }
                            }else {
                                rateSumOff += Integer.valueOf(rates[i]);//请求开关是关的百分比总和
                            }
                        }else {
                            break;
                        }
                    }

                    //获取没有配置流量的比例,放入map
                    if ((rateSumOn+rateSumOff)<100){
                        map.put("999999999",100-rateSumOn-rateSumOff);
                    }

                    //获取权重总和
                    Integer sum = map.values().parallelStream().reduce(Integer::sum).get();
                    //获取一个随机数
                    Integer random = new Random().nextInt(sum);

                    //处理随机到的联盟id
                    for (String str : map.keySet()) {
                        int weight = map.get(str);//比例
                        if (random >= weight) {
                            random -= weight;
                        } else {
                            //随机到的数据
                            ran_map.put(str,map.get(str));

                            //如果机型的map中包含了随机到的，则直接返回，不能重复请求
                            if (make_map.containsKey(str)){
                                return bidResponse;
                            }

                            //并发中如果包含随机的，则去除
                            if (low_map.containsKey(str)){
                                Iterator<String> iter = low_map.keySet().iterator();
                                while(iter.hasNext()){
                                    if (iter.next().equals(str)){
                                        iter.remove();
                                    }
                                }
                            }
                            break;
                        }
                    }

                    //2、调用异步请求时，公共的逻辑-返回比例和最后返回数据----随机的advid
//                Future<Map<Integer, TzBidResponse>> conMap = null;//
                    conMap = randomRateService.randomRequest(ran_map,distribute,request);
                    ranMapObj = conMap.get();

                    //2、调用异步请求时，公共的逻辑-返回比例和最后返回数据----并发的advid
                    if(!low_map.isEmpty()){
                        conMap = randomRateService.concurrentRequest(low_map,distribute,request);
                        conMapObj = conMap.get();
                    }

                    //3、处理最后的返回数据
                    if (ranMapObj.isEmpty()&&conMapObj.isEmpty()){
                        return bidResponse;
                    }else if (!ranMapObj.isEmpty()&&conMapObj.isEmpty()){
                        //随机不为空，并发为空，直接返回随机
                        for (Integer rate : ranMapObj.keySet()) {
                            bidResponse = ranMapObj.get(rate);
                        }
                        return bidResponse;
                    }else if (ranMapObj.isEmpty()&&!conMapObj.isEmpty()){
                        //随机为空，并发不为空，处理并发，返回并发里概率大的
                        Integer max = 0;//设置比较值最大值
                        for (Integer rate : conMapObj.keySet()) {
                            if (conMapObj.keySet().size()==1){
                                bidResponse = conMapObj.get(rate);//如果只有一个返回，直接返回
                                break;
                            }else {
                                if (rate>max){
                                    max = rate;
                                    bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                                }
                            }
                        }
                        return bidResponse;
                    }else if (!ranMapObj.isEmpty()&&!conMapObj.isEmpty()){
                        //随机不为空，并发不为空，取所有概率大的
                        //获取随机的比例
                        Integer ranRate = 0;//设置比较值最大值
                        for (Integer rate : ranMapObj.keySet()) {
                            ranRate = rate;
                            bidResponse = ranMapObj.get(rate);//返回随机比例的最后返回数据

                        }
                        //处理并发，比较比例大小，返回比例大的
                        for (Integer rate : conMapObj.keySet()) {
                            if (rate>ranRate){
                                bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                            }
                        }
                        return bidResponse;
                    }
                }
            }else {
                /**
                 * 随机和并发
                 */
                Integer rateSumOn = 0;//总配置比例-开关开
                Integer rateSumOff = 0;//总配置比例-开关关

                /**
                 * 1、获取随机advid和并发advid
                 */
                for (int i=0; i<rates.length; i++){
                    if(!StringUtils.isEmpty(rates[i])){
                        if ("1".equals(dir_status[i])){//请求开光状态是1的分配流量
                            //请求开关开的百分比总和
                            rateSumOn += Integer.valueOf(rates[i]);

                            //联盟广告位id当key，百分比为value
                            map.put(advert_ids[i], Integer.valueOf(rates[i]));

                            //并发开光状态是1的记录
                            if (null!=low_status){
                                if ("1".equals(low_status[i])) {
                                    low_map.put(advert_ids[i], Integer.valueOf(rates[i]));//联盟广告位id当key，百分比为value
                                }
                            }
                        }else {
                            rateSumOff += Integer.valueOf(rates[i]);//请求开关是关的百分比总和
                        }
                    }else {
                        break;
                    }
                }

                //获取没有配置流量的比例,放入map
                if ((rateSumOn+rateSumOff)<100){
                    map.put("999999999",100-rateSumOn-rateSumOff);
                }

                //获取权重总和
                Integer sum = map.values().parallelStream().reduce(Integer::sum).get();
                //获取一个随机数
                Integer random = new Random().nextInt(sum);

                //处理随机到的联盟id
                for (String str : map.keySet()) {
                    int weight = map.get(str);//比例
                    if (random >= weight) {
                        random -= weight;
                    } else {
                        //随机到的数据
                        ran_map.put(str,map.get(str));

                        //并发中如果包含随机的，则去除
                        if (low_map.containsKey(str)){
                            Iterator<String> iter = low_map.keySet().iterator();
                            while(iter.hasNext()){
                                if (iter.next().equals(str)){
                                    iter.remove();
                                }
                            }
                        }
                        break;
                    }
                }

                //2、调用异步请求时，公共的逻辑-返回比例和最后返回数据----随机的advid
                Future<Map<Integer, MkBidResponse>> conMap = null;//
                conMap = randomRateService.randomRequest(ran_map,distribute,request);
                ranMapObj = conMap.get();

                //2、调用异步请求时，公共的逻辑-返回比例和最后返回数据----并发的advid
                if(!low_map.isEmpty()){
                    conMap = randomRateService.concurrentRequest(low_map,distribute,request);
                    conMapObj = conMap.get();
                }

                //3、处理最后的返回数据
                if (ranMapObj.isEmpty()&&conMapObj.isEmpty()){
                    return bidResponse;
                }else if (!ranMapObj.isEmpty()&&conMapObj.isEmpty()){
                    //随机不为空，并发为空，直接返回随机
                    for (Integer rate : ranMapObj.keySet()) {
                        bidResponse = ranMapObj.get(rate);
                    }
                    return bidResponse;
                }else if (ranMapObj.isEmpty()&&!conMapObj.isEmpty()){
                    //随机为空，并发不为空，处理并发，返回并发里概率大的
                    Integer max = 0;//设置比较值最大值
                    for (Integer rate : conMapObj.keySet()) {
                        if (conMapObj.keySet().size()==1){
                            bidResponse = conMapObj.get(rate);//如果只有一个返回，直接返回
                            break;
                        }else {
                            if (rate>max){
                                max = rate;
                                bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                            }
                        }
                    }
                    return bidResponse;
                }else if (!ranMapObj.isEmpty()&&!conMapObj.isEmpty()){
                    //随机不为空，并发不为空，取所有概率大的
                    //获取随机的比例
                    Integer ranRate = 0;//设置比较值最大值
                    for (Integer rate : ranMapObj.keySet()) {
                        ranRate = rate;
                        bidResponse = ranMapObj.get(rate);//返回随机比例的最后返回数据

                    }
                    //处理并发，比较比例大小，返回比例大的
                    for (Integer rate : conMapObj.keySet()) {
                        if (rate>ranRate){
                            bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                        }
                    }
                    return bidResponse;
                }
            }
        }else {
            /**
             * 随机和并发
             */
            Integer rateSumOn = 0;//总配置比例-开关开
            Integer rateSumOff = 0;//总配置比例-开关关

            /**
             * 1、获取随机advid和并发advid
             */
            for (int i=0; i<rates.length; i++){
                if(!StringUtils.isEmpty(rates[i])){
                    if ("1".equals(dir_status[i])){//请求开光状态是1的分配流量
                        //请求开关开的百分比总和
                        rateSumOn += Integer.valueOf(rates[i]);

                        //联盟广告位id当key，百分比为value
                        map.put(advert_ids[i], Integer.valueOf(rates[i]));

                        //并发开光状态是1的记录
                        if (null!=low_status){
                            if ("1".equals(low_status[i])) {
                                low_map.put(advert_ids[i], Integer.valueOf(rates[i]));//联盟广告位id当key，百分比为value
                            }
                        }
                    }else {
                        rateSumOff += Integer.valueOf(rates[i]);//请求开关是关的百分比总和
                    }
                }else {
                    break;
                }
            }

            //获取没有配置流量的比例,放入map
            if ((rateSumOn+rateSumOff)<100){
                map.put("999999999",100-rateSumOn-rateSumOff);
            }

            //获取权重总和
            Integer sum = map.values().parallelStream().reduce(Integer::sum).get();
            //获取一个随机数
            Integer random = new Random().nextInt(sum);

            //处理随机到的联盟id
            for (String str : map.keySet()) {
                int weight = map.get(str);//比例
                if (random >= weight) {
                    random -= weight;
                } else {
                    //随机到的数据
                    ran_map.put(str,map.get(str));

                    //并发中如果包含随机的，则去除
                    if (low_map.containsKey(str)){
                        Iterator<String> iter = low_map.keySet().iterator();
                        while(iter.hasNext()){
                            if (iter.next().equals(str)){
                                iter.remove();
                            }
                        }
                    }
                    break;
                }
            }

            //2、调用异步请求时，公共的逻辑-返回比例和最后返回数据----随机的advid
            Future<Map<Integer, MkBidResponse>> conMap = null;//
            conMap = randomRateService.randomRequest(ran_map,distribute,request);
            ranMapObj = conMap.get();

            //2、调用异步请求时，公共的逻辑-返回比例和最后返回数据----并发的advid
            if(!low_map.isEmpty()){
                conMap = randomRateService.concurrentRequest(low_map,distribute,request);
                conMapObj = conMap.get();
            }

            //3、处理最后的返回数据
            if (ranMapObj.isEmpty()&&conMapObj.isEmpty()){
                return bidResponse;
            }else if (!ranMapObj.isEmpty()&&conMapObj.isEmpty()){
                //随机不为空，并发为空，直接返回随机
                for (Integer rate : ranMapObj.keySet()) {
                    bidResponse = ranMapObj.get(rate);
                }
                return bidResponse;
            }else if (ranMapObj.isEmpty()&&!conMapObj.isEmpty()){
                //随机为空，并发不为空，处理并发，返回并发里概率大的
                Integer max = 0;//设置比较值最大值
                for (Integer rate : conMapObj.keySet()) {
                    if (conMapObj.keySet().size()==1){
                        bidResponse = conMapObj.get(rate);//如果只有一个返回，直接返回
                        break;
                    }else {
                        if (rate>max){
                            max = rate;
                            bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                        }
                    }
                }
                return bidResponse;
            }else if (!ranMapObj.isEmpty()&&!conMapObj.isEmpty()){
                //随机不为空，并发不为空，取所有概率大的
                //获取随机的比例
                Integer ranRate = 0;//设置比较值最大值
                for (Integer rate : ranMapObj.keySet()) {
                    ranRate = rate;
                    bidResponse = ranMapObj.get(rate);//返回随机比例的最后返回数据

                }
                //处理并发，比较比例大小，返回比例大的
                for (Integer rate : conMapObj.keySet()) {
                    if (rate>ranRate){
                        bidResponse = conMapObj.get(rate);//取比例最大的返回数据
                    }
                }
                return bidResponse;
            }
        }

        return bidResponse;
    }

}
