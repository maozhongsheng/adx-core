package com.mk.adx.config;

import com.mk.adx.entity.json.request.mk.MkBidRequest;
import com.mk.adx.entity.json.response.mk.MkBid;
import com.mk.adx.entity.json.response.mk.MkBidResponse;
import com.mk.adx.entity.json.response.mk.MkImage;
import com.mk.adx.util.Base64;
import com.mk.adx.util.MD5Util;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;

/**
 * 测试请求返回数据
 *
 * @author mzs
 * @version 1.0
 * @date 2021/8/4 16:33
 */
@Slf4j
public class MkResponseTestConfig {

    /**
     * 信息流
     */
    public static final MkBidResponse XxlbidResponseTest (MkBidRequest request) {
        MkBidResponse mkBidResponse = new MkBidResponse();
        List<MkBid> bids = new ArrayList();
        MkBid bid = new MkBid();
        MkImage image = new MkImage();
        List<MkImage> images = new ArrayList();
        bid.setPrice(1);
        bid.setAdid(request.getImp().get(0).getTagid());
        bid.setTitle("信息流测试标题");
        bid.setDesc("信息流测试描述");
        image.setUrl("http://oimagec7.ydstatic.com/image?id=-3617364665137034073&product=adpublish&w=1280&h=720&sc=0&rm=2&gsb=0&gsbd=60&sc=0");
        image.setH(720);
        image.setW(1280);
        images.add(image);
        bid.setImages(images);
        bid.setClick_url("http://cpro.baidu.com/cpro/ui/uijs.php?en=mywWUA71T1Yknzu9TZKxpyfqn1m3nWRdnW6srau9TZKxTv-b5ywBPvmYmhR3FhFbpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnauWpjYsFhPdpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnaubpHYdnWfzrj6hpyd-pHYYPhmkuWIhPWP-uAR3uj0dPj01nWw9n1RLnyNbnhuWuauGUyNGgvPs5HchpgPxmgKs5HDhph_qn164PvfYmW9WPjmdrjwBPBuo5iNawBNafzNDniNDnAdsmyPWFRndFRRsFRfkFRcdFRFaFRuKFRc4FRc4FhkdpvbqniuVmLKV5HD4Pj0kFhq15y78uZFEpyfhTHdbmWIhPAF-r7qWTZchThcqniuzT1YkFMPbpdqv5HfhTvwogLu-TMPGUv3qPi3vQW0hTvN_UANzgv-b5HchTv-b5ycvrjfvPynYmW9bPjbLn16hTLwGujYvnj0kFMfqIZKWUA-WpvNbndqVUvFxmgPsg1nhIAYqnWTdPHc4Pj03FMwVT1YkPWf1n1RLrjn4FMwd5HT3njfdP16hIHdCIZwsFHPKFHFAFHFAPAFbmWw9FHFDFHFDuAcLuWwBuH6-nbNWUvYhIWYzFhbqnjIbrjc3ryc&action_type=16&adx=1&besl=-1&br=12&c=news&cf=1&cp=fc_middle_page_app&cvrq=19748&ds=fmp&eid_list=1022_201014_203568_203752_205833_206556_207279_208396_209006_209176_209479&expid=200408_201008_201014_201415_201708_203000_203568_203752_205833_206232_206556_208396_209006&fr=33&fv=0&h=720&haacp=156&iad=0&iad_ex=0&iif=1&img_typ=20578&itm=0&lu_idc=tc&lukid=1&lus=b68465c4b8d49738&lust=61f3a68f&luwtr=3474019357187637248&mscf=0&mtids=3000003182&n=10&nttp=3&oi=25&p=baidu&sce=5&sh=2034&sr=464&ssp2=0&sw=1080&swi=4&tpl=template_inlay_all_mobile_lu_native_ad_app_feed&tsf=dtp:2&tt_sign=mpacc%C5%E0%D1%B5%B8%A8%B5%BC&tt_src=0&u=&uicf=lurecv&urlid=0&vn=8103&w=1280&wi=4&eot=1");
        List<String> checkViews = new ArrayList<>();
        checkViews.add("http://adx.fxlxz.com/sl/pv?pv=MSwwLDIxOC42OS41MC43MCwxLDEzMjIsMTAwMDAzLDEsMjAyMTAwMDAxNCxkYjdmNGJlOCw3NzcwNDM4LOWQjOeoi+aXheihjA==");
        bid.setCheck_views(checkViews);
        List<String> clickList = new ArrayList<>();
        clickList.add("http://adx.fxlxz.com/sl/click?click=MSwwLDIxOC42OS41MC43MCwxLDEzMjIsMTAwMDAzLDEsMjAyMTAwMDAxNCxkYjdmNGJlOCw3NzcwNDM4LOWQjOeoi+aXheihjA==");
        bid.setCheck_clicks(clickList);
        bid.setClicktype("0");
        bid.setSource("测试");
        bids.add(bid);
        mkBidResponse.setSeatbid(bids);
        mkBidResponse.setId(request.getId());
        mkBidResponse.setBidid(request.getId());
        mkBidResponse.setProcess_time_ms(System.currentTimeMillis());
        log.info(request.getId()+":此返回为测试返回");
        return  mkBidResponse;
    }



