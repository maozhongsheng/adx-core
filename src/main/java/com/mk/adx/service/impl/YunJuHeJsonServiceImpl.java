package com.mk.adx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.yunjuhe.*;
import com.mk.adx.entity.json.response.tz.*;
import com.mk.adx.entity.json.response.yunjuhe.Feature;
import com.mk.adx.util.Base64;
import com.mk.adx.util.HttppostUtil;
import com.mk.adx.service.YunJuHeJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jny
 * @Description 云聚合
 * @Date 2022/3/21 9:48
 */
@Slf4j
@Service("yunJuHeJsonService")
public class YunJuHeJsonServiceImpl implements YunJuHeJsonService {

    private static final String name = "yunjuhe";

    private static final String source = "云聚合";

    @SneakyThrows
    @Override
    public TzBidResponse getYunJuHeDataByJson(TzBidRequest request) {
        YunJuHeBidRequest yjh = new YunJuHeBidRequest();//总请求
        yjh.setVersion("0.0.1");//Api 版本号 0.0.1

        //App 信息
        YunJuHeApp app = new YunJuHeApp();
        app.setAppId(request.getAdv().getApp_id());
        app.setName(request.getApp().getName());
        app.setPackage(request.getApp().getBundle());
        app.setVersion(request.getApp().getVer());
        yjh.setApp(app);//

        //设备信息
        YunJuHeDevice device = new YunJuHeDevice();
        String os = request.getDevice().getOs();//系统类型
        if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)){//android
            device.setOsType(1);//安卓
            device.setAdid(request.getDevice().getAndroid_id());//Android ID 手机唯一标识
            device.setAndroidIdMd5(request.getDevice().getAndroid_id_md5());//
            device.setVendor(request.getDevice().getMake());//设备厂商
            device.setImei(request.getDevice().getImei());//android Q 之前设备标识必填，Android 必传
            device.setImeiMd5(request.getDevice().getImei_md5());//
            device.setOaid(request.getDevice().getOaid());//Android Q 之后广告标识符，Android 必传
        }else if ("1".equals(os) || "ios".equals(os) || "IOS".equals(os)) {//ios
            device.setOsType(2);//ios
            device.setIdfa(request.getDevice().getIdfa());
            device.setUdid(request.getDevice().getOpen_udid());//OS 设备的 OpenUDID,小于 ios 6 必传
        }else {
            device.setOsType(0);//未知

        }

        //设备类型
        if ("phone".equals(request.getDevice().getDevicetype())){
            device.setType(1);//手机
        }else if ("ipad".equals(request.getDevice().getDevicetype())){
            device.setType(2);//平板
        }else {
            device.setType(9);//其他
        }

        //系统版本号
        if (StringUtils.isNotEmpty(request.getDevice().getOsv())){
            device.setOsVersion(request.getDevice().getOsv());
        }else {
            device.setOsVersion("4.3.3");
        }
        device.setLanguage(request.getDevice().getLanguage());//系统语言
        device.setModel(request.getDevice().getModel());//设备型号，系统原始值，不要做修改
        device.setBrand(request.getDevice().getMake());//设备品牌
        device.setWidth(request.getDevice().getW());//设备屏幕宽度
        device.setHeight(request.getDevice().getH());//设备屏幕高度
        device.setDensity(Float.parseFloat(String.valueOf(request.getDevice().getDeny())) );//每英寸像素,获取方法
        device.setOrientation(0);//屏幕方向 0：unknown 1：竖屏 2：横屏
        if(null != request.getDevice().getPpi()){
            device.setScreenDpi(request.getDevice().getPpi());//设备屏幕像素密度，如：160
        }else{
            device.setScreenDpi(160);//设备屏幕像素密度，如：160
        }
        device.setScreenSize("5.5");//屏幕尺寸 例:4.7 , 5.5 单位:英寸
        device.setBssid(request.getDevice().getMac());//所连接的 WIFI 设备的 MAC 地址 ,路由器 WIFI 的MAC 地址
        device.setIsSupportDp(true);//是否支持 DP
        yjh.setDevice(device);//

        //Network 对象
        YunJuHeNetwork network = new YunJuHeNetwork();
        network.setIp(request.getDevice().getIp());//客户端 ip 地址，若是服务器请求，必填
        network.setIpv6(request.getDevice().getIpv6());//ipv6 版本，与 ip 一起必须存在一个有效值
        network.setMac(request.getDevice().getMac());//mac 地址
        //网络连接类型
        String connectiontype = request.getDevice().getConnectiontype().toString();
        if("0".equals(connectiontype)){
            network.setNet(0);
        }else if("2".equals(connectiontype)){
            network.setNet(1);
        }else if("4".equals(connectiontype)){
            network.setNet(2);
        }else if("5".equals(connectiontype)){
            network.setNet(3);
        }else if("6".equals(connectiontype)){
            network.setNet(4);
        }else if("7".equals(connectiontype)){
            network.setNet(5);
        } else {
            network.setNet(0);
        }
        //运营商类型
        if(StringUtils.isNotEmpty(request.getDevice().getCarrier())){
            if("70120".equals(request.getDevice().getCarrier())){
                network.setCarrier(1);//中国移动
            }else if("70123".equals(request.getDevice().getCarrier())){
                network.setCarrier(3);//中国联通
            }else if("70121".equals(request.getDevice().getCarrier())){
                network.setCarrier(2);//中国电信
            }
        }else{
            network.setCarrier(0);
        }
        network.setUa(request.getDevice().getUa());//客户端 UA
        network.setCountry(request.getDevice().getCountry());//国家，使用 ISO-3166-1 Alpha-3
        network.setMcc("460");//移动国家码,如:460
        network.setMnc("00");//移动网络码,如:00
        yjh.setNetwork(network);//

        //Imp 对象
        YunJuHeImp imp = new YunJuHeImp();
        imp.setPosId(request.getAdv().getTag_id());//广告位 id
        Integer w = request.getImp().get(0).getNATIVE().getW();
        Integer h = request.getImp().get(0).getNATIVE().getH();
        imp.setWidth(w);//广告位宽度
        imp.setHeight(h);//广告位高度
        List<Integer> typeList = new ArrayList<>();
        typeList.add(0);
        imp.setOpType(typeList);//广告操作类型0：无限制 1:app 下载 2:H5 (在 app 内 webview 打开目标链接) 3:Deeplink 4:电话广告 5：广点通下载广告,6 微信小程序拉起 7.广点通跳转 8.浏览器打开目标链接
        yjh.setImp(imp);



        //总返回
        TzBidResponse bidResponse = new TzBidResponse();
        String content = JSONObject.toJSONString(yjh);
        log.info("请求云聚合广告参数"+JSONObject.parseObject(content));
        String url = "http://union.ad.yunjuhe.cn/Public/ad";
        String ua = request.getDevice().getUa();//ua
        PostUtilDTO pud = new PostUtilDTO();//工具类请求参数
        pud.setUrl(url);//请求路径
        pud.setUa(ua);//ua
        pud.setContent(content);//请求参数
        pud.setHeaderYunJuHe("gzip");//上游名称

        Long startTime = System.currentTimeMillis();// 放在要检测的代码段前，取开始前的时间戳
        String response = HttppostUtil.doJsonPost(pud);
