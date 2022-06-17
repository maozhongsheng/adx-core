//package com.mk.adx.config;
//
//import com.mk.adx.entity.json.request.tz.TzBidRequest;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 测试请求返回数据
// *
// * @author mzs
// * @version 1.0
// * @date 2021/8/4 16:33
// */
//@Slf4j
//public class TzResponseTestConfig {
//
//    /**
//     * 信息流
//     */
//    public static final TzBidResponse XxlbidResponseTest (TzBidRequest request) throws UnsupportedEncodingException {
//        List<TzSeat> seatList = new ArrayList<>();
//        TzSeat seat = new TzSeat();
//        List<TzBid> bidList = new ArrayList<>();
//        TzBid bid = new TzBid();
//        TzBidResponse tzBidResponse = new TzBidResponse();
//        List<TzMacros> tzMacros1 = new ArrayList();
//        TzMacros tzMacros = new TzMacros();
//        TzNative tzNative = new TzNative();
//        List<TzImage> image = new ArrayList();
//        TzImage tzImage = new TzImage();
//        TzIcon tzIcon = new TzIcon();
//
//
//        bid.setAd_type(8);
//        bid.setAdid("25252316");
//        bid.setTitle("信息流大图样式");
//      //  bid.setClick_url("https://zhilianghui.oss-cn-beijing.aliyuncs.com/apk/tz-app-release_117_guanwang.apk");
//        bid.setClick_url("http://cpro.baidu.com/cpro/ui/uijs.php?en=mywWUA71T1Yknzu9TZKxpyfqn1m3nWRdnW6srau9TZKxTv-b5ywBPvmYmhR3FhFbpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnauWpjYsFhPdpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnaubpHYdnWfzrj6hpyd-pHYYPhmkuWIhPWP-uAR3uj0dPj01nWw9n1RLnyNbnhuWuauGUyNGgvPs5HchpgPxmgKs5HDhph_qn164PvfYmW9WPjmdrjwBPBuo5iNawBNafzNDniNDnAdsmyPWFRndFRRsFRfkFRcdFRFaFRuKFRc4FRc4FhkdpvbqniuVmLKV5HD4Pj0kFhq15y78uZFEpyfhTHdbmWIhPAF-r7qWTZchThcqniuzT1YkFMPbpdqv5HfhTvwogLu-TMPGUv3qPi3vQW0hTvN_UANzgv-b5HchTv-b5ycvrjfvPynYmW9bPjbLn16hTLwGujYvnj0kFMfqIZKWUA-WpvNbndqVUvFxmgPsg1nhIAYqnWTdPHc4Pj03FMwVT1YkPWf1n1RLrjn4FMwd5HT3njfdP16hIHdCIZwsFHPKFHFAFHFAPAFbmWw9FHFDFHFDuAcLuWwBuH6-nbNWUvYhIWYzFhbqnjIbrjc3ryc&action_type=16&adx=1&besl=-1&br=12&c=news&cf=1&cp=fc_middle_page_app&cvrq=19748&ds=fmp&eid_list=1022_201014_203568_203752_205833_206556_207279_208396_209006_209176_209479&expid=200408_201008_201014_201415_201708_203000_203568_203752_205833_206232_206556_208396_209006&fr=33&fv=0&h=720&haacp=156&iad=0&iad_ex=0&iif=1&img_typ=20578&itm=0&lu_idc=tc&lukid=1&lus=b68465c4b8d49738&lust=61f3a68f&luwtr=3474019357187637248&mscf=0&mtids=3000003182&n=10&nttp=3&oi=25&p=baidu&sce=5&sh=2034&sr=464&ssp2=0&sw=1080&swi=4&tpl=template_inlay_all_mobile_lu_native_ad_app_feed&tsf=dtp:2&tt_sign=mpacc%C5%E0%D1%B5%B8%A8%B5%BC&tt_src=0&u=&uicf=lurecv&urlid=0&vn=8103&w=1280&wi=4&eot=1");
//
//       // bid.setDeeplink_url("openapp.jdmobile://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_f734cef4afba45d5bbde46f789546704");
//
//        List<String> checkViews = new ArrayList<>();
//        checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv/test?pv=%%CHECK_VIEWS%%");
//
//        bid.setCheck_views(checkViews);
//        List<String> clickList = new ArrayList<>();
//        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks/test?checkClicks=%%CHECK_CLICKS%%");
//        bid.setCheck_clicks(clickList);
//        bid.setClicktype("0");
//
////       bid.setPrice(550);
//
//        TzBidApps tzBidApps = new TzBidApps();
//        tzBidApps.setBundle("com.zslm.xishuashua");
//        tzBidApps.setApp_name("喜刷刷");
//        tzBidApps.setApp_icon("https://tz-1305375713.cos.ap-beijing.myqcloud.com/tzIcoMaterial/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20220105183627.png");
//        bid.setApp(tzBidApps);
//        List<String> clickList2 = new ArrayList<>();
//        clickList2.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start/test?downloadStart=%%DOWN_LOAD%%");
//        clickList2.add("http://xss-test.zhilianghui.com:8081/api/download/report");
//        bid.setCheck_start_downloads(clickList2);
//        tzMacros = new TzMacros();
////        bid.setNurl("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/winPrice/test?price=aHR0cDovL2x4LWNoYXJnZS55aWRpYW56aXh1bi5jb20vb3Blbi93aW5fbm90aWNlP2V4dD1DR01TSkdVeE9XWmpNbUl5TFdSbE1qY3ROR1JpTXkwNVltTmpMV1ZoTjJVd01UYzFZVEpqTkJnQklBRW9BVEQ5NGRPdHVTODRfdl9fX19fX19fX19BVWdEVVBfX19fX19fX19fX3dGWUFtQ0FDR2lnd2g1d0FYaWd3aDZJQWJEakxaZ0JBcUFCQXJJQkhHaDBkSEJ6T2k4dmQzZDNMbmxwWkdsaGJucHBlSFZ1TG1OdmJTLTZBUVIwWlhOMHdnRTFjSEp2ZG1sdVkyVTlNekk3SUhCeWIzWnBibU5sVG1GdFpUMHpNekF3TURBN0lHTnBkSGs5TURzZ1kybDBlVTVoYldVOU95REtBUWQxYm10dWIzZHUwZ0VIZFc1cmJtOTNidklCRWhvUU9HSTFNamhsWkdNME56ZGlZVFl6WlBvQkJIZHBabW1LQW5ZS0RqSXlNeTR4TURRdU1UWXdMams1R2lCRU5ERkVPRU5FT1RoR01EQkNNakEwUlRrNE1EQTVPVGhGUTBZNE5ESTNSVklnUkRReFJEaERSRGs0UmpBd1FqSXdORVU1T0RBd09UazRSVU5HT0RReU4wVmFJREZrTmpWbVpUQTRZV1EzT1dNeVpHRTBNalF4TnpRMU5EQXhPR1ptTTJVMmtBSUJvQUtnd2g2eUFoVUtCM1Z1YTI1dmQyNFNCM1Z1YTI1dmQyNGFBVEhDQXAwQlRXOTZhV3hzWVM4MUxqQWdLRXhwYm5WNE95QkJibVJ5YjJsa0lERXdPeUJXTWpBeE1VRWdRblZwYkdRdlVWQXhRUzR4T1RBM01URXVNREl3T3lCM2Rpa2dRWEJ3YkdWWFpXSkxhWFF2TlRNM0xqTTJJQ2hMU0ZSTlRDQWdiR2xyWlNCSFpXTnJieWtnVm1WeWMybHZiaTgwTGpBZ1EyaHliMjFsTHpjNExqQXVNemt3TkM0NU5pQk5iMkpwYkdVZ1UyRm1ZWEpwTHpVek55NHpOc29DQ0dWNFkyaGhibWRsMGdJS2MzTndYM1JsYzNSZk1kb0NDR1Y0WTJoaGJtZGw0Z0lTWVhCd1gyeGhkVzVqYUY5emRXTmpaWE56eUFNQmtBU2d3aDZZQktEQ0hxQUVBYkFFQWc9PcKvbz12MiZhbXA7cHJpY2U9MCwxMjM0NTY3OCwxLjEuMS4yLOS4gOeCueWSqOivoizmtYvor5VBcHA=&winNotice=%%WIN_PRICE%%");
////
////        List<String> check_start_downloads = new ArrayList<>();
////        check_start_downloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start/test?downloadStart=%%DOWN_LOAD%%");
////
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%WIN_PRICE%%");
////        tzMacros.setValue("RDBENjJEQzY1NDZDRDJGMjBFRUY4Mzk2NUMwNUYwNzc%3D");
////        tzMacros1.add(tzMacros);
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%CLICK_URL%%");
////        tzMacros.setValue("aHR0cHM6Ly93d3cueWlkaWFueml4dW4uY29tLywxNjMwMzA0MjY4NDk2LDM5LjEzMC4zNS44MCzkuIDngrnotYTorq8s5rWL6K+VQXBw");
////        tzMacros1.add(tzMacros);
////
//        tzMacros.setMacro("%%DOWN_LOAD%%");
//        tzMacros.setValue("MzY0MTkyNzQ1MTM4NDM4MTk1LDAsMjE4LjY5LjUwLjcwLDk0MzI3ODE1LDEwMDAwMDA1MTIsMTAwMDAwMDcyMywxLDIwMjEwMDAwMTQsZGI3ZjRiZTgsNzc3MDQzOCzlkIznqIvml4XooYw=");
//        tzMacros1.add(tzMacros);
//
//        tzMacros = new TzMacros();
//        tzMacros.setMacro("%%CHECK_CLICKS%%");
//        tzMacros.setValue("MzY0MTkyNzQ1MTM4NDM4MTk1LDAsMjE4LjY5LjUwLjcwLDk0MzI3ODE1LDEwMDAwMDA1MTIsMTAwMDAwMDcyMywxLDIwMjEwMDAwMTQsZGI3ZjRiZTgsNzc3MDQzOCzlkIznqIvml4XooYw=");
//        tzMacros1.add(tzMacros);
//
//        tzMacros = new TzMacros();
//        tzMacros.setMacro("%%CHECK_VIEWS%%");
//        tzMacros.setValue("MzY0MTkyNzQ1MTM4NDM4MTk1LDAsMjE4LjY5LjUwLjcwLDk0MzI3ODE1LDEwMDAwMDA1MTIsMTAwMDAwMDcyMywxLDIwMjEwMDAwMTQsZGI3ZjRiZTgsNzc3MDQzOCzlkIznqIvml4XooYw=");
//        tzMacros1.add(tzMacros);
////
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%TS%%");
////        tzMacros.setValue("QkZCQkJFNDQ4MzQ4MjdEOEEyRjA2NTIxQURCM0JCNkQ=");
////        tzMacros1.add(tzMacros);
////
//        bid.setMacros(tzMacros1);
//
//
//        tzNative.setTitle("不知道怎么学英语？");
//        tzImage.setUrl("http://oimagec7.ydstatic.com/image?id=-3617364665137034073&product=adpublish&w=1280&h=720&sc=0&rm=2&gsb=0&gsbd=60&sc=0");
//        tzImage.setH(720);
//        tzImage.setW(1280);
//        image.add(tzImage);
//        tzNative.setIcon(tzIcon);
//        tzNative.setImages(image);
//
//        bid.setNATIVE(tzNative);
//
//        bid.setSource("ceshi");
//        bid.setImpid(request.getImp().get(0).getId());
//        bidList.add(bid);
//        seat.setBid(bidList);
//        seatList.add(seat);
//
//
//        tzBidResponse.setBidid("1630375998821");
//        tzBidResponse.setId(request.getId());
//        tzBidResponse.setProcess_time_ms(Long.valueOf(36));
//        tzBidResponse.setSeatbid(seatList);//广告集合对象
//
//        log.info("此返回为测试返回");
//
//        return  tzBidResponse;
//    }
//
//
//
//    /**
//     * 开屏
//     */
//    public static final TzBidResponse KpbidResponseTest (TzBidRequest request) {
//        List<TzSeat> seatList = new ArrayList<>();
//        TzSeat seat = new TzSeat();
//        List<TzBid> bidList = new ArrayList<>();
//        TzBid bid = new TzBid();
//        TzBidResponse tzBidResponse = new TzBidResponse();
//        List<TzMacros> tzMacros1 = new ArrayList();
//        TzMacros tzMacros = new TzMacros();
//        List<TzImage> tzImages = new ArrayList();
//        TzImage tzImage = new TzImage();
//
//
//        bid.setAd_type(5);
//        bid.setAdid("25228730");
//        bid.setTitle("开屏");
//
//        bid.setClick_url("http://cpro.baidu.com/cpro/ui/uijs.php?en=mywWUA71T1Yknzu9TZKxpyfqn1m3nWRdnW6srau9TZKxTv-b5ywBPvmYmhR3FhFbpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnauWpjYsFhPdpyfqnDfsPDRzfRckwHIAPHTvPRc1nRnsf1n4f1RLwWwanWPtnaubpHYdnWfzrj6hpyd-pHYYPhmkuWIhPWP-uAR3uj0dPj01nWw9n1RLnyNbnhuWuauGUyNGgvPs5HchpgPxmgKs5HDhph_qn164PvfYmW9WPjmdrjwBPBuo5iNawBNafzNDniNDnAdsmyPWFRndFRRsFRfkFRcdFRFaFRuKFRc4FRc4FhkdpvbqniuVmLKV5HD4Pj0kFhq15y78uZFEpyfhTHdbmWIhPAF-r7qWTZchThcqniuzT1YkFMPbpdqv5HfhTvwogLu-TMPGUv3qPi3vQW0hTvN_UANzgv-b5HchTv-b5ycvrjfvPynYmW9bPjbLn16hTLwGujYvnj0kFMfqIZKWUA-WpvNbndqVUvFxmgPsg1nhIAYqnWTdPHc4Pj03FMwVT1YkPWf1n1RLrjn4FMwd5HT3njfdP16hIHdCIZwsFHPKFHFAFHFAPAFbmWw9FHFDFHFDuAcLuWwBuH6-nbNWUvYhIWYzFhbqnjIbrjc3ryc&action_type=16&adx=1&besl=-1&br=12&c=news&cf=1&cp=fc_middle_page_app&cvrq=19748&ds=fmp&eid_list=1022_201014_203568_203752_205833_206556_207279_208396_209006_209176_209479&expid=200408_201008_201014_201415_201708_203000_203568_203752_205833_206232_206556_208396_209006&fr=33&fv=0&h=720&haacp=156&iad=0&iad_ex=0&iif=1&img_typ=20578&itm=0&lu_idc=tc&lukid=1&lus=b68465c4b8d49738&lust=61f3a68f&luwtr=3474019357187637248&mscf=0&mtids=3000003182&n=10&nttp=3&oi=25&p=baidu&sce=5&sh=2034&sr=464&ssp2=0&sw=1080&swi=4&tpl=template_inlay_all_mobile_lu_native_ad_app_feed&tsf=dtp:2&tt_sign=mpacc%C5%E0%D1%B5%B8%A8%B5%BC&tt_src=0&u=&uicf=lurecv&urlid=0&vn=8103&w=1280&wi=4&eot=1");
//
////        bid.setDeeplink_url("openapp.jdmobile://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_f734cef4afba45d5bbde46f789546704");
//
////        List<String> checkViews = new ArrayList<>();
////        checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pvf/test?pv=%%CHECK_VIEWS%%");
////
////        bid.setCheck_views(checkViews);
////        List<String> clickList = new ArrayList<>();
////        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicksf/test?checkClicks=%%CHECK_CLICKS%%");
////        bid.setCheck_clicks(clickList);
//
//        List<String> checkViews = new ArrayList<>();
//        checkViews.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/pv/test?pv=%%CHECK_VIEWS%%");
//
//        bid.setCheck_views(checkViews);
//        List<String> clickList = new ArrayList<>();
//        clickList.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/checkClicks/test?checkClicks=%%CHECK_CLICKS%%");
//        bid.setCheck_clicks(clickList);
//        bid.setClicktype("0");
//
////       bid.setPrice(550);
//
////        TzBidApps tzBidApps = new TzBidApps();
////        tzBidApps.setBundle("com.zslm.xishuashua");
////        tzBidApps.setApp_name("喜刷刷");
////        tzBidApps.setApp_icon("https://tz-1305375713.cos.ap-beijing.myqcloud.com/tzIcoMaterial/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20220105183627.png");
////        bid.setApp(tzBidApps);
////        List<String> clickList2 = new ArrayList<>();
////        clickList2.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start?downloadStart=%%DOWN_LOAD%%");
////       // clickList2.add("http://xss-test.zhilianghui.com:8081/api/download/report");
////        bid.setCheck_start_downloads(clickList2);
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%DOWN_LOAD%%");
////        tzMacros.setValue("MSwxLDEsMSwxLDEsMSwx");
////        tzMacros1.add(tzMacros);
//
//        tzMacros = new TzMacros();
//        tzMacros.setMacro("%%CHECK_CLICKS%%");
//        tzMacros.setValue("MzY0MTkyNzQ1MTM4NDM4MTk1LDAsMjE4LjY5LjUwLjcwLDk0MzI3ODE1LDEwMDAwMDA1MTIsMTAwMDAwMDcyNCwxLDIwMjEwMDAwMTQsZGI3ZjRiZTgsNzc3MDQzOCzlkIznqIvml4XooYw=");
//        tzMacros1.add(tzMacros);
//
//        tzMacros = new TzMacros();
//        tzMacros.setMacro("%%CHECK_VIEWS%%");
//        tzMacros.setValue("MzY0MTkyNzQ1MTM4NDM4MTk1LDAsMjE4LjY5LjUwLjcwLDk0MzI3ODE1LDEwMDAwMDA1MTIsMTAwMDAwMDcyNCwxLDIwMjEwMDAwMTQsZGI3ZjRiZTgsNzc3MDQzOCzlkIznqIvml4XooYw=");
//        tzMacros1.add(tzMacros);
//
////        bid.setNurl("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/winPrice/test?price=aHR0cDovL2x4LWNoYXJnZS55aWRpYW56aXh1bi5jb20vb3Blbi93aW5fbm90aWNlP2V4dD1DR01TSkdVeE9XWmpNbUl5TFdSbE1qY3ROR1JpTXkwNVltTmpMV1ZoTjJVd01UYzFZVEpqTkJnQklBRW9BVEQ5NGRPdHVTODRfdl9fX19fX19fX19BVWdEVVBfX19fX19fX19fX3dGWUFtQ0FDR2lnd2g1d0FYaWd3aDZJQWJEakxaZ0JBcUFCQXJJQkhHaDBkSEJ6T2k4dmQzZDNMbmxwWkdsaGJucHBlSFZ1TG1OdmJTLTZBUVIwWlhOMHdnRTFjSEp2ZG1sdVkyVTlNekk3SUhCeWIzWnBibU5sVG1GdFpUMHpNekF3TURBN0lHTnBkSGs5TURzZ1kybDBlVTVoYldVOU95REtBUWQxYm10dWIzZHUwZ0VIZFc1cmJtOTNidklCRWhvUU9HSTFNamhsWkdNME56ZGlZVFl6WlBvQkJIZHBabW1LQW5ZS0RqSXlNeTR4TURRdU1UWXdMams1R2lCRU5ERkVPRU5FT1RoR01EQkNNakEwUlRrNE1EQTVPVGhGUTBZNE5ESTNSVklnUkRReFJEaERSRGs0UmpBd1FqSXdORVU1T0RBd09UazRSVU5HT0RReU4wVmFJREZrTmpWbVpUQTRZV1EzT1dNeVpHRTBNalF4TnpRMU5EQXhPR1ptTTJVMmtBSUJvQUtnd2g2eUFoVUtCM1Z1YTI1dmQyNFNCM1Z1YTI1dmQyNGFBVEhDQXAwQlRXOTZhV3hzWVM4MUxqQWdLRXhwYm5WNE95QkJibVJ5YjJsa0lERXdPeUJXTWpBeE1VRWdRblZwYkdRdlVWQXhRUzR4T1RBM01URXVNREl3T3lCM2Rpa2dRWEJ3YkdWWFpXSkxhWFF2TlRNM0xqTTJJQ2hMU0ZSTlRDQWdiR2xyWlNCSFpXTnJieWtnVm1WeWMybHZiaTgwTGpBZ1EyaHliMjFsTHpjNExqQXVNemt3TkM0NU5pQk5iMkpwYkdVZ1UyRm1ZWEpwTHpVek55NHpOc29DQ0dWNFkyaGhibWRsMGdJS2MzTndYM1JsYzNSZk1kb0NDR1Y0WTJoaGJtZGw0Z0lTWVhCd1gyeGhkVzVqYUY5emRXTmpaWE56eUFNQmtBU2d3aDZZQktEQ0hxQUVBYkFFQWc9PcKvbz12MiZhbXA7cHJpY2U9MCwxMjM0NTY3OCwxLjEuMS4yLOS4gOeCueWSqOivoizmtYvor5VBcHA=&winNotice=%%WIN_PRICE%%");
////
////        List<String> check_start_downloads = new ArrayList<>();
////        check_start_downloads.add("http://adx-bid.tianzhuobj.com/ad-core-master/api/feedback/download/start/test?downloadStart=%%DOWN_LOAD%%");
////
////
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%WIN_PRICE%%");
////        tzMacros.setValue("OEIyMTg5MEU5OUQ2MEQ0RkFFMENGQjhEMTU2MkY3NUE%3D");
////        tzMacros1.add(tzMacros);
////
////
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%CLICK_URL%%");
////        tzMacros.setValue("aHR0cHM6Ly93d3cueWlkaWFueml4dW4uY29tLywxNjMwMzA0MjY4NDk2LDM5LjEzMC4zNS44MCzkuIDngrnotYTorq8s5rWL6K+VQXBw");
////        tzMacros1.add(tzMacros);
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%CHECK_CLICKS%%");
////        tzMacros.setValue("aHR0cDovL3AuY2xrc2VydmljZS55b3VkYW8uY29tL2Nsay9yZXF1ZXN0LnM/c2xvdD00NmM2Y2QxZTBhZjJmNmFjZjAxMmJhYWJmNThmMThkNSZrPXR2JTJCSGJCSDNscEdOdEJpSkNjUDJrSm9zNGdXYVIxbHdINDBkMzMlMkZVbWduWHhvJTJCb1J4Y0pwampBZ0t1VmlDcXZYcXZCNXN5TXMxQTR0YnlObnRPdENXJTJCYVVtZFZkRUJ5djdoJTJGT0MwSVVpSmRWVUJvQnZQRDRyUTBIZnV0aXBpYTJQbSUyRmZzbVk2SiUyQlVaSll1eE8lMkYzOWQlMkZ4VzQ5aGJCNkVVOW5ESklKY29jQkxvM3ZrbFA4aDRTN1piYnBra3g1aTU4dXVYJTJCOVVLYnE2NDRTcXUxS3diZE5qQiUyQmRiaDE0c3VjRlBCVnVPb0RCdHBaaFhUQW12c3IxZWlQWHVyWXJwbHo2TE9Ea1VjaGJQTk9lcXVtRkc0TEdMQ2tLd0VkT3R2TUhRN2RpTFFTdWN5V2lYNEdoR2RnOSUyQlZhdTZkZE5TcVVodWpxVyUyRlFvdEU2eHZPcE1XJTJCalhqN0g5WXdGS3dPTXNvcjM1Mk40RVdwZ1FGYkttampqVDlKQnRMcEpwYmQ0NW9RWmVWRHNPdCUyRjZGczZQTjZXenZiTXVNUHNDTFMyaUlldmJRUHhwWFlWT1F5cHJwdHhzWjlvTjNHb3BLdSUyQk1uMWN0Q1ZmUyUyRjdnUzc0aWMxdG1tWkVOa3dhVW53TG1YV3I5OUh0UHVaMU5ERkVLNWJoNG9LUEd0TkVib2xxZ1FIaVlkanZUclBwNXQ4MUpseTNPYllObU40eSUyRldBM3ByckZqeXBTV0I5dGklMkJ6TnpacWoxd1JPVUdERk5tSzJPV2dUSVhlaktVdEwxZHRDa1FTdyUyQjY5bTNFaXJTanBzemJTYVF0S2FLMXZhMUUlMkIxTEZPZGFZNWptJTJCTDFhMFpMVnRydlp1Y1R3bm5LY0NvbTVVUnYlMkJzZldPMzdDeHA5dUNCWWt6VnhUa1hSU1JHJTJCbFNRNmowY3dOcGFTWXJzaDltc3RzdDFaV2RQamxKJTJGZDdxaWdpWXNFOVlvSyUyQjQ1JTJCVmNSUTNQaEE2eVhSZ1ZtdzQ4WWhUQ0hsZiUyRlhSZWM4cjJTMjB6UGQ1JTJCWFJOYmJxVTZtd05BZFlLMnFtTklmZzgzZW9OM3ozOTdYTDJzU3dRVURnRE5NbFBxT05PWFRSYmwwJTJGUVo4SlJ6JTJGM0IxbVNaTDRHZTVIOFNnZVklMkJ5MXRNclZ4JTJGV1JDdmh4WnBITUpWJTJGTm1ob2ElMkZudTFWY1VhUU9wTVlSeVdYZElsc1RWWHVSVURTSk1hY1J4UGhFRmNudHlIZFRBN0pXOEhLSWI1MEZsNEk2WUEwemRRMEhoRE5USjJKbUpzOFEyVzZieFRua2ZKSE9XQndDNnRBWElqUSUyQjlQMTNZVGhvb2VHVHFudTVHYVFoNDhydFNOWk5oRTQlMkJuRXFyNHZDOWdOM2ZaeHBmNEgzdVRHTEdIV01SRGo5SXJmeTBEUXNUU0VlZkIxa3VYNVlqQ0I3SWJIN0tKV3lCQ0phcEl3OTljY0NDUDdIM0NiYUFhMldFS0FnSVZza1g3SHpPWUlBN2RjeGJLdGJSTEYyNXM5MnlaUjR6UmRJREhFVGc4NCUyQkFpQ2RicWxJSkFLTGJ4JTJGM056TE1QZCUyQmd1TEhsTzZDVjBWeDV1bnp1UmxwSkZDWEdUa29leWRhZFlnazlYQlp3VG9MbWRMT1BVdEdIQVVtb3lqeTNxOXJWSXhHVXB3WmpiQmxtWEFRa2Y1SkZvVnNmajROdUpubjNuUXRNck1PT3FuRXJvM2Nrcjd4TWVLZ1FOM01CdzA0bWptUjlsOFFNRFdicG9CczhueEcwQVhCN1klMkY2dVpWeklRcnZoTmlkU3RGRG9Zc2tXZUdDcWFQb2ZEcDRNdW1qbFVIJTJCTTduUGI1cFVlVkVYdWdHamhERUslMkZkbU9PM0VUejJFajE4YVBxRWNYQ2FZNHdJQ3JsWWdxcjlmR2o2aEhGd21tT01DQXE1V0lLcSUyRlh4byUyQm9SeGNKcGpqQWdLdVZpQ3F2SGJzTUklMkZEampBSUhLOUM1byUyQktxNlElM0QlM0QmaXNyZD0wJnlvdWRhb19iaWQ9NjYyZmIxNjgtNTg1Yi00NjA3LTllMGYtMWQ5YjY5NDcxYTI1JnlvdWRhb19kZXZpY2VJZD1ENDFEOENEOThGMDBCMjA0RTk4MDA5OThFQ0Y4NDI3RSZjb252PUVnbzBNakEzTkRRME1ETXhHQUVnQWlydUFtaDBkSEJ6T2k4dmIzQmxiaTV6Ym5OelpHc3VZMjl0TDNWbkwyRmtMMlJwYzNCc1lYa3ZZMnhwWTJ0elAyRmtYM0JzWVhSbWIzSnRQWGx2ZFdSaGJ5WmhjSEJmY0d4aGRHWnZjbTA5WVc1a2NtOXBaQ1p6ZFhKc1gzUnZhMlZ1UFRadFkxY21ZMjl1ZGoxZlgyTnZiblpmWHlaa1pYWnBZMlU5WDE5a1pYWnBZMlZmYVdSZlh5WnlaWEU5WDE5eVpYRmZhV1JmWHlaaFpIWmxjblJwYzJWeVgybGtQVjlmYzNCdmJuTnZjbDlwWkY5ZkptTmhiWEJoYVdkdVgybGtQVjlmWTJGdGNHRnBaMjVmYVdSZlh5WmhaRjlwWkQxZlgyZHliM1Z3WDJsa1gxOG1ZM0psWVhScGRtVmZhV1E5WDE5amIyNTBaVzUwWDJsa1gxOG1aR1YyWld4dmNHVnlQVjlmWkdWMlpXeHZjR1Z5WDJsa1gxOG1ZWEJ3UFY5ZllYQndYMmxrWDE4bWFYQTlYMTlwY0Y5ZkptOXpQVjlmYjNOZlh5WjBjejFmWDNSelgxOG1iMkZwWkQxZlgyOWhhV1JmWHlaMVlUMWZYM1ZoWDE4bWRXZGZjMlZ0ZG1WeVBYWXhMakF1TUEmaWlkPSU3QiUyMi03MDE4MDk4MjQ0MjE0NDQ2NjU3JTIyJTNBNCU3RCZzaWQ9Mzc1NzgmdGlkPTEyLDE2MzAzNzU5OTg4MjEsMTgwLjk1LjE2Mi41OSzmnInpgZMs5rWL6K+VQXBwLEs=");
////        tzMacros1.add(tzMacros);
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%CHECK_VIEWS%%");
////        tzMacros.setValue("aHR0cDovL2RzcC1pbXByMi55b3VkYW8uY29tL2suZ2lmP3lkX2V3cD00MjEmeWRfZXh0PUV0d0JDZ0V3RWlBME5tTTJZMlF4WlRCaFpqSm1ObUZqWmpBeE1tSmhZV0ptTlRobU1UaGtOU0t5QVFpc3hvWU1FTTczZ2dNWXhzbEVJT0NERXlpcTN3SXdHVGdWWlFBQTBrTndBSGdBZ0FFQW1BRUJvZ0VMVkhKaFpHbDBhVzl1WVd5NkFUQjdJazlTUkVWU1JVUmZTVVFpT2lJeElpd2lkSEpoWm1acFkxOXpaV2R0Wlc1MFlYUnBiMjVmYVdRaU9pSTBJbjNnQWRBRmdBSUFpZ0lnWVRFMFlUWXpNR0pqTm1ReE5EaGlaVEEwT0dVek9UUTJNamN6TlRReU1UQ1NBZ1V3TURBNE1KZ0NIYUFDQUtnQ0FMZ0NBY0FDQWNnQ0FOQUM0Z1F3QWlJa05qWXlabUl4TmpndE5UZzFZaTAwTmpBM0xUbGxNR1l0TVdRNVlqWTVORGN4WVRJMUtHNHdBRG9BUWdCU0RURTRNQzQ1TlM0eE5qSXVOVGxxRFRFMk16QXpOelU1T1RnNU5EaDRBSUlCQUlnQnBRT1FBZVRyN2MtNUw2Z0JBYkFCQWJnQkFjSUJCVEUwTnprMDBBRUIyZ0VnUkRReFJEaERSRGs0UmpBd1FqSXdORVU1T0RBd09UazRSVU5HT0RReU4wWGlBV3NhRHpnMk5qY3hNakF6TXpJMk5qRTRNeUlBS2dBeUVFVTJNVEkyTURWRlJqRkROa1JGTkRkQ1FEWTNNVU0yUmtVME1FVTVORFJFUWpRNE16YzRSREJGTlRrME9VRTVOamhCTnpZeE1qRXhZMlV6TnpjM01HTmhZMll6TkdFMFpqbG1ORFk0TldSaVpEbEtBT2dCc0JhQUF0c0VpZ0lLZEdnd01qSXRPVEF4TXBnQ2lvYmtCSmdDLXZYakJKZ0Nrb2JrQkxJQ0Rnb0tOREl3TnpRME5EQXpNUkFCdUFJSXdnSVNDUUFBQUFBQUFBQUFFUUFBQUFBQUFBQUEmaWlkPSU3QiUyMi03MDE4MDk4MjQ0MjE0NDQ2NjU3JTIyJTNBNCU3RCZzaWQ9Mzc1NzgmdGlkPTEyLDE2MzAzNzU5OTg4MjEsMTgwLjk1LjE2Mi41OSzmnInpgZMs5rWL6K+VQXBwLEs=");
////        tzMacros1.add(tzMacros);
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%DOWN_LOAD%%");
////        tzMacros.setValue("aHR0cDovL3AuY2xrc2VydmljZS55b3VkYW8uY29tL2Nsay9yZXF1ZXN0LnM/c2xvdD00NmM2Y2QxZTBhZjJmNmFjZjAxMmJhYWJmNThmMThkNSZrPXR2JTJCSGJCSDNscEdOdEJpSkNjUDJrSm9zNGdXYVIxbHdINDBkMzMlMkZVbWduWHhvJTJCb1J4Y0pwampBZ0t1VmlDcXZYcXZCNXN5TXMxQTR0YnlObnRPdENXJTJCYVVtZFZkRUJ5djdoJTJGT0MwSVVpSmRWVUJvQnZQRDRyUTBIZnV0aXBpYTJQbSUyRmZzbVk2SiUyQlVaSll1eE8lMkYzOWQlMkZ4VzQ5aGJCNkVVOW5ESklKY29jQkxvM3ZrbFA4aDRTN1piYnBra3g1aTU4dXVYJTJCOVVLYnE2NDRTcXUxS3diZE5qQiUyQmRiaDE0c3VjRlBCVnVPb0RCdHBaaFhUQW12c3IxZWlQWHVyWXJwbHo2TE9Ea1VjaGJQTk9lcXVtRkc0TEdMQ2tLd0VkT3R2TUhRN2RpTFFTdWN5V2lYNEdoR2RnOSUyQlZhdTZkZE5TcVVodWpxVyUyRlFvdEU2eHZPcE1XJTJCalhqN0g5WXdGS3dPTXNvcjM1Mk40RVdwZ1FGYkttampqVDlKQnRMcEpwYmQ0NW9RWmVWRHNPdCUyRjZGczZQTjZXenZiTXVNUHNDTFMyaUlldmJRUHhwWFlWT1F5cHJwdHhzWjlvTjNHb3BLdSUyQk1uMWN0Q1ZmUyUyRjdnUzc0aWMxdG1tWkVOa3dhVW53TG1YV3I5OUh0UHVaMU5ERkVLNWJoNG9LUEd0TkVib2xxZ1FIaVlkanZUclBwNXQ4MUpseTNPYllObU40eSUyRldBM3ByckZqeXBTV0I5dGklMkJ6TnpacWoxd1JPVUdERk5tSzJPV2dUSVhlaktVdEwxZHRDa1FTdyUyQjY5bTNFaXJTanBzemJTYVF0S2FLMXZhMUUlMkIxTEZPZGFZNWptJTJCTDFhMFpMVnRydlp1Y1R3bm5LY0NvbTVVUnYlMkJzZldPMzdDeHA5dUNCWWt6VnhUa1hSU1JHJTJCbFNRNmowY3dOcGFTWXJzaDltc3RzdDFaV2RQamxKJTJGZDdxaWdpWXNFOVlvSyUyQjQ1JTJCVmNSUTNQaEE2eVhSZ1ZtdzQ4WWhUQ0hsZiUyRlhSZWM4cjJTMjB6UGQ1JTJCWFJOYmJxVTZtd05BZFlLMnFtTklmZzgzZW9OM3ozOTdYTDJzU3dRVURnRE5NbFBxT05PWFRSYmwwJTJGUVo4SlJ6JTJGM0IxbVNaTDRHZTVIOFNnZVklMkJ5MXRNclZ4JTJGV1JDdmh4WnBITUpWJTJGTm1ob2ElMkZudTFWY1VhUU9wTVlSeVdYZElsc1RWWHVSVURTSk1hY1J4UGhFRmNudHlIZFRBN0pXOEhLSWI1MEZsNEk2WUEwemRRMEhoRE5USjJKbUpzOFEyVzZieFRua2ZKSE9XQndDNnRBWElqUSUyQjlQMTNZVGhvb2VHVHFudTVHYVFoNDhydFNOWk5oRTQlMkJuRXFyNHZDOWdOM2ZaeHBmNEgzdVRHTEdIV01SRGo5SXJmeTBEUXNUU0VlZkIxa3VYNVlqQ0I3SWJIN0tKV3lCQ0phcEl3OTljY0NDUDdIM0NiYUFhMldFS0FnSVZza1g3SHpPWUlBN2RjeGJLdGJSTEYyNXM5MnlaUjR6UmRJREhFVGc4NCUyQkFpQ2RicWxJSkFLTGJ4JTJGM056TE1QZCUyQmd1TEhsTzZDVjBWeDV1bnp1UmxwSkZDWEdUa29leWRhZFlnazlYQlp3VG9MbWRMT1BVdEdIQVVtb3lqeTNxOXJWSXhHVXB3WmpiQmxtWEFRa2Y1SkZvVnNmajROdUpubjNuUXRNck1PT3FuRXJvM2Nrcjd4TWVLZ1FOM01CdzA0bWptUjlsOFFNRFdicG9CczhueEcwQVhCN1klMkY2dVpWeklRcnZoTmlkU3RGRG9Zc2tXZUdDcWFQb2ZEcDRNdW1qbFVIJTJCTTduUGI1cFVlVkVYdWdHamhERUslMkZkbU9PM0VUejJFajE4YVBxRWNYQ2FZNHdJQ3JsWWdxcjlmR2o2aEhGd21tT01DQXE1V0lLcSUyRlh4byUyQm9SeGNKcGpqQWdLdVZpQ3F2SGJzTUklMkZEampBSUhLOUM1byUyQktxNlElM0QlM0QmaXNyZD0wJnlvdWRhb19iaWQ9NjYyZmIxNjgtNTg1Yi00NjA3LTllMGYtMWQ5YjY5NDcxYTI1JnlvdWRhb19kZXZpY2VJZD1ENDFEOENEOThGMDBCMjA0RTk4MDA5OThFQ0Y4NDI3RSZjb252PUVnbzBNakEzTkRRME1ETXhHQUVnQWlydUFtaDBkSEJ6T2k4dmIzQmxiaTV6Ym5OelpHc3VZMjl0TDNWbkwyRmtMMlJwYzNCc1lYa3ZZMnhwWTJ0elAyRmtYM0JzWVhSbWIzSnRQWGx2ZFdSaGJ5WmhjSEJmY0d4aGRHWnZjbTA5WVc1a2NtOXBaQ1p6ZFhKc1gzUnZhMlZ1UFRadFkxY21ZMjl1ZGoxZlgyTnZiblpmWHlaa1pYWnBZMlU5WDE5a1pYWnBZMlZmYVdSZlh5WnlaWEU5WDE5eVpYRmZhV1JmWHlaaFpIWmxjblJwYzJWeVgybGtQVjlmYzNCdmJuTnZjbDlwWkY5ZkptTmhiWEJoYVdkdVgybGtQVjlmWTJGdGNHRnBaMjVmYVdSZlh5WmhaRjlwWkQxZlgyZHliM1Z3WDJsa1gxOG1ZM0psWVhScGRtVmZhV1E5WDE5amIyNTBaVzUwWDJsa1gxOG1aR1YyWld4dmNHVnlQVjlmWkdWMlpXeHZjR1Z5WDJsa1gxOG1ZWEJ3UFY5ZllYQndYMmxrWDE4bWFYQTlYMTlwY0Y5ZkptOXpQVjlmYjNOZlh5WjBjejFmWDNSelgxOG1iMkZwWkQxZlgyOWhhV1JmWHlaMVlUMWZYM1ZoWDE4bWRXZGZjMlZ0ZG1WeVBYWXhMakF1TUEmaWlkPSU3QiUyMi03MDE4MDk4MjQ0MjE0NDQ2NjU3JTIyJTNBNCU3RCZzaWQ9Mzc1NzgmdGlkPTEyLDE2MzAzNzU5OTg4MjEsMTgwLjk1LjE2Mi41OSzmnInpgZMs5rWL6K+VQXBwLDE=");
////        tzMacros1.add(tzMacros);
////
////        tzMacros = new TzMacros();
////        tzMacros.setMacro("%%TS%%");
////        tzMacros.setValue("QkZCQkJFNDQ4MzQ4MjdEOEEyRjA2NTIxQURCM0JCNkQ=");
////        tzMacros1.add(tzMacros);
////
//        bid.setMacros(tzMacros1);
//
//
//        tzImage.setUrl("http://oimagec7.ydstatic.com/image?id=-3617364665137034073&product=adpublish&w=1280&h=720&sc=0&rm=2&gsb=0&gsbd=60&sc=0");
//        tzImage.setH(1280);
//        tzImage.setW(720);
//        tzImages.add(tzImage);
//        bid.setImages(tzImages);
//
//        bid.setSource("");
//        bid.setImpid(request.getImp().get(0).getId());
//        bidList.add(bid);
//        seat.setBid(bidList);
//        seatList.add(seat);
//
//
//        tzBidResponse.setBidid("1630375998821");
//        tzBidResponse.setId(request.getId());
//        tzBidResponse.setProcess_time_ms(Long.valueOf(36));
//        tzBidResponse.setSeatbid(seatList);//广告集合对象
//
//        log.info("此返回为测试返回");
//
//        return  tzBidResponse;
//    }
//    /**
//     * 开屏
//     */
//    public static final String response = "{\n" +
//            "    \"code\": 200,\n" +
//            "    \"msg\": \"成功\",\n" +
//            "    \"data\": {\n" +
//            "        \"id\": \"tz_wifi_test\",\n" +
//            "        \"bidid\": null,\n" +
//            "        \"seatbid\": [\n" +
//            "            {\n" +
//            "                \"bid\": [\n" +
//            "                    {\n" +
//            "                        \"id\": null,\n" +
//            "                        \"impid\": null,\n" +
//            "                        \"price\": 0.0,\n" +
//            "                        \"adid\": \"POSIDcm56ekyo43kc\",\n" +
//            "                        \"nurl\": null,\n" +
//            "                        \"adm\": null,\n" +
//            "                        \"adms\": null,\n" +
//            "                        \"adomain\": null,\n" +
//            "                        \"iurl\": null,\n" +
//            "                        \"cid\": null,\n" +
//            "                        \"crid\": null,\n" +
//            "                        \"cat\": null,\n" +
//            "                        \"attr\": null,\n" +
//            "                        \"dealid\": null,\n" +
//            "                        \"h\": null,\n" +
//            "                        \"w\": null,\n" +
//            "                        \"title\": null,\n" +
//            "                        \"sub_title\": null,\n" +
//            "                        \"desc\": null,\n" +
//            "                        \"style_id\": null,\n" +
//            "                        \"android_url\": null,\n" +
//            "                        \"ios_url\": null,\n" +
//            "                        \"download_url\": null,\n" +
//            "                        \"download_md5\": null,\n" +
//            "                        \"click_url\": \"https://open.adview.cn/agent/alnotice?st=6&ud2=MDUxNTE4YDdiMTJkNWc2Z2BkMjhjN2Q4ZWBjZzAwZzQ=&uuidEncType=0&sv=0&src=1&sy=0&nt=wifi&adi=20210714-114730_LOff6ty1_133-477-ZzRl-105_1&bi=com.wangniu.wifiboost&ai=kHAgo3oBAAAqfi4RVFMJRPq-3dR1id0oQK4jrLRNvFGg1Nv2523TBUDZWQz52Vgi6Sio9S9luWqanJHdvn-JGIpPhyNZl3i86SHInKunKYB9vAQxbtBBBB2UBbke3RZMyUG5qFyW0-YoJXb6XLAvMbIP6cNZ98x4fYP2fZlzw3HhzVy_dN3VnyyWW38itFxm4KHrgIK7o4ksB0js9eHOtKjanSc-TLEZROsl6tlUwhHzP1cpCkwQWuBe6m6sOGsFrqSvRb_o&target=kHAgo3oBAABSd1N1Ln4TLdqvkSAJOOoK8EuiiP-ws5JH0ZwCEUFtjw&andt=0&posId=POSIDcm56ekyo43kc&as=720x1280&se=46002&cv=&rqt=1&tm=1&aid=SDK202109230906049vqhfei3ueeq3ql&ro=1&ca=0\",\n" +
//            "                        \"deeplink_url\": \"openapp.jdmobile://virtual?params=%7B%22SE%22%3A%22ADC_3%2FBp2y96tx9bnaAlwjgn%2B%2BquBGaOpsV2318dXafc94QUhX8jMTQ0OvScDqBIkT%2BbzeNfhtBO0KuqTjU8LzFwySomROBa4%2FUorwtrDZ76FWbG4TtPQ1X5jv8rPE%2B1djzVqUTVAwbNpj8o116OZvePvskd5cgF72900SyVaiWgt3Q%3D%22%2C%22action%22%3A%22to%22%2C%22category%22%3A%22jump%22%2C%22des%22%3A%22getCoupon%22%2C%22ext%22%3A%22%7B%5C%22ad%5C%22%3A%5C%22%5C%22%2C%5C%22ch%5C%22%3A%5C%22%5C%22%2C%5C%22shop%5C%22%3A%5C%22%5C%22%2C%5C%22sku%5C%22%3A%5C%22%5C%22%2C%5C%22ts%5C%22%3A%5C%22%5C%22%2C%5C%22uniqid%5C%22%3A%5C%22%7B%5C%5C%5C%22material_id%5C%5C%5C%22%3A%5C%5C%5C%221997164276%5C%5C%5C%22%2C%5C%5C%5C%22pos_id%5C%5C%5C%22%3A%5C%5C%5C%224318%5C%5C%5C%22%2C%5C%5C%5C%22sid%5C%5C%5C%22%3A%5C%5C%5C%2283_4e2431a935f54339b9395e5a8e024f82_1%5C%5C%5C%22%7D%5C%22%7D%22%2C%22kepler_param%22%3A%7B%22channel%22%3A%222e3b9ecfb3a1465badbbbeb48df4140c%22%2C%22source%22%3A%22kepler-open%22%7D%2C%22m_param%22%3A%7B%22jdv%22%3A%22238571484%7Cadviewnativeadx%7Ct_1000011166_1997164276_4318%7Cadrealizable%7C_2_app_0_1ccd165175fb43a98ba29c518358abf4-p_4318%22%7D%2C%22sourceType%22%3A%22adx%22%2C%22sourceValue%22%3A%22adviewnativeadx_83%22%2C%22url%22%3A%22https%3A%2F%2Fccc-x.jd.com%2Fdsp%2Fnc%3Fext%3DaHR0cHM6Ly9wcm8ubS5qZC5jb20vbWFsbC9hY3RpdmUvMnlFVVhvSnJuUGRtVng3YzdZbWJWWXJxeGZXNy9pbmRleC5odG1sP1BUQUc9MTcwNTEuOS4xJmFkX29kPTE%26log%3Dv2Fo_X9T9p5mupt9rAfZHKZTCy8rHhS9Ay1B8z2pQjAuRi4bv8oU7spa9NVwru2sexM6ozPR66bbEHs5YzXnoXv7CCSASzRoNh2ZqStL1bO5Pl-xTD-Nz_OW61dpSd4wM72ut3BVAN_JxeW_mk_aFj7uSn5J_fOo8T8N4r9KU88kyLVjqh3Ph8SYUztqX8psEgf_wyPmwlHA0SMzWOYKN68R8yOUP5Amfn0sh5gWOdCn0yY6nsK0K2v_-RkN0DJpTZyh7UTH9WJ2f8SM3bvdvcUT3wiE3FIlg17Lokm5QW0aXyWby72pH7TESA3ZHO6nuF1sZ3s1Rlz1hNUFskQZaFYHlRMomR2ZO_-WqlaFc9nAzeKVPgl3E9IVGTXQnFZ8cL5Zs0IV06kC9G9a2YSE2BP-ppxUuWjBKwtAtyaQiAlT4dRKD6rLG5swxU5kXe9nzxi6lRx4mLyuZBuJ8MMapk6vE18pToGxY9xo_ohEFL5rM2cxe1TSvPVIZE12gxLBKh5S9IbNpcsYVZtuaQ68cE3GRvDWSOYs3IV6zqO9oMFpAG_n6Q2SEvPicEZeczEK9VIJk9YyAfKLSc5JhyjTq3Y8LvMPzY6BBMPAfX2rJcl8gHwRDeUvJQGg_8iQshGbNbzPJXZZljyq9HIl3-LwIvMxMd8V58WlC0ipxS-JRUs%26v%3D404%26SE%3D1%22%7D%0A\",\n" +
//            "                        \"fallback\": null,\n" +
//            "                        \"aptAppId\": null,\n" +
//            "                        \"aptOrgId\": null,\n" +
//            "                        \"aptPath\": null,\n" +
//            "                        \"aptType\": null,\n" +
//            "                        \"aptUL\": null,\n" +
//            "                        \"ad_type\": 8,\n" +
//            "                        \"act\": 1,\n" +
//            "                        \"as\": null,\n" +
//            "                        \"xs\": null,\n" +
//            "                        \"index\": null,\n" +
//            "                        \"source\": null,\n" +
//            "                        \"valid_time\": null,\n" +
//            "                        \"check_views\": [\n" +
//            "                            \"https://open.adview.cn/agent/openDisplay.do?st=6&ud2=MDUxNTE4YDdiMTJkNWc2Z2BkMjhjN2Q4ZWBjZzAwZzQ=&uuidEncType=0&sv=0&src=1&sy=0&nt=wifi&adi=20210714-114730_LOff6ty1_133-477-ZzRl-105_1&bi=com.wangniu.wifiboost&ai=kHAgo3oBAAAqfi4RVFMJRPq-3dR1id0oQK4jrLRNvFGg1Nv2523TBUDZWQz52Vgi6Sio9S9luWqanJHdvn-JGIpPhyNZl3i86SHInKunKYB9vAQxbtBBBB2UBbke3RZMyUG5qFyW0-YoJXb6XLAvMbIP6cNZ98x4fYP2fZlzw3HhzVy_dN3VnyyWW38itFxm4KHrgIK7o4ksB0js9eHOtKjanSc-TLEZROsl6tlUwhHzP1cpCkwQWuBe6m6sOGsFrqSvRb_o&andt=0&posId=POSIDcm56ekyo43kc&as=720x1280&se=46002&cv=&rqt=1&ti=1626234455&tm=1&to=946ce39e1c72031db454c3548d73439a&aid=SDK202109230906049vqhfei3ueeq3ql&ro=1&ca=0\"\n" +
//            "                        ],\n" +
//            "                        \"check_clicks\": [\n" +
//            "                            \"https://open.adview.cn/agent/openClick.do?st=6&ud2=MDUxNTE4YDdiMTJkNWc2Z2BkMjhjN2Q4ZWBjZzAwZzQ=&uuidEncType=0&sv=0&src=1&sy=0&nt=wifi&adi=20210714-114730_LOff6ty1_133-477-ZzRl-105_1&bi=com.wangniu.wifiboost&ai=kHAgo3oBAAAqfi4RVFMJRPq-3dR1id0oQK4jrLRNvFGg1Nv2523TBUDZWQz52Vgi6Sio9S9luWqanJHdvn-JGIpPhyNZl3i86SHInKunKYB9vAQxbtBBBB2UBbke3RZMyUG5qFyW0-YoJXb6XLAvMbIP6cNZ98x4fYP2fZlzw3HhzVy_dN3VnyyWW38itFxm4KHrgIK7o4ksB0js9eHOtKjanSc-TLEZROsl6tlUwhHzP1cpCkwQWuBe6m6sOGsFrqSvRb_o&andt=0&posId=POSIDcm56ekyo43kc&as=720x1280&se=46002&cv=&rqt=1&ti=1626234455&tm=1&to=946ce39e1c72031db454c3548d73439a&aid=SDK202109230906049vqhfei3ueeq3ql&ro=1&ca=0\"\n" +
//            "                        ],\n" +
//            "                        \"check_start_downloads\": null,\n" +
//            "                        \"check_end_downloads\": null,\n" +
//            "                        \"check_start_installs\": null,\n" +
//            "                        \"check_end_installs\": null,\n" +
//            "                        \"check_activations\": null,\n" +
//            "                        \"check_success_deeplinks\": [\n" +
//            "                            \"https://open.adview.cn/agent/openDeeplink.do?suc=true&st=6&ud2=MDUxNTE4YDdiMTJkNWc2Z2BkMjhjN2Q4ZWBjZzAwZzQ=&uuidEncType=0&sv=0&src=1&sy=0&nt=wifi&adi=20210714-114730_LOff6ty1_133-477-ZzRl-105_1&bi=com.wangniu.wifiboost&ai=kHAgo3oBAAAqfi4RVFMJRPq-3dR1id0oQK4jrLRNvFGg1Nv2523TBUDZWQz52Vgi6Sio9S9luWqanJHdvn-JGIpPhyNZl3i86SHInKunKYB9vAQxbtBBBB2UBbke3RZMyUG5qFyW0-YoJXb6XLAvMbIP6cNZ98x4fYP2fZlzw3HhzVy_dN3VnyyWW38itFxm4KHrgIK7o4ksB0js9eHOtKjanSc-TLEZROsl6tlUwhHzP1cpCkwQWuBe6m6sOGsFrqSvRb_o&andt=0&posId=POSIDcm56ekyo43kc&as=720x1280&se=46002&cv=&rqt=1&ti=1626234455&tm=1&to=946ce39e1c72031db454c3548d73439a&aid=SDK202109230906049vqhfei3ueeq3ql&ro=1&ca=0\"\n" +
//            "                        ],\n" +
//            "                        \"check_fail_deeplinks\": [\n" +
//            "                            \"https://open.adview.cn/agent/openDeeplink.do?suc=false&st=6&ud2=MDUxNTE4YDdiMTJkNWc2Z2BkMjhjN2Q4ZWBjZzAwZzQ=&uuidEncType=0&sv=0&src=1&sy=0&nt=wifi&adi=20210714-114730_LOff6ty1_133-477-ZzRl-105_1&bi=com.wangniu.wifiboost&ai=kHAgo3oBAAAqfi4RVFMJRPq-3dR1id0oQK4jrLRNvFGg1Nv2523TBUDZWQz52Vgi6Sio9S9luWqanJHdvn-JGIpPhyNZl3i86SHInKunKYB9vAQxbtBBBB2UBbke3RZMyUG5qFyW0-YoJXb6XLAvMbIP6cNZ98x4fYP2fZlzw3HhzVy_dN3VnyyWW38itFxm4KHrgIK7o4ksB0js9eHOtKjanSc-TLEZROsl6tlUwhHzP1cpCkwQWuBe6m6sOGsFrqSvRb_o&andt=0&posId=POSIDcm56ekyo43kc&as=720x1280&se=46002&cv=&rqt=1&ti=1626234455&tm=1&to=946ce39e1c72031db454c3548d73439a&aid=SDK202109230906049vqhfei3ueeq3ql&ro=1&ca=0\"\n" +
//            "                        ],\n" +
//            "                        \"altype\": null,\n" +
//            "                        \"gdt_conversion_link\": null,\n" +
//            "                        \"clicktype\": null,\n" +
//            "                        \"image\": null,\n" +
//            "                        \"aic\": null,\n" +
//            "                        \"ate\": null,\n" +
//            "                        \"abi\": null,\n" +
//            "                        \"adLogo\": null,\n" +
//            "                        \"iamges\": null,\n" +
//            "                        \"video\": null,\n" +
//            "                        \"check_video_urls\": null,\n" +
//            "                        \"advertiser\": null,\n" +
//            "                        \"app\": {\n" +
//            "                            \"app_name\": \"weixin\",\n" +
//            "                            \"bundle\": \"\",\n" +
//            "                            \"app_icon\": \"\",\n" +
//            "                            \"app_size\": 0\n" +
//            "                        },\n" +
//            "                        \"attachDetail\": null,\n" +
//            "                        \"macros\": null,\n" +
//            "                        \"ext\": null,\n" +
//            "                        \"native\": {\n" +
//            "                            \"ver\": \"1.0\",\n" +
//            "                            \"icon\": {\n" +
//            "                                \"url\": \"https://img.adview.cn/loff6ty1_icon_20200304155007750823_200_200.jpg\",\n" +
//            "                                \"h\": 200,\n" +
//            "                                \"w\": 200,\n" +
//            "                                \"type\": 1\n" +
//            "                            },\n" +
//            "                            \"logo\": null,\n" +
//            "                            \"images\": [\n" +
//            "                                {\n" +
//            "                                    \"id\": null,\n" +
//            "                                    \"url\": \"https://img.adview.cn/loff6ty1_main_20200304154820849265_1280_720.jpg\",\n" +
//            "                                    \"h\": 720,\n" +
//            "                                    \"w\": 1280,\n" +
//            "                                    \"type\": 3\n" +
//            "                                }\n" +
//            "                            ],\n" +
//            "                            \"video\": null,\n" +
//            "                            \"title\": \"Adview测试\",\n" +
//            "                            \"desc\": \"Adview测试\",\n" +
//            "                            \"desc2\": null\n" +
//            "                        }\n" +
//            "                    }\n" +
//            "                ],\n" +
//            "                \"seat\": null\n" +
//            "            }\n" +
//            "        ],\n" +
//            "        \"cur\": null,\n" +
//            "        \"nbr\": null,\n" +
//            "        \"debug_info\": null,\n" +
//            "        \"process_time_ms\": 16,\n" +
//            "        \"ext\": null\n" +
//            "    }\n" +
//            "}";
//
//}
