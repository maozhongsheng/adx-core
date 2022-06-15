package com.mk.adx.service.impl;

import com.mk.adx.entity.json.request.tz.TzBidRequest;
import com.mk.adx.entity.json.request.tz.TzDevice;
import com.mk.adx.entity.json.request.tz.TzImp;
import com.mk.adx.entity.json.request.tzyoloho.YolohoBidRequest;
import com.mk.adx.entity.json.response.tz.TzBid;
import com.mk.adx.entity.json.response.tz.TzBidResponse;
import com.mk.adx.entity.json.response.tz.TzImage;
import com.mk.adx.entity.json.response.tz.TzMacros;
import com.mk.adx.entity.json.response.yoloho.YolohoAdms;
import com.mk.adx.entity.json.response.yoloho.YolohoBid;
import com.mk.adx.entity.json.response.yoloho.YolohoBidRespones;
import com.mk.adx.service.YolohoJsonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author mzs
 * @Description
 * @date 2022/5/9 15:21
 */
@Slf4j
@Service("yolohoJsonService")
public class YolohoJsonServiceImpl implements YolohoJsonService {

    private static final String name = "大姨妈";

    private static final String source = "大姨妈";

    @SneakyThrows
    @Override
    public YolohoBidRespones getYolohoResponse(TzBidResponse response) {
        YolohoBidRespones yolohoBidRespones = new YolohoBidRespones();
        List<YolohoAdms> adms = new ArrayList();
        List<YolohoBid> bids = new ArrayList();
        YolohoBid bid = new YolohoBid();
        List<TzBid> res = response.getSeatbid().get(0).getBid();
        for (int i = 0; i < res.size(); i++) {
            YolohoAdms adm = new YolohoAdms();
            TzBid tzBid = res.get(i);
            Integer ad_type = tzBid.getAd_type();
            if(8 == ad_type){
                adm.setTitle(tzBid.getNATIVE().getTitle());
                adm.setDesc(tzBid.getNATIVE().getDesc());
                List<TzImage> images = tzBid.getNATIVE().getImages();
                List<String> yimages = new ArrayList();
                for (int m = 0; m < images.size(); m++){
                    yimages.add(images.get(m).getUrl());
                }
                adm.setImages(yimages);
                adm.setImageHeight(images.get(0).getH());
                adm.setImageWidth(images.get(0).getW());
                adm.setIcon_image(tzBid.getNATIVE().getIcon().getUrl());
                adm.setStyle_type(1);
            } else if (5 == ad_type) {
                if(null != tzBid.getVideo()){
                    adm.setImageHeight(tzBid.getVideo().getH());
                    adm.setImageWidth(tzBid.getVideo().getW());
                    adm.setDuration(tzBid.getVideo().getDuration());
                    adm.setVideo_url(tzBid.getVideo().getUrl());
                    adm.setIcon_image(tzBid.getAic());
                    adm.setStyle_type(5);
                }else{
                    adm.setTitle(tzBid.getTitle());
                    adm.setDesc(tzBid.getDesc());
                    List<TzImage> images = tzBid.getImages();
                    List<String> yimages = new ArrayList();
                    for (int m = 0; m < images.size(); m++){
                        yimages.add(images.get(m).getUrl());
                    }
                    adm.setImages(yimages);
                    adm.setImageHeight(images.get(0).getH());
                    adm.setImageWidth(images.get(0).getW());
                    adm.setIcon_image(tzBid.getAic());
                    adm.setStyle_type(6);
                }
            }else{
                adm.setTitle(tzBid.getTitle());
                adm.setDesc(tzBid.getDesc());
                List<TzImage> images = tzBid.getImages();
                List<String> yimages = new ArrayList();
                for (int m = 0; m < images.size(); m++){
                    yimages.add(images.get(m).getUrl());
                }
                adm.setImages(yimages);
                adm.setImageHeight(images.get(0).getH());
                adm.setImageWidth(images.get(0).getW());
                adm.setIcon_image(tzBid.getAic());
                adm.setStyle_type(6);
            }


            adm.setClick_url(tzBid.getClick_url());
            if(StringUtils.isNotEmpty(tzBid.getDownload_url())){
                adm.setDownloadUrl(tzBid.getDownload_url());
            }else{
                adm.setDownloadUrl(tzBid.getClick_url());
            }
            if(StringUtils.isNotEmpty(tzBid.getDeeplink_url())){
                adm.setDpl_url(tzBid.getDeeplink_url());
            }
            String clicktype = tzBid.getClicktype();
            if("0".equals(clicktype) || "1".equals(clicktype)){
                adm.setAd_type(0);
            } else if ("2".equals(clicktype)) {
                adm.setAd_type(2);
            } else if ("4".equals(clicktype)) {
                adm.setAd_type(1);
            }
            if(null != tzBid.getApp()){
                adm.setApp_name(tzBid.getApp().getApp_name());
                adm.setPackage_name(tzBid.getApp().getBundle());
            }

            //曝光
            List<String> check_views = tzBid.getCheck_views();
            List<String> imptr = new ArrayList();
            List<TzMacros> macros = tzBid.getMacros();
            if(null != check_views && 0 < check_views.size()){
                String mac = "";
                for (int k = 0; k < macros.size(); k++) {
                    if("%%CHECK_VIEWS%%".equals(macros.get(k).getMacro())){
                        mac = macros.get(k).getValue();
                    }
                }
                for (int j = 0; j < check_views.size(); j++) {
                    String replace = check_views.get(j).replace("%%CHECK_VIEWS%%", mac);
                    imptr.add(replace);
                }
                adm.setImp_trackers(imptr);
            }


            //点击
            List<String> check_clicks = tzBid.getCheck_clicks();
            List<String> clktr = new ArrayList();
            if(null != check_clicks &&  0 < check_clicks.size()){
                String mac = "";
                for (int k = 0; k < macros.size(); k++) {
                    if("%%CHECK_CLICKS%%".equals(macros.get(k).getMacro())){
                        mac = macros.get(k).getValue();
                    }
                }
                for (int j = 0; j < check_clicks.size(); j++) {
                    String replace = check_clicks.get(j).replace("%%CHECK_CLICKS%%", mac);
                    clktr.add(replace);
                }
                adm.setClk_trackers(clktr);
            }

            //下载开始
            List<String> check_start_downloads = tzBid.getCheck_start_downloads();
            List<String> dowtr = new ArrayList();
            if(null != check_start_downloads && 0 < check_start_downloads.size()){
                String mac = "";
                for (int k = 0; k < macros.size(); k++) {
                    if("%%DOWN_LOAD%%".equals(macros.get(k).getMacro())){
                        mac = macros.get(k).getValue();
                    }
                }
                for (int j = 0; j < check_start_downloads.size(); j++) {
                    String replace = check_start_downloads.get(j).replace("%%DOWN_LOAD%%", mac);
                    dowtr.add(replace);
                }
                adm.setDownload_trackers(dowtr);
            }


            //下载结束
            List<String> check_end_downloads = tzBid.getCheck_end_downloads();
            List<String> dowtend = new ArrayList();
            if(null != check_end_downloads && 0 < check_end_downloads.size()){
                String mac = "";
                for (int k = 0; k < macros.size(); k++) {
                    if("%%DOWN_END%%".equals(macros.get(k).getMacro())){
                        mac = macros.get(k).getValue();
                    }
                }
                for (int j = 0; j < check_end_downloads.size(); j++) {
                    String replace = check_end_downloads.get(j).replace("%%DOWN_END%%", mac);
                    dowtend.add(replace);
                }
                adm.setDownloaded_trackers(dowtend);
            }

            //安装结束
            List<String> check_end_installs = tzBid.getCheck_end_installs();
            List<String> instra = new ArrayList();
            if(null != check_end_installs && 0 < check_end_installs.size()){
                String mac = "";
                for (int k = 0; k < macros.size(); k++) {
                    if("%%INSTALL_SUCCESS%%".equals(macros.get(k).getMacro())){
                        mac = macros.get(k).getValue();
                    }
                }
                for (int j = 0; j < check_end_installs.size(); j++) {
                    String replace = check_end_installs.get(j).replace("%%INSTALL_SUCCESS%%", mac);
                    instra.add(replace);
                }
                adm.setInstalled_trackers(instra);
            }
            if(null == tzBid.getCheck_success_deeplinks()){
                //成功唤醒
                List<String> check_success_deeplinks = tzBid.getCheck_success_deeplinks();
                List<String> dptr = new ArrayList();
                if(null != check_success_deeplinks && 0 < check_success_deeplinks.size()){
                    String mac = "";
                    for (int k = 0; k < macros.size(); k++) {
                        if("%%DEEP_LINK%%".equals(macros.get(k).getMacro())){
                            mac = macros.get(k).getValue();
                        }
                    }
                    for (int j = 0; j < check_success_deeplinks.size(); j++) {
                        String replace = check_success_deeplinks.get(j).replace("%%DEEP_LINK%%", mac);
                        dptr.add(replace);
                    }
                    adm.setDp_trackers(dptr);
                }
            }

            adms.add(adm);
        }
        bid.setId(response.getId());
        bid.setAdid(response.getId());
        bid.setImpid(response.getSeatbid().get(0).getBid().get(0).getImpid());
        bid.setAdms(adms);
        bids.add(bid);
        yolohoBidRespones.setBids(bids);
        yolohoBidRespones.setReqid(response.getId());
        yolohoBidRespones.setBidid(response.getBidid());
        return yolohoBidRespones;
    }