//        String response = "{\"data\":{\"adInfo\":{\"material\":{\"features\":[{\"materialUrl\":\"http://pic5.iqiyipic.com/image/20220323/a9/77/pv_10007008829_em_601.jpg\",\"width\":0,\"type\":2,\"height\":0}],\"type\":2},\"opType\":1,\"appInfo\":{\"package\":\"\",\"size\":0,\"name\":\"庆余年\"},\"name\":\"1:1真实还原庆余年世界，快来探索吧\",\"icons\":[\"http://pic5.iqiyipic.com/image/20220323/db/a9/pv_10007007211_em_601.png\"],\"clickUrls\":[\"http://api-collect.yunjuhe.cn/log?mac=__MAC__&adid=__ADID__&imei=__IMEI__&oaid=__OAID__&idfa=__IDFA__&ts=__TS__&tsms=__TS_MS__&lat=__LATITUDE__&lng=__LONGITUDE__&w=__WIDTH__&h=__HEIGHT__&dx=__DOWN_X__&dy=__DOWN_Y__&ux=__UP_X__&uy=__UP_Y__&pdx=__PNT_DOWN_X__&pdy=__PNT_DOWN_Y__&pux=__PNT_UP_X__&puy=__PNT_UP_Y__&ct=__CLICK_TIME__&ch=48&rid=13s7010892R3sd&pid=6q8u85z2&origin=n%2BqjGx%2Fjg8OZINCbN0m4Ew%3D%3D&action=4&sign=04BF2A6F627D0BC67BB545A446E2C785\",\"http://t7z.cupid.iqiyi.com/dsp_track3?dcafg=0&bbt=1&blt=0&tim=0&obp=1&tr=0.080000&poc=1&wpa=0&wpb=0&acu=1&bln=%E4%B8%8A%E6%B5%B7%E6%95%B0%E9%BE%99%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&bpaoo=66001449908&da=0&mchft=10,1&pmgi=101&mcplid=10&fp=0.002008&tabi=1%3AB%7C1017%3Ab03%7C1018%3Ab03%7C100433%3AE%7C100398%3AB%7C100284%3AB&mvpt=0&sspr=0.879021:-1:0.85:0.879021&imid=0&mctid=2147483649&sbtt=50000&sco=CUPID_OFFSET&scpx=__CLICK_POS_X__&ruiie=0&ass=0.002008&sdt=1&bss=0.002008&mch=17,15&tivc=z0yeAiie1wncGHuoQeJv_A!!&iat=0&iav=0&scpy=__CLICK_POS_Y__&ids=0&iiad=0&iia=0&iirv=-1&ws=5336&qxlu=0&siga=0&dt=0&ac=3383791261&cg=6861639&lt=1&eiv=1&cnty=86330000&odb=A94u5QYA0aLTN5s86qXz5A!!&ardb=fhwumvnO3z0PSNI5sqdqwA!!&rdb=i6KU1gSPGape19gXwu25eA!!&sci=3216&pri=3017&stm=1&opdt=-1&dps=1&z=0&sds=10017000&rsbss=3&acvr=9ItH9U_1vC-VgV0CVqtuqg!!&rtg=D&ox=4l2Wn54aULOhe1pDK5vWBA!!&o=0&h=CUPID_PPID&a=1&acvrb=9ItH9U_1vC-VgV0CVqtuqg!!&sbr=0.210000&actr=rbawD1SCE4ePV2f2yeg0Iw!!&bct=1&og=65000449708&udm=0&lpt=32&x=4l2Wn54aULOhe1pDK5vWBA!!&srt=1&w=66001460558&tu=0&api=CAA%3D&tial=z0yeAiie1wncGHuoQeJv_A!!&q=67003317387&spe=U2xCfhWb59rkVsRnykH85A!!&ptmn=Basis_PCTR_v2-3&pwb=leZk4RO4sPODk74F99glIw!!&rp=leZk4RO4sPODk74F99glIw!!&xwi=CUPID_XWI&uvpt=CUPID_UVPT&scf=0.747168&m=Mozilla%2F5%2E0%20%28iPhone%3B%20CPU%20iPhone%20OS%2014%5F3%20like%20Mac%20OS%20X%29%20AppleWebKit%2F605%2E1%2E15%20%28KHTML%2C%20like%20Gecko%29%20Mobile%2F15E148&smi=1655988206286083&ofp=0.000000&rm=TSCORER_D&pro=8633&actrb=rbawD1SCE4ePV2f2yeg0Iw!!&d=66001449908&sesd=0&v=CAASEFNsQn4Vm_fa5FbEZ8pB-OQaIGNjZTQyODA3MTYzYzcyODUyNmI4Nzg1YmJiZDI5ZWM4&req=13s7010892R3sd&rdi=CAASABgAIAA&edct=1.000000&bm=103&ct=1000000000002&tpt=1&osv=14%2E3&sib=0&e=MTIzLjE1NC4xMDAuNTc&aeg=0&b=1648450807&gd=0&cti=1687553540346114&ps=0&osbz=211&omcf=0.747168&ad=0&bts=1648450135&bz=1&dc=wh&odhs=1&owt=1&cou=86&ssi=MToyMDAuNzU4MTAyOjA7MjoyMDAuNzU4MTAyOjA&pf=32&j=30010&gisp=5&dbt=2&afd=0&bs=1&c=03e638b52b72d45ecb606c715225e1e3&sbsd=2&lc=86330000&dcafrtl=3&osar=T6f7vbQf7l1T6-Hbc4mbeA!!&cr=w5mcOfRn59l6r9ETr4yKfg!!&rbss=2&y=0&dcvi=10230&et=__BROADCAST_SCENE__&ifi=1&tv=2&si=176345461&scte=0&eii=XF0MfFNNvYwZH6ppGgxAn1pNDWy2WngUhJxtMFwO9aRflRnqQnqT29xJVCCCSA_A8E8P29wD0n0XLVHH4ycoTQ8tdUJenmZ0nEpplVHja8yPmEE_7CAGDtJz4BPcITGkyN1fhbDgAUVLE6ElS7vA1l158M7Csy2nzDLumi0XVl9flRnqQnqT29xJVCCCSA_A7HMgdlDPjl_l84Qkp0CG3Q&p=1655988590561536&agt=51100010103&scp=__CLICK_POS_XY__&srai=1000000000660&ai=51200183297&ospe=U2xCfhWb59rkVsRnykH85A!!&agi=51200183297&tfs=2&ocf=0.879021&smaz=fnbiz57h&i=qc_105336_501408&sms=com%2Eyouku%2Ephone&city=863303&aci=10006000&tt=2101508&ae=%7B%22adx%5Fadzone%22%3A%221655988590561536%22%7D&sqe=%7B%22ssp%5Fextra%22%3A%5B%7B%22key%22%3A%22sspums%22%2C%22value%22%3A%221048597%22%7D%5D%7D&sspt=3&gs=2&cft=1648450807263020&crver=0&sia=__IMP_AREA_X1Y1X2Y2__&cp=OWKHJHMFSYyZrUQDwkAu-A!!&odt=101&irst=0&ocs=0&ci=10204&cos=1&ccf=0.747168&crwb=w5mcOfRn59l6r9ETr4yKfg!!&s=2ae18bdb5fccaae8bf85b969fc988ea7&osb=T6f7vbQf7l1T6-Hbc4mbeA!!&wpe=tdeexONUaj2L105oEDramQ!!&ag=0&fc=CUPID_FC&oicp=OWKHJHMFSYyZrUQDwkAu-A!!&orm=ESSM_SCORE_B&ctt=11&pmn=AdNetworkAll_ColdStart_PCVR_v1-11&ms=1&bt=3&oaap=T6f7vbQf7l1T6-Hbc4mbeA!!&eof=1\",\"http://api.bjzghd.com/adx/report/event?param=DD6C587B7F74E0D9AD6415E92AF949169DC3012A714A9B60BDE5CF61BB2A4CE172D80164D64AA0AD&event=width:__WIDTH__height:__DURATION__dx:__DOWN_X__dy:__DOWN_Y__ux:__UP_X__uy:__UP_Y__ts:__BROADCAST_SCENE__\"],\"convUrls\":{\"dlEnd\":[\"http://api-collect.yunjuhe.cn/log?mac=__MAC__&adid=__ADID__&imei=__IMEI__&oaid=__OAID__&idfa=__IDFA__&ts=__TS__&tsms=__TS_MS__&lat=__LATITUDE__&lng=__LONGITUDE__&ch=48&rid=13s7010892R3sd&pid=6q8u85z2&origin=n%2BqjGx%2Fjg8OZINCbN0m4Ew%3D%3D&action=11&sign=E0D38A620DF48A4E7439B96B87B5DAE6\"],\"isBegin\":[\"http://api-collect.yunjuhe.cn/log?mac=__MAC__&adid=__ADID__&imei=__IMEI__&oaid=__OAID__&idfa=__IDFA__&ts=__TS__&tsms=__TS_MS__&lat=__LATITUDE__&lng=__LONGITUDE__&ch=48&rid=13s7010892R3sd&pid=6q8u85z2&origin=n%2BqjGx%2Fjg8OZINCbN0m4Ew%3D%3D&action=12&sign=9F148D8E7CFFF857D57F8314ED479539\"],\"dlBegin\":[\"http://api-collect.yunjuhe.cn/log?mac=__MAC__&adid=__ADID__&imei=__IMEI__&oaid=__OAID__&idfa=__IDFA__&ts=__TS__&tsms=__TS_MS__&lat=__LATITUDE__&lng=__LONGITUDE__&ch=48&rid=13s7010892R3sd&pid=6q8u85z2&origin=n%2BqjGx%2Fjg8OZINCbN0m4Ew%3D%3D&action=10&sign=87BADC5E3164E370EC5C43341450AC5F\"],\"isEnd\":[\"http://api-collect.yunjuhe.cn/log?mac=__MAC__&adid=__ADID__&imei=__IMEI__&oaid=__OAID__&idfa=__IDFA__&ts=__TS__&tsms=__TS_MS__&lat=__LATITUDE__&lng=__LONGITUDE__&ch=48&rid=13s7010892R3sd&pid=6q8u85z2&origin=n%2BqjGx%2Fjg8OZINCbN0m4Ew%3D%3D&action=13&sign=75542A3C11D2DEB5397E20E2AA67BE13\"]},\"desc\":\"\",\"conversion\":{\"h5Url\":\"\",\"deeplinkUrl\":\"\",\"appUrl\":\"https://apps.apple.com/cn/app/id1497840524\"},\"showUrls\":[\"http://api-collect.yunjuhe.cn/log?mac=__MAC__&adid=__ADID__&imei=__IMEI__&oaid=__OAID__&idfa=__IDFA__&ts=__TS__&tsms=__TS_MS__&lat=__LATITUDE__&lng=__LONGITUDE__&st=__SHOW_TIME__&ch=48&rid=13s7010892R3sd&pid=6q8u85z2&origin=n%2BqjGx%2Fjg8OZINCbN0m4Ew%3D%3D&action=3&sign=4F3FAD316E311B4E0073AE187011C143\",\"http://t7z.cupid.iqiyi.com/dsp_track3?dcafg=0&bbt=1&blt=0&tim=0&obp=1&tr=0.080000&poc=1&wpa=0&wpb=0&acu=1&bln=%E4%B8%8A%E6%B5%B7%E6%95%B0%E9%BE%99%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&bpaoo=66001449908&da=0&mchft=10,1&pmgi=101&mcplid=10&fp=0.002008&tabi=1%3AB%7C1017%3Ab03%7C1018%3Ab03%7C100433%3AE%7C100398%3AB%7C100284%3AB&mvpt=0&sspr=0.879021:-1:0.85:0.879021&imid=0&mctid=2147483649&sbtt=50000&sba=__BUTTON_AREA_X1Y1X2Y2__&ruiie=0&ass=0.002008&sdt=1&bss=0.002008&mch=17,15&tivc=z0yeAiie1wncGHuoQeJv_A!!&iat=0&iav=0&ids=0&iiad=0&iia=0&iirv=-1&ws=5336&qxlu=0&siga=0&dt=0&ac=3383791261&cg=6861639&lt=1&eiv=1&cnty=86330000&odb=A94u5QYA0aLTN5s86qXz5A!!&ardb=fhwumvnO3z0PSNI5sqdqwA!!&rdb=i6KU1gSPGape19gXwu25eA!!&sci=3216&pri=3017&opdt=-1&dps=1&z=0&sds=10017000&rsbss=3&acvr=9ItH9U_1vC-VgV0CVqtuqg!!&rtg=D&ox=4l2Wn54aULOhe1pDK5vWBA!!&o=0&h=CUPID_PPID&a=0&acvrb=9ItH9U_1vC-VgV0CVqtuqg!!&sbr=0.210000&actr=rbawD1SCE4ePV2f2yeg0Iw!!&bct=1&og=65000449708&udm=0&lpt=32&x=4l2Wn54aULOhe1pDK5vWBA!!&srt=1&w=66001460558&tu=0&api=CAA%3D&tial=z0yeAiie1wncGHuoQeJv_A!!&q=67003317387&spe=U2xCfhWb59rkVsRnykH85A!!&ptmn=Basis_PCTR_v2-3&pwb=leZk4RO4sPODk74F99glIw!!&rp=leZk4RO4sPODk74F99glIw!!&xwi=CUPID_XWI&uvpt=CUPID_UVPT&scf=0.747168&m=Mozilla%2F5%2E0%20%28iPhone%3B%20CPU%20iPhone%20OS%2014%5F3%20like%20Mac%20OS%20X%29%20AppleWebKit%2F605%2E1%2E15%20%28KHTML%2C%20like%20Gecko%29%20Mobile%2F15E148&smi=1655988206286083&ofp=0.000000&rm=TSCORER_D&pro=8633&actrb=rbawD1SCE4ePV2f2yeg0Iw!!&d=66001449908&sesd=0&v=CAASEFNsQn4Vm_fa5FbEZ8pB-OQaIGNjZTQyODA3MTYzYzcyODUyNmI4Nzg1YmJiZDI5ZWM4&req=13s7010892R3sd&rdi=CAASABgAIAA&edct=1.000000&bm=103&ct=1000000000002&tpt=1&osv=14%2E3&sib=0&e=MTIzLjE1NC4xMDAuNTc&aeg=0&b=1648450807&gd=0&cti=1687553540346114&ps=0&osbz=211&omcf=0.747168&ad=0&bts=1648450135&bz=1&dc=wh&odhs=1&owt=1&cou=86&ssi=MToyMDAuNzU4MTAyOjA7MjoyMDAuNzU4MTAyOjA&pf=32&j=30010&gisp=5&dbt=2&afd=0&bs=1&c=03e638b52b72d45ecb606c715225e1e3&sbsd=2&lc=86330000&dcafrtl=3&osar=T6f7vbQf7l1T6-Hbc4mbeA!!&cr=w5mcOfRn59l6r9ETr4yKfg!!&rbss=2&y=0&dcvi=10230&et=__BROADCAST_SCENE__&ifi=1&tv=2&si=176345461&scte=0&eii=XF0MfFNNvYwZH6ppGgxAn1pNDWy2WngUhJxtMFwO9aRflRnqQnqT29xJVCCCSA_A8E8P29wD0n0XLVHH4ycoTQ8tdUJenmZ0nEpplVHja8yPmEE_7CAGDtJz4BPcITGkyN1fhbDgAUVLE6ElS7vA1l158M7Csy2nzDLumi0XVl9flRnqQnqT29xJVCCCSA_A7HMgdlDPjl_l84Qkp0CG3Q&p=1655988590561536&agt=51100010103&srai=1000000000660&ai=51200183297&ospe=U2xCfhWb59rkVsRnykH85A!!&agi=51200183297&tfs=2&ocf=0.879021&smaz=fnbiz57h&i=qc_105336_501408&sms=com%2Eyouku%2Ephone&city=863303&aci=10006000&tt=2101508&ae=%7B%22adx%5Fadzone%22%3A%221655988590561536%22%7D&sqe=%7B%22ssp%5Fextra%22%3A%5B%7B%22key%22%3A%22sspums%22%2C%22value%22%3A%221048597%22%7D%5D%7D&sspt=3&gs=2&cft=1648450807263020&crver=0&sia=__IMP_AREA_X1Y1X2Y2__&cp=OWKHJHMFSYyZrUQDwkAu-A!!&odt=101&irst=0&ocs=0&ci=10204&cos=1&ccf=0.747168&crwb=w5mcOfRn59l6r9ETr4yKfg!!&s=edf871e3ebff9e451751521cfa3f95bd&osb=T6f7vbQf7l1T6-Hbc4mbeA!!&wpe=tdeexONUaj2L105oEDramQ!!&ag=0&fc=CUPID_FC&oicp=OWKHJHMFSYyZrUQDwkAu-A!!&orm=ESSM_SCORE_B&ctt=11&pmn=AdNetworkAll_ColdStart_PCVR_v1-11&ms=1&bt=3&oaap=T6f7vbQf7l1T6-Hbc4mbeA!!&eof=1\",\"http://api.bjzghd.com/adx/report/event?param=DD6C587B7F74E0D9AD6415E92AF949169DC3012A714A9B60BDE5CF61BB2A4CE1A604121BA23283CD&event=width:__WIDTH__height:__DURATION__dx:__DOWN_X__dy:__DOWN_Y__ux:__UP_X__uy:__UP_Y__ts:__BROADCAST_SCENE__\"]},\"postId\":\"6q8u85z2\",\"rid\":\"13s7010892R3sd\",\"errorMsg\":\"\",\"status\":0},\"errmsg\":\"\",\"error\":0,\"timestamp\":1648450807}\n";
        Long endTime = System.currentTimeMillis();// 放在要检测的代码段前，取结束后的时间戳
        // 计算并打印耗时
        Long tempTime = (endTime - startTime);
        bidResponse.setProcess_time_ms(tempTime);//请求上游花费时间
        log.info("请求上游云聚合广告花费时间："+
                (((tempTime/86400000)>0)?((tempTime/86400000)+"d"):"")+
                ((((tempTime/86400000)>0)||((tempTime%86400000/3600000)>0))?((tempTime%86400000/3600000)+"h"):(""))+
                ((((tempTime/3600000)>0)||((tempTime%3600000/60000)>0))?((tempTime%3600000/60000)+"m"):(""))+
                ((((tempTime/60000)>0)||((tempTime%60000/1000)>0))?((tempTime%60000/1000)+"s"):(""))+
                ((tempTime%1000)+"ms"));
        log.info("云聚合广告返回参数"+JSONObject.parseObject(response));

        List<TzMacros> tzMacros1 = new ArrayList();
        TzMacros tzMacros = new TzMacros();
        List<TzSeat> seatList = new ArrayList<>();
        String id = request.getId();////请求id
        //多层解析json
        JSONObject jo = JSONObject.parseObject(response);

        //code=0的时候成功返回，才做解析
        if (0 == jo.getInteger("error")){
            JSONObject jj = jo.getJSONObject("data");//广告信息
            String errmsg = jo.getString("errmsg");
            if (null!=jj || !"".equals(jj.toJSONString()) || !"".equals(errmsg)){
                String rid = jj.getString("rid");//请求id
                JSONObject adInfo = jj.getJSONObject("adInfo");//广告数据
                if (null!=jj.getInteger("status")&&0==jj.getInteger("status")){
                    List<TzBid> bidList = new ArrayList<>();
                    TzBid tb = new TzBid();
                    //区分信息流和开屏等其他类型广告
                    TzNative tzNative = new TzNative();
                    List<TzImage> list = new ArrayList<>();
                    if (null!=request.getImp()) {
                        for (int j = 0; j < request.getImp().size(); j++) {
                            JSONArray features = adInfo.getJSONObject("material").getJSONArray("features");//素材详情
                            List<Feature> imgs = JSONObject.parseArray(features.toJSONString(), Feature.class);
                            Integer type = adInfo.getJSONObject("material").getInteger("type");//广告素材总体类型:1 视频 2 单图 3 多图 4 html 文本 5 音频
                            TzVideo tzVideo = new TzVideo();//视频素材
                            TzImage tzImage = new TzImage();//图片素材
                            for (int g = 0; g < imgs.size(); g++){
                                Integer imgType = imgs.get(g).getType();//广告素材类型 1.视频 2.图片 3 文本 4 html
                                if (type==2){//单图
                                    if (imgType == 2){

                                        tzImage.setUrl(imgs.get(g).getMaterialUrl());//素材的 URL
                                        tb.setW(imgs.get(g).getWidth());//素材宽
                                        tb.setH(imgs.get(g).getHeight());//素材高
                                        list.add(tzImage);//信息流素材-图片集合
                                    }
                                }else if (type==1){//视频
                                    if (imgType == 1){
                                        tzVideo.setUrl(imgs.get(g).getMaterialUrl());//素材的 URL
                                        tzVideo.setW(imgs.get(g).getVWidth());//素材宽
                                        tzVideo.setH(imgs.get(g).getVHeight());//素材高
                                    }
                                }
                            }
                            if ("4".equals(jj.getInteger("ad_type"))) {//信息流素材-图片集合
                                tzNative.setTitle(adInfo.getString("title"));//广告标题
                                tzNative.setDesc(adInfo.getString("desc"));//广告描述
                                if (type==1){
                                    tzNative.setVideo(tzVideo);
                                }else {
                                    tzNative.setImages(list);
                                }
                                tb.setNATIVE(tzNative);
                                tb.setAd_type(8);//信息流-广告素材类型
                            }else {
                                tb.setAd_type(5);//开屏-广告素材类型
                                tb.setTitle(adInfo.getString("title"));//广告标题
                                tb.setDesc(adInfo.getString("desc"));//广告描述
                                if (type==1){
                                    tb.setVideo(tzVideo);//视频
                                }else {
                                    tb.setImages(list);//图片集合
                                }
                            }
                        }
                    }

                    Integer opType = adInfo.getInteger("opType");//广告操作类型1:app 下载 2:H5(在 app 内 webview 打开目标链接) 3:Deeplink 4:电话广告 5：广点通下载广告,6 微信小程序拉起 7.广点通跳转 8.浏览器打开目标链接
                    if(2 == opType){
                        tb.setClicktype("0");//点击
                    }else if(1 == opType){
                        tb.setClicktype("4");//下载
                    }else if(5 == opType){
                        tb.setClicktype("3");//,二次下载类广告(广点通特有)
                    } else if(3 == opType){
                        tb.setClicktype("2");//拉活-deeplink

                        tb.setDeeplink_url(adInfo.getJSONObject("conversion").getString("deeplinkUrl"));//deeplink 唤醒地址deeplink 唤醒广告打开页面
                        List<String> deep_linkT = new ArrayList<>();
                        deep_linkT.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/deeplink?deeplink=%%DEEP_LINK%%");
                        JSONArray tk_dp_try = adInfo.getJSONObject("convUrls").getJSONArray("dplinkTry");//deeplink被点击(尝试唤起)，如果为空，无需上报
                        JSONArray tk_dp_success = adInfo.getJSONObject("convUrls").getJSONArray("dplink");//deeplink_url 不为空时，唤醒成功时监测
                        JSONArray tk_dp_fail = adInfo.getJSONObject("convUrls").getJSONArray("dplinkFail");//deeplink_url 不为空时，唤醒失败时监测
//                        for (int t = 0; t < tk_dp_try.size(); t++) {
//                            deep_linkT.add(tk_dp_try.get(t).toString());
//                        }
                        for (int dp = 0; dp < tk_dp_success.size(); dp++) {
                            deep_linkT.add(tk_dp_success.get(dp).toString());
                        }
//                        for (int f = 0; f < tk_dp_fail.size(); f++) {
//                            deep_linkT.add(tk_dp_fail.get(f).toString());
//                        }
                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_success_deeplinks(deep_linkT);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DEEP_LINK%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }else{
                        tb.setClicktype("0");//点击
                    }

                    tb.setClick_url(adInfo.getJSONObject("conversion").getString("appUrl")); // 点击跳转url地址

                    //展示监测
                    if(null != adInfo.getJSONArray("showUrls")){
                        List<String> check_views = new ArrayList<>();
                        check_views.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv?pv=%%CHECK_VIEWS%%");
                        JSONArray urls1 = adInfo.getJSONArray("showUrls");
                        for (int cv = 0; cv < urls1.size(); cv++) {
                            String replace = "";
                            if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {//android
                                if (null!=request.getDevice().getImei()){
                                    replace = urls1.get(cv).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__IMEI__", request.getDevice().getImei()).replace("TS",System.currentTimeMillis()+"");
                                }else if (null!=request.getDevice().getOaid()){
                                    replace = urls1.get(cv).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__OAID__", request.getDevice().getOaid()).replace("TS",System.currentTimeMillis()+"");
                                }
                            }else {
                                replace = urls1.get(cv).toString().replace("MAC", request.getDevice().getMac()).replace("__IDFA__",request.getDevice().getIdfa()).replace("TS",System.currentTimeMillis()+"");
                            }

                            check_views.add(replace);
                            check_views.add(urls1.get(cv).toString());
                        }
                        String encode =  id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_views(check_views);//曝光监测URL，支持宏替换 第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_VIEWS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //点击监测
                    if(null != adInfo.getJSONArray("clickUrls")){
                        List<String> clickList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONArray("clickUrls");
                        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks?checkClicks=%%CHECK_CLICKS%%");
                        for (int cc = 0; cc < urls1.size(); cc++) {
                            String replace = "";
                            if("0".equals(os) || "android".equals(os) || "ANDROID".equals(os)) {//android
                                if (null!=request.getDevice().getImei()){
                                    replace = urls1.get(cc).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__IMEI__", request.getDevice().getImei()).replace("TS",System.currentTimeMillis()+"");
                                }else if (null!=request.getDevice().getOaid()){
                                    replace = urls1.get(cc).toString().replace("MAC", request.getDevice().getMac()).replace("__ADID__", request.getDevice().getAndroid_id()).replace("__OAID__", request.getDevice().getOaid()).replace("TS",System.currentTimeMillis()+"");
                                }
                            }else {
                                replace = urls1.get(cc).toString().replace("MAC", request.getDevice().getMac()).replace("__IDFA__",request.getDevice().getIdfa()).replace("TS",System.currentTimeMillis()+"");
                            }

                            clickList.add(replace);
                            clickList.add(urls1.get(cc).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_clicks(clickList);//点击监测URL第三方曝光监测
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%CHECK_CLICKS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //开始下载上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("dlBegin")){
                        List<String> downLoadList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("dlBegin");
                        downLoadList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
                        for (int dl = 0; dl < urls1.size(); dl++) {
                            downLoadList.add(urls1.get(dl).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_start_downloads(downLoadList);//开始下载
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DOWN_LOAD%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //下载完成上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("dlEnd")){
                        List<String> downLoadDList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("dlEnd");
                        downLoadDList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/download/end?downloadEnd=%%DOWN_END%%");
                        for (int dle = 0; dle < urls1.size(); dle++) {
                            downLoadDList.add(urls1.get(dle).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_end_downloads(downLoadDList);//结束下载
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%DOWN_END%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //开始安装上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("isBegin")){
                        List<String> installList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("isBegin");
                        installList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/start?installStart=%%INSTALL_START%%");
                        for (int ins = 0; ins < urls1.size(); ins++) {
                            installList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_start_installs(installList);//开始安装
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%INSTALL_START%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //安装完成上报数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("isEnd")){
                        List<String> installEList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("isEnd");
                        installEList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedbac/install/end?installEnd=%%INSTALL_SUCCESS%%");
                        for (int ins = 0; ins < urls1.size(); ins++) {
                            installEList.add(urls1.get(ins).toString().replace("__CLICK_ID__","%%CLICK_ID%%"));
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        tb.setCheck_end_installs(installEList);//安装完成
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%INSTALL_SUCCESS%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //视频开始播放追踪 url 数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("pyBegin")){
                        List<String> voidStartList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("pyBegin");
                        voidStartList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/start?vedioStart=%%VEDIO_START%%");
                        for (int vs = 0; vs < urls1.size(); vs++) {
                            voidStartList.add(urls1.get(vs).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        //    tb.setCheck_views(voidStartList);//视频开始播放
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%VEDIO_START%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    //视频播放完成追踪 url 数组
                    if(null != adInfo.getJSONObject("convUrls").getJSONArray("pyEnd")){
                        List<String> voidEndList = new ArrayList<>();
                        JSONArray urls1 =  adInfo.getJSONObject("convUrls").getJSONArray("pyEnd");
                        voidEndList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/vedio/end?vedioEnd=%%VEDIO_END%%");
                        for (int ve = 0; ve < urls1.size(); ve++) {
                            voidEndList.add(urls1.get(ve).toString());
                        }
                        String encode = id + "," + request.getAdv().getPrice() + "," + request.getDevice().getIp() + ","+ request.getTzkafka().getPublish_id() + "," + request.getTzkafka().getMedia_id() + "," + request.getTzkafka().getPos_id() + "," + request.getTzkafka().getSlot_type() + "," + request.getTzkafka().getDsp_id() + "," + request.getTzkafka().getDsp_media_id() + "," + request.getTzkafka().getDsp_pos_id() + "," + request.getAdv().getApp_name();
                        //    tb.setCheck_clicks(voidEndList);//视频播放结束
                        tzMacros = new TzMacros();
                        tzMacros.setMacro("%%VEDIO_END%%");
                        tzMacros.setValue(Base64.encode(encode));
                        tzMacros1.add(tzMacros);
                    }

                    tb.setMacros(tzMacros1);
                    tb.setImpid(request.getImp().get(0).getId());
                    bidList.add(tb);

                    TzSeat seat = new TzSeat();//素材集合对象
                    seat.setBid(bidList);
                    seatList.add(seat);

                    bidResponse.setId(id);//请求id
                    bidResponse.setBidid(jo.getString("unique"));//广告主返回id 请求唯一标识符
                    bidResponse.setSeatbid(seatList);//广告集合对象
                    bidResponse.setProcess_time_ms(tempTime);//请求上游消耗时间
                    log.info("云聚合广告总返回"+JSONObject.toJSONString(bidResponse));
                }
            }

        }

        return bidResponse;
    }
}
