package com.mk.adx.entity.json.response.tz;

import lombok.Data;

import java.util.List;

/**
 * 若干组广告素材集合对象
 *
 * @author yjn
 * @version 1.0
 * @date 2021/6/16 16:51
 */
@Data
public class TzItem {
    private String title;//标题 (创意名称)
    private String desc;//预留描述
    private String source;//广告来源
    private String index;//序号
    private String clicktype;//点击类型:0,点击 1,跳转 2,拉活(deeplink)
    private String download_url;//下载地址
    private String click_url;//点击 url
    private String deeplink_url;//deeplinkUrl
    private String image;//单个图片
    private List<TzImage> iamges;//图片对象数组
    private String video;//视频对象
    private List<String> check_views;//曝光监测URL，支持宏替换 第三方曝光监测
    private List<String> check_clicks;//点击监测URL 第三方曝光监测
}