    @Override
    public TzBidRequest getTzRequest(YolohoBidRequest request) {
        TzBidRequest tzBidRequest = new TzBidRequest();
        List<TzImp> imps = new ArrayList();
        TzDevice device = new TzDevice();
        TzImp imp = new TzImp();
        //imp
        imp.setId(request.getImps().get(0).getImpid());
        String spaceid = request.getImps().get(0).getNative_ad().getSpaceid();
        if("8fbfe42d3c21411d".equals(spaceid)){
            imp.setTagid("1000000782"); //ios开屏
            imp.setAd_slot_type("3");
        }else if("3d124b48a1204aea".equals(spaceid)){
            imp.setTagid("1000000783"); //ios信息流
            imp.setAd_slot_type("1");
        }else if("c9331fce59c6453a".equals(spaceid)){
            imp.setTagid("1000000784"); //Android开屏
            imp.setAd_slot_type("3");
        }else if("72e2bf385c8f4376".equals(spaceid)){
            imp.setTagid("1000000785"); //Android信息流
            imp.setAd_slot_type("1");
        }
        //device
        device.setW(request.getDevice().getW());
        device.setH(request.getDevice().getH());
        device.setUa(request.getDevice().getUa());
        device.setIp(request.getDevice().getIp());
        device.setImei(request.getDevice().getDid());
        device.setImei_md5(request.getDevice().getDidmd5());
        device.setAndroid_id(request.getDevice().getDpid());
        device.setAndroid_id_md5(request.getDevice().getDpidmd5());
        device.setOaid(request.getDevice().getOaid());
        device.setMac(request.getDevice().getMac());
        device.setMac_md5(request.getDevice().getMacmd5());
        device.setIdfa(request.getDevice().getIfa());
        device.setIdfa_md5(request.getDevice().getIfamd5());
        device.setMake(request.getDevice().getMake());
        device.setModel(request.getDevice().getModel());
        device.setOs(request.getDevice().getOs().toLowerCase(Locale.ROOT));
        device.setOsv(request.getDevice().getOsv());
        String carrier = request.getDevice().getCarrier();
        if("46000".equals(carrier)){
            device.setCarrier("70120");
        }else if("46001".equals(carrier)){
            device.setCarrier("70123");
        }else if("46003".equals(carrier)){
            device.setCarrier("70121");
        }else{
            device.setCarrier("70123");
        }
        device.setLanguage(request.getDevice().getLanguage());
        device.setBootTimeSec(request.getDevice().getBootMark());
        device.setOsUpdateTimeSec(request.getDevice().getUpdateMark());
        device.setPpi(request.getDevice().getPpi());
        int connectiontype = request.getDevice().getConnectiontype();
        if(0 == connectiontype){
            device.setConnectiontype(0);
        } else if (1 == connectiontype) {
            device.setConnectiontype(1);
        } else if (2 == connectiontype) {
            device.setConnectiontype(2);
        } else if (3 == connectiontype) {
            device.setConnectiontype(4);
        } else if (4 == connectiontype) {
            device.setConnectiontype(5);
        } else if (5 == connectiontype) {
            device.setConnectiontype(6);
        } else {
            device.setConnectiontype(0);
        }
        int devicetype = request.getDevice().getDevicetype();
        if(0 == devicetype){
            device.setDevicetype("phone");
        }else if (1 == devicetype){
            device.setDevicetype("ipad");
        }


        imps.add(imp);
        tzBidRequest.setId(request.getReqid());
        tzBidRequest.setImp(imps);
        tzBidRequest.setDevice(device);

        return tzBidRequest;

    }
}
