package com.mk.adx.entity.json.response.ydzx;

import lombok.Data;

import java.util.List;

/**
 * 出价对象,广告集合,目前有且只有一个
 *
 * @author yjn
 * @version 1.0
 * @date 2021/8/3 16:51
 */
@Data
public class YdzxBid {
    private String id;//出价 ID，协助日志/跟踪
    private int price;//出价，cpm
    private String nurl;//win notice url，竞价成功提醒 url,支持的“宏”标准
    private int ctype;//广告点击类型：1：跳转 2：下载
    private int templateid;//参与竞价的广告位模板类型，参照6广告位模板类型
    private String adid;//广告 ID
    private String crid;//创意 ID
    private Integer h;//素材高，单位像素
    private Integer w;//素材宽，单位像素
    private String curl;//用户点击后需要跳转到的落地页
    private String durl;//下载地址
    private String deeplinkurl;//deeplink链接，如返回结果中包括deeplink链接则调起第三方应用，否则跳转落地页
    private String title;//广告标题 (创意名称)
    private String source;//广告主名称
    private String summary;//App描述
    private String dpkgname;//仅用于下载广告，App包名
    private List<String> aurl;//素材图片地址，如为视频广告则为封面图
    private List<String> murl;//曝光监测地址数组
    private List<String> cmurl;//点击监测地址数组
    private List<String> dmurl;//仅用于下载广告，app下载开始的监测地址数组
    private List<String> downsuccessurl;//仅用于下载广告，app下载完成的监测地址数组
    private List<String> deeplinkmurl;//仅用于唤醒广告，deeplink链接调起成功监测地址
    private String playvideomurl;//仅用于视频广告，视频开始播放监测地址
    private String finishvideomurl;//仅用于视频广告，视频播放完成监测地址
    private YdzxVideo video;//视频素材，仅用于视频广告

}