    /**
     * 开屏
     */
    public static final MkBidResponse KpbidResponseTest (MkBidRequest request) {
        MkBidResponse mkBidResponse = new MkBidResponse();
        List<MkBid> bids = new ArrayList();
        MkBid bid = new MkBid();
        MkImage image = new MkImage();
        List<MkImage> images = new ArrayList();
        bid.setPrice(1);
        bid.setAdid(request.getImp().get(0).getTagid());
        bid.setTitle("开屏测试标题");
        bid.setDesc("开屏测试描述");
        image.setUrl("http://oimagec7.ydstatic.com/image?id=-3617364665137034073&product=adpublish&w=1280&h=720&sc=0&rm=2&gsb=0&gsbd=60&sc=0");
        image.setH(1280);
        image.setW(720);
        images.add(image);
        bid.setImages(images);
        bid.setClick_url("http://cpro.baidu.com/cpro/ui/uijs.php?en=mywWUA71T1Yknzu9TZKxpyfqn1m3nWRdnW6srau9TZKxTv-b5ywBPvmYmhR3FhFbpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnauWpjYsFhPdpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnaubpHYdnWfzrj6hpyd-pHYYPhmkuWIhPWP-uAR3uj0dPj01nWw9n1RLnyNbnhuWuauGUyNGgvPs5HchpgPxmgKs5HDhph_qn164PvfYmW9WPjmdrjwBPBuo5iNawBNafzNDniNDnAdsmyPWFRndFRRsFRfkFRcdFRFaFRuKFRc4FRc4FhkdpvbqniuVmLKV5HD4Pj0kFhq15y78uZFEpyfhTHdbmWIhPAF-r7qWTZchThcqniuzT1YkFMPbpdqv5HfhTvwogLu-TMPGUv3qPi3vQW0hTvN_UANzgv-b5HchTv-b5ycvrjfvPynYmW9bPjbLn16hTLwGujYvnj0kFMfqIZKWUA-WpvNbndqVUvFxmgPsg1nhIAYqnWTdPHc4Pj03FMwVT1YkPWf1n1RLrjn4FMwd5HT3njfdP16hIHdCIZwsFHPKFHFAFHFAPAFbmWw9FHFDFHFDuAcLuWwBuH6-nbNWUvYhIWYzFhbqnjIbrjc3ryc&action_type=16&adx=1&besl=-1&br=12&c=news&cf=1&cp=fc_middle_page_app&cvrq=19748&ds=fmp&eid_list=1022_201014_203568_203752_205833_206556_207279_208396_209006_209176_209479&expid=200408_201008_201014_201415_201708_203000_203568_203752_205833_206232_206556_208396_209006&fr=33&fv=0&h=720&haacp=156&iad=0&iad_ex=0&iif=1&img_typ=20578&itm=0&lu_idc=tc&lukid=1&lus=b68465c4b8d49738&lust=61f3a68f&luwtr=3474019357187637248&mscf=0&mtids=3000003182&n=10&nttp=3&oi=25&p=baidu&sce=5&sh=2034&sr=464&ssp2=0&sw=1080&swi=4&tpl=template_inlay_all_mobile_lu_native_ad_app_feed&tsf=dtp:2&tt_sign=mpacc%C5%E0%D1%B5%B8%A8%B5%BC&tt_src=0&u=&uicf=lurecv&urlid=0&vn=8103&w=1280&wi=4&eot=1");
        List<String> checkViews = new ArrayList<>();
        checkViews.add("http://adx.fxlxz.com/sl/pv?pv=MSwwLDIxOC42OS41MC43MCwxLDEzMjIsMTAwMDAxLDEsMjAyMTAwMDAxNCxkYjdmNGJlOCw3NzcwNDM4LOWQjOeoi+aXheihjA==");
        bid.setCheck_views(checkViews);
        List<String> clickList = new ArrayList<>();
        clickList.add("http://adx.fxlxz.com/sl/click?click=MSwwLDIxOC42OS41MC43MCwxLDEzMjIsMTAwMDAxLDEsMjAyMTAwMDAxNCxkYjdmNGJlOCw3NzcwNDM4LOWQjOeoi+aXheihjA==");
        bid.setCheck_clicks(clickList);
        bid.setClicktype("0");
        bid.setSource("测试");
        bids.add(bid);
        mkBidResponse.setSeatbid(bids);
        mkBidResponse.setId(request.getId());
        mkBidResponse.setBidid(request.getId());
        mkBidResponse.setProcess_time_ms(System.currentTimeMillis());
        log.info(request.getId()+":此返回为测试返回");
        return  mkBidResponse;
    }

}
